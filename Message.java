/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

/**
 * The class Message wraps a value of String with a Friend.
 * Each object is a message, with a String content and an author Friend.
 * 
 * @author MatteHammar
 */
public class Message {
    
    private String message;
    private Friend author;
    
    /**
     * Constructs an empty Message.
     */
    public Message() {
        message = null;
        author = null;
    }
    
    /**
     * Constructs a Message with given content and author.
     * @param message - The message which wants to be conveyd.
     * @param author - The author to that message.
     */
    public Message(String message, Friend author) {
        this.message = message;
        this.author = author;
    }
    
    /**
     * Get author for message.
     * 
     * @return - Friend
     */
    public Friend getAuthor() {
        return author;
    }
    
    /**
     * Get message content for that Message
     * 
     * @return - String
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Set message content for that Message
     * 
     * @param msg - The message that wants to be set 
     */
    public void setMessage(String msg) {
        message = msg;
    }
    
    /**
     * Checks if Message is empty.
     * 
     * @return - Boolean
     */
    public Boolean isEmpty() {
        return message == null;
    }
}
