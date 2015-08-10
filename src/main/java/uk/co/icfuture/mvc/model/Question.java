package uk.co.icfuture.mvc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import uk.co.icfuture.mvc.utils.Helper;

@Entity
@Table(name = "tblquestion")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@OrderColumn(name = "ordering")
	private List<Statement> statements = new ArrayList<Statement>();

	@Transient
	private ArrayList<String> answers = null;

	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Question(String question) {
		statements.add(new Statement(question));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Statement> getStatements() {
		return statements;
	}

	public void setStatements(List<Statement> statements) {
		Helper.mergeCollection(this.statements, statements, false);
		getAnswers();
	}

	@Transient
	public String getQuestion() {
		if (this.statements.size() == 0) {
			this.statements.add(new Statement());
		}
		return this.statements.get(0).getStatement();
	}

	public void setQuestion(String question) {
		if (this.statements.size() > 0) {
			if (!this.statements.get(0).getStatement().equals(question)) {
				this.statements.set(0, new Statement(question));
			}
		} else {
			this.statements.add(new Statement(question));
		}
	}

	public List<String> getAnswers() {
		if (answers == null) {
			answers = new ArrayList<String>();
			boolean question = true;
			if (this.statements.size() < 2) {
				getQuestion();
				this.statements.add(new Statement());
			}
			for (Statement s : this.statements) {
				if (!question) {
					this.answers.add(s.getStatement());
				}
				question = false;
			}
		}
		return answers;
	}

	public void copyAnswers() {
		ArrayList<Statement> all = new ArrayList<Statement>();
		all.add(this.statements.get(0));
		for (String s : this.answers) {
			if (s != null) {
				if (!s.isEmpty()) {
					all.add(new Statement(s));
				}
			}
		}
		Helper.mergeCollection(this.statements, all, true);
		this.answers = null;
	}
}
