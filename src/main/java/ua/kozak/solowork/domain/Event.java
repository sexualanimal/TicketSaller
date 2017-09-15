package ua.kozak.solowork.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Event implements Serializable {

    private long id;
    private String name;
    private String description;
    private double basePrice;
    private Set<Date> dates = new HashSet<>();

    public Event(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public Set<Date> getDates() {
        return dates;
    }

    public void setDates(Set<Date> dates) {
        this.dates = dates;
    }

    public void addDate(Date date) {
        dates.add(date);
    }
}
