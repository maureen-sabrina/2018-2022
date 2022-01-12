/**
 * 
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.01
 * @date 04-14-20
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Inbox implements Serializable {
	
	ArrayList<Message> messages = new ArrayList<> ();
	
	Set<User> blockedUsers = new HashSet<> ();

	public Inbox() {
		super();
	}
	
	public Inbox(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	/**
	 * @return the messages
	 */
	public ArrayList<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	
	
	public Message getMessage(int num) {
		return this.messages.get(num);
	}

	public void sendMessage(Message m) {
		//only accept message if user isn't blocked
		if (!blockedUsers.contains(m.fromUser))
			m.toUser.getInbox().messages.add(m);
	}


	public void block(User user) {
		blockedUsers.add(user);
	}
	
	public void unblock (User user) {
		blockedUsers.remove(user);
	}
	
	/**
	 * @param m is a message to be added to messages ArrayList
	 */
	public void addMessage(Message m) {
		messages.add(m);
	}

  /*
	 *@param fromUser is who is sending
	  @param message is what will be sent
	 */
	public void sendMessage(String toUsername, String message) {
		
		User targetUser = Session.activeSession().getStore().getCurrentUsers().get(toUsername);
		Message messageToSend = new Message (targetUser, Session.activeSession().getCurrentUser(), message);
		targetUser.getInbox().messages.add(messageToSend);
		

		/* This is really good! i am just worried that creating a new inbox might not
		be necessary
		ArrayList<Message> newMessages = m.toUser.getUserInbox().getMessages();
		newMessages.add(m);
		m.toUser.setUserInbox(new Inbox(newMessages));
		 */
	}

	/*
	/**
	 *@param toUser is who current user is replying to
	 *@param message is the message they want to send
	public void reply(User toUser, String message) {
		ArrayList<Message> newMessages = m.fromUser.getUserInbox().getMessages();
		Message newM = new Message(m.fromUser, m.toUser, message);
		newMessages.add(newM);
		m.fromUser.setUserInbox(new Inbox(newMessages));
	}
	*/

	@Override
	public String toString() {
		String endString = "Inbox --> \n";
		for(Message m: messages) {
			endString = endString + "from: " + m.getFromUser() + "\n";
		}
		return endString;
	}
}