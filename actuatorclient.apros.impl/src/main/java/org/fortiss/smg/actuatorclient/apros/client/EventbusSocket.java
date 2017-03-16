package org.fortiss.smg.actuatorclient.apros.client;

import java.io.IOException;

public interface EventbusSocket {
	public void connect() throws IOException;
	public void close(String reason);
	public void setErrorHandler(MessageHandler handler);
	public void publish(Object data) throws Exception;
}