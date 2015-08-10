package uk.co.icfuture.mvc.form;

import javax.validation.Valid;

import uk.co.icfuture.mvc.form.filter.Filter;

public class FilterForm<T, U extends Filter> {

	@Valid
	private T object;

	private U filter;

	public FilterForm(T object, U filter) {
		this.object = object;
		this.filter = filter;
	}

	public T getObject() {
		return this.object;
	}

	public void setObject(T obj) {
		this.object = obj;
	}

	public U getFilter() {
		return this.filter;
	}
}
