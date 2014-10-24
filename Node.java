package Maxflow;

/**
 * Created by david on 20/10/14.
 */
public class Node {
    int value;
    boolean isXnode;
    boolean isStartOrSink;

    public Node(int value, boolean isXnode, boolean isStartOrSink) {
        this.value = value;
        this.isXnode = isXnode;
        this.isStartOrSink = isStartOrSink;
    }

}
