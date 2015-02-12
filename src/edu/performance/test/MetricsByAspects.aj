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

/**
 * This class is responsible to collect metric information about the tests.
 *  
 * @author Thiago
 * 
 */
public aspect MetricsByAspects {
	private long start = 0;
	private long start2 = 0;
	private long startActivity = 0;
	final double nanoSegRate = 1000000.0;
	BufferedWriter out = null;
	//Variables to get FPS rate
	protected long startTime;
	protected long fpsStartTime;
	protected long numFrames;


	public MetricsByAspects() {
		
		try {
			
			out = new BufferedWriter(new FileWriter((new File(
					Library.DATA_FILE_NAME))));
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n");
			out.write("<performanceData \n\t\t\t xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n\t\t\t xsi:noNamespaceSchemaLocation=\"model.xsd\">\n");
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Esse pointcut define o joinpoint para todas as chamadas de métodos
	 * nas classes de operações de código nativo
	 */
	@Pointcut("call(* edu.performance.test.*.*.*.testN*(..)) ||"
			+ "call(* edu.performance.test.*.*.*.testTNM*(..))")
	public void metricsNativeMethods() {

	}
	/**
	 * Esse pointcut define o joinpoint para todas as chamadas de métodos
	 * nas classes de operações de JAVA
	 */
	@Pointcut("call(* edu.performance.test.*.*.testA*(..)) || "
			+ "call(* edu.performance.test.*.*.testJ*(..)) || "
			+ "call(* edu.performance.test.*.*.testTJM*(..))")
	public void metricsJavaMethods() {

	}
	/**
	 * Esse pointcut é usado para não monitorar possíveis chamadas de métodos na classe
	 * Library e BatteryMetric
	 */
	@Pointcut("call(* edu.performance.test.Library.*.testJ*(..)) ||"
			+ "within(Library) ||"
			+ "within(edu.performance.test.batterytest.BatteryMetric)")
	public void inNotMonitoredClasses() {

	}
	/**
	 * Esse pointcut define o joinpoint para todas as chamadas de métodos
	 * na classe de operações de BD
	 */
	@Pointcut("call(* edu.performance.test.*.*.testTpJM*(..))")
	public void metricsDBMethods() {

	}


	@Pointcut("execution(* edu.performance.test.screenoperation.*.execute(..))")
	public void startMetricsFromUI() {

	}
	
	@Pointcut("call(* edu.performance.test.screenoperation.*.finish(..)) || "
			+ "call(* edu.performance.test.streamingoperation.*.finish(..))")
	public void finishMetricsFromUI() {

	}
	/**
	 * Esse pointcur define o joinpoint da criação das interfaces gráficas nas
	 * unidades de teste que compõem a categoria GPU
	 */
	@Pointcut ("execution(* edu.performance.test.*.*.*.surfaceCreated(..)) || "
			+  "execution(* edu.performance.test.*.*.surfaceCreated(..)) || "
			+  "execution(* edu.performance.test.*.*.*.onSurfaceCreated(..)) || "
			+  "execution(* edu.performance.test.streamingoperation.*.onSurfaceTextureAvailable(..))")
	public void startGraphicMetrics(){
		
	}
	/**
	 * Esse pointcur define o joinpoint do método de desenho nas
	 * unidades de teste que compõem a categoria GPU
	 */
	@Pointcut("call(* edu.performance.test.*.*.*.doDraw(..)) || "
			+ "call(* edu.performance.test.*.*.doDraw(..)) || "
			+ "execution(* edu.performance.test.*.*.*.onDrawFrame(..)) ||"
			+ "execution(* edu.performance.test.streamingoperation.*.onSurfaceTextureUpdated(..))")
	public void finishGraphicMetrics() {

	}

	/**
	 * 
	 */
	@Pointcut("call(* edu.performance.test.Library.finish(..))")
	public void finishTestProgram() {

	}
	
	


	/**
	 * Esse advice é utilizado para iniciar a coleta das métricas
	 * de todos os métodos JAVA ou fim da Activities de Screen e Streaming.
	 */
	@Before("(metricsJavaMethods() || startMetricsFromUI()) && !inNotMonitoredClasses()")
	public void logBefore(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
	}
	/**
	 * Esse advice é utilizado para finalizar a coleta das métricas
	 * de todos os métodos JAVA ou fim da Activities de Screen e Streaming.
	 * Métricas coletadas:
	 * - Tempo de CPU
	 * - Tempo de execução
	 * - Quantidade de objetos alocados global e localmente
	 * - Tamanho dos objetos alocados global e localmente
	 * - Acionamentos do GC global e localmente
	 */
	@After("(metricsJavaMethods() || finishMetricsFromUI()) && !inNotMonitoredClasses()")
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
	/**
	 * Esse advice é utilizado para iniciar a coleta das métricas dos métodos
	 * nativos
	 */
	@Before("metricsNativeMethods()")
	public void logBeforeNative(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
		Debug.startNativeTracing();
	}
	/**
	 * Esse advice é utilizado para finalizar a coleta das métricas dos métodos
	 * nativos.
	 * Métricas coletadas:
	 * - Tempo de CPU
	 * - Tempo de execução
	 * - Quantidade de objetos alocados global e localmente
	 * - Tamanho dos objetos alocados global e localmente
	 * - Acionamentos do GC global e localmente
	 * - Tamanho do heap
	 * - Heap alocado
	 */
	@After("metricsNativeMethods()")
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
	/**
	 * Esse advice é usado para iniciar as métricas de operações
	 * de banco de dados
	 */
	@Before("metricsDBMethods()")
	public void logBeforeTpJM(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		start = System.nanoTime();
		start2 = SystemClock.uptimeMillis();
		
		Debug.startAllocCounting();
	}
	/**
	 * Esse advice é usado para finalizar as métricas de operações
	 * de banco de dados.
	 * Métricas coletadas:
	 * - Tempo de CPU
	 * - Tempo de execução
	 * - Quantidade de objetos alocados global e localmente
	 * - Tamanho dos objetos alocados global e localmente
	 * - Acionamentos do GC global e localmente
	 * - Throughput
	 */
	@After("metricsDBMethods()")
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
	/**
	 * Esse advice é usado para iniciar a coleta de todas as métricas
	 * relacionadas a categoria GPU
	 */
	@Before("startGraphicMetrics()")
	public void logBeforeActivity(JoinPoint joinPoint) {
		Debug.resetAllCounts();
		Debug.startAllocCounting();
		startActivity = SystemClock.uptimeMillis();
		startTime = SystemClock.uptimeMillis();
		fpsStartTime = startTime;
		numFrames = 0;
	}
	
	/**
	 * Esse advice é usado para iniciar a coleta de todas as métricas
	 * relacionadas a categoria GPU.
	 * Métricas coletadas:
	 * - Tempo de CPU
	 * - Tempo de execução
	 * - Quantidade de objetos alocados global e localmente
	 * - Tamanho dos objetos alocados global e localmente
	 * - Acionamentos do GC global e localmente
	 * - FPS
	 */
	@After("finishGraphicMetrics()")
	public void logAfterActivity(JoinPoint joinPoint) {
		long fpsElapsed = SystemClock.uptimeMillis() - fpsStartTime;
		numFrames++;
		if (fpsElapsed > 5 * 1000) {
		double elapsedTime = (SystemClock.uptimeMillis() - startActivity);
		Debug.stopAllocCounting();
		String fpsString = "";		

			float fps = (numFrames * 1000.0F) / fpsElapsed;

			fpsString = fpsString.concat("\t\t<fps>" + fps + "</fps>\n");
			
			fpsStartTime = SystemClock.uptimeMillis();
			numFrames = 0;
		
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
	
	/**
	 * Esse advice finaliza a coleta das métricas.
	 */
	@Before("finishTestProgram()")
	public void logBeforeFinish(JoinPoint joinPoint) {
		System.out.println(joinPoint.getSourceLocation().getFileName());
		try {
			out.write("</performanceData>");
			out.flush();
			out.close();
			System.out.println("Closing " + Library.DATA_FILE_NAME + " file at MetricsByAspect!!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * Esses métodos é utilizado para formatar os paramêtros dos métodos.
	 */
	private String print(Object objets[]){
		if(objets == null || objets.length == 0)
			return "";
		
		String message = "";
		for(Object s : objets){
			if(s != null)
				if(s instanceof Cursor)
					message = message.concat("\n\t\t\t<item>\"" + ((Cursor)s).getCount() + "\"</item>");
				else
					message = message.concat("\n\t\t\t<item>\"" + 
				((s.toString() != null && s.toString().length() < 80)? s.toString() : "TooBigOrNull!" ) + "\"</item>");
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
//--------------------------------------------------------------------------------------------------------------------------------------------------
	

}
