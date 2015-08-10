package uk.co.icfuture.mvc.dao;

import java.util.List;

import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Questionnaire;

public interface QuestionnaireDao {
	public Questionnaire saveQuestionnaire(Questionnaire questionnaire);

	public List<Questionnaire> getQuestionnaires(QuestionnaireFilter filter);

	public Questionnaire getQuestionnaire(int questionnaire);

}
