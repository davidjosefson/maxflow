package Maxflow;

/**
 * Created by david on 20/10/14.
 */
public class Node {
    int value;
    //Node pointer;
    boolean traveled;
    int capacity;

    public Node(int value) {
        this.value = value;
        traveled = false;
        capacity = 1;
    }

}
