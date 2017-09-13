package com.example.socket;

import java.io.PrintWriter;

public class SocketUtils {

    public static void sendMessage(String s, PrintWriter printWriter) {
        printWriter.println(s);
        printWriter.flush();
    }
}
