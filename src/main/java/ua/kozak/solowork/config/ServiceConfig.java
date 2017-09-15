package ua.kozak.solowork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kozak.solowork.service.AuditoriumService;
import ua.kozak.solowork.service.EventService;
import ua.kozak.solowork.service.UserService;
import ua.kozak.solowork.service.impl.AuditoriumServiceImpl;
import ua.kozak.solowork.service.impl.EventServiceImpl;
import ua.kozak.solowork.service.impl.UserServiceImpl;

@Configuration
public class ServiceConfig {

    @Bean
    public AuditoriumService auditoriumService() {
        AuditoriumService auditoriumService = new AuditoriumServiceImpl();
        return auditoriumService;
    }

    @Bean
    public EventService eventService() {
        EventService eventService = new EventServiceImpl();
        return eventService;
    }

    @Bean
    public UserService userService() {
        UserService userService = new UserServiceImpl();
        return userService;
    }
}
