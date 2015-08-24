package uk.co.icfuture.mvc.controller.jsp;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uk.co.icfuture.mvc.exception.ItemNotFoundException;
import uk.co.icfuture.mvc.form.StatementForm;
import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;
import uk.co.icfuture.mvc.service.StatementService;

@Controller
@SessionAttributes("statementFilter")
public class StatementController {

	private final StatementService statementService;

	@Autowired
	public StatementController(StatementService statementService) {
		this.statementService = statementService;
	}

	@RequestMapping(value = { "/admin/statements" }, method = RequestMethod.GET)
	public String statementsAction(ModelMap model) throws ItemNotFoundException {
		return statementsAction(0, model);
	}

	@RequestMapping(value = { "/admin/statements/{id}" }, method = RequestMethod.GET)
	public String statementsAction(@PathVariable("id") int id, ModelMap model)
			throws ItemNotFoundException {
		populateStatementModel(statementService.getStatement(id), null, model,
				null);
		return "statements";
	}

	@RequestMapping(value = { "/admin/statements" }, method = RequestMethod.POST)
	public String statementsPostAction(
			@RequestParam(value = "filter", required = false) String filter,
			@Valid StatementForm statementForm, BindingResult resultStatement,
			ModelMap model, RedirectAttributes redirectAtts) {

		Statement statement = statementForm.getObject();
		boolean save = statementService.shouldSaveStatement(statement,
				filter != null, true);
		if (save) {
			if (resultStatement.hasErrors()) {
				return "statements";
			}
			statement = statementService.saveStatement(statementForm
					.getObject());
		}

		populateStatementModel(statement, statementForm, (ModelMap) model,
				save ? redirectAtts : null);

		if (save) {
			redirectAtts.addAttribute("id", statement.getStatementId());
			return "redirect:/admin/statements/{id}";
		} else {
			return "statements";
		}

	}

	@RequestMapping(value = { "/admin/statements/{id}" }, method = RequestMethod.POST)
	public String statementsPostAction(@PathVariable("id") int id,
			@RequestParam(value = "filter", required = false) String filter,
			@Valid StatementForm statementForm, BindingResult resultStatement,
			ModelMap model) {

		Statement statement = statementForm.getObject();
		if (statementService.shouldSaveStatement(statement, filter != null,
				id == 0)) {
			if (resultStatement.hasErrors()) {
				return "statements";
			}
			statement = statementService.saveStatementWithId(statement, id);
		}

		populateStatementModel(statement, statementForm, model, null);

		return "statements";
	}

	private void populateStatementModel(Statement statement,
			StatementForm form, ModelMap model, RedirectAttributes redirect) {
		StatementFilter filter = null;
		if (form == null) {
			form = new StatementForm(statement, new StatementFilter());
			if (model.containsAttribute("statementFilter")) {
				filter = (StatementFilter) model.get("statementFilter");
			} else {
				filter = form.getFilter();
			}
		} else {
			filter = form.getFilter();
			form.setObject(statement);
		}

		List<Statement> statements = statementService.getStatements(filter);
		if (redirect != null) {
			redirect.addFlashAttribute("statementFilter", filter);
		}

		model.addAttribute("statementFilter", filter);
		model.addAttribute("statementForm", form);
		model.addAttribute("statements", statements);

	}
}
