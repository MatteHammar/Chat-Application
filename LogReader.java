/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * The LogReader class reads logfiles.
 * 
 * @author MatteHammar
 */
public class LogReader {
    private static String fileName;  
    
    /**
     * Constructor sets with logfile to read.
     * 
     * @param filename - Name of logfile
     */
    public LogReader(String filename) {
        fileName = filename;
    }
    
    /**
     * Returns the content of the logfile.
     * 
     * @return - Array of Strings
     */
    public ArrayList<String> getLog() {
        try {
            Scanner logReader = new Scanner(new FileReader(fileName));
            
            ArrayList<String> log = new ArrayList<>();
            
            while( logReader.hasNext() ) {
                log.add(logReader.nextLine());
            }
            
            
            logReader.close();
            return log;
        } catch (FileNotFoundException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, fileName);
            LogWriter writer = new LogWriter();
            writer.createLog(fileName);
        }
        return null;
    }
}
