package com.example.controller;

import com.example.entity.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import static com.example.socket.SocketUtils.sendMessage;

public class MainController {
    Random random = new Random();
    Client client = new Client(random.nextInt(10));

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private Button sendBtn;

    public void connect() {
        try (Socket socket = new Socket("192.168.1.22", 8080)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int i = 0;
            while (i != 1000) {
                new Thread(() -> {
                    sendMessage("Client " + client.getId() + ": " +sendMsg(), printWriter);
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
                            textArea.appendText(line);
                            textArea.appendText("\n");
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

    @FXML
    private String sendMsg() {
        return textField.getText();
    }
}
