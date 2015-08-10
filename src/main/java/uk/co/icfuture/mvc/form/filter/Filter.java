package uk.co.icfuture.mvc.form.filter;

public interface Filter {
	public String getFilterText();

	public boolean isEmpty();
	
	public void setFilterText(String text);

}
