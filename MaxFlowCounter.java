package Maxflow;

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

        int[][] input = new int[5][2];
        input[0][0] = 4;
        input[0][1] = 0;

        input[1][0] = 1;
        input[1][1] = 1;

        input[2][0] = 2;
        input[2][1] = 2;

        input[3][0] = 2;
        input[3][1] = 3;

        input[4][0] = 3;
        input[4][1] = 2;

        //A list of all nodes in one axis, startnode, xnodes, ynodes and sinknode
        nodeArray = new ArrayList<Node>();

        //Add a startnode
        nodeArray.add(new Node(-1, false));

        //Fill the list with xnodes and ynodes
        for(int[] i : input){
            if (i == input[0])
                continue;

            boolean xIsFound = false;
            boolean yIsFound = false;
            for (Node node: nodeArray){

                if((node.value == i[0] && node.isXnode == true)) {
                    xIsFound = true;
                }
                if((node.value == i[1] && node.isXnode == false)) {
                    yIsFound = true;
                }

                if(xIsFound && yIsFound)
                    break;
            }

            if(!xIsFound)
                nodeArray.add(new Node(i[0], true));
            if(!yIsFound)
                nodeArray.add(new Node(i[1], false));

        }

        //Add a sinknode to the list of nodes
        nodeArray.add(new Node(-1, false));



        //The matrix of capacity for each edge, is symmetrical and each side is the size of nodeArray
        CapacityGraph = new int[nodeArray.size()][nodeArray.size()];

        //Loop through the input-list and set the corresponding edges to 1 in the matrix
        for (int i1 = 0, inputLength = input.length; i1 < inputLength; i1++) {
            int[] i = input[i1];
            if (i == input[0])
                continue;

            int xIndex = -4;

            //Hitta index där x-noden är i nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == i[0] && nodeArray.get(j).isXnode ) {
                    xIndex = j;
                    break;
                }
            }

            int yIndex = -4;
            //Hitta index där y-noden är i nodeArray
            for (int j = 0; j < nodeArray.size(); j++) {
                if(nodeArray.get(j).value == i[0] && !nodeArray.get(j).isXnode ) {
                    yIndex = j;
                    break;
                }
            }


            CapacityGraph[yIndex][xIndex] = 1;
            CapacityGraph[xIndex][yIndex] = 1;


            CapacityGraph[0][xIndex] = 1;
            CapacityGraph[yIndex][0] = 1;


        }

        printThatShit();

        //KANSKE ONÖDIG, gör en riktig copy, inte denna clone.
        /*
        FlowGraph = CapacityGraph.clone();
        for (int i = 0; i < FlowGraph.length; i++) {
            for (int j = 0; j < FlowGraph[i].length ; j++) {
                //Sätter alla värden i grafen till 0
                FlowGraph[i][j] = 0;
            }
        }
        */

        BFS();

    }

    private void printThatShit() {

        for (int i = 0; i < CapacityGraph.length; i++) {
            String m = "";
            for (int j = 0; j < CapacityGraph[i].length ; j++) {
                m += CapacityGraph[i][j] + " ";
            }
            System.out.println(m);
        }
    }

    private void BFS(){
        Stack<Node> stack = new Stack();
        stack.push( nodeArray.get(0) );

        while (!stack.empty()){
            Node node = stack.pop();
            node.traveled = true;

            int[] i = CapacityGraph[nodeArray.indexOf(node)] ;

            for( int i1  : i ){


            }

        }


    }
}
