package ua.kozak.solowork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kozak.solowork.Application;

@Configuration
public class MainConfig {
    @Bean
    public Application application() {
        Application application = new Application();
        return application;
    }
}
