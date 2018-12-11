/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *  Class created to represent an Order given to us from the Application. 
 * @author dj
 */
public class Order {
    
    // Instance variables representing all pertinant information towards an order. 
    private String  firstName;
    private String  lastName;
    private String  address;
    private String  city;
    private String  zipCode;
    private String  deliveryDate;
    private String  deliveryTime;
    private CartItem[] cartArray;
    private JSONObject object;
    
    // Constants to represent the different fields coming over in our JSON object. 
    // Allows us to find information. 
    private static final String JSON_FNAME = "firstName";
    private static final String JSON_LNAME = "lastName";
    private static final String JSON_ADDR = "address";
    private static final String JSON_CITY = "city";
    private static final String JSON_ZIP = "zip";
    private static final String JSON_DDATE = "deliveryDate";
    private static final String JSON_DTIME = "arriveBy";
    private static final String JSON_CART = "cart";
    private static final String JSON_QUANT = "quantity";
    private static final String JSON_ITEM = "item";
    // Constant used for testing. Location of my .json file in memory. 
    private static final String PATH = "C:/Users/dj/Documents/NetBeansProjects/Server/src/server/example_order.json";
    
    // Constructor method to parse the Json object and fill out our order fields
    public Order(JSONObject jsonObj) throws ParseException {
        getOrder(jsonObj);
    }
    
    /*
    Helper function to parse the JSON Object into required fields. 
    Fills out our Order class as desired. 
    */
    private void getOrder(JSONObject jsonObj) throws ParseException
    {
        this.object = jsonObj;
        this.firstName = (String)jsonObj.get(JSON_FNAME);
        this.lastName = (String)jsonObj.get(JSON_LNAME);
        this.address = (String)jsonObj.get(JSON_ADDR);
        this.city = (String)jsonObj.get(JSON_CITY);
        this.zipCode = (String)jsonObj.get(JSON_ZIP);
        this.deliveryDate = (String)jsonObj.get(JSON_DDATE);
        this.deliveryTime = (String)jsonObj.get(JSON_DTIME);
        JSONArray cart = (JSONArray)jsonObj.get(JSON_CART);
        cartArray = new CartItem[cart.size()];
        
        // Our total order information.
        for (int i = 0; i < cart.size(); i++)
        {
            JSONObject cItem = (JSONObject)cart.get(i);
            long quant = (long)cItem.get(JSON_QUANT);
            String item = (String)cItem.get(JSON_ITEM);
            CartItem contents = new CartItem(item, (int)quant );
            setCartIndex(contents, i);
        }
        
    }
    
    // Prints out a representation of our entire order. 
    public void print() {
        
        System.out.printf("Name: %s %s\n", firstName, lastName);
        System.out.printf("Address: %s,%s,%s\n", address, city, zipCode);
        for (CartItem cartArray1 : cartArray) {
            cartArray1.printItems();
        }
    }
    
    // Sets the name variables for our order. 
    public void setName(String first, String last)
    {
        this.firstName = first;
        this.lastName = last;
    }
    
    // Sets the address variables for our order. 
    public void setAddr(String addr, String city, String zip)
    {
        this.address = addr;
        this.city = city; 
        this.zipCode = zip;
    }
    
    // Sets the delivery date variables for our order. 
    public void setDelivery(String date, String time)
    {
        this.deliveryDate = date;
        this.deliveryTime = time;
    }
    
    // Sets a cart item for our order. 
    public void setCartIndex(CartItem cart, int index)
    {
        this.cartArray[index] = cart;
    }
    
    // Returns the JSONObject represented by this order. 
    public JSONObject getObject() throws FileNotFoundException, IOException, ParseException
    {
        
        return this.object;
    }
    
    // Returns the name of the order as a combined string. 
    public String getName()
    {
        return (firstName + " " + lastName);
    }
    
    // Returns the address of the order. 
    public String getAddr()
    {
        return this.address;
    }
    
    // Returns the city of our order. 
    public String getCity()
    {
        return this.city;
    }
    
    // Returns the zip code of our order. 
    public String getZip()
    {
        return this.zipCode;
    }
    
    // Returns the delivery date and time as a string. 
    public String getDelDate()
    {
        return this.deliveryDate;
    }
    
    public String getDelTime()
    {
        return this.deliveryTime;
    }
    
    // Returns a particular cart item from our order. 
    public CartItem getCartIndex(int i)
    {
        return cartArray[i];
    }
    
    public int getNumberOfItems()
    {
        return cartArray.length;
    }
    
}
