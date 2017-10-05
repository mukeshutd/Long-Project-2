
// change following line to your group number
package cs6301.g40;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;

public class Euler extends GraphAlgorithm{
	Graph graph;
    int VERBOSE;
  //  Euler.EulerEdge [][] EdgeArray;
    Graph.Vertex start;
    ArrayList<Graph.Edge> finalTour;
    boolean [] explore;
//	ArrayList<List<Graph.Edge>> Tourlist ; //= new java.util.ArrayList<>();
//	Map<Graph.Vertex, List<Graph.Edge>> mapper = new HashMap<>();
	
    // Constructor
	
    Euler(Graph g, Graph.Vertex start) {
    super(g);
    this.start = start;
	VERBOSE = 1;
//	tour = new LinkedList<>();
//	Tourlist = new java.util.ArrayList<>();
	explore = new boolean[g.size()];
	this.graph = g;
//	this.EdgeArray = new Euler.EulerEdge[g.size()][g.size()];
	node = new Euler.EulerVertex[g.size()];
	finalTour = new ArrayList<>();
	
	
    }
    
    static class EulerVertex{
    int limit;
    boolean seenTour;
  //  TreeMap<Graph.Vertex, Boolean> Edgeseen;
    ArrayList<Graph.Edge> tour;
    boolean TourStarter;
     EulerVertex(int limit){
     	this.limit = limit;
     	this.seenTour=false;
     	this.TourStarter=false;
     	ArrayList tourFinal=
     	tour = new ArrayList<>();
//		 Edgeseen = new TreeMap<>();
     	
		}
	}
	
  /* static class EulerEdge extends Graph.Edge{
    	boolean seen;
    	EulerEdge(boolean seen){
    		
    		this.seen = false;
		}
	}*/

    // To do: function to find an Euler tour
	
    public ArrayList<Graph.Edge> findEulerTour() {
    initializeNode(g);
	findTours();
	if(VERBOSE > 9) { printTours(); }
	stitchTours();
	return finalTour;
    }
	
	
	
/*	public ArrayList<List<Graph.Edge>>  findEulerTourList() {
		initializeNode(g);
		findTours();
		if(VERBOSE > 9) { printTours(); }
//	stitchTours();
//	return tour;
		return this.Tourlist;
	}*/
    
    /* To do: test if the graph is Eulerian.
     * If the graph is not Eulerian, it prints the message:
     * "Graph is not Eulerian" and one reason why, such as
     * "inDegree = 5, outDegree = 3 at Vertex 37" or
     * "Graph is not strongly connected"
     */
    boolean isEulerian() {
    	
			for (Graph.Vertex v:this.graph
				 ) {
				if(v.adj.size()!=v.revAdj.size()) {
					System.out.println("Graph is not Eulerian");
					System.out.print("inDegree = "+v.revAdj.size()+ ", outDegree = "+v.adj.size()+ " at Vertex "+ (v.getName()+1));
					return false;
				}
			}
	
		StronglyConnected sc = new StronglyConnected();
		boolean checkStronglyConnected = sc.checkStronglyConnected(this.graph);
		if(!checkStronglyConnected){
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
			return false;
		}
	return true;
    }
    //Initialize EdgeArray array which contains instances of EulerEdge
	public void initializeNode(Graph g){
    	//int k=0;
		for (Graph.Vertex v:g
			 ) {
			this.node[v.getName()] = new EulerVertex(v.adj.size());
			for (Graph.Edge e:v.adj
				 ) {
		//	this.EdgeArray[e.from.getName()][e.to.getName()] = new Euler.EulerEdge(false);
				//((cs6301.g40.Euler.EulerVertex) this.node[v.getName()]).Edgeseen.put(e.otherEnd(v),false);
			}
			
		}
		
	}
	
    //find the number of edges in graph
	static int edgesCount(Graph g){
    	int count = 0;
		for (Graph.Vertex v :g
			 ) {
			count += v.adj.size();
		}
		return count;
	}

    // Find tours starting at vertices with unexplored edges
    void findTours() {
		ArrayList<Graph.Edge> Anytour = new ArrayList<>();
		
		findTour(this.start,Anytour);
	//	this.start.tour.add(Anytour);
	//	((cs6301.g40.Euler.EulerVertex) node[start.getName()]).tour.add(Anytour);
		((cs6301.g40.Euler.EulerVertex) node[start.getName()]).tour = Anytour;
		//mapper.put(this.start,Anytour);
		this.explore[this.start.getName()]=false;
		((Euler.EulerVertex) node[this.start.getName()]).TourStarter = true;
		for (Graph.Vertex u: this.graph
			 ) {
			if(((Euler.EulerVertex)node[u.getName()]).limit>0) {
				for (Graph.Edge e : u.adj
						) {
				//	if (!this.EdgeArray[u.getName()][e.to.getName()].seen)
				//	if(!((Euler.EulerVertex)node[u.getName()]).Edgeseen.get(e.otherEnd(u)))
					if(!e.seen)
					{
						ArrayList<Graph.Edge> Anytour1 = new ArrayList<>();
						findTour(u, Anytour1);
					//	this.Tourlist.add(Anytour1);
						((cs6301.g40.Euler.EulerVertex) node[u.getName()]).tour = Anytour1;
					//	mapper.put(u,Anytour1);
						this.explore[u.getName()]=false;
						((Euler.EulerVertex) node[u.getName()]).TourStarter = true;
						
					}
				}
			}
		}
	//	while
    }

    
    //
	void findTour(Graph.Vertex start,ArrayList<Graph.Edge> tour1){
		for (Graph.Edge e: start.adj
			 ) {
		//	if (!this.EdgeArray[start.getName()][e.to.getName()].seen)
		//	if(!((Euler.EulerVertex)node[start.getName()]).Edgeseen.get(e.otherEnd(start)))
			if(!e.seen)
			{
				//this.EdgeArray[start.getName()][e.to.getName()].seen = true;
			//	((Euler.EulerVertex)node[start.getName()]).Edgeseen.put(e.otherEnd(start),true);
				e.seen =true;
				tour1.add(e);
				((Euler.EulerVertex) node[start.getName()]).limit -= 1;
		//		temp = e.to;
				findTour(e.to,tour1);
				return;
			}
		
		}
		
	}
    /* Print tours found by findTours() using following format:
     * Start vertex of tour: list of edges with no separators
     * Example: lp2-in1.txt, with start vertex 3, following tours may be found.
     * 3: (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3)
     * 4: (4,7)(7,8)(8,4)
     * 5: (5,7)(7,9)(9,5)
     *
     * Just use System.out.print(u) and System.out.print(e)
     */
    void printTours() {
    }

    // Stitch tours into a single tour using the algorithm discussed in class
    void stitchTours() {
		
    	//this.explore[this.start.getName()]=true;
    	explore(this.start);
    	
    	
    }
    
    void explore(Graph.Vertex start){
    	Graph.Vertex tmp = start;
		//List<Graph.Edge> tempTour = mapper.get(start);
		this.explore[tmp.getName()]=true;
		for (Graph.Edge e: ((Euler.EulerVertex) node[tmp.getName()]).tour
			 ) {
			this.finalTour.add(e);
			tmp = e.otherEnd(tmp);
			if(((Euler.EulerVertex) node[tmp.getName()]).TourStarter && !this.explore[tmp.getName()]){
				explore(tmp);
			}
			
		}
		
		
	}

    void setVerbose(int v) {
	VERBOSE = v;
    }
	
	public static void main(String []  args) throws java.io.FileNotFoundException{
		
		int evens = 0;
		java.util.Scanner in;
		if (args.length > 0) {
			java.io.File inputFile = new java.io.File(args[0]);
			in = new java.util.Scanner(inputFile);
		} else {
			in = new java.util.Scanner(System.in);
		}
		Graph g = Graph.readGraph(in,true);
		List<Graph.Edge> tr = new LinkedList<>();
		Euler eu = new Euler(g,g.getVertex(1));
		eu.isEulerian();
		tr = eu.findEulerTour();
		
	/*	ArrayList<List<Graph.Edge>> tourlist = new java.util.ArrayList<>();
		tourlist = eu.findEulerTourList(); */
		
		for (Graph.Edge e:tr
			 ) {
		//	System.out.print("("+e.from+","+e.to+ "); "+ "->");
			System.out.print(e.from+" ->");
		}
		
		/*for (List<Graph.Edge> list:tourlist
			 ) {
			for (Graph.Edge e:list
				 ) {
				System.out.print("("+e.from+","+e.to+"); ");
			}
			System.out.println();
		}*/
		
	}
}
