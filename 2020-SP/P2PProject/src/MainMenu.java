import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class MainMenu extends Menu {

	private class SignOut extends Command {

		public SignOut() {
			super("Sign Out and Return to Login");
		}

		@Override
		void execute() {
			Session.activeSession().signOut();
		}
	}
	
	private class DeleteAccount extends Command {

		public DeleteAccount() {
			super("Delete This Account");
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
			super("Browse Products");
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new StoreMenu());
		}
		
	}
	
	private class SellProduct extends Command {
		public SellProduct () {
			super ("Sell a New Product");
		}

		void execute () {
			boolean commandClose = false;
			boolean nameOK = false;
			String productName = "";
			boolean priceOK = false;
			float price = 0;
			boolean descriptionOK = false;
			String description = "Description: ";
			boolean tagsOK = false;
			boolean firstTime = true;
			Set<String> tags = new HashSet<> ();

			do {
				if (!nameOK) {
					if(firstTime) {
						userInput.nextLine();
						firstTime = false;
					}
					System.out.println("Please provide a product name");
					productName = userInput.nextLine();
					nameOK = productName.length() > 0;
				} else if (!priceOK) {
					System.out.println("Enter a price for your listing");
					System.out.println("You can either format it as 0 or 0.00");
					System.out.println("Price has to be greater than 0");
					try {
						price = Float.valueOf(userInput.nextLine());
						priceOK = price > 0;
					} catch (NumberFormatException e) {
						System.out.println("That isn't a valid price! Please try again");
					}
				} else if (!descriptionOK) {
					System.out.println("Please write a tempting description:");
					String userDescribe = userInput.nextLine();

					if(userDescribe.length() > 0) {
						description += userDescribe;
					}else { description = "The user did not provide a description."; }
					descriptionOK = true;

				} else if (!tagsOK) {
					System.out.println("Would you like to add any tags? (y/any key)");
					String nextInput = userInput.nextLine();

					while(nextInput.equals("y")){
						System.out.println("Enter the desired tag:");
						String tag = userInput.nextLine();

						if (tag.length() > 0){
							tags.add(tag);
						}else{
							System.out.println("Not added. Tag needs to be at least 1 char.");
						}

						System.out.println("Do you want to add more tags?");
						nextInput = userInput.nextLine();
					}
					tagsOK = true;
				}

				if (nameOK && priceOK && descriptionOK && tagsOK) {
					//If everything is ok we will create a product

					int productID = Session.activeSession().getStore().getCurrentProducts().size();
					Product selling = new Product (productName, tags, description, price, Session.activeSession().getCurrentUser());
					Session.activeSession().getStore().addProduct(productID, selling);
					System.out.println("Product " + selling.getName() + " has been successfully posted.");
					System.out.println("Press enter to go back to previous menu.");
					userInput.nextLine();
					commandClose = true;
				}
			} while (!commandClose);
		}
	}
	
	private class ViewHistory extends Command {

		public ViewHistory() {
			super("View buying and selling history");
		}

		@Override
		void execute() {
			History userHistory = Session.activeSession().getCurrentUser().getUserHistory();
			Session.activeSession().changeMenu(new HistoryMenu(userHistory));
			
		}
	}
	
	private class GoToInbox extends Command {

		public GoToInbox() {
			super("Go to Inbox");


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

	public MainMenu () {
		ArrayList<Command>  menuCommands = new ArrayList<Command> ();
		menuCommands.add(new GoToInbox ());
		menuCommands.add(new Search());
		menuCommands.add(new BrowseProducts ());
		menuCommands.add(new SellProduct ());
		menuCommands.add(new ViewHistory());
		menuCommands.add(new SignOut ());
		
		super.initializeCommands(menuCommands);
	}
	
	@Override
	void displayBody() {
		// TODO Auto-generated method stub

	}
}