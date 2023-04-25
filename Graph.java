import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//class that represents a graph
public class Graph {
    //list of vertex names (keeps track of index for source of edges in the adjacency list)
    ArrayList<String> vertex_names;
    //linked list holding all edges in graph
	LinkedList<Edge> [] adjacencylist;
	

    //constructor (receives info from text file)
	Graph(String file_path){
        File file = new File(file_path);    //file that the graph info is in
		Scanner scan;                       //scanner to read the file



        //initialize the arraylist for vertex names
        this.vertex_names = new ArrayList<>();

        try {
            //open the file using scanner
			scan = new Scanner(file);

            //read through each line in the file
			while(scan.hasNextLine()) {
                String next_line = scan.nextLine();

                if (next_line.contains(":")) {
                    int colon_index = next_line.indexOf(":");
                    this.vertex_names.add(next_line.substring(0, colon_index));
                }
            }
        //throw error if file not found
		} catch (Exception error) {
				error.printStackTrace();
		}

		

        //initialize the adjacency list
        this.adjacencylist = new LinkedList[this.vertex_names.size()];
		
        for (int i=0; i < this.adjacencylist.length; i++) {
            adjacencylist[i] = new LinkedList<>();
        }

		//check that the file exists
		try {
			//open the file using scanner
			scan = new Scanner(file);

            //itteration tracker
            int itteration = 0;
            
			//read through each line in the file
			while(scan.hasNextLine()) {
                String unread = scan.nextLine(); //string holding entire line
				
                //look for edges leading from source vertex
                while (unread.contains("(")) {
                    //find index of open parenthesis
                    int open_paren_index = unread.indexOf("(");
                    //remove part of string leading to the open parenthesis (inclusive)
                    unread = unread.substring(open_paren_index+1);

                    //find index of comma
                    int comma_index = unread.indexOf(",");
                    //destination vertex is the part of string before comma
                    String destination = unread.substring(0, comma_index);
                    unread = unread.substring(comma_index+1);

                    //find index of close parenthesis
                    int close_paran_index = unread.indexOf(")");
                    //distance to destination if the part of string after comma
                    String distance = unread.substring(0, close_paran_index); //the part before the close parenthesis is the distance to the destination vertex
                    unread = unread.substring(close_paran_index);
                    
                    addVertex(destination);
                    addEdge(vertex_names.get(itteration), destination, Double.parseDouble(distance));
                }

                itteration++;
            }

		//throw error if file not found
		} catch (Exception error) {
				error.printStackTrace();
		}
	}
	
    //attempts to add a vertex to the vertex names list (will do nothing if the vertex is already listed)
    public void addVertex(String name) {
        if (!this.vertex_names.contains(name)) {
            this.vertex_names.add(name);
        }
    }

    //adds edge to adjacency list
	public void addEdge(String source, String destination, double weight) {
		Edge edge = new Edge(source, destination, weight);
		adjacencylist[vertex_names.indexOf(source)].addLast(edge);
	}
	
    //displays graph (useful for testing)
	public void printGraph() {
		for(int i = 0; i < vertex_names.size(); i++) {
			LinkedList<Edge> list = adjacencylist[i];

			for(int j = 0; j < list.size(); j++) {
				System.out.println("vertex-" + vertex_names.get(i) + " is connected to " +
						list.get(j).destination + " with weight " + list.get(j).weight);
			}
		}
	}
    
	//Method to check if the graph is directed or undirected
    public boolean checkIfDirected() {
        boolean directed = false;

        for (String vertex1 : this.vertex_names) {
            int vertex1_index = this.vertex_names.indexOf(vertex1);
            for (Edge edge1 : this.adjacencylist[vertex1_index]) {
                int vertex2_index = this.vertex_names.indexOf(edge1.destination);
                
                boolean equivelent_edge = false;
                for (Edge edge2 : this.adjacencylist[vertex2_index]) {
                    if (edge1.isEqual(edge2)) {
                        equivelent_edge = true;
                        break;
                    }
                }

                if (!equivelent_edge) {
                    directed = true;
                    break;
                }
            }
        }

        return directed;
    }

    //nested class for edges
    static class Edge{
        String source;
		String destination;
		double weight;
	
		public Edge(String source_, String destination_, double weight) {
			this.source = source_;
			this.destination = destination_;
			this.weight = weight;
		}

        public boolean isEqual(Edge edge2) {
            boolean same = false;

            String source2 = edge2.source;
            String destination2 = edge2.destination;
            double weight2 = edge2.weight;
            
            if (this.source.contentEquals(source2) && this.destination.contentEquals(destination2) && this.weight == weight2) {
                same = true;
            }

            if (this.source.contentEquals(destination2) && this.destination.contentEquals(source2) && this.weight == weight2) {
                same = true;
            }

            return same;
        }
    }
}
