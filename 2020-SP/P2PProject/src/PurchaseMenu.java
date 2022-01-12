import java.util.ArrayList;

public class PurchaseMenu extends Menu {

	Product product;
	
	public PurchaseMenu (Product product) {
		this.product = product;
		
		ArrayList<Command> commandList = new ArrayList<> ();
		//commandList.add(new Session.GoBack ());
		commandList.add(new ConfirmPurchase());
		commandList.add(new goBack2());
		
		super.initializeCommands(commandList);
	}
	
	private class ConfirmPurchase extends Command{

		public ConfirmPurchase() {
			super("Confirm Purchase");
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			Session.activeSession().getStore().getCurrentProducts().values().remove(product);
			product.seller.getUserHistory().getSoldItems().add(product);
			product.setBuyer(Session.activeSession().getCurrentUser());
			Session.activeSession().getCurrentUser().getUserHistory().addPurchase(product);
			Session.activeSession().changeMenu(new MainMenu());
		}
		
	}
	
	private class goBack2 extends Command{

		public goBack2() {
			super("Return to main menu");
			// TODO Auto-generated constructor stub
		}

		@Override
		void execute() {
			Session.activeSession().changeMenu(new MainMenu());
		}
		
	}
	
	
	
	@Override
	void displayBody() {
		// TODO Auto-generated method stub
		
		System.out.println("Menu to buy " + product.getName());
		System.out.println("*********************************************");
		System.out.println();
		System.out.println();
		System.out.println("When you choose 'Confirm purchase' you would be");
		System.out.println("take to an outside payment portal. After paying,");
		System.out.println("we bring you back to the main menu.");
		
		this.bufferLines(2);
	}

}