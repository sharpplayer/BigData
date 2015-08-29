package uk.co.icfuture.mvc.service;

import java.util.List;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;

public interface StatementService {
	public Statement saveStatement(Statement statement);

	public Statement saveStatementWithId(Statement statement, int id);

	public List<Statement> getStatements(StatementFilter filter);

	public Statement getStatement(int id) throws ResourceNotFoundException;

	public boolean shouldSaveStatement(Statement statement, boolean filter,
			boolean newStatement);

}
