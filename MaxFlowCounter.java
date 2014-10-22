package Maxflow;

import com.sun.tools.javac.util.List;


/**
 * Created by david on 20/10/14.
 */
public class MaxFlowCounter {
    List<Node> graph;
    List<Node> residual;

    Node startNode;
    Node sinkNode;

    List<Edge> startEdges;

    int numberOfEdges;

    Node[][] incomingGraph;
    Node[][] augmentedGraph;

    public MaxFlowCounter(List<Edge> graph, Node startNode, Node sinkNode, List<Edge> startEdges) {
        this.graph = graph;
        this.startNode = startNode;
        this.sinkNode = sinkNode;
        this.startEdges = startEdges;
    }

    public int countFlow() {
        //WHILE ( depthfirst(residualGraph[startNode][sinkNode]) )   <-- spara undan noderna som gåtts igenom i en array
        //  gå igenom den arrayen med hittade noder och spara undan vilket som är det minsta flow-värdet (1)
        //
        //  FOR
        //
        //
        //
        //
        return 0;
    }

    private findPath(Edge current) {
        if(current.secondNode == sinkNode) {
            return;
        }
        if(current.flow < 0)
        {}

        for(int i = 0; i < startEdges.length(); i++) {
            if(startEdges.get(i).flow > 0)

        }


    }
}
