package uk.co.icfuture.mvc.form.filter;

public class QuestionnaireFilter implements Filter {

	private String filterText = "";

	private QuestionFilter questionFilter = new QuestionFilter();

	private int selectedQuestion = 0;
	
	@Override
	public String getFilterText() {
		return this.filterText;
	}

	@Override
	public void setFilterText(String text) {
		this.filterText = text;
	}

	@Override
	public boolean isEmpty() {
		return this.filterText.isEmpty();
	}

	public QuestionFilter getQuestionFilter() {
		return this.questionFilter;
	}

	public int getSelectedQuestion() {
		return selectedQuestion;
	}

	public void setSelectedQuestion(int selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}

}
