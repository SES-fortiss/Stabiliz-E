package org.fortiss.smg.containermanager.impl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

//import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.Pair;
import org.fortiss.smg.actuatormaster.api.ActuatorMasterQueueNames;
import org.fortiss.smg.actuatormaster.api.IActuatorListener;
import org.fortiss.smg.actuatormaster.api.IActuatorMaster;
import org.fortiss.smg.actuatormaster.api.events.DeviceEvent;
import org.fortiss.smg.actuatormaster.api.events.DoubleEvent;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.IContainerManagerListener;
import org.fortiss.smg.containermanager.api.devices.CommandRangeStepType;
import org.fortiss.smg.containermanager.api.devices.Container;
import org.fortiss.smg.containermanager.api.devices.ContainerEdge;
import org.fortiss.smg.containermanager.api.devices.ContainerFunction;
import org.fortiss.smg.containermanager.api.devices.ContainerType;
import org.fortiss.smg.containermanager.api.devices.DeviceContainer;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.containermanager.api.devices.EdgeType;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.containermanager.api.devices.SingleContainerEdge;
import org.fortiss.smg.informationbroker.api.IDatabase;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.fortiss.smg.sqltools.lib.serialize.SimpleSerializer;
import org.slf4j.LoggerFactory;

public class ContainerManagerImpl implements ContainerManagerInterface, IActuatorListener {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(ContainerManagerImpl.class);

	public Map<String, Container> cons;
	public Map<String, ArrayList<SingleContainerEdge>> edges;

	IDatabase database;
	private DefaultProxy<IActuatorMaster> proxyMaster;
	private IActuatorMaster master;

	private DefaultProxy<IActuatorMaster> proxyMasterPermanent;
	private IActuatorMaster masterPermanent;

	private ConcurrentHashMap<String, String> listeners;

	private static ExecutorService executors;
	private Map<String, String> containerMappingHRName;
	private Map<DeviceId, String> containerMappingDeviceId;


	public static final String DB_NAME_CONTAINERS = "ContainerManager_Containers";
	public static final String DB_NAME_CONTAINEREDGES = "ContainerManager_ContainerEdges";
	public static final String DB_NAME_DEVICES = "ContainerManager_Devices";

	private static final int TIMEOUTLONG = 5000;

	private static final String DB_NAME_SPECS = "ContainerManager_Specs";

	private ArrayList<SIDeviceType> reasonableforSum = new ArrayList<SIDeviceType>(Arrays.asList(
			SIDeviceType.ConsumptionAmperemeter, SIDeviceType.ConsumptionPowermeter,
			SIDeviceType.ConsumptionPowermeterAggregated, SIDeviceType.FeedAmperemeter, SIDeviceType.FeedPowerMeter,
			SIDeviceType.FeedPowerMeterAggregated, SIDeviceType.ProductionAmperemeter,
			SIDeviceType.ProductionPowermeter, SIDeviceType.ProductionPowermeterAggregated));

	private ArrayList<SIDeviceType> reasonableforBool = new ArrayList<SIDeviceType>(
			Arrays.asList(SIDeviceType.Door, SIDeviceType.Window, SIDeviceType.LightSimple, SIDeviceType.Occupancy));

	private EdgeHandler edgeHandler;

	public ContainerManagerImpl() {

		proxyMasterPermanent = new DefaultProxy<IActuatorMaster>(IActuatorMaster.class,
				ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(), TIMEOUTLONG);
		try {
			masterPermanent = proxyMasterPermanent.init();

		} catch (IOException e) {
			logger.error("ContainerManager: Unable to connect to master");
			waitOrIsKilled();
		} catch (TimeoutException e) {
			logger.error("ContainerManager: Unable to connect to master (Timeout).");
			waitOrIsKilled();
		}

		listeners = new ConcurrentHashMap<String, String>();
		executors = Executors.newCachedThreadPool();
		containerMappingHRName = new HashMap<String, String>();
		containerMappingDeviceId = new HashMap<DeviceId, String>();

		DefaultProxy<InformationBrokerInterface> clientInfo = new DefaultProxy<InformationBrokerInterface>(
				InformationBrokerInterface.class, InformationBrokerQueueNames.getQueryQueue(), TIMEOUTLONG);

		edgeHandler = new EdgeHandler(this);

		try {
			InformationBrokerInterface broker = clientInfo.init();

			this.cons = new HashMap<String, Container>();
			this.edges = new HashMap<String, ArrayList<SingleContainerEdge>>();
			this.database = broker;

			/*
			 * Request Structure from Database (if exists)
			 */
			loadfromDB();

			/*
			 * in case some devices have already registered at the master
			 */

			updateRegisteredDevices();

			String registeredContainers = "";
			for (Container container : this.cons.values()) {
				registeredContainers = registeredContainers + container.getContainerId() + ", ";
			}
			logger.info("Registered Containers/Devices: " + registeredContainers);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void loadfromDB() {

		try {
			/*
			 * Filter out Devices, we want to consider only active devices
			 */
			String sql = "SELECT *  FROM " + ContainerManagerImpl.DB_NAME_CONTAINERS
					+ " WHERE ContainerType != 'DEVICE';";

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet == null || resultSet.size() < 1) {
				ContainerManagerImpl.logger.warn("No Containers found - adding initial Container");
				Container root = new Container("", "Initial Container", ContainerType.COMPLEX, ContainerFunction.ROOT,
						false);
				String containerId = "" + this.database.getContainerID("none", "none");
				root.setContainerId(containerId);
				this.addContainer(root);

				// SimpleSerializer.saveToDB(root.serialize(), database,
				// ContainerManagerImpl.DB_NAME_CONTAINERS);
				// this.cons.put(root.getContainerId(), root);
			}
			/*
			 * go through results and create containers
			 */
			else {
				for (Map<String, Object> result : resultSet) {
					Boolean virtual = false;
					if ((Integer) result.get("virtualcontainer") == 1) {
						virtual = true;
					}

					Container container = new Container(result.get("containerid").toString(),
							result.get("containerhrname").toString(),
							ContainerType.fromSting(result.get("containertype").toString()),
							ContainerFunction.fromString(result.get("containerfunction").toString()), virtual);
					ContainerManagerImpl.logger.info("Container added: " + result.get("containerhrname").toString()
							+ "(" + container.getContainerId() + ")");

					this.cons.put(container.getContainerId(), container);
					logger.info("Container added from DB: #" + container.getContainerId() + " Name: "
							+ container.getHrName());

				}
			}

		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}

		/*
		 * Possibly assign devices to containers they belong to
		 */

		edgeHandler.loadEdges();
	}

	@Override
	public boolean registerListener(String clientName, String queueName) throws TimeoutException {
		listeners.put(queueName, clientName);
		if (listeners.containsKey(queueName)) {
			if (listeners.get(queueName).equals(clientName)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean unregisterListener(String queueName) throws TimeoutException {
		if (listeners.containsKey(queueName)) {
			listeners.remove(queueName);
			if (!listeners.containsKey(queueName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateRegisteredDevices() {
		// try to connect to master ...
		for (int i = 0; i < 100; i++) {
			proxyMaster = new DefaultProxy<IActuatorMaster>(IActuatorMaster.class,
					ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(), 2000);
			try {
				master = proxyMaster.init();
				break;
			} catch (IOException e) {
				logger.error("ContainerManager: Unable to connect to master");
				waitOrIsKilled();
			} catch (TimeoutException e) {
				logger.error("ContainerManager: Unable to connect to master (Timeout).");
				waitOrIsKilled();
			}
		}

		if (this.master != null) {

			try {
				this.master.resendRegisteredDevices();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		} else {
			logger.debug("No Connection to Master");
		}
		try {
			proxyMaster.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void waitOrIsKilled() {

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			logger.debug("Could not sleep");
			e.printStackTrace();
		}

	}

	public String doSomething(String s) {
		return "Hello smg";
	}

	@Override
	public boolean isComponentAlive() {
		/**
		 * perform quick self check
		 */
		boolean alive = false;
		if (!(this.cons.size() > 0)) {
			return alive;
		}
		/*
		 * more to add
		 */

		return true;
	}

	@Override
	public void onDoubleEventReceived(DoubleEvent ev, String containerId, String client) throws TimeoutException {

		logger.info("Event from ContainerID: " + containerId + " Value: " + ev.getValue());

		if (containerId != null) {

			DeviceContainer device = getDeviceContainer(containerId);

			if (device != null) {
				logger.info("received Event from: " + device.getHrName() + " (" + ev.getValue() + " "
						+ device.getDeviceType().getType() + ")");

				// set value to device
				device.setValue(ev.getValue());

				// get all relevant parents which should update the value -
				// according to their depth
				// Not sure if reverse order of unsorted array reflects the tree
				// ordering ! hence we go for unsortedParents
				// TreeMap<Integer, Set<String>> listParents = new
				// TreeMap<Integer, Set<String>>();
				// Collections.reverseOrder());
				// this.getSortedParentContainers(device.getContainerId(),
				// listParents);

				TreeMap<Integer, List<String>> listParentsUnsorted = new TreeMap<Integer, List<String>>();

				this.getUnSortedParentContainers(device.getContainerId(), listParentsUnsorted);

				// for each depth starting with the deepest
				
				Long currenttime = new Date().getTime();
				
				for (Entry<Integer, List<String>> entry : listParentsUnsorted.entrySet()) {

					// Not sure if reverse order of unsorted array reflects the
					// tree ordering ! hence we go for unsortedParents
					// List<String> parentContainerIDList = new
					// ArrayList<String>(entry.getValue());
					// Collections.reverse(parentContainerIDList);

					for (String parent : entry.getValue()) {

						Container parentContainer = this.getContainer(parent);

						logger.debug("update statistics for Parent " + parentContainer.getHrName() + " " + parent + "\n"
								+ "update statistics for Parent " + ev.getValue() + " " + device.getDeviceType());

						parentContainer.onUpdateStatistics(device.getDeviceType(),currenttime ,this);


					}
					for (String id : entry.getValue()) {
						logger.info("Container " + cons.get(id).getHrName() + " Value "
								+ cons.get(id).getSum(device.getDeviceType()));

					}
				}

			} else {
				logger.debug("Device not found (ContainerID:" + containerId);
			}
		} else {
			logger.debug("ContainerID was null: " + containerId);
		}

	}

	@Override
	public void onComplexDoubleEventReceived(HashMap<String, DoubleEvent> events, String client)
			throws TimeoutException {
		logger.info("Received ComplexEvent from " + client);
		for (Map.Entry<String, DoubleEvent> entry : events.entrySet()) {
			logger.info("key: " + entry.getKey() + " value: " + entry.getValue());
		}
		for (Map.Entry<String, DoubleEvent> entry : events.entrySet()) {
			String containerId = entry.getKey();
			DoubleEvent ev = entry.getValue();
			logger.info("Received DoubleEvent in a Complex event: containerId: " + containerId + " value: "
					+ entry.getValue().getValue());
			onDoubleEventReceived(ev, containerId, client);
		}
	}

	private void getSortedParentContainers(String containerId, TreeMap<Integer, Set<String>> sortedParents) {

		ArrayList<String> parents = this.getParentContainer(containerId);
		for (String parent : parents) {
			if (this.getContainer(parent) != null) {
				if (!sortedParents.containsKey(this.getContainer(parent).getDepth(this))) {
					sortedParents.put(this.getContainer(parent).getDepth(this), new TreeSet<String>());
				}

				sortedParents.get(this.getContainer(parent).getDepth(this)).add(parent);
			}
			this.getSortedParentContainers(parent, sortedParents);
		}
	}

	private void getUnSortedParentContainers(String containerId, TreeMap<Integer, List<String>> sortedParents) {

		ArrayList<String> parents = this.getParentContainer(containerId);
		for (String parent : parents) {
			if (this.getContainer(parent) != null) {
				if (!sortedParents.containsKey(this.getContainer(parent).getDepth(this))) {
					sortedParents.put(this.getContainer(parent).getDepth(this), new ArrayList<String>());
				}

				sortedParents.get(this.getContainer(parent).getDepth(this)).add(parent);
			}
			this.getUnSortedParentContainers(parent, sortedParents);
		}
	}

	@Override
	public void onDeviceEventReceived(DeviceEvent devEvent, String client) {

		DeviceContainer ev = devEvent.getValue();

		logger.info("Device Event: " + ev.getContainerId() + " hr: " + ev.getHrName() + " devId " + ev.getDeviceId()
				+ " " + ev.getDevId() + " " + ev.getDeviceId().toString());

		addDevContainer(ev);

		// TODO: check for all raspberry types

		// add an edge for the new mobile Device automatically (Parent=:
		// getContainer(wrapperId).getParent() CHild:=
		// ev.getDeviceContainer.getDeviceID()
		if (ev.getDeviceType() == null) {
			logger.debug("invalid device type for ContainerID: " + ev.getContainerId());
			return;
		}

		if (ev.getDeviceId().getWrapperId().startsWith("rasp.")) {
			String raspberryParentContainerId = this.getRealParentContainer(ev.getContainerId());
			if (this.removeRealContainerEdge(ev.getContainerId())) {
				logger.debug("Removed old real Edge from Raspberry child (MobileDevice): " + ev.getContainerId());
			}
			this.addContainerEdge(raspberryParentContainerId, ev.getContainerId(), 0);
			logger.info(
					"Automatically added/updated Edge Parent "); /*
																	 * +
																	 * this.cons
																	 * .get(
																	 * raspberryParentContainerId
																	 * )
																	 * .getHrName
																	 * () +
																	 * " and Child "
																	 * + ev.
																	 * getContainerId
																	 * ());
																	 */
		}

		// DeviceId devID = ev.getDeviceId();
		String containerId = ev.getContainerId();

		// ensure there is a root
		String root = getRoot();

		// if (!edges.containsKey(root)) {
		// this.edges.put(root, new ArrayList<SingleContainerEdge>());
		// }

		// connect non-tree childs with the root
		if (!childIsInTree(containerId)) { // devID
			this.addRealContainerEdge(root, containerId); // devID.toContainterId());
		}

		informListeners();

	}

	private void informListeners() {
		/*
		 * Inform all listeners about the current mappings for HRName and
		 * DeviceID
		 */

		// prepare the mappings
		for (Container container : cons.values()) {
			if (container instanceof DeviceContainer) {
				DeviceContainer deviceContainer = (DeviceContainer) container;
				containerMappingDeviceId.put(deviceContainer.getDeviceId(), deviceContainer.getContainerId());
			}

			containerMappingHRName.put(container.getHrName(), container.getContainerId());

		}

		for (final String queue : listeners.values()) {
			Runnable task = new Runnable() {

				@Override
				public void run() {
					DefaultProxy<IContainerManagerListener> listenerProxy = new DefaultProxy<IContainerManagerListener>(
							IContainerManagerListener.class, queue, TIMEOUTLONG);
					try {
						IContainerManagerListener proxy = listenerProxy.init();
						proxy.currentlyRegisteredContainers(containerMappingDeviceId, containerMappingHRName);
					} catch (IOException e) {
						logger.info("No conection to " + queue + ".");

					} catch (TimeoutException e) {
						logger.info("Timeout for " + queue + ".");
					}
					try {
						listenerProxy.destroy();

					} catch (IOException e) {
						logger.info("Unable to close con. for queue:" + queue);
					}
					try {
						this.finalize();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}

			};
			executors.submit(task);

		}
	}

	public String getRoot() {
		String root = "";
		for (Entry<String, Container> entry : this.cons.entrySet()) {
			ContainerFunction function = entry.getValue().getContainerFunction();
			if (function.equals(ContainerFunction.ROOT)) {
				logger.info("Found root ContainerID: " + entry.getKey());
				return entry.getKey();

			}

		}
		return root;
	}

	public boolean childIsInTree(String containerId) { // DeviceId devID) {
		// boolean existingEdge = false;
		for (Entry<String, ArrayList<SingleContainerEdge>> parent : edges.entrySet()) {
			// look for existing edge
			for (SingleContainerEdge edge : parent.getValue()) {
				if (edge.getChild().equals(containerId)) { // devID.toContainterId()))
															// {
					// existingEdge = true;
					return true;
				}
			}
		}
		return false; // existingEdge;
	}

	public ArrayList<SingleContainerEdge> getChilds(DeviceId devId) {
		return this.edges.get(devId);
	}

	private void checkDevice(DeviceContainer ev) {
		try {
			/**
			 * Check if Container exists
			 */

			// TODO: need observation, what does already exist ? ContainerID,
			// DeviceID
			String sql = "SELECT *  FROM " + ContainerManagerImpl.DB_NAME_CONTAINERS
			// TODO: Check due to new ContainerID
					+ " WHERE DevID = '" + ev.getDevId() + "' AND WrapperID = '" + ev.getWrapperId() + "';";
			// ContainerID = '" + ev.getContainerId() + "' AND we do not need
			// this since only devices are checked

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet != null && resultSet.size() == 1) {
				/**
				 * exactly one container
				 */
				ev.setHrName(resultSet.get(0).get("containerhrname").toString());
				ev.setVirtualContainer(Double.parseDouble(resultSet.get(0).get("virtualcontainer").toString()) == 1);
				logger.debug("DeviceContainer found in DB HRName and virtual-tag replaced (" + ev.getHrName()
						+ ", is virtual: " + ev.isVirtualContainer() + ")");
			}
		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}
	}

	@Override
	public EdgeType getEdgeType(String parent, String child) {
		return edgeHandler.getEdgeType(parent, child);
	}

	@Override
	public List<Entry<Container, EdgeType>> getChildrenWithEdgeTypes(String id) {
		return edgeHandler.getChildrenWithEdgeTypes(id);
	}

	/**
	 * will pick only dev containers
	 */
	@Override
	public DeviceContainer getDeviceContainer(String containerId) {
		if (containerId != null) {

			if (cons.keySet().contains(containerId)) {
				if (cons.get(containerId) instanceof DeviceContainer) {
					return (DeviceContainer) cons.get(containerId);
				}
			}

		}
		logger.debug("no container found for ContainerID: " + containerId);
		return null;
	}

	/**
	 * will return the ContainerIDString for an DevID
	 */
	@Override
	public String getContainerId(DeviceId devId) {
		logger.debug("try to get containerId by deviceID " + devId.toDeviceID());
		for (Container con : cons.values()) {
			if (con instanceof DeviceContainer) {
				if (((DeviceContainer) con).getDeviceId().equals(devId)) {
					return ((DeviceContainer) con).getContainerId();
				}

			}
		}

		// for (Container con : cons.values()) {
		// if (con instanceof DeviceContainer) {
		// if (((DeviceContainer) con).getDeviceId().equals(dev)) {
		// return (DeviceContainer) con;
		// }
		//
		// }
		// }
		logger.debug("no container found for " + devId.toDeviceID());
		return null;
	}

	/**
	 * will return the Container for an String ContainerID
	 */
	@Override
	public Container getContainer(String containerId) {
		logger.debug("try to get container by containerID " + containerId);
		if (this.cons.containsKey(containerId)) {
			return this.cons.get(containerId);
		}
		return null;
	}

	@Override
	public void sendCommand(DoubleCommand command, String containerId) {
		DefaultProxy<IActuatorMaster> proxyMasterCommand = new DefaultProxy<IActuatorMaster>(IActuatorMaster.class,
				ActuatorMasterQueueNames.getActuatorMasterInterfaceQueue(), TIMEOUTLONG);

		Container con = this.cons.get(containerId);
		if (con instanceof DeviceContainer) {
			try {
				IActuatorMaster proxyCommand = proxyMasterCommand.init();
				if (proxyCommand != null) {
					((DeviceContainer) con).setValue(command.getValue());

					proxyCommand.sendDoubleCommand(command, containerId);
					logger.info("Command was sent to Master: " + command + " for Container "
							+ cons.get(containerId).getHrName());
				}
			} catch (TimeoutException e) {
				logger.debug("sending command failed", e.fillInStackTrace());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				proxyMasterCommand.destroy();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendCommandToContainer(DoubleCommand command, String containerId, SIDeviceType type) {
		
		// first get all children
		Container con;
		for (Entry<Container, EdgeType> entry : this.getChildrenWithEdgeTypes(containerId)) {
			con = entry.getKey();
			if (con instanceof DeviceContainer) {
				DeviceContainer deviceContainer = (DeviceContainer) con;
				if (deviceContainer.getDeviceType().equals(type) && deviceContainer.getCommandMaxRange() > 0) {
					deviceContainer.setValue(command.getValue());
					sendCommand(command, deviceContainer.getContainerId());
				}
			}
			else if (con instanceof Container) {
				sendCommandToContainer(command, con.getContainerId(), type);
			}

		}
	}

	public void sendCommand(DoubleCommand command, String containerId, SIDeviceType type, long userId) {
		// TODO Connect to the Usermanager and verify userId has access to
		// containerId
	}

	@Override
	public ArrayList<String> getParentContainer(String containerId) {

		ArrayList<String> parents = new ArrayList<String>();

		SingleContainerEdge edge = new SingleContainerEdge(containerId, null);

		for (Entry<String, ArrayList<SingleContainerEdge>> entry : edges.entrySet()) {
			// if we see a child == the one we look for -> we found a parent
			if (entry.getValue().contains(edge)) {
				if (!parents.contains(entry.getKey())) {
					parents.add(entry.getKey());
				}
			}

		}
		logger.debug("found " + parents.size() + " parents for containerID: " + containerId);
		return parents;
	}

	@Override
	public String getRealParentContainer(String containerId) {

		SingleContainerEdge edge = new SingleContainerEdge(containerId, EdgeType.REAL);
		for (Entry<String, ArrayList<SingleContainerEdge>> entry : this.edges.entrySet()) {

			// if we see a child == the one we look for -> we found a parent
			if (entry.getValue().contains(edge)) {
				for (SingleContainerEdge childs : entry.getValue()) {
					if (childs.getType().equals(edge.getType())) {
						if (containerId.equals(entry.getKey())) {
							return null;
						}
						return entry.getKey();
					}
				}
			}

		}
		return null;
	}

	@Override
	public Pair<List<Container>, List<ContainerEdge>> getRoomMap(String containerId) {
		List<Container> roomcontainers = new ArrayList<Container>();
		List<ContainerEdge> roomedges = new ArrayList<ContainerEdge>();

		// if the requested container is null return an empty Pair
		Container requestedContainer = this.getContainer(containerId);
		if (requestedContainer != null) {
			roomcontainers.add(requestedContainer);
		} else {

			return new Pair<List<Container>, List<ContainerEdge>>(roomcontainers, roomedges);
		}

		// if edges are empty there is no tree information (yet)
		if (this.edges.isEmpty()) {
			return new Pair<List<Container>, List<ContainerEdge>>(roomcontainers, roomedges);
		}

		if (this.edges.get(containerId) != null) {
			for (SingleContainerEdge singleContainerEdge : this.edges.get(containerId)) {
				if (singleContainerEdge.getType().equals(EdgeType.REAL)) {
					roomedges.add(new ContainerEdge(containerId, singleContainerEdge.getChild(),
							singleContainerEdge.getType()));

					Pair<List<Container>, List<ContainerEdge>> tmpPair = getRoomMap(singleContainerEdge.getChild());
					roomcontainers.addAll(tmpPair.getKey());
					roomedges.addAll(tmpPair.getValue());
				}
			}
		}

		return new Pair<List<Container>, List<ContainerEdge>>(roomcontainers, roomedges);
	}

	public ArrayList<String> getRegisteredDevices() {
		ArrayList<String> containerIDs = new ArrayList<String>();
		for (Container con : this.cons.values()) {
			if (con instanceof DeviceContainer) {
				containerIDs.add(((DeviceContainer) con).getContainerId());
			}
		}
		return containerIDs;

	}

	@Override
	public double getCurrentValueByType(String ContainerId, SIDeviceType type) {
		/*
		 * if (reasonableforBool.contains(type)) { Container con =
		 * this.cons.get(ContainerId); if (con instanceof DeviceContainer) {
		 * DeviceContainer deviceContainer = (DeviceContainer) con; return
		 * deviceContainer.getValue(); } return -1; } else
		 */
		if (reasonableforSum.contains(type)) {
			// TODO it is the other way round
			return this.getSumByType(ContainerId, type);
		}
		else if (reasonableforBool.contains(type)) {
			return this.getMaxByType(ContainerId, type);
		}
		
		
		else {
			return this.getMeanByType(ContainerId, type);
		}
	}

	@Override
	public double getSumByType(String ContainerId, SIDeviceType type) {
		double result = -1.0;
		if (this.cons.containsKey(ContainerId)) {
			if (typeIsReasonable("SUM", type)) {

				result = this.cons.get(ContainerId).getSum(type);
			} else {
				// should we return the mean or -1
				result = -1;
				// result = this.cons.get(ContainerId).getMean(type);
			}
		}
		return result;
	}

	@Override
	public double getMeanByType(String ContainerId, SIDeviceType type) {
		double result = -1.0;
		if (this.cons.containsKey(ContainerId)) {
			if (typeIsReasonable("MEAN", type)) {
				result = this.cons.get(ContainerId).getMean(type);
			} else {
				// should we return the sum or -1 ?
				result = -1;
				// result = this.cons.get(ContainerId).getSum(type);
			}
		}
		return result;
	}

	@Override
	public double getMinByType(String ContainerId, SIDeviceType type) {
		if (this.cons.containsKey(ContainerId)) {
			return this.cons.get(ContainerId).getMin(type);
		} else {
			return -1.0;
		}
	}

	@Override
	public double getMaxByType(String ContainerId, SIDeviceType type) {
		if (this.cons.containsKey(ContainerId)) {
			return this.cons.get(ContainerId).getMax(type);
		} else {
			return -1.0;
		}
	}

	@Override
	public ArrayList<Double> getStatisticsByType(String ContainerId, SIDeviceType type) {
		ArrayList<Double> results = new ArrayList<Double>();

		results.add(getSumByType(ContainerId, type));
		results.add(getMeanByType(ContainerId, type));
		results.add(getMinByType(ContainerId, type));
		results.add(getMaxByType(ContainerId, type));

		return results;
	}

	

//	@Override
//	public HashMap<SIDeviceType, Pair<Double, Long>> getDetailedValues(String ContainerId) throws TimeoutException {
		// TODO Auto-generated method stub
//		HashMap<SIDeviceType, Pair<Double, Long>>  detailedValues = new HashMap<SIDeviceType, Pair<Double, Long>>();
		
//		Map<SIDeviceType, SummaryStatistics> summaryStatistics = this.cons.get(ContainerId).getSummaryStatisticsMap();
//		
//		for (SIDeviceType type : summaryStatistics.keySet()) {
//			Double value = this.getCurrentValueByType(ContainerId, type);
//		
//			Pair<Double, Long> entry =  new Pair<Double, Long>(value, this.cons.get(ContainerId).getTimeStamp(type));
//		
//			detailedValues.put(type, entry);
//					
//		}
		
		
//		return detailedValues;
//	}

	
	
	private boolean typeIsReasonable(String method, SIDeviceType type) {
		/*
		 * Min and Max are always reasonable
		 */

		if (method.equals("SUM")) {
			if (reasonableforSum.contains(type)) {
				return true;
			}
			return false;
		} else if (method.equals("MEAN")) {
			/*
			 * Due to simplicity invert sum
			 */
			if (!reasonableforSum.contains(type)) {
				return true;
			}
			return false;
		} else {
			return false;
		}

	}

	// TODO refactor, possibly not neccessary ! see smartphonegateway code
	// for what purpose the ContainerId is required
	/*
	 * @Override public String serverStarted(String macAdress) { return
	 * getRealParentContainer(macAdress); //return
	 * getContainer(getRealParentContainer(macAdress)).getContainerId(); }
	 */
	@Override
	public String getRoomName(String macAdress) {
		String parent = getRealParentContainer(macAdress);
		if (parent != null) {
			Container con = getContainer(parent);
			if (con != null) {
				return con.getHrName();
			}
		}
		logger.debug("No container found for containerId " + macAdress);
		return null;
	}

	/**
	 * returns HR device name as value (useful for user menus)
	 */
	@Override
	public HashMap<Integer, String> getSupportedSensorDeviceCodes() {
		HashMap<Integer, String> supported = new HashMap<Integer, String>();
		try {
			/**
			 * Check if Container exists
			 */
			String sql = "SELECT DeviceCode, SMGDeviceType FROM " + ContainerManagerImpl.DB_NAME_DEVICES + " WHERE 1;";

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet != null) {
				for (Map<String, Object> deviceCodeEntry : resultSet) {
					try {
						supported.put((Integer.parseInt(deviceCodeEntry.get("devicecode").toString())),
								deviceCodeEntry.get("smgdevicetype").toString());
					} catch (NumberFormatException e) {
						logger.error(
								"Could not parse Google Device Code: " + deviceCodeEntry.get("devicecode").toString()
										+ "using SMG type: " + deviceCodeEntry.get("smgdevicetype").toString());
					}
				}

			}
		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}
		return supported;
	}

	public String addContainer(Container dev) {

		// TODO return ContainerId

		if (saveContainer(dev)) {
			return dev.getContainerId();
		}
		return "-1";
	}

	public String addContainer(ContainerType containertype) {

		// TODO: need to be not in DB
		String ContainerID = null;
		try {
			ContainerID = "" + this.database.getContainerID("-1", "-1");

			Container container = new Container(ContainerID, "none", containertype, ContainerFunction.NONE, false);

			if (saveContainer(container)) {
				return container.getContainerId();
			}
			return "error";
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return "error";
		}
	}

	private boolean saveContainer(Container dev) {
		logger.info("saving " + dev.getContainerId());
		if (!cons.containsKey(dev.getContainerId())) {
			cons.put(dev.getContainerId(), dev);
			informListeners();
			logger.info("Container " + dev.getContainerId() + " added to internal container list " + dev.getClass());
		}
		return SimpleSerializer.saveToDB(dev.serialize(), database, ContainerManagerImpl.DB_NAME_CONTAINERS);
	}

	private boolean saveSpec(DeviceContainer dev) {
		return SimpleSerializer.saveToDB(dev.serializeDev(), database, ContainerManagerImpl.DB_NAME_SPECS);
	}

	@Override
	public String addDevContainer(DeviceContainer ev) {
		checkDevice(ev);

		// serialize first
		if (saveContainer(ev)) {
			// saveSpec(ev);
			return ev.getContainerId();
		}
		return "error";
	}

	/*
	 * Update anything but the ContainerId
	 * 
	 * @see org.fortiss.smg.containermanager.api.ContainerManagerInterface#
	 * updateContainer(java.lang.String, java.lang.String,
	 * org.fortiss.smg.containermanager.api.devices.ContainerType,
	 * org.fortiss.smg.containermanager.api.devices.ContainerFunction, int)
	 */
	@Override
	public boolean updateContainer(Container con) {
		return saveContainer(con);
	}

	@Override
	public boolean updateContainer(String containerIDold, Container newContainer) {
		if (containerIDold.equals(newContainer.getContainerId())) {
			return updateContainer(newContainer);
		} else {
			cons.remove(containerIDold);
			return saveContainer(newContainer);
		}
	}

	@Override
	public boolean removeContainer(String containerID) {
		/*
		 * Update the Edges for all children and set them to this.parent TODO:
		 * All devices to parent node delete "bulding" structure
		 */
		List<Entry<Container, EdgeType>> children = new ArrayList<Entry<Container, EdgeType>>();
		children = this.getChildrenWithEdgeTypes(containerID);
		if (getRealParentContainer(containerID) != null) {
			for (Entry<Container, EdgeType> entry : children) {
				if (entry.getValue().equals(EdgeType.REAL)) {

					logger.debug("updating (real) edge from children to parent of " + containerID);
					this.updateRealContainerEdgeFixedChild(getRealParentContainer(containerID),
							entry.getKey().getContainerId());
				} else {
					logger.debug("updating (virtual) edge from children to parent of " + containerID);
					this.updateVirtualContainerEdgeFixedChild(containerID, getRealParentContainer(containerID),
							entry.getKey().getContainerId());
				}
			}
		}

		try {
			/**
			 * Check if Container exists
			 */
			String sql = "SELECT *  FROM " + ContainerManagerImpl.DB_NAME_CONTAINERS + " WHERE ContainerID = '"
					+ containerID + "';";

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet == null || resultSet.size() < 1) {
				/**
				 * no Container to delete return 0
				 */
				return false;
			}
			sql = "DELETE FROM " + ContainerManagerImpl.DB_NAME_CONTAINERS + " WHERE `ContainerID` =  '" + containerID
					+ "';";
			if (this.database.executeQuery(sql)) {

				this.cons.remove(containerID);

				informListeners();

				return true;
			} else {
				return false;
			}
		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}

		return false;
	}

	@Override
	public boolean addContainerEdge(String parentID, String childID, int virtual) {
		return edgeHandler.addContainerEdge(parentID, childID, virtual);
	}

	@Override
	public boolean addRealContainerEdge(String parentID, String childID) {
		return addContainerEdge(parentID, childID, 0);
	}

	@Override
	public boolean addVirtualContainerEdge(String parentID, String childID) {
		return addContainerEdge(parentID, childID, 1);
	}

	@Override
	public boolean updateRealContainerEdgeFixedChild(String newParentID, String childID) {
		return edgeHandler.updateRealContainerEdgeFixedChild(newParentID, childID);
	}

	@Override
	public boolean updateRealContainerEdgeFixedParent(String parentID, String oldChildID, String newChildID) {
		return edgeHandler.updateRealContainerEdgeFixedParent(parentID, oldChildID, newChildID);
	}

	@Override
	public boolean updateVirtualContainerEdgeFixedChild(String oldParentID, String newParentID, String childID) {
		return edgeHandler.updateVirtualContainerEdgeFixedChild(oldParentID, newParentID, childID);
	}

	@Override
	public boolean updateVirtualContainerEdgeFixedParent(String parentID, String newChildID, String oldChildID) {
		return edgeHandler.updateVirtualContainerEdgeFixedParent(parentID, newChildID, oldChildID);
	}

	@Override
	public boolean updateContainerEdgeType(String parentID, String childID, int virtual) {
		return edgeHandler.updateContainerEdgeType(parentID, childID, virtual);
	}

	@Override
	public boolean removeRealContainerEdge(String childID) {
		return edgeHandler.removeRealContainerEdge(childID);
	}

	@Override
	public boolean removeContainerEdge(String parentID, String childID) {
		return edgeHandler.removeContainerEdge(parentID, childID);
	}

	@Override
	public HashMap<String, Object> getDeviceSpecData(int deviceID) {

		// TODO: Fix null return deviceSpec was set to null before;
		HashMap<String, Object> deviceSpec = new HashMap<String, Object>();
		try {
			String sql = "SELECT *  FROM " + ContainerManagerImpl.DB_NAME_DEVICES + " WHERE DeviceCode = " + deviceID
					+ ";";

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet == null || resultSet.size() != 1) {
				ContainerManagerImpl.logger.warn("No or More than one DeviceSpecData found");
			}
			/*
			 * go through results and create containers
			 */
			else {
				// deviceSpec = new HashMap<String, Object>();
				for (Map<String, Object> result : resultSet) {
					deviceSpec.put("devicecode", result.get("devicecode")); // int
					deviceSpec.put("devicetype", result.get("devicetype")); // String
																			// googletype
					deviceSpec.put("smgdevicetype", result.get("smgdevicetype")); // String
																					// smg
																					// type
					deviceSpec.put("alloweduserprofile", result.get("alloweduserprofile")); // "Boolean"
																							// as
																							// Double
					deviceSpec.put("minupdaterate", result.get("minupdaterate")); // Double
					deviceSpec.put("maxupdaterate", result.get("maxupdaterate")); // Double
					deviceSpec.put("acceptscommands", result.get("acceptscommands")); // "Boolean"
																						// as
																						// Double
					deviceSpec.put("hasvalue", result.get("hasvalue")); // "Boolean"
																		// as
																		// Double
					deviceSpec.put("rangemin", result.get("rangemin")); // Double
					deviceSpec.put("rangemax", result.get("rangemax")); // Double
					deviceSpec.put("rangestep", result.get("rangestep")); // Double
					deviceSpec.put("commandminrange", result.get("commandminrange")); // Double
					deviceSpec.put("commandmaxrange", result.get("commandmaxrange")); // Double
					deviceSpec.put("commandrangestep", result.get("commandrangestep")); // Double
					deviceSpec.put("commandrangesteptype", result.get("commandrangesteptype")); // String
					deviceSpec.put("humanreadablename", result.get("humanreadablename")); // String
					deviceSpec.put("description", result.get("description")); // String
				}
			}
		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}
		return deviceSpec;
	}

	/*
	 * information is only stored in memory
	 */
	/*
	 * @Override public DeviceContainer getDeviceSpec(DeviceId id) { return
	 * getDeviceContainer(id); }
	 */

	@Override
	public DeviceContainer getDeviceSpec(DeviceId id, int deviceCode) {
		/*
		 * select (if available) the devicecontainer (HR Name) from DB
		 */
		String hrName = null;

		try {
			String sql = "SELECT ContainerHRName FROM " + ContainerManagerImpl.DB_NAME_CONTAINERS
			// TODO: Check ContainerID due to change
					+ " WHERE DeviceID = " + id.toDeviceID()// toContainterId()
					+ " AND ContainerType = DEVICE;";

			List<Map<String, Object>> resultSet;

			resultSet = this.database.getSQLResults(sql);

			if (resultSet != null) {
				if (resultSet.size() == 1) {
					hrName = resultSet.get(0).toString();
				}
			}
			/*
			 * go through results and create containers
			 */

		} catch (TimeoutException e) {
			ContainerManagerImpl.logger.warn("SQL Statement error", e);
		}

		/*
		 * get the Rest from DeviceSpec
		 */

		HashMap<String, Object> deviceSpec = getDeviceSpecData(deviceCode);

		if (hrName == null && deviceSpec.size() > 0) {
			hrName = deviceSpec.get("humanreadablename").toString();
		}

		DeviceContainer dev = new DeviceContainer(id, id.getWrapperId());
		if (deviceSpec.size() > 0) {
			dev.setCommandRange((Double.parseDouble(deviceSpec.get("rangemin").toString())),
					(Double.parseDouble(deviceSpec.get("rangemax").toString())),
					(Double.parseDouble(deviceSpec.get("rangestep").toString())));
			dev.setDeviceType(SIDeviceType.fromString(deviceSpec.get("smgdevicetype").toString()));
			dev.setCommandRange((Double.parseDouble(deviceSpec.get("commandminrange").toString())),
					(Double.parseDouble(deviceSpec.get("commandmaxrange").toString())),
					(Double.parseDouble(deviceSpec.get("commandrangestep").toString())));

			dev.setCommandRangeStepType(CommandRangeStepType.fromString("LINEAR"));

			dev.setHrName(hrName);

			// boolean!!
			dev.setHasValue((Double.parseDouble(deviceSpec.get("hasvalue").toString()) != 0));
			dev.setAcceptsCommands((Double.parseDouble(deviceSpec.get("acceptscommands").toString()) != 0));
		}
		return dev;

	}

	@Override
	public HashMap<String, String> getActuatorsInContainer(String containerId) {
		HashMap<String, String> actuators = new HashMap<String, String>();

		List<Entry<Container, EdgeType>> children = getChildrenWithEdgeTypes(containerId);

		for (Entry<Container, EdgeType> child : children) {
			Container con = child.getKey();

			if (con instanceof DeviceContainer) {
				DeviceContainer deviceContainer = (DeviceContainer) con;
				/*
				 * Only those DeviceContainers are SensorActuators which do have
				 * a state value e.g. light switch (no sensor but actuator with
				 * state)
				 */
				if (deviceContainer.getAcceptsCommands() && deviceContainer.isHasValue()) {
					actuators.put(deviceContainer.getContainerId(), deviceContainer.getDeviceType().toString());
				}
			} else {
				if (con != null) {
					actuators.putAll(getActuatorsInContainer(con.getContainerId()));
				}

			}
		}

		return actuators;
	}

	@Override
	public List<String> getActuatorsInContainerByType(String containerId) {

		HashSet<String> actuatortypes = new HashSet<String>();
		HashMap<String, String> actuators = getActuatorsInContainer(containerId);

		for (Entry<String, String> entry : actuators.entrySet()) {
			actuatortypes.add(entry.getValue());
		}

		return new ArrayList<String>(actuatortypes);
	}

	@Override
	public HashMap<String, String> getSensorsInContainer(String containerId) {
		HashMap<String, String> sensors = new HashMap<String, String>();

		List<Entry<Container, EdgeType>> children = getChildrenWithEdgeTypes(containerId);

		for (Entry<Container, EdgeType> child : children) {
			Container con = child.getKey();

			if (con instanceof DeviceContainer) {
				DeviceContainer deviceContainer = (DeviceContainer) con;
				if (!deviceContainer.getAcceptsCommands()) {
					// && !deviceContainer.isHasValue()) {
					sensors.put(deviceContainer.getContainerId(), deviceContainer.getDeviceType().toString());
				}
			} else {
				if (con != null) {
					sensors.putAll(getSensorsInContainer(con.getContainerId()));
				}

			}
		}

		return sensors;
	}

	@Override
	public List<String> getSensorsInContainerByType(String containerId) {

		HashSet<String> sensortypes = new HashSet<String>();
		HashMap<String, String> sensors = getSensorsInContainer(containerId);

		for (Entry<String, String> entry : sensors.entrySet()) {
			sensortypes.add(entry.getValue());
		}

		return new ArrayList<String>(sensortypes);
	}

	@Override
	public HashMap<String, String> getActuatorSensorsInContainer(String containerId) {
		HashMap<String, String> sensorsactors = new HashMap<String, String>();

		List<Entry<Container, EdgeType>> children = getChildrenWithEdgeTypes(containerId);

		for (Entry<Container, EdgeType> child : children) {
			Container con = child.getKey();

			if (con instanceof DeviceContainer) {
				DeviceContainer deviceContainer = (DeviceContainer) con;
				/*
				 * Only those DeviceContainers are SensorActuators which do not
				 * have a state value
				 */
				if (deviceContainer.getAcceptsCommands() && !deviceContainer.isHasValue()) {
					sensorsactors.put(deviceContainer.getContainerId(), deviceContainer.getDeviceType().toString());
				}
			} else {
				if (con != null) {
					sensorsactors.putAll(getActuatorSensorsInContainer(con.getContainerId()));
				}

			}
		}

		return sensorsactors;
	}

	@Override
	public List<String> getActuatorSensorsInContainerByType(String containerId) {

		HashSet<String> actuatorsensortypes = new HashSet<String>();
		HashMap<String, String> actuatorsensors = getActuatorSensorsInContainer(containerId);

		for (Entry<String, String> entry : actuatorsensors.entrySet()) {
			actuatorsensortypes.add(entry.getValue());
		}

		return new ArrayList<String>(actuatorsensortypes);
	}

	@Override
	public List<String> getReasonableTypeForSum() {
		List<String> reasonable = new ArrayList<String>();
		for (SIDeviceType type : reasonableforSum) {
			reasonable.add(type.toString());

		}
		return reasonable;
	}

}
