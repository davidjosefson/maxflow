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
    ArrayList<Node> xNodes;
    ArrayList<Node> yNodes;
    ArrayList<Node> nodeArray;

    public MaxFlowCounter() {
        testmetuud();
    }



    private void findPath(Edge current) {


    }
    private void testmetuud(){

        int[][] input = new int[10][2];
        /*input[0][0] = 4;
        input[0][1] = 0;

        input[1][0] = 1;
        input[1][1] = 2;

        input[2][0] = 2;
        input[2][1] = 1;

        input[3][0] = 2;
        input[3][1] = 2;

        input[4][0] = 3;
        input[4][1] = 3;*/

        input[0][0] = 9;
        input[0][1] = 0;

        input[1][0] = 1;
        input[1][1] = 2;

        input[2][0] = 1;
        input[2][1] = 3;

        input[3][0] = 3;
        input[3][1] = 1;

        input[4][0] = 3;
        input[4][1] = 4;

        input[5][0] = 4;
        input[5][1] = 3;

        input[6][0] = 5;
        input[6][1] = 3;

        input[7][0] = 5;
        input[7][1] = 4;

        input[8][0] = 6;
        input[8][1] = 6;

        //Extra
        input[9][0] = 2;
        input[9][1] = 5;

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

        System.out.println("Printa flow före sök:");
        printThatShit(FlowGraph);
        System.out.println();

        Stack<Edge> path = DFS();
        ArrayList<Stack<Edge>> maxFlow = new ArrayList<Stack<Edge>>();
        int c = 1;


        while(path != null) {
            for(Edge edge : path) {
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


                FlowGraph[u][v] = FlowGraph[u][v] + c;
                FlowGraph[v][u] = -FlowGraph[u][v];

                System.out.println("Printar flow efter varje pathfind: ");
                printThatShit(FlowGraph);
            }

            //OBS! NÄR ALLT GÅTTS IGENOM SKA MAN ANROPA EN METUUD FÖR ATT RÄKNA ALLA 1:or I FLOWGRAPH = MAXFLOW!!
            maxFlow.add(path);
            path = DFS();
        }

        printThatShit(FlowGraph);
        presentThatShit();

        Stack<Edge> actualMaxFlow = new Stack<Edge>();
        for(Stack<Edge> newpath : maxFlow) {
            newpath.pop();
            actualMaxFlow.push(newpath.pop());
        }


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

    private Stack<Edge> DFS(){
        Stack<Node> nodesToSearch = new Stack<Node>();
        Stack<Node> traveled = new Stack<Node>();

        Stack<Edge> path = new Stack<Edge>();

        nodesToSearch.push(nodeArray.get(0));

        Node node = null;

        while (!nodesToSearch.empty()){
            if(node != null && !nodesToSearch.empty()) {
                Edge edge = new Edge(node, nodesToSearch.peek());
                path.push(edge);
            }

            //Stack to search
            node = nodesToSearch.pop();

            //Path from startNode to sinkNode
            traveled.push(node);

            //If node is sinkNode, stop
            if(node == nodeArray.get(nodeArray.size() - 1)) {
                return path;
            }

            int[] capRow = CapacityGraph[nodeArray.indexOf(node)] ;
            int[] flowRow = FlowGraph[nodeArray.indexOf(node)] ;

            //Loop through the row and add all children (where there is a 1)
            for (int i = 0; i < capRow.length; i++) {
                if(capRow[i] == 1 && flowRow[i] < 1) {
                    //Check if node is not already traveled
                    if(!traveled.contains(nodeArray.get(i)) && !nodesToSearch.contains(nodeArray.get(i))) {
                        //Add node to nodesToSearch-stack
                        nodesToSearch.push(nodeArray.get(i));
                    }
                }
            }
        }

        //If no path was found return null
        return null;

    }
}
