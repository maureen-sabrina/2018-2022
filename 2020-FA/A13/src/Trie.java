/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */

import java.util.Hashtable;

public class Trie implements Words {
    private boolean endsHere;
    public final Hashtable<Character,Trie> children;

    Trie () {
        this.endsHere = false;
        this.children = new Hashtable<>();
    }


    /**
     * Inserts the string s in the current trie.
     */
    void insert(String s) {
        if(s.length() == 0) {
            endsHere = true;
        }else{
            if(!children.containsKey(s.charAt(0))) {
                children.put(s.charAt(0), new Trie());
            }
            children.get(s.charAt(0)).insert(s.substring(1));
        }
    }

    /**
     * Checks whether the string s is a full word
     * stored in the current trie.
     */
    public boolean contains(String s) {
        if(s.length() == 0) {
            return endsHere;
        }else if(children.containsKey(s.charAt(0))) {
            return children.get(s.charAt(0)).contains(s.substring(1));
        }else{ return false;}
    }

    /**
     * Checks whether the string s is a prefix
     * of at least one word in the current trie.
     */
    public boolean possiblePrefix (String s) {
        if(s.length() == 0) {
            return true;
        }else if(children.containsKey(s.charAt(0))) {
            return children.get(s.charAt(0)).possiblePrefix(s.substring(1));
        }else{ return false;}
    }

    public String toString () {
        return children.toString();
    }
}
