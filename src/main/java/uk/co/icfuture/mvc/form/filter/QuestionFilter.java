package uk.co.icfuture.mvc.form.filter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class QuestionFilter implements Filter, Serializable {

	private static final long serialVersionUID = 578871347869090794L;

	public static final List<String> PREVIEW_METHODS = Arrays.asList("single",
			"multiple");

	private String filterText = "";

	private String descriptionFilterText = "";

	private int selectedAnswer;

	private String previewMethod = "";

	private StatementFilter statementFilter = new StatementFilter();

	public String getFilterText() {
		return this.filterText;
	}

	public void setFilterText(String text) {
		this.filterText = text;
	}

	public boolean isEmpty() {
		return this.filterText.isEmpty()
				&& this.descriptionFilterText.isEmpty();
	}

	public StatementFilter getStatementFilter() {
		return statementFilter;
	}

	public void setStatementFilter(StatementFilter statementFilter) {
		this.statementFilter = statementFilter;
	}

	public int getSelectedAnswer() {
		return this.selectedAnswer;
	}

	public void setSelectedAnswer(int answer) {
		this.selectedAnswer = answer;
	}

	public String getPreviewMethod() {
		if (previewMethod.isEmpty()) {
			previewMethod = PREVIEW_METHODS.get(0);
		}
		return previewMethod;
	}

	public void setPreviewMethod(String previewMethod) {
		this.previewMethod = previewMethod;
	}

	public String getDescriptionFilterText() {
		return descriptionFilterText;
	}

	public void setDescriptionFilterText(String descriptionFilterText) {
		this.descriptionFilterText = descriptionFilterText;
	}
}
