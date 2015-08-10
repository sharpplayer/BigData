package uk.co.icfuture.mvc.service;

import java.util.List;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Questionnaire;

public interface QuestionnaireService {

	public Questionnaire saveQuestionnaire(Questionnaire question,
			boolean fromQText) throws ItemNotFoundException;

	public Questionnaire saveQuestionnaireWithId(Questionnaire questionnaire,
			int id, boolean fromQText) throws ItemNotFoundException;

	public List<Questionnaire> getQuestionnaires(QuestionnaireFilter filter);

	public Questionnaire getQuestionnaire(int id) throws ItemNotFoundException;

	public Questionnaire insertQuestion(Questionnaire questionnaire, int index,
			int questionId);

}
