/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * The class FriendPanel is the panel that handles friend JList
 * @author MatteHammar
 */
public class FriendPanel extends JPanel{
    public JList<Friend> friendList;
    
    private DefaultListModel<Friend> friends;
    
    /**
     * Construcs a friendJList using DAO object
     */
    public FriendPanel() { 
        // Create a DataAccessObject
        DAO dataObject = DAO.getInstance();
        
        // Initalize JList
        friends = new DefaultListModel<>();
        friendList = new JList<>(friends);        
        friendList.setCellRenderer(new MyCellRenderer());

        // Add all Friends in DAO, except myself
        for (Friend friend : dataObject.getFriendList()) {
            if (friend.getNickname().equals(DAO.SELFNICKNAME))
                continue;    
            friends.addElement(friend);
        }
        // Set JList options
        friendList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        friendList.setFont(new Font("Courier New", 1, 25));
        friendList.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        friendList.setEnabled(false);
    }
    
    public void addFriend(Friend friend) {
        
        int index = -1;  
        friend.setOnlineStatus(Boolean.TRUE);
        
        for(int i = 0; i < friends.getSize(); i++) {
            if(friend.getNickname().compareTo(friends.get(i).getNickname()) < 0) {
                index = i;
                break;
            }
        }
        
        if(index > -1) 
            friends.add(index, friend);
        else
            friends.addElement(friend);       
    }
    
    /**
     * Set message indicator for specified friend
     * @param friend - Friend
     */
    public void messageIndicator(Friend friend) {
        friend.setMessageIndicator(true);
        updateFriend(friend);
    }
    
    /**
     * Update given friend from DAO
     * @param friend 
     */
    public void updateFriend(Friend friend) {
        int currentIndex = 0;
        boolean selected = false;
        if(!friendList.isSelectionEmpty()) {
            currentIndex = friendList.getSelectedIndex();
            selected = true;
        }
        
        for(int i = 0; i < friends.getSize(); i++) {
            if(friends.get(i).getNickname().equals(friend.getNickname())) {
                friends.remove(i);
                addFriend(friend);
                if(selected && currentIndex == i)
                    friendList.setSelectedIndex(i);
                return;
            }
        }
    }
    
    /**
     * Logout given friend from friendlist
     * @param friend 
     */
    public void logoutFriend(Friend friend) { 
        
        int index = -1;
        friend.setOnlineStatus(Boolean.FALSE);
        
        // Remove friend, then add them again to update. (Probably a cleaner way to do this)
        for(int i = 0; i < friends.getSize(); i++) {
            // temporarily remove
            if(friends.get(i).getNickname().equals(friend.getNickname())) {
                friends.remove(i);
                continue;
            }
            // If element is online no point comparing lexicographically
            if(friends.get(i).getOnlineStatus())
                continue;
            // Find where friend belongs lexicographically
            if(friend.getNickname().compareTo(friends.get(i).getNickname()) < 0) {
                index = i;
                break;
            }
        }
        
        // Add friend to specified index
        if(index > -1) 
            friends.add(index, friend);
        // Or if no appropriate index was found, add to the back of the list
        else
            friends.addElement(friend);
    }
    
    /**
     * Creates the friend panel
     */
    public void createFriendPanelWindow() {
        // Set JPanel options
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        setBackground(Color.getColor("beige"));
        
        // Create a Label
        JLabel friendLabel = new JLabel("Friends List");
        friendLabel.setFont(new Font("Arial", 4, 25));
        
        JScrollPane friendListScrollPane = new JScrollPane(friendList);
        
        // Create an edit friend button
        JButton btn = new JButton("Edit Friend");
        
        // Open a new window to edit Friends attributes
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If no friend was selected return
                if(friendList.isSelectionEmpty())
                    return;
                
                // Create new Frame and set options
                JFrame friendFrame = new JFrame("Friend: " + friendList.getSelectedValue().getNickname());
                friendFrame.setSize(new Dimension(400, 500));
                friendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                friendFrame.setVisible(true);
                friendFrame.setBackground(Color.white);
                friendFrame.setLayout(new FlowLayout());
                
                // Create Textfields and Labels for each attribute
                JTextField nickNField = new JTextField();
                JTextField fullNField = new JTextField();
                JTextField lastIPField = new JTextField(); 
                
                nickNField.setFont(new Font("Courier New", 1, 24));
                nickNField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                
                fullNField.setFont(new Font("Courier New", 1, 24));
                fullNField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                
                lastIPField.setFont(new Font("Courier New", 1, 24));
                lastIPField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                
                JLabel l1 = new JLabel("NICKNAME");
                JLabel l2 = new JLabel("FULLNAME");
                JLabel l3 = new JLabel("LASTIP");
                
                // Create a Panel for each attribute
                // Nickname panel
                JPanel p1 = new JPanel();
                p1.setLayout(new BorderLayout());
                p1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                p1.setBackground(Color.getColor("beige"));
                p1.add(l1, BorderLayout.NORTH);
                p1.add(nickNField, BorderLayout.CENTER);
                
                // Fullname panel
                JPanel p2 = new JPanel();
                p2.setLayout(new BorderLayout());
                p2.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                p2.setBackground(Color.getColor("beige"));
                p2.add(l2, BorderLayout.NORTH);
                p2.add(fullNField, BorderLayout.CENTER);
                
                // IP panel
                JPanel p3 = new JPanel();
                p3.setLayout(new BorderLayout());
                p3.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
                p3.setBackground(Color.getColor("beige"));
                p3.add(l3, BorderLayout.NORTH);
                p3.add(lastIPField, BorderLayout.CENTER);
                
                // Get attributes from friend and assign them to textfields
                nickNField.setText(friendList.getSelectedValue().getNickname());
                fullNField.setText(friendList.getSelectedValue().getFullname());
                lastIPField.setText(friendList.getSelectedValue().getLastSeenIP());
                
                
                // Create a confirm button to change attributes
                JButton btn = new JButton("Confirm");
                btn.addActionListener(new ActionListener() {
                    @Override
                    // Change some attributes
                    public void actionPerformed(ActionEvent e) {
                        friendList.getSelectedValue().setFullname(fullNField.getText());
                        friendList.getSelectedValue().setLastSeenIP(lastIPField.getText());
                        friendFrame.dispose();
                    }
                });
                
                // Add all components to temporary frame
                friendFrame.add(p1);
                friendFrame.add(p2);
                friendFrame.add(p3);
                friendFrame.add(btn);
            }
        });
        
        // Add all components to JPanel
        add(friendListScrollPane, BorderLayout.CENTER);
        add(friendLabel, BorderLayout.NORTH);
        add(btn, BorderLayout.SOUTH);
    }
}
