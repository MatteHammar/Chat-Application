/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * The class MyCellRenderer is the renderer for the Friend JList
 * @author Matte
 */
public class MyCellRenderer extends JLabel implements ListCellRenderer<Friend>{
    
    /**
     * Constructs the Cellrenderer
     */
    public MyCellRenderer() {
        setOpaque(true);
        setFont(new Font("Courier New", 1, 25));
    }

    /**
     * Creates the cellrenderer Component
     * @param list - List to be implemented for
     * @param friend - Element in list
     * @param index - Index selected
     * @param isSelected - Is selected
     * @param cellHasFocus - If cell has focus
     * @return - Component
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends Friend> list, Friend friend, int index, boolean isSelected, boolean cellHasFocus) {
        setText(friend.getNickname());
        setOpaque(true);

        if(isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        
        if(friend.getOnlineStatus())
            setForeground(Color.green);
        else
            setForeground(Color.red);
        if(friend.getMessageIndicator())
            setBackground(Color.orange);
        
        return this;
    }
    
}
