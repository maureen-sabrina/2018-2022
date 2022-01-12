/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphTest {

    //---------------- LAB 14: PERSONAL TESTS BELOW ----------------

    @Test
    void findSCCTest(){
        int inf = Integer.MAX_VALUE;
        Node n1 = new Node("1", inf);
        Node n2 = new Node("2", inf);
        Node n3 = new Node("3", inf);
        Node n4 = new Node("4", inf);

        Edge e12 = new Edge(n1, n2, 1);
        Edge e13 = new Edge(n1, n3, 1);
        Edge e32 = new Edge(n3, n2, 1);
        Edge e34 = new Edge(n3, n4, 1);
        Edge e43 = new Edge(n4, n3, 1);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n1, new ArrayList<>(Arrays.asList(e12, e13)));
        neighbors.put(n2, new ArrayList<>(new ArrayList<>()));
        neighbors.put(n3, new ArrayList<>(Arrays.asList(e32, e34)));
        neighbors.put(n4, new ArrayList<>(Collections.singletonList(e43)));

        DirectedGraph graph = new DirectedGraph(neighbors);
        ArrayList<ArrayList<Node>> firstCycle = new ArrayList<>();
        firstCycle.add(new ArrayList<>(Arrays.asList(n3, n4)));
        assertEquals(firstCycle, graph.findSCC());
    }

    @Test
    void DFSTest(){
        int inf = Integer.MAX_VALUE;
        Node n1 = new Node("1", inf);
        Node n2 = new Node("2", inf);
        Node n3 = new Node("3", inf);
        Node n4 = new Node("4", inf);

        Edge e12 = new Edge(n1, n2, 1);
        Edge e13 = new Edge(n1, n3, 1);
        Edge e32 = new Edge(n3, n2, 1);
        Edge e34 = new Edge(n3, n4, 1);
        Edge e43 = new Edge(n4, n3, 1);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n1, new ArrayList<>(Arrays.asList(e12, e13)));
        neighbors.put(n2, new ArrayList<>(new ArrayList<>()));
        neighbors.put(n3, new ArrayList<>(Arrays.asList(e32, e34)));
        neighbors.put(n4, new ArrayList<>(Collections.singletonList(e43)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        Stack result = graph.DFS(n1);
        assertEquals(4, result.size());
        assertEquals(n4, result.pop());
        assertEquals(n3, result.pop());
        assertEquals(n2, result.pop());
        assertEquals(n1, result.pop());
    }

    @Test
    void transposeTest() {
        int inf = Integer.MAX_VALUE;
        Node n1 = new Node("1", inf);
        Node n2 = new Node("2", inf);
        Node n3 = new Node("3", inf);
        Node n4 = new Node("4", inf);

        Edge e12 = new Edge(n1, n2, 3);
        Edge e13 = new Edge(n1, n3, 14);
        Edge e32 = new Edge(n3, n2, 55);
        Edge e34 = new Edge(n3, n4, 7);
        Edge e43 = new Edge(n4, n3, 20);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(n1, new ArrayList<>(Arrays.asList(e12, e13)));
        neighbors.put(n2, new ArrayList<>(new ArrayList<>()));
        neighbors.put(n3, new ArrayList<>(Arrays.asList(e32, e34)));
        neighbors.put(n4, new ArrayList<>(Collections.singletonList(e43)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        DirectedGraph result = graph.transpose();
        assertEquals(new ArrayList<>(), result.getNeighbors(n1));
        assertEquals(new ArrayList<>(Arrays.asList(e32.flip(), e12.flip())), result.getNeighbors(n2));
        assertEquals(new ArrayList<>(Arrays.asList(e43.flip(), e13.flip())), result.getNeighbors(n3));
        assertEquals(new ArrayList<>(Collections.singletonList(e34.flip())), result.getNeighbors(n4));
    }


    //---------------- lAB 13: PERSONAL TESTS BELOW ----------------

    @Test
    void getInDegreeTest() throws NoPathE{
        //getInDegree method -->
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node f = new Node("f", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        assertEquals(0, graph.getInDegree(s));
        assertEquals(1, graph.getInDegree(a));
        assertEquals(2, graph.getInDegree(b));
        assertEquals(1, graph.getInDegree(c));
        assertEquals(3, graph.getInDegree(f));
        assertEquals(2, graph.getInDegree(t));
    }

    @Test
    void maxFlow2(){
        Node s = new Node("s", 0);
        Node a = new Node("a", 0);
        Node b = new Node("b", 0);
        Node t = new Node("t", 0);

        Edge sa = new Edge(s,a,3);
        Edge sc = new Edge(s,b,2);

        Edge ab = new Edge(a,b,5);
        Edge at = new Edge(a,t,2);

        Edge bt = new Edge(b,t,3);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sc)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,at)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bt)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        Collection<Edge> flow = graph.maximumFlow(s,t);
        assertEquals(5,flow.size());
        assertTrue(flow.contains(new Edge(s,a,2)));
        assertTrue(flow.contains(new Edge(a,t,2)));
        assertTrue(flow.contains(new Edge(a,b,1)));
        assertTrue(flow.contains(new Edge(s,b,2)));
        assertTrue(flow.contains(new Edge(b,t,3)));
        System.out.println(flow);

        /*
        FLOW GRAPH CREATED ABOVE -->

        3/3              2/2
          ______ A ______
         /       |       \
       S         |1/5     T
         \______ | ______/
        2/2      B       3/3

         */
    }

    //-------------------- GIVEN TESTS BELOW ---------------------

    @Test
    void shortestPaths () throws NoPathE {
        int inf = Integer.MAX_VALUE;
        Node s = new Node("s", inf);
        Node a = new Node("a", inf);
        Node b = new Node("b", inf);
        Node c = new Node("c", inf);
        Node f = new Node("f", inf);
        Node t = new Node("t", inf);

        Edge sa = new Edge(s,a,3);
        Edge sb = new Edge(s,b,4);
        Edge ab = new Edge(a,b,6);
        Edge bf = new Edge(b,f,5);
        Edge af = new Edge(a,f,7);
        Edge ac = new Edge(a,c,2);
        Edge cf = new Edge(c,f,1);
        Edge ct = new Edge(c,t,8);
        Edge ft = new Edge(f,t,4);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,af,ac)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bf)));
        neighbors.put(c, new ArrayList<>(Arrays.asList(cf,ct)));
        neighbors.put(f, new ArrayList<>(Collections.singletonList(ft)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        ArrayList<Edge> path = graph.shortestPath(s,t);
        assertEquals(4,path.size());
        assertEquals(sa,path.get(0));
        assertEquals(ac,path.get(1));
        assertEquals(cf,path.get(2));
        assertEquals(ft,path.get(3));
    }

    @Test
    public void topologicalSort () {
        Node v1 = new Node("v1", 0);
        Node v2 = new Node("v2", 0);
        Node v3 = new Node("v3", 0);
        Node v4 = new Node("v4", 0);
        Node v5 = new Node("v5", 0);
        Node v6 = new Node("v6", 0);
        Node v7 = new Node("v7", 0);

        Edge v12 = new Edge(v1,v2,0);
        Edge v13 = new Edge(v1,v3,0);
        Edge v14 = new Edge(v1,v4,0);
        Edge v24 = new Edge(v2,v4,0);
        Edge v25 = new Edge(v2,v5,0);
        Edge v36 = new Edge(v3,v6,0);
        Edge v43 = new Edge(v3,v3,0);
        Edge v46 = new Edge(v4,v6,0);
        Edge v47 = new Edge(v4,v7,0);
        Edge v54 = new Edge(v5,v4,0);
        Edge v57 = new Edge(v5,v7,0);
        Edge v76 = new Edge(v7,v6,0);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(v1, new ArrayList<>(Arrays.asList(v12,v13,v14)));
        neighbors.put(v2, new ArrayList<>(Arrays.asList(v24,v25)));
        neighbors.put(v3, new ArrayList<>(Collections.singletonList(v36)));
        neighbors.put(v4, new ArrayList<>(Arrays.asList(v43,v46,v47)));
        neighbors.put(v5, new ArrayList<>(Arrays.asList(v54,v57)));
        neighbors.put(v6, new ArrayList<>());
        neighbors.put(v7, new ArrayList<>(Collections.singletonList(v76)));

        DirectedGraph graph = new DirectedGraph(neighbors);

        Queue<Node> sort = graph.topologicalSort();
        assertEquals(7, sort.size());
        assertEquals(v1,sort.poll());
        assertEquals(v2,sort.poll());
        assertEquals(v5,sort.poll());
        assertEquals(v4,sort.poll());
        assertEquals(v3,sort.poll());
        assertEquals(v7,sort.poll());
        assertEquals(v6,sort.poll());
    }

    @Test
    void maximumFlow () {
        Node s = new Node("s", 0);
        Node a = new Node("a", 0);
        Node b = new Node("b", 0);
        Node c = new Node("c", 0);
        Node d = new Node("d", 0);
        Node t = new Node("t", 0);

        Edge sa = new Edge(s,a,4);
        Edge sb = new Edge(s,b,2);
        Edge ab = new Edge(a,b,1);
        Edge ac = new Edge(a,c,2);
        Edge ad = new Edge(a,d,4);
        Edge bd = new Edge(b,d,2);
        Edge ct = new Edge(c,t,3);
        Edge dt = new Edge(d,t,3);

        Hashtable<Node, ArrayList<Edge>> neighbors = new Hashtable<>();
        neighbors.put(s, new ArrayList<>(Arrays.asList(sa,sb)));
        neighbors.put(a, new ArrayList<>(Arrays.asList(ab,ac,ad)));
        neighbors.put(b, new ArrayList<>(Collections.singletonList(bd)));
        neighbors.put(c, new ArrayList<>(Collections.singletonList(ct)));
        neighbors.put(d, new ArrayList<>(Collections.singletonList(dt)));
        neighbors.put(t, new ArrayList<>());

        DirectedGraph graph = new DirectedGraph(neighbors);

        Collection<Edge> flow = graph.maximumFlow(s,t);
        assertEquals(8,flow.size());
        assertTrue(flow.contains(new Edge(s,a,3)));
        assertTrue(flow.contains(new Edge(s,b,2)));
        assertTrue(flow.contains(new Edge(a,b,0)));
        assertTrue(flow.contains(new Edge(a,c,2)));
        assertTrue(flow.contains(new Edge(a,d,1)));
        assertTrue(flow.contains(new Edge(b,d,2)));
        assertTrue(flow.contains(new Edge(c,t,2)));
        assertTrue(flow.contains(new Edge(d,t,2)));
    }

}