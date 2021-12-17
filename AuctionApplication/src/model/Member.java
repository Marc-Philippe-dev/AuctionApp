package model;

public class Member {
	private int id ;
	private String name; 
	static private int num = 0;
	
	public Member(String name) {
		this.id = num++;
		this.name = name;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
