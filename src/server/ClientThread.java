
package server;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author dj
 */
public class ClientThread extends Thread {
    private final Socket socket;
    Connection con;
	
    /**
     * Constructor method to initialize socket. 
     * @param socket
     */
    public ClientThread(Socket socket)
    {
        this.socket = socket;
    }
    
    public ClientThread()
    {
        socket = null;
    }
    
    void getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/mydb", "root", "c13141802");
            System.out.println("Database Connection complete");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void insertOrder(Order order)
    {
        try {
            // Insert info into User table 
            Statement myStmt = con.createStatement();
            String sql = "INSERT INTO USER(name, address, city, zip, delDate, delTime) "
                    + "VALUES('" + order.getName() +"','" + order.getAddr() +"','" + order.getCity() + "','" + order.getZip() 
                    + "','" + order.getDelDate() + "','" + order.getDelTime() + "')";
            myStmt.execute(sql);
            myStmt.close();
            
            int numberOfItems = order.getNumberOfItems();
            // Insert info into USER_ITEM table (how much of each thing did they order. 
            for(int i = 0; i < numberOfItems; i++)
            {
                CartItem item = order.getCartIndex(i);
                String name = item.getItem();
                int quant = item.getQuant();
                
                myStmt = con.createStatement();
                //System.out.println(userId);
                sql = "INSERT IGNORE INTO USER_ITEM(username, itemname, quantity) "
                        + "VALUES('" + order.getName() +"','" + name + "'," + quant + ")"; 
                myStmt.execute(sql);
                myStmt.close();
            }
            System.out.println("Insert Complete");
        } catch (SQLException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    Method to override the thread run method. 
    Reads a JSON object from the socket interface and creates a new order object from this. 
    All parsing and data finding is handled by the Order class. 
    */
    @Override
    @SuppressWarnings("null")
    public void run() {
		
        BufferedReader reader = null;
        try {
            // Get JSON Object from input stream. 
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = reader.readLine();
            JSONParser parser = new JSONParser();
            Object object = parser.parse(content);
            JSONObject jsonObj = (JSONObject)object;
            Order order = new Order(jsonObj);
            getConnection();
            insertOrder(order);
            // We need to do the database work right here. 
            //order.print();
            con.close();
            // Crazy IDE try catch bullshit. Glad I didnt have to do it. 
            try {
                
                socket.close();
            } catch (IOException exception) {
                System.err.println("Failure closing connection");
            }
        } catch (IOException | ParseException | SQLException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	
    }
}
