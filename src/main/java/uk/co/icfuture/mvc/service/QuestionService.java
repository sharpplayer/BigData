package uk.co.icfuture.mvc.service;

import java.util.List;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Question;

public interface QuestionService {
	public Question saveQuestion(Question question, boolean fromAnswers) throws ItemNotFoundException;

	public Question saveQuestionWithId(Question question, int id,
			boolean fromAnswers) throws ItemNotFoundException;

	public List<Question> getQuestions(QuestionFilter filter);

	public Question getQuestion(int id) throws ItemNotFoundException;

	public Question insertStatement(Question question, int index,
			int statementId);
}
