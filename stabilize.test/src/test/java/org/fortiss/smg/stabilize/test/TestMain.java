package org.fortiss.smg.stabilize.test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.fortiss.smg.analyzer.api.AnalyzerInterface;
import org.fortiss.smg.analyzer.api.AnalyzerQueueNames;
import org.fortiss.smg.analyzer.api.NoDataFoundException;
import org.fortiss.smg.containermanager.api.devices.String;
import org.fortiss.smg.remoteframework.lib.DefaultProxy;

public class TestMain {

	public static void main(String[] args) {
		DefaultProxy<AnalyzerInterface> analyzerInfo = new DefaultProxy<AnalyzerInterface>(AnalyzerInterface.class,
				AnalyzerQueueNames.getQueryQueue(), 5000);
		
		try {
			AnalyzerInterface analyzer = analyzerInfo.init();
			String dev = new String("DAPO", "dummy.wrapper");
			analyzer.getArithmeticMean(1487199600905L, 1487203200905L, dev);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
