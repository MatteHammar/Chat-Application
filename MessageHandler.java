/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labb6;

import java.util.List;

/**
 * The Class MessageHandler handles the messages recived by the server, and carries out appropriate actions.
 * @author MatteHammar
 */
public class MessageHandler {
    
    private final DAO dataObject;
    private GUI gui;
    
    /**
     * Constructs a MessageHandler object.
     * @param gui - UI Object
     */
    public MessageHandler(GUI gui) {
        dataObject = DAO.getInstance();
        //this.gui = gui;
        this.gui = gui;
    }

    /**
     * This method decides what actions is to be taken ,depending on server message, then executes that action. 
     * @param message - Message recived from server
     */
    public void executeMessage(String message) {
        List<String> messages = StringManipulation.serverMessageToArray(message);
        if(messages == null) {
            CustomExceptionHandler e = new CustomExceptionHandler(new CustomException("Error: wrong format from server, ignoring message"));
            return;
        }
        
        
        switch(messages.get(0)) {
            // Try to add friend
            case "FRIEND":
                // If friend already exits, dont add
                if(dataObject.getFriend(messages.get(1)) != null) {
                    gui.friendPanel.updateFriend(dataObject.getFriend(messages.get(1)));
                    break;
                }
                // add friend to DAO and Jfriendlist
                Friend friend = new User(messages.get(1), messages.get(2), messages.get(3), new ChatLog());
                dataObject.addFriend(friend);
                gui.friendPanel.addFriend(friend);
                break;
                
            // Add a public message
            case "PUBLIC":
                // If incomplete format ignore
                if(messages.size() != 3)
                    break;
                // If friend sending message does not exist display error
                if(dataObject.getFriend(messages.get(1)) == null) {
                    CustomExceptionHandler m = new CustomExceptionHandler(new CustomException("Message recieved from '" + messages.get(1) + "', which does not exist on friendlist"));
                }
                
                // Add message to DAO and to the chatText window if public chat is active
                dataObject.addMessage(dataObject.getFriend(DAO.SELFNICKNAME), new Message(messages.get(2), dataObject.getFriend(messages.get(1))));
                if(gui.publicChatButton.isSelected())
                    gui.chatPanel.chatText.setText(gui.chatPanel.chatText.getText() + "<" + messages.get(1) + ">" + messages.get(2) + "\n");
                break;
                
            // Add a private message
            case "PRIVATE":
                // Display server message then exit
                if(messages.get(1).equals("SERVER")) {
                    gui.chatPanel.chatText.setText(gui.chatPanel.chatText.getText() + "<SERVER>" + messages.get(2) + "\n");
                    break;
                }

                
                // Add message to given friend in DAO
                Friend fr = dataObject.getFriend(messages.get(1));
                if(fr == null) return;
                
                dataObject.addMessage(fr, new Message(messages.get(2), fr));
                
                fr.setMessageIndicator(true);
                gui.friendPanel.updateFriend(fr);
                // display message in chatwindow if that private chat is active
                /*
                if(gui.privateChatButton.isSelected() && !gui.friendPanel.friendList.isSelectionEmpty()) {
                    if(gui.friendPanel.friendList.getSelectedValue().getNickname().equals(messages.get(1))) {
                        gui.chatPanel.chatText.setText(gui.chatPanel.chatText.getText() + "<" + messages.get(1) + ">" + messages.get(2) + "\n");
                    }
                }*/
                break;
            // Logout requested friend
            case "LOGOUT":
                // logout friend from Jfriendlist
                Friend f = dataObject.getFriend(messages.get(1));
                if(f != null)
                    gui.friendPanel.logoutFriend(f);
                
                break;
            default:
                String errorMessage = "Error: Unknown syntax from server, message recieved: ";
                for(String m : messages)
                    errorMessage += m;
                CustomExceptionHandler exception = new CustomExceptionHandler(new CustomException(errorMessage));
        }
    }
}
