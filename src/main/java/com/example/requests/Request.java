package com.example.requests;

import com.example.enumRequests.RequestType;

public class Request implements RequestInterface {
    private RequestType requestType;
    private String clientData;

    public Request(){

    }

    public Request(RequestType requestType, String clientData) {
        this.requestType = requestType;
        this.clientData = clientData;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getClientData() {
        return clientData;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

}
