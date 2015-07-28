package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.icfuture.mvc.dao.MetaDao;
import uk.co.icfuture.mvc.dao.StatementDao;
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
			return statementDao.getStatements(filter);
		}
	}

	public Statement getStatement(int id) {
		if (id == 0) {
			return new Statement();
		} else {
			try {
				Statement statement = statementDao.getStatement(id);
				return statement;
			} catch (Exception ex) {
				return new Statement();
			}
		}
	}

	public Statement saveStatementWithId(Statement statement, int id) {
		if (statement.getId() == 0 && id != 0) {
			statement.setId(id);
		}
		return saveStatement(statement);
	}
}
