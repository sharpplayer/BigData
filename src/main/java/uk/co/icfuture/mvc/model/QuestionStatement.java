package uk.co.icfuture.mvc.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
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

	private String renderHints = "";

	public QuestionStatement() {
	}

	public QuestionStatement(Question question) {
		this(question, "");
	}

	public QuestionStatement(Question question, String statement) {
		this(question, new Statement(statement));
	}

	public QuestionStatement(Question question, Statement statement) {
		setQuestion(question);
		setStatement(statement);
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionStatement other = (QuestionStatement) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}
