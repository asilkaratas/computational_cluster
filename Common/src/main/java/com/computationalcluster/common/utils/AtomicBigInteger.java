package com.computationalcluster.common.utils;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicBigInteger {
	private final AtomicReference<BigInteger> valueHolder = new AtomicReference<>();
	
    public AtomicBigInteger(BigInteger bigInteger) {
        valueHolder.set(bigInteger);
    }


	public BigInteger incrementAndGet() {
        for (; ; ) {
        	final BigInteger current = valueHolder.get();
        	final BigInteger next = current.add(BigInteger.ONE);
            if (valueHolder.compareAndSet(current, next)) {
                return next;
            }
        }
    }
}
