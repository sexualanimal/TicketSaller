package ua.kozak.solowork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.kozak.solowork.Application;

@Configuration
@ComponentScan("ua.kozak.solowork")
public class MainConfig {
    @Bean
    public Application application() {
        Application application = new Application();
        return application;
    }
}
