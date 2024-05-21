/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * StringManipulation Class contains a number of methods to manipulate strings.
 * 
 * @author MatteHammar
 * @see String
 */
public class StringManipulation {
    
    private static final String NAMETAGTOKENS = "<>";  
    private static final String KEYTAGTOKENS = "[]";
    
    /**
     * Finds and Removes Keytags ('[', ']') from specified String.
     * It removes all characters inbetween the keytags.
     * 
     * @param str Specified String
     * @return A String without keytags
     */
    public static String removeKeytags(String str) {  
        // Declare variables
        int beginIndex = -1; 
        int endIndex = -1;
        
        // Find Tokens in String
        for(int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == KEYTAGTOKENS.charAt(0))
                beginIndex = i;
            else if(str.charAt(i) == KEYTAGTOKENS.charAt(1)) {
                endIndex = i;
                if(beginIndex != -1)
                    break;
            }
        }
      
        // If no Tokens found, return str
        if(beginIndex == -1 || endIndex == -1)
            return str;

        // Remove tokens and everything between
        if(beginIndex == 0)
            return str.substring(endIndex + 1, str.length());
        
        else if(endIndex == str.length())
            return str.substring(0, beginIndex - 1);
        
        return str.substring(0, beginIndex) + str.substring(endIndex + 1, str.length());
    }
    
    /**
     * Removes Nametags from specified String
     * @param str Specified String
     * @return Nickname without Nametags
     */
    public static String removeNametags(String str) {        
        
        // Check for tokens
        if(str.charAt(0) == NAMETAGTOKENS.charAt(0) && str.charAt(str.length() - 1) == NAMETAGTOKENS.charAt(1))
            // Remove tokens
            return str.substring(1, str.length() - 1);
        
        // If no tokens found, return str
        return str;
    }
    
    /**
     * Splits a Message string into a pair, containing Author and message separated.
     * @param str Message string to be split
     * @return Pair of strings
     */
    public static Pair<String, String> splitMessageString( String str ) {
        Pair<String, String> message;
        str = removeKeytags(str);
        
        int beginIndex = -1, endIndex = -1;
        
        for(int i = 0; i < str.length(); i++) {
            if( str.charAt(i) == NAMETAGTOKENS.charAt(0))
                beginIndex = i;
            else if( str.charAt(i) == NAMETAGTOKENS.charAt(1)) {
                endIndex = i;
                if(beginIndex != -1)
                    break;
            }
                
        }
        message = new Pair(str.substring(beginIndex + 1, endIndex), str.substring(endIndex + 1));
        return message;
    }
    
    public static List<String> serverMessageToArray(String message) {
        if(message.isEmpty())
            return null;
        
        List<String> messList = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        
        for(int i = 0; i < message.length(); i++) {
            if(message.charAt(i) == '<')
                indexes.add(i);
            else if(message.charAt(i) == '>')
                indexes.add(i);
        }
        
        if(indexes.size() < 2 || indexes.size() % 2 != 0) {
            CustomExceptionHandler e = new CustomExceptionHandler(new CustomException("Error: wrong format from server, ignoring message"));
            return null;
        }
        
        for(int i = 0; i < indexes.size() - 1; i += 2) {
            messList.add(message.substring(indexes.get(i) + 1, indexes.get(i + 1)));
        }
        
        return messList;
    }
}
