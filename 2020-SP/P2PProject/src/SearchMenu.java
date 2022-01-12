import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;

public class SearchMenu extends Menu {
	
	private String tag;
	private ArrayList<User> foundUsers = new ArrayList<>();
	private ArrayList<Product> foundProducts = new ArrayList<>();
	
	@Override
	void displayBody() {
		System.out.println("Search Results");
		System.out.println("*************************");
		System.out.println("Users found with tag ->");
		System.out.println("*************************");
		Map<String, User> userKeys = Session.activeSession().getStore().getCurrentUsers();
		if(foundUsers.isEmpty()) {
			System.out.println("No users found with that tag");
		}
		else {
			for(User user: foundUsers) {
				System.out.println(user);
			}
		}
		System.out.println();
		System.out.println("*************************");
		System.out.println("Products found with tag ->");
		System.out.println("*************************");
		Map<Integer, Product> productKeys = Session.activeSession().getStore().getCurrentProducts();
		if(foundProducts.isEmpty()) {
			System.out.println("No products found with that tag");
		}
		else {
			for(Product p: foundProducts) {
				System.out.println(p.getName() + " for $" +p.getPrice());
			}
		}
	}
	
	private class SignOut extends Command {

		public SignOut() {
			super("Sign out and return to login");
		}

		@Override
		void execute() {
			Session.activeSession().signOut();
		}
		
	}
	
	private class ViewUser extends Command{
		
		User viewedUser;
		
		public ViewUser(User user) {
			super("view user " + user);
			this.viewedUser = user;
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new UserMenu(viewedUser));
		}
		
	}
	
	private class BuyProduct extends Command{
		
		Product viewedProduct;
		
		public BuyProduct(Product p) {
			super("Purchase " + p.getName() + " for $" +p.getPrice());
			this.viewedProduct = p;
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new PurchaseMenu(viewedProduct));
		}
		
	}
	
	private class SearchAgain extends Command {
		
		
		
		public SearchAgain() {
			super("Search with different tag");
		}

		@Override
		void execute() {
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter the new tag you'd like to search with: ");
			String newTag = scan.next();
			Session.activeSession().changeMenu(new SearchMenu(newTag), false);
			
			
		}
		
	}
	
	private class Filter5 extends Command{

		public Filter5() {
			super("Filter Product results for those less than $5");
		}

		@Override
		void execute() {
			System.out.println("Items from search that are less than $5:");
			ArrayList<Product> filteredProducts = new ArrayList<>();
			for(Product p: foundProducts) {if(p.lessThan5) {filteredProducts.add(p);}}
			foundProducts = filteredProducts;
			
		}
		
	}
	
	private class Filter20 extends Command{

		public Filter20() {
			super("Filter Product results for those less than $20");
		}

		@Override
		void execute() {
			System.out.println("Items from search that are less than $20:");
			ArrayList<Product> filteredProducts = new ArrayList<>();
			for(Product p: foundProducts) {if(p.lessThan20) {filteredProducts.add(p);}}
			foundProducts = filteredProducts;
		}
	}
	
	private class Filter50 extends Command{

		public Filter50() {
			super("Filter Product results for those less than $50");
		}

		@Override
		void execute() {
			System.out.println("Items from search that are less than $50:");
			ArrayList<Product> filteredProducts = new ArrayList<>();
			for(Product p: foundProducts) {if(p.lessThan50) {filteredProducts.add(p);}}
			foundProducts = filteredProducts;
		}
	}
	
	private class SortLowHigh extends Command{

		public SortLowHigh() {
			super("Sort products by pricing low to high");
		}

		@Override
		void execute() {
			ArrayList<Product> lowToHigh = foundProducts;
			Collections.sort(lowToHigh, Comparator.comparingDouble(Product::getPrice));
			foundProducts = lowToHigh;
		}
	}
	
	private class SortHighLow extends Command{

		public SortHighLow() {
			super("Sort products by pricing high to low");
		}

		@Override
		void execute() {
			ArrayList<Product> lowToHigh = foundProducts;
			Collections.sort(lowToHigh, Collections.reverseOrder(Comparator.comparingDouble(Product::getPrice)));
			foundProducts = lowToHigh;
			
		}
	}
	
	
	
	public SearchMenu (String tag) {
		this.tag = tag;
		Map<String, User> userKeys = Session.activeSession().getStore().getCurrentUsers();
		for(String key: userKeys.keySet()) {
			User currentUser = userKeys.get(key);
			if(currentUser.hasTag(tag)) {					
				this.foundUsers.add(currentUser);
			}
		}
		Map<Integer, Product> productKeys = Session.activeSession().getStore().getCurrentProducts();
		for(int key: productKeys.keySet()) {
			Product currentProduct = productKeys.get(key);
			if(currentProduct.hasTag(tag)) {
				this.foundProducts.add(currentProduct);
			}
		}
		ArrayList<Command>  searchCommands = new ArrayList<Command> ();
		
		for(User u: foundUsers) {
			searchCommands.add(new ViewUser(u));
		}
		for(Product p: foundProducts) {
			searchCommands.add(new BuyProduct(p));
		}
		searchCommands.add(new Filter5());
		searchCommands.add(new Filter20());
		searchCommands.add(new Filter50());
		searchCommands.add(new SortLowHigh());
		searchCommands.add(new SortHighLow());
		searchCommands.add(new SearchAgain());
		searchCommands.add(new Session.GoBack());
		searchCommands.add(new SignOut ());
		
		super.initializeCommands(searchCommands);
	}

}
