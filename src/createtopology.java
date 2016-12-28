/*****************************************************************************************
*Class Name: CreateTopology
*
*Dependencies: Graph.java Topology.java
*
*			
*
*Description: Generic implementation of Djikstra Algorithm to find the shortest path from source to destination.
*			  The priority queue has been used in order to find the minimum of the unsettled sets.
*			We have taken from user the input and printed the shortest path from source to destination.
*			The connection table of the source router has been created. After taking destination router as input, w
*			the shortest path has been calculated. when router has been put down, new graph has been calculated.
*			Input is in the form of no of vertex in the graph and adjacency matrix.
*
*			Test case :
*			
*			0 4 -1 2 -1
*			4 0 8 -1 5
*			-1 8 0 3 -1
*			2 -1 3 0 4
*			-1 5 -1 4 0	
*			
*
*Return code:a) Connection table of the source router
*			 b) Connection table of the destination router 
*			 c) Shortest path from source to destination using Dijkstra algorithm.
*			 d) Deletion of the router
*			 e) Best router in the graph
*
*Author: Suraj Kumar Didwania <dsuraj@hawk.iit.edu> Seq No: 22
*
*************************************************************************************************/

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class createtopology extends JFrame
{ 	int size=0;
	JFrame jf1;
	Graph graph;
	int[] shortestDistance;
	int[]  totalcost;
	int source,destination;
	String input;
	int k;
	Object[][] matrix = null;
	String modify;
	Object[][] pathmatrix;
	
	//Constructor for the creation of topology called in Topology.java. Input given by user in .txt file
	public createtopology()
	{
		try{
		input = JOptionPane.showInputDialog(null,"Input original network topology matrix data file:");
		 if( input.isEmpty()){
			 JOptionPane.showMessageDialog(null, "Please input the original network topology matrix data file correctly","Error Message",JOptionPane.ERROR_MESSAGE);
			 new createtopology();  //If the output is given wrong
			 return;
		 }
	        modify = null;
	        try {
				takinginputs(input,modify);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Input the correct network topology matrix data file:","Error", JOptionPane.ERROR_MESSAGE);
			new createtopology();
			return;
		}
	}
	
	// It takes the input file and convert into matrix and creates the graph using graph.java and prints the graph
	protected void takinginputs(String text, String modify) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		BufferedReader br1 = null;
		   int len=0;	   
		   int row = 0;
		   String line;
		try {
			File f=new File(text);
             br = new BufferedReader(new FileReader(f.getAbsolutePath()));
             br1 = new BufferedReader(new FileReader(f.getAbsolutePath()));
            while((line = br.readLine())!= null) 
            {
            	String[] cols = line.split(" ");
            	if(matrix == null)
            	{
            		size = cols.length;
            		matrix = new Object[size][size];
            	}
				for(int i=0;i<size;i++)
				{
					matrix[row][i] = Integer.parseInt(cols[i]);
				}
				row++;
            }
            if(modify!=null)
            {
            	System.out.println(modify);
            	for(int column = 0;column<size;column++)
            	{
            	matrix[Integer.parseInt(modify)][column] = -1;
            	matrix[column][Integer.parseInt(modify)] = -1;
            	}
            }
             graph = new Graph(size);
            for(int i=0;i<size;i++)
            {	
            	for(int j=0;j<size;j++)
            	{
            		if((int) matrix[i][j] >0)
        		graph.addEdge(i, j, (int) matrix[i][j]);
            		else continue;
            	}
            }
            System.out.println(" ");
            graph.printGraph();
            }catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}
	//Output has been displayed as the table in Swing showing all the routers
	public void showingoutput() {	
		JFrame jf2 = new JFrame();
		setTitle("Original Topology Matrix");
		JLabel input = new JLabel("Review original topology matrix:");
    	add(input,BorderLayout.NORTH);
    	String[] column = new String[size];
    	for(int i=0;i<size;i++)
    	{	
    		StringBuilder value = new StringBuilder();
    		value.append("R");
    		value.append(i+1);
    		column[i] = value.toString();
    	}
			JTable table  = new JTable(matrix,column);
			this.add(new JScrollPane(table));
	    	input.setBounds(50, 100, 400, 30);
			setLocationRelativeTo(null);
		    setLocation(100, 100);
		    setSize(size*100,size*100);
		    setVisible(true);
		    setAlwaysOnTop(true);
		    setResizable(true);
	}
	// Connection Table through the source router and calling Dijkstra Algorithm
	public void ConnectionTable() throws IOException
	{
		try{
		source = Integer.parseInt(JOptionPane.showInputDialog(null,"Select a Source Router"));
		 source = source-1;
		 if(!(source>=0 && source <size) || Integer.toString(source).isEmpty() || !Integer.toString(source).matches("[0-9]*")){
			 JOptionPane.showMessageDialog(null, "Please input the Source correctly","Error Message",JOptionPane.ERROR_MESSAGE);
			 ConnectionTable();  //If the output is given wrong
			 return;
		 }
    	    	dijkstraShortestDistance(graph,source);
    	    	JFrame jf3 = new JFrame();
    			jf3.setTitle("Connection Table of Router" + (source+1));
    			pathmatrix= new Object[size][2];
    			//JLabel input = new JLabel("Router " +(source+1) + " Connection Table");
    			//jf3.add(input);
    			String[] column = new String[]{"Destination","Interface"};
    	    	k=0;
    	    	for(int i=0;i<size;i++)
    	    	pathmatrix[i][0] = i+1;
    	    	for(int i=0;i<size;i++)
    	    	{
    	    		GraphNode dest = graph.getindex(i);
    	    	List<GraphNode> path = shorterpath(dest);
    	    	if(k == source)
    	    			pathmatrix[k][1] = '-';
    	    		else if(path.size()>1)
    	    		{
    				pathmatrix[k][1] = path.get(1);
    				pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;
    	    		}
    	    		else
    	    		{
    				pathmatrix[k][1] = path.get(0);
    				pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;
    	    		}
    				k++;
    	    	}
    	    	JTable table  = new JTable(pathmatrix,column);
    	    	jf3.add(new JScrollPane(table));
    			jf3.setLayout(new CardLayout());
    	        jf3.setLocationRelativeTo(null);
    		    jf3.setLocation(100, 100);
    		    jf3.setSize(800,800);
    		    jf3.setVisible(true);
    		    jf3.setAlwaysOnTop(true);
    		    //setResizable(true);
    		    jf3.show();
		} catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Please input the Source correctly","Error Message",JOptionPane.ERROR_MESSAGE);
			 ConnectionTable();  //If the output is given wrong
			 return;
		}
	}
	
	// Connection Table through the source router and calling Dijkstra Algorithm if the source is already given
	public void ConnectionTable(int source)
	{
    	    	dijkstraShortestDistance(graph,source);
    	    	JFrame jf5 = new JFrame();
    			jf5.setTitle("Connection Table");
    			pathmatrix= new Object[size][2];
    			JLabel input = new JLabel("Router " +(source+1) + " Connection Table");
    			jf5.add(input);
    			String[] column = new String[]{"Destination","Interface"};
    	    	k=0;
    	    	for(int i=0;i<size;i++)
    	    	pathmatrix[i][0] = i+1;
    	    	for(int i=0;i<size;i++)
    	    	{
    	    		GraphNode dest = graph.getindex(i);
    	    	List<GraphNode> path = shorterpath(dest);
    	    	if(k == source)
	    			pathmatrix[k][1] = '-';
    	    	else if(path.size()>1){
    				pathmatrix[k][1] = (path.get(1));
    	    	pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;}
    	        		//add(new JLabel(" "+path.get(1)));//,SwingConstants.CENTER);
    			else{
    				pathmatrix[k][1] = (path.get(0));
    	    	pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;}
    				//add(new JLabel(" "+path.get(0)));
    			k++;
    	    	}
    	    	JTable table  = new JTable(pathmatrix,column);
    	    	jf5.add(new JScrollPane(table));
    			jf5.setLayout(new CardLayout());
    	        jf5.setLocationRelativeTo(null);
    		    jf5.setLocation(100, 100);
    		    jf5.setSize(800,800);
    		    jf5.setVisible(true);
    		    jf5.setAlwaysOnTop(true);
    		    //setResizable(true);
    		    jf5.show();
	}
	// Connection Table through the source router and calling Dijkstra Algorithm if the modify router is already given
	public void ConnectionTable(int source,String modify)
	{
    	    	dijkstraShortestDistance(graph,source);
    	    	JFrame jf5 = new JFrame();
    			jf5.setTitle("Connection Table:");
    			pathmatrix= new Object[size][2];
    			JLabel input1 = new JLabel("Router " +(source+1) + " Connection Table");
    			jf5.add(input1);
    			String[] column = new String[]{"Destination","Interface"};
    	    	k=0;
    	    	for(int i=0;i<size;i++)
    	    	pathmatrix[i][0] = i+1;
    	    	for(int i=0;i<size;i++)
    	    	{
    	    		GraphNode dest = graph.getindex(i);
    	    	List<GraphNode> path = shorterpath(dest);
    	    	if(k == source || k== Integer.parseInt(modify))
	    			pathmatrix[k][1] = '-';
    	    	else if(path.size()>1){
    				pathmatrix[k][1] = (path.get(1));pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;}
    	        		//add(new JLabel(" "+path.get(1)));//,SwingConstants.CENTER);
    			else{
    				pathmatrix[k][1] = (path.get(0));pathmatrix[k][1] = (Integer)(pathmatrix[k][1]) + 1;}
    				//add(new JLabel(" "+path.get(0)));
    			k++;
    	    	}
    	    	JTable table1  = new JTable(pathmatrix,column);
    	    	jf5.add(new JScrollPane(table1));
    	    	jf5.setLayout(new CardLayout());
    	    	jf5.setLocationRelativeTo(null);
    	    	jf5.setLocation(100, 100);
    	    	jf5.setSize(800,800);
    	    	jf5.setVisible(true);
    		    //setAlwaysOnTop(true);
    		    //setResizable(true);
    	    	jf5.show();
	}
	//To calculate the shortest distance for the best router
	public int[] shortestdistance(int source)
	{
		return dijkstraShortestDistance(graph,source);
		
	}
	// Dijkstra Algorithm for the calculation of the shortest path from source to destination router
	private int[] dijkstraShortestDistance(Graph graph, int start)

    {
        boolean[] visited = new boolean[graph.size()];
        for (int i=0; i<visited.length; i++) {
            visited[i] = false;
        }
        PriorityQueue<GraphNode> q = new PriorityQueue<GraphNode>();
        
        graph.getindex(start).shortestdistance = 0;
        q.add(graph.getindex(start));
        
        while (!q.isEmpty()) {
            GraphNode node = q.poll();
            visited[node.getindex()] = true;
            
            // For every neighbor         
            for (GraphEdge edge : node.getneighbours()) {
                GraphNode neighbour = graph.getindex(edge.neighbour);
                
                // If not visited, recalculate shortest distance to neighbor
                if (!visited[neighbour.getindex()]) {
                    int distance = node.shortestdistance + edge.weight;
                    if(distance < neighbour.shortestdistance)
                    {
                    neighbour.shortestdistance = Math.min(neighbour.shortestdistance, distance);
                    neighbour.setPredec(node);
                    System.out.println(neighbour.getPredec());
                    // Add to queue
                    if (!q.contains(neighbour)) {
                        q.add(neighbour);
                    }
                    }
                }
            }
        }
     // Put shortest distances into array, -1 if not reachable
        shortestDistance= new int[graph.size()];
        for (int i=0; i<shortestDistance.length; i++) {
            if (graph.getindex(i).shortestdistance != Integer.MAX_VALUE) {
                shortestDistance[i] = graph.getindex(i).shortestdistance;
            }
            else {
                shortestDistance[i] = -1;
            }
        }
        return shortestDistance;
    }
	//Path has beeb calculated by taking reverse of the predecessor
	public static List<GraphNode> shorterpath(GraphNode target){
		List path = new ArrayList();
		for(GraphNode v = target; v != null; v = v.getPredec()){
			int node = v.getindex();
			path.add(node);
		}
		Collections.reverse(path);
		return path;
	}
	
	 // Ask for the destination router and work on it.
	public void shortestpath()
	{
		try{
		 destination = Integer.parseInt(JOptionPane.showInputDialog(null,"Select a destination Router"));
		destination = destination-1;
		 if(destination<0 || destination > size || Integer.toString(destination).isEmpty() || !Integer.toString(destination).matches("[0-9]*")){
			 JOptionPane.showMessageDialog(null, "Please input the Destination correctly","Error Message",JOptionPane.ERROR_MESSAGE);
			 shortestpath();
			 return;
		 }
		 shortestpathoutput(shortestDistance,destination);
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Please input the Destination correctly","Error Message",JOptionPane.ERROR_MESSAGE);
			 shortestpath();
			 return;
		}
	}
	public void shortestpath(int destination)
	{
		shortestpathoutput(shortestDistance,destination);
	}
	
	//Prints the shortest path in dialog box with the cost asscosiated with it.
	private void shortestpathoutput(int[] shortestDistance2, int destination) {
    	GraphNode dest = graph.getindex(destination);
    	JFrame jf = new JFrame();
    	List path = shorterpath(dest);
    	for(int i=0;i<path.size();i++) { int value = (int) path.get(i); path.set(i, value+1);}
    		System.out.println(path);
       	System.out.println(shortestDistance2[destination]);
    	String title = "Shortest Path from Router " + (source+1) + " to " + (destination+1);
    	JOptionPane.showMessageDialog(jf, "Path: " + path.toString().substring(1, path.toString().length() -1).replaceAll(",", " ->") + "  Total Cost: "+shortestDistance2[destination],title,JOptionPane.INFORMATION_MESSAGE);
	}
	public static void main(String[] args)
	{
		new createtopology();
	}
	
	//Ask for the modified router
	public void modify() throws IOException {
		// TODO Auto-generated method stub
		try{
	 modify = (JOptionPane.showInputDialog(null,"Input the integer to be modified"));
	 if(Integer.parseInt(modify)<0 || Integer.parseInt(modify) > size || modify.isEmpty() || !modify.matches("[0-9]*")){
		 JOptionPane.showMessageDialog(null, "Please input the Modify Router correctly","Error Message",JOptionPane.ERROR_MESSAGE);
		 modify();
		 return;
	 }
	 modify = Integer.toString((Integer.parseInt(modify)-1));
		modification(graph,modify);
	}
	catch(Exception e)
	{
		JOptionPane.showMessageDialog(null, "Please input the Modify Router correctly","Error Message",JOptionPane.ERROR_MESSAGE);
		 modify();
		 return;
	}
	}
	void modification(Graph graph,String modify) throws IOException {
		takinginputs(input,modify);
		ConnectionTable(source,modify);	
		shortestpath(destination);
	}
	
	//Best router is calculated by checking all the routers
	public void bestrouter() throws IOException {
		totalcost = new int[size] ;
		for(int i=0;i<size;i++)
		{
			totalcost[i]=0;
		}
		for(int i=0;i<size;i++)
		{
			takinginputs(input,modify);
				int[] result = shortestdistance(i);
				for (int j=0; j<result.length; j++) {
					if(result[j]<0) result[j] = 0;
			 totalcost[i] += result[j];}
		 }
		for(int i=0;i<size;i++)
		{
			System.out.println(totalcost[i]);
		}
		int min = totalcost[0];
		int minimum = 0;
		for(int i=1;i<size;i++)
		{
			if(totalcost[i]>0 && totalcost[i] < min)
			{
				min = totalcost[i];
				minimum = i;
			}
		}
		JFrame jf6 = new JFrame();
		jf6.setTitle("Best router Available");
		jf6.add(new JLabel("Best router is " + (minimum+1)+ " Total cost: " + totalcost[minimum]));
		takinginputs(input,modify);
		int[] result1 = shortestdistance(minimum);
		for(int i=0;i<result1.length;i++)
		{
			System.out.println(result1[i]);
		}
		Object[][] result2 = new Object[result1.length][result1.length];
		for(int i=0;i<size;i++)
		{
			result2[i][0] = i+1;
		}
		for(int i=0;i<size;i++)
		{
			if(modify==null) modify=Integer.toString(0);
			if(i!=minimum || i!=Integer.parseInt(modify))
			result2[i][1] = result1[i];
			else
				result2[i][1]='-';
		}
		
		String[] column = new String[]{"Router","Distance"};
		JTable table  = new JTable(result2,column);
		jf6.add(new JScrollPane(table));
		jf6.setLayout(new CardLayout());
        jf6.setLocationRelativeTo(null);
	    jf6.setLocation(100, 100);
	    jf6.setSize(800,800);
	    jf6.setVisible(true);
	    jf6.setAlwaysOnTop(true);
	    //setResizable(true);
	    jf6.show();
		System.out.println(totalcost[minimum]);
	}
}
