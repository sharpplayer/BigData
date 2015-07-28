package uk.co.icfuture.mvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import uk.co.icfuture.mvc.utils.Helper;

@Entity
@Table(name = "tblquestion")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(cascade = CascadeType.ALL)
	private Statement question;

	@ManyToMany(cascade = CascadeType.ALL)
	@OrderColumn(name = "order")
	private List<Statement> answers = new ArrayList<Statement>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Statement getQuestion() {
		return question;
	}

	public void setQuestion(Statement question) {
		this.question = question;
	}

	public List<Statement> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Statement> answers) {
		Helper.mergeCollection(this.answers, answers, false);
	}
}
