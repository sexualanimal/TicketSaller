package ua.kozak.solowork.domain;

import java.text.SimpleDateFormat;

public class Date extends java.util.Date {

    private SimpleDateFormat dateFormat;
    private String formatted;
    private Auditorium auditorium;

    public Date(String dateFormatPattern) {
        super();
        this.dateFormat = new SimpleDateFormat(dateFormatPattern);
        formatted = dateFormat.format(this);
    }

    public Date() {
        super();
        this.dateFormat = new SimpleDateFormat();
        formatted = dateFormat.format(this);
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
}
