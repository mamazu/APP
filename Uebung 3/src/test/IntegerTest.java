package test;

import RBT.RedBlackTree;
import java.util.Iterator;

public class IntegerTest {

    public static void main(String[] args) {
	RedBlackTree<Integer> tree = new RedBlackTree<Integer>(null);

	for (String arg : args) {
	    int num = Integer.parseInt(arg);
	    tree.add(num);
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
