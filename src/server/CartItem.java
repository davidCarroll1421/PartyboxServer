/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author dj
 */
public class CartItem {
    
    private final String  item;
    private final int     quantity;
    
    public CartItem(String item, int quant)
    {
        this.item = item;
        this.quantity = quant;
    }
    
    public String getItem()
    {
        return this.item;
    }
    
    public int getQuant()
    {
        return this.quantity;
    }
    
    public void printItems()
    {
        System.out.printf("%s : %d\n", item, quantity);
    }
    
}
