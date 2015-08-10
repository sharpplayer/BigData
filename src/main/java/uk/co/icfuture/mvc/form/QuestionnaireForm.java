package uk.co.icfuture.mvc.form;

import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Questionnaire;

public class QuestionnaireForm extends
		FilterForm<Questionnaire, QuestionnaireFilter> {

	public QuestionnaireForm() {
		this(new Questionnaire(), new QuestionnaireFilter());
	}

	public QuestionnaireForm(Questionnaire object, QuestionnaireFilter filter) {
		super(object, filter);
	}

	public int getSlots() {
		return 10 * (1 + getObject().getQuestions().size() / 10) - 1;
	}
}
