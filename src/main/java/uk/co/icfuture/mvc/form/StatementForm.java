package uk.co.icfuture.mvc.form;

import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;

public class StatementForm extends FilterForm<Statement, StatementFilter> {

	public StatementForm() {
		this(new Statement(), new StatementFilter());
	}

	public StatementForm(Statement object, StatementFilter filter) {
		super(object, filter);
	}
}
