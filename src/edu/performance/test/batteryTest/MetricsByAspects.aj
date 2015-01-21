package com.fsck.k9.controller;

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
import android.os.Environment;
import android.os.SystemClock;

/**
 * This class is responsible to get metric information about the tests.
 * Currently the collected data are: - execution time. Missing - memory use
 * (Debug class) - data traffic - Others
 * 
 * @author Thiago
 * 
 */
public aspect  MetricsByAspects {
	private long start = 0;
	private long start2 = 0;
	private long startActivity = 0;
	double nanoSegRate = 1000000.0;
	BufferedWriter out = null;
	private final String dataFileName = "k9_Test.xml";
	//Variables to get FPS rate
	protected long startTime;
	protected long fpsStartTime;
	protected long numFrames;


	public MetricsByAspects() {
		
		try {
			
			out = new BufferedWriter(new FileWriter((new File(
					Environment
					.getExternalStorageDirectory() + "/" + "PerformanceTestReports" + "/" + dataFileName))));
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

	
	@Pointcut("execution(* com.glTron.*.onSurfaceCreated(..))")
	public void TestMethodsEnd() {

	}
	
	@Pointcut(" execution(* com.fsck.k9.controller.MessagingController.run(..))")
	public void TestMethodsTimeAndMemoryJava() {

	}
	
	@Pointcut("execution(* com.fsck.k9.controller.MessagingController.notifyWhileSendingDone(..))")
	public void TestMethodsFinish() {

	}
	
	@Before("TestMethodsTimeAndMemoryJava()")
	public void logBeforeTM(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
	}

	@After("TestMethodsFinish()")
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
	
	
	

	/*@Before("TestMethodsFinish()")
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

	}*/
	
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
