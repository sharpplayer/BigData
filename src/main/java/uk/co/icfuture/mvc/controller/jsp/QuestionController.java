package uk.co.icfuture.mvc.controller.jsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.co.icfuture.mvc.exception.InvalidFactorValueException;
import uk.co.icfuture.mvc.exception.InvalidResponseException;
import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.form.QuestionForm;
import uk.co.icfuture.mvc.form.filter.QuestionFilter;
import uk.co.icfuture.mvc.model.Answer;
import uk.co.icfuture.mvc.model.FactorMap;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.Statement;
import uk.co.icfuture.mvc.service.QuestionService;
import uk.co.icfuture.mvc.service.StatementService;

@Controller
@SessionAttributes("questionFilter")
public class QuestionController {

	private final QuestionService questionService;

	private final StatementService statementService;

	private final SmartValidator validator;

	@Autowired
	public QuestionController(QuestionService questionService,
			StatementService statementService, SmartValidator validator) {
		this.questionService = questionService;
		this.statementService = statementService;
		this.validator = validator;
	}

	@RequestMapping(value = { "/admin/questions" }, method = RequestMethod.GET)
	public String questionsAction(ModelMap model)
			throws ResourceNotFoundException {
		return questionsAction(0, model);
	}

	@RequestMapping(value = { "/admin/questions/{id}" }, method = RequestMethod.GET)
	public String questionsAction(@PathVariable("id") int id, ModelMap model)
			throws ResourceNotFoundException {
		populateQuestionModel(questionService.getQuestion(id), null, model,
				null);
		return "questions";
	}

	@RequestMapping(value = { "/admin/questions" }, method = RequestMethod.POST)
	public String questionsPostAction(QuestionForm questionForm,
			BindingResult resultQuestion, ModelMap model,
			RedirectAttributes redirect,
			@RequestParam Map<String, String> request) {
		return commonPost(0, questionForm, resultQuestion, model, request,
				redirect);
	}

	@RequestMapping(value = { "/admin/questions/{id}" }, method = RequestMethod.POST)
	public String questionsPostAction(@PathVariable("id") int id,
			QuestionForm questionForm, BindingResult resultQuestion,
			ModelMap model, RedirectAttributes redirect,
			@RequestParam Map<String, String> request) {
		return commonPost(id, questionForm, resultQuestion, model, request,
				redirect);
	}

	@RequestMapping(value = { "/preview/questions/{id}/{method}" }, method = RequestMethod.GET)
	public String questionsPreviewAction(@PathVariable("id") int id,
			@PathVariable("method") String method, ModelMap model)
			throws ResourceNotFoundException {
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		if (!QuestionFilter.PREVIEW_METHODS.contains(method)) {
			method = QuestionFilter.PREVIEW_METHODS.get(0);
		}
		model.put("question", question);
		model.put("answers", new ArrayList<Answer>());
		return method;
	}

	@RequestMapping(value = { "/preview/questions/{id}/{method}" }, method = RequestMethod.POST)
	public String questionsPreviewPostAction(@PathVariable("id") int id,
			@PathVariable("method") String method, ModelMap model,
			@RequestParam Map<String, String> request)
			throws ResourceNotFoundException, NumberFormatException,
			InvalidFactorValueException {
		String ret = questionsPreviewAction(id, method, model);
		List<Answer> answers = new ArrayList<Answer>();
		FactorMap factor = new FactorMap();
		model.put("error", "");
		List<Integer> selectionList = new ArrayList<Integer>();
		try {
			if (method.equals("single")) {
				if (request.containsKey("response")) {
					answers.add(questionService.addAnswer(false, id,
							Integer.parseInt(request.get("response")), factor));
				}
			} else if (method.equals("multiple")) {
				Set<Integer> selections = new HashSet<Integer>();
				for (Entry<String, String> entry : request.entrySet()) {
					if (entry.getKey().startsWith("response[")) {
						selections.add(Integer.parseInt(entry.getValue()));
					}
				}
				answers.addAll(questionService.addAnswers(false, id,
						selections, factor));
			} 	else if (method.equals("ordered")) {
				for (Entry<String, String> entry : request.entrySet()) {
					if (entry.getKey().startsWith("response[")) {
						String index = entry.getKey().substring(9,
								entry.getKey().length() - 1);
						if (!entry.getValue().equals("0")) {
							int ind = Integer.parseInt(index);
							if (ind >= selectionList.size()) {
								selectionList.addAll(Collections.nCopies(
										10 * (1 + (ind / 10))
												- selectionList.size(), null));
							}
							selectionList.set(Integer.parseInt(index),
									Integer.parseInt(entry.getValue()));
						}
					}
				}
				if (selectionList.size() > 0) {
					while (selectionList.get(selectionList.size() - 1) == null) {
						selectionList.remove(selectionList.size() - 1);
					}
				}
				answers.addAll(questionService.addAnswers(false, id,
						selectionList, factor));
			}
		} catch (InvalidResponseException ex) {
			model.put("response", selectionList);
			model.put("error", ex.getMessage());
		}
		model.put("answers", answers);
		return ret;
	}

	private String commonPost(int id, QuestionForm questionForm,
			BindingResult resultQuestion, ModelMap model,
			Map<String, String> request, RedirectAttributes redirect) {
		Question question = questionForm.getObject();

		int insert = 0;
		int insertAt = -1;
		for (String att : request.keySet()) {
			if (att.startsWith("insert")) {
				insert = Integer.parseInt(att.substring(6));
				insertAt = Integer.parseInt(request
						.get("filter.selectedAnswer"));
			}
		}

		boolean save = questionService
				.shouldSaveQuestion(question, request.containsKey("filter")
						|| request.containsKey("preview"), insertAt, true);

		if (insert != 0) {
			question = questionService.insertStatement(
					questionForm.getObject(), insertAt, insert);
			questionForm.getFilter().setSelectedAnswer(insertAt + 1);
		}
		if (save) {
			validator.validate(questionForm, resultQuestion);
			if (!resultQuestion.hasErrors()) {
				try {
					question = questionService.saveQuestionWithId(
							questionForm.getObject(), id, insert == 0);
				} catch (ResourceNotFoundException e) {
					resultQuestion.rejectValue("object.answers[" + e.getIndex()
							+ "]", "answer.notfound", e.getMessage());

				}
			}
		}

		populateQuestionModel(question, questionForm, model, redirect);
		if (save && resultQuestion.hasErrors()) {
			return "questions";
		} else if (request.containsKey("preview")
				&& question.getQuestionId() != 0) {
			redirect.addAttribute("id", question.getQuestionId());
			redirect.addAttribute("method", questionForm.getFilter()
					.getPreviewMethod());
			return "redirect:/preview/questions/{id}/{method}";
		} else if (id != question.getQuestionId()) {
			redirect.addAttribute("id", question.getQuestionId());
			return "redirect:/admin/questions/{id}";
		} else {
			return "questions";
		}

	}

	private void populateQuestionModel(Question question, QuestionForm form,
			ModelMap model, RedirectAttributes redirect) {
		QuestionFilter filter = null;
		if (form == null) {
			if (model.containsAttribute("questionFilter")) {
				filter = (QuestionFilter) model.get("questionFilter");
			} else {
				filter = new QuestionFilter();
			}
			form = new QuestionForm(question, filter);
			filter.setSelectedAnswer(question.getAnswers().size() + 1);
		} else {
			filter = form.getFilter();
			form.setObject(question);
		}
		if (question.getQuestion().isEmpty()) {
			filter.setSelectedAnswer(0);
		}

		List<Question> questions = questionService.getQuestions(filter);
		List<Statement> statements = statementService.getStatements(filter
				.getStatementFilter());
		if (redirect != null) {
			redirect.addFlashAttribute("questionFilter", filter);
		}

		model.addAttribute("questionFilter", filter);
		model.addAttribute("questionForm", form);
		model.addAttribute("questions", questions);
		model.addAttribute("statements", statements);
		model.addAttribute("previewList", QuestionFilter.PREVIEW_METHODS);
	}
}
