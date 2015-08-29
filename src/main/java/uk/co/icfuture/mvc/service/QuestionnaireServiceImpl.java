package uk.co.icfuture.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.co.icfuture.mvc.dao.QuestionDao;
import uk.co.icfuture.mvc.dao.QuestionnaireDao;
import uk.co.icfuture.mvc.dao.StatementDao;
import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
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
			boolean fromQText) throws ResourceNotFoundException {
		if (fromQText) {
			questionnaire.copyQuestionText();
		}

		for (Question q : questionnaire.getQuestions()) {
			q.setQuestionStatements(statementDao.findStatements(
					q.getQuestionStatements(), false));
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
			List<Questionnaire> ret = questionnaireDao
					.getQuestionnaires(filter);
			for (Questionnaire q : ret) {
				Hibernate.initialize(q.getQuestions());
			}
			return ret;
		}
	}

	@Override
	public Questionnaire getQuestionnaire(int id) throws ResourceNotFoundException {
		if (id == 0) {
			return new Questionnaire();
		} else {
			Questionnaire questionnaire = questionnaireDao.getQuestionnaire(id);
			if (questionnaire == null) {
				throw new ResourceNotFoundException("questionnaire", id);
			} else {
				Hibernate.initialize(questionnaire.getQuestions());
				return questionnaire;
			}
		}
	}

	@Override
	public Questionnaire saveQuestionnaireWithId(Questionnaire questionnaire,
			int id, boolean fromQText) throws ResourceNotFoundException {
		if (questionnaire.getQuestionnaireId() == 0 && id != 0) {
			questionnaire = questionnaireDao.getQuestionnaire(id).merge(
					questionnaire);
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

	@Override
	public boolean shouldSaveQuestionnaire(Questionnaire questionnaire,
			boolean nonUpdateSubmit, int insertAt, boolean newQuestionnaire) {
		boolean save = true;
		Hibernate.initialize(questionnaire.getQuestions());
		if (nonUpdateSubmit) {
			// Don't save if not updated nor new item created
			if (newQuestionnaire
					&& (questionnaire.getDescription().isEmpty() || questionnaire
							.getQuestionText().get(0).isEmpty())) {
				save = false;
			}
		} else if (insertAt != -1) {
			save = !questionnaire.getDescription().isEmpty();
		}
		return save;
	}

	@Override
	public Question getNextQuestion(int id) {
		return null;
	}

}
