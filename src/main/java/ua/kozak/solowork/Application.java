package ua.kozak.solowork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.kozak.solowork.dao.AuditoryDAO;
import ua.kozak.solowork.dao.SeatDAO;
import ua.kozak.solowork.dao.TicketDAO;
import ua.kozak.solowork.dao.UserDAO;

@Component
public class Application {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TicketDAO ticketDAO;
}
