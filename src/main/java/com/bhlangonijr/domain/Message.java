package com.bhlangonijr.domain;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {

    private String from;
    private String to;
    private String text;

    public Message() {
    }

    public Message(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("from='").append(from).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, text);
    }
}
