/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {

    //------------------- PERSONAL TESTS BELOW -------------------

    @Test
    void simpleGraph2() {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node d = new Node("d", inf);
        Node e = new Node("e", inf);
        Node f = new Node("f", inf);

        Edge sa = new Edge(s, a, 5);
        Edge sc = new Edge(s, c, 17);

        Edge as = new Edge(a, s, 5);
        Edge ac = new Edge(a, c, 12);
        Edge ab = new Edge(a, b, 8);
        Edge ae = new Edge(a, e, 13);

        Edge bd = new Edge(b, d, 4);
        Edge bc = new Edge(b, c, 8);
        Edge ba = new Edge(b, a, 8);
        Edge be = new Edge(b, e, 11);
        Edge bf = new Edge(b, f, 14);

        Edge cs = new Edge(c, s, 17);
        Edge ca = new Edge(c, a, 12);
        Edge cd = new Edge(c, d, 10);
        Edge cb = new Edge(c, b, 8);

        Edge dc = new Edge(d, c, 10);
        Edge db = new Edge(d, b, 4);

        Edge ea = new Edge(e, a, 13);
        Edge eb = new Edge(e, b, 11);
        Edge ef = new Edge(e, f, 6);

        Edge fe = new Edge(f, e, 6);
        Edge fb = new Edge(f, b, 14);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa, sc)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(as, ac, ab, ae)));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bd, bc, ba, bf, be)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cs, ca, cd, cb)));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dc, db)));
        neighbors.put(e, new ArrayList<>(Arrays.asList(ea, eb, ef)));
        neighbors.put(f, new ArrayList<>(Arrays.asList(fe, fb)));

        UndirectedGraph graph = new UndirectedGraph(neighbors);

        Set<Edge> tree = graph.minimumSpanningTree(s);
        System.out.println(tree);
        assertEquals(6, tree.size());

        assertTrue(tree.contains(sa));
        assertTrue(tree.contains(ab));
        assertTrue(tree.contains(bc));
        assertTrue(tree.contains(bd));
        assertTrue(tree.contains(be));
        assertTrue(tree.contains(ef));

    }

    //-------------------- GIVEN TESTS BELOW ---------------------

    @Test
    void simpleGraph() {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node d = new Node("d", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s, a, 7);
        Edge sc = new Edge(s, c, 8);
        Edge ab = new Edge(a, b, 6);
        Edge ac = new Edge(a, c, 3);
        Edge cb = new Edge(c, b, 4);
        Edge cd = new Edge(c, d, 3);
        Edge bd = new Edge(b, d, 2);
        Edge bt = new Edge(b, t, 5);
        Edge dt = new Edge(d, t, 2);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa, sc)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ac, ab, sa.flip())));
        neighbors.put(b, new ArrayList<>(Arrays.asList(bd,bt,ab.flip(),cb.flip())));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cb, cd, ac.flip(), sc.flip())));
        neighbors.put(d, new ArrayList<>(Arrays.asList(dt,bd.flip(),cd.flip())));
        neighbors.put(t, new ArrayList<>(Arrays.asList(bt.flip(),dt.flip())));

        UndirectedGraph graph = new UndirectedGraph(neighbors);

        Set<Edge> tree = graph.minimumSpanningTree(s);
        for(Edge e: tree){
            System.out.println(e);
        }
        assertEquals(5, tree.size());
        assertTrue(tree.contains(sa));
        assertTrue(tree.contains(ac));
        assertTrue(tree.contains(cd));
        assertTrue(tree.contains(bd.flip()));
        assertTrue(tree.contains(dt));
        
    }
}