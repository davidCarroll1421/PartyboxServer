/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.net.*;
import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 *
 * @author dj
 */
public class Client {
    private static final int PORT = 9000;
    private static final String PATH = "C:/Users/dj/Documents/NetBeansProjects/Server/src/server/example_order.json";
    //private static final String PATH = "/home/dj/Desktop/example_order.json";
    
    /*
    Connects to PORT. Sends a JSONObject over the socket to our remote server. 
    (NEED TO FIGURE OUT HOW TO FIND SERVER IP). After it sends data it closes the 
    output stream and its connection to the server. 
    */
    public static void main(String[] args) throws IOException, ParseException {
        
        // Get order from json file
        // NEED TO FIX THIS TO SEND JSON STRING 
        JSONParser parser = new JSONParser();
        Object object = parser.parse(new FileReader(PATH));
        JSONObject jsonObj = (JSONObject)object;
        
        InetAddress addr = InetAddress.getLocalHost();
		
        try {
            Socket socket = new Socket("10.0.0.121", PORT);
            ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
            writer.writeObject(jsonObj);
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.err.println("Failed to connect to server socket");
            System.exit(1);
        }
		
    }
}
