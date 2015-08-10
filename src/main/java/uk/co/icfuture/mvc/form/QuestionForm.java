package uk.co.icfuture.mvc.form;

import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Question;

public class QuestionForm extends FilterForm<Question, QuestionFilter> {

	public QuestionForm() {
		this(new Question(), new QuestionFilter());
	}

	public QuestionForm(Question object, QuestionFilter filter) {
		super(object, filter);
	}

	public int getSlots() {
		int count = 0;
		for (String s : getObject().getAnswers()) {
			if (!s.isEmpty()) {
				count++;
			}
		}
		return 10 * (1 + count / 10) - 1;
	}
}