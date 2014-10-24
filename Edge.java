package Maxflow;

/**
 * Created by david on 20/10/14.
 */
public class Edge {
    Node x;
    Node y;
    int flow;
    int capacity;
    int residualCapacity;

    public Edge(Node firstNode, Node secondNode){
        /* if(firstNode.isXnode) {
            this.x = firstNode;
            this.y = secondNode;
        }
        else {
            this.x = firstNode;
            this.y = secondNode;
        }*/
        this.x = firstNode;
        this.y = secondNode;
    }
}
