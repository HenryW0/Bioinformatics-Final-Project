import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

/**
 * CS 123A Project Code for Phylogenetic trees based on Newick Format
 * @author Henry Wahhab
 * @version 1.0 5/3/2023
 */

public class TreePanel extends JPanel {

	public static final int CIRCLE = 8;
	public static final int BASE_LEN = 4;
	
	private PhyloTree tree;
	
	//Constructs a tree panel
	public TreePanel(PhyloTree t)
	{
		tree = t;
		setPreferredSize(new Dimension(3000, 2500));
	}
	
	//Checks if any lengths are given for the node, if not, uses 50 as default
	public void checkLen(PhyloTree.Node n)
	{
		if (n.getLength() == 0 && !n.getName().equals("Root"))
		{
			n.setLength(50);
		}
	}
	
	//Recursive draw function to draw tree
	public void traverseDraw(PhyloTree.Node root, Graphics2D g2, int x, int y)
	{
		if (!root.hasChildren()) //If the current node does not have children
		{
			Line2D.Double l = new Line2D.Double(x, y, x + root.getLength(), y);
			g2.draw(l); //Draw line based on length using x and y coordinates
			
			float midpoint = (float) ((l.getX2() + l.getX1()) / 2.0); //Find midpoint of line
			
			g2.setColor(Color.RED);
			checkLen(root);
			g2.drawString(Double.toString(Math.round(root.getLength())), midpoint - 6, (float) y - 5); //Write the length on the middle of the line
			g2.setColor(Color.BLACK);
			
			g2.fillOval((int) (x + root.getLength()), (int) (y - CIRCLE / 2), CIRCLE, CIRCLE);
			g2.drawString(root.getName(), x + (float) root.getLength() + 12, y); //Draw circle where node is with name
		}

	    if (root.hasChildren())
	    {
	    	Line2D.Double horiz = new Line2D.Double(x, y, x + root.getLength(), y); //Same process as above
    		g2.draw(horiz);
    		
    		float midpoint = (float) ((horiz.getX2() + horiz.getX1()) / 2.0);
    		checkLen(root);
    		
    		if (root.getLength() != 0)
    		{
    			g2.setColor(Color.RED);
    			g2.drawString(Double.toString(Math.round(root.getLength())), midpoint - 6, (float) y - 4);
        		g2.setColor(Color.BLACK);
    		}
    	
    		x += root.getLength();
    		
    		int numChild = root.getChildren().size(); //Gets number of direct children this node has
    		int numLeafs = PhyloTree.getLeafNodes(root); //Counts number of leaf nodes that stem from this node
    		double baseLength = (Math.pow(numLeafs, 1.4)) * BASE_LEN; //This controls the vertical lines for internal nodes
    		
    		Line2D.Double base = new Line2D.Double(x, y - baseLength, x, y + baseLength);
    		g2.draw(base); //Draws verticle base line
    		
    		double increment = (base.getY2() - base.getY1()) / (numChild - 1); //Equally separates children on the verticle base line
    		y = (int) base.getY1();
    		
    		//Repeat for all children recursively
	    	for (PhyloTree.Node n: root.getChildren())
	    	{	
	    		traverseDraw(n, g2, x, y);
	    		y += increment;
	    	}

	    }
	    
	}
	
	//Override for JPanel's paintComponent function
	public void paintComponent(Graphics g)
	{ 
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		traverseDraw(tree.getRoot(), g2, 25, getHeight() / 2); //Call recursive draw method
	}
	
}
