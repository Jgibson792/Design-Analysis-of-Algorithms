public class project3 {
	//Main driver
    public static void main(String args[]) {

        Graph project_graph = new Graph("C:\\Users\\jgibs\\OneDrive - Texas Tech University\\2023 Spring\\Design & Analysis of Algorithms\\Project3\\Project3\\src\\project3\\projectgraph.txt");
        
        System.out.println("projectgraph is running on a" + (project_graph.checkIfDirected() ? " directed" : "n undirected") + " graph");
        System.out.println();
        
        Dijkstra dijkstra_graph = new Dijkstra(project_graph);
        
        BellmanFord bellman_graph = new BellmanFord(project_graph);
        
        dijkstra_graph.run();
        bellman_graph.run();
        
        
        System.out.println("Dijkstra's Algorithm:");
        dijkstra_graph.printResults();
        System.out.println();
        System.out.println("Bellman Ford Algorithm:");
        bellman_graph.printResults();
        System.out.println();
    }
}