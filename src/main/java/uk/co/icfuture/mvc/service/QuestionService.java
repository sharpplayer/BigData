package uk.co.icfuture.mvc.service;

import java.util.List;
import java.util.Set;

import uk.co.icfuture.mvc.exception.InvalidFactorValueException;
import uk.co.icfuture.mvc.exception.InvalidResponseException;
import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Answer;
import uk.co.icfuture.mvc.model.FactorMap;
import uk.co.icfuture.mvc.model.Question;

public interface QuestionService {
	public Question saveQuestion(Question question, boolean fromAnswers)
			throws ResourceNotFoundException;

	public Question saveQuestionWithId(Question question, int id,
			boolean fromAnswers) throws ResourceNotFoundException;

	public List<Question> getQuestions(QuestionFilter filter);

	public Question getQuestion(int id) throws ResourceNotFoundException;

	public Question insertStatement(Question question, int index,
			int statementId);

	public boolean shouldSaveQuestion(Question question,
			boolean nonUpdateSubmit, int insertAt, boolean newQuestion);

	public Answer addAnswer(boolean save, int questionId, int answerId,
			FactorMap factors) throws ResourceNotFoundException,
			InvalidFactorValueException, InvalidResponseException;

	public List<Answer> addAnswers(boolean save, int questionId,
			Set<Integer> answerId, FactorMap factors)
			throws ResourceNotFoundException, InvalidFactorValueException,
			InvalidResponseException;

	public List<Answer> addAnswers(boolean save, int questionId,
			List<Integer> answerId, FactorMap factors)
			throws ResourceNotFoundException, InvalidFactorValueException,
			InvalidResponseException;

}
