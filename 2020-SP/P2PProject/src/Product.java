/**
 * 
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.01
 * @date 04-14-20
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Product implements Searchable, Serializable{
	
	private String name;
	public final Set<String> tags;
	private String description;
	private double price;
	private boolean bought;
	public final User seller;
	private User buyer = null;
	boolean lessThan5 = false;
	boolean lessThan20 = false;
	boolean lessThan50 = false;
	
	
	public Product(String name, Set<String> tags, String description, double price, User seller) {
		this.name = name;
		this.tags = tags;
		this.description = description;
		this.price = (Math.round(price * 100.0) / 100.0);
		this.bought = false;
		this.seller = seller;
		if(price<5)  {this.lessThan5 = true;}
		if(price<20) {this.lessThan20 = true;}
		if(price<50) {this.lessThan50 = true;}
	}
	
	/**
	 * @return the buyer
	 */
	public User getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	
	public void addTag (String newTag) {
		tags.add(newTag);
	}
	
	public void removeTag (String tagToRemove) {
		if (tags.contains(tagToRemove))
			tags.remove(tagToRemove);
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(float price) {
		this.price = price;
	}

	/**
	 * @return the bought
	 */
	public boolean isBought() {
		return bought;
	}

	/**
	 * @param bought the bought to set
	 */
	public void setBought(boolean bought) {
		this.bought = bought;
	}

	@Override
	public boolean hasTag(String s) {
		for(String quality: this.tags) {
			if(s.equals(quality)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		boolean validity = false;
		if(obj instanceof Product) {
			if(((Product)obj).name.equals(this.name)
				&& ((Product)obj).price == (this.price)
				&& ((Product)obj).seller.getUsername().equals(this.seller.getUsername())
				&& ((Product)obj).description.equals(this.description)) {
				validity = true;
			}
		}
		return validity;
	}

	/**
	 * @return the seller
	 */
	public User getSeller() {
		return seller;
	}


	
	
}