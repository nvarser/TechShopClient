package com.example.client;

import com.example.entities.User;
import com.example.requests.Request;
import com.example.enumRequests.RequestType;
import com.example.requests.Response;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client{

    private static Client instance;

    private Gson gson = new Gson();

    private Request requestClient;
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private ObjectOutputStream outputStream;

    private Client(){
        this.socket = createClientSocket();
        this.writer = getWriter();
        this.reader = getReader();
        this.outputStream = getObjectOutputStream();
    }

    public void sendingRequestToServer(String request){
        try {
            writer.write(request);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Response acceptingReplyEnteringFromServer(){
        try {
            String response = reader.readLine();
            return (Response) gson.fromJson(response, Response.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeEverything() throws IOException {
        requestClient = new Request();
        requestClient.setRequestType(RequestType.STOPPED);
        sendingRequestToServer(gson.toJson(requestClient));
        if(socket != null) socket.close();
        if(reader != null) reader.close();
        if(writer != null) writer.close();
        if(outputStream != null) outputStream.close();
    }

    public static Client getInstance(){
        if(instance == null) {
            instance = new Client();
        }
        return instance;
    }

    private Socket createClientSocket(){
        try {
            return new Socket("localhost",4288);
        } catch (IOException e) {
            System.out.println("Error in creating Client Socket!");
            throw new RuntimeException(e);
        }
    }
    private BufferedReader getReader(){
        try {
            return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  BufferedWriter getWriter(){
        try {
            return new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectOutputStream getObjectOutputStream(){
        try {
            return new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
