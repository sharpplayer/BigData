package uk.co.icfuture.mvc.form.filter;

import java.io.Serializable;

public class StatementFilter implements Filter, Serializable {

	private static final long serialVersionUID = 8485203717293326473L;

	private String filterText = "";

	public String getFilterText() {
		return this.filterText;
	}

	public void setFilterText(String text) {
		this.filterText = text;
	}

	public boolean isEmpty() {
		return this.filterText.isEmpty();
	}

}
