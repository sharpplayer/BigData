package uk.co.icfuture.mvc.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tblquestion_tblstatement")
@AssociationOverrides({
		@AssociationOverride(name = "pk.question", joinColumns = @JoinColumn(name = "questionId")),
		@AssociationOverride(name = "pk.statement", joinColumns = @JoinColumn(name = "statementId")) })
public class QuestionStatement implements Serializable {

	private static final long serialVersionUID = 6771110284644719847L;

	@EmbeddedId
	private QuestionStatementId pk = new QuestionStatementId();

	@Column(name = "renderHints")
	private String renderHints = "";

	public QuestionStatement() {
	}

	public QuestionStatement(Question question) {
		this(question, "", "");
	}

	public QuestionStatement(Question question, String statement) {
		this(question, statement, "");
	}

	public QuestionStatement(Question question, String statement,
			String renderHints) {
		this(question, new Statement(statement), renderHints);
	}

	public QuestionStatement(Question question, Statement statement) {
		this(question, statement, "");
	}

	public QuestionStatement(Question question, Statement statement,
			String renderHints) {
		setQuestion(question);
		setStatement(statement);
		setRenderHints(renderHints);
	}

	public QuestionStatementId getPk() {
		return pk;
	}

	public void setPk(QuestionStatementId pk) {
		this.pk = pk;
	}

	public String getRenderHints() {
		return renderHints;
	}

	public void setRenderHints(String renderHints) {
		this.renderHints = renderHints;
	}

	@Transient
	public Question getQuestion() {
		return getPk().getQuestion();
	}

	public void setQuestion(Question question) {
		getPk().setQuestion(question);
	}

	@Transient
	public Statement getStatement() {
		return getPk().getStatement();
	}

	public void setStatement(Statement statement) {
		getPk().setStatement(statement);
	}

	@Override
	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		QuestionStatement qs = (QuestionStatement) obj;
		if (getPk() != null ? !getPk().equals(qs.getPk()) : qs.getPk() != null) {
			return false;
		}

		return true;
	}

}
