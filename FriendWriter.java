/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class for writing Friends to a file
 * 
 * @author MatteHammar
 */
public class FriendWriter {
    private FileWriter writer; 
    private static final String DEFAULTFILE = "friends.list";
    
    /**
     * Constructor
     * 
     */
    public FriendWriter() {
    }
    /**
     * This method is used to add friends to file
     * 
     * @param friend - A Friend object
     * @see Friend
     */
    public void addFriend(Friend friend) {
        try {
            writer = new FileWriter(DEFAULTFILE, true);
            
            writer.write("<" + friend.getNickname() + ">\n");
            writer.write("[FULLNAME]" + friend.getFullname() + "\n");
            writer.write("[LASTIP]" + friend.getLastSeenIP()+ "\n");
            writer.write("[IMAGE]no.image\n");
            writer.flush();
            
            writer.close();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE);
        }
    }
    
    /**
     * Edits a Friend attributes in friend file
     * @param friend - Friend to be changed
     * @param nickName - new Nickname
     * @param fullName - new Fullname
     * @param lastIP - new IP
     * @param imageURL - new Image
     */
    public void editFriend(Friend friend, String nickName, String fullName, String lastIP, String imageURL) {
        try {
            File defaultFile = new File(DEFAULTFILE);
            File tempFile = new File(DEFAULTFILE + ".tmp");
            Scanner reader = new Scanner(defaultFile);
            writer = new FileWriter(tempFile, true);
            String line;
            
            while(reader.hasNextLine()) {
                line = reader.nextLine();
                
                if(StringManipulation.removeNametags(line).equals(friend.getNickname())) {
                    writer.write("<" + nickName + ">" + "\n");
                    writer.write("[FULLNAME]" + fullName + "\n");
                    writer.write("[LASTIP]" + lastIP + "\n");
                    writer.write("[IMAGE]" + imageURL + "\n");
                }
                else
                    writer.write(line + "\n");
            }
            writer.close();
            reader.close();
            defaultFile.delete();
            Boolean success = tempFile.renameTo(defaultFile);
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE);
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE + ".tmp");
        } 
    }
    
    /**
     * Edits a Friend fullname in friend file
     * @param friend - Friend to be changed
     * @param fullName - new Fullname
     */
    public void editFullname(Friend friend, String fullName) {
        try {
            File defaultFile = new File(DEFAULTFILE);
            File tempFile = new File(DEFAULTFILE + ".tmp");
            Scanner reader = new Scanner(defaultFile);
            writer = new FileWriter(tempFile, true);
            String line;
            
            while(reader.hasNextLine()) {
                line = reader.nextLine();
                
                if(StringManipulation.removeNametags(line).equals(friend.getNickname())) {
                    writer.write(line + "\n");
                    writer.write("[FULLNAME]" + fullName + "\n");
                    reader.nextLine();
                }
                else
                    writer.write(line + "\n");
            }
            writer.close();
            reader.close();
            defaultFile.delete();
            Boolean success = tempFile.renameTo(defaultFile);
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE);
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE + ".tmp");
        } 
    }
    
    /**
     * Edits a Friends LastIP in friend file
     * @param friend - Friend to be changed
     * @param lastIP - new IP
     */
    public void editLastIP(Friend friend, String lastIP) {
        try {
            File defaultFile = new File(DEFAULTFILE);
            File tempFile = new File(DEFAULTFILE + ".tmp");
            Scanner reader = new Scanner(defaultFile);
            writer = new FileWriter(tempFile, true);
            String line;
            
            while(reader.hasNextLine()) {
                line = reader.nextLine();
                
                if(StringManipulation.removeNametags(line).equals(friend.getNickname())) {
                    writer.write(line + "\n");
                    writer.write(reader.nextLine() + "\n");
                    writer.write("[LASTIP]" + lastIP + "\n");
                    reader.nextLine();
                }
                else
                    writer.write(line + "\n");
                
            }
            writer.close();
            reader.close();
            defaultFile.delete();
            Boolean success = tempFile.renameTo(defaultFile);
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE);
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE + ".tmp");
        } 
    }
    /**
     * Creates a empty friend list file
     * 
     */
    public void createFriendList() {
        try {
            writer = new FileWriter(DEFAULTFILE);
            writer.close();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, DEFAULTFILE);
        } 
    }
}
