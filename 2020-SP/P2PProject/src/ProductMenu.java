import java.util.ArrayList;

public class ProductMenu extends Menu {

	
	
	private class Purchase extends Command {
		public Purchase () {
			super("Purchase " + product.getName() + " for $" +product.getPrice());
		}
		
		public void execute () {
			Session.activeSession().changeMenu(new PurchaseMenu (product));
		}
	}
	
	
	
	public final Product product;
	
	public ProductMenu (Product product) {
		this.product = product;
		
		ArrayList<Command> commandList = new ArrayList<> ();
		commandList.add(new Purchase());
		commandList.add(product.seller.goToProfile());
		commandList.add(new Session.GoBack());
		
		super.initializeCommands(commandList);
	}
	
	@Override
	void displayBody() {
		
		System.out.println(
				"*".repeat(50) +
				"\n" +
				product.getName() +
				"\n" +
				"*".repeat(50)
				);
		
		this.bufferLines(3);
		
		System.out.println(product.getDescription());
		
		this.bufferLines(3);
		
		System.out.println("Product price :" + product.getPrice());
		
		System.out.println("*".repeat(50));
		
		System.out.print("Product tags:");
		
		for (String tag : product.getTags())
			System.out.println(" " + tag + ",");
		
	}

}