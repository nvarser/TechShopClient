package com.example.requests;

import com.example.enumRequests.Responses;

public class Response {
    private Responses responses;
    private String serverData;

    public Responses getResponse() {
        return responses;
    }

    public String getServerData() {
        return serverData;
    }

    public void setResponse(Responses response) {
        this.responses = response;
    }

    public void setServerData(String serverData) {
        this.serverData = serverData;
    }
}
