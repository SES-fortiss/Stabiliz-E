package org.fortiss.smg.actuatormaster.api;

import java.util.concurrent.TimeoutException;

import org.fortiss.smg.ambulance.api.HealthCheck;
import org.fortiss.smg.smgschemas.commands.DoubleCommand;

public interface IActuatorClient extends HealthCheck {

	void onDoubleCommand(DoubleCommand command, String containerId) throws TimeoutException;
	
}
