/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;


/**
 * The class User implements Friend interface.
 * Each object of User is a Friend with all information regarding that friend.
 * 
 * @author MatteHammar
 */
public class User implements Friend{
    
    private String nickName;
    private String fullName;
    private String lastSeenIP;
    private Chat chat;
    private Boolean onlineStatus;
    private Boolean messageIndicator;
    
    // ????
    private String imageURL;
    
    /**
     * Constructs an empty Friend
     */
    public User() {
    }
    
    /**
     * Constructs a Friend object with parameters.
     * 
     * @param nName - Nickname of that friend
     * @param fName - Fullname of that friend
     * @param IP - Last seen IP-Address of that friend
     * @param chat - The chat bound to that friend
     */
    public User(String nName, String fName, String IP, Chat chat) {
        nickName = nName;
        fullName = fName;
        lastSeenIP = IP;
        this.chat = chat;
        onlineStatus = false;
        messageIndicator = false;
    }  
    
    /**
     * Get the nickname for that friend.
     * @return - String
     */
    @Override
    public String getNickname() {
        return nickName;
    }
    /**
     * Set the nickname for that friend.
     * @param name - Nickname
     */
    @Override
    public void setNickname(String name) {
        nickName = name;
    }
    
    /**
     * Get the fullname for that friend.
     * @return - String
     */
    @Override
    public String getFullname() {
        return fullName;
    }
    /**
     * Set the fullname for that friend.
     * @param fName - Fullname
     */
    @Override
    public void setFullname(String fName) {
        fullName = fName;
        
        FriendWriter fWriter = new FriendWriter();
        fWriter.editFullname(this, fName);
    }
    
    /**
     * Get the last seen IP Address for that friend.
     * @return - String
     */
    @Override
    public String getLastSeenIP() {
        return lastSeenIP;
    }
    /**
     * Set the last seen IP Address for that friend.
     * @param IP - IP Address
     */
    @Override
    public void setLastSeenIP(String IP) {
        lastSeenIP = IP;
        
        FriendWriter fWriter = new FriendWriter();
        fWriter.editLastIP(this, IP);
    }
    
    /**
     * Get the chat for that friend.
     * @return - Chat
     * @see Chat
     */
    @Override
    public Chat getChat() {
        return chat;
    }
    /**
     * Set the chat for that friend.
     * @param chat - The chatlog
     */
    @Override
    public void setChat(Chat chat) {
        this.chat = chat;
    }
    
    /**
     * Add a message to a Friends chat.
     * @param msg - Message to be added
     */
    @Override
    public void addMessage(Message msg){
        // Add message to Chat<Message> list
        this.getChat().addMessage(msg);
    }
    
    // ???
    public String getImage() {
        return imageURL;
    }

    @Override
    public Boolean getOnlineStatus() {
        return onlineStatus;
    }

    @Override
    public void setOnlineStatus(Boolean status) {
        onlineStatus = status;
    }

    @Override
    public Boolean getMessageIndicator() {
        return messageIndicator;
    }

    @Override
    public void setMessageIndicator(Boolean status) {
        messageIndicator = status;
    }
}
