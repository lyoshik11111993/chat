package com.example;

import com.example.socket.SocketServer;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) throws IOException {
        SocketServer socketServer = new SocketServer();
        socketServer.start();
    }

}
