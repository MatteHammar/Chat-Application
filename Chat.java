/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.util.List;

/**
 * The Chat interface declares what functions a chat must have.
 * @author MatteHammar
 */
public interface Chat {
    public void addMessage(Message msg);
    public List<Message> getMessages();
    public Boolean isEmpty();
}
