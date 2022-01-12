import java.util.ArrayList;
import java.util.List;

public class ListWords implements Words {
    private final List<String> words;

    ListWords (List<String> words) {
        this.words = words;
    }

    public boolean contains(String w) {
        return words.contains(w);
    }

    public boolean possiblePrefixSpecific(String w, String fromList){
        if(w.length() == 0) return true;
        else if(w.charAt(0) == fromList.charAt(0))
            return possiblePrefixSpecific(w.substring(1), fromList.substring(1));
        else return false;
    }
    /**
     * Checks if the string w is a prefix of at least one
     * words in the current list.
     */
    public boolean possiblePrefix(String w) {
        boolean result = false;
        for (String word : words) {
            if (w.length() <= word.length()) {
                result = possiblePrefixSpecific(w, word);
                if (result == true){ break;}
            }
        }
        return result;
    }
}
