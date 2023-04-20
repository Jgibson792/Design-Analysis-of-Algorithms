package project3;

import java.util.*;

public class WeightedGraph {
	
	static class Edge {
		String source;
		String destination;
		int weight;
		
		public Edge(String source_, String destination_, int weight_) {
			this.source = source_;
			this.destination = destination_;
			this.weight = weight_;
		}
	}
	
	static class Graph {
		ArrayList<String> vertex_names;
		LinkedList<Edge> [] adjacencylist;
		
		//constructor (recieves info from text file)
		Graph(String file_path){
			File file = new File(file_path);	//file that the graph info is in
			Scanner scan;				//scanner to read the file
			
			
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
						addEdge(vertex_names.get(itteration), destination, Integer.parseInt(distance));
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
		
		public void addEdge(String source, String destination, int weight) {
			Edge edge = new Edge(source, destination, weight);
			adjacencylist[vertex_names.indexof(source)].addLast(edge);
		}
		
		public void printGraph() {
			for(int i = 0; i < vertex_names.size(); i++) {
				LinkedList<Edge> list = adjacencylist[i];
				for(int j = 0; j < list.size(); j++) {
					System.out.println("vertex-" + vertex_names.get(i) + " is connected to " +
							list.get(j).destination + " with weight " + list.get(j).weight);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		/*
		int vertices = 3;
		Graph graph = new Graph(vertices);
		graph.addEdge(0, 2, 4);
		graph.addEdge(0, 1, 2);
		graph.addEdge(1, 1, 2);
		graph.addEdge(1, 2, 5);
		graph.addEdge(2, 1, 4);
		graph.printGraph();
		*/
		
		Graph dijkstra_1 = new Graph("DIJKSTRA_GRAPH_1.txt");
		Graph dijkstra_2 = new Graph("DIJKSTRA_GRAPH_2.txt");
		Graph bellman_ford_1 = new Graph("BELLMAN_FORD_GRAPH_1.txt");
		
		dijkstra_1.printGraph();
		System.out.println();
		dijkstra_2.printGraph();
		System.out.println();
		bellman_ford_1.printGraph();
		System.out.println();
		
	}
	
	/*
	public static String lookup(int vertex) {
		switch(vertex) {
		case 0:
				return "A";
		case 1:
				return "B";
		case 2:
				return "C";
		default:
				return null;
		}
	}
	*/
}
