package edu.performance.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import android.database.Cursor;
import android.os.Debug;
import android.os.SystemClock;
import edu.performance.test.util.WriteNeededFiles;

/**
 * This class is responsible to get metric information about the tests.
 * Currently the collected data are: - execution time. Missing - memory use
 * (Debug class) - data traffic - Others
 * 
 * @author Thiago
 * 
 */
public aspect MetricsByAspects {
	private long start = 0;
	private long start2 = 0;
	private long startActivity = 0;
	double nanoSegRate = 1000000.0;
	BufferedWriter out = null;
	private final String dataFileName = "testBrowserApp.xml";
	//Variables to get FPS rate
	protected long startTime;
	protected long fpsStartTime;
	protected long numFrames;


	public MetricsByAspects() {
		
		try {
			
			out = new BufferedWriter(new FileWriter((new File(
					WriteNeededFiles.REPORT_DIRECTORY_NAME + "/" + dataFileName))));
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
			out.write("<performanceData \n\t\t\t xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n\t\t\t xsi:noNamespaceSchemaLocation=\"model.xsd\">\n");
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * @Before(
	 * "execution(* edu.performance.test.memorymanipulation.MemoryManipulation.*(..))"
	 * ) public void logBefore(JoinPoint joinPoint) {
	 * 
	 * System.out.println("logBefore() is running!");
	 * System.out.println("hijacked : " + joinPoint.getSignature().getName());
	 * System.out.println("******"); //long start = System.nanoTime(); }
	 * 
	 * @After("call(* edu.performance.test.memorymanipulation.*.*(..))") public
	 * void logAfter(JoinPoint joinPoint) {
	 * 
	 * System.out.println("logAfter() is running!");
	 * System.out.println("hijacked : " + joinPoint.getSignature().getName());
	 * System.out.println("******"); //long elapsedTime = System.nanoTime() -
	 * start; //System.out.println("Method execution time: " + elapsedTime +
	 * " milliseconds."); }
	 */

	@Pointcut("call(* edu.performance.test.*.*.*.testN*(..))")
	public void TestMethodsExecutionTimeNative() {

	}

	@Pointcut("call(* edu.performance.test.*.*.testA*(..))")
	public void TestMethodsExecutionTime() {

	}

	@Pointcut("call(* edu.performance.test.*.*.testJ*(..))")
	public void TestMethodsExecutionTimeJava() {

	}
	
	@Pointcut("call(* edu.performance.test.Library.*.testJ*(..))")
	public void NTestMethodsExecutionTimeJava() {

	}

	@Pointcut("call(* edu.performance.test.*.*.testTJM*(..))")
	public void TestMethodsTimeAndMemoryJava() {

	}
	
	@Pointcut("call(* edu.performance.test.*.*.testTpJM*(..))")
	public void TestMethodsThroughputAndMemoryJava() {

	}
	@Pointcut("call(* edu.performance.test.*.*.*.testTNM*(..))")
	public void TestMethodsTimeAndMemoryNative() {

	}
	
	@Pointcut("execution(* edu.performance.test.screen.*.execute(..))")
	public void TestMethodsTimeAndMemoryInterfaceCreated() {

	}
	
	@Pointcut("call(* edu.performance.test.screen.*.finish(..))")
	public void TestMethodsTimeAndMemoryInterfaceFinished() {

	}
	
	@Pointcut("call(* edu.performance.test.streamingoperation.*.finish(..))")
	public void TestMethodsTimeAndMemoryInterfaceFinished2() {

	}	
	
	//@Pointcut("execution(* edu.performance.test.*.*.*Activity.onCreate(..))")
	@Pointcut("execution(* edu.performance.test.*.*.*.surfaceCreated(..))")
	public void TestMethodsTimeAndMemorySurfaceCreated() {

	}
	@Pointcut("execution(* edu.performance.test.*.*.surfaceCreated(..))")
	public void TestMethodsTimeAndMemorySurfaceCreated2() {

	}
	@Pointcut("execution(* edu.performance.test.*.*.onSurfaceCreated(..))")
	public void TestMethodsTimeAndMemorySurfaceCreated3() {

	}
	@Pointcut("call(* edu.performance.test.*.*.*.doDraw(..))")
	public void TestMethodsTimeAndMemoryActivityOnFinish() {

	}
	@Pointcut("call(* edu.performance.test.*.*.doDraw(..))")
	public void TestMethodsTimeAndMemoryActivityOnFinish2() {

	}
	@Pointcut("execution(* edu.performance.test.*.*.onDrawFrame(..))")
	public void TestMethodsTimeAndMemoryActivityOnFinish3() {

	}
	
	@Pointcut("call(* edu.performance.test.streamingoperation.*.finishTest(..))")
	public void TestMethodsTimeAndMemoryActivityOnFinish4() {

	}
	
	@Pointcut("call(* edu.performance.test.Library.finish(..))")
	public void TestMethodsFinish() {

	}
	
	@Pointcut("(within(Library) || within(edu.performance.test.batterytest.BatteryOperation))")
	public void InClassesNotMonitored(){
		
	}



	@Before("TestMethodsExecutionTime() || TestMethodsExecutionTimeJava()"
			+ " && !NTestMethodsExecutionTimeJava()")
	public void logBefore(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
		// System.out.println("Start counting " +
		// joinPoint.getSignature().getName() + start);
	}

	@After("TestMethodsExecutionTime() || TestMethodsExecutionTimeJava()"
			+ " && !NTestMethodsExecutionTimeJava()")
	public void logAfter(JoinPoint joinPoint) {
		Debug.stopAllocCounting();
		double elapsedTime = (System.nanoTime() - start) / nanoSegRate;
		double elapsedTime2 = (SystemClock.uptimeMillis() - start2);
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		
		try {
			out.append("\t<method> \n"
					+ "\t\t<name>" + joinPoint.getSignature().getName() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ elapsedTime + "</elapsedTime>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ "\t\t<elapsedTime2>"	+ elapsedTime2 + "</elapsedTime2>\n"
					+ "\t</method>\n");
			out.flush();

		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfter");
		}

	}

	@Before("TestMethodsExecutionTimeNative() || TestMethodsTimeAndMemoryNative()")
	public void logBeforeNative(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
		Debug.startNativeTracing();
	}

	@After("TestMethodsExecutionTimeNative() || TestMethodsTimeAndMemoryNative()")
	public void logAfterNative(JoinPoint joinPoint) {
		Debug.stopAllocCounting();
		Debug.stopNativeTracing();
		double elapsedTime = (System.nanoTime() - start) / nanoSegRate;
		double elapsedTime2 = (SystemClock.uptimeMillis() - start2);
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		long nativeheapSize = Debug.getNativeHeapSize();
		long nativeallocated = Debug.getNativeHeapAllocatedSize();
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		
		
		try {
			out.append("\t<method>\n"
					+ "\t\t<name>" + joinPoint.getSignature().getName() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ elapsedTime + "</elapsedTime>\n"
					+ "\t\t<heapSize>" + nativeheapSize + "</heapSize>\n" 
					+ "\t\t<allocatedHeap>" + nativeallocated + "</allocatedHeap>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ "\t\t<elapsedTime2>"	+ elapsedTime2 + "</elapsedTime2>\n"
					+ "\t</method>\n");
			out.flush();

		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfterNative");
		}

	}

	@Before(" (TestMethodsTimeAndMemoryJava() ||  TestMethodsTimeAndMemoryInterfaceCreated()) && !InClassesNotMonitored()")
	public void logBeforeTM(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
	}

	@After("(TestMethodsTimeAndMemoryJava() || TestMethodsTimeAndMemoryInterfaceFinished()"
			+ " || TestMethodsTimeAndMemoryInterfaceFinished2()) && !InClassesNotMonitored() ")
	public void logAfterTM(JoinPoint joinPoint) {
		Debug.stopAllocCounting();
		double elapsedTime = (System.nanoTime() - start) / nanoSegRate;
		double elapsedTime2 = (SystemClock.uptimeMillis() - start2);
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		long tempoCpu = android.os.Process.getElapsedCpuTime();

		try {
			out.append("\t<method>\n"
					+ "\t\t<name>" + joinPoint.getSignature().toShortString() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ elapsedTime + "</elapsedTime>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ "\t\t<elapsedTime2>"	+ elapsedTime2 + "</elapsedTime2>\n"
					+ "\t</method>\n");
			out.flush();

		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfterTM");
		}

	}
	
	
	@Before("TestMethodsThroughputAndMemoryJava()")
	public void logBeforeTpJM(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
	}

	@After("TestMethodsThroughputAndMemoryJava()")
	public void logAfterTpJM(JoinPoint joinPoint) {
		Debug.stopAllocCounting();
		double elapsedTime = (System.nanoTime() - start) / nanoSegRate;
		double elapsedTime2 = (SystemClock.uptimeMillis() - start2);
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		try {
			out.append("\t<method>\n"
					+ "\t\t<name>" + joinPoint.getSignature().toShortString() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ elapsedTime + "</elapsedTime>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ "\t\t<elapsedTime2>"	+ elapsedTime2 + "</elapsedTime2>\n"
					+ "\t\t<throughput>"	+ myToInteger(joinPoint.getArgs()[0]) /(elapsedTime/1000f) + "</throughput>\n"
					+ "\t</method>\n");
			out.flush();
		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfterTpJM");
		}

	}
	@Before("TestMethodsTimeAndMemorySurfaceCreated() || TestMethodsTimeAndMemorySurfaceCreated2()"
			+ " || TestMethodsTimeAndMemorySurfaceCreated3() ")
	public void logBeforeActivity(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		Debug.startAllocCounting();
		startActivity = SystemClock.uptimeMillis();
		startTime = SystemClock.uptimeMillis();
		fpsStartTime = startTime;
		numFrames = 0;
	}
	
	@After("TestMethodsTimeAndMemoryActivityOnFinish() || TestMethodsTimeAndMemoryActivityOnFinish2()"
			+ " || TestMethodsTimeAndMemoryActivityOnFinish3()")
	public void logAfterActivity(JoinPoint joinPoint) {
		long fpsElapsed = SystemClock.uptimeMillis() - fpsStartTime;
		numFrames++;
		if (fpsElapsed > 5 * 1000) {
		double elapsedTime = (SystemClock.uptimeMillis() - startActivity);
		Debug.stopAllocCounting();
		String fpsString = "";		
		//if (fpsElapsed > 5 * 1000) { // every 5 seconds
			float fps = (numFrames * 1000.0F) / fpsElapsed;
			//Log.d("Library", "Frames per second: " + fps + " (" + numFrames
					//+ " frames in " + fpsElapsed + " ms)");
			fpsString = fpsString.concat("\t\t<fps>" + fps + "</fps>\n");
			
			fpsStartTime = SystemClock.uptimeMillis();
			numFrames = 0;
		//}
		
		
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		try {
			out.append("\t<method>\n"
					+ "\t\t<name>" + joinPoint.getTarget().getClass().getSimpleName() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ fpsElapsed + "</elapsedTime>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ (!fpsString.trim().isEmpty() ? fpsString : "")
					+ "\t</method>\n");
			out.flush();
				fpsString = "";
				if(elapsedTime < 15000) //TODO Se é a ultima execução da operação gráfica
					Debug.startAllocCounting();
				
		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfterActivity");
		}
		}

	}
	
	@After("TestMethodsTimeAndMemoryActivityOnFinish4()")
	public void logAfterActivity4(JoinPoint joinPoint) {
		
		numFrames++;
		
		double elapsedTime = (SystemClock.uptimeMillis() - startActivity);
		Debug.stopAllocCounting();	
		int allocCountG = Debug.getGlobalAllocCount();
		int allocSizeG = Debug.getGlobalAllocSize();
		int gcInvocationG = Debug.getGlobalGcInvocationCount();
		int gcInvocationT = Debug.getThreadGcInvocationCount();
		int allocCountT = Debug.getThreadAllocCount();
		int allocSizeT = Debug.getThreadAllocSize();
		Debug.resetAllCounts();
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		try {
			out.append("\t<method>\n"
					+ "\t\t<name>" + joinPoint.getTarget().getClass().getSimpleName() + "</name>\n"
					+ "\t\t<methodParameters>" + print(((MethodSignature)joinPoint.getSignature()).getParameterNames()) + "</methodParameters>\n"
					+ "\t\t<methodArguments>" + print(joinPoint.getArgs()) + "</methodArguments>\n"	
					+ "\t\t<cpuTime>" + tempoCpu + "</cpuTime>\n" 
					+ "\t\t<elapsedTime>"	+ elapsedTime + "</elapsedTime>\n"
					+ "\t\t<allocCountG>" + allocCountG + "</allocCountG>\n" 
					+ "\t\t<allocSizeG>" + allocSizeG + "</allocSizeG>\n"
					+ "\t\t<gcInvocationG>" + gcInvocationG + "</gcInvocationG>\n"
					+ "\t\t<allocCountT>" + allocCountT + "</allocCountT>\n"
					+ "\t\t<allocSizeT>" + allocSizeT + "</allocSizeT>\n"
					+ "\t\t<gcInvocationT>" + gcInvocationT + "</gcInvocationT>\n"
					+ "\t</method>\n");
			out.flush();
		} catch (IOException e) {

			System.err.println("No possible to write in file. logAfterActivity4");
		}
		

	}
	

	@Before("TestMethodsFinish()")
	public void logBeforeFinish(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSourceLocation().getFileName());
		try {
			out.write("</performanceData>");
			out.flush();
			out.close();
			System.out.println("Closing" + dataFileName + " file at MetricsByAspect!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private String print(Object objets[]){
		if(objets == null || objets.length == 0)
			return "";
		
		String message = "";
		for(Object s : objets){
			if(s != null)
				if(s instanceof Cursor)
					message = message.concat("\n\t\t\t<item>\"" + ((Cursor)s).getCount() + "\"</item>");
				else
					message = message.concat("\n\t\t\t<item>\"" + ((s.toString() != null && s.toString().length() < 80)? s.toString() : "TooBigOrNull!" ) + "\"</item>");
		}
		
		return message;
	}
	
	private Integer myToInteger(Object o){
		if(o instanceof Integer)
			return (Integer) o;
		else if (o instanceof String)
			return Integer.parseInt((String)o);
		else if(o instanceof Cursor)
			return ((Cursor)o).getCount();
		else
			return 1;
	}

	/*
	 * @Before("TestMethodsB() || TestMethodsB2()") public void
	 * logBeforeB(JoinPoint joinPoint) {
	 * 
	 * start = System.nanoTime();
	 * 
	 * System.out.println("Start counting " + joinPoint.getSignature().getName()
	 * + start); }
	 * 
	 * @Before("TestMethodsC() || TestMethodsC2()") public void
	 * logBeforeC(JoinPoint joinPoint) {
	 * 
	 * start = System.nanoTime(); System.out.println("Start counting " +
	 * joinPoint.getSignature().getName() + start); }
	 * 
	 * @After("TestMethodsC()") public void logAfterC(JoinPoint joinPoint) {
	 * long elapsedTime = System.nanoTime() - start; long tempoCpu =
	 * android.os.Process.getElapsedCpuTime(); System.out.println("cpu:"+
	 * tempoCpu + "Method execution time: " + elapsedTime + " milliseconds." +
	 * start);
	 * 
	 * }
	 * 
	 * @Before("TestMethodsF()") public void logBeforeF(JoinPoint joinPoint) {
	 * 
	 * start = System.nanoTime(); System.out.println("Start counting " +
	 * joinPoint.getSignature().getName() + start); }
	 * 
	 * @After("TestMethodsF()") public void logAfterF(JoinPoint joinPoint) {
	 * long elapsedTime = System.nanoTime() - start; long tempoCpu =
	 * android.os.Process.getElapsedCpuTime(); System.out.println("cpu:"+
	 * tempoCpu + "Method execution time: " + elapsedTime + " milliseconds." +
	 * start);
	 * 
	 * }
	 */
	/*
	 * @Around("TestMethodsExecutionTime()") public PerformanceTest
	 * profile(ProceedingJoinPoint pjp) throws Throwable{ long start =
	 * System.nanoTime(); System.out.println("Going to call the method.");
	 * PerformanceTest output = (P)pjp.proceed();
	 * System.out.println("Method execution completed."); long elapsedTime =
	 * (System.nanoTime() - start) / nanoSegRate;
	 * System.out.println("Method execution time: " + elapsedTime +
	 * " milliseconds."); return output; }
	 */

}
