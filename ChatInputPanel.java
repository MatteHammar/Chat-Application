/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The class ChatInputPanel is a JPanel containing an inputfield.
 * @author MatteHammar
 */
public class ChatInputPanel extends JPanel {
    
    public JTextField inputField;
    public JButton sendButton;
    
    private final String DEFAULTMESSAGE = "Enter Message Here...";
    
    /**
     * Constructs a JPanel with an inputfield.
     */
    public ChatInputPanel() {
        // Create JTextField as chat inputfield
        inputField = new JTextField(DEFAULTMESSAGE);
        inputField.setFont(new Font("Courier New", 1, 25));
        inputField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        // Remove defaultmessage when a user wants to type
        inputField.addMouseListener(new MouseAdapter() {         
            @Override
            public void mouseClicked(MouseEvent e) {
                if(inputField.getText().equals(DEFAULTMESSAGE));
                    inputField.setText("");
            }
        });
        
        // Create send button
        sendButton = new JButton("Send");
    }
    
    public void createChatInputWindow() {
        // Set JPanel options
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,15,15,0));
        setBackground(Color.getColor("beige"));
        
        // Add both components to JPanel
        add(inputField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }
}
