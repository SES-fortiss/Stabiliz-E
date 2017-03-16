package org.fortiss.smg.analyzer.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.math3.exception.NullArgumentException;
import org.fortiss.smg.containermanager.api.ContainerManagerInterface;
import org.fortiss.smg.containermanager.api.ContainerManagerQueueNames;
import org.fortiss.smg.containermanager.api.devices.DeviceId;
import org.fortiss.smg.informationbroker.api.DoublePoint;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;

/**
 * @author Ann Katrin Gibtner (annkatrin.gibtner@tum.de)
 *
 */
public class DataSet {

	/**
	 * time where the data collection in the database starts
	 */
	private long startDate = 0;
	/**
	 * time where the data collection in the database ends
	 */
	private long stopDate = 0;
	/**
	 * includes the single data points which were found in the database
	 */
	private List<DoublePoint> dataList = new ArrayList<DoublePoint>();
	/**
	 * the sensor device, to whom the data belong
	 */
	private String containerId;

	/**
	 * represents a data set, which includes data from the database; <br>
	 * NOTE: If startDate and stopDate have the same value the data set includes
	 * the newest element found in the database
	 * 
	 * @param startDate
	 *            time where the data collection in the database starts
	 * @param stopDate
	 *            time where the data collection in the database ends
	 * @param containerId
	 *            defines the sensor device, of whom data should be collected
	 * @param dataList
	 *            includes the single data points which were found in the
	 *            database
	 * @throws NullArgumentException
	 *             if either {@code stopDate, startDate or dev} is set null
	 */
	public DataSet(long startDate, long stopDate, String containerId,
			List<DoublePoint> dataList) throws NullArgumentException {
		if (startDate == 0) {
			throw new NullArgumentException();
		}
		if (stopDate == 0) {
			throw new NullArgumentException();
		}
		if (containerId == null) {
			throw new NullArgumentException();
		}
		this.startDate = (startDate);
		this.stopDate = (stopDate);
		this.containerId = containerId;
		this.dataList = dataList;
	}
	
	public DataSet(long startDate, long stopDate, DeviceId devid,
			List<DoublePoint> dataList) throws NullArgumentException {
	
		DefaultProxy<ContainerManagerInterface> containerInfo = new DefaultProxy<ContainerManagerInterface>(
				ContainerManagerInterface.class, ContainerManagerQueueNames.getContainerManagerInterfaceQueue(), 5000);

		try {
			ContainerManagerInterface containerManager = containerInfo.init();
			String containerId = containerManager.getContainerId(devid);
			
			if (startDate == 0) {
				throw new NullArgumentException();
			}
			if (stopDate == 0) {
				throw new NullArgumentException();
			}
			if (containerId == null) {
				throw new NullArgumentException();
			}
			this.startDate = (startDate);
			this.stopDate = (stopDate);
			this.containerId = containerId;
			this.dataList = dataList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * represents a data set, which includes data from the database
	 * 
	 * @param startDate
	 *            time where the data collection in the database starts
	 * @param stopDate
	 *            time where the data collection in the database ends
	 * @param dev
	 *            defines the sensor device, of whom data should be collected
	 * @throws NullArgumentException
	 *             if either {@code stopDate, startDate or device} is set null
	 */
	public DataSet(long startDate, long stopDate, String containerId)
			throws NullArgumentException {
		if (startDate == 0) {
			throw new NullArgumentException();
		}
		if (stopDate == 0) {
			throw new NullArgumentException();
		}
		if (containerId == null) {
			throw new NullArgumentException();
		}
		this.startDate= (startDate);
		this.stopDate = (stopDate);
		this.containerId = containerId;
		this.dataList = null;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getStopDate() {
		return stopDate;
	}

	public void setStopDate(long stopDate) {
		this.stopDate = stopDate;
	}

	public String getContainerId() {
		return containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public List<DoublePoint> getDataList() {
		return dataList;
	}

	public void setDataList(List<DoublePoint> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return DataSet.class.toString() + "[startDate=" + startDate
				+ ", stopDate=" + stopDate + ", ContainerId=" + containerId
				+ "dataList=" + dataList + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DataSet other = (DataSet) obj;
		if (this.startDate != other.startDate) {
			return false;
		}
		if (this.stopDate != other.stopDate){
			return false;
		}
		if (!this.containerId.equals(other.containerId)) {
			return false;
		}
		if (!this.dataList.equals(other.dataList)) {
			return false;
		}
		return true;
	}
}
