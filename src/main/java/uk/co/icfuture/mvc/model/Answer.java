package uk.co.icfuture.mvc.model;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Table;

@Entity
@Table(name = "tblanswer")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answerId")
	private int answerId;

	@Column(name = "questionId")
	private Question question;

	@Column(name = "statementId")
	private Statement selection;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@MapKey(name = "factorType")
	private Map<FactorType, Factor> factors;

	public Answer() {
	}

	public Answer(Question q, Statement selection) {
		question = q;
		this.selection = selection;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public Statement getSelection() {
		return selection;
	}

	public void setSelection(Statement selection) {
		this.selection = selection;
	}

	public Map<FactorType, Factor> getFactors() {
		return factors;
	}

	public void setFactors(Map<FactorType, Factor> factors) {
		this.factors = factors;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

}
