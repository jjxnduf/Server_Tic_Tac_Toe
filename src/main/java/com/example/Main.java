package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

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

        char[] tris = { '0', '0', '0', '0', '0', '0', '0', '0', '0' };

        boolean gameEnded = false;
        while (!gameEnded) {

            gameEnded = TurnoGiocatore(tris, G1_out, G1, G1_in, G2_out, "1");

            if (!gameEnded) {
                gameEnded = TurnoGiocatore(tris, G2_out, G2, G2_in, G1_out, "2");
            }
        }

        mioServerSocket.close();
        G1.close();
        G2.close();
    }

    public static boolean TurnoGiocatore(char[] celle, PrintWriter currentPlayerOut, Socket currentPlayer,BufferedReader currentPlayerIn, PrintWriter opponentOut, String numeroGiocatore) throws IOException {
        // Il giocatore fa una mossa
        String input = currentPlayerIn.readLine();
        int mossa = Integer.parseInt(input);

        if (mossa >= 0 && mossa < 9 && celle[mossa] == '0') {

            celle[mossa] = numeroGiocatore.charAt(0);
            currentPlayerOut.println("OK");
            currentPlayerOut.println(Arrays.toString(celle));

            char esito = Vincitore(celle);
            if (esito == '0') {

                opponentOut.println(Arrays.toString(celle) + ",");
                opponentOut.println("");

            } else if (esito == '1' || esito == '2') {

                opponentOut.println(Arrays.toString(celle) + ",");
                opponentOut.println(esito == '1' ? "L" : "L");
                currentPlayerOut.println("W");
                return true;

            } else {
                opponentOut.println(Arrays.toString(celle) + ",");
                opponentOut.println("P");
                currentPlayerOut.println("P");
                return true;
            }
        } else {

            currentPlayerOut.println("KO");
        }
        return false; // La partita continua
    }

    public static char Vincitore(char[] celle) {
        int[][] combinazioni_vincenti = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 },
                { 0, 3, 6 },
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 0, 4, 8 },
                { 2, 4, 6 }
        };

        for (int[] combinazioni : combinazioni_vincenti) {

            if (celle[combinazioni[0]] != '0' && celle[combinazioni[0]] == celle[combinazioni[1]] && celle[combinazioni[1]] == celle[combinazioni[2]]) {
                return celle[combinazioni[0]];
            }
        }

        // Controllo pareggio
        for (char c : celle) {
            if (c == '0')
                return '0';
        }

        // Pareggio
        return 'P';
    }
}
