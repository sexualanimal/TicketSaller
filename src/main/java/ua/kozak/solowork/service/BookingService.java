package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.Date;
import ua.kozak.solowork.domain.Event;
import ua.kozak.solowork.domain.Ticket;

import java.util.Set;

public interface BookingService {
//    String FILENAME = "booking_accounts_data";
//
//    Ticket save(Ticket booking);
//
//    Set<Ticket> saveAll(Set<Ticket> booking, boolean overwrite);
//
//    Set<Ticket> getAll();
//
//    Ticket remove(Ticket booking);

    double getTicketsPrice(Event event, int[] places);

    boolean bookTicket(Ticket ticket);

    Set<Ticket> getPurchasedTicketsForEvent(Event event, Date dateTime);
}
