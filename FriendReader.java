/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

/**
 * The FriendReader class reads friends from a file
 * 
 * @author MatteHammar
 */
public class FriendReader {
    
    public static final String DEFAULTFILE = "friends.list";
    private static String fileName = "";
    
    /**
     * Constructor sets filename to Defaultname
     */
    public FriendReader() {
        fileName = DEFAULTFILE;
    }
    
    /**
     * Constructor sets filename to argument
     * 
     * @param filename - The name of the friend file
     */
    public FriendReader(String filename) {
        // Set filename
        fileName = filename;
    }
    
    /**
     * Returns a sorted list, ( by nicknames ), of all friends nicknames and fullnames from friend file
     * 
     * @return - Array of Pair Nicknames and fullnames of all friends
     */
    public ArrayList<Pair<String, String>> getFriends() {
        
        try {
            // Initiate Scanner
            Scanner friendReader = new Scanner(new FileReader(fileName));
            ArrayList<Pair<String, String>> nameList = new ArrayList();
            
            // Read all lines
            while(friendReader.hasNext()) {
                // Get Nickname and Fullname and remove TAGS
                nameList.add(new Pair(StringManipulation.removeKeytags(StringManipulation.removeNametags(friendReader.nextLine())),
                        StringManipulation.removeKeytags(friendReader.nextLine())));
                
                // Skip LASTIP and IMAGE line
                friendReader.nextLine();
                friendReader.nextLine();
            }
            
            // Sort list by Nicknames
            Collections.sort(nameList, new Comparator<Pair<String, String>>() {
                @Override
                public int compare(Pair<String, String> p1, Pair<String, String> p2) {
                    String n1 = p1.getKey().toLowerCase();
                    String n2 = p2.getKey().toLowerCase();
                    return n1.compareTo(n2);
                }
            });
            
            // Close Scanner and Return list
            friendReader.close();
            return nameList;
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, fileName);
            FriendWriter writer = new FriendWriter();
            writer.createFriendList();
        }
        return null;
    }
   
    /**
     * Returns all information of a given friend.
     * Info[1] - Nickname
     * Info[2] - Fullname
     * Info[3] - LastSeenIP
     * Info[4] - Image
     * 
     * @param name - Nickname of the friend.
     * @return - An array of information.
     */
    public ArrayList<String> getFriendinfo(String name) {
        try {
            // Initiate Scanner and declare variables
            Scanner friendReader = new Scanner(new FileReader(fileName));
            String nickName = null;
            Boolean foundName = false;
            ArrayList<String> infoList = new ArrayList<>();
            
            
            // Go through entire file for a match
            while(friendReader.hasNext()) {
                // Get line
                nickName = friendReader.nextLine();
                while(nickName.isEmpty())
                    nickName = friendReader.nextLine();
                
                // If String starts and ends with NameTag then treat it as a Nickname
                if(nickName.charAt(0) == '<' && nickName.charAt(nickName.length() - 1) == '>') {
                    // Remove TAGS
                    nickName = StringManipulation.removeNametags(StringManipulation.removeKeytags(nickName));
                    // If Nicknames matches with search, then quit the search
                    if(nickName.equals(name)) {
                        foundName = true;
                        break;
                    }
                }
            }
            
            // If name was found, add info to array
            if(foundName) {
                infoList.add(nickName);
                infoList.add(StringManipulation.removeKeytags(friendReader.nextLine()));
                infoList.add(StringManipulation.removeKeytags(friendReader.nextLine()));
                infoList.add(StringManipulation.removeKeytags(friendReader.nextLine()));
                friendReader.close();
                return infoList;
            }
            // Return empty array if the name was not found
            friendReader.close();
            return infoList;
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, fileName);
            FriendWriter writer = new FriendWriter();
            writer.createFriendList();
        }
        return null;
    }
}
