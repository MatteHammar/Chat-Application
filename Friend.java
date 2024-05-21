/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

/**
 * The interface Friend represent what a Friend is and what functions must be implemented.
 *
 * @author MatteHammar
 */
public interface Friend {
    String getNickname();
    String getFullname();
    String getLastSeenIP();
    Chat getChat();
    Boolean getOnlineStatus();
    Boolean getMessageIndicator();
    
    void setNickname(String name);
    void setFullname(String name);
    void setLastSeenIP(String IP);
    void setChat(Chat log);
    void setOnlineStatus(Boolean status);
    void setMessageIndicator(Boolean status);
    
    void addMessage(Message msg);
}
