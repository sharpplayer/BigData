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
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import uk.co.icfuture.mvc.utils.Helper;

@Entity
@Table(name = "tblquestionnaire")
public class Questionnaire implements Serializable {

	private static final long serialVersionUID = -7814278571543200515L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "questionnaireId")
	private int questionnaireId;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@OrderColumn(name = "ordering")
	private List<Question> questions = new ArrayList<Question>();

	@NotEmpty
	@Column(name = "description", unique = true)
	private String description = "";

	@Transient
	private ArrayList<String> questionText = null;

	public Questionnaire merge(Questionnaire questionnaire) {
		setDescription(questionnaire.getDescription());
		getQuestionText().clear();
		getQuestionText().addAll(questionnaire.getQuestionText());
		return this;
	}

	public int getQuestionnaireId() {
		return this.questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getQuestionText() {
		if (questionText == null) {
			questionText = new ArrayList<String>();
			for (Question q : this.questions) {
				this.questionText.add(q.getQuestion());
			}
		}
		return questionText;
	}

	public void copyQuestionText() {
		ArrayList<Question> all = new ArrayList<Question>();
		for (String s : this.questionText) {
			if (s != null) {
				if (!s.isEmpty()) {
					all.add(new Question(s));
				}
			}
		}
		Helper.mergeCollection(this.questions, all, true);
		this.questionText = null;
	}
}
