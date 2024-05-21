/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The class ChatPanel represents a Panel containing a chatText area.
 * @author MatteHammar
 */
public class ChatPanel extends JPanel{
    
    public JTextArea chatText;
    public JLabel chatLabel;

    /**
     * Constructs a ChatPanel with chat window and label
     */
    public ChatPanel() { 
        // Create a JTextArea as chat
        chatText = new JTextArea();
        chatText.setLineWrap(true);
        chatText.setEditable(false);
        chatText.setFont(new Font("Courier New", 1, 16));
        chatText.setMargin(new Insets(10, 10, 10, 10));
        chatText.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        
        // Create a JLabel as chatlabel
        chatLabel = new JLabel("Chatroom");
        chatLabel.setFont(new Font("Courier New", 1, 25));
    }
    
    public void createChatWindow() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,15,15,0));
        setBackground(Color.getColor("beige"));
        JScrollPane chatScrollPane = new JScrollPane(chatText);
        
        add(chatScrollPane, BorderLayout.CENTER);
        add(chatLabel, BorderLayout.NORTH);
    }
    
    public void setText(String text) {
        chatText.setText(text);
    }
}
