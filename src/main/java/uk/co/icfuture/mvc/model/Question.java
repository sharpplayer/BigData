package uk.co.icfuture.mvc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import uk.co.icfuture.mvc.utils.Helper;

@Entity
@Table(name = "tblquestion")
public class Question implements Serializable {

	private static final long serialVersionUID = -2607205552884442638L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pk.question")
	@OrderColumn(name = "ordering")
	private List<QuestionStatement> questionStatements = new ArrayList<QuestionStatement>();

	@NotEmpty
	@Column(unique = true)
	private String description;

	@Transient
	private ArrayList<String> answers = null;

	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Question(String question) {
		questionStatements.add(new QuestionStatement(this, question));
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public List<QuestionStatement> getQuestionStatements() {
		return questionStatements;
	}

	public void setQuestionStatements(List<QuestionStatement> questionStatements) {
		Helper.mergeCollection(this.questionStatements, questionStatements,
				false);
		getAnswers();
	}

	@Transient
	public String getQuestion() {
		if (this.questionStatements.size() == 0) {
			this.questionStatements.add(new QuestionStatement(this));
		}
		return this.questionStatements.get(0).getStatement().getStatement();
	}

	public void setQuestion(String question) {
		if (this.questionStatements.size() > 0) {
			if (!this.questionStatements.get(0).getStatement().getStatement()
					.equals(question)) {
				this.questionStatements.set(0, new QuestionStatement(this,
						question));
			}
		} else {
			this.questionStatements.add(new QuestionStatement(this, question));
		}
		if (description.isEmpty()) {
			description = question;
		}
	}

	public List<String> getAnswers() {
		if (answers == null) {
			answers = new ArrayList<String>();
			boolean question = true;
			if (this.questionStatements.size() < 2) {
				getQuestion();
				this.questionStatements.add(new QuestionStatement(this));
			}
			for (QuestionStatement s : this.questionStatements) {
				if (!question) {
					this.answers.add(s.getStatement().getStatement());
				}
				question = false;
			}
		}
		return answers;
	}

	public void copyAnswers() {
		ArrayList<QuestionStatement> all = new ArrayList<QuestionStatement>();
		all.add(this.questionStatements.get(0));
		for (String s : this.answers) {
			if (s != null) {
				if (!s.isEmpty()) {
					all.add(new QuestionStatement(this, s));
				}
			}
		}
		Helper.mergeCollection(this.questionStatements, all, true);
		this.answers = null;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String getDescriptionText() {
		if (this.description == null || this.description.equals(getQuestion())) {
			return "";
		} else {
			return this.description;
		}
	}

	public void setDescriptionText(String text) {
		this.description = text;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Question) {
			Question q = (Question) obj;
			return q.questionId == this.questionId;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return questionId;
	}
}
