import java.util.ArrayList;

public class StoreMenu extends Menu {

	private class ViewProduct extends Command {
		
		Product p;
		
		public ViewProduct (Product p) {
			super (p.getName() + ": selling for $" + p.getPrice() + " : sold by " + p.seller.getUsername());
			this.p = p;
			
			System.out.println(p.seller);
		}
		
		public void execute () {
			Session.activeSession().changeMenu(new ProductMenu(p));
		}
	}
	
	
	public StoreMenu () {
		ArrayList<Command> commandList = new ArrayList<Command> ();
		
		for (Product p : Session.activeSession().getStore().getCurrentProducts().values())
			commandList.add(new ViewProduct(p));
		
		commandList.add(new Session.GoBack ());
		
		super.initializeCommands(commandList);
	}
	
	@Override
	void displayBody() {
		System.out.println("Products for sale below:");
		System.out.println("*".repeat(50));
		this.bufferLines(1);
	}

}