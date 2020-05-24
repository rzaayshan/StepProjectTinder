package org.step.tinder.Helpers;

import java.time.LocalDateTime;


public class Message {
    private String from;
    private String to;
    private LocalDateTime time;
    private String mes;

    public Message(String from, String to, LocalDateTime time, String mes) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.mes = mes;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getMes() {
        return mes;
    }

    @Override
    public String toString() {
        return String.format("Message{from='%s', to='%s', time=%s, message='%s'}", from, to, time, mes);
    }
}
