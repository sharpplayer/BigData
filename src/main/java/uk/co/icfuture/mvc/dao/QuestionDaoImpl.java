package uk.co.icfuture.mvc.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import uk.co.icfuture.mvc.exception.ResourceNotFoundException;
import uk.co.icfuture.mvc.model.Question;

@Repository("questionDao")
public class QuestionDaoImpl extends AbstractDao<Question> implements
		QuestionDao {

	public Question saveQuestion(Question question) {
		if (question.getQuestionId() == 0) {
			return persist(question);
		} else {
			return update(question);
		}
	}

	public List<Question> getQuestionsByStatement(String filter) {
		TypedQuery<Question> tq = getEntityManager()
				.createQuery(
						"select distinct q from Question q join q.questionStatements s where s.pk.statement.statement like :text",
						Question.class)
				.setParameter("text", "%" + filter + "%");

		return tq.getResultList();

	}

	public List<Question> getQuestions(String descriptionFilter) {
		return getFilteredList("description", "%" + descriptionFilter + "%");
	}

	public Question getQuestion(int id) {
		return getItemById(id);
	}

	@Override
	public List<Question> findQuestions(List<Question> questions)
			throws ResourceNotFoundException {
		ArrayList<Question> ret = new ArrayList<Question>();
		int index = 0;
		for (Question m : questions) {
			if (m.getQuestionId() == 0) {
				TypedQuery<Question> tq = getEntityManager()
						.createQuery(
								"select distinct q from Question q join q.statements s where s.statement = :text",
								Question.class).setParameter("text",
								m.getQuestion());
				List<Question> l = tq.getResultList();
				boolean added = true;
				for (Question q : l) {
					if (q.getQuestion().equals(m.getQuestion())) {
						added = true;
						ret.add(q);
						break;
					}
				}
				if (!added) {
					throw new ResourceNotFoundException("question",
							m.getQuestion(), index);
				}
			} else {
				ret.add(m);
			}
			index++;
		}
		return ret;
	}

}
