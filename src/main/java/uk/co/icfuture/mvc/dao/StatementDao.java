package uk.co.icfuture.mvc.dao;

import java.util.List;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.model.QuestionStatement;
import uk.co.icfuture.mvc.model.Statement;

public interface StatementDao {
	public Statement saveStatement(Statement statement);

	public List<Statement> getStatements(String filter);

	public Statement getStatement(int statement);

	public List<QuestionStatement> findStatements(
			List<QuestionStatement> statements, boolean persistNew)
			throws ResourceNotFoundException;

	public Statement getItem(String field, String text);

}
