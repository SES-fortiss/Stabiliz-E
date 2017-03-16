package org.fortiss.smg.actuatorclient.apros.client;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.ws.http.HTTPException;
import javax.xml.bind.DatatypeConverter;

import org.fortiss.smg.actuatorclient.apros.client.messaging.encoder.JSONEncoder;
import org.fortiss.smg.actuatorclient.apros.client.messaging.messages.Status;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;



/**
 * The Class HTTPClient hides the HTTP interface
 * between client and web-server by providing
 * possibilities for providing data (publish) to the server
 * and to retrieve data (query) from the server. 
 */

public class HTTPClient
{
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(HTTPClient.class);
	
	private String username;
	private String password;	
	private SSLContext sslContext;
	private SSLSocketFactory sslSocketFactory;

	/**
	 * Instantiates a new HTTP client.
	 *
	 * @param username for authentication
	 * @param password for authentication
	 */
	public HTTPClient(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	private byte[] compress(String data) throws IOException
	{		
		ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(data.getBytes("utf-8"));
        gzip.close();
        return obj.toByteArray();
	}
	
	private String encode(byte[] data)
	{			
		return DatatypeConverter.printBase64Binary(data);
	}
	
	/**
	 * Creates the HTTP connection.
	 *
	 * @param url to which to connect
	 * @param compress Switch in/out compression on
	 * @return the http url connection
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	static {			
	    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
	        {
	            public boolean verify(String hostname, SSLSession session)
	            {
	                // ip address of the service URL(like.23.28.244.244)
	                //if (hostname.equals("23.28.244.244"))
	                return true;
	                //return false;
	            }
	        });
	}
	
	private URLConnection createConnection(String url, boolean compress) throws IOException
	{				
		HttpURLConnection conn;		
		URLConnection connection;
		URL address;		
		// set default authenticator
				Authenticator.setDefault(new Authenticator() {
				    protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password.toCharArray());
				    }
				});
				
		address = new URL(url);				
		connection = address.openConnection();		
		if (connection instanceof HttpsURLConnection){
			conn = (HttpsURLConnection) connection;
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

		        @Override
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		            return null;
		        }
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
				}
				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
				}
		    } };
			try {
				sslContext = SSLContext.getInstance( "SSL" );
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		    try {
				sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
			} catch (KeyManagementException e) {
				e.printStackTrace();
			}

		    // Create an ssl socket factory with our all-trusting manager
		    sslSocketFactory = sslContext.getSocketFactory();
			
			((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier()
			{
				@Override
					public boolean verify(String hostname, SSLSession session) {
		            return true;
				}
			});
		}
		else conn = (HttpURLConnection) connection;
		
		logger.info("Calling POST method");
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "fortiss");
		if (compress) conn.setRequestProperty("Accept-Encoding", "gzip,deflate");		
		conn.setRequestProperty("Authorization", "Basic " + encode(new String(username + ":" + password).getBytes()));		
		conn.setDoOutput(true);	
		return conn;
	}
	
	
	/**
	 * Read.
	 *
	 * @param connection the connection
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Status read(URLConnection connection) throws IOException
	{					
		BufferedReader responseReader;
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		if ("gzip".equals(connection.getContentEncoding())) 
		{		
			responseReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream())));
		}
		else
		{
			if ("deflate".equals(connection.getContentEncoding())) 
			{		
				responseReader = new BufferedReader(new InputStreamReader(new InflaterInputStream(connection.getInputStream())));
			}else{
				responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));	
			}			
		}
		while ((inputLine = responseReader.readLine()) != null) 
		{
			response.append(inputLine);
		}		
		responseReader.close();
		return JSONEncoder.getInstance().decode(response.toString(), Status.class);
	}

	public Status send(String data, String url) throws HTTPException, IOException
	{				
		return send(data, new HashMap<String, Object>(), url, 0, true);		
	}
	
	/**
	 * Send the data to server compression turned on
	 *
	 * @param data the data
	 * @param connection the connection
	 * @throws HTTPException the HTTP exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
		   
	public Status send(String data, HashMap<String, Object> fields, String url) throws HTTPException, IOException
	{				
		return send(data, fields, url, 0, true);		
	}
	
	/**
	 * Send.
	 *
	 * @param data the data
	 * @param connection the connection
	 * @param compress Switch in/out compression on
	 * @throws HTTPException the HTTP exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Status send(String data, HashMap<String, Object> fields, String url, boolean compress) throws HTTPException, IOException
	{			
		return send(data, fields, url, 0, compress);		
	}
	
	/**
	 * Send.
	 *
	 * @param data the data
	 * @param connection the connection
	 * @param timeout the timeout
	 * @throws HTTPException the HTTP exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Status send(String data, HashMap<String, Object> fields, String url, int timeout) throws HTTPException, IOException
	{			
		return send(data, fields, url, timeout, true);		
	}
	
	/**
	 * Send.
	 *
	 * @param data the data
	 * @param fields the other parameter in addition to data to be sent
	 * @param url
	 * @param timeout the timeout
	 * @param compress
	 * @throws HTTPException the HTTP exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Status send(String data, HashMap<String, Object> fields, String url, int timeout, boolean compress) throws HTTPException, IOException
	{					
		URLConnection connection = createConnection(url, compress);
		DataOutputStream postWriter = new DataOutputStream(connection.getOutputStream());
		
		//TODO: Debug always false
		compress = false;
		
		
		String payload = new String();		
		if (compress)
		{	
			if (data != null) payload = "data=" + URLEncoder.encode(encode(compress(data)), "UTF-8") + "&";			
			payload += "compress=True";
			logger.info("Payload: " + payload);
		}
		else
		{
			if (data != null) payload = "data=" + data + "&";
			payload += "compress=False";
		}				
		for (String key : fields.keySet())
		{
			payload += ("&" + key + "=" + fields.get(key)); 
		}

		payload +="&timeout=" + String.valueOf(timeout);
		postWriter.writeBytes(payload);
		postWriter.flush();
		postWriter.close();		
				
		if (((HttpURLConnection)connection).getResponseCode() != 200)
		{									
			throw(new HTTPException(((HttpURLConnection)connection).getResponseCode()));
		}		
		Status status = read(connection);
		return status;
	}
}
