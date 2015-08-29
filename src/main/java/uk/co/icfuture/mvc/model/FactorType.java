package uk.co.icfuture.mvc.model;

public enum FactorType {

	START_TIME(FactorTypeType.DATE), SELECT_TIME(FactorTypeType.DATE), END_TIME(
			FactorTypeType.DATE), POSITION(FactorTypeType.LONG), OUT_OF(
			FactorTypeType.LONG), WEIGHT(FactorTypeType.DOUBLE), GROUP_SIZE(
			FactorTypeType.LONG);

	private enum FactorTypeType {
		STRING, DATE, LONG, DOUBLE
	}

	private FactorTypeType type;

	private FactorType(FactorTypeType type) {
		this.type = type;
	}

	public boolean isString() {
		return type == FactorTypeType.STRING;
	}

	public boolean isDate() {
		return type == FactorTypeType.DATE;
	}

	public boolean isLong() {
		return type == FactorTypeType.LONG;
	}

	public boolean isDouble() {
		return type == FactorTypeType.DOUBLE;
	}

}
