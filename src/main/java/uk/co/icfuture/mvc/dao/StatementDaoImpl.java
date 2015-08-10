package uk.co.icfuture.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
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
		return getItemById(id);
	}

	public List<Statement> findStatements(List<Statement> statements,
			boolean persistNew) throws ItemNotFoundException {
		ArrayList<Statement> ret = new ArrayList<Statement>();
		int index = 0;
		for (Statement m : statements) {
			if (m.getId() == 0) {
				List<Statement> l = getList("statement", m.getStatement());
				if (l.size() == 0) {
					if (persistNew) {
						ret.add(persist(m));
					} else {
						throw new ItemNotFoundException("statement",
								m.getStatement(), index);
					}
				} else {
					ret.add(l.get(0));
				}
			} else {
				ret.add(m);
			}
			index++;
		}
		return ret;
	}

}
