package edu.performance.test;


public interface PerformanceTestInterface {
	
	// Constants used on bundle 
		public static final String THELASTTEST = "THELASTTEST";
		public static final String STATUS = "STATUS";
		public static final String FILEPATH = "FILEPATH";
		public static final String FILENAME = "FILENAME";
		public static final String FILELOCATION = "FILELOCATION";
		public static final String STRETCH = "STRETCH";
		public static final String SEARCHABLE = "SEARCHABLE";
		public static final String SNIPPETS = "SNIPPETS";
		public static final String BATTERYTEST = "BATTERYTEST";
		public static final String LEVEL_INT = "LEVEL_INT";
		public static final String LEVEL_DOUBLE = "LEVEL_DOUBLE";
		public static final String LEVEL_FILENAME = "LEVEL_FILENAME";
		public static final String LEVEL_WEBSITE = "LEVEL_WEBSITE";
		public static final String LEVEL_URL = "LEVEL_URL";
		public static final String NETWORK_TEST = "NETWORKTEST";
		public static final String STRING_ARRAY = "STRINGARRAY";
		public static final String ERROR_MESSAGE = "ERRORMESSAGE";
		public static final String PARAMETERS_INT = "PARAMETERSINT";
		public static final String PARAMETERS_WEB_ARRAY = "PARAMETERSWEBARRAY";

	public abstract void execute();
	
	//public abstract <T> void getLevel();

}
