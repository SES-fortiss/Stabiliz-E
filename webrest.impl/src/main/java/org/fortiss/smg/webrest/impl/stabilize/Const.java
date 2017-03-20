package org.fortiss.smg.webrest.impl.stabilize;

import java.net.InetAddress;

public class Const {

    private static String accessKey;
    private static String secretKey;
    
    private static int port = 8091;

  
    public static String getSecretKey() {
        return Const.secretKey;
    }

    public static String getAccessKey() {
        return Const.accessKey;
    }

    public static String getServerURL(int port) {
    	return "http://" + AuthenticationHelper.serverIP + ":"+ port;
    }
    
    public static String getServerURL() {
    	return "http://" + AuthenticationHelper.serverIP + ":"+ port;
    }
    
    public static int getUserId() {
        return 10;
    }

    public static void setSecretKey(String secretKey) {
        Const.secretKey = secretKey;
    }

    public static void setAccessKey(String key) {
        Const.accessKey = key;
    }

	public static void setServerIP(InetAddress ip) {
		AuthenticationHelper.serverIP = ip;
	}

	public static int getPort() {
		// TODO Auto-generated method stub
		return port;
	}
}
