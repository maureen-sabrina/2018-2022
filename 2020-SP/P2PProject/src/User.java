/**
 * 
 /**
 * 
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.01
 * @date 04-14-20
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class User implements Searchable, Serializable{

	private int id;
	private String username;
	private String password;
	private ArrayList<Product> selling;
	private ArrayList<Integer> allRatings;
	private Inbox userInbox;
	private History userHistory = new History();
	
	
	/**
	 * @return the userHistory
	 */
	public History getUserHistory() {
		return userHistory;
	}


	/**
	 * @param userHistory the userHistory to set
	 */
	public void setUserHistory(History userHistory) {
		this.userHistory = userHistory;
	}


	private class GoToProfile extends Command {

		public GoToProfile() {
			super("Go to " + getUsername() + "'s profile");
		}

		@Override
		void execute() {
			// TODO Auto-generated method stub
			Session.activeSession().changeMenu(new UserMenu(User.this));
		}
		
	}
	
	
	public Command goToProfile () {
		return new GoToProfile ();
	}
	
	
	public User (String username, String password) {
		this.username = username;
		this.password = password;
		this.selling = new ArrayList<Product>();
		this.allRatings = new ArrayList<Integer>();
		this.userInbox = new Inbox();
		char[] beginId = username.toCharArray();
		String endId = "";
		for(char c: beginId) {
			endId = endId + (int)c;
		}
		int intID = (int)(Long.parseLong(endId)/200); 
		if(intID>1000000000) {
			intID = intID/10000000;
		}
		if(intID<0) {
			intID = intID*-1;
		}
		this.id = intID;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	public double getRating() {
		double averageRating = 0;
		int amount = 0;
		if(this.allRatings.size() > 0) {
			for(int r: this.allRatings) {
				averageRating = averageRating + r;
				amount++;
			}
		}
		return (averageRating/amount);
	}
	
	public void addRating(int rating) {
		this.allRatings.add(rating);
	}
	
	
	/**
	 * @return the allRatings
	 */
	public ArrayList<Integer> getAllRatings() {
		return allRatings;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String newPassword, String currentPassword) {
		if (currentPassword.equals(this.password)) this.password = newPassword;
	}


	/**
	 * @return the selling
	 */
	public ArrayList<Product> getSelling() {
		return selling;
	}

	
	/**
	 * @param selling the selling to set
	 */
	public void setSelling(ArrayList<Product> selling) {
		this.selling = selling;
	}

	@Override
	public String toString() {
		return getUsername();
	}

	/**
	 * @return the userInbox
	 */
	public Inbox getUserInbox() {
		return userInbox;
	}

	/**
	 * @param userInbox the userInbox to set
	 */
	public void setUserInbox(Inbox userInbox) {
		this.userInbox = userInbox;
	}


	@Override
	public boolean hasTag(String s) {
		if(s.equals(this.getUsername())) {
			return true;
		}
		return false;
	}


	public Inbox getInbox() {
		// TODO Auto-generated method stub
		return userInbox;
	}


	public boolean passwordIs(String password2) {
		// TODO Auto-generated method stub
		return password.equals(password2);
	}


	public String getUsername() {
		return username;
	}


	public boolean setUsername(String username, String password) {
		
		boolean success = this.passwordIs(password);
		
		if (success) this.username = username;
		return success;
	}
}