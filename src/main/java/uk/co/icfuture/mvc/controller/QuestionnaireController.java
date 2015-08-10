package uk.co.icfuture.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.QuestionnaireForm;
import uk.co.icfuture.mvc.form.filter.QuestionnaireFilter;
import uk.co.icfuture.mvc.model.Question;
import uk.co.icfuture.mvc.model.Questionnaire;
import uk.co.icfuture.mvc.service.QuestionService;
import uk.co.icfuture.mvc.service.QuestionnaireService;

@Controller
@SessionAttributes("questionnaireFilter")
public class QuestionnaireController {

	private final QuestionnaireService questionnaireService;

	private final QuestionService questionService;

	private final SmartValidator validator;

	@Autowired
	public QuestionnaireController(QuestionnaireService questionnaireService,
			QuestionService questionService, SmartValidator validator) {
		this.questionnaireService = questionnaireService;
		this.questionService = questionService;
		this.validator = validator;
	}

	@RequestMapping(value = { "/admin/questionnaires" }, method = RequestMethod.GET)
	public String questionnairesAction(ModelMap model)
			throws ItemNotFoundException {
		return questionnairesAction(0, model);
	}

	@RequestMapping(value = { "/admin/questionnaires/{id}" }, method = RequestMethod.GET)
	public String questionnairesAction(@PathVariable("id") int id,
			ModelMap model) throws ItemNotFoundException {
		populateQuestionnaireModel(questionnaireService.getQuestionnaire(id),
				null, model, null);
		return "questionnaires";
	}

	@RequestMapping(value = { "/admin/questionnaires" }, method = RequestMethod.POST)
	public String questionnairesPostAction(
			@ModelAttribute("questionnaireForm") QuestionnaireForm questionnaireForm,
			BindingResult resultQuestionnaire, ModelMap model,
			RedirectAttributes redirect,
			@RequestParam Map<String, String> request) {
		return commonPost(0, questionnaireForm, resultQuestionnaire, model,
				request, redirect);
	}

	@RequestMapping(value = { "/admin/questionnaires/{id}" }, method = RequestMethod.POST)
	public String questionsPostAction(
			@PathVariable("id") int id,
			@ModelAttribute("questionnaireForm") QuestionnaireForm questionnaireForm,
			BindingResult resultQuestionnaire, ModelMap model,
			RedirectAttributes redirect,
			@RequestParam Map<String, String> request) {
		return commonPost(id, questionnaireForm, resultQuestionnaire, model,
				request, redirect);
	}

	private String commonPost(int id, QuestionnaireForm questionnaireForm,
			BindingResult resultQuestionnaire, ModelMap model,
			Map<String, String> request, RedirectAttributes redirect) {
		Questionnaire questionnaire = questionnaireForm.getObject();

		int insert = 0;
		int insertAt = -1;
		for (String att : request.keySet()) {
			if (att.startsWith("insert")) {
				insert = Integer.parseInt(att.substring(6));
				insertAt = Integer.parseInt(request
						.get("filter.selectedQuestion"));
			}
		}

		boolean save = shouldSaveQuestionnaire(questionnaire,
				request.containsKey("filter"), insertAt, true);

		if (insert != 0) {
			questionnaire = questionnaireService.insertQuestion(
					questionnaireForm.getObject(), insertAt, insert);
			questionnaireForm.getFilter().setSelectedQuestion(insertAt + 1);
		}
		if (save) {
			validator.validate(questionnaireForm, resultQuestionnaire);
			if (!resultQuestionnaire.hasErrors()) {
				try {
					questionnaire = questionnaireService
							.saveQuestionnaireWithId(
									questionnaireForm.getObject(), id,
									insert == 0);
				} catch (ItemNotFoundException e) {
					resultQuestionnaire.rejectValue(
							"object.questionText[" + e.getIndex() + "]",
							"question.notfound", e.getMessage());
				}
			}
		}

		populateQuestionnaireModel(questionnaire, questionnaireForm, model,
				redirect);
		if (save && resultQuestionnaire.hasErrors()) {
			return "questionnaires";
		} else if (id != questionnaire.getId()) {
			redirect.addAttribute("id", questionnaire.getId());
			return "redirect:/admin/questionnaires/{id}";
		} else {
			return "questionnaires";
		}

	}

	private void populateQuestionnaireModel(Questionnaire questionnaire,
			QuestionnaireForm form, ModelMap model, RedirectAttributes redirect) {
		QuestionnaireFilter filter = null;
		if (form == null) {
			if (model.containsAttribute("questionnaireFilter")) {
				filter = (QuestionnaireFilter) model.get("questionnaireFilter");
			} else {
				filter = new QuestionnaireFilter();
			}
			form = new QuestionnaireForm(questionnaire, filter);
			filter.setSelectedQuestion(questionnaire.getQuestions().size());
		} else {
			filter = form.getFilter();
			form.setObject(questionnaire);
		}
		if (questionnaire.getQuestions().isEmpty()) {
			filter.setSelectedQuestion(0);
		}

		List<Questionnaire> questionnaires = questionnaireService
				.getQuestionnaires(filter);
		List<Question> questions = questionService.getQuestions(filter
				.getQuestionFilter());
		if (redirect != null) {
			redirect.addFlashAttribute("questionnaireFilter", filter);
		}

		model.addAttribute("questionnaireFilter", filter);
		model.addAttribute("questionnaireForm", form);
		model.addAttribute("questionnaires", questionnaires);
		model.addAttribute("questions", questions);
	}

	private boolean shouldSaveQuestionnaire(Questionnaire questionnaire,
			boolean nonUpdateSubmit, int insertAt, boolean newQuestionnaire) {
		boolean save = true;
		if (nonUpdateSubmit) {
			// Don't save if not updated nor new item created
			if (newQuestionnaire
					&& (questionnaire.getDescription().isEmpty() || questionnaire
							.getQuestionText().get(0).isEmpty())) {
				save = false;
			}
		} else if (insertAt != -1) {
			save = !questionnaire.getDescription().isEmpty();
		}
		return save;
	}

}
