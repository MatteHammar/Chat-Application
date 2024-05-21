/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.util.ArrayList;
import java.util.List;

/**
 * The class ChatLog, which implements Chat, represents a chatlog which contains a list of Messages.
 * 
 * @author MatteHammar
 */
public class ChatLog implements Chat{
    
    private List<Message> chatLog;
    
    /**
     * Constructs an empty ChatLog.
     */
    public ChatLog() {
        chatLog = new ArrayList<>();
    }
    
    /**
     * Constructs a ChatLog with the given Array of Message
     * @param chatLog - Array of Message
     * @see Message
     */
    public ChatLog(List<Message> chatLog) {
        this.chatLog = chatLog;
    }
    
    /**
     * Adds a Message to the chatlog
     * @param msg - A Message object
     * @see Message
     */
    @Override
    public void addMessage(Message msg) {
        chatLog.add(msg);
    }
    
    /**
     * Gets all Messages from the chatlog
     * @return - List of Messages
     * @see Message
     */
    @Override
    public List<Message> getMessages() {
        return chatLog;
    }
    
    /**
     * Checks if the Chatlog is empty
     * @return - Boolean
     */
    @Override
    public Boolean isEmpty() {
        return chatLog.isEmpty();
    }
}
