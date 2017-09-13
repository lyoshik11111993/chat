package com.example;

import com.example.socket.SocketClient;

import java.io.IOException;

public class MainClient {

    public static void main(String[] args) throws IOException {
        SocketClient socketClient = new SocketClient();
        socketClient.connect();
    }
}
