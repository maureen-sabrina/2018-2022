/**
 * 
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.01
 * @date 04-14-20
 */
import java.io.Serializable;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

public class Message implements Serializable {
	User toUser;
	User fromUser;
	String userMessage;
	String dateTime;
	
	public Message() {
		super();
	}
	
	public Message(User to, User from, String message) {
		this.toUser = to;
		this.fromUser = from;
		this.userMessage = message;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		this.dateTime = dtf.format(now);
	}
	

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the toUsername
	 */
	public User getToUser() {
		return toUser;
	}


	/**
	 * @param toUsername the toUsername to set
	 */
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}


	/**
	 * @return the fromUsername
	 */
	public User getFromUser() {
		return fromUser;
	}


	/**
	 * @param fromUsername the fromUsername to set
	 */
	public void setFromUsername(User fromUser) {
		this.fromUser = fromUser;
	}


	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}


	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	@Override
	public String toString() {
		return "[from: " + fromUser + ", to: " + toUser + "]"
				+"\nMessage: " + userMessage;
	}
	
	
	
}

