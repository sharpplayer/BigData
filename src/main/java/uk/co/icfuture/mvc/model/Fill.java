package uk.co.icfuture.mvc.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tblfill")
public class Fill {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fillId")
	private int fillId;

	@Column(name = "questionnaireId")
	private Questionnaire questionnaire;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Answer> answers;

	public int getFillId() {
		return fillId;
	}

	public void setFillId(int fillId) {
		this.fillId = fillId;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

}
