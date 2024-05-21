/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LogWriter class writes to a logfile
 * 
 * @author MatteHammar
 */
public class LogWriter {
    private FileWriter writer;
    
    /**
     * Constructor
     */
    public LogWriter(){
    }
    
    /**
     * Adds a message to the logfile tied to the specified Friend.
     * 
     * @param friend - A Friend Object
     * @param message - A Message Object
     * @see Friend
     * @see Message
     */
    public void addMessage(Friend friend, Message message) {
        try {
            writer = new FileWriter(friend.getNickname() + ".log", true);
            
            writer.write("<" + message.getAuthor().getNickname() + ">" + message.getMessage() + "\n");
            
            writer.close();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, friend.getNickname() + ".log");
        }
    }
    
    /**
     * Creates a log file with given name.
     * 
     * @param filename - Name of logfile
     */
    public void createLog(String filename) {
        try {
            writer = new FileWriter(filename);
            writer.close();
        } catch (IOException ex) {
            SystemExceptionHandler s = new SystemExceptionHandler(ex);
        }
    }
    
    /**
     * Add a error message to system.log file
     * @param message 
     */
    public void addErrorMessage(String message) {
        try {
            writer = new FileWriter("system.log", true);
            
            writer.write(message);
            
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(LogWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
