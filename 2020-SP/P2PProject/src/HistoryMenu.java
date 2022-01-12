import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class HistoryMenu extends Menu{

	History history;
	HashSet<User> uniqueUsers = new HashSet<>();
	
	public HistoryMenu(History userHistory) {
		this.history = userHistory;
		for(Product p: history.purchases) {
			uniqueUsers.add(p.getSeller());
		}
		for(Product p: history.soldItems) {
			uniqueUsers.add(p.getBuyer());
		}
		
		ArrayList<Command>  historyCommands = new ArrayList<Command> ();
		for(User u: uniqueUsers) {
			historyCommands.add(new RateUser(u));
		}
		historyCommands.add(new Session.GoBack());
		super.initializeCommands(historyCommands);
	}
	
	
	private class RateUser extends Command{
		
		User user;
		
		public RateUser(User u) {
			super("Rate user -> " + u.getUsername());
			this.user = u;
		}

		@Override
		void execute() {
			Scanner in = new Scanner(System.in);
			System.out.println("What would you like to rate the user, on a scale");
			System.out.println("From 1-5? (Whole numbers only)");
			int rating = in.nextInt();
			user.addRating(rating);
		}
		
	}

	
	@Override
	void displayBody() {
		System.out.println("Your History");
		System.out.println("****************************");
		System.out.println("Sold Products: ");
		for(Product p: history.getSoldItems()) {
			System.out.println(p.getName() + ": sold for $" + p.getPrice() + " to " + p.getBuyer());
		}
		System.out.println();
		System.out.println("------------------------------");
		System.out.println("Bought Products: ");
		for(Product p: history.getPurchases()) {
			System.out.println(p.getName() + ": bought for $" + p.getPrice() + " : sold by " + p.seller.getUsername());
		}
		System.out.println();
	}

}
