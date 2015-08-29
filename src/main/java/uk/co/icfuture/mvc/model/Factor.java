package uk.co.icfuture.mvc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

import uk.co.icfuture.mvc.exception.InvalidFactorValueException;

@Entity
@Table(name = "tblfactor")
public class Factor {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "factorId", unique = true)
	private int factorId;

	@Enumerated
	@Column(name = "factorType")
	private FactorType factorType;

	@Column(name = "stringValue")
	private String stringValue;

	@Column(name = "dateValue")
	private DateTime dateValue;

	@Column(name = "doubleValue")
	private double doubleValue;

	@Column(name = "longValue")
	private long longValue;

	@Column(name = "hashValue")
	private int hashValue;

	public Factor() {
	}

	public Factor(FactorType t, String value)
			throws InvalidFactorValueException {
		if (t.isString()) {
			this.factorType = t;
			setStringValue(value);
		} else {
			throw new InvalidFactorValueException(t, value);
		}
	}

	public Factor(FactorType t, DateTime value)
			throws InvalidFactorValueException {
		if (t.isDate()) {
			this.factorType = t;
			setDateValue(value);
		} else {
			throw new InvalidFactorValueException(t, value);
		}
	}

	public Factor(FactorType t, long value) throws InvalidFactorValueException {
		if (t.isLong()) {
			this.factorType = t;
			setLongValue(value);
		} else {
			throw new InvalidFactorValueException(t, value);
		}
	}

	public Factor(FactorType t, double value)
			throws InvalidFactorValueException {
		if (t.isDouble()) {
			this.factorType = t;
			setDoubleValue(value);
		} else {
			throw new InvalidFactorValueException(t, value);
		}
	}

	public int getFactorId() {
		return factorId;
	}

	public void setFactorId(int factorId) {
		this.factorId = factorId;
	}

	public FactorType getFactorType() {
		return factorType;
	}

	public void setFactorType(FactorType factorType) {
		this.factorType = factorType;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
		setHashValue(this.stringValue.hashCode());
	}

	public DateTime getDateValue() {
		return dateValue;
	}

	public void setDateValue(DateTime dateValue) {
		this.dateValue = dateValue;
		setHashValue(this.dateValue.hashCode());
	}

	public double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValue = doubleValue;
		setHashValue(new Double(this.doubleValue).hashCode());
	}

	public long getLongValue() {
		return longValue;
	}

	public void setLongValue(long longValue) {
		this.longValue = longValue;
		setHashValue(new Double(this.longValue).hashCode());
	}

	public int getHashValue() {
		return hashValue;
	}

	public void setHashValue(int hashValue) {
		this.hashValue = hashValue;
	}

	@Override
	public String toString() {
		if (factorType.isDate()) {
			return dateValue.toString();
		} else if (factorType.isLong()) {
			return Long.toString(longValue);
		} else if (factorType.isDouble()) {
			return Double.toString(doubleValue);
		}
		return stringValue;
	}
}
