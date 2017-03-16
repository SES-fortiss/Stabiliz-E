package org.fortiss.smg.alarming.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import org.fortiss.smg.alarming.api.AlarmingInterface;
import org.fortiss.smg.remoteframework.lib.OsgiProxy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.osgi.framework.BundleContext;

@RunWith(JUnit4TestRunner.class)
public class AlarmingTestCase {

	@Inject
	private BundleContext ctx;

	@Configuration
	public Option[] config() {
		return CoreOptions.options(CoreOptions.mavenBundle(
				"org.fortiss.smartmicrogrid", "alarming.api"), CoreOptions
				.mavenBundle("org.fortiss.smartmicrogrid", "alarming.impl"),
				CoreOptions.mavenBundle("org.fortiss.smartmicrogrid",
						"remoteframework.lib"), CoreOptions.junitBundles());
	}

	@Test
	public void getHelloService() throws IOException, TimeoutException {

		OsgiProxy<AlarmingInterface> proxy = new OsgiProxy<AlarmingInterface>(
				AlarmingInterface.class, ctx, 1000);
		AlarmingInterface client = proxy.init();

		Assert.assertEquals(
				"This service implementation should be concatenated", "Hello smg 1234",
				client.doSomething("1234"));
	}
}
