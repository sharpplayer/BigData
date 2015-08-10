package uk.co.icfuture.mvc.dao;

import java.util.List;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Question;

public interface QuestionDao {
	public Question saveQuestion(Question question);

	public List<Question> getQuestions(QuestionFilter filter);

	public Question getQuestion(int question);

	public List<Question> findQuestions(List<Question> questions)
			throws ItemNotFoundException;

}