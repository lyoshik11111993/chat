package com.example.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    List<String> messages = new ArrayList<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            int i = 0;
            while (i != 1000) {
                System.out.println("Server waiting!");
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println("Message received: " + line);
                            messages.add(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                System.out.println("Request received!");
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
