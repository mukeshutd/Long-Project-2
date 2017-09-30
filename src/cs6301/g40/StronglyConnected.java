package cs6301.g40;

/**
 * Created by mukku on 9/24/2017.
 */
import java.util.List;
import java.util.LinkedList;
public class StronglyConnected {
	
	public boolean checkStronglyConnected(Graph g){
		TopologicalOrder topOrder = new TopologicalOrder(g);
		List<Graph.Vertex> slist = new LinkedList<>();
		slist = topOrder.toplogicalOrder2(g);
		slist = topOrder.toplogicalOrder3(g,slist);
	
		
		for (Graph.Vertex v: slist
			 ) {
		//	System.out.print(v.getName()+1 + "->");
		}
		System.out.println();
		for (Object tp: topOrder.node
			 ) {
			if(((TopologicalOrder.TopVertex) tp) .cno >1)
				return false;
			
		}
		
		return true;
		
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
				
		StronglyConnected sc = new StronglyConnected();
		boolean check = sc.checkStronglyConnected(g);
		System.out.println(check);

		
	}
}
