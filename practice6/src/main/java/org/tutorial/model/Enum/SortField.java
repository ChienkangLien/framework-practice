package org.tutorial.model.Enum;

public enum SortField {
	EMP_NO("empno"), EMP_NAME("ename"), EMP_JOB("job"), EMP_HIREDATE("hiredate"), EMP_SAL("sal"), EMP_COMM("comm");

	private final String value;

	SortField(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static SortField fromValue(String value) {
		for (SortField field : SortField.values()) {
			if (field.value.equalsIgnoreCase(value)) {
				return field;
			}
		}
		throw new IllegalArgumentException("Invalid sort field: " + value);
	}
}
