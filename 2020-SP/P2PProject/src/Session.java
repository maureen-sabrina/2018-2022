import java.util.Stack;

/**
 * 
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.02
 * @date 04-29-20
 */
public class Session {
	
	static public class GoBack extends Command {
		/*
		 * This is a special command, because it interacts directly with the Session state.
		 * It can be used on any menu within the same Session, so it belongs here.
		 * 
		 * NOTE:
		 * I'm not sure what the correct scope for this class should be, so
		 * we should talk about that
		 *
		 *-Arthur
		 */
		
		public GoBack() {
			//How the command will display in a menu
			super("Return to previous menu");
		}

		@Override
		void execute() {
			Session.activeSession().goBack();
		}
		
	}
	
	
	private User currentUser;
	
	private Menu currentMenu;
	
	private Stack<Menu> lastMenu = new Stack<>();
	
	private StoreFront store;
	
	private boolean sessionExit = false;
	
	public final int minPasswordLength = 5;
	
	//Session uses the Singleton design pattern
	static private Session activeSession = new Session ();
	
	//This can stay empty for now-- the program will run fine without it
	public static void main (String[] args) {	
		
		/*
		 * After startup is done, the session loop executes until the session exits.
		 * NOTE FROM ARTHUR: I believe this method should be moved into another class later, preferably the one handling serialization.
		 */
		do {
			Session.activeSession().currentMenu.run();
		} while (!Session.activeSession().sessionExit);
	}
	
	
	private Session () {
		/*
		 * Session startup triggers the login window
		 */
		
		
		
		StoreFront nextStore = (StoreFront) P2Papp.pullInfo(store, "save.txt");
		
		//Check if savefile exists, and if not create one
		if (nextStore == null){
			System.out.println("Load failed...");
			store = new StoreFront ();
		} else {
			store = nextStore;
		}
		
		
		
		currentMenu = new LoginMenu ();
		
		
	}
	
	static public Session activeSession () {
		return activeSession;
	}
	
	public void changeMenu (Menu newMenu) {
		lastMenu.add(currentMenu);
		currentMenu.close();
		currentMenu = newMenu;
	}
	
	public void changeMenu (Menu newMenu, boolean saveLastMenu) {
		if (saveLastMenu) lastMenu.add(currentMenu);
		currentMenu.close();
		currentMenu = newMenu;
	}
	
	public boolean goBack () {
		boolean lastMenuExists = (lastMenu.peek() != null);
		
		if (lastMenuExists) {
			//Go to last menu without adding the current menu to the stack
			changeMenu(lastMenu.pop(), false);
		}
		
		return lastMenuExists;
	}
	
	public boolean endSession () {
		/*
		 * Attempts to end the active session.
		 * Returns true iff session successfully terminated
		 */
		currentMenu.close();
		sessionExit = true;
		System.out.println("Session terminated");
		boolean saveSuccessful = P2Papp.saveInfo(store, "save.txt");
		
		if (saveSuccessful) {
			System.out.println("Saved successfully!");
			return true;
		}
		else return false;
	}

	public String requestProductSpecs() {
		return null;
	}
	
	public void postProductToSell(Product p) {
		
	}
	public void viewInbox() {
		
	}
	public void signOut() {
		/*
		 * Attempts to end the active session.
		 * Returns true iff session successfully terminated
		 */
		currentMenu.close();
		sessionExit = true;
		System.out.println("Session terminated");
		P2Papp.saveInfo(store, "save.txt");
		Session.activeSession = new Session();
	}
	
	public boolean login (String username, String password) {
		/*
		 * Assigns the correct user to the session, but only if they are recognized by the store AND if the password is correct.
		 * Returns false on failure.
		 */
		
		boolean loginResult = (store.getCurrentUsers().containsKey(username) && store.getCurrentUsers().get(username).passwordIs(password));
		
		if (loginResult) currentUser = store.getCurrentUsers().get(username);
		
		return loginResult;
	}
	
	public boolean deleteAccount() {
		boolean checkResult = currentUser != null;
		
		if (checkResult)
			store.getCurrentUsers().remove(currentUser.getUsername());
		
		return checkResult;
	}
	
	public void rateUser(int stars) {
		
	}
	
	public String browseProducts() {
		return null;
	}
	public String viewHistory() {
		return null;
	}
	
	public void buyProduct(Product product) {
		
	}
	
	public StoreFront getStore () {
		return store;
	}


	public User getCurrentUser() {
		return currentUser;
	}


	public boolean checkUsername(String username) {
		/*
		 * Checks whether the given username is unique and appropriate
		 */

		return !store.getCurrentUsers().keySet().contains(username);
	}


	public boolean checkPassword(String password) {
		return password.length() > minPasswordLength;
	}


	public boolean createAccount(String username, String password) {
		/*
		 * Creates a new user account in the store, but only if the username is unique and appropriate.
		 * Returns false if the check fails.
		 */
		
		boolean checkResult = checkUsername(username);
		
		if (checkResult)
			store.addUser(username, new User(username, password));
		
		return checkResult;
	}

}