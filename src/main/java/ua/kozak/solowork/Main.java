package ua.kozak.solowork;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.kozak.solowork.domain.Auditory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("ua.kozak.solowork");
        ctx.registerShutdownHook();
        ctx.start();

        Application application = ctx.getBean(Application.class);

        Auditory auditory = new Auditory();
        auditory.setName("Name");
        auditory.setLocation("Location");

        Auditory resultAuditory = application.getAuditoryDAO().add(auditory);

        System.out.println();
    }
}
