package app.exercise.testing;

import app.exercise.adt.BinaryTree;
import app.exercise.algebra.CompRational;

public class TestBST {

    public static final BinaryTree<Double> doubleTree = new BinaryTree<Double>();
    public static final BinaryTree<CompRational> rationalTree = new BinaryTree<CompRational>();

    public static void main(String[] args) {
        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                int first = Integer.parseInt(args[i]);
                int next = Integer.parseInt(args[i]);
                CompRational node = new CompRational(first, next);
                if (i == 0) {
                    rationalTree.setValue(node);
                }else{
                    rationalTree.insert(node);
                }
            }
            System.out.println(rationalTree.toString());
        }else{
            for(int i = 0; i < args.length; i++){
                double node = Double.parseDouble(args[i]);
                if(i==0){
                    doubleTree.setValue(node);
                }else{
                    doubleTree.insert(node);
                }                
            }
            System.out.println(doubleTree.toString());
        }
    }
}
