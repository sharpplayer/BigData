package uk.co.icfuture.mvc.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import uk.co.icfuture.mvc.configuration.contentnegotiation.AppContentNegotiationStrategy;
import uk.co.icfuture.mvc.configuration.messageconverter.JsonMessageConverter;
import uk.co.icfuture.mvc.configuration.viewresolver.Jaxb2MarshallingXmlViewResolver;
import uk.co.icfuture.mvc.configuration.viewresolver.JsonViewResolver;
import uk.co.icfuture.mvc.model.ObjectFactory;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addViewController("login").setViewName("login");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		return messageSource;
	}

	@Bean
	public SmartValidator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ViewResolver jspViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer
				.ignoreAcceptHeader(true)
				.defaultContentType(MediaType.TEXT_HTML)
				.defaultContentTypeStrategy(new AppContentNegotiationStrategy());
	}

	@Bean
	public ViewResolver jsonViewResolver() {
		return new JsonViewResolver();
	}

	@Bean
	public ViewResolver jaxb2MarshallingXmlViewResolver() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(new String[] {});
		return new Jaxb2MarshallingXmlViewResolver(marshaller);
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver cnvr = new ContentNegotiatingViewResolver();
		cnvr.setContentNegotiationManager(manager);
		List<ViewResolver> vrs = new ArrayList<ViewResolver>();
		vrs.add(jsonViewResolver());
		vrs.add(jspViewResolver());
		vrs.add(jaxb2MarshallingXmlViewResolver());
		cnvr.setViewResolvers(vrs);
		return cnvr;
	}

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		try {
			converters.add(jsonMessageConverter());
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
		super.configureMessageConverters(converters);
	}

	@Bean
	public HttpMessageConverter<Object> jsonMessageConverter()
			throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("uk.co.icfuture.mvc.model",
				ObjectFactory.class.getClassLoader());
		return new JsonMessageConverter<Object>(jc);
	}
}
