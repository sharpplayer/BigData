package uk.co.icfuture.mvc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Question.class)
public abstract class Question_ {

	public static volatile SingularAttribute<Question, Integer> questionId;
	public static volatile ListAttribute<Question, QuestionStatement> questionStatements;
	public static volatile SingularAttribute<Question, String> description;

}

