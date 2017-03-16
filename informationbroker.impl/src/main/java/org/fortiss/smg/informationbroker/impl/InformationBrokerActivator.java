
package org.fortiss.smg.informationbroker.impl;

import org.fortiss.smg.actuatormaster.api.AbstractListener;
import org.fortiss.smg.actuatormaster.api.IActuatorListener;
import org.fortiss.smg.ambulance.api.AmbulanceInterface;
import org.fortiss.smg.ambulance.api.AmbulanceQueueNames;
import org.fortiss.smg.informationbroker.api.InformationBrokerInterface;
import org.fortiss.smg.informationbroker.api.InformationBrokerQueueNames;
import org.fortiss.smg.informationbroker.impl.cache.LocalCacheManager;
import org.fortiss.smg.informationbroker.impl.persistency.PersistencyDBUtil;
import org.fortiss.smg.informationbroker.impl.persistency.PersistencyLog;
import org.fortiss.smg.informationbroker.impl.persistency.PersistencyQuery;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;
import org.fortiss.smg.remoteframework.lib.DefaultServer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

public class InformationBrokerActivator implements BundleActivator {

	private static final int TIMEOUTSHORT = 2000;
	private static final int TIMEOUTLONG = 5000;

	DefaultServer<InformationBrokerInterface> serverQuery;
	// Logger from sl4j
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(InformationBrokerActivator.class);

	// private Server serverPersistencyQuery;
	private PersistencyLog persistencyLog;
	private PersistencyQuery persistencyQuery;
	private LocalCacheManager localCacheManager;
	private PersistencyDBUtil localdbUtil;
	private PersistencyDBUtil remoteDbUtil;
	private DefaultServer<IActuatorListener> serverLog;
	private AbstractListener serverLogger;

	@Override
	public void start(BundleContext context) throws Exception {
		// register here your services etc.
		// DO NOT start heavy operations here - use threads
		// say hello to ambulance
		DefaultProxy<AmbulanceInterface> ambulanceClient = new DefaultProxy<AmbulanceInterface>(
				AmbulanceInterface.class, AmbulanceQueueNames.getAmbulanceQueue(), TIMEOUTLONG);
		AmbulanceInterface ambuInt = ambulanceClient.init();

		ambuInt.registerComponent(InformationBrokerQueueNames.getQueryQueue(), "InformationBroker");
		ambulanceClient.destroy();

		String dbRemoteConnectionString = "jdbc:" + context.getProperty("remotesql.loc");
		String dbConnectionString = "jdbc:" + context.getProperty("sql.loc");
		String dbictprojectConnectionString = "jdbc:" + context.getProperty("ictprojectsql.loc");

		String dbUser = context.getProperty("sql.user");
		String dbPassword = context.getProperty("sql.pass");

		String dbRemoteUser = context.getProperty("remotesql.user");
		String dbRemotePassword = context.getProperty("remotesql.pass");

		logger.warn("trying to connect to database --> " + dbConnectionString + "," + dbUser + "," + dbPassword);

		logger.warn("trying to connect to remote database --> " + dbRemoteConnectionString + "," + dbRemotePassword
				+ "," + dbRemotePassword);

		/*
		 * LocalCache is started with persistent storage
		 */
		localCacheManager = new LocalCacheManager();

		localCacheManager.startDoubleCache("DoubleCache", localCacheManager.getCacheManager(), logger);

		localdbUtil = new PersistencyDBUtil(dbRemoteConnectionString, dbConnectionString, dbictprojectConnectionString,
				dbUser, dbPassword);
		if (localdbUtil.checkOrOpenDBConnection()) {
			persistencyQuery = new PersistencyQuery(localdbUtil, localCacheManager, logger);

			if (context.getProperty("remotesql.loc") != null) { // remote db
																// given?
				remoteDbUtil = new PersistencyDBUtil(null, dbRemoteConnectionString, dbictprojectConnectionString,
						dbRemoteUser, dbRemotePassword);
				logger.debug("startComDatabase: Creating and publishing PersistencyLog");
				if (remoteDbUtil.checkOrOpenDBConnection()) {
					persistencyLog = new PersistencyLog(remoteDbUtil, localCacheManager, logger);
				} else {
					persistencyLog = new PersistencyLog(localdbUtil, localCacheManager, logger);
				}
			} else {
				persistencyLog = new PersistencyLog(localdbUtil, localCacheManager, logger);
			}
		} else {
			logger.error("Cannot establish connection to the local database");
		}

		// query
		serverQuery = new DefaultServer<InformationBrokerInterface>(InformationBrokerInterface.class, persistencyQuery,
				InformationBrokerQueueNames.getQueryQueue());
		serverQuery.init();

		// logging
		// serverLog = new
		// DefaultServer<IActuatorListener>(IActuatorListener.class,
		// persistencyLog, InformationBrokerQueueNames.getListenerQueue());
		// serverLog.init();

		serverLogger = AbstractListener.registerAsListenerAtServerStatic(persistencyLog, "informationbroker",
				InformationBrokerQueueNames.getListenerQueue(), new AbstractListener.IOnConnectListener() {

					@Override
					public void onSuccessFullConnection() {
						// TODO Auto-generated method stub

					}
				});

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// REMEMBER to destroy all resources, threads and do cleanup

		// remove cache
		localCacheManager.getCacheManager().removeAllCaches();
		localCacheManager.getCacheManager().shutdown();

		// stop db
		persistencyQuery.closeDBConnetion();
		persistencyLog.closeDBConnetion();

		serverQuery.destroy();
		serverLogger.destroy();
		logger.info("InformationBroker is dead");
	}
}
