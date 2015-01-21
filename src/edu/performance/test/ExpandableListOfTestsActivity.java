package edu.performance.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.performance.test.database.DatabaseOperationActivity;
import edu.performance.test.domain.Operation;
import edu.performance.test.domain.TestParameter;
import edu.performance.test.downloadoperation.DownloadOperationActivity;
import edu.performance.test.fileoperation.FileOperationActivity;
import edu.performance.test.floatoperation.FloatOperationActivity;
import edu.performance.test.integeroperation.IntegerOperationActivity;
import edu.performance.test.locationoperation.GpsOperationActivity;
import edu.performance.test.mailoperation.MailOperationActivity;
import edu.performance.test.memoryoperation.MemoryOperationActivity;
import edu.performance.test.nativo.fileoperation.FileOperationNativeActivity;
import edu.performance.test.nativo.floatoperation.FloatOperationNativeActivity;
import edu.performance.test.nativo.integeroperation.IntegerOperationNativeActivity;
import edu.performance.test.nativo.memoryoperation.MemoryOperationNativeActivity;
import edu.performance.test.nativo.stringoperation.StringOperationNativeActivity;
import edu.performance.test.sortingoperation.SortingOperationActivity;
import edu.performance.test.stringoperation.StringOperationActivity;
import edu.performance.test.util.TestExpandableListAdapter;

public class ExpandableListOfTestsActivity extends ExpandableListActivity {
	
	
	    //Initialize variables
	    /*private static final String STR_CHECKED = " has Checked!";
	    private static final String STR_UNCHECKED = " has unChecked!";
	    private int ParentClickStatus=-1;
	    private int ChildClickStatus=-1;*/
	    private List<Operation> tests;
	    private Button btStart;
	     
	    @Override
	    protected void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        
	        final Context appRef = this; 
	        Resources res = this.getResources();
	        Drawable devider = res.getDrawable(R.drawable.line);
	         
	        // Set ExpandableListView values
	         
	        getExpandableListView().setGroupIndicator(null);
	        getExpandableListView().setDivider(devider);
	        getExpandableListView().setChildDivider(devider);
	        getExpandableListView().setDividerHeight(1);
	        registerForContextMenu(getExpandableListView());
	         
	        setContentView(R.layout.list_of_tests);
			
			tests = createTests();
			
			loadOperations(tests);
			btStart = (Button) findViewById(R.id.bt_start_test2);
			btStart.setOnClickListener(new OnClickListener()
		    {

		        public void onClick(View v)
		        {   
		             Intent i = new Intent(appRef, Library.class);
		             startActivity(i);
		        } 

		    });
	    }
	    
	    private List<Operation> createTests(){
			List<Operation> tests = new ArrayList<Operation>();
			Operation eachTest = null;
			HashMap<String, TestParameter> p = new HashMap<String, TestParameter>();
			
			//Database Operation 
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 15, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 100, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",DatabaseOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.dbOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Download Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", true, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 0, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",DownloadOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.dwOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//File Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 0, PerformanceTestActivity.LEVEL_INT));
			p.put(PerformanceTestActivity.LEVEL_FILENAME, new TestParameter("extraStrFile",  this.getFilesDir().getPath() + "/app_performanceDir/small.txt", PerformanceTestActivity.LEVEL_FILENAME));
			p.put("ActivityName", new TestParameter("string",FileOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.flOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Float Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_DOUBLE, new TestParameter("extraDec",  999983, PerformanceTestActivity.LEVEL_DOUBLE));
			p.put("ActivityName", new TestParameter("string",FloatOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.fpOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Integer Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  1000, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",IntegerOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.itOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Gps Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", true, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  5, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",GpsOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.gpOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Mail Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", true, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_FILENAME, new TestParameter("extraStrFile",  this.getFilesDir().getPath() + "/app_performanceDir/small.txt", PerformanceTestActivity.LEVEL_FILENAME));
			p.put(MailOperationActivity.DESTINATION, new TestParameter("extraStr", "thiago.soares@ymail.com", MailOperationActivity.DESTINATION));
			p.put("ActivityName", new TestParameter("string",GpsOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.mlOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Memory Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  1000, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",MemoryOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.mmOp), false, p);
			tests.add(eachTest);
			p = null;

			//Native File Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 0, PerformanceTestActivity.LEVEL_INT));
			p.put(PerformanceTestActivity.LEVEL_FILENAME, new TestParameter("extraStrFile",  this.getFilesDir().getPath() + "/app_performanceDir/small.txt", PerformanceTestActivity.LEVEL_FILENAME));
			p.put("ActivityName", new TestParameter("string",FileOperationNativeActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.flnOp), false, p);
			tests.add(eachTest);
			
			//Native Float Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_DOUBLE, new TestParameter("extraDec",  999983, PerformanceTestActivity.LEVEL_DOUBLE));
			p.put("ActivityName", new TestParameter("string",FloatOperationNativeActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.fpnOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Native Integer Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  1000, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",IntegerOperationNativeActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.itnOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Memory Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  1000, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",MemoryOperationNativeActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.mmnOp), false, p);
			tests.add(eachTest);
			p = null;
			
			//Sorting Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt",  1000, PerformanceTestActivity.LEVEL_INT));
			p.put("ActivityName", new TestParameter("string",SortingOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.stOp), false, p);
			tests.add(eachTest);			
			p = null;

			//String Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 0, PerformanceTestActivity.LEVEL_INT));
			p.put(PerformanceTestActivity.LEVEL_FILENAME, new TestParameter("extraStrFile",  this.getFilesDir().getPath() + "/app_performanceDir/small.txt", PerformanceTestActivity.LEVEL_FILENAME));
			p.put(PerformanceTestActivity.SNIPPETS, new TestParameter("extraStrID", 2131165184, PerformanceTestActivity.SNIPPETS));
			p.put("ActivityName", new TestParameter("string",StringOperationActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.sgOp), false, p);
			tests.add(eachTest);
			p = null;

			//String Operation
			p = new HashMap<String, TestParameter>();
			p.put(PerformanceTestActivity.MAXTIME, new TestParameter("extraInt", 17, PerformanceTestActivity.MAXTIME));
			p.put(PerformanceTestActivity.NETWORK_TEST, new TestParameter("extraBool", false, PerformanceTestActivity.NETWORK_TEST));
			p.put(PerformanceTestActivity.LEVEL_INT, new TestParameter("extraInt", 0, PerformanceTestActivity.LEVEL_INT));
			p.put(PerformanceTestActivity.LEVEL_FILENAME, new TestParameter("extraStrFile",  this.getFilesDir().getPath() + "/app_performanceDir/small.txt", PerformanceTestActivity.LEVEL_FILENAME));
			p.put(PerformanceTestActivity.SNIPPETS, new TestParameter("extraStrID", 2131165184, PerformanceTestActivity.SNIPPETS));
			p.put("ActivityName", new TestParameter("string",StringOperationNativeActivity.class.toString(), "ActivityName"));
			eachTest = new Operation(getApplicationContext().getResources().getString(R.string.sgnOp), false, p);
			tests.add(eachTest);
			
			return tests;
		}
	    
	    private void loadOperations(final List<Operation> newParents)
	    {
	        if (newParents == null)
	            return;
	         
	        //parents = newParents;
	         
	        // Check for ExpandableListAdapter object
	        if (this.getExpandableListAdapter() == null)
	        {
	             //Create ExpandableListAdapter Object
	            final TestExpandableListAdapter mAdapter = new TestExpandableListAdapter(this, newParents);
	             
	            // Set Adapter to ExpandableList Adapter
	            this.setListAdapter(mAdapter);
	        }
	        else
	        {
	             // Refresh ExpandableListView data 
	            ((TestExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
	        }   
	    }
	    
	    
	    public void onGroupCollapse(int groupPosition){
	    	Operation eachTest = tests.get(groupPosition);
	    	System.out.println(eachTest.isChecked());
	    }
	    public void onGroupExpand(int groupPosition){
	    	Operation eachTest = tests.get(groupPosition);
	    	System.out.println(eachTest.isChecked());
	    }


}
