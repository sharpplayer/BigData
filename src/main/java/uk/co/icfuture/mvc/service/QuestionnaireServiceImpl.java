package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.icfuture.mvc.dao.QuestionDao;
import uk.co.icfuture.mvc.dao.QuestionnaireDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.Questionnaire;

@Service("questionnaireService")
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Autowired
	private QuestionnaireDao questionnaireDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private StatementDao statementDao;

	@Override
	public Questionnaire saveQuestionnaire(Questionnaire questionnaire,
			boolean fromQText) throws ItemNotFoundException {
		if (fromQText) {
			questionnaire.copyQuestionText();
		}

		for (Question q : questionnaire.getQuestions()) {
			q.setStatements(statementDao.findStatements(q.getStatements(),
					false));
		}
		questionnaire.setQuestions(questionDao.findQuestions(questionnaire
				.getQuestions()));
		return questionnaireDao.saveQuestionnaire(questionnaire);
	}

	@Override
	public List<Questionnaire> getQuestionnaires(QuestionnaireFilter filter) {
		if (filter.isEmpty()) {
			return new ArrayList<Questionnaire>();
		} else {
			return questionnaireDao.getQuestionnaires(filter);
		}
	}

	@Override
	public Questionnaire getQuestionnaire(int id) throws ItemNotFoundException {
		if (id == 0) {
			return new Questionnaire();
		} else {
			Questionnaire questionnaire = questionnaireDao.getQuestionnaire(id);
			if (questionnaire == null) {
				throw new ItemNotFoundException("questionnaire", id);
			} else {
				return questionnaire;
			}
		}
	}

	@Override
	public Questionnaire saveQuestionnaireWithId(Questionnaire questionnaire,
			int id, boolean fromQText) throws ItemNotFoundException {
		if (questionnaire.getId() == 0 && id != 0) {
			questionnaire.setId(id);
		}
		return saveQuestionnaire(questionnaire, fromQText);
	}

	@Override
	public Questionnaire insertQuestion(Questionnaire questionnaire, int index,
			int questionId) {
		questionnaire.copyQuestionText();
		if (index >= questionnaire.getQuestions().size()) {
			questionnaire.getQuestions().add(
					questionDao.getQuestion(questionId));
		} else {
			questionnaire.getQuestions().add(index,
					questionDao.getQuestion(questionId));
		}
		return questionnaire;
	}

}
