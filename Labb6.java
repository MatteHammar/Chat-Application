/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.IOException;

/**
 *
 * @author MatteHammar
 */
public class Labb6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI gui = GUI.getInstance();
        Client client = new Client("0.0.0.0", 8000);
        client.setGUI(gui);
        gui.setClientInstance(client);
        gui.createGUIWindow();
        
        try {
            client.connect();
            client.register(DAO.SELFNICKNAME, DAO.SELFFULLNAME);
        } catch (IOException ex) {
            SystemExceptionHandler s = new SystemExceptionHandler(ex, gui);
        }
    }
    
}
