package ua.kozak.solowork.domain;

import java.text.SimpleDateFormat;

public class Date extends java.util.Date {

    private SimpleDateFormat dateFormat;

    public Date(String dateFormatPattern) {
        super();
        this.dateFormat = new SimpleDateFormat(dateFormatPattern);
    }

    public Date() {
        super();
        this.dateFormat = new SimpleDateFormat();
    }

    public Date(long date, SimpleDateFormat dateFormat) {
        super(date);
        this.dateFormat = dateFormat;
    }

    public Date(long date) {
        super(date);
        this.dateFormat = new SimpleDateFormat();
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFormatted() {
        return dateFormat.format(this);
    }
}
