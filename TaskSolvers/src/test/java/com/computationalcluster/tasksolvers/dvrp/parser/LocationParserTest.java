package com.computationalcluster.tasksolvers.dvrp.parser;

import org.junit.Assert;
import org.junit.Test;

public class LocationParserTest {

	@Test
	public void parserTest() {
		LocationParser locationParser = new LocationParser(1);
		locationParser.parse("");
		locationParser.parse("1 2.0 3.0");
		
		Assert.assertEquals(2, locationParser.getData().length);
		Assert.assertTrue(locationParser.getData()[0][0] == 2);
		Assert.assertTrue(locationParser.getData()[1][0] == 3);
	}
}
