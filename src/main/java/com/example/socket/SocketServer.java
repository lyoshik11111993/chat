package com.example.socket;

import lombok.EqualsAndHashCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class SocketServer {
    List<String> messages = new ArrayList<>();
    List<PrintWriter> clients = new ArrayList<>();
    String line;

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            int i = 0;
            while (i != 1000) {
                System.out.println("Server waiting!");
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                            if (!clients.contains(printWriter)) {
                                clients.add(printWriter);
                            }
                        messages.forEach(element -> SocketUtils.sendMessage(element, printWriter));
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println("Message received: " + line);
                            messages.add(line);
                            new Thread(() -> {
                                for (int j = 0; j < clients.size(); j++) {
                                    SocketUtils.sendMessage(line, clients.get(j));
                                }
                            }).start();
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
