import java.util.ArrayList;
import java.util.Scanner;


public class MessageMenu extends Menu {

	public Message message;
	
	@Override
	void displayBody() {
		System.out.println("Message -->");
		System.out.println();
		System.out.println("--------------------------------");
		System.out.println("| sender ID: " + message.fromUser.getId() + " |");
		System.out.println("| Time sent: " + message.getDateTime() + " |");
		System.out.println("--------------------------------");
		System.out.println(message);
		System.out.println();
	}
	
	
	private class ReplyTo extends Command {

		
		public ReplyTo() {
			super("Reply to message");
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			Scanner currentInput = new Scanner(System.in);
			System.out.print("Message to recipient: ");
			String messageText = currentInput.nextLine();
			System.out.println("Sent! Press enter to return to the previous menu");
			currentInput.nextLine();
			
			User fromUser = message.toUser;
			User toUser = message.fromUser;
		
			Message newMessage = new Message(toUser, fromUser, messageText);
			Inbox newInbox = toUser.getUserInbox();
			newInbox.messages.add(newMessage);
			toUser.setUserInbox(newInbox);
			
		}
		
	}
	
	private class DeleteMessage extends Command {

		public DeleteMessage() {
			super("delete this message");
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			System.out.println("Are you sure you want to delete this message? Y/N");
			//userChoice
			
		}
		
	}
	
	
	public MessageMenu(Message message) {
		this.message = message;
		ArrayList<Command>  messageCommands = new ArrayList<Command> ();
		messageCommands.add(new ReplyTo());
		messageCommands.add(new Session.GoBack());
		super.initializeCommands(messageCommands);
	}

}
