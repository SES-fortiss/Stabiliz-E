package org.fortiss.smg.informationbroker.api;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public interface IDatabase {

	 List<Map<String,Object>>  getSQLResults(String sql) throws TimeoutException;
	 boolean executeQuery(String sql) throws TimeoutException;
	
	 List<Map<String,Object>>  getSQLResultsoldDB(String sql) throws TimeoutException;
	 boolean executeQueryoldDB(String sql) throws TimeoutException;
	 
	 ResultSet getSQLResultSet(String sql) throws TimeoutException;	 
	 
	 //returns the next available ConatinerID
	 long getContainerID(String devID, String wrapperID) throws TimeoutException ;
	
}
