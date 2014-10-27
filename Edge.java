package Maxflow;

/**
 * A class holding pointers to two Nodes.
 * @author David Josefson and Elias Agetorp. Created: 2014-10-27
 */
public class Edge {
    Node firstNode;
    Node secondNode;

    public Edge(Node firstNode, Node secondNode){
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }
}
