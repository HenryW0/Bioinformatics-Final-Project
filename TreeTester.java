
/**
 * CS 123A Project Code for Phylogenetic trees based on Newick Format
 * @author Henry Wahhab
 * @version 1.0 5/3/2023
 */

public class TreeTester {

	//Main method where you can input strings of data that are formatted in Newick
	public static void main (String[] args)
	{
		//String from Clustal Omega for phylogenetic tree
		String data = "((((((Zebrafish:0.24238,((((Finch:0.03385, Hawaiian Crow:0.02987):0.01912,Emperor Penguin:0.04018):0.01578,"
				+ "Hummingbird:0.04407):0.06737,((Alligator:0.01189,Crocodile:0.01281):0.07425,"
				+ "(Terrapin:0.02861,Tortoise:0.00094):0.05210):0.01816):0.04355):0.02941,Koala:0.13352):0.01494,"
				+ "Hedgehog:0.11800):0.02770,((Human:0.11205,Mouse:0.11536):0.00965,Rabbit:0.05994):0.00273):0.01504,Zebra:0.08289):0.00072,"
				+ "(Big Brown Bat:0.05242,((Dolphin:0.00251,Killer Whale:0.00073):0.04510,(Deer:0.02882,"
				+ "((Water Buffalo:0.00585,Cow:0.00928):0.00636,Bison:0.00230):0.00891):0.04297):0.01083):0.00362,"
				+ "(((Cat:0.00203,Lion:0.01065):0.02929,Dog:0.03277):0.00146,Ferret:0.05442):0.01859);";
		
		//PhyloTree tree = new PhyloTree(data, 8000);
		
		//Below are just other examples that can be used which are commented out, used for testing as well
		
	    //PhyloTree tree = new PhyloTree("(Z:15,Y:20,(X:30,(W:10,T:20,U:30):40,Q:50,R:80):50):50;", 100);
		//PhyloTree tree = new PhyloTree("(A:10,B:20,(C:30,D:40):50);", 10);
		//PhyloTree tree = new PhyloTree("(A,B,(C,D)E);", 1);
		PhyloTree tree = new PhyloTree("(:10,:20,(:30,:40):50);", 10);
		//PhyloTree tree = new PhyloTree("(,,(, ));", 1); //This one needs an extra space for it to work
		
		new TreeFrame(tree); //Creates tree frame and draws 
		tree.traverse(tree.getRoot(), ""); //Console output for printing tree data
	}
	
}
