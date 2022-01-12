/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */
import java.util.*;

/**
 *
 * A directed graph represented using adjacency lists.
 *
 * There are two methods to implement in this class: topological sorting
 * maximum flow.
 *
 */
class NoPathE extends Exception {}

public class DirectedGraph {
    private final Set<Node> nodes;
    private Hashtable<Node,ArrayList<Edge>> neighbors;

    DirectedGraph(Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.nodes = neighbors.keySet();
        this.neighbors = neighbors;
    }

    /**
     *
     * This constructor is used to clone the given graph creating
     * new copies of the edges. So the edges in either graph can be
     * updated without affecting the other graph.
     *
     */
    DirectedGraph(DirectedGraph graph) {
        this.nodes = graph.nodes;
        this.neighbors = new Hashtable<>();
        for (Node n : nodes) {
            ArrayList<Edge> ns = new ArrayList<>();
            for (Edge e : graph.neighbors.get(n)) {
                ns.add(new Edge(e.getSource(), e.getDestination(), e.getWeight()));
            }
            neighbors.put(n,ns);
        }
    }

    void setNeighbors (Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    public ArrayList<Edge> getNeighbors(Node n){
        return neighbors.get(n);
    }
    /**
     *
     * An implementation of Dijkstra's algorithm that throws an exception of the
     * destination is not reachable from the source. Otherwise it returns the list
     * of edges forming the shortest path from source to destination.
     *
     */
    ArrayList<Edge> shortestPath (Node source, Node destination) throws NoPathE {
        for (Node n : nodes) n.reset(Integer.MAX_VALUE);
        source.setValue(0);
        Heap heap = new Heap(nodes);
        assert heap.getMin().equals(source);

        while (heap.getSize() > 0) {
            Node current = heap.extractMin();
            if (current.equals(destination) && destination.getValue() != Integer.MAX_VALUE) {
                return destination.followPreviousEdge();
            }
            if (current.isVisited()) break;
            current.setVisited();
            for (Edge edge : neighbors.get(current)) {
                Node neighbor = edge.getDestination();
                int newWeight =  current.getValue() + edge.getWeight();
                if (newWeight < neighbor.getValue()) {
                    heap.update(neighbor,newWeight);
                    neighbor.setPreviousEdge(edge);
                }
            }
        }
        throw new NoPathE ();
    }

    /**
     *
     * This method has a similar structure to Dijkstra's algorithm with the
     * following differences:
     *
     *   - in the initialization phase, the value of each node is set to
     *     its in-degree (number of edges pointing to the node)
     *   - when extracting the minimum node from the heap, if the value is
     *     not 0 the algorithm throws an error as this indicates a cycle in
     *     the graph
     *   - instead of marking the extracted nodes as visited, we collect
     *     them into a queue
     *   - when relaxing an edge v --> w, the value of w is reduced by 1.
     *
     */

    public int getInDegree(Node n){
        int count = 0;
        for(Node source: nodes){
            for(Edge dest: neighbors.get(source)) {
                if(dest.getDestination().equals(n)){
                    count++;
                }
            }
        }
        return count;
    }

    public Queue<Node> topologicalSort () {
        Queue<Node> endQueue = new LinkedList<Node>();
        for (Node n : nodes){
            // set value of each node to its in-degree (out degree rn FIX)
            n.reset(getInDegree(n));
        }
        Heap heap = new Heap(nodes);

        while (heap.getSize() > 0) {
            Node current = heap.extractMin();
            // if value is not 0, throw error (cycle in graph)
            if (!(current.getValue() == 0)) {
                throw new Error();
            }
            endQueue.add(current);
            for (Edge edge : neighbors.get(current)) {
               Node d = edge.getDestination();
               heap.update(d, d.getValue()-1);
            }
        }
        return endQueue;
    }

    /**
     *
     * This algorithm maintains two intermediate structures:
     *   - a Hashtable<Edge,Edge> to represent the flow graph
     *   - a DirectedGraph to represent the residual graph
     *
     * The flow graph is represented in an unusual way as
     * a Hashtable<Edge,Edge>. The key to this hashtable is an
     * edge where we only care about the identity of the edge
     * (i.e., its source and destination but not its weight).
     * The values in the hashtable are edges in which the weights
     * are significant. So if the original graph had an edge 'e'
     * between nodes A and B, the key 'e' may be associated with
     * edges from A to B with different weights at different times.
     * Initially all these edges would have weight 0. The final
     * result of the algorithm is the collection of values in the
     * hashtable.
     *
     * The residual graph is initially created by cloning the
     * current graph using the special copy constructor.
     *
     * We then start a while (true) loop that terminates when
     * Dijkstra's algorithm fails to find a path from source
     * to destination in the residual graph.
     *
     * Each iteration of the loop performs the following actions:
     *   - compute the shortest path P from source to destination
     *     in the residual graph
     *   - get the minimum weight of any edge along this path P
     *   - update the flow graph by adding that minimum weight to
     *     all edges in P
     *   - recompute the residual graph as follows:
     *       - create a new hashtable of adjacency lists which
     *         will be used to update the residual graph by calling
     *         setNeighbors.
     *       - for each edge in the original graph, compute its new
     *         weight as original weight minus its weight in the
     *         flow graph
     *       - if the new weight is not zero, add the edge with its
     *         weight to the residual graph
     *       - for each edge A->B in the flow graph whose weight is
     *         not 0, add the edge B->A to the residual graph
     *
     *
     */

    Collection<Edge> maximumFlow (Node source, Node destination) {

        //  - a Hashtable<Edge,Edge> to represent the flow graph
        //  - a DirectedGraph to represent the residual graph

        //The residual graph is initially created by cloning the
        //current graph using the special copy constructor.
        DirectedGraph residualGraph = new DirectedGraph(this);
        Hashtable<Edge, Edge> flowGraph = new Hashtable<>();
        for(Node n: nodes){
            for(Edge e: neighbors.get(n)){
                Edge newE = new Edge(e.getSource(), e.getDestination(), 0);
                flowGraph.put(newE, newE);
            }
        }

        // We then start a while (true) loop that terminates when
        // Dijkstra's algorithm fails to find a path from source
        // to destination in the residual graph.
        // Each iteration of the loop performs the following actions:

        while(true) {
            try {
                //  - compute the shortest path P from source to destination
                //    in the residual graph
                ArrayList<Edge> P = residualGraph.shortestPath(source, destination);

                //  - get the minimum weight of any edge along this path P
                int minWeight = Integer.MAX_VALUE;
                for(Edge pe: P){ if(pe.getWeight()<minWeight) minWeight = pe.getWeight(); }

                //  - update the flow graph by adding that minimum weight to
                //    all edges in P
                for(Edge pe: P){
                    Edge oldFlow = flowGraph.get(pe);
                    Edge newFlow = new Edge(oldFlow.getSource(),
                            oldFlow.getDestination(),
                            oldFlow.getWeight()+minWeight);
                    flowGraph.put(newFlow, newFlow);
                }

                //  - recompute the residual graph as follows:

                //      - create a new hashtable of adjacency lists which
                //        will be used to update the residual graph by calling
                //        setNeighbors.
                Hashtable<Node, ArrayList<Edge>> newNeighbors = new Hashtable<>();

                for(Node n: nodes){
                    ArrayList<Edge> nsEdges = new ArrayList<Edge>();
                    for(Edge e: neighbors.get(n)){
                        int newWeight = e.getWeight()-flowGraph.get(e).getWeight();
                        if(newWeight != 0){
                            nsEdges.add(new Edge(e.getSource(), e.getDestination(), newWeight));
                        }
                    }
                    newNeighbors.put(n, nsEdges);
                }
                for(Edge e: flowGraph.values()){
                    if(e.getWeight()!=0){
                        newNeighbors.get(e.getDestination()).add(e.flip());
                        //GIVEN A->B ADD B->A TO RESIDUAL GRAPH
                    }
                }
                residualGraph.setNeighbors(newNeighbors);
                //      - for each edge in the original graph, compute its new
                //        weight as original weight minus its weight in the
                //        flow graph

                //      - if the new weight is not zero, add the edge with its
                //        weight to the residual graph

                //      - for each edge A->B in the flow graph whose weight is
                //        not 0, add the edge B->A to the residual graph
            }
            catch(NoPathE e){
                return flowGraph.values();
            }
        }
    }

    public String toString () {
        return neighbors.toString();
    }

    //----------------- STRONGLY CONNECTED COMPONENTS -----------------
    public Stack<Node> DFS(Node start){
        Stack<Node> stack = new Stack<Node>();
        Stack<Node> result = new Stack<>();
        stack.push(start);
        result.add(start);
        start.setVisited();
        while(!stack.empty()){
            Node next = (Node) stack.pop();
            for(Edge e: neighbors.get(next)){
                if(!e.getDestination().isVisited()){
                    stack.push(e.getDestination());
                    e.getDestination().setVisited();
                    result.push(e.getDestination());
                }
            }
        }
        return result;
    }

    public DirectedGraph transpose(){
        Hashtable<Node, ArrayList<Edge>> transposedNeighbors = new Hashtable<Node, ArrayList<Edge>>();
        for(Node n: nodes){
            transposedNeighbors.put(n, new ArrayList<>());
        }
        for(Node n: nodes){
            for(Edge e: neighbors.get(n)){
                ArrayList<Edge> destEdges = transposedNeighbors.get(e.getDestination());
                destEdges.add(e.flip());
                transposedNeighbors.put(e.getDestination(), destEdges);
            }
        }
        return new DirectedGraph(transposedNeighbors);
    }

    public void fillOrder(Node start, Stack stack){
        // Mark the current node as visited and print it
        // Recur for all adjacent Nodes
        // All vertices reachable from v are processed by now,
        // push node to Stack
        start.setVisited();
        for(Edge e: neighbors.get(start)){
            if(!e.getDestination().isVisited()){
                fillOrder(e.getDestination(), stack);
            }
        }
        stack.push(start);
    }


    // findSCC takes a DirectedGraph and returns a list of collections of edges that connect nodes such
    // that they, together, are a strong connected component
    public ArrayList<ArrayList<Node>> findSCC(){
        ArrayList<ArrayList<Node>> endList = new ArrayList<>();
        Stack stack = new Stack();

        for(Node n: nodes){
            n.reset(n.getValue());
        }

        for(Node n: nodes){
            if(!n.isVisited()){
                fillOrder(n, stack);
            }
        }

        DirectedGraph gr = this.transpose();

        System.out.println(this);
        System.out.println(gr);

        while(!stack.isEmpty()){
            Node current = (Node) stack.pop();
            System.out.println(current);
            if(!current.isVisited()){
                System.out.println(new ArrayList<>(gr.DFS(current)));
                endList.add(new ArrayList<>(gr.DFS(current)));
            }
        }
        return endList;
    }
}
