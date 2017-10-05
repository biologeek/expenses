package io.biologeek.expenses.api.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
@JsonAutoDetect
public enum OperationType {
	EXPENSE(-1, false, false), INCOME(1, false, false), LOAN(1, false, true), DEBT(-1, false, true), REGULAR_EXPENSE(-1,
			true, false), REGULAR_INCOME(1, true,
					false), REGULAR_LOAN(1, true, true), REGULAR_DEBT(-1, true, true), ALL(0, false, false);

	private String name;
	private int sign;
	private boolean regular;
	private boolean temporary;

	OperationType(int sign, boolean regular, boolean temporary) {
		this.regular = regular;
		this.temporary = temporary;
		this.sign = sign;
		this.name = this.name();
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public boolean isTemporary() {
		return temporary;
	}

	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}

	public String getName() {
		return this.name();
	}

	public void setName(String name) {
		this.name = this.name();
	}
	
	@JsonCreator
	public static OperationType creator(Map<String, Object> params) {
		return OperationType.valueOf((String) params.get("name"));		
	}
}
