/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author MatteHammar
 */
public class CustomExceptionHandler {
    
    private LogWriter logWriter;


    public CustomExceptionHandler() {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + "Unknown error" + "\n");
    }
    
    public CustomExceptionHandler(CustomException e) {
        logWriter = new LogWriter();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date date = new Date();
        logWriter.addErrorMessage(df.format(date) + ": " + e.toString() + "\n");
    }
    
}
