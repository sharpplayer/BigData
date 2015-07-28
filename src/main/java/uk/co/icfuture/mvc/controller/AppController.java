package uk.co.icfuture.mvc.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import uk.co.icfuture.mvc.form.StatementForm;
import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;
import uk.co.icfuture.mvc.service.StatementService;

@Controller
@RequestMapping("/")
@SessionAttributes("myFilter")
public class AppController {

	private final StatementService statementService;

	@Autowired
	public AppController(StatementService statementService) {
		this.statementService = statementService;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String indexAction(ModelMap model) {
		return "home";
	}

	@RequestMapping(value = { "/html/statements" }, method = RequestMethod.GET)
	public String statementsAction(ModelMap model) {
		return statementsAction(0, model);
	}

	@RequestMapping(value = { "/html/statements/{id}" }, method = RequestMethod.GET)
	public String statementsAction(@PathVariable("id") int id, ModelMap model) {
		populateStatementModel(id, null, model);
		return "statements";
	}

	@RequestMapping(value = { "/html/statements" }, method = RequestMethod.POST)
	public String statementsPostAction(
			@RequestParam(value = "filter", required = false) String filter,
			@Valid StatementForm statementForm, BindingResult resultStatement,
			RedirectAttributes model) {

		Statement statement = statementForm.getObject();
		if (shouldSaveStatement(statement, filter != null, true)) {
			if (resultStatement.hasErrors()) {
				return "statements";
			}
			statement = statementService.saveStatement(statementForm
					.getObject());
		}

		if (model instanceof ModelMap) {
			populateStatementModel(statement, statementForm.getFilter(),
					(ModelMap) model);
		}

		model.addAttribute("id", statement.getId());

		return "redirect:/html/statements/{id}";

	}

	@RequestMapping(value = { "/html/statements/{id}" }, method = RequestMethod.POST)
	public String statementsPostAction(@PathVariable("id") int id,
			@RequestParam(value = "filter", required = false) String filter,
			@Valid StatementForm statementForm, BindingResult resultStatement,
			ModelMap model) {

		Statement statement = statementForm.getObject();
		if (shouldSaveStatement(statement, filter != null, id != 0)) {
			if (resultStatement.hasErrors()) {
				return "statements";
			}
			statement = statementService.saveStatementWithId(statement, id);
		}

		populateStatementModel(statement, statementForm.getFilter(), model);

		return "statements";
	}

	private void populateStatementModel(int id, StatementFilter filter,
			ModelMap model) {
		populateStatementModel(statementService.getStatement(id), filter, model);
	}

	private void populateStatementModel(Statement statement,
			StatementFilter filter, ModelMap model) {
		if (filter == null) {
			if (model.containsAttribute("myFilter")) {
				filter = (StatementFilter) model.get("myFilter");
				System.out.println("Filter from model:"
						+ filter.getFilterText() + ":");
			} else {
				System.out.println("New filter");
				filter = new StatementFilter();
			}
		} else {
			System.out.println("Filter received:" + filter.getFilterText()
					+ ":");
		}

		List<Statement> statements = statementService.getStatements(filter);
		if (model instanceof RedirectAttributes) {
			RedirectAttributes ra = (RedirectAttributes) model;
			ra.addFlashAttribute("myFilter", filter);
			ra.addFlashAttribute("statementForm", new StatementForm(statement,
					filter));
			ra.addFlashAttribute("statements", statements);
		} else {
			model.addAttribute("myFilter", filter);
			model.addAttribute("statementForm", new StatementForm(statement,
					filter));
			model.addAttribute("statements", statements);
		}
	}

	private boolean shouldSaveStatement(Statement statement, boolean filter,
			boolean newStatement) {
		boolean save = true;
		if (filter) {
			// Don't save if not updated nor new item created
			if (newStatement && statement.getStatement().isEmpty()) {
				save = false;
			}
		}

		return save;
	}
}
