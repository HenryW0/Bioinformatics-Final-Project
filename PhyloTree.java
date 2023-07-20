import java.util.ArrayList;

/**
 * CS 123A Project Code for Phylogenetic trees based on Newick Format
 * @author Henry Wahhab
 * @version 1.0 5/3/2023
 */

public class PhyloTree {

	static int out;
	
	private Node root; 
	public String original;
	public int scale;
	
	public PhyloTree(String s, int scale)
	{
		original = s; //Original string
		this.scale = scale; //Scale value used for lengths
		s = s.substring(0, s.length() - 1); //Removes ; from string
		root = new Node(0, "start"); //Creates the root node 
		recursiveTreeParse(s, root); //Method call to helper 
		
		if (root.getName().equals("Parent") || root.getName().isEmpty()) //Renames root node after
		{
			root.setName("Root");
		}
		
		scaleDist(root); //Scales all the nodes based on the scale parameter
	}
	
	//Helper method for constructor
	private void recursiveTreeParse(String s, Node parent)
	{
		
		int endIndex = rindex(s, ')') + 1; //returns -1 if cannot be found
		
		String currStr = s.substring(endIndex); //Helps separate internal node data from its children
		
		//Get and set
		String name = getName(currStr);
		double distance = getDistance(currStr);
		parent.setName(name);
		parent.setLength(distance);
		
		if (endIndex != 0)
		{
			String childrenStr = s.substring(1, endIndex - 1); //This substring contains all child information
			
			while (!childrenStr.isEmpty())
			{
				int childrenEndIndex = getEndIndex(childrenStr); //Calls complex helper method to find endIndex
				Node currNode = new Node(distance, name); //Creates new node
                parent.addChild(currNode);  //Adds node as a child to this current parent
                
                String next = childrenStr.substring(0, childrenEndIndex); //Gets the string for the next child
                recursiveTreeParse(next, currNode); //Recursively call this method
                
                if (childrenEndIndex < childrenStr.length()) 
                {
                	childrenStr = childrenStr.substring(childrenEndIndex + 1); //Update the new string for the loop to be the rest of the data
                }
                
                else //End loop
                {
                	break;
                }
			}
			
		}
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	//Gets the name of the node based on the position of the colon or lack thereof 
	private static String getName(String s)
	{
		if (!s.contains(":") && !s.isEmpty())
		{
			return s;
		}
		
		else if (s.indexOf(':') != 0 && !s.isEmpty())
		{
			return s.substring(0, s.indexOf(":"));
		}
		
		else
		{
			return "";
		}
	}
	
	//Gets the distance of the node based on the position of the colon or lack thereof
	private static Double getDistance(String s)
	{
		if (s.contains(":"))
		{
			return Double.parseDouble(s.substring(s.indexOf(":") + 1));
		}
		
		else
		{
			return 0.0;
		}
	}
	
	//Helper method to obtain the end index of the current region of the string we want parsed
	private int getEndIndex(String s)
	{
		int endIndex;
		
		if (s.charAt(0) != '(') //Checks if this child is not an internal node
		{
			if (s.contains(",")) 
			{
				return s.indexOf(','); //Splices string based on comma separator
			}
			
			else //Otherwise this is the end of an internal node's child list
			{
				return s.length();
			}
		}
		
		else //If this else statement is used, it means this string is an internal node
		{
			int leftPCount = 1; //Left parenthesis count
			int offset = 1; //Number of right parentheses that it must skip
			
			s = s.substring(1); //Removes first ( in the string 
			
			while (!s.isEmpty())
			{
				int rightPIndex = s.indexOf(')') + 1; //Right parenthesis index
				//Add one since substring later on does not include end index
				
				if (!s.contains("(") || rightPIndex < s.indexOf('(') + 1) //If this is true, we are in the inner most children for this part of the string
				{
					leftPCount --;
					
					if (leftPCount == 0)
					{
						String parentInfo = s.substring(rightPIndex); //Parent information would be after the ) of the string since this is an internal node
						
						if (parentInfo.contains(",")) //Checks if there are more nodes after this parent
						{
							endIndex = rightPIndex + parentInfo.indexOf(','); //This index contains the rest of the nodes after the parent
						}
												
						else
						{
							endIndex = s.length(); //No nodes after the parent, end index is the length of the rest of the string
						}
						
						return offset + endIndex; //Offset helps endIndex skip extra commas and/or extra inner nodes to the most outer most at a time 
					}
				
					s = s.substring(rightPIndex);
					offset += rightPIndex;			
				}
				
				else //Not the inner most children meaning there are more left parentheses 
				{
					leftPCount ++;
					int leftPIndex = s.indexOf('(') + 1; //Finds and skips the next left parenthesis index
					s = s.substring(leftPIndex);
			        offset += leftPIndex; //increment offset by this new index
				}
				
			}
		}
		return -2; //Should never be used
	}
	
	//Get index of the first appearance of the character from the right side of the string instead 
	private static int rindex(String s, char c)
	{
		String reversed = "";
		
		for (int i = 0; i < s.length(); i++) 
		{
			reversed = s.charAt(i) + reversed;
		}
		
		if (reversed.indexOf(c) != -1)
		{
			return (s.length() - 1 - reversed.indexOf(c));
		}
		
		else
		{
			return -1;
		}
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	//Returns root node
	public Node getRoot()
	{
		return root;
	}
	
	//Counts the amount of leaf nodes stemming from this node
	public static int getLeafNodes(Node n)
	{
		out = 0;
		childRecurse(n);
		return out;
	}
	
	//Helps count the leaf nodes
	private static void childRecurse(Node n)
	{
		if (n.hasChildren())
	    {
	    	for (Node c: n.getChildren())
	    	{
	    		childRecurse(c);
	    	}
	    }
		
		else
		{
			out ++; //Increments leaf node counter
		}
	}
	
	//Traverses through all nodes and scales their length based on scale
	public void scaleDist(Node root)
	{
		root.setLength(root.getLength() * scale);
		
		if (root.hasChildren())
		{
			for (Node n: root.getChildren())
			{
				scaleDist(n);
			}
		}
	}
	
	//Traverses through all nodes and simply prints them out with indents to symbolize children
	public void traverse(Node root, String indent)
	{ 	
		System.out.println(indent + root.toString());
		
	    if (root.hasChildren())
	    {
	    	indent = indent + "     ";
	    	for (Node n: root.getChildren())
	    	{
	    		traverse(n, indent);
	    	}
	    }
	    
	}
	
	//Static inner class that composes up the PhyloTree class
	public static class Node {
		
		private double length;
		private String name;
		private ArrayList<Node> children;
		
		//Constructor for the node
		public Node(double l, String c)
		{
			setLength(l);
			setName(c);
			children = null;
		}
		
		//Returns length
		public double getLength() {
			return length;
		}

		//Sets length 
		public void setLength(double length) {
			this.length = length;
		}

		//Returns name
		public String getName() {
			return name;
		}

		//Sets name
		public void setName(String name) {
			this.name = name;
		}

		//Adds a child to the current node
		public void addChild(Node n)
		{
			
			if (children == null)
			{
				children = new ArrayList<Node>();
			}
			
			children.add(n);
		}
		
		//Returns true if the node has children, false otherwise
		public boolean hasChildren()
		{
			if (children == null)
			{
				return false;
			}
			
			return true;
		}
		
		//Returns an arraylist of all children this node has
		public ArrayList<Node> getChildren()
		{
			return children;
		}
		
		//Converts the node into a readable format
		public String toString() 
		{
			return ("Name: " + name + ", Length: " + length);
		}
		
	}
}
