package com.example.socket;

import com.example.entity.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

import static com.example.socket.SocketUtils.sendMessage;

public class SocketClient {

    public void connect() {
        try (Socket socket = new Socket("192.168.1.22", 8080)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Random random = new Random();
            Scanner scanner = new Scanner(System.in);
            Client client = new Client(random.nextInt(10));
            int i = 0;
            while (i != 1000) {
                new Thread(() -> {
                    sendMessage("Client " + client.getId() + " : Hi Server!", printWriter);
                }).start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(() -> {
                    String line;
                    try {
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                i++;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
