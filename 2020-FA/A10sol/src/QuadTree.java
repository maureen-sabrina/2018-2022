/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */

import java.math.BigInteger;
import java.util.HashMap;

class WrongRegionE extends Exception {}

abstract class QuadTree {
    abstract int getLevel();
    abstract int liveCells();
    abstract boolean isAlive();
    abstract QuadTree getNW() throws WrongRegionE;
    abstract QuadTree getNE() throws WrongRegionE;
    abstract QuadTree getSW() throws WrongRegionE;
    abstract QuadTree getSE() throws WrongRegionE;
    abstract QuadTree step();

    //------------------------------------------------------------------------------------
    //-- Static methods ------------------------------------------------------------------
    //------------------------------------------------------------------------------------

    static final HashMap<QuadTree,QuadTree> hash = new HashMap<>();

    static QuadTree intern (QuadTree tree) {
        QuadTree t = hash.get(tree);
        if (t != null) return t;
        hash.put(tree,tree);
        return tree;
    }

    static QuadTree newCell (boolean status) {
        return intern(new Cell(status));
    }

    static QuadTree newRegion (QuadTree nw, QuadTree ne, QuadTree sw, QuadTree se) {
        return intern(new Region(nw,ne,sw,se));
    }

    static QuadTree emptyQuadTree (int level) {
        if (level == 0) {
            return newCell(false);
        }
        else {
            QuadTree empty = emptyQuadTree(level-1);
            return newRegion(empty,empty,empty,empty);
        }
    }

    static QuadTree fromArray(int size, boolean[][] points) {
        // size should be a power of 2
        // level k = log size
        if (size == 1) // k = 0
            return newCell(points[0][0]); // level = 0
        else {
            int subSize = size / 2;
            boolean[][] nwPoints = new boolean[subSize][subSize];
            boolean[][] nePoints = new boolean[subSize][subSize];
            boolean[][] swPoints = new boolean[subSize][subSize];
            boolean[][] sePoints = new boolean[subSize][subSize];
            for (int i = 0; i < subSize; i++)
                System.arraycopy(points[i], 0, nwPoints[i], 0, subSize);
            for (int i = 0; i < subSize; i++)
                System.arraycopy(points[i], subSize, nePoints[i], 0, subSize);
            for (int i = 0; i < subSize; i++)
                System.arraycopy(points[subSize + i], 0, swPoints[i], 0, subSize);
            for (int i = 0; i < subSize; i++)
                System.arraycopy(points[subSize + i], subSize, sePoints[i], 0, subSize);
            return newRegion(
                    fromArray(subSize, nwPoints),
                    fromArray(subSize, nePoints),
                    fromArray(subSize, swPoints),
                    fromArray(subSize, sePoints));
        }
    }

    static boolean[][] toArray(QuadTree region) {
        int k = region.getLevel();
        int size = new BigInteger("2").pow(k).intValue();
        boolean[][] points = new boolean[size][size];
        try {
            if (k == 0) {
                points[0][0] = region.isAlive();
            } else {
                boolean[][] nwPoints = toArray(region.getNW());
                boolean[][] nePoints = toArray(region.getNE());
                boolean[][] swPoints = toArray(region.getSW());
                boolean[][] sePoints = toArray(region.getSE());
                int subSize = size / 2;
                for (int i = 0; i < subSize; i++)
                    System.arraycopy(nwPoints[i], 0, points[i], 0, subSize);
                for (int i = 0; i < subSize; i++)
                    System.arraycopy(nePoints[i], 0, points[i], subSize, subSize);
                for (int i = 0; i < subSize; i++)
                    System.arraycopy(swPoints[i], 0, points[subSize + i], 0, subSize);
                for (int i = 0; i < subSize; i++)
                    System.arraycopy(sePoints[i], 0, points[subSize + i], subSize, subSize);
            }
            return points;
        } catch (WrongRegionE e) {
            throw new Error("toArray");
        }
    }
}

// ---------------------------------------------------------------------------------

class Cell extends QuadTree {
    private final boolean status;

    Cell (boolean status) { this.status = status; }

    int getLevel () { return 0; }
    int liveCells () { return status? 1 : 0; }
    boolean isAlive() { return status; }
    QuadTree getNW() throws WrongRegionE { throw new WrongRegionE(); }
    QuadTree getNE() throws WrongRegionE { throw new WrongRegionE(); }
    QuadTree getSW() throws WrongRegionE { throw new WrongRegionE(); }
    QuadTree getSE() throws WrongRegionE { throw new WrongRegionE(); }
    QuadTree step() { throw new Error("Step should never be called on Cell"); }

    public boolean equals (Object o) {
        if (o instanceof Cell) return ((Cell)o).status == status;
        return false;
    }

    public int hashCode () {
        return liveCells();
    }

    public String toString () { return status ? "*" : "_"; }
}

// ---------------------------------------------------------------------------------

class Region extends QuadTree {
    private final int k; // k > 0; current region is of size 2^k x 2^k
    private final QuadTree nw;
    private final QuadTree ne;
    private final QuadTree sw;
    private final QuadTree se; // corner regions of size 2^(k-1) x 2^(k-1)
    private final int live;
    private QuadTree nextGen;

    Region (QuadTree nw, QuadTree ne, QuadTree sw, QuadTree se) {
        this.k = nw.getLevel()+1;
        this.nw = nw;
        this.ne = ne;
        this.sw = sw;
        this.se = se;
        live = nw.liveCells() + ne.liveCells() + sw.liveCells() + se.liveCells();
    }

    int getLevel () { return k ; }
    int liveCells () { return live; }
    boolean isAlive() { return live > 0; }
    QuadTree getNW() { return nw; }
    QuadTree getNE() { return ne; }
    QuadTree getSW() { return sw; }
    QuadTree getSE() { return se; }

    QuadTree centered () throws WrongRegionE {
        return QuadTree.newRegion(
                nw.getSE(),
                ne.getSW(),
                sw.getNE(),
                se.getNW());
    }
    // ^^ checked for correctness $

    QuadTree centeredHorizontal (QuadTree w, QuadTree e) throws WrongRegionE {
        return QuadTree.newRegion(
                w.getNE().getSE(),
                e.getNW().getSW(),
                w.getSE().getNE(),
                e.getSW().getNW());
    }
    //centeredHorizontal checked for correctness $

    QuadTree centeredVertical (QuadTree n, QuadTree s) throws WrongRegionE {
        return QuadTree.newRegion(
                n.getSW().getSE(),
                n.getSE().getSW(),
                s.getNW().getNE(),
                s.getNE().getNW());
    }
    //centeredVertical checked for correctness $

    QuadTree centeredx2() throws WrongRegionE {
        return QuadTree.newRegion(
                nw.getSE().getSE(),
                ne.getSW().getSW(),
                sw.getNE().getNE(),
                se.getNW().getNW());
    }

    QuadTree step() {
        if (nextGen != null) return nextGen;
        if (k == 2) { // have a 4x4 grid
            GameMatrix g = new GameMatrix(4, 0, toArray(this));
            g.step();
            //Need to take the INNER region which is smaller: a 4x4 -> 2x2 (fixed below)
            /*
            boolean[][] gpoints     = g.getPoints();
            boolean[][] innerPoints = new boolean[2][2];
            innerPoints[0][0] = gpoints[1][1];
            innerPoints[0][1] = gpoints[1][2];
            innerPoints[1][0] = gpoints[2][1];
            innerPoints[1][1] = gpoints[2][2];
            nextGen = fromArray(2, innerPoints);

             */
            try {
                nextGen = ((Region) fromArray(4, g.getPoints())).centered();
            }
            catch (WrongRegionE wrongRegionE) {
                wrongRegionE.printStackTrace();
            }
            return nextGen;
        } else {
            try {

                //n00 should be rank 1
                //all steps should not happen here, should happen all at once at the end
                QuadTree n00 = ((Region)nw).centered();
                QuadTree n01 = centeredHorizontal(nw, ne);
                QuadTree n02 = ((Region)ne).centered();
                QuadTree n10 = centeredVertical(nw, sw);

                QuadTree n11 = centeredx2();

                QuadTree n12 = centeredVertical(ne, se);
                QuadTree n20 = ((Region)sw).centered();
                QuadTree n21 = centeredHorizontal(sw, se);
                QuadTree n22 = ((Region)se).centered();
               // System.out.println("successfully passed creating n00/22 Quadtrees");

                QuadTree nnw = QuadTree.newRegion(n00, n01, n10, n11).step();
                QuadTree nne = QuadTree.newRegion(n01, n02, n11, n12).step();
                QuadTree nsw = QuadTree.newRegion(n10, n11, n20, n21).step();
                QuadTree nse = QuadTree.newRegion(n11, n12, n21, n22).step();

                nextGen = QuadTree.newRegion(nnw, nne, nsw, nse);
                return nextGen;
            } catch (WrongRegionE e) {
                throw new Error("Internal bug: recursive step");
            }
        }
    }

    public boolean equals (Object o) {
        if (o instanceof Region) {
            Region other = (Region)o;
            return other.getLevel() == getLevel() &&
                    nw.equals(other.nw) &&
                    ne.equals(other.ne) &&
                    sw.equals(other.sw) &&
                    se.equals(other.se);
        }
        return false;
    }

    public int hashCode () {
        return 7 * nw.hashCode() + 31 * ne.hashCode() + 307 * sw.hashCode() + 2531 * se.hashCode();
    }

    public String toString () {
        return String.format("NW=[%s]; NE=[%s]; SW=[%s]; SE=[%s]%n", nw, ne, sw, se);
    }
}
