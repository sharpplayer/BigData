package uk.co.icfuture.mvc.controller;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import uk.co.icfuture.mvc.form.filter.StatementFilter;
import uk.co.icfuture.mvc.model.Statement;
import uk.co.icfuture.mvc.service.StatementService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class AppControllerTest {

	private MockMvc mockMvc;

	@Mock
	private StatementService mockStatementService;

	private Statement statement1;

	private Statement statement2;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views");
		vr.setSuffix(".jsp");
		mockMvc = MockMvcBuilders
				.standaloneSetup(new AppController(mockStatementService))
				.setViewResolvers(vr).build();
		statement1 = new Statement(1, "statement1", new String[] { "one",
				"uno", "1" });
		statement2 = new Statement(2, "statement2", new String[] { "two",
				"dos", "2" });
	}

	@Test
	public void testIndexAction() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	public void testStatementsAction() throws Exception {
		when(mockStatementService.getStatements(any(StatementFilter.class)))
				.thenReturn(Arrays.asList(statement1, statement2));
		when(mockStatementService.getStatement(0))
		.thenReturn(new Statement());
		mockMvc.perform(get("/html/statements")).andExpect(status().isOk())
				.andExpect(view().name("statements"))
				.andExpect(model().attributeExists("statementForm"));
		verify(mockStatementService, times(1)).getStatements(any(StatementFilter.class));
		verify(mockStatementService, times(1)).getStatement(0);
		verifyNoMoreInteractions(mockStatementService);
	}

	@Test
	public void testStatementsActionById() {
		fail("Not yet implemented");
	}

	@Test
	public void testStatementsPostActionStringStatementFormBindingResultModelMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testStatementsPostActionIntStringStatementFormBindingResultModelMap() {
		fail("Not yet implemented");
	}

}
