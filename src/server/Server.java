/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.net.*;
import java.io.*;

/**
 *
 * @author dj
 */
public class Server {
    
    // Random port. 9000 chosen as a tribute to DBZ
    private static final int PORT = 9000;

    /**
     * Opens socket PORT. Waits for incoming client connections. 
     *  Once a connection is established it spawns a CLientThread. 
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket myServer = null;
        InetAddress ip;
        ip = InetAddress.getLocalHost();
        String host = ip.getHostName();
        //System.out.println("IP = " + ip);
       // System.out.println("Host = " + host);
        try {
            myServer = new ServerSocket(PORT);
        } catch (IOException exception) {
            System.err.println("Could not connect to port requested.");
            System.exit(1);
        }
	
        // Wait for incoming clients 
        while(true) {
            try {
                Socket client = myServer.accept();
                System.out.println("Connected"); 
                ClientThread thread = new ClientThread(client);
                thread.start();
            } catch (IOException exception) {
                System.err.println("Could not accept incoming client");
                System.exit(1);
            }
        }
    }
}
