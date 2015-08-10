package uk.co.icfuture.mvc.dao;

import java.util.List;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;

public interface StatementDao {
	public Statement saveStatement(Statement statement);

	public List<Statement> getStatements(StatementFilter filter);

	public Statement getStatement(int statement);

	public List<Statement> findStatements(List<Statement> statements,
			boolean persistNew) throws ItemNotFoundException;

}
