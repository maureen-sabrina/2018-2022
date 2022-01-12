import java.util.ArrayList;
import java.util.Scanner;

public class InboxMenu extends Menu {

	Inbox inbox;
	
	@Override
	void displayBody() {
		System.out.println(inbox);
	}
	
	private class ViewMessage extends Command{
		public Message message;
		
		public ViewMessage(Message m) {
			super("from: "+ m.fromUser);
			this.message = m;
		}
		@Override
		void execute() {
			Session.activeSession().changeMenu(new MessageMenu(message));
		}
	}
	
	private class SendMessage extends Command{
		
		public SendMessage() {
			super("Send message");
		}
		@Override
		void execute() {
			Scanner currentInput = new Scanner(System.in);
			System.out.print("username of recipient: ");
			String username = currentInput.next();
			Scanner currentInput2 = new Scanner(System.in);
			System.out.print("Message to recipient: ");
			String messageText = currentInput2.nextLine();
			System.out.println("Sent! Press enter to return to the previous menu");
			currentInput2.nextLine();
			//currentInput.close(); currentInput2.close();
			
			User fromUser = Session.activeSession().getCurrentUser();
			User toUser = Session.activeSession().getStore().getCurrentUsers().get(username);
		
			Message newMessage = new Message(toUser, fromUser, messageText);
			Inbox newInbox = toUser.getUserInbox();
			newInbox.messages.add(newMessage);
			toUser.setUserInbox(newInbox);
		}
	}
	
		
	
	public InboxMenu (Inbox currentInbox) {
		this.inbox = currentInbox;
		ArrayList<Command>  inboxCommands = new ArrayList<Command> ();
		for(Message m: currentInbox.messages) {
			inboxCommands.add(new ViewMessage(m));
		}
		inboxCommands.add(new SendMessage());
		inboxCommands.add(new Session.GoBack());
	//	loginCommands.add(new Register());
	//	loginCommands.add(new Login());
	//	loginCommands.add(new Close ());
		
		super.initializeCommands(inboxCommands);
	}
}
