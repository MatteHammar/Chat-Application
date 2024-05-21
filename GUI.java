/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The class GUI controls all other Graphical classes
 * And is the Main Frame for the GUI.
 * @author MatteHammar
 */
public class GUI {
    
    public JRadioButtonMenuItem privateChatButton;
    public JRadioButtonMenuItem publicChatButton;
    public FriendPanel friendPanel;
    public ChatPanel chatPanel;
    
    public JFrame errorWindow;
    public JButton reconnectButton;
    
    private static GUI guiObject;
    private Client clientInstance;
    
    private GUI() {
        chatPanel = new ChatPanel();
        friendPanel = new FriendPanel();
        
        privateChatButton = new JRadioButtonMenuItem("Private");
        publicChatButton = new JRadioButtonMenuItem("Public");
    }
    
    public static GUI getInstance() {
        if(guiObject == null)
            return new GUI();
        return guiObject;
    }
    
    public void setClientInstance(Client client) {
        clientInstance = client;
    }
    
    private void setPublicChat() {
        publicChatButton.setEnabled(true);
        
        DAO dataObject = DAO.getInstance();

        friendPanel.friendList.setEnabled(false);                
        String log = "";
        if(!dataObject.getFriendList().get(0).getChat().isEmpty()) {
            for( Message mess : dataObject.getFriendList().get(0).getChat().getMessages()) {
                log += "<" + mess.getAuthor().getNickname() + ">" + mess.getMessage() + "\n";
            }
        }

        chatPanel.chatText.setText(log);
        chatPanel.chatLabel.setText("Public Chatroom");
        friendPanel.friendList.clearSelection();
    }
    
    public void createErrorWindow(String errorMessage) {
        errorWindow = new JFrame("Error Window");    
        errorWindow.setSize(new Dimension(400, 500));
        errorWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorWindow.setVisible(true);
        errorWindow.setBackground(Color.white);
        errorWindow.setLayout(new BorderLayout());
        
        JTextArea errorTextArea = new JTextArea();
        errorTextArea.setLineWrap(true);
        errorTextArea.setEditable(false);
        errorTextArea.setFont(new Font("Courier New", 1, 16));
        errorTextArea.setMargin(new Insets(10, 10, 10, 10));
        errorTextArea.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        errorTextArea.setText(errorMessage);
        
        reconnectButton = new JButton("Reconnect");
        reconnectButton.setVisible(true);
        reconnectButton.addActionListener(new ActionListener() {
            @Override
            // Change some attributes
            public void actionPerformed(ActionEvent e) {
                errorTextArea.setText("Trying to connect...");
                
                try {
                    clientInstance.reconnect(DAO.SELFNICKNAME, DAO.SELFFULLNAME);
                    errorTextArea.setText("Succesfully Connected to server!");
                } catch (IOException ex) {
                    errorTextArea.setText("Could not connect to server!");
                }
            }
        });
        
        errorWindow.add(errorTextArea, BorderLayout.CENTER);
        errorWindow.add(reconnectButton, BorderLayout.SOUTH);
    }

    /**
     * Constructs a GUI using other graphical classes
     */
    public void createGUIWindow() {
        // Get DAO
        
        
        // Create frame and set options
        JFrame frame = new JFrame("My first GUI");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setBackground(Color.white);
        frame.setLayout(new BorderLayout());
        
        // Disconnect from server on close
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientInstance.logout();
                System.exit(0);
            }
        });
        
        // Create menubar
        JMenuBar menuBar = new JMenuBar();
        JMenu m1 = new JMenu("File");       
        JMenu m2 = new JMenu("Show");
        JMenuItem exit = new JMenuItem("Exit");
        // Disconnect from server on close
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                clientInstance.logout();
                System.exit(0);
            }
        });
        m1.add(exit);
        
        
        // Add radiobuttons to group and menu 2
        ButtonGroup bGroup = new ButtonGroup();
        
        bGroup.add(privateChatButton);
        bGroup.add(publicChatButton);
        m2.add(privateChatButton);
        m2.add(publicChatButton);

        // Add menus to menubar
        menuBar.add(m1);
        menuBar.add(m2);   
        
        // Add menubar to frame
        frame.add(menuBar, BorderLayout.NORTH);
        
        // Initiate ChatPanel, FriendPanel and ChatInputPanel
        // And add them to frame
        chatPanel.createChatWindow();
        friendPanel.createFriendPanelWindow();
        ChatInputPanel chatInput = new ChatInputPanel();
        chatInput.createChatInputWindow();
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(friendPanel, BorderLayout.EAST);
        frame.add(chatInput, BorderLayout.SOUTH);
        
        // Send message button
        // Send message to DAO.Friend and ChatPanel
        // Sends messages to server using client
        chatInput.sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAO dataObject = DAO.getInstance();
                if(!(chatInput.inputField.getText().equals(""))){
                    Friend me = dataObject.getFriendList().get(0);
                    if(privateChatButton.isSelected()){
                        if(!friendPanel.friendList.isSelectionEmpty()) {
                            clientInstance.sendPrivateMessage(chatInput.inputField.getText(), friendPanel.friendList.getSelectedValue());
                            dataObject.addMessage(friendPanel.friendList.getSelectedValue(), new Message(chatInput.inputField.getText(), me));
                        }
                    }
                    else if(publicChatButton.isSelected()) {
                        clientInstance.sendPublicMessage(chatInput.inputField.getText());
                        dataObject.addMessage(me, new Message(chatInput.inputField.getText(), me));
                    }
                    else {
                        return;
                    }
                    chatPanel.chatText.setText(chatPanel.chatText.getText() + "<" + me.getNickname() + ">" + chatInput.inputField.getText() + "\n");
                    chatInput.inputField.setText("");
                }
            }
        });

        // Private chatroom Menubutton
        // Switches ChatText to private and enables Friend-JList
        privateChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(privateChatButton.isSelected()) {
                    friendPanel.friendList.setEnabled(true);
                    
                    if(friendPanel.friendList.isSelectionEmpty()) {
                        chatPanel.chatText.setText("");
                        chatPanel.chatLabel.setText("Private Chatroom");
                    }
                }
                else
                    friendPanel.friendList.setEnabled(false);
            }
        });
        
        // Public chatroom Menubutton
        // Switches ChatText to public and disables Friend JList
        publicChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DAO dataObject = DAO.getInstance();
                if(publicChatButton.isSelected()) {
                    friendPanel.friendList.setEnabled(false);                
                    String log = "";
                    if(!dataObject.getFriendList().get(0).getChat().isEmpty()) {
                        for( Message mess : dataObject.getFriendList().get(0).getChat().getMessages()) {
                            log += "<" + mess.getAuthor().getNickname() + ">" + mess.getMessage() + "\n";
                        }
                    }

                    chatPanel.chatText.setText(log);
                    chatPanel.chatLabel.setText("Public Chatroom");
                    friendPanel.friendList.clearSelection();
                }
                else
                    friendPanel.friendList.setEnabled(true);
            }
        });
        
        // Friend JList selectionHandler
        // Sends the Chat of selected Friend to ChatText, given that JList is enabled (in Private mode)
        friendPanel.friendList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(friendPanel.friendList.isSelectionEmpty())
                    return;
                Friend selectedFriend = friendPanel.friendList.getSelectedValue();
                if(selectedFriend.getMessageIndicator()) {
                    selectedFriend.setMessageIndicator(false);
                }
                DAO dataObject = DAO.getInstance();
                String log = "";
                if(!dataObject.getFriend(friendPanel.friendList.getSelectedValue().getNickname()).getChat().isEmpty()) {
                    for ( Message mess : dataObject.getFriend(friendPanel.friendList.getSelectedValue().getNickname()).getChat().getMessages()) {
                        log += "<" + mess.getAuthor().getNickname() + ">" + mess.getMessage() + "\n";
                    }
                }
                chatPanel.chatText.setText(log);
                chatPanel.chatLabel.setText("Private Chatroom with " + friendPanel.friendList.getSelectedValue().getNickname());
            }
        });
        
        setPublicChat();
    }
}
