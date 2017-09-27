package ua.kozak.solowork.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.kozak.solowork.config.TestApplicationContextConfig;
import ua.kozak.solowork.domain.Seat;
import ua.kozak.solowork.domain.SeatType;
import ua.kozak.solowork.domain.Ticket;
import ua.kozak.solowork.domain.User;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationContextConfig.class})
public class UserDAOJdbcImplTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TicketDAO ticketDAO;

    @Autowired
    private SeatDAO seatDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void add() throws Exception {
        User userExpected = new User();
        userExpected.setEmail("expected@email");
        userExpected.setFirstName("expectedFirstName");
        userExpected.setLastName("expectedLastName");

        Seat seat = new Seat();
        seat.setId(1);
        seat.setAuditoryId(1);
        seat.setNumber(228);
        seat.setType(SeatType.regural);
        seat = seatDAO.add(seat);
        userExpected.addTicket(new Ticket(1, seat));
        userExpected.addTicket(new Ticket(2, seat));
        userExpected.addTicket(ticketDAO.add(new Ticket(3, seat)));

        long expectedId = 1;

        User userActual = userDAO.add(userExpected);

        assertEquals(userExpected.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getFirstName(), userActual.getFirstName());
        assertEquals(userExpected.getLastName(), userActual.getLastName());
        assertEquals(expectedId, userActual.getId());

        List<Ticket> expectedTickets = userExpected.getTickets().stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());
        List<Ticket> actualTickets = userActual.getTickets().stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());

        assertEquals(expectedTickets.get(0).getEventId(), actualTickets.get(0).getEventId());   //1
        assertEquals(expectedTickets.get(1).getEventId(), actualTickets.get(1).getEventId());   //2
        assertEquals(expectedTickets.get(2).getEventId(), actualTickets.get(2).getEventId());   //3
    }

    @Test
    public void update() throws Exception {
        Seat seat = new Seat();
        seat.setId(1);
        seat.setAuditoryId(1);
        seat.setNumber(228);
        seat.setType(SeatType.regural);
        seat = seatDAO.add(seat);

        User userOld = new User();
        userOld.setEmail("old@email");
        userOld.setFirstName("oldFirstName");
        userOld.setLastName("oldLastName");
        long expectedId = 1;
        userOld.addTicket(ticketDAO.add(new Ticket(1, seat)));      // db_id = 1
        userOld.addTicket(new Ticket(2, seat));
        userOld.addTicket(new Ticket(3, seat));
        userDAO.add(userOld);

        User userExpected = new User();
        userExpected.setId(expectedId);
        userExpected.setEmail("expected@email");
        userExpected.setFirstName("expectedFirstName");
        userExpected.setLastName("expectedLastName");
        userExpected.addTicket(ticketDAO.getById(1));               // db_id = 1 -> Ticket(1, seat)
        userExpected.addTicket(new Ticket(4, seat));
        userExpected.addTicket(ticketDAO.add(new Ticket(5, seat)));

        userDAO.update(userExpected);

        User userActual = userDAO.getById(expectedId);

        assertNotEquals(userOld.getEmail(), userActual.getEmail());
        assertNotEquals(userOld.getFirstName(), userActual.getFirstName());
        assertNotEquals(userOld.getLastName(), userActual.getLastName());
        assertNotEquals(userOld, userActual.getId());

        assertEquals(userExpected.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getFirstName(), userActual.getFirstName());
        assertEquals(userExpected.getLastName(), userActual.getLastName());
        assertEquals(expectedId, userActual.getId());

        List<Ticket> oldTickets = userOld.getTickets().stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());
        List<Ticket> expectedTickets = userExpected.getTickets().stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());
        List<Ticket> actualTickets = userActual.getTickets().stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());

        assertEquals(oldTickets.get(0).getEventId(), actualTickets.get(0).getEventId());            //1
        assertEquals(expectedTickets.get(0).getEventId(), actualTickets.get(0).getEventId());       //1
        assertEquals(oldTickets.get(1).getEventId(), actualTickets.get(1).getEventId());            //2
        assertEquals(oldTickets.get(2).getEventId(), actualTickets.get(2).getEventId());            //3
        assertEquals(expectedTickets.get(1).getEventId(), actualTickets.get(3).getEventId());       //4
        assertEquals(expectedTickets.get(2).getEventId(), actualTickets.get(4).getEventId());       //5
    }

    @Test(expected = NoSuchElementException.class)
    public void delete() throws Exception {
        User userOld = new User();
        userOld.setEmail("old@email");
        userOld.setFirstName("oldFirstName");
        userOld.setLastName("oldLastName");
        long expectedId = 1;

        Seat seat = new Seat();
        seat.setId(1);
        seat.setAuditoryId(1);
        seat.setNumber(228);
        seat.setType(SeatType.regural);
        seat = seatDAO.add(seat);
        userOld.addTicket(ticketDAO.add(new Ticket(1, seat)));
        userOld.addTicket(new Ticket(2, seat));
        userOld.addTicket(new Ticket(3, seat));

        userOld = userDAO.add(userOld);

        userDAO.delete(userOld);

        List<Ticket> expectedTickets = ticketDAO.getAllByUserId(0).stream().sorted(Comparator.comparing(Ticket::getEventId)).collect(Collectors.toList());

        assertEquals(expectedTickets.get(0).getUserId(), 0);
        assertEquals(expectedTickets.get(1).getUserId(), 0);
        assertEquals(expectedTickets.get(2).getUserId(), 0);
        assertEquals(expectedTickets.get(0).getEventId(), 1);
        assertEquals(expectedTickets.get(1).getEventId(), 2);
        assertEquals(expectedTickets.get(2).getEventId(), 3);

        userDAO.getById(expectedId);
    }

    @Test
    public void getAll() throws Exception {
        int expectedSize = 2;

        User userEx1 = new User();
        userEx1.setEmail("ex1@email");
        userEx1.setFirstName("ex1FirstName");
        userEx1.setLastName("ex1LastName");
        long expectedId1 = 1;
        userEx1 = userDAO.add(userEx1);

        User userEx2 = new User();
        userEx2.setEmail("ex2@email");
        userEx2.setFirstName("ex2FirstName");
        userEx2.setLastName("ex2LastName");
        long expectedId2 = 2;
        userEx2 = userDAO.add(userEx2);

        List<User> actualUsers = userDAO.getAll();

        assertEquals(actualUsers.size(), expectedSize);

        assertEquals(userEx1.getEmail(), actualUsers.get(0).getEmail());
        assertEquals(userEx1.getFirstName(), actualUsers.get(0).getFirstName());
        assertEquals(userEx1.getLastName(), actualUsers.get(0).getLastName());
        assertEquals(expectedId1, actualUsers.get(0).getId());

        assertEquals(userEx2.getEmail(), actualUsers.get(1).getEmail());
        assertEquals(userEx2.getFirstName(), actualUsers.get(1).getFirstName());
        assertEquals(userEx2.getLastName(), actualUsers.get(1).getLastName());
        assertEquals(expectedId2, actualUsers.get(1).getId());
    }

    @Test
    public void getById() throws Exception {
        User userExpected = new User();
        userExpected.setEmail("expected@email");
        userExpected.setFirstName("expectedFirstName");
        userExpected.setLastName("expectedLastName");
        long expectedId = 1;
        userDAO.add(userExpected);

        User userAnother = new User();
        userAnother.setEmail("another@email");
        userAnother.setFirstName("anotherFirstName");
        userAnother.setLastName("anotherLastName");
        long anotherId = 2;
        userDAO.add(userAnother);

        User userActual = userDAO.getById(expectedId);


        assertEquals(userExpected.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getFirstName(), userActual.getFirstName());
        assertEquals(userExpected.getLastName(), userActual.getLastName());
        assertEquals(expectedId, userActual.getId());

        assertNotEquals(userAnother.getEmail(), userActual.getEmail());
        assertNotEquals(userAnother.getFirstName(), userActual.getFirstName());
        assertNotEquals(userAnother.getLastName(), userActual.getLastName());
        assertNotEquals(anotherId, userActual.getId());
    }

    @Test
    public void getByEmail() throws Exception {
        String expectedEmail = "expected@email";

        User userExpected = new User();
        userExpected.setEmail(expectedEmail);
        userExpected.setFirstName("expectedFirstName");
        userExpected.setLastName("expectedLastName");
        long expectedId = 1;
        userDAO.add(userExpected);

        User userAnother = new User();
        userAnother.setEmail("another@email");
        userAnother.setFirstName("anotherFirstName");
        userAnother.setLastName("anotherLastName");
        long anotherId = 2;
        userDAO.add(userAnother);

        User userActual = userDAO.getByEmail(expectedEmail);


        assertEquals(userExpected.getEmail(), userActual.getEmail());
        assertEquals(userExpected.getFirstName(), userActual.getFirstName());
        assertEquals(userExpected.getLastName(), userActual.getLastName());
        assertEquals(expectedId, userActual.getId());

        assertNotEquals(userAnother.getEmail(), userActual.getEmail());
        assertNotEquals(userAnother.getFirstName(), userActual.getFirstName());
        assertNotEquals(userAnother.getLastName(), userActual.getLastName());
        assertNotEquals(anotherId, userActual.getId());
    }

    @Before
    public void clearDBBefore() {
        String sqlU = "TRUNCATE TABLE ticket_seller.public.user_table RESTART IDENTITY RESTRICT";
        jdbcTemplate.execute(sqlU);
        String sqlT = "TRUNCATE TABLE ticket_seller.public.ticket_table RESTART IDENTITY RESTRICT";
        jdbcTemplate.execute(sqlT);
    }

    @After
    public void clearDBAfter() {
        String sqlU = "TRUNCATE TABLE ticket_seller.public.user_table RESTART IDENTITY RESTRICT";
        jdbcTemplate.execute(sqlU);
        String sqlT = "TRUNCATE TABLE ticket_seller.public.ticket_table RESTART IDENTITY RESTRICT";
        jdbcTemplate.execute(sqlT);
        String sqlS = "TRUNCATE TABLE ticket_seller.public.seat_table RESTART IDENTITY RESTRICT";
        jdbcTemplate.execute(sqlS);
    }
}
