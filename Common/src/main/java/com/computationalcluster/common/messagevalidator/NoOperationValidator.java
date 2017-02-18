package com.computationalcluster.common.messagevalidator;

import java.util.List;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers.BackupCommunicationServer;

public class NoOperationValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final NoOperation noOperation = (NoOperation)message;
		
		final BackupCommunicationServers backupServers = noOperation.getBackupCommunicationServers();
		if(backupServers != null) {
			final List<BackupCommunicationServer> serverList = backupServers.getBackupCommunicationServer();
			
			for(int i = 0; i < serverList.size(); i++) {
				final BackupCommunicationServer server = serverList.get(i);
				if(server.getAddress() == null || serverList.isEmpty()) {
					error.setErrorMessage(ErrorMessage.NO_OPERATION_INVALID_SERVER_ADDRESS);
					return error;
				}
				
				if(server.getPort() <= 0) {
					error.setErrorMessage(ErrorMessage.NO_OPERATION_INVALID_SERVER_PORT);
					return error;
				}
			}
		}
		
		return null;
	}

}
