package org.fortiss.smg.actuatorclient.apros.client.messaging.encoder;

public class Log {
public static void d(String tag, String msg){
	System.out.println(String.format("D:[%s] %s\n",tag,msg));
}

public static void e(String tag, String msg){
	System.out.println(String.format("E:[%s] %s\n",tag,msg));
}

public static void e(String tag, String msg, Throwable err){
	System.out.println(String.format("E:[%s] %s\n",tag,msg));
	err.printStackTrace();
}

}
