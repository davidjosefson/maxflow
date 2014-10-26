/*
TODO: Göra hela grejen till en static som inte behöver instansieras?
    TODO: Skicka in en input-2d-array och få ut FlowGraph?
    TODO: Ha två public print-metoder som man kan anropa där man skickar in den färdiga flowgraphen för att skriva ut resultatet?
TODO: Dela upp i fler metoder, inte bara en som körs rakt upp och ner
TODO: Optimera koden? Städa?
TODO: Kommentarer, gah..

*/

package Maxflow;

import com.sun.tools.javac.comp.Flow;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Created by david on 20/10/14.
 */
public class MaxFlowCounter {


    int[][] CapacityGraph;
    int[][] FlowGraph;
    ArrayList<Node> nodeArray;

    Stack<Node> traveledStack;
    Stack<Edge> pathStack;

    public MaxFlowCounter(int[][] input) {
        traveledStack = new Stack<Node>();
        pathStack = new Stack<Edge>();
        testmetuud(input);
    }

    private void testmetuud(int[][] input){
        //A list of all nodes in one axis, startnode, xnodes, ynodes and sinknode
        nodeArray = new ArrayList<Node>();

        //Add a startnode
        nodeArray.add(new Node(7, false, true));

        //Fill the list with xnodes and ynodes
        for(int[] i : input){
            if (i == input[0])
                continue;

            boolean xIsFound = false;
            boolean yIsFound = false;
            for (Node node: nodeArray){

                if((node.value == i[0] && node.isXnode)) {
                    xIsFound = true;
                }
                if((node.value == i[1] && !node.isXnode)) {
                    yIsFound = true;
                }

                if(xIsFound && yIsFound)
                    break;
            }

            if(!xIsFound)
                nodeArray.add(new Node(i[0], true, false));
            if(!yIsFound)
                nodeArray.add(new Node(i[1], false, false));

        }

        //Add a sinknode to the list of nodes
        nodeArray.add(new Node(9, true, true));

        //The matrix of capacity for each edge, is symmetrical and each side is the size of nodeArray
        CapacityGraph = new int[nodeArray.size()][nodeArray.size()];

        //Create a flow path where every edge has 0 flow
        FlowGraph = new int[nodeArray.size()][nodeArray.size()];

        //Loop through the input-list and set the corresponding edges to 1 in the matrix
        for (int i = 0; i < input.length; i++) {
            int[] inputRow = input[i];

            if (inputRow == input[0])
                continue;

            int xIndex = -4;

            //Hitta index där x-noden är i nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == inputRow[0] && nodeArray.get(j).isXnode ) {
                    xIndex = j;
                    break;
                }
            }

            int yIndex = -4;
            //Hitta index där y-noden är i nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == inputRow[1] && !nodeArray.get(j).isXnode ) {
                    yIndex = j;
                    break;
                }
            }


            CapacityGraph[yIndex][xIndex] = 1;
            CapacityGraph[xIndex][yIndex] = 1;

            //Set startNode to x-node edge to 1 on both y-axis and x-axis
            CapacityGraph[0][xIndex] = 1;
            CapacityGraph[xIndex][0] = 1;

            //Set sinkNode to Y-node edge to 1 on both y-axis and x-axis
            CapacityGraph[CapacityGraph.length - 1][yIndex] = 1;
            CapacityGraph[yIndex][CapacityGraph.length - 1] = 1;

        }
        System.out.println("Printar capacity");
        printThatShit(CapacityGraph);

        System.out.println();

        int c = 1;

        //DFS returns null if no path is found
        while(DFS(nodeArray.get(0)) != null) {
            for(Edge edge : pathStack) {
                int u = -4;

                //Hitta index där x-noden är i nodeArray
                for (int j = 0; j < nodeArray.size(); j++) {
                    if(nodeArray.get(j) == edge.x) {
                        u = j;
                        break;
                    }
                }

                int v = -4;
                //Hitta index där y-noden är i nodeArray
                for (int j = 0; j < nodeArray.size(); j++) {
                    if(nodeArray.get(j) == edge.y) {
                        v = j;
                        break;
                    }
                }

                //Set flow
                FlowGraph[u][v] = FlowGraph[u][v] + c;
                FlowGraph[v][u] = -FlowGraph[u][v];

                System.out.println("Printar flow efter varje pathfind: ");
                printThatShit(FlowGraph);
            }

            //maxFlow.add(pathStack);
            pathStack = new Stack<Edge>();
            traveledStack = new Stack<Node>();
        }

        printThatShit(FlowGraph);
        presentThatShit();
    }

    //Recursive DFS
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
            //If the sinknode is a child of this node, a path has been found
            if(nodesToSearch.peek() == nodeArray.get(nodeArray.size() -1)) {
                //save the edge from this node to sinknode
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

    private void printThatShit(int[][] array) {
        String out = "   ";
        for(int i = 0; i < nodeArray.size(); i++) {
            if(nodeArray.get(i).isXnode)
                out += "x" + nodeArray.get(i).value + " ";
            else
                out += "y" + nodeArray.get(i).value + " ";
        }
        System.out.println(out);
        for (int i = 0; i < array.length; i++) {
            String m = "";

            if(nodeArray.get(i).isXnode)
                m += "x" + nodeArray.get(i).value + " ";
            else
                m += "y" + nodeArray.get(i).value + " ";

            for (int j = 0; j < array[i].length ; j++) {
                if(array[i][j] < 0)
                    m += " - ";
                else
                    m += " " + array[i][j] + " ";
            }
            System.out.println(m);
        }
    }

    private void presentThatShit() {
        String out = "";
        for(int i = 0; i < FlowGraph.length; i++) {
            for (int j = 0; j < FlowGraph[i].length; j++) {
                if(FlowGraph[i][j] == 1) {
                    if(!(nodeArray.get(i).value == 7 || nodeArray.get(i).value == 9 || nodeArray.get(j).value == 7 || nodeArray.get(j).value == 9))
                        out += "(" + nodeArray.get(i).value + ", " + nodeArray.get(j).value + ") \n";
                }
            }
        }

        System.out.println(out);
    }
}


