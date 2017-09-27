package ua.kozak.solowork;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.kozak.solowork.domain.*;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("ua.kozak.solowork");
        ctx.registerShutdownHook();
        ctx.start();

        Application application = ctx.getBean(Application.class);

        Seat seat = new Seat();
        seat.setNumber(228);
        seat.setId(1);
        seat.setType(SeatType.regural);
        seat.setAuditoryId(1);

        Ticket ticket = new Ticket();
        ticket.setSeat(seat);

        application.ticketDAO.add(ticket);

        System.out.println();
    }
}
