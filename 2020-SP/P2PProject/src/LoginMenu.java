import java.util.ArrayList;

public class LoginMenu extends Menu {
	
	
	private class Register extends Command {
		/*
		 * This command executes the new user registration dialogue
		 */
		
		//The Command class has only one constructor, an abstract method, and a String variable.
		//The purpose of this constructor is to tell the Menu class how to display the command
		public Register () {super ("Register new user");};
		
		@Override
		void execute() {
			boolean commandClose = false;
			String username = "";
			boolean usernameOK = false;
			String password = "";
			boolean passwordOK = false;
			boolean loginOK = false;
			
			do {
				if (!usernameOK) {
					userInput.nextLine();
					System.out.println("Please provide a valid username.");
					System.out.println("(P.S. Usernames must be unique!)");
					username = userInput.nextLine();
					usernameOK = Session.activeSession().checkUsername(username);
				} else if (!passwordOK) {
					System.out.println("Please provide a valid password:");
					password = userInput.nextLine();
					passwordOK = Session.activeSession().checkPassword(password);
				}
				
				if (usernameOK && passwordOK) {
					//If username and password are OK, attempt to create user
					loginOK = Session.activeSession().createAccount(username, password);
					
					if (!loginOK) {
						System.out.println("That username has already been claimed. Please try again!");
						commandClose = true;
						System.out.println("Press enter to continue");
					} else {
						System.out.println("User created successfully!");
						commandClose = true;
					}
				}
			} while (!commandClose);
			
			System.out.println("Press enter to continue");
			userInput.nextLine();
		}
		
	}

	private class Login extends Command {
		/*
		 * This command executes the existing user login dialogue
		 */
		
		public Login () {super ("Login as existing user");};
		
		@Override
		void execute() {
			boolean commandClose = false;
			String username = "";
			boolean usernameOK = false;
			String password = "";
			boolean passwordOK = false;
			boolean loginOK = false;
			
			do {
				if (!usernameOK) {
					userInput.nextLine();
					System.out.println("Enter your username");
					username = userInput.nextLine();
					usernameOK = true;
				} else if (!passwordOK) {
					System.out.println("Enter your password");
					password = userInput.nextLine();
					passwordOK = true;
				}
				
				if (usernameOK && passwordOK) {
					//If username and password are OK, attempt to log user in
					loginOK = Session.activeSession().login(username, password);
					
					if (!loginOK) {
						System.out.println("Login failed! Are you sure that user exists?");
						commandClose = true;

					} else {
						System.out.println("Login successful!");
						Session.activeSession().changeMenu(new MainMenu ());
						commandClose = true;
					}
				}
			} while (!commandClose);
			
			System.out.println("Press enter to continue");
			userInput.nextLine();
		}
	}
		
	private class Close extends Command {
		public Close () { super ("Quit the program"); };
		
		@Override
		public void execute () {
			Session.activeSession().endSession();
		}
	}
	
	
	public LoginMenu () {
		ArrayList<Command>  loginCommands = new ArrayList<Command> ();
		loginCommands.add(new Register());
		loginCommands.add(new Login());
		loginCommands.add(new Close ());
		
		super.initializeCommands(loginCommands);
	}
	



	@Override
	void displayBody() {
		System.out.println("Welcome to the login menu!\nHere you can either register as a unique user or login as an existing one.");
		bufferLines(5);
	}

	
	
	
}


