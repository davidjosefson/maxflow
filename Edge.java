package Maxflow;

/**
 * Created by david on 20/10/14.
 */
public class Edge {
    Node firstNode;
    Node secondNode;
    int flow;
    int capacity;
    int residualCapacity;

    public Edge(Node firstNode, Node secondNode, int flow){
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.flow = flow;
    }
}
