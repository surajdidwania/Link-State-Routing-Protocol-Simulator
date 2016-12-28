/*****************************************************************************************
*Class Name: Graph
*
*Dependencies: Createtopology.java Topology.java
*
*			
*
*Description: It consists of 3 classes. A) Graph which specifies all the properties of the graph 	
*			  such as index of the node, add edge
*			B) GraphNode which specifies all the properties of the node such as index, neighbours and shortest distance.
*			C) GraphEdge which specifies egde property
*
*
*Author: Suraj Kumar Didwania <dsuraj@hawk.iit.edu> Seq No: 22
*
*************************************************************************************************/import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// Graph represents the graph of the matrix
 class Graph{
		 ArrayList<GraphNode> nodes;
		public Graph(int numnodes)
		{
			nodes = new ArrayList<GraphNode>(numnodes);
			for(int i=0;i<numnodes;i++)
			{
				nodes.add(new GraphNode(i));
			}
		}
		public void removenode(int node)
		{
			nodes.remove(node);
		}
		
		public GraphNode getindex(int index)
		{
			return nodes.get(index);
		}
		public int size()
		{
			return nodes.size();
		}
		public void addEdge(int x,int y,int weight)
		{			GraphNode X = getindex(x); GraphNode Y = getindex(y);
			X.addedge(y, weight);
			Y.addedge(x, weight);
		}
		public void deletedge(int node)
		{
			GraphNode X = getindex(node);
			X.deleteedge1(node);
		}
		public void deletetarget(int n)
		{
			for (GraphNode node : nodes)
			{
				node.deletevertexnode(n);
			}
				
		}
		public void printGraph()
        {
            for (GraphNode node : nodes) {
                node.printneighbours();
            }
        }
	}
 //GraphNode of the graph represent each node
	class GraphNode implements Comparable<GraphNode>
	{
		public int index;
		public HashMap<Integer,GraphEdge> neighbours;
		public int shortestdistance;
		public GraphNode predec;
		public GraphNode(int i) {
			this.index = i;
			neighbours = new HashMap<Integer,GraphEdge>();
			shortestdistance = Integer.MAX_VALUE;
		}
		public void deletevertexnode(int n) {
			for(GraphEdge edge1: neighbours.values())
			{
				if(edge1.neighbour == n)
				{
					neighbours.remove(index,edge1.neighbour);
				}
				}
			
		}
		public void deleteedge1(int node) {
			// TODO Auto-generated method stub
			neighbours.remove(node);
			
		}
		
		public void addedge(int neighbour,int weight)
		{
			if(neighbours.containsKey(neighbour))
			{
				GraphEdge edge = neighbours.get(neighbour);
				if(edge.weight > weight)
				{
					edge.weight = weight;
				}
			}
			else
			{
				neighbours.put(neighbour, new GraphEdge(neighbour,weight));
			}
		}
		public GraphNode getPredec() {
			return predec;
		}
		public void setPredec(GraphNode predec) {
			this.predec = predec;
		}
		public GraphEdge getEdge(int index)
		{
			return neighbours.get(index);
		}
		public int getindex()
		{
			return index;
		}
		public Collection<GraphEdge> getneighbours()
		{
			return neighbours.values();
		}
		public void printneighbours()
		{
			for(GraphEdge edge: neighbours.values())
			{
				System.out.println((index+1) + " " + (edge.neighbour+1) + " " + edge.weight);
			}
		}
		public int compareTo(GraphNode other) {
			if (shortestdistance < other.shortestdistance) {
                return -1;
            }
            else if (shortestdistance == other.shortestdistance) {
                return 0;
            }
            else {
                return 1;
            }
        }
	}	
	
	//GraphEdge of the graph represents each edge
	 class GraphEdge
	{
		public int neighbour;
		public int weight;
		public GraphEdge(int neighbour, int weight) {
			this.neighbour = neighbour;
			this.weight = weight;
		}	
	}