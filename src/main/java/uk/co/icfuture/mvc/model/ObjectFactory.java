package uk.co.icfuture.mvc.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public Question createQuestion() {
		return new Question();
	}

}
