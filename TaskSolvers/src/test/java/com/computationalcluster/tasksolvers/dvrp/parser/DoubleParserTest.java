package com.computationalcluster.tasksolvers.dvrp.parser;

import org.junit.Assert;
import org.junit.Test;

public class DoubleParserTest {

	@Test
	public void parserTest() {
		DoubleParser doubleParser = new DoubleParser(1);
		doubleParser.parse(" 2");
		
		Assert.assertEquals(1, doubleParser.getData().length);
		Assert.assertTrue(doubleParser.getData()[0] == 2);
		
	}
}
