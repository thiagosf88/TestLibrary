package edu.performance.test.util;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.domain.Operation;
import edu.performance.test.domain.TestParameter;

public class TestExpandableListAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private List<Operation> operations;


	public TestExpandableListAdapter(Context context, List<Operation> operations) {
		super();
		this.context = context;
		this.operations = operations;
	}

	@Override
	public int getGroupCount() {
		
		
		
		return operations == null?  0:  this.operations.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return operations.get(groupPosition) == null ? 0 : operations.get(groupPosition).getParameters().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return operations.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return operations.get(groupPosition).getParameters().values().toArray()[childPosition];
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final Operation operation = operations.get(groupPosition);
        
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.operation_line, parent, false);
		
		TextView nome = (TextView)view.findViewById(R.id.labelOperation);
		nome.setText(operation.toString());
		CheckBox isTested = (CheckBox) view.findViewById(R.id.check_will_be_tested);
		isTested.setChecked(operation.isChecked());
		isTested.setOnCheckedChangeListener(new CheckUpdateListener(operation));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.test_line, parent, false);
		
		//Operation tf = operations.get(groupPosition);
		TestParameter tp = (TestParameter)
				operations.get(groupPosition).getParameters().values().toArray()[childPosition];
		if(tp.getName().contains(PerformanceTestActivity.NETWORK_TEST)){
		CheckBox isNetworkTest = (CheckBox) view.findViewById(R.id.isnetworktest);
		isNetworkTest.setChecked((Boolean)tp.getValue());
		isNetworkTest.setVisibility(View.VISIBLE);
		}else
		if(tp.getName().contains(PerformanceTestActivity.MAXTIME)){
		EditText maxTime = (EditText) view.findViewById(R.id.maxTime);
		TextView label = (TextView) view.findViewById(R.id.labelTime);
		label.setVisibility(View.VISIBLE);
		maxTime.setText(tp.getValue().toString());
		maxTime.setVisibility(View.VISIBLE);
		}else
		
		if(tp.getName().contains(PerformanceTestActivity.LEVEL_WEBSITE)){
			EditText levelString = (EditText) view.findViewById(R.id.levelString);
			TextView label = (TextView) view.findViewById(R.id.labelLevelString);
			label.setVisibility(View.VISIBLE);
			levelString.setInputType(EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
			levelString.setText(tp.getValue().toString());
			levelString.setVisibility(View.VISIBLE);
			label.setVisibility(View.VISIBLE);
		}else
		if(tp.getName().contains(PerformanceTestActivity.LEVEL_FILENAME)){
			EditText levelString = (EditText) view.findViewById(R.id.levelString);
			TextView label = (TextView) view.findViewById(R.id.labelLevelString);
			label.setVisibility(View.VISIBLE);
			levelString.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);
			levelString.setText(tp.getValue().toString());
			levelString.setVisibility(View.VISIBLE);
		}
		if(tp.getName().contains(PerformanceTestActivity.LEVEL_INT)){
			EditText levelInt = (EditText) view.findViewById(R.id.levelInt);
			TextView label = (TextView) view.findViewById(R.id.labelLevel);
			label.setVisibility(View.VISIBLE);
			levelInt.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
			levelInt.setText(tp.getValue().toString());
			levelInt.setVisibility(View.VISIBLE);
		}else
		if(tp.getName().contains(PerformanceTestActivity.LEVEL_DOUBLE)){
			EditText levelInt = (EditText) view.findViewById(R.id.levelInt);
			TextView label = (TextView) view.findViewById(R.id.labelLevel);
			label.setVisibility(View.VISIBLE);
			levelInt.setInputType(EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
			levelInt.setText(tp.getValue().toString());
			levelInt.setVisibility(View.VISIBLE);
		}
		
		
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private final class CheckUpdateListener implements OnCheckedChangeListener
    {
        private final Operation parent;
         
        private CheckUpdateListener(Operation parent)
        {
            this.parent = parent;
        }
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            Log.i("onCheckedChanged", "isChecked: "+isChecked);
            parent.setChecked(isChecked);
             
            //((TestExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
             
          
        }
    }

}
