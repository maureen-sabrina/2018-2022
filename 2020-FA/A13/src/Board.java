/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Board {
    private final Tile[][] tiles;
    private final int boardSize;
    private final Words dict;

    public Board(Tile[][] tiles, Words dict) {
        this.tiles = tiles;
        this.boardSize = tiles.length;
        this.dict = dict;
    }

    public int getBoardSize () { return boardSize; }

    public Tile get(int row, int col) {
        return tiles[row][col];
    }

    //returns if a tile exists in tiles and is fresh
    public boolean tileIsValid(int r, int c){
        if(r>=0 && r<boardSize && c>=0 && c<boardSize){
            if(tiles[r][c].isFresh()){ return true; }
            else{ return false; }
        }
        else{ return false;}
    }

    /**
     * The method returns a list of neighboring cells
     * that have not been visited.
     */
    public List<Tile> getFreshNeighbors (int r, int c) {
        List<Tile> result = new ArrayList<>();
        if(tileIsValid(r-1, c-1)) result.add(tiles[r-1][c-1]);
        if(tileIsValid(r-1, c)) result.add(tiles[r-1][c]);
        if(tileIsValid(r-1, c+1)) result.add(tiles[r-1][c+1]);

        if(tileIsValid(r, c-1)) result.add(tiles[r][c-1]);
        //CURRENT TILE -> if(tileIsValid(r, c)) result.add(tiles[r1][1]);
        if(tileIsValid(r, c+1)) result.add(tiles[r][c+1]);

        if(tileIsValid(r+1, c-1)) result.add(tiles[r+1][c-1]);
        if(tileIsValid(r+1, c)) result.add(tiles[r+1][c]);
        if(tileIsValid(r+1, c+1)) result.add(tiles[r+1][c+1]);
        return result;
    }

    /**
     * Starting from the given position (r,c) and the string s
     * found so far, the method performs the following actions:
     *   - marks the tile (r,c) as visited
     *   - extends s with the character at (r,c); if that
     *     new string is a word, add it to the result to be
     *     returned
     *   - if the extended string is not a valid prefix of
     *     any word, mark the tile (r,c) is unvisited
     *     and return
     *   - otherwise visit all fresh neighbors recursively
     */
    public HashSet<String> findWordsFromPos(int r, int c, String s) {
        HashSet<String> result = new HashSet<>();

        // - marks the tile (r,c) as visited
        tiles[r][c].setVisited();

        // - extends s with the character at (r,c); if that new
        //   string is a word, add it to the result to be returned
        //String newWord = s + tiles[r][c];
        s = s + tiles[r][c].toString().toLowerCase();
        if(dict.contains(s)) {
            System.out.println("found new word: " + s);
            result.add(s);
        }

        // - if the extended string is not a valid prefix of any
        //   word, mark the tile (r,c) as unvisited and return
        if(!dict.possiblePrefix(s)){
            System.out.println(s + " -> not a possible prefix, return result\n");
            tiles[r][c].reset();
            return result;
        }
        // - otherwise visit all fresh neighbors recursively
        else{
            System.out.println("possible prefix, recurring with freshNeighbors");
            for(Tile neighbor: getFreshNeighbors(r,c)){
                HashSet<String> wordsFound = findWordsFromPos(neighbor.getRow(), neighbor.getCol(), s);
                for(String word: wordsFound){
                    result.add(word);
                }

            }
            tiles[r][c].reset();
        }
        return result;
    }

    public HashSet<String> findWords() {
        HashSet<String> result = new HashSet<>();
        for (int r=0; r<boardSize; r++) {
            for (int c=0; c<boardSize; c++) {
                System.out.println("AT TILE "+r +", "+c+" -> "+tiles[r][c].toString());
                result.addAll(findWordsFromPos(r,c,""));
            }
        }
        return result;
    }

    public void paint(Graphics2D g2, int offset, Dimension dim) {
        Rectangle2D.Double tileBox;
        int tileSize = dim.width / boardSize;

        FontMetrics fm = g2.getFontMetrics();
        int scaleFactor = dim.width / (boardSize * 10 * fm.stringWidth("J"));

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                tileBox = new Rectangle2D.Double(
                        offset + c * tileSize,
                        offset + r * tileSize,
                        tileSize,
                        tileSize);
                tiles[r][c].paint(g2, tileBox, scaleFactor);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                sb.append(get(r,c));
                sb.append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

