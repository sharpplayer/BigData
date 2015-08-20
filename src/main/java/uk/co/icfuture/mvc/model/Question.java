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
	@Column(name = "questionId", unique = true)
	private int questionId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pk.question", orphanRemoval = true)
	@OrderColumn(name = "ordering")
	private List<QuestionStatement> questionStatements = new ArrayList<QuestionStatement>();

	@NotEmpty
	@Column(name = "description", unique = true)
	private String description = "";

	@Transient
	private ArrayList<String> answers = null;

	@Transient
	private ArrayList<String> answerRenderHints = null;

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
		getAnswerRenderHints();
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

	@Transient
	public String getQuestionRenderHints() {
		if (this.questionStatements.size() == 0) {
			this.questionStatements.add(new QuestionStatement(this));
		}
		return this.questionStatements.get(0).getRenderHints();
	}

	public void setQuestionRenderHints(String hint) {
		if (this.questionStatements.size() == 0) {
			getQuestion();
		}
		this.questionStatements.get(0).setRenderHints(hint);
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

	public List<String> getAnswerRenderHints() {
		if (answerRenderHints == null) {
			answerRenderHints = new ArrayList<String>();
			boolean question = true;
			if (this.questionStatements.size() < 2) {
				getQuestion();
				this.questionStatements.add(new QuestionStatement(this));
			}
			for (QuestionStatement s : this.questionStatements) {
				if (!question) {
					this.answerRenderHints.add(s.getRenderHints());
				}
				question = false;
			}
		}
		return answerRenderHints;
	}

	public void copyAnswers() {
		if (this.answers != null) {
			ArrayList<QuestionStatement> all = new ArrayList<QuestionStatement>();
			all.add(this.questionStatements.get(0));
			int index = 0;
			int renderHintIndex = 0;
			for (String s : this.answers) {
				if (s != null) {
					if (!s.isEmpty()) {
						QuestionStatement qs = null;
						for (QuestionStatement qs1 : this.questionStatements) {
							if (qs1.getStatement().getStatement().equals(s)) {
								qs = qs1;
								break;
							}
						}
						if (qs == null) {
							/*if (this.questionStatements.size() > index + 1) {
								qs = this.questionStatements.get(index + 1);
								if (!qs.getStatement().getStatement().equals(s)) {
									qs.setStatement(new Statement(s));
								}
							} else {*/
								qs = new QuestionStatement(this, s);
							//}
						}
						qs.setRenderHints(this.answerRenderHints
								.get(renderHintIndex));
						all.add(qs);
						index++;
					}
				}
				renderHintIndex++;
			}
			Helper.mergeCollection(this.questionStatements, all, true);
			this.answers = null;
			this.answerRenderHints = null;
		}
	}

	public Question merge(Question question) {
		setDescription(question.getDescription());
		setQuestion(question.getQuestion());
		setQuestionRenderHints(question.getQuestionRenderHints());
		getAnswers().clear();
		getAnswers().addAll(question.getAnswers());
		getAnswerRenderHints().clear();
		getAnswerRenderHints().addAll(question.getAnswerRenderHints());
		return this;
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
}
