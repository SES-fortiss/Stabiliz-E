package org.fortiss.smg.actuatormaster.api.events;



public class DoubleEvent extends AbstractDeviceEvent<Double> {

	double maxAbsError =0.0;
	long timestamp = 0;
	
	protected DoubleEvent() {
		super();
	}
	
	public double getMaxAbsError() {
		return maxAbsError;
	}

	public void setMaxAbsError(double maxAbsError) {
		this.maxAbsError = maxAbsError;
	}

	public long getTimeStamp() {
		return timestamp;
	}
	
	public void setTimeStamp(long timestamp) {
		this.timestamp = timestamp;
		
	}
	
	public DoubleEvent(double value) {
		super(value);
	}

	@Override
	public String toString() {
		return "DoubleEvent [maxAbsError=" + maxAbsError + ", value=" + value
				+ "]";
	}
	




	
	
}
