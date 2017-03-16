package org.fortiss.smg.actuatorclient.dummy.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.actuatormaster.api.events.DoubleEvent;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.containermanager.api.devices.SIDeviceType;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;
import org.slf4j.LoggerFactory;

public class DummyLooper implements Runnable {

	private static org.slf4j.Logger logger = LoggerFactory
			.getLogger(DummyLooper.class);

	/*
	 * global consumption 10k 50k with light
	 */
	private double consumtion_global_wo_light = 10000.0;
	private double consumtion_global_w_light = 50000.0;
	private double chargerate = 5000.0;

	private ActuatorClientImpl impl;
	private DeviceId origin;
	private DoubleEvent ev;

	private double consumption = 0.0;
	private double generation = 0.0;
	private double batterypercentage = 1.0; // 1 = 100%
	private double lightson = 0.0;

	DummyLooper(ActuatorClientImpl impl) {
		this.impl = impl;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		readDummyValues();
	}

	private void readDummyValues() {
		// create dummy values for dimis meter
		//for (int i = 1; i <= 46; i++) {
			// TODO randomize valutes
		//	DataIdentifier type = DataIdentifier.fromKey(i);
			Random r = new Random();
			double min = 0;
			double max = 100;
			double randomValue = min + (max - min) * r.nextDouble();
			DoubleEvent ev = new DoubleEvent(randomValue);
			
//			//DeviceId 
			String origin = impl.devices.get(0).getContainerId(); //new DeviceId("dummy" + "." + type, impl.getWrapperTag());
//			try {
//				logger.debug("event: " + ev + ", origin Container id: " + origin );
//				impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			} catch (TimeoutException e) {
//				logger.error("timeout sending to master", e);
//			}
//
//		
//
		 try {
//		
//			/*
//			 * read values from devices (enocean)
//			 */
//			//			DefaultProxy<ContainerManagerInterface> clientInfo = new DefaultProxy<ContainerManagerInterface>(
//			//					ContainerManagerInterface.class,
//			//					ContainerManagerQueueNames
//			//							.getContainerManagerInterfaceQueryQueue(), 5000);
//			//			ContainerManagerInterface containerManagerClient = null;
//
//			try {
//				//containerManagerClient = clientInfo.init();
//				lightson = this.impl.containerManagerClient.getMeanByType(
//						"enoceanUSB.wrapper.PowerPlug", SIDeviceType.Powerplug);
//
//				if (lightson == Double.NaN) {
//					lightson = 0.0;
//
//				}
//				logger.debug("Lightstatus is " + lightson);
//
//				//			} catch (IOException e) {
//				//				e.printStackTrace();
//			} catch (TimeoutException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			//			try {
//			//				clientInfo.destroy();
//			//			} catch (IOException e) {
//			//				e.printStackTrace();
//			//			}
//
//			generation = impl.generation.getStatus();
//			batterypercentage = impl.getBatteryPercentage();
//
			/*
			 * Consumption (for 5 instances) in connected mode: battery full if
			 * light on 50k if light off 10k
			 * 
			 * battery not full if light on 100k if light off 60k
			 * 
			 * minus PV generation 100% = 80k if in grid and battery < 100 %
			 * 80k-100k (light on/off)
			 */

//			if (impl.gridconnected.getStatus() == 1.0
//					&& impl.energyfrombatteryrequest.getStatus() == 0.0) {
//				if (batterypercentage == 1.0) {
//					if (lightson == 0.0) {
//						consumption = consumtion_global_wo_light;
//					} else {
//						consumption = consumtion_global_w_light;
//					}
//				} else {
//
//					if ((impl.getBattery() + chargerate)
//							/ impl.batterycapacitymax > 1.0) {
//						chargerate = impl.batterycapacitymax
//								- impl.getBattery();
//					}
//					if (lightson == 0.0) {
//						consumption = consumtion_global_wo_light + chargerate;
//					} else {
//						consumption = consumtion_global_w_light + chargerate;
//					}
//
//					impl.setBattery(impl.getBattery() + chargerate);
//					batterypercentage = impl.getBattery()
//							/ impl.batterycapacitymax;
//				}
//
//			} else if (impl.energyfrombatteryrequest.getStatus() == 1.0) {
//				if (batterypercentage >= 0.5) {
//					generation = impl.generation.getStatus() + chargerate;
//					impl.setBattery(impl.getBattery() - chargerate);
//					batterypercentage = impl.getBattery()
//							/ impl.batterycapacitymax;
//				}
//				else {
//					impl.energyfrombatteryrequest.setStatus(0.0);
//
//				}
//
//			} else if (impl.gridconnected.getStatus() == 0.0) {
//				/*
//				 * not in grid connected mode
//				 * 
//				 * energy requests are not allowed
//				 */
//
//				impl.energyfrombatteryrequest.setStatus(0.0);
//
//				if (lightson == 0.0) {
//					consumption = consumtion_global_wo_light;
//				} else {
//					consumption = consumtion_global_w_light;
//				}
//
//				if (consumption <= impl.getBattery() ) {
//					/*
//					 * Switch off light
//					 */
//					impl.setBattery(impl.getBattery() - consumption/8.0);
//					batterypercentage = impl.getBattery()
//							/ impl.batterycapacitymax;
//
//				} 
//				else if (consumption > impl.getBattery() * 0.50  && lightson == 1.0) {
//					lightOff();
//					impl.setBattery(impl.getBattery() - consumption/8.0);
//					batterypercentage = impl.getBattery()
//							/ impl.batterycapacitymax;
//				}
//				else {
//					impl.gridconnected.setStatus(1.0);
//				}
//
//			}


//			double tempval = 4 + (int)(Math.random() * ((8 - 4) + 1));
//			//			double tempval = 5;
//			impl.setGridConnected(tempval);
//			origin = impl.getDeviceSpecs().get(5).getContainerId();
//			ev = new DoubleEvent(tempval);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("Outside temprature is "+ tempval +" Celcius");
//
////			tempval = 10 + (int)(Math.random() * ((25 - 10) + 1));
//			tempval = 17;
//			impl.setGridConnected(tempval);
//			origin = impl.getDeviceSpecs().get(8).getContainerId();
//			ev = new DoubleEvent(tempval);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("Inside temprature is"+ tempval+" Celcius!");
//
//			//			impl.setGridConnected(0.0);
//			//			origin = impl.getDeviceSpecs().get(7).getDeviceId();
//			//			ev = new DoubleEvent(0.0);
//			//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			//			logger.info("Window is open!");
//
//			double brightVal = 100 + (int)(Math.random() * ((5000 - 100) + 1));
//			//			double brightVal = 120.0;
//			impl.setGridConnected(brightVal);
//			origin = impl.getDeviceSpecs().get(12).getContainerId();
//			ev = new DoubleEvent(brightVal);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("Inside is dark"+ brightVal);
//
//
//			brightVal = 50 + (int)(Math.random() * ((10000 - 50) + 1));
//			//			brightVal = 1500.0;
//			impl.setGridConnected(brightVal);
//			origin = impl.getDeviceSpecs().get(6).getContainerId();
//			ev = new DoubleEvent(brightVal);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("Sunny day " + brightVal);
//
//			Random generator = new Random(); 
//			int blind = generator.nextInt(3) + 1;
//			int status[] = {0,25,50,75,100};
//			impl.setGridConnected(status[blind]);
//			origin = impl.getDeviceSpecs().get(9).getContainerId();
//			ev = new DoubleEvent(status[blind]);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			//			Random generator = new Random(); 
//			//			impl.setGridConnected(1.0);
//			//			origin = impl.getDeviceSpecs().get(9).getDeviceId();
//			//			ev = new DoubleEvent(1.0);
//			//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("Blinds "+ status[blind] );
//


			//			impl.setGridConnected(1.0);
			//			origin = impl.getDeviceSpecs().get(10).getDeviceId();
			//			ev = new DoubleEvent(1.0);
			//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
			////			logger.info("DummyDevice: run(): getEventHandler - new Event from " + 
			////					origin + " value " + 1.0);
			//			logger.info("Light is on!");

			//			impl.setGridConnected(0.0);
			//			origin = impl.getDeviceSpecs().get(11).getDeviceId();
			//			ev = new DoubleEvent(0.0);
			//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
			//
			//			logger.info("No one is in the room!");




			// send Complex DoubleEvent
			double dummyDouble;
			HashMap<String, DoubleEvent> events = new HashMap<String, DoubleEvent>();
			for (int i = 0; i < 5; i++) {
				origin = impl.getDeviceSpecs().get(i).getContainerId();
//				Random generator = new Random();
//				dummyDouble = generator.nextDouble() * 100;
				DoubleEvent event = new DoubleEvent(1000 + i);
				logger.debug("Complex: containerID: " + origin + " value: " + event.getValue());
				events.put(origin, event);
			}
			for(Map.Entry<String, DoubleEvent> event : events.entrySet()) {
				logger.debug("Key: " + event.getKey() + " value: " + event.getValue());
			}
			impl.getMaster().sendComplexDoubleEvent(events, impl.getClientId());

			impl.setConsumption(consumption);
			origin = impl.getDeviceSpecs().get(0).getContainerId();
			if (impl.gridconnected.getStatus() == 0.0) {
				ev = new DoubleEvent(0.0);
			} else {
				ev = new DoubleEvent(consumption);
			}
			Random generator = new Random(); 
			consumption = generator.nextDouble()*100;
			
			ev = new DoubleEvent(consumption);
			
			
			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
			logger.info("DummyDevice: run(): getEventHandler - new Event from "
					+ origin + " value " + consumption);

			generation = generator.nextDouble()*100;
			
			//impl.setGeneration(generation);
			origin = impl.getDeviceSpecs().get(1).getContainerId();
//			if (impl.gridconnected.getStatus() == 0.0) {
//				ev = new DoubleEvent(0.0);
//			} else {
			ev = new DoubleEvent(generation);
//			}
			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
			logger.info("DummyDevice: run(): getEventHandler - new Event from "
					+ origin + " value " + generation);

			impl.setBatteryPercentage(batterypercentage);
			origin = impl.getDeviceSpecs().get(2).getContainerId();
			ev = new DoubleEvent(batterypercentage * 100.0);
			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
			logger.info("DummyDevice: run(): getEventHandler - new Event from "
					+ origin + " value " + batterypercentage * 100);
			
//			double gridconnected = Math.round(Math.random());
//			impl.setGridConnected(gridconnected);
//			origin = impl.getDeviceSpecs().get(3).getContainerId();
//			ev = new DoubleEvent(gridconnected);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("DummyDevice: run(): getEventHandler - new Event from " + 
//					origin + " value " + gridconnected);


//			generator = new Random(); 
//			int i = generator.nextInt(1) + 1;
//			int statusb[] = {0,1};
//
//			
//			DefaultProxy<ContainerManagerInterface> clientInfo = new DefaultProxy<ContainerManagerInterface>(
//									ContainerManagerInterface.class,
//									ContainerManagerQueueNames
//											.getContainerManagerInterfaceQueryQueue(), 5000);
//						 
//
//					try {
//						ContainerManagerInterface containerManagerClient = clientInfo.init();
//						containerManagerClient.sendCommand(new DoubleCommand(statusb[i]),
//								impl.getDeviceSpecs().get(4).getContainerId());
//					}
//			catch (TimeoutException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			impl.setGridConnected(statusb[i]);
//			origin = impl.getDeviceSpecs().get(4).getContainerId();
//			ev = new DoubleEvent(statusb[i]);
//			impl.getMaster().sendDoubleEvent(ev, origin, impl.getClientId());
//			logger.info("DummyDevice: run(): getEventHandler - new Event from " + 
//					origin + " value " + statusb[i]);
//

			
			
			
			


		} catch (TimeoutException e) {
			logger.error("timeout sending to master", e);
		}
	}

	private void lightOff() {
		//		DefaultProxy<ContainerManagerInterface> clientInfo = new DefaultProxy<ContainerManagerInterface>(
		//				ContainerManagerInterface.class,
		//				ContainerManagerQueueNames
		//						.getContainerManagerInterfaceQueryQueue(), 5000);
		//		ContainerManagerInterface containerManagerClient = null;

/*		try {
			//containerManagerClient = clientInfo.init();
			this.impl.containerManagerClient.sendCommand(new DoubleCommand(0.0),
					new DeviceId("PowerPlug", "enoceanUSB.wrapper"));

			if (lightson == Double.NaN) {
				lightson = 0.0;

			}
			logger.debug("Lightstatus is " + lightson);
		
			//		} catch (IOException e) {
			//			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		//		try {
		//			clientInfo.destroy();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}

	}
}
