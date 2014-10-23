package Maxflow;

/**
 * Created by david on 20/10/14.
 */
public class Node {
    int value;
    boolean traveled;
    boolean isXnode;

    public Node(int value, boolean isXnode) {
        this.value = value;
        this.isXnode = isXnode;
        traveled = false;
    }

}
