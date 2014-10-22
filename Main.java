package Maxflow;

import com.sun.tools.javac.util.List;

public class Main {

    public static void main(String[] args) {
        List<Edge> listofedges = new List<Edge>();
        List<Node> xNodes = new List<Node>();
        List<Node> yNodes = new List<Node>();

        List<Edge> startEdges = new List<Edge>();
        List<Edge> sinkEdges = new List<Edge>();


        Node startNode = new Node(0);
        Node sinkNode = new Node(0);

        Node x1 = new Node(1);
        Node x2 = new Node(2);
        Node x3 = new Node(3);
        Node x4 = new Node(4);
        Node x5 = new Node(5);
        Node x6 = new Node(6);

        xNodes.add(x1);
        xNodes.add(x2);
        xNodes.add(x3);
        xNodes.add(x4);
        xNodes.add(x5);
        xNodes.add(x6);

        Node y1 = new Node(1);
        Node y2 = new Node(2);
        Node y3 = new Node(3);
        Node y4 = new Node(4);
        Node y5 = new Node(5);
        Node y6 = new Node(6);

        yNodes.add(y1);
        yNodes.add(y2);
        yNodes.add(y3);
        yNodes.add(y4);
        yNodes.add(y5);
        yNodes.add(y6);

        //Set edges/connections between nodes
        Edge e1 = new Edge(x1, y1 0);
        Edge e2 = new Edge(x1, y2, 0);
        Edge e3 = new Edge(x3, y2, 0);
        Edge e4 = new Edge(x4, x6, 0);
        Edge e5 = new Edge(x5, y3, 0);
        Edge e6 = new Edge(x6, y1, 0);

        //Create edges between start/sink nodes and the x/y group of nodes
        //and add these edges in the list of edges
        for(int i = 0; i < xNodes.length(); i++)
            startEdges.add(new Edge(startNode, xNodes.get(i), 0));

        for(int i = 0; i < yNodes.length(); i++)
            sinkEdges.add(new Edge(yNodes.get(i), sinkNode, 0));

        //Add the previously created edges
        listofedges.add(e1);
        listofedges.add(e2);
        listofedges.add(e3);
        listofedges.add(e4);
        listofedges.add(e5);
        listofedges.add(e6);

        MaxFlowCounter flowCounter = new MaxFlowCounter(listofedges, startNode, sinkNode, startEdges);

        int flow = flowCounter.countFlow();

    }
}
