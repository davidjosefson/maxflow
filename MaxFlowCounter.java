package Maxflow;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A class which finds the maximum mathing in a bipartite graph.
 * @author David Josefson and Elias Agetorp. Created: 2014-10-27
 */
public class MaxFlowCounter {
    private int[][] CapacityGraph;
    private int[][] FlowGraph;
    private ArrayList<Node> nodeArray;
    private Stack<Node> traveledStack;
    private Stack<Edge> pathStack;

    /**
     * Constructor which instantiates stacks, creates graphs and finds the max flow
     * @param input a two dimensional array of edges (node pairs)
     */
    public MaxFlowCounter(int[][] input) {
        traveledStack = new Stack<Node>();
        pathStack = new Stack<Edge>();

        createGraphs(input);
        MaxFlowFulkerson();
    }

    private void createGraphs(int[][] input) {
        //Create a list of the nodes from input array
        createNodeList(input);

        //Initiate flow and capacity graph
        CapacityGraph = new int[nodeArray.size()][nodeArray.size()];
        FlowGraph = new int[nodeArray.size()][nodeArray.size()];

        //Set capacity to 1 in capacityGraph where an edge exists
        setCapacity(input);
    }

    /**
     * Creates a list of nodes from the input array of edges. These nodes with will be used as a reference.
     * @param input a two dimensional array of edges (node pairs)
     */
    private void createNodeList(int[][] input) {
        //A list of all nodes in one axis, startnode, u-nodes, v-nodes and sinknode
        nodeArray = new ArrayList<Node>();

        //Add a startnode
        nodeArray.add(new Node(0, false, true));

        //Fill the list with u-nodes and v-nodes
        for(int[] i : input){
            //Skip the row with amount of edges
            if (i == input[0])
                continue;

            boolean uIsFound = false;
            boolean vIsFound = false;
            for (Node node: nodeArray){

                if((node.value == i[0] && node.isUnode && !node.isStartOrSink)) {
                    uIsFound = true;
                }
                if((node.value == i[1] && !node.isUnode && !node.isStartOrSink)) {
                    vIsFound = true;
                }

                if(uIsFound && vIsFound)
                    break;
            }

            if(!uIsFound)
                nodeArray.add(new Node(i[0], true, false));
            if(!vIsFound)
                nodeArray.add(new Node(i[1], false, false));

        }

        //Add a sink-node and the end of the list
        nodeArray.add(new Node(0, true, true));
    }

    /**
     * Sets capacity to 1 in the capacity graph where there is a corresponding edge in the input array of edges
     * @param input a two dimensional array of edges (node pairs)
     */
    private void setCapacity(int[][] input) {
        //Loop through the input-list and set the corresponding edges to 1 in the matrix
        for (int i = 0; i < input.length; i++) {
            int[] inputRow = input[i];

            //Skip the row with amount of edges
            if (inputRow == input[0])
                continue;

            int u = 0;

            //Find index of u-node from nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == inputRow[0] && nodeArray.get(j).isUnode) {
                    u = j;
                    break;
                }
            }

            int v = 0;

            //Find index of v-node from nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == inputRow[1] && !nodeArray.get(j).isUnode) {
                    v = j;
                    break;
                }
            }

            //set the edge to 1
            CapacityGraph[v][u] = 1;
            CapacityGraph[u][v] = 1;

            //Set startNode to u-node edge to 1 on both y-axis and x-axis
            CapacityGraph[0][u] = 1;
            CapacityGraph[u][0] = 1;

            //Set sinkNode to v-node edge to 1 on both y-axis and x-axis
            CapacityGraph[CapacityGraph.length - 1][v] = 1;
            CapacityGraph[v][CapacityGraph.length - 1] = 1;

        }
    }


    /**
     * Reduces the maximum bipartite matching to a maximum flow using the Ford-Fulkerson method
     */
    private void MaxFlowFulkerson(){
        //Capacity will always be 1 if there is an edge in the Bipartite maximum matching problem
        int c = 1;

        //DFS returns null if no path is found
        while(DFS(nodeArray.get(0)) != null) {
            for(Edge edge : pathStack) {
                //Find index of u and v-nodes from nodeArray
                int u = nodeArray.indexOf(edge.firstNode);
                int v = nodeArray.indexOf(edge.secondNode);

                //Set flow
                FlowGraph[u][v] = FlowGraph[u][v] + c;
                FlowGraph[v][u] = -FlowGraph[u][v];
            }
            //Reset the instance variables pathStack and traveledStack before next
            //path-search in the while loop
            pathStack = new Stack<Edge>();
            traveledStack = new Stack<Node>();
        }

    }

    /**
     * This recursive implementation of depth-first search does the following:
     * 1. Add @param node to the stack with traveled nodes.
     * 2. Find adjacent nodes and add them to the stack with nodes to search from (nodesToSearch)
     * 3. Check if the top node is the sink-node, which means that a path has been found
     *      3.1 Add an edge from this node to sink-node to the stack with the edges of this path (pathStack)
     * 4. Loop through each node in nodesToSearch
     *      4.1 Set childNode to a recursive call of this method on the top node in nodesToSearch
     *      4.2 If childNode is not null, it means a path has been found and an edge between this node and
     *          childNode is to be added into pathStack
     * @param node node to search from
     * @return null if no path is found, otherwise the @param node
     */
    private Node DFS(Node node){
        //Stack for all the children of this node (possible paths to sinknode)
        Stack<Node> nodesToSearch = new Stack<Node>();

        //Mark this node as traveled
        traveledStack.push(node);

        int[] capRow = CapacityGraph[nodeArray.indexOf(node)] ;
        int[] flowRow = FlowGraph[nodeArray.indexOf(node)] ;

        //Loop through the row and add all children (where there is a 1)
        for (int i = 0; i < capRow.length; i++) {
            // Make sure there is an edge between nodes and that there is no flow.
            if(capRow[i] == 1 && flowRow[i] < 1) {
                //Check if node is not already traveled
                if(!traveledStack.contains(nodeArray.get(i))) {
                    //Add node to nodesToSearch-stack
                    nodesToSearch.push(nodeArray.get(i));
                }
            }
        }

        if(!nodesToSearch.isEmpty()){
            //If the sink-node is a child of this node, a path has been found
            if(nodesToSearch.peek() == nodeArray.get(nodeArray.size() -1)) {
                //save the edge from this node to sink-node
                Edge edge = new Edge(node, nodesToSearch.pop());
                pathStack.push(edge);
                return node;
            }
        }

        while (!nodesToSearch.empty()){
            //Recursive call to search from child of this node
            Node childNode = DFS(nodesToSearch.pop());
            if (childNode != null) {
                Edge edge = new Edge(node, childNode);
                pathStack.push(edge);
                return node;
            }
        }

        return null;
    }

    /**
     * Present the capacity/flow graphs and edges in the flow as a string
     * @return result of the max flow
     */
    public String toString() {
        String out = "";

        out += "Capacity matrix: \n";
        out += graphToString(CapacityGraph);
        out += "\n";

        out += "Flow matrix: \n";
        out += graphToString(FlowGraph);
        out += "\n";

        out += pathsToString();

        return out;
    }

    /**
     * Prints the matrix of graph @param
     * @param array Graph to print
     */
    private String graphToString(int[][] array) {
        String out = "   ";

        //Prints first row with node-names as columns
        for(int i = 0; i < nodeArray.size(); i++) {
            if(nodeArray.get(i).isUnode)
                out += "u" + nodeArray.get(i).value + " ";
            else
                out += "v" + nodeArray.get(i).value + " ";
        }
        out += "\n";

        //Prints matrix (with node-names as rows)
        for (int i = 0; i < array.length; i++) {
            String m = "";

            if(nodeArray.get(i).isUnode)
                m += "u" + nodeArray.get(i).value + " ";
            else
                m += "v" + nodeArray.get(i).value + " ";

            for (int j = 0; j < array[i].length ; j++) {
                if(array[i][j] < 0)
                    m += " - ";
                else
                    m += " " + array[i][j] + " ";
            }
            out += m + "\n";
        }

        return out;
    }

    /**
     * Call this function after MaxFlowFulkerson()
     * Presents the result from MaxFlowFulkerson()
     */
    private String pathsToString() {
        String out = "";
        String edges = "";

        int MaximumMatching = 0;
        for(int i = 0; i < FlowGraph.length; i++) {
            for (int j = 0; j < FlowGraph[i].length; j++) {
                if(FlowGraph[i][j] == 1) {
                    if(!(nodeArray.get(i).isStartOrSink || nodeArray.get(j).isStartOrSink)) {
                        edges += "(" + nodeArray.get(i).value + ", " + nodeArray.get(j).value + ") \n";
                        MaximumMatching ++;
                    }
                }
            }
        }

        out += "\nMaximum matching is: " + MaximumMatching + "\nand the edges are the following:\n";
        out += edges;

        return out;
    }

}


