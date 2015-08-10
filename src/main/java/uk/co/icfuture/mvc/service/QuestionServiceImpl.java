package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.icfuture.mvc.dao.QuestionDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Question;

@Service("questionService")
@Transactional
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private StatementDao statementDao;

	public Question saveQuestion(Question question, boolean fromAnswers) throws ItemNotFoundException {
		if (fromAnswers) {
			question.copyAnswers();
		}
		question.setStatements(statementDao.findStatements(
				question.getStatements(), true));
		return questionDao.saveQuestion(question);
	}

	public Question saveQuestionWithId(Question question, int id,
			boolean fromAnswers) throws ItemNotFoundException {
		if (question.getId() == 0 && id != 0) {
			question.setId(id);
		}
		return saveQuestion(question, fromAnswers);
	}

	public List<Question> getQuestions(QuestionFilter filter) {
		if (filter.isEmpty()) {
			return new ArrayList<Question>();
		} else {
			return questionDao.getQuestions(filter);
		}
	}

	public Question getQuestion(int id) throws ItemNotFoundException {
		if (id == 0) {
			return new Question();
		} else {
			Question question = questionDao.getQuestion(id);
			if (question == null) {
				throw new ItemNotFoundException("question", id);
			} else {
				return question;
			}
		}
	}

	public Question insertStatement(Question question, int index,
			int statementId) {
		question.copyAnswers();
		if (index >= question.getStatements().size()) {
			question.getStatements()
					.add(statementDao.getStatement(statementId));
		} else {
			if (question.getStatements().get(index).isEmpty()) {
				question.getStatements().remove(index);
			}
			question.getStatements().add(index,
					statementDao.getStatement(statementId));
		}
		return question;
	}

}
