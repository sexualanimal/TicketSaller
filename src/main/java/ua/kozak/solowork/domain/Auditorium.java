package ua.kozak.solowork.domain;

import java.io.Serializable;

public class Auditorium implements Serializable {

    private String name;
    private int numberOfSeats;
    private int vipSeats;

    public Auditorium(String name, int numberOfSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = 0;
    }

    public Auditorium(String name, int numberOfSeats, int vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public int getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(int vipSeats) {
        this.vipSeats = vipSeats;
    }
}
