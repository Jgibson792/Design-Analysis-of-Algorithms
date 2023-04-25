import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
    Graph graph;
    ArrayList<Vertex> vertices;
    
    //Constructor
    Dijkstra(Graph graph_) {
        this.graph = graph_;

        this.vertices = new ArrayList<>();
        for (String vertex : this.graph.vertex_names) {
            this.vertices.add(new Vertex(vertex, Double.POSITIVE_INFINITY, null));
        }
    }

    //Runs djikstra's algorithm on the graph
    public void run() {
        if (this.graph.vertex_names.size() > 0) {
            PriorityQueue<Vertex> q = new PriorityQueue<>(new VertexComparator());

            this.vertices.get(0).updateDistance(0);
            for (Vertex v : this.vertices) {
                q.add(v);
            }

            
            while (q.size()>0) {
                Vertex source = q.remove();
                int source_index = this.graph.vertex_names.indexOf(source.name);

                for (Graph.Edge edge : this.graph.adjacencylist[source_index]) {
                    String destination_name = edge.destination;
                    int destination_index = this.graph.vertex_names.indexOf(destination_name);
                    
                    Vertex destination = this.vertices.get(destination_index);

                    double current_distance = destination.distance;
                    double alternative_distance = source.distance + edge.weight;

                    if (alternative_distance < current_distance) {
                        q.remove(destination);
                        destination.updateDistance(alternative_distance);
                        destination.updatePrevious(source);
                        q.add(destination);
                    }
                }
            }
        }
    }


    //Prints the vertex, previous vertex and distance for all V to the console
    public void printResults() {
        for (Vertex v : this.vertices) {
            if (v.previous == null) {
                System.out.println(v.name + "|" + v.previous + "|" + v.distance);    
            } else {
                System.out.println(v.name + "|" + v.previous.name + "|" + v.distance);
            }
        }
    }

    //Vertex class holds the name, distance, and previous vertex data
    static class Vertex {
        String name;
        double distance;
        Vertex previous;
        
        Vertex(String name_, double distance_, Vertex previous_) {
            this.name = name_;
            this.distance = distance_;
            this.previous = previous_;
        }

        void updateDistance(double distance_) {
            this.distance = distance_;
        }

        void updatePrevious(Vertex previous_) {
            this.previous = previous_;
        }
    }
    
    //Compares the distances of two vertices
    class VertexComparator implements Comparator<Vertex> {
        public int compare(Vertex v1, Vertex v2) {
            if (v1.distance < v2.distance) {
                return 1;
            } else if (v1.distance > v2.distance) {
                return -1;
            }

            return 0;
        }
    }
}
