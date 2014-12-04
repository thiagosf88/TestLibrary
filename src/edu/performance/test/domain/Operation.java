package edu.performance.test.domain;

import java.util.HashMap;

public class Operation
{
    private String nameActivity;

    private boolean checked;
     
    private HashMap<String,TestParameter> parameters;   
    
     
    public Operation(String nameActivity, boolean checked,
			HashMap<String, TestParameter> parameters) {
		super();
		this.nameActivity = nameActivity;
		this.checked = checked;
		this.parameters = parameters;
	}
	public String getNameActivity() {
		return nameActivity;
	}
	public void setNameActivity(String nameActivity) {
		this.nameActivity = nameActivity;
	}
	public boolean isChecked()
    {
        return checked;
    }
    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
     
    public HashMap<String,TestParameter> getParameters()
    {
        return parameters;
    }
     
    public void setParameters(HashMap<String,TestParameter> parameter)
    {
        this.parameters = parameter;
    }
    
    @Override
    public String toString() {
    	
    	return nameActivity;
    }
}
