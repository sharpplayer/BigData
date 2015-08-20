package uk.co.icfuture.mvc.service;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.model.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/resources/servlet-context-test.xml")
public class QuestionServiceIntegrationTest extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	private QuestionService questionService;

	private Question getQuestion(boolean twoAnswers) {
		Question q = new Question();
		q.setQuestion("Favourite colour?");
		q.getAnswers().clear();
		q.getAnswers().add("Green");
		if (twoAnswers) {
			q.getAnswers().add("Red");
		}
		q.setQuestionRenderHints("a");
		q.getAnswerRenderHints().add("b");
		q.getAnswerRenderHints().add("c");
		return q;
	}

	@Test
	@Ignore
	public void saveQuestionWithIdTest() throws ItemNotFoundException {
		int id = 1;
		questionService.saveQuestionWithId(getQuestion(false), id, true);
	}

	@Test
	@Ignore
	public void saveQuestionWithIdRemoveTest() throws ItemNotFoundException {
		int id = 1;
		Question q = getQuestion(true);
		questionService.saveQuestionWithId(q, id, true);
		q.getAnswers().remove(1);
		q.getAnswerRenderHints().remove(1);
		questionService.saveQuestionWithId(q, id, true);

		q = questionService.getQuestion(id);
		assertEquals("Failed to remove answer statement", 1, q.getAnswers()
				.size());
	}

	@Test
	@Ignore
	public void appendStatementAndSaveTest() throws ItemNotFoundException {
		int id = 1;
		Question q = getQuestion(false);
		q = questionService.insertStatement(q, 2, 2);
		q = questionService.saveQuestionWithId(q, id, false);
		q = questionService.getQuestion(id);
		assertEquals("Failed to insert answer statement", 2, q.getAnswers()
				.size());
	}

	@Test
	public void insertStatementAndSaveTest() throws ItemNotFoundException {
		int id = 1;
		Question q = getQuestion(false);
		q = questionService.insertStatement(q, 1, 2);
		q = questionService.saveQuestionWithId(q, id, false);
		q = questionService.getQuestion(id);
		assertEquals("Failed to insert answer statement", 2, q.getAnswers()
				.size());
	}

}
