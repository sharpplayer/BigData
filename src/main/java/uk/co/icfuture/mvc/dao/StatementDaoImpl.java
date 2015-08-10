package uk.co.icfuture.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.QuestionStatement;
import uk.co.icfuture.mvc.model.Statement;

@Repository("statementDao")
public class StatementDaoImpl extends AbstractDao<Statement> implements
		StatementDao {

	public Statement saveStatement(Statement statement) {
		if (statement.getStatementId() == 0) {
			return persist(statement);
		} else {
			return update(statement);
		}
	}

	public List<Statement> getStatements(String filter) {
		return getFilteredList("statement", "%" + filter + "%");
	}

	public Statement getStatement(int id) {
		return getItemById(id);
	}

	public List<QuestionStatement> findStatements(
			List<QuestionStatement> statements, boolean persistNew)
			throws ItemNotFoundException {
		ArrayList<QuestionStatement> ret = new ArrayList<QuestionStatement>();
		int index = 0;
		for (QuestionStatement m : statements) {
			if (m.getStatement().getStatementId() == 0) {
				List<Statement> l = getList("statement", m.getStatement()
						.getStatement());
				if (l.size() == 0) {
					if (persistNew) {
						m.setStatement(persist(m.getStatement()));
						ret.add(m);
					} else {
						throw new ItemNotFoundException("statement", m
								.getStatement().getStatement(), index);
					}
				} else {
					m.setStatement(l.get(0));
					ret.add(m);
				}
			} else {
				ret.add(m);
			}
			index++;
		}
		return ret;
	}

}
