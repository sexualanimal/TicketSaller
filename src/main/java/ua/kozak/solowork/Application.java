package ua.kozak.solowork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ua.kozak.solowork.service.EventService;

@Component
public class Application {

    @Autowired
    EventService eventService;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("ua/kozak/solowork");
        ctx.registerShutdownHook();
        ctx.start();

        Application application = ctx.getBean(Application.class);

        System.out.println();
    }
}
