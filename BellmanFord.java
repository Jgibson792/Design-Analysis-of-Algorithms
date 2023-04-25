import java.util.ArrayList;

public class BellmanFord {
    Graph graph;
    ArrayList<Double> distances;
    ArrayList<String> previous;

    //Constructor
    BellmanFord(Graph graph_) {
        this.graph = graph_;
        this.distances = new ArrayList<>();
        this.previous = new ArrayList<>();
    }

    //Runs the bellman ford algorithm on the graph
    public void run() {
        if (this.graph.vertex_names.size() > 0) {
            this.distances.add(0d);
            this.previous.add(null);

            for (int i=1; i<this.graph.vertex_names.size(); i++) {
                this.distances.add(Double.POSITIVE_INFINITY);
                this.previous.add(null);
            }

            for (int i=0; i<this.graph.vertex_names.size(); i++) {
                for (String source : this.graph.vertex_names) {
                    int source_index = this.graph.vertex_names.indexOf(source);
                
                    for (Graph.Edge edge : this.graph.adjacencylist[source_index]) {
                        String destination = edge.destination;
                        int destination_index = this.graph.vertex_names.indexOf(destination);
                        double weight = edge.weight;
                        
                        double current_distance = this.distances.get(destination_index);
                        double alternative_distance = this.distances.get(source_index) + weight;

                        if (alternative_distance < current_distance) {
                            this.distances.remove(destination_index);
                            this.distances.add(destination_index, alternative_distance);
                            
                            this.previous.remove(destination_index);
                            this.previous.add(destination_index, source);
                        }
                    }
                }
            }
        }
    }
    
    //Prints vertex, previous vertex, and distance for all V to the console
    public void printResults() {
        for (int i=0; i<this.previous.size(); i++) {
            String vertex = this.graph.vertex_names.get(i);
            String previous = this.previous.get(i);
            double distance = this.distances.get(i);
            
            System.out.println(vertex + "|" + previous + "|" + distance);
        }
    }
}
