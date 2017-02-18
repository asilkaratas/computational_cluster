package com.computationalcluster.common.serializer;

import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.common.messages.Status;

public class SerializerFactory {
	
	private static XMLSerializer serializer = null;
	public static Serializer getDefaultXMLSerializer(){
		if(serializer == null){
			serializer = new XMLSerializer();
			serializer.register("Error.xsd", Error.class);
			serializer.register("Register.xsd", Register.class);
			serializer.register("RegisterResponse.xsd", RegisterResponse.class);
			serializer.register("SolveRequest.xsd", SolveRequest.class);
			serializer.register("SolveRequestResponse.xsd", SolveRequestResponse.class);
			serializer.register("NoOperation.xsd", NoOperation.class);
			serializer.register("DivideProblem.xsd", DivideProblem.class);
			serializer.register("SolutionRequest.xsd", SolutionRequest.class);
			serializer.register("Status.xsd", Status.class);
			serializer.register("SolvePartialProblems.xsd", SolvePartialProblems.class);
			serializer.register("SolutionMessage.xsd", Solutions.class);
		}
		
		return serializer;
	}
}
