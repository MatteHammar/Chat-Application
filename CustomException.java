/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

/**
 *
 * @author MatteHammar
 */
public class CustomException extends Exception{
        
    public CustomException() {
        super("Unkown Error");
    }
    
    public CustomException(String message) {
        super(message);
    }
}
