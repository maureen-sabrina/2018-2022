import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  PERSONAL TESTS BELOW
 */

class BoardTest {

    /*
        public Board(Tile[][] tiles, Words dict) {
        this.tiles = tiles;
        this.boardSize = tiles.length;
        this.dict = dict;
    }
     */

    @Test
    void tileIsValid() {
        Tile[][] testTiles = new Tile[2][2];
        testTiles[0][0] = new Tile('n', 0, 0);
        testTiles[0][1] = new Tile('o', 0, 1);
        testTiles[1][0] = new Tile('y', 1, 0);
        testTiles[1][1] = new Tile('m', 1, 1);
        /**
         *   n o
         *   y m
         */
        Trie testDict = new Trie();
        testDict.insert("no"); testDict.insert("mo"); testDict.insert("my");
        Board testBoard = new Board(testTiles, testDict);

        assertEquals(true, testBoard.tileIsValid(0, 1));
        assertEquals(false, testBoard.tileIsValid(-1, 1));
        assertEquals(false, testBoard.tileIsValid(1, 2));
        testTiles[0][1].setVisited();
        assertEquals(false, testBoard.tileIsValid(0, 1));
    }

    @Test
    void getFreshNeighbors() {
        Tile[][] testTiles = new Tile[2][2];
        testTiles[0][0] = new Tile('n', 0, 0);
        testTiles[0][1] = new Tile('o', 0, 1);
        testTiles[1][0] = new Tile('y', 1, 0);
        testTiles[1][1] = new Tile('m', 1, 1);
        /**
         *   n o
         *   y m
         */
        Trie testDict = new Trie();
        testDict.insert("no"); testDict.insert("mo"); testDict.insert("my");
        Board testBoard = new Board(testTiles, testDict);

        ArrayList<Tile> fresh =
                new ArrayList<Tile>(Arrays.asList(
                        new Tile('n', 0, 0),
                        new Tile('o', 0, 1),
                        new Tile('y', 1, 0)));
        assertEquals(fresh.toString(),
                testBoard.getFreshNeighbors(1,1).toString());
        testTiles[1][0].setVisited();
        fresh.remove(2);
        assertEquals(fresh.toString(),
                testBoard.getFreshNeighbors(1,1).toString());
    }

    @Test
    void findWordFromPosn(){
        Tile[][] testTiles = new Tile[2][2];
        testTiles[0][0] = new Tile('n', 0, 0);
        testTiles[0][1] = new Tile('o', 0, 1);
        testTiles[1][0] = new Tile('y', 1, 0);
        testTiles[1][1] = new Tile('m', 1, 1);
        /**
         *   n o
         *   y m
         */
        Trie testDict = new Trie();
        testDict.insert("no"); testDict.insert("mo"); testDict.insert("my");
        Board testBoard = new Board(testTiles, testDict);
        System.out.println(testDict.toString());

        HashSet<String> result = testBoard.findWordsFromPos(0,0, "");
        System.out.println(result.toString());

    }
}