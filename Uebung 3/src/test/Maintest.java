package test;

import RBT.RedBlackTree;
import algebra.CompRational;
import algebra.Rational;
import java.util.Iterator;

public class Maintest {

    private static CompRational<Rational> eval(String[] args) {
	int num = Integer.parseInt(args[0]);
	int den = Integer.parseInt(args[1]);
	CompRational<Rational> element = new CompRational<Rational>(num, den);
	return element;
    }

    public static void main(String[] args) {
	RedBlackTree<CompRational<Rational>> tree;

	if (args.length % 2 != 0) {
	    System.err.println("The argument count has to be even");
	    return;
	} else if (args.length < 2) {
	    System.err.println("Cant evaluate argument length");
	    return;
	}
	String[] parts = new String[0];
	System.arraycopy(args, 0, parts, 0, 2);
	tree = new RedBlackTree<CompRational<Rational>>(null, eval(parts));

	for (int i = 2; i < args.length; i += 2) {
	    parts = new String[0];
	    System.arraycopy(args, 0, parts, 0, 2);
	    tree.add(eval(parts));
	}

	Iterator<CompRational<Rational>> iterator = tree.iterator();

	while (iterator.hasNext()) {
	    CompRational<Rational> next = iterator.next();
	    System.out.println(next);
	    if (next.valueOf() < 1) {
		iterator.remove();
	    }
	}
    }

}
