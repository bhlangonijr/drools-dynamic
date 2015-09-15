package com.bhlangonijr.domain;

import java.io.Serializable;
import java.util.UUID;

public class Response implements Serializable {

    private String messageId;
    private Boolean success = false;
    private String text;

    public Response() {
        this(null);
    }

    public Response(String text) {
        this.messageId = UUID.randomUUID().toString();
        this.text = text;
    }
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Response{");
        sb.append("messageId='").append(messageId).append('\'');
        sb.append(", success=").append(success);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
