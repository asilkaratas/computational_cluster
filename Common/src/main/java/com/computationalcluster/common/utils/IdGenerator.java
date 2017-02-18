package com.computationalcluster.common.utils;

import java.math.BigInteger;

public class IdGenerator {
	
	private BigInteger counter = null;
	
	public IdGenerator() {
		counter = new BigInteger("0");
	}
	
	public BigInteger getNextId() {
		counter = counter.add(BigInteger.ONE);
		final BigInteger id = new BigInteger(counter.toByteArray());
		return id;
	}
	
	
}
