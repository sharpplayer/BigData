package uk.co.icfuture.mvc.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({ "uk.co.icfuture.mvc.*" })
@PropertySource(value = { "classpath:application.properties" })
@ImportResource("classpath:servlet-context.xml")
@Import({ WebMvcConfig.class, PersistenceConfig.class, SecurityConfig.class })
public class AppConfig {
}
