package test;

import RBT.RedBlackTree;
import java.util.Iterator;

public class IntegerTest {

    public static void main(String[] args) {
	RedBlackTree<Integer> tree;
	if (args.length < 1) {
	    System.out.println("Tree with zero elements");
	    return;
	} else {
	    tree = new RedBlackTree<Integer>(null, Integer.parseInt(args[0]));
	}

	for (int i = 1; i < args.length; i++) {
	    int num = Integer.parseInt(args[i]);
	    boolean add = tree.add(num);
	    if (!add) {
		System.err.println("Could not add " + num + " to the tree");
	    }
	}

	Iterator<Integer> iterator = tree.iterator();
	while (iterator.hasNext()) {
	    int next = iterator.next();
	    System.out.println(next);
	    if (next < 0) {
		iterator.remove();
	    }
	}
    }
}
