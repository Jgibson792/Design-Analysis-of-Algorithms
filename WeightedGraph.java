package project3;

import java.util.*;

public class WeightedGraph {
	
	static class Edge {
		int source;
		int destination;
		int weight;
	
		public Edge(int source, int destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
	}
	
	static class Graph {
		int vertices;
		LinkedList<Edge> [] adjacencylist;
		
		Graph(int vertices){
			this.vertices = vertices;
			adjacencylist = new LinkedList[vertices];
			
			for (int i=0; i < vertices; i++) {
				adjacencylist[i] = new LinkedList<>();
			}
		}
		
		public void addEdge(int source, int destination, int weight) {
			Edge edge = new Edge(source, destination, weight);
			adjacencylist[source].addFirst(edge);
		}
		
		public void printGraph() {
			for(int i = 0; i < vertices; i++) {
				LinkedList<Edge> list = adjacencylist[i];
				for(int j = 0; j < list.size(); j++) {
					System.out.println("vertex-" + lookup(i) + " is connected to " +
							lookup(list.get(j).destination) + " with weight " + list.get(j).weight);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int vertices = 3;
		Graph graph = new Graph(vertices);
		graph.addEdge(0, 2, 4);
		graph.addEdge(0, 1, 2);
		graph.addEdge(1, 1, 2);
		graph.addEdge(1, 2, 5);
		graph.addEdge(2, 1, 4);
		graph.printGraph();
	}
	
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
}
