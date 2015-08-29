package uk.co.icfuture.mvc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.co.icfuture.mvc.model.FactorType;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Factor value invalid")
public class InvalidFactorValueException extends Exception {
	private static final long serialVersionUID = 952855608781440391L;

	public InvalidFactorValueException(FactorType ft, Object value) {
		super("Factor type " + ft.name() + " is not a string, but value "
				+ value.toString() + " given");
	}
}
