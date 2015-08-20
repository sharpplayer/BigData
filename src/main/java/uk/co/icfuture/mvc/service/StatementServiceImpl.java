package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.icfuture.mvc.dao.MetaDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;

@Service("statementService")
@Transactional
public class StatementServiceImpl implements StatementService {

	@Autowired
	private StatementDao statementDao;

	@Autowired
	private MetaDao metaDao;

	public Statement saveStatement(Statement statement) {
		statement.setMeta(metaDao.findMetas(statement.getMeta()));
		return statementDao.saveStatement(statement);
	}

	public List<Statement> getStatements(StatementFilter filter) {
		if (filter.isEmpty()) {
			return new ArrayList<Statement>();
		} else {
			return statementDao.getStatements(filter.getFilterText());
		}
	}

	public Statement getStatement(int id) throws ItemNotFoundException {
		if (id == 0) {
			return new Statement();
		} else {
			Statement statement = statementDao.getStatement(id);
			if (statement == null) {
				throw new ItemNotFoundException("statement", id);
			} else {
				Hibernate.initialize(statement.getMeta());
				return statement;
			}
		}
	}

	public Statement saveStatementWithId(Statement statement, int id) {
		if (statement.getStatementId() == 0 && id != 0) {
			statement = statementDao.getStatement(id).merge(statement);
		}
		return saveStatement(statement);
	}

	public boolean shouldSaveStatement(Statement statement, boolean filter,
			boolean newStatement) {
		boolean save = true;
		if (filter) {
			// Don't save if not updated nor new item created
			if (newStatement && statement.getStatement().isEmpty()) {
				save = false;
			}
		}

		return save;
	}

}
