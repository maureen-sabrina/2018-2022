import java.util.ArrayList;


public class UserMenu extends Menu {

	public final User user;
	
	private Command blockCommand;
	
	private class SendMessage extends Command {

		public SendMessage() {
			super("Send a message to " + user.getUsername());
		}

		@Override
		void execute() {
			//TODO: open a new message menu pointing to the target user's inbox
		}
		
	}
	
	private class FriendRequest extends Command {

		public FriendRequest() {
			super("Send a friend request to " + user.getUsername());
		}

		@Override
		void execute() {
			//TODO: send a new friend request to the target user
		}
		
	}
	
	private class BlockUser extends Command {

		public BlockUser() {
			super("Block " + user.getUsername());
		}

		@Override
		void execute() {
			//Sets the target user to BLOCKED in the active user's inbox
			Session.activeSession().getCurrentUser().getInbox().block(user);
			blockCommand = new UnblockUser ();
		}
		
	}
	
	private class UnblockUser extends Command {
		public UnblockUser () {
			super ("Unblock " + user.getUsername());
		}
		
		@Override
		void execute () {
			//Sets the target user to UNBLOCKED in the active user's inbox
			Session.activeSession().getCurrentUser().getInbox().unblock(user);
			blockCommand = new BlockUser ();
		}
	}
	
	
	public UserMenu(User user) {
		this.user = user;
		
		blockCommand = new BlockUser ();
		
		
		ArrayList<Command> commandList = new ArrayList<> ();
		
		commandList.add(blockCommand);
		commandList.add(new Session.GoBack ());
		
		super.initializeCommands(commandList);
	}

	@Override
	void displayBody() {
		// TODO Auto-generated method stub

	}
	
	

}