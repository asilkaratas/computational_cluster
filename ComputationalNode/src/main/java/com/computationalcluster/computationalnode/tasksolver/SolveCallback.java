package com.computationalcluster.computationalnode.tasksolver;

import java.math.BigInteger;

public interface SolveCallback {
	void onComplete(BigInteger taskId, byte[] solutionData, BigInteger computationTime, boolean timeoutOccured);
}
