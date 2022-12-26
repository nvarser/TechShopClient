package com.example.requests;

import com.example.enumRequests.RequestType;

public interface RequestInterface {
    public RequestType getRequestType();

    public String getClientData();

    public void setRequestType(RequestType requestType);

    public void setClientData(String clientData);
}
