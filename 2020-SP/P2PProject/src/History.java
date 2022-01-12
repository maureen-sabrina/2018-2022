import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Annie Pompa, Arthur Hertz, Maureen Lynch
 * @version 1.03
 * @date 04-29-20
 */
public class History implements Serializable {
    ArrayList<Product> purchases;
    ArrayList<Product> soldItems;
    ArrayList<Integer> ratings;
    /**
	 * @return the soldItems
	 */
    
    public History() {
    	this.purchases = new ArrayList<Product>();
    	this.soldItems = new ArrayList<Product>();
    	this.ratings   = new ArrayList<Integer>();
    }
    
	public ArrayList<Product> getSoldItems() {
		return soldItems;
	}

	/**
	 * @param soldItems the soldItems to set
	 */
	public void setSoldItems(ArrayList<Product> soldItems) {
		this.soldItems = soldItems;
	}    

    /**
     * @return the mean rating for a user
     */
    // todo consider there being a diff between having no ratings and having everyone rate 0
    public double getRating() {
        double sumOfRatings = 0;
        int amtOfRatings = 0;

        if(this.ratings.size() > 0) {

            for(int r: this.ratings) {
                sumOfRatings += r;
                amtOfRatings+=1;
            }
        }
        return (sumOfRatings/amtOfRatings);
    }

    /**
     * @param rating for an additional rating the User receives
     */
    public void addRating(int rating) {
        this.ratings.add(rating);
    }

    /**
     * @return the allRatings
     */
    public ArrayList<Integer> getAllRatings() {
        return ratings;
    }

    /**
     * @param allRatings the allRatings to set
     */
    public void setAllRatings(ArrayList<Integer> allRatings) {
        this.ratings = allRatings;
    }

    /**
     * @return the history
     */
    public ArrayList<Product> getPurchases() {
        return purchases;
    }

    /**
     * @param purchase a product that a user has purchased
     */
    public void addPurchase(Product purchase) {
        purchase.setBought(true);
        this.purchases.add(purchase);
    }
}