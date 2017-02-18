package com.computationalcluster.tasksolvers.dvrp.parser;

import org.junit.Assert;
import org.junit.Test;

public class IntParserTest {

	@Test
	public void parserTest() {
		IntParser intParser = new IntParser(1);
		intParser.parse(" 2");
		
		Assert.assertEquals(1, intParser.getData().length);
		Assert.assertTrue(intParser.getData()[0] == 2);
		
	}
}
