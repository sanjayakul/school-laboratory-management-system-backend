package com.laboratory.management.system.util;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Response {

    protected static final String DEFAULT_PAGE_SIZE = "100";

    protected static final String DEFAULT_PAGE_NUMBER = "0";

    private Object data;
    private String message;
    private final Date timestamp;

    public Response() {
        this.timestamp = new Date();
    }

    protected Response response(String message) {
        this.message = message;
        return this;
    }

    protected Response error(String message) {
        this.message = message;
        this.data = null;
        return this;
    }

    protected Response response(Object data, String message) {
        this.data = data;
        this.message = message;
        return this;
    }

    protected Response error(Object data, String message) {
        this.data = data;
        this.message = message;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}