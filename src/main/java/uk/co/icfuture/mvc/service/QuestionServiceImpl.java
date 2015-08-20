package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.icfuture.mvc.dao.QuestionDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.QuestionStatement;

@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private StatementDao statementDao;

	public Question saveQuestion(Question question, boolean fromAnswers)
			throws ItemNotFoundException {
		if (fromAnswers) {
			question.copyAnswers();
		}
		question.setQuestionStatements(statementDao.findStatements(
				question.getQuestionStatements(), true));
		return questionDao.saveQuestion(question);
	}

	public Question saveQuestionWithId(Question question, int id,
			boolean fromAnswers) throws ItemNotFoundException {
		if (question.getQuestionId() == 0 && id != 0) {
			question = questionDao.getQuestion(id).merge(question);
			Hibernate.initialize(question.getQuestionStatements());
			fromAnswers = true;
		}
		return saveQuestion(question, fromAnswers);
	}

	public List<Question> getQuestions(QuestionFilter filter) {
		List<Question> ret;
		if (filter.isEmpty()) {
			ret = new ArrayList<Question>();
		} else {
			if (filter.getDescriptionFilterText().isEmpty()) {
				ret = questionDao.getQuestionsByStatement(filter
						.getStatementFilter().getFilterText());
			} else {
				List<Question> tmp = questionDao.getQuestions(filter
						.getDescriptionFilterText());
				ret = new ArrayList<Question>();
				if (!filter.getStatementFilter().isEmpty()) {
					for (Question q : tmp) {
						if (q.getQuestion().contains(
								filter.getStatementFilter().getFilterText())) {
							ret.add(q);
						}
					}
				} else {
					ret = tmp;
				}
			}
		}
		for (Question q : ret) {
			Hibernate.initialize(q.getQuestionStatements());
		}
		return ret;
	}

	public Question getQuestion(int id) throws ItemNotFoundException {
		if (id == 0) {
			return new Question();
		} else {
			Question question = questionDao.getQuestion(id);
			if (question == null) {
				throw new ItemNotFoundException("question", id);
			} else {
				Hibernate.initialize(question.getQuestionStatements());
				return question;
			}
		}
	}

	public Question insertStatement(Question question, int index,
			int statementId) {
		question.copyAnswers();
		if (index >= question.getQuestionStatements().size()) {
			question.getQuestionStatements().add(
					new QuestionStatement(question, statementDao
							.getStatement(statementId)));
		} else {
			if (question.getQuestionStatements().get(index).getStatement()
					.isEmpty()) {
				question.getQuestionStatements().remove(index);
			}
			question.getQuestionStatements().add(
					index,
					new QuestionStatement(question, statementDao
							.getStatement(statementId)));
		}
		return question;
	}

	@Override
	public boolean shouldSaveQuestion(Question question,
			boolean nonUpdateSubmit, int insertAt, boolean newQuestion) {
		boolean save = true;
		if (nonUpdateSubmit) {

			// Don't save if not updated nor new item created
			if (newQuestion
					&& (question.getQuestion().isEmpty() || question
							.getAnswers().get(0).isEmpty())) {
				save = false;
			}
		} else if (insertAt != -1) {
			if (insertAt == 0 && !question.getAnswers().get(0).isEmpty()) {
				save = true;
			} else if (insertAt == 1 && !question.getQuestion().isEmpty()) {
				save = true;
			} else {
				save = !question.getQuestion().isEmpty()
						&& !question.getAnswers().get(0).isEmpty();
			}
		}

		return save;
	}

}
