package uk.co.icfuture.mvc.model;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import uk.co.icfuture.mvc.exception.InvalidFactorValueException;
import uk.co.icfuture.mvc.utils.Helper;

public class FactorMap implements Cloneable {

	private HashMap<FactorType, Factor> factorMap = new HashMap<FactorType, Factor>();

	public void put(FactorType type, String value)
			throws InvalidFactorValueException {
		factorMap.put(type, new Factor(type, value));
	}

	public void put(FactorType type, DateTime value)
			throws InvalidFactorValueException {
		factorMap.put(type, new Factor(type, value));
	}

	public void put(FactorType type, long value)
			throws InvalidFactorValueException {
		factorMap.put(type, new Factor(type, value));
	}

	public void put(FactorType type, double value)
			throws InvalidFactorValueException {
		factorMap.put(type, new Factor(type, value));
	}

	public boolean contains(FactorType type) {
		return factorMap.containsKey(type);
	}

	public Map<FactorType, Factor> getMap() {
		return this.factorMap;
	}

	@Override
	public Object clone() {
		FactorMap fm = null;
		try {
			fm = Helper.uncheckedCast(super.clone());
			fm.factorMap = Helper.uncheckedCast(factorMap.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return fm;
	}
}
