package uk.co.icfuture.mvc.controller;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class BaseUnitTest {

	protected MockMvc getMockMvc(Object controller) {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views");
		vr.setSuffix(".jsp");
		return MockMvcBuilders.standaloneSetup(controller).setViewResolvers(vr)
				.build();
	}
}
