package test;

import RBT.RedBlackTree;
import java.util.Random;
import app.exercise.visualtree.RedBlackTreeDrawer;

public class IntegerTestRandom {

    public static void main(String[] args) {
	//Initing the varables
	RedBlackTree<Integer> tree;
	RedBlackTreeDrawer rd = new RedBlackTreeDrawer();
	Random rand = new Random();

	//Evaluating the input
	if (args.length < 1) {
	    System.out.println("Tree with zero elements");
	    return;
	} else {
	    tree = new RedBlackTree<Integer>(null, Integer.parseInt(args[0]));
	}

	//
	for (int i = 1; i < args.length; i++) {
	    int num = rand.nextInt(Integer.MAX_VALUE);
	    if (!tree.contains(num)) {
		tree.add(num);
		rd.draw(tree);
	    }
	}


    }
}
