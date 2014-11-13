package edu.performance.test.sortingoperation;

import java.util.GregorianCalendar;
import java.util.Random;

public class People implements Comparable<People>{
	
	private String name;
	private String address;
	private String id;
	private GregorianCalendar bornDate;

	@Override
	public int compareTo(People another) {
		
		return this.id.compareTo(another.getId());
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GregorianCalendar getBornDate() {
		return bornDate;
	}

	public void setBornDate(GregorianCalendar bornDate) {
		this.bornDate = bornDate;
	}
	
public People[] createPeople(int numberOfPeople){
		
		People[] p = new People[numberOfPeople];
		People p1;
		Random r = new Random(77);
		
		for(int i = 0; i<numberOfPeople;i++){
			
			p1 = new People();
			p1.setAddress(String.valueOf(r.nextDouble()));
			p1.setBornDate(new GregorianCalendar());
			p1.setId(String.valueOf(r.nextLong()));
			p1.setName(String.valueOf(r.nextFloat()));
			p[i] = p1;
			p1 = null;
		}
		return p;
		
	}

public String toString(){
	return "id:" + this.id + " name:" + this.name;
}
	


}
