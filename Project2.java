import java.io.File;
import java.util.*;

public class Project2 {
	
	//Main driver
	public static void main(String args[]) {

		//DirectedGraph graph = createGraph();
		DirectedGraph graph = createGraph("CLASS_INFO.txt") //not certain if this is the correct path, should work if it is though
		depthFirstSearch(graph);
		
	}
	
	/* For team mates: 
	 * addEdge now accepts varargs! addEdge(source, destinations...) 
	 * This graph has prerequisites pointing to post requisites. Change as needed */
	//Creates and returns directed graph
	/*
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
	*/
	//new createGraph method (reads from textfile)
	static DirectedGraph createGraph(String filepath) {
		DirectedGraph graph = new DirectedGraph();
		
		File file = new File(filepath); //file that the class info is in
		Scanner scan;	//scanner to read the file
		
		//check that the file exists
		try {
			//open the file using scanner
			scan = new Scanner(file);
			
			//read through each line in the file
			while(scan.hasNextLine()) {
				//example lines of class info file
				//-------------------------------------------------
				//"crn1 - course_name1 (N/A)"
				//"crn2 - course_name1 (N/A)"
				//"crn3 - course_name3 (crn1)"
				//"crn4 - course_name4 (crn1 and crn2)"
				//"crn5 - course_name4 (crn3 and crn4)"
				//"crn6 - course_name6 (crn1, crn2 and crn5)"
				//-------------------------------------------------
				String nextLine = scan.nextLine(); //string holding entire line
				
				char[] delimiters = {'-', '(', ')'};
				int[] delimIndex =	{	nextLine.indexOf(delimiters[0]),			//index of the hyphen that follows the crn
										nextLine.indexOf(delimiters[1]),			//index of the left parenthesis that the pre-requisites are inside of 
										nextLine.indexOf(delimiters[2])		};		//index of the right parenthesis that the pre-requisites are inside of
				
				//separate the line into 3 strings
				String crn = nextLine.substring(0, delimIndex[0]).trim();							//string holding the crn of the course
				String course_name = nextLine.substring(delimIndex[0]+1, delimIndex[1]).trim();		//string holding the course name (currently unnused)
				String prereqString = nextLine.substring(delimIndex[1]+1, delimIndex[2]).trim();	//string holding all prerequisites (if none exist then it holds "N/A")
				
				//initialize an arraylist for pre-requisites
				ArrayList<String> preReqs = new ArrayList<>(); //arraylist to hold each prerequisite individually
				
				
				//check that at least one pre-requisite exists
				if (!prereqString.equalsIgnoreCase("N/A")) {
					String temp = prereqString; //temporary string that will be slowly broken apart while finding pre-reqs
					
					//find every pre-requisite crn that is followed by a comma
					while (temp.contains(",")) {
						//find comma
						int commaIndex = temp.indexOf(",");	//index location of comma
						//break string in 2
						String beforeComma = temp.substring(0, commaIndex);	// pre-req before comma
						String afterComma = temp.substring(commaIndex+1);		// string following comma
						
						//add pre-req to array
						preReqs.add(beforeComma.trim());
						//keep searching for commas
						temp = afterComma;
					}
					
					//check if there is more than 1 pre-requisite
					if (temp.contains(" and ")) {
						//break the string using " and " as a delimiter
						int andStartIndex = temp.indexOf(" and "); //find the location of " and " in the string
						
						//separate into 2 strings
						String beforeAnd = temp.substring(0, andStartIndex);	//crn before " and "
						String afterAnd = temp.substring(andStartIndex+4);		//crn after " and "
						
						//add strings to arraylist
						preReqs.add(beforeAnd.trim());
						preReqs.add(afterAnd.trim());
					
					//if there is only 1 pre-requisite
					} else {
						//add the full string to the arraylist
						preReqs.add(temp.trim());
					}
				}
				
				
				
				//vertex info has been found
				//following is graph generation
				
				//add vertex to graph
				graph.addVertex(crn);
				
				//check if preReqs exist
				if (preReqs.size()>0) {
					//for every preReq that exists
					for (String preReq : preReqs)
						//add an edge to the graph of preReq -> vertex (this will generate a vertex if the preReq doesn't exist yet)
						graph.addEdge(preReq, crn);
				}
				
			}
			
		//throw error if file not found
		} catch (Exception error) {
				error.printStackTrace();
		}
		
		//return objects
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
