import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {
	
	protected ArrayList<Command> menuCommands;
	private boolean closeMenu = false;
	protected final Scanner userInput = new Scanner (System.in);
	
	static final int barLength = 30;
	
	
	protected void initializeCommands (ArrayList<Command> menuCommands) {
		/*
		 * The command list is only initialized when the child menu is ready.
		 */
		this.menuCommands = menuCommands;
	}
	
	public void run() {
		/*
		 * Every menu is composed of a body and commands.
		 * The command logic is in the abstract menu class,
		 * the implementations provide a list of their own commands
		 * and their own display body.
		 * 
		 * -Arthur 
		 */
		
		int nextCommand;
		
		do {
			clearScreen();
			
			displayBody ();
			
			displayCommands ();
			
			try {
				nextCommand = userInput.nextInt();
				menuCommands.get(nextCommand).execute();
				//This exception handling can be much more specific
			} catch (InputMismatchException e) {
				e.printStackTrace();
				System.out.println("Please select a valid command");
				waitForEnter();
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("Please select a valid command");
				waitForEnter();
			}
		} while (!closeMenu);
	}
	
	
	abstract void displayBody ();
	
	private void displayCommands () {
		System.out.println("-".repeat(barLength));
		for (int i = 0; i < menuCommands.size(); i++) {
			Command c = menuCommands.get(i);
			System.out.println(i + ". " + c.displayText);
		}
	}
	
	public boolean close () {
		closeMenu = true;
		
		//Return true iff menu successfully closed
		return true;
	}
	
	public void clearScreen() {
		bufferLines(50);
	}
	
	public void bufferLines (int n) {
		System.out.println("\r\n".repeat(n));
	}
	
	public void waitForEnter () {
		/*
		 * Use this wherever the menu skips messages (usually when no input is required)
		 */
		System.out.println("Press enter to continue");
		userInput.nextLine ();
		userInput.nextLine ();
	}
}
