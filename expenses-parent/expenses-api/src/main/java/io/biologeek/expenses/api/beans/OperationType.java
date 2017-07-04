package io.biologeek.expenses.api.beans;

public class OperationType {
    
	private String name;
    private int sign;


    

	public OperationType(String name2, int sign2) {
		this.name = name2;
		this.sign = sign2;
	}

	public OperationType() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public OperationType name(String name2) {
		this.name = name2;
		return this;
	}
	public OperationType sign(int name2) {
		this.sign = name2;
		return this;
	}
	
}