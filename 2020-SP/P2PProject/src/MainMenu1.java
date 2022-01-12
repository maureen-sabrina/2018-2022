import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class MainMenu1 extends Menu {

	private class SignOut extends Command {

		public SignOut() {
			super("Sign out and return to login");
		}

		@Override
		void execute() {
			Session.activeSession().signOut();
		}
		
	}
	
	private class DeleteAccount extends Command {

		public DeleteAccount() {
			super("Delete this account");
		}

		@Override
		void execute() {
			
			boolean confirmOK = false;
			String confirmation = "";
			boolean passwordOK = false;
			String password = "";
			boolean commandExit = false;
			
			do {
				if (!confirmOK) {
					userInput.nextLine();
					System.out.println("Would you like to delete your account? (y/n)");
					confirmation = userInput.nextLine();
					if (confirmation.equals("y"))
						confirmOK = true;
					else if (confirmation.equals("n"))
					{
						commandExit = true;
					}
				}
				
				if (confirmOK && !passwordOK) {
					System.out.println("Please enter the correct password for this account:");
					password = userInput.nextLine();
					if (Session.activeSession().getCurrentUser().passwordIs(password)) {
						System.out.println("Password correct, account will be deleted immediately");
						userInput.nextLine();
						Session.activeSession().deleteAccount();
					} else {
						System.out.println("Incorrect password, please retry");
						waitForEnter();
					}
					commandExit = true;
				}
			} while (!commandExit);
		}
		
	}
	
	private class BrowseProducts extends Command {

		public BrowseProducts() {
			super("Browse products");
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new StoreMenu());
		}
		
	}
	
	private class SellProduct extends Command {
		public SellProduct () {
			super ("Sell a new product");
		}
	
		void execute () {
			boolean commandClose = false;
			boolean nameOK = false;
			String productName = "";
			boolean priceOK = false;
			double price = 0;
			boolean descriptionOK = false;
			String description = "Description: ";
			boolean tagsOK = false;
			Set<String> tags = new HashSet<> ();
			
			do {
				if (!nameOK) {
					userInput.nextLine();
					System.out.println("Please provide a product name");
					productName = userInput.nextLine();
					nameOK = !"".equals(productName);
				} else if (!priceOK) {
					System.out.println("Enter a price for your listing");
					try {
						price = Float.valueOf(userInput.nextLine());
						priceOK = price > 0;
					} catch (InputMismatchException e) {
						System.out.println("That isn't a valid price! Please try again");
					}
				} else if (!descriptionOK) {
					System.out.println("Please write a tempting description:");
					description += userInput.nextLine();
					descriptionOK = true;
				} else if (!tagsOK) {
					System.out.println("Would you like to add any tags? (y/n)");
					//TODO: add a do/while loop to add tags
					String nextInput = userInput.nextLine();
					
					if (nextInput.equals("y")) {
						System.out.println("Enter the desired tag:");
						tags.add(userInput.nextLine());
					} else {
						tagsOK = true;
						userInput.nextLine();
					}
				} else if (nameOK && priceOK && descriptionOK && tagsOK) {
					//If username and password are OK, attempt to create user
					
					int productID = Session.activeSession().getStore().getCurrentProducts().size();
					
					Session.activeSession().getStore().addProduct(productID, new Product (productName, tags, description, (float) price, Session.activeSession().getCurrentUser()));
					commandClose = true;
				}
			} while (!commandClose);
		}
	}
	
	private class ViewHistory extends Command {

		public ViewHistory() {
			super("View your purchase and selling history");
		}

		@Override
		void execute() {
			History userHistory = Session.activeSession().getCurrentUser().getUserHistory();
			Session.activeSession().changeMenu(new HistoryMenu(userHistory));
		}
		
	}

	
	
	private class GoToInbox extends Command {

		public GoToInbox() {
			super("Go to inbox");
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new InboxMenu(Session.activeSession().getCurrentUser().getUserInbox()));
			
		}
		
	}
	
	private class Search extends Command{
		
		public Search() {
			super("Search Products and Users");
		}

		@Override
		void execute() {
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter the tag you'd like to search: ");
			String tag = scan.next();
			Session.activeSession().changeMenu(new SearchMenu(tag));
			
		}
		
	}
	
	
	public MainMenu1 () {
		ArrayList<Command>  menuCommands = new ArrayList<Command> ();
		menuCommands.add(new GoToInbox ());
		menuCommands.add(new Search());
		menuCommands.add(new BrowseProducts ());
		menuCommands.add(new SellProduct ());
		menuCommands.add(new ViewHistory ());
		menuCommands.add(new SignOut ());
		
		super.initializeCommands(menuCommands);
	}
	
	@Override
	void displayBody() {
		// TODO Auto-generated method stub

	}

}