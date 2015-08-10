package uk.co.icfuture.mvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Questionnaire;

@Repository("questionnaireDao")
public class QuestionnaireDaoImpl extends AbstractDao<Questionnaire> implements
		QuestionnaireDao {

	public Questionnaire saveQuestionnaire(Questionnaire questionnaire) {
		if (questionnaire.getId() == 0) {
			return persist(questionnaire);
		} else {
			return update(questionnaire);
		}
	}

	public List<Questionnaire> getQuestionnaires(QuestionnaireFilter filter) {
		return getFilteredList("description", "%" + filter.getFilterText()
				+ "%");
	}

	public Questionnaire getQuestionnaire(int id) {
		return getItemById(id);
	}

}
