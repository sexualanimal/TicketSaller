package ua.kozak.solowork.domain;

public class Seat {

    private long id;
    private int number;
    private long auditoryId;
    private SeatType type;

    public Seat() {
    }

    public Seat(int number, long auditoryId, SeatType type) {
        this.number = number;
        this.auditoryId = auditoryId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getAuditoryId() {
        return auditoryId;
    }

    public void setAuditoryId(long auditoryId) {
        this.auditoryId = auditoryId;
    }

    public SeatType getType() {
        return type;
    }

    public void setType(SeatType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", number=" + number +
                ", auditoryId=" + auditoryId +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seat seat = (Seat) o;

        if (id != seat.id) return false;
        if (number != seat.number) return false;
        if (auditoryId != seat.auditoryId) return false;
        return type == seat.type;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + number;
        result = 31 * result + (int) (auditoryId ^ (auditoryId >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
