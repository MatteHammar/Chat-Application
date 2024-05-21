/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * The class DAO is the Data Access Object to all friend data.
 * 
 * @author MatteHammar
 */
public class DAO {
    // My own information
    public static String SELFNICKNAME = "labb6";
    public static String SELFFULLNAME = "Sven Svensson";
    public static final String SELFIP = "127.0.0.1";
    
    // List of friends
    private List<Friend> friendList;
    private static DAO dataAccessObject;
    
    /**
     * Constructs the friend data using IO classes.
     */
    private DAO() {
        friendList = new ArrayList();
        // Create myself as Friend
        friendList.add(new User(SELFNICKNAME, SELFFULLNAME, SELFIP, new ChatLog()));
        
        // Get all the Friends nicknames
        FriendReader fReader = new FriendReader();
        List<Pair<String, String>> friendNames = new ArrayList<>();  
        friendNames = fReader.getFriends();
        if(friendNames == null) {
            CustomExceptionHandler e = new CustomExceptionHandler(new CustomException("No friends found in " + FriendReader.DEFAULTFILE));
            return;
        }
        
        // Create a Friend object for every friend in friend file
        ArrayList<String> info = null;

        for( Pair<String, String> name : friendNames ) {
            info = fReader.getFriendinfo(name.getKey());
            friendList.add(new User(info.get(0), info.get(1), info.get(2), new ChatLog()));
            friendList.get(friendList.size() - 1).setChat(getLog(friendList.get(friendList.size() - 1)));
            
        }
        // Finally when all Friends are made, create the public Chat 
        friendList.get(0).setChat(getLog(friendList.get(0))); 
    }
    
    /**
     * Get instance of dao object
     * @return 
     */
    public static DAO getInstance() {
        if(dataAccessObject == null)
            return new DAO();
        return dataAccessObject;
    }
 
    /**
     * Returns a list of Friends
     * @return - Array of Friends
     * @see Friend
     */
    public List<Friend> getFriendList() {
        return friendList;
    }
    
    /**
     * Get the friend with the specified Nickname
     * @param nickName - Nickname for that Friend
     * @return - Friend with that Nickname
     * @see Friend
     */
    public Friend getFriend(String nickName) {
        for ( Friend f : friendList ) {
            if( f.getNickname().equals(nickName) )
                return f;
        }
        return null;
    }
    
    /**
     * Add a Friend to the list of Friends, also adds it to the friend file
     * @param friend - Friend to be added
     * @see Friend
     */
    public void addFriend(Friend friend) {
        if(getFriend(friend.getNickname()) != null) {
            return;
        }
        
        friendList.add(friend);
        
        FriendWriter fWriter = new FriendWriter();
        LogWriter lWriter = new LogWriter();

        fWriter.addFriend(friend);
        lWriter.createLog(friend.getNickname() + ".log");
    }
    
    /**
     * Add message to friends chat
     * @param friend - Friend to recieve new message
     * @param message -  message to add
     */
    public void addMessage(Friend friend, Message message) {
        if(friend == null) {
            CustomExceptionHandler m = new CustomExceptionHandler(new CustomException("Message recieved from 'null', which does not exist on friendlist"));
            return;
        }
        friend.addMessage(message);
        
        LogWriter logWriter = new LogWriter();
        logWriter.addMessage(friend, message);
    }
    
    /**
     * Get the chatlog for specified Friend.
     * Fetches the log from logfile.
     * @param friend - Friend
     * @return - Chat
     * @see Chat
     */
    private Chat getLog(Friend friend) {
        Friend correctFriend = new User();
        Boolean foundFriend = false;
        LogReader logReader = new LogReader(friend.getNickname() + ".log");
        List<Message> messageList = new ArrayList<>();
        List<String> chatLog = logReader.getLog();
        if(chatLog == null)
            return new ChatLog(messageList);
        
        for( String line : chatLog) {
            // Get Author for message
            for( Friend fr : friendList ) {
                // Split chatlog line into Nickname and message
                // Look which Friend has correct nickname and make him author
                if(fr.getNickname().equals(StringManipulation.splitMessageString(line).getKey())) {
                    foundFriend = true;
                    correctFriend = fr;
                }
            }
            // If friend was found create Message with that Friend as author
            if( foundFriend ) {
                messageList.add(new Message(StringManipulation.splitMessageString(line).getValue(), correctFriend));
            }
            
            // If no Friend was found create a NULL Friend as author
            else
                messageList.add(new Message(StringManipulation.splitMessageString(line).getValue(), new User()));
            
            // Reset
            foundFriend = false;
            correctFriend = new User();
        }
        return new ChatLog(messageList);
    }
}
