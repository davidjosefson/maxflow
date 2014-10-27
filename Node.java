package Maxflow;

/**
 * A class holding a value and some extra information
 * @author David Josefson and Elias Agetorp. Created: 2014-10-27
 */
public class Node {
    int value;
    boolean isUnode;
    boolean isStartOrSink;

    public Node(int value, boolean isUnode, boolean isStartOrSink) {
        this.value = value;
        this.isUnode = isUnode;
        this.isStartOrSink = isStartOrSink;
    }

}
