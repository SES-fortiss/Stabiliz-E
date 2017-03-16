package org.fortiss.smg.containermanager.api.devices;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum ContainerType {	
	COMPLEX("Complex"), FLOOR("Floor"), WING("Wing"), ROOM("Room"), BUILDING("Building"), DEVICE("Device"), DEVICEGATEWAY("Devicegateway"), UNKNOWN("Unknown"), VIRTUALNODE("Virtualnode"), NODE("Node");
	private String value;
	
	ContainerType(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
	
	
	public static ContainerType fromSting(String x) {
		for (ContainerType type : ContainerType.values()) {
			if (type.toString().equals(x)) {
				return type;
			}
		}
		return ContainerType.UNKNOWN;
    }
	
	public static ContainerType fromValue(String v) {
		for (ContainerType c: ContainerType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
	
}
