package edu.performance.test.util;

import android.os.Parcel;
import android.os.Parcelable;

public class StringParcel implements Parcelable {
	
	private String string;
	private Integer integer;

	@Override
	public int describeContents() {
		
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(string);
		dest.writeInt(integer);
		
	}
	
	public StringParcel(){
		
	}
	
	public StringParcel(String s){
		string = s;
	}
	
	public StringParcel(Parcel in) {
		readFromParcelable(in); 
		} 
	
	
	private void readFromParcelable(Parcel entrada){
		string = entrada.readString();
		integer = entrada.readInt();
	}
	
	
	public static final Parcelable.Creator<StringParcel> CREATOR = new Parcelable.Creator<StringParcel>() { 
		public StringParcel createFromParcel(Parcel in) { return new StringParcel(in); }
		public StringParcel[] newArray(int size) { return new StringParcel[size]; } 
		}; 
	}