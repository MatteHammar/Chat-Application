/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MatteHammar
 */
public class SystemExceptionHandler {
    
    private LogWriter logWriter;
        
    
    public SystemExceptionHandler() {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + "Unknown error" + "\n");
    }
    
    public SystemExceptionHandler(Exception e) {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
    
    public SystemExceptionHandler(FileNotFoundException e, String filename) {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
    
    public SystemExceptionHandler(IOException e, String filename) {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
    
    public SystemExceptionHandler(IOException e, GUI guiInstance) {
        logWriter = new LogWriter();
        
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        
        if(guiInstance.errorWindow != null) return;
        
        guiInstance.chatPanel.chatText.setText(guiInstance.chatPanel.chatText.getText() + "ERROR: Not Connected to server!");
        guiInstance.createErrorWindow(df.format(date) + ": " + e.toString());
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
    
    public SystemExceptionHandler(UnknownHostException e) {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
}
