package uk.co.icfuture.mvc.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionResource {

	private QuestionService questionService;

	@Autowired
	public QuestionResource(QuestionService qs) {
		this.questionService = qs;
	}

	@RequestMapping(value = { "{id}" }, method = RequestMethod.GET, produces = { "application/json", "application/xml" })
	public Question getQuestion(@PathVariable("id") int id)
			throws NumberFormatException, ResourceNotFoundException {
		return questionService.getQuestion(id);
	}
}
