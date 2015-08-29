package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.icfuture.mvc.dao.QuestionDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.InvalidFactorValueException;
import uk.co.icfuture.mvc.exception.InvalidResponseException;
import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Answer;
import uk.co.icfuture.mvc.model.FactorMap;
import uk.co.icfuture.mvc.model.FactorType;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.QuestionStatement;
import uk.co.icfuture.mvc.model.Statement;
import uk.co.icfuture.mvc.utils.Helper;

@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private StatementDao statementDao;

	public Question saveQuestion(Question question, boolean fromAnswers)
			throws ResourceNotFoundException {
		if (fromAnswers) {
			question.copyAnswers();
		}
		question.setQuestionStatements(statementDao.findStatements(
				question.getQuestionStatements(), true));
		return questionDao.saveQuestion(question);
	}

	public Question saveQuestionWithId(Question question, int id,
			boolean fromAnswers) throws ResourceNotFoundException {
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

	public Question getQuestion(int id) throws ResourceNotFoundException {
		if (id == 0) {
			return new Question();
		} else {
			Question question = questionDao.getQuestion(id);
			if (question == null) {
				throw new ResourceNotFoundException("question", id);
			} else {
				Hibernate.initialize(question.getQuestionStatements());
				for (QuestionStatement qs : question.getQuestionStatements()) {
					Hibernate.initialize(qs.getStatement().getMeta());
				}
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

	@Override
	public Answer addAnswer(boolean save, int questionId, int answerId,
			FactorMap factors) throws ResourceNotFoundException,
			InvalidFactorValueException, InvalidResponseException {
		List<Answer> ans = processAnswers(save, questionId,
				Arrays.asList(new Integer(answerId)), factors, true);
		return ans.get(0);
	}

	@Override
	public List<Answer> addAnswers(boolean save, int questionId,
			Set<Integer> answerIds, FactorMap factors)
			throws ResourceNotFoundException, InvalidFactorValueException,
			InvalidResponseException {
		return processAnswers(save, questionId, new ArrayList<Integer>(
				answerIds), factors, false);
	}

	@Override
	public List<Answer> addAnswers(boolean save, int questionId,
			List<Integer> answerIds, FactorMap factors)
			throws ResourceNotFoundException, InvalidFactorValueException,
			InvalidResponseException {
		return processAnswers(save, questionId, answerIds, factors, true);
	}

	private List<Answer> processAnswers(boolean save, int questionId,
			List<Integer> answerIds, FactorMap facts, boolean ordered)
			throws ResourceNotFoundException, InvalidFactorValueException,
			InvalidResponseException {
		List<Answer> ret = new ArrayList<Answer>();
		Question q = getQuestion(questionId);
		int answerCount = q.getAnswers().size();
		int groupSize = answerIds.size();
		int index = 1;
		boolean nullFound = false;
		Set<Integer> added = new HashSet<Integer>();
		if (answerIds.size() == 0) {
			throw new InvalidResponseException("Nothing selected");
		}
		for (Integer answerId : answerIds) {
			if (answerId == null) {
				nullFound = true;
			} else if (!nullFound) {
				FactorMap factors = Helper.uncheckedCast(facts.clone());
				if (added.contains(answerId)) {
					throw new InvalidResponseException("Duplicate selection");
				}
				added.add(answerId);
				Statement s = statementDao.getStatement(answerId);
				if (s == null) {
					throw new ResourceNotFoundException("statement", answerId);
				}
				Answer ans = new Answer(q, s);
				if (!factors.contains(FactorType.SELECT_TIME)) {
					factors.put(FactorType.SELECT_TIME, new DateTime());
				}
				if (ordered) {
					factors.put(FactorType.POSITION, index);
					factors.put(FactorType.WEIGHT, (double) index
							/ (double) groupSize);
				}
				factors.put(FactorType.GROUP_SIZE, groupSize);
				factors.put(FactorType.OUT_OF, answerCount);
				ans.setFactors(factors.getMap());
				index++;
				if (save) {
					// Save answer
				}
				ret.add(ans);
			} else {
				throw new InvalidResponseException("Missing selection");
			}
		}
		return ret;

	}
}
