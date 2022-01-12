/**
 * @author Maureen Lynch
 * @version 1.0
 * @date Fall 2020
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 *
 * A undirected graph represented using adjacency lists. There is one method
 * to implement in this class which computes a minimum spanning tree.
 *
 */
public class UndirectedGraph {
    private final Hashtable<Node, ArrayList<Edge>> neighbors;
    private final Set<Node> nodes;

    UndirectedGraph(Hashtable<Node,ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
        this.nodes = neighbors.keySet();
    }

    /**
     * The outline of this algorithm is quite similar to Dijkstra's
     * shortest path algorithm:
     *
     *   - Initialize the nodes values and put them in a heap
     *   - Repeatedly extract the min node from the heap and
     *     relax its outgoing edges. If the weight of an edge
     *     is less than the current value at its destination,
     *     then the destination's value is updated and its
     *     previous pointer is updated.
     *
     * The result will be the collection of all edges stored
     * in the previous pointers.
     *
     */

    // Returns set of smallest weight edges
    Set<Edge> minimumSpanningTree (Node source) {
        // - Initialize the nodes values and put them in a heap
        for(Node n: nodes){
            n.reset(Integer.MAX_VALUE);
        }
        source.setValue(0);
        Heap heap = new Heap(nodes);

        // - Repeatedly extract the min node from the heap and
        //   relax its outgoing edges. If the weight of an edge
        //   is less than the current value at its destination,
        //   then the destination's value is updated and its
        //   previous pointer is updated.
        Set<Edge> endSet = new HashSet<Edge>();
        while(heap.getSize() > 0) {
            Node min = heap.extractMin();
            if(min.isVisited()){ break;}
            else{ min.setVisited();}
            for (Edge e : neighbors.get(min)) {
                if(!e.getDestination().isVisited() && e.getWeight() < e.getDestination().getValue()){
                    heap.update(e.getDestination(), e.getWeight());
                    e.getDestination().setPreviousEdge(e);
                }
            }
        }
        for(Node n: nodes){
            if(n.getPreviousEdge() != null){
                endSet.add(n.getPreviousEdge());
            }
        }
        // The result will be the collection of all edges stored
        // in the previous pointers.
        return endSet;
    }

    public String toString () {
        return neighbors.toString();
    }
}
