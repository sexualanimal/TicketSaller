package ua.kozak.solowork.service;

import ua.kozak.solowork.domain.Booking;

import java.util.Set;

public interface BookingService {
    String FILENAME = "booking_accounts_data";

    Booking save(Booking booking);

    Set<Booking> saveAll(Set<Booking> booking, boolean overwrite);

    Set<Booking> getAll();

    Booking remove(Booking booking);

//    getTicketsPrice(event, dateTime, user, seats) - возвращает общую стоимость всех билетов на указанное событие в определенные даты и время для указанных мест.
//
//    bookTicket(tickets) - билет должен содержать  информацию о event, dateTime, seat, и пользователя. User может зарегистрироваться или нет. Если пользователь зарегистрировался, тогда информация о заказе должна содержать этого пользователя в каждом билете нашего заказа.
//
//    getPurchasedTicketsForEvent(event, dateTime) - получает все купленые билеты на event по конкретной дате и времени.

}
