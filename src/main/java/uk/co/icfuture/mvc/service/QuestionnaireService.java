package uk.co.icfuture.mvc.service;

import java.util.List;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.Questionnaire;

public interface QuestionnaireService {

	public Questionnaire saveQuestionnaire(Questionnaire question,
			boolean fromQText) throws ResourceNotFoundException;

	public Questionnaire saveQuestionnaireWithId(Questionnaire questionnaire,
			int id, boolean fromQText) throws ResourceNotFoundException;

	public List<Questionnaire> getQuestionnaires(QuestionnaireFilter filter);

	public Questionnaire getQuestionnaire(int id) throws ResourceNotFoundException;

	public Questionnaire insertQuestion(Questionnaire questionnaire, int index,
			int questionId);

	public boolean shouldSaveQuestionnaire(Questionnaire questionnaire,
			boolean nonUpdateSubmit, int insertAt, boolean newQuestionnaire);

	public Question getNextQuestion(int id);

}
