package com.oxfordplus.hypen.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("timeStamp")
    @Expose
    private long timeStamp;

    public ServerResponse(){

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long time) {
        this.timeStamp = time;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", time=" + timeStamp +
                '}';
    }
}
