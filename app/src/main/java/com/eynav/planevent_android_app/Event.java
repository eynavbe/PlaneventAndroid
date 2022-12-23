package com.eynav.planevent_android_app;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Event {
    String emailClient1;
    String emailClient2;
    long countInvited, price;
    String typeEvent, dateEvent, lastNameEvent, hourEvent;

    public Event(String emailClient1, String emailClient2, long countInvited, long price, String typeEvent, String dateEvent, String lastNameEvent, String hourEvent) {
        this.emailClient1 = emailClient1;
        this.emailClient2 = emailClient2;
        this.countInvited = countInvited;
        this.price = price;
        this.typeEvent = typeEvent;
        this.dateEvent = dateEvent;
        this.lastNameEvent = lastNameEvent;
        this.hourEvent = hourEvent;
    }

    public long getCountInvited() {
        return countInvited;
    }

    public long getPrice() {
        return price;
    }

    public String getHourEvent() {
        return hourEvent;
    }

    public String getEmailClient1() {
        return emailClient1;
    }

    public String getEmailClient2() {
        return emailClient2;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public String getLastNameEvent() {
        return lastNameEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }
}
