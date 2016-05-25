package test;

import RBT.RedBlackTree;
import algebra.CompRational;
import algebra.Rational;
import java.util.Iterator;

public class Maintest {

    public static void main(String[] args) {
	RedBlackTree<CompRational<Rational>> tree = new RedBlackTree<CompRational<Rational>>(null);

	if (args.length % 2 != 0) {
	    System.err.println("The argument count has to be even");
	}
	for (int i = 0; i < args.length; i += 2) {
	    int num = Integer.parseInt(args[i]);
	    int den = Integer.parseInt(args[i + 1]);
	    CompRational<Rational> element = new CompRational(num, den);
	    tree.add(element);
	}

	Iterator<CompRational<Rational>> iterator = tree.iterator();
	while (iterator.hasNext()) {
	    CompRational<Rational> next = iterator.next();
	    System.out.println(next);
	    if(next.valueOf() < 1){
		iterator.remove();
	    }
	}
    }

}
