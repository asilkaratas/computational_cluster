package com.computationalcluster.tasksolvers.dvrp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ProblemDataTest {
	private static final Logger logger = Logger.getLogger(ProblemDataTest.class);
	
	@Test
	public void problemTest() throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("10.vrp");
		
		byte[] data = IOUtils.toByteArray(is);
		
		ProblemData problemData = new ProblemData(new String(data));
		
		logger.info("problemData:\n" + problemData);
		
		Assert.assertEquals(10, problemData.getAvabilityTimes().length);
	}
}
