package edu.performance.test.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class InternetController {

	public static void setWifiAvailability(boolean status, Activity ref) {

		WifiManager wifiManager = (WifiManager) ref.getBaseContext()
				.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(status);
	}
	
	public static boolean getInternetAvailability(Activity ref){
		
		    ConnectivityManager cm =
		        (ConnectivityManager) ref.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    if (netInfo != null && netInfo.isConnected()) {
		    	
		        return true;
		    }
		    
		    return false;
		
	}
	/*
	public static void setMobileDataAvailability(Context ref, boolean status){
	    final ConnectivityManager conman = (ConnectivityManager) ref.getSystemService(Context.CONNECTIVITY_SERVICE);
	    @SuppressWarnings("rawtypes")
		Class conmanClass, iConnectivityManagerClass;
	    Field iConnectivityManagerField;
	    final Object TestingConnectivityManager;
	    final Method setMobileDataEnabledMethod;
		try {
			conmanClass = Class.forName(conman.getClass().getName());
			System.out.println(conman.getClass().getName());
			iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
		    TestingConnectivityManager = iConnectivityManagerField.get(conman);
		    iConnectivityManagerClass = Class.forName(TestingConnectivityManager.getClass().getName());
		    //System.out.println(TestingConnectivityManager.getClass().getName());
		    Method mts[] = iConnectivityManagerClass.getDeclaredMethods();
		    for (Method m : mts){
		    	if(m.getName().contains("setMobileDataEnabled"))
		    	System.out.println(m.getName() + " " + m.getReturnType());
		    	for(int i = 0; i < m.getTypeParameters().length; i++){
		    		System.out.println(m.getTypeParameters()[i]);
		    	}
		    }
			setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", void.class);
		    setMobileDataEnabledMethod.setAccessible(true);

		    setMobileDataEnabledMethod.invoke(TestingConnectivityManager, status);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally {

		} 
	   
		
	    
	}*/ 

}
