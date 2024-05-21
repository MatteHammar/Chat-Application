/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class creates and maintains the connection with the server.
 * @author MatteHammar
 */
public class Client {

    // Networking objects/variables
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private final String hostName;
    private final int port;
    
    // Message formats for server
    private final String register = "<REGISTER><%1$s><%2$s><%3$s><IMAGE>";
    private final String publicMessage = "<PUBLIC><%1$s><%2$s>";
    private final String privateMessage = "<PRIVATE><%1$s><%2$s><%3$s>";
    private final String logout = "<LOGOUT><%1$s>";
    
    // User info
    private String nickName;
    private String fullName;
    private boolean connected;
    private String ip;
    
    // For communicating to UI
    private GUI gui;
    
    /**
     * Construcs a client
     * @param hostName - URL for server
     * @param port - port for server
     */
    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
        
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex);
            this.ip = "127.0.0.1";
        }
    }
    
    /**
     * Set active Instance for communication
     * @param gui 
     */
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
    
    /**
     * Send a public message to server
     * @param message -  The content of message
     */
    public void sendPublicMessage(String message) {
        try {
            assertConnected();
            String mess = String.format(publicMessage, DAO.SELFNICKNAME, message);
            writer.println(mess);
            writer.flush();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, gui);
        }
    }
    
    /**
     * Send a private message to server
     * @param message - message to send
     * @param recieverFriend - the recipient friend
     */
    public void sendPrivateMessage(String message, Friend recieverFriend) {
        try {
            assertConnected();
            String mess = String.format(privateMessage, DAO.SELFNICKNAME, recieverFriend.getNickname(), message);
            writer.println(mess);
            writer.flush();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, gui);
        }
    }
    
    /**
     * Send a logout request to server

     */
    public void logout() {
        try {
            assertConnected();
            String mess = String.format(logout, DAO.SELFNICKNAME);
            writer.println(mess);
            writer.flush();
            socket.close();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, gui);
        }
        connected = false;
    }
    
    /**
     * Registers to the server with Nickname and fullname that is given.
     * @param nickName - Nickname
     * @param fullName - Fullname
     */
    public void register(String nickName, String fullName) {
        try {
            assertConnected();
            this.nickName = nickName;
            this.fullName = fullName;
            
            String message = String.format(register, nickName, fullName, ip);
            writer.println(message);
            writer.flush();
        } catch (IOException ex) {
            SystemExceptionHandler e = new SystemExceptionHandler(ex, gui);
        }
    }
    
    /**
     * Opens the socket to the server and gets input/output stream to socket.
     * @throws IOException 
     */
    public void connect() throws IOException {
        socket = new Socket(hostName, port);
        writer = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        connected = true;
        new ListenerThread(reader).start();
    }
    
    public void reconnect(String nickName, String fullName) throws IOException {
        connect();
        register(nickName, fullName);
    }
    
    /**
     * Checks if client is connected.
     * @throws java.io.IOException
     */
    public void assertConnected() throws IOException {
        if(!connected) throw new IOException();
    }
    
    /**
     * Listenerthread that listen using socket inputstream and delivers messages from server to a worker thread.
     */
    private class ListenerThread extends Thread {
        
        private final BufferedReader in;
        
        public ListenerThread(BufferedReader in) {
            this.in = in;
        }
        
        @Override
        public void run() {
            while(connected) {
                try {
                    String line = in.readLine();
                    new MessageHandlerThread(line).start();
                } catch (IOException ex) {
                    SystemExceptionHandler e = new SystemExceptionHandler(ex, gui);
                }
            }
        }
        
        /**
        * MessageHandlerThread is the thread that handles the messages from server.
        */
        private class MessageHandlerThread extends Thread {

            private final String message;

            public MessageHandlerThread(String message) {
                this.message = message;
            }

            @Override
            public void run() {
                MessageHandler mHandler = new MessageHandler(gui);
                mHandler.executeMessage(message);
            }
        }
    }
}
