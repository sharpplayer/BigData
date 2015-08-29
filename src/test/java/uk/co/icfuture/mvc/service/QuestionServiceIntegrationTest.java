package uk.co.icfuture.mvc.service;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.oxm.MediaType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.model.ObjectFactory;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.utils.Helper;

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
	public void saveQuestionWithIdTest() throws ResourceNotFoundException {
		int id = 1;
		questionService.saveQuestionWithId(getQuestion(false), id, true);
	}

	@Test
	public void saveQuestionWithIdRemoveTest() throws ResourceNotFoundException {
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
	public void appendStatementAndSaveTest() throws ResourceNotFoundException {
		int id = 1;
		Question q = getQuestion(false);
		q = questionService.insertStatement(q, 2, 2);
		q = questionService.saveQuestionWithId(q, id, false);
		q = questionService.getQuestion(id);
		assertEquals("Failed to insert answer statement", 2, q.getAnswers()
				.size());
	}

	@Test
	public void insertStatementAndSaveTest() throws ResourceNotFoundException {
		int id = 1;
		Question q = getQuestion(false);
		q = questionService.insertStatement(q, 1, 2);
		q = questionService.saveQuestionWithId(q, id, false);
		q = questionService.getQuestion(id);
		assertEquals("Failed to insert answer statement", 2, q.getAnswers()
				.size());
	}

	@Test
	public void xmlSerialization() throws ResourceNotFoundException, JAXBException {
		int id = 1;
		Question q = questionService.getQuestion(id);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		serialize(q, os, MediaType.APPLICATION_XML);
	}

	@Test
	public void xmlDeserialization() throws ResourceNotFoundException,
			JAXBException {

		int id = 1;
		Question q = questionService.getQuestion(id);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		serialize(q, os, MediaType.APPLICATION_XML);

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		JAXBContext jc = getJaxbContext();
		Unmarshaller u = jc.createUnmarshaller();
		q = Helper.uncheckedCast(u.unmarshal(is));
	}

	@Test
	public void jsonSerialization() throws ResourceNotFoundException, JAXBException {
		int id = 1;
		Question q = questionService.getQuestion(id);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		serialize(q, os, MediaType.APPLICATION_JSON);
	}

	@Test
	public void jsonDeserialization() throws ResourceNotFoundException,
			JAXBException {

		int id = 1;
		Question q = questionService.getQuestion(id);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		serialize(q, os, MediaType.APPLICATION_JSON);

		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		JAXBContext jc = getJaxbContext();
		Unmarshaller u = jc.createUnmarshaller();
		u.setProperty(UnmarshallerProperties.MEDIA_TYPE,
				MediaType.APPLICATION_JSON);
		q = Helper.uncheckedCast(u.unmarshal(is));
	}

	private void serialize(Question q, ByteArrayOutputStream os,
			MediaType mediaType) throws JAXBException {
		JAXBContext jc = getJaxbContext();
		JAXBElement<Question> q2 = new JAXBElement<Question>(new QName(
				"question"), Question.class, q);
		Marshaller m = jc.createMarshaller();
		m.setProperty(MarshallerProperties.MEDIA_TYPE, mediaType);
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(q2, os);
	}

	private JAXBContext getJaxbContext() throws JAXBException {
		return JAXBContext.newInstance("uk.co.icfuture.mvc.model",
				ObjectFactory.class.getClassLoader());
	}
}
