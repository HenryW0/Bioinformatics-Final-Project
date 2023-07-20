import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.*;

/**
 * CS 123A Project Code for Phylogenetic trees based on Newick Format
 * @author Henry Wahhab
 * @version 1.0 5/3/2023
 */

public class TreeFrame extends JFrame {

	 public static final int DEFAULT_WIDTH = 1000;
	 public static final int DEFAULT_HEIGHT = 800;
	 
	 private PhyloTree tree;
	 
	 //Creates a Frame to create and open a window
	 public TreeFrame(PhyloTree t)
	 {
		 tree = t;
		 
		 setTitle("Newick Tree Generator");
		 setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		 setLayout(new BorderLayout());
		 
		 JTextArea ta = new JTextArea(tree.original);
		 ta.append("\n");
		 JScrollPane sp = new JScrollPane(ta);
		 add(sp, BorderLayout.NORTH); //Adds a text area with the string and a scroll to see the whole text
		 
		 TreePanel panel = new TreePanel(tree);
		 JScrollPane sp2 = new JScrollPane(panel);
		 sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 
		 add(sp2, BorderLayout.CENTER); //Adds a tree panel which controls the drawing functionality and adds scroll-bars 
		 
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 setVisible(true);
	 }
	
}
