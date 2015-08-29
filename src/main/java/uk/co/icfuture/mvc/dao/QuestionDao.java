package uk.co.icfuture.mvc.dao;

import java.util.List;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.model.Question;

public interface QuestionDao {
	public Question saveQuestion(Question question);

	public List<Question> getQuestionsByStatement(String filter);

	public List<Question> getQuestions(String filter);

	public Question getQuestion(int question);

	public List<Question> findQuestions(List<Question> questions)
			throws ResourceNotFoundException;

}
