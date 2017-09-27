package ua.kozak.solowork.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Auditory implements Serializable {

    private long id;
    private String name;
    private String location;
    private List<Seat> seats = new ArrayList<>();
    private List<Event> events = new ArrayList<>();

    public Auditory() {
    }

    public Auditory(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public boolean addSeat(Seat seat) {
        return seats.add(seat);
    }

    public boolean removeSeat(Seat seat) {
        return seats.remove(seat);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    public boolean removeEvent(Event event) {
        return events.remove(event);
    }

    @Override
    public String toString() {
        return "Auditory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", seats=" + seats +
                ", events=" + events +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditory auditory = (Auditory) o;

        if (id != auditory.id) return false;
        if (name != null ? !name.equals(auditory.name) : auditory.name != null) return false;
        if (location != null ? !location.equals(auditory.location) : auditory.location != null) return false;
        if (seats != null ? !seats.equals(auditory.seats) : auditory.seats != null) return false;
        return events != null ? events.equals(auditory.events) : auditory.events == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (seats != null ? seats.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        return result;
    }
}
