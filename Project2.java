import java.util.*;

public class Project2 {
	
	//Main driver
	public static void main(String args[]) {

		DirectedGraph graph = createGraph();
		depthFirstSearch(graph);
		
	}
	
	/* For team mates: 
	 * addEdge now accepts varargs! addEdge(source, destinations...) 
	 * This graph has prerequisites pointing to post requisites. Change as needed */
	//Creates and returns directed graph
	static DirectedGraph createGraph() {
		DirectedGraph graph = new DirectedGraph();

		graph.addVertex("CS 1411"); 
		graph.addVertex("MATH 1451");
		graph.addVertex("ENGL 1301");
		graph.addVertex("CS 1412"); 	graph.addEdge("CS 1411", "CS 1412");
		graph.addVertex("MATH 1452"); 	graph.addEdge("MATH 1451", "MATH 1452");
		graph.addVertex("PHYS 1408"); 	graph.addEdge("MATH 1451", "PHYS 1408");
		graph.addVertex("ENGL 1302");	graph.addEdge("ENGL 1301", "ENGL 1302");
		graph.addVertex("CS 2413"); 	graph.addEdge("CS 1412", "CS 2413");
		graph.addVertex("CS 1382");	graph.addEdge("CS 1412", "CS 1382");
		graph.addVertex("ECE 2372");	graph.addEdge("MATH 1451", "ECE 2372");
		graph.addVertex("MATH 2450"); 	graph.addEdge("MATH 1452", "MATH 2450");
		graph.addVertex("PHYS 2401"); 	graph.addEdge("PHYS 1408", "PHYS 2401");
		graph.addVertex("CS 2350");	graph.addEdge("CS 1412", "CS 2350"); graph.addEdge("ECE 2372", "CS 2350");
		graph.addVertex("CS 2365");	graph.addEdge("CS 2413", "CS 2365");
		graph.addVertex("ENGR 2392");
		graph.addVertex("POLS 1301");
		graph.addVertex("MATH 2360");	graph.addEdge("MATH 1452", "MATH 2360");
		graph.addVertex("ENGL 2311");	graph.addEdge("ENGL 1301", "ENGL 2311"); graph.addEdge("ENGL 1302", "ENGL 2311");
		graph.addVertex("CS 3361");	graph.addEdge("CS 2413", "CS 3361");
		graph.addVertex("CS 3364");	graph.addEdge("CS 2413", "CS 3364"); graph.addEdge("CS 1382", "CS 3364"); graph.addEdge("MATH 2360", "CS 3364");
		graph.addVertex("MATH 3342");	graph.addEdge("MATH 2450", "MATH 3342");
		graph.addVertex("POLS 2306");
		graph.addVertex("CS 3365");	graph.addEdge("CS 2365", "CS 3365"); graph.addEdge("CS 2413", "CS 3365"); graph.addEdge("MATH 3342", "MATH 3365");
		graph.addVertex("CS 3375");	graph.addEdge("CS 2350", "CS 3375");
		graph.addVertex("CS 3383");	graph.addEdge("CS 1382", "CS 3383");
		graph.addVertex("CS 4365");	graph.addEdge("CS 1382", "CS 4365");
		graph.addVertex("CS 4352");	graph.addEdge("CS 3364", "CS 4352"); graph.addEdge("CS 3375", "CS 4352");
		graph.addVertex("CS 4354");	graph.addEdge("CS 3364", "CS 4352"); graph.addEdge("CS 3375", "CS 4352");
		graph.addVertex("CS 4366");	graph.addEdge("CS 4365", "CS 4366");
		
		return graph;
	}
	
	//depthFirstSearch() creates an iterator with all vertices, iterates through it until all vertices are visited
	static void depthFirstSearch(DirectedGraph graph) {
		Iterator<DirectedGraph.Vertex> itr = graph.adjacent.keySet().iterator();				//creates iterator for all vertices
		Set<DirectedGraph.Vertex> visited = new LinkedHashSet<DirectedGraph.Vertex>();				//creates visited = false for all vertices
		while(itr.hasNext()) {											//for all vertices
			if(!visited.contains(itr.next()))								//if not visited
				explore(graph, itr.next(), visited);							//explore
		}
	}
	
	//explore() explores all vertices adjacent to a vertex (recursively)
	static void explore(DirectedGraph graph, DirectedGraph.Vertex root, Set<DirectedGraph.Vertex> visited) {
		if(!visited.contains(root) && graph.getAdjVertices(root.label)!= null)  { 				//if not visited
			visited.add(root); 										//visited = true
			if(graph.getAdjVertices(root.label)!= null) {							//if there are adjacent vertices
				for (int i = 0; i<graph.getAdjVertices(root.label).size(); i++) {			//visit all vertices in the adjacency list
					explore(graph, graph.getAdjVertices(root.label).get(i), visited);		//recurs until all vertices have been visited
				}
			}
		}
	}
}


class DirectedGraph {
	 
	//Declares a map interface named adjacent
	public Map<Vertex, List<Vertex>> adjacent;

    //Constructor, initializes adjacent as a hash map object
    DirectedGraph() {
        this.adjacent = new HashMap<Vertex, List<Vertex>>();			//vertex, adjacency list
    }

    void addVertex(String label) {
        adjacent.putIfAbsent(new Vertex(label), new ArrayList<>()); 		//Creates a key(Vertex) and maps it to an ArrayList(Adjacent vertices)
    }

    //addEdge(String source, String... destination) adds any number of destinations to the source's adjacent vertices
    void addEdge(String source, String... destination) {
        Vertex sourceVertex = new Vertex(source);
        for(int i=0;i<destination.length;i++) {
        	Vertex destinationVertex = new Vertex(destination[i]);
        	adjacent.get(sourceVertex).add(destinationVertex);
        }
    }

    //getAdjacent(String label) takes a String(Vertex label) as an input and returns the list of adjacent vertices
    List<Vertex> getAdjVertices(String label) {
        return adjacent.get(new Vertex(label));
    }
    
    //printGraph() prints a complete adjacency list to the console
    void printGraph() {
        StringBuffer sb = new StringBuffer();
        System.out.println("Prereq \t\t\t For");
        for(Vertex v : adjacent.keySet()) {
            sb.append(v);
            System.out.print(sb);
            System.out.print(" -> \t\t");
            int length = sb.length();
            sb.delete(0, length);
            sb.append(adjacent.get(v));
            System.out.println(sb);
            length = sb.length();
            sb.delete(0, length);
        }
    }
    
    //Nested in DirectedGraph
    class Vertex {
        String label;
        
        //Constructor
        Vertex(String label) {
            this.label = label;
        }
        
        //hashCode() returns a unique hash code for this Vertex instance
        @Override
        public int hashCode() {
            int result = 1;
            result = 31 * result + getOuterType().hashCode();
            result = 31 * result + ((label == null) ? 0 : label.hashCode());
            return result;
        }
        
        //equals(Object obj) takes an object to see if it is equal to this Vertex instance
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            if (!getOuterType().equals(((Vertex) obj).getOuterType()))
                return false;
            if (label == null) {
                if (((Vertex) obj).label != null)
                    return false;
            } else if (!label.equals(((Vertex) obj).label))
                return false;
            return true;
        }
        
        //toString() returns the label of the Vertex instance when called
        @Override
        public String toString() {
            return label;
        }

        
        //getOuterType() returns the enclosing instance of DirectedGraph
        private DirectedGraph getOuterType() {
            return DirectedGraph.this;
        }
    }
}
