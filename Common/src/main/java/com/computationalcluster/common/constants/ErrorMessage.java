package com.computationalcluster.common.constants;

public class ErrorMessage {
	public static final String INVALID_THREAD_STATE = "Invalid thread state.";
	public static final String NO_THREAD_STATUS = "No thread status.";
	public static final String NO_ID = "No id.";
	public static final String INVALID_ID = "Invalid id.";
	public static final String COMPONENT_TYPE_IS_UNDEFINED = "Component type is undefined.";
	public static final String NO_SOLVABLE_PROBLEMS = "No solvable problems.";
	public static final String INVALID_THREAD_COUNT = "Invalid thread count.";
	public static final String SENDER_IS_UNKNOWN = "Sender is unknown. Register again.";
	public static final String NO_PROBLEM_DATA = "No problem data.";
	public static final String INVALID_PROBLEM_TYPE = "Invalid problem type.";
	public static final String INVALID_TIMEOUT = "Invalid timeout.";
	
	//Register
	public static final String REGISTER_NULL_TYPE = "[Register] Null type.";
	public static final String REGISTER_NULL_COMPONENT_TYPE = "[Register] Null component type.";
	public static final String REGISTER_NULL_SOLVABLE_PROBLEMS = "[Register] Null solvable problems.";
	public static final String REGISTER_EMPTY_SOLVABLE_PROBLEMS = "[Register] Empty solvable problems.";
	public static final String REGISTER_INVALID_THREAD_COUNT = "[Register] Invalid thread count.";
	
	//RegisterResponse
	public static final String REGISTER_RESPONSE_NULL_ID = "[RegisterResponse] Null id.";
	public static final String REGISTER_RESPONSE_INVALID_TIMEOUT = "[RegisterResponse] Invalid timeout.";
	
	//NoOperation
	public static final String NO_OPERATION_INVALID_SERVER_ADDRESS = "[NoOperation] Invalid server address.";
	public static final String NO_OPERATION_INVALID_SERVER_PORT = "[RegisterResponse] Invalid server port.";
		
	
	
	//SolveRequest
	public static final String SOLVE_REQUEST_NULL_PROBLEM_DATA = "[SolveRequest] Null problem data.";
	public static final String SOLVE_REQUEST_INVALID_PROBLEM_TYPE = "[SolveRequest] Invalid problem type.";
	public static final String SOLVE_REQUEST_INVALID_TIMEOUT = "[SolveRequest] Invalid timeout.";
	
	//StatusHandler
	public static final String STATUS_HANDLER_INVALID_ID = "[StatusHandler] Invalid id.";
	public static final String STATUS_HANDLER_INVALID_THREAD_COUNT = "[StatusHandler] Invalid thread count.";
	public static final String STATUS_HANDLER_UNKNOWN_SENDER = "[StatusHandler] Unknown sender.";
	
	//SolvePartialProblems
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_ID = "[SolvePartialProblem] Null id.";
	public static final String SOLVE_PARTIAL_PROBLEM_INVALID_PROBLEM_TYPE = "[SolvePartialProblem] Invalid problem type.";
	public static final String SOLVE_PARTIAL_PROBLEM_INVALID_TIMEOUT = "[SolvePartialProblem] Invalid timeout.";
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_COMMON_DATA = "[SolvePartialProblem] Null common data.";
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEMS = "[SolvePartialProblem] Null partial problems.";
	public static final String SOLVE_PARTIAL_PROBLEM_EMPTY_PARTIAL_PROBLEMS = "[SolvePartialProblem] Empty partial problems.";
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_DATA = "[SolvePartialProblem] Null partial problem data.";
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_NODE_ID = "[SolvePartialProblem] Null partial problem node id.";
	public static final String SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_TASK_ID = "[SolvePartialProblem] Null partial problem task id.";
	
	//Status
	public static final String STATUS_NULL_ID = "[Status] Null id.";
	public static final String STATUS_NULL_THREADS = "[Status] Null threads.";
	public static final String STATUS_EMPTY_THREADS = "[Status] Empty threads.";
	public static final String STATUS_INVALID_THREAD_STATE = "[Status] Invalid thread state.";
	public static final String STATUS_NULL_HOW_LONG = "[Status] Null how long.";
	
	//Solutions
	public static final String SOLUTIONS_NULL_ID = "[Solutions] Null id.";
	public static final String SOLUTIONS_NULL_COMMON_DATA = "[Solutions] Null common data.";
	public static final String SOLUTIONS_INVALID_PROBLEM_TYPE = "[Solutions] Invalid problem type.";
	public static final String SOLUTIONS_NULL_SOLUTIONS_LIST = "[Solutions] Null solutions list.";
	public static final String SOLUTIONS_EMPTY_SOLUTIONS_LIST = "[Solutions] Empty solutions list.";
	public static final String SOLUTIONS_NULL_SOLUTION = "[Solutions] Null solution.";
	public static final String SOLUTIONS_NULL_SOLUTION_DATA = "[Solutions] Null solution data.";
	public static final String SOLUTIONS_INVALID_COMPUTATIONS_TIME = "[Solutions] Invalid computations time.";
	public static final String SOLUTIONS_NULL_TASK_ID = "[Solutions] Null task id.";
	public static final String SOLUTIONS_INVALID_SOLUTION_TYPE = "[Solutions] Invalid solution type.";
	
	//SolutionsHandler
	public static final String SOLUTIONS_HANDLER_UNKNOWN_SENDER = "[SolutionsHandler] Unknown sender.";
	public static final String SOLUTIONS_HANDLER_INVALID_COMPONENT_TYPE = "[SolutionsHandler] Invalid component type.";

	//SolutionRequest
	public static final String SOLUTION_REQUEST_NULL_ID = "[SolutionRequest] Null id.";
	
	//Error
	public static final String ERROR_INVALID_ERROR_TYPE = "[Error] Invalid error type.";
	
	//SolveRequestResponse
	public static final String SOLVE_REQUEST_RESPONSE_NULL_ID = "[SolveRequestResponse] Null id.";
	
	//DivideProblem
	public static final String DIVIDE_PROBLEM_NULL_ID = "[DivideProblem] Null id.";
	public static final String DIVIDE_PROBLEM_NULL_DATA = "[DivideProblem] Null data.";
	public static final String DIVIDE_PROBLEM_INVALID_PROBLEM_TYPE = "[DivideProblem] Invalid problem type.";
	public static final String DIVIDE_PROBLEM_INVALID_NODE_COUNT = "[DivideProblem] Invalid node count.";
	public static final String DIVIDE_PROBLEM_NULL_NODE_ID = "[DivideProblem] Null node id.";
	
}
