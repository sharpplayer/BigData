package uk.co.icfuture.mvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;

@Repository("statementDao")
public class StatementDaoImpl extends AbstractDao<Statement> implements
		StatementDao {

	public Statement saveStatement(Statement statement) {
		if (statement.getId() == 0) {
			return persist(statement);
		} else {
			return update(statement);
		}
	}

	public List<Statement> getStatements(StatementFilter filter) {
		return getFilteredList("statement", "%" + filter.getFilterText() + "%");
	}

	public Statement getStatement(int id) {
		Statement statement = getEntityManager().find(Statement.class, id);
		return statement;
	}

}
