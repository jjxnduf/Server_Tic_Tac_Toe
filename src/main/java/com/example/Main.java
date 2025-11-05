package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {


        ServerSocket mioServerSocket = new ServerSocket(3000);

        Socket G1 = mioServerSocket.accept();

        BufferedReader G1_in = new BufferedReader(new InputStreamReader(G1.getInputStream()));
        PrintWriter G1_out = new PrintWriter(G1.getOutputStream(), true);
        
        System.out.println("Giocatore 1 collegato");
        G1_out.println("WAIT");

        Socket G2 = mioServerSocket.accept();

        BufferedReader G2_in = new BufferedReader(new InputStreamReader(G2.getInputStream()));
        PrintWriter G2_out = new PrintWriter(G2.getOutputStream(), true);
        
        System.out.println("Giocatore 2 collegato");
        G1_out.println("READY");
        G2_out.println("READY");

        
       
    }
}