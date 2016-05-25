package RBT;

import java.util.AbstractCollection;
import java.util.Iterator;

public class BinaryTree<E extends Comparable> extends AbstractCollection<E> implements Iterable<E> {

    protected BinaryTree left, right, parent;
    protected E value;

    public BinaryTree(E value) {
	this.parent = null;
	this.left = null;
	this.value = value;
	this.right = null;
    }

    /**
     * Creates a Binary Tree with a parent node and a value
     *
     * @param parent
     * @param value
     */
    public BinaryTree(BinaryTree parent, E value) {
	this(value);
	this.parent = parent;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public BinaryTree getParent() {
	return parent;
    }

    public BinaryTree getLeft() {
	return left;
    }

    public BinaryTree getRight() {
	return right;
    }

    public E getValue() {
	return value;
    }
    //</editor-fold>

    //Itterator properties
    public Iterator<E> iterator() {
	return new TreeItterator<BinaryTree>(this);
    }

    public Iterator<E> reversedIterator() {
	return new TreeItterator<BinaryTree>(this, false);
    }

    public boolean remove() {
	if (this == null) {
	    return false;
	}
	if (left == null || right == null) {
	    // Node is a leaf or a half leaf
	    if (parent.left == this) {
		parent.left = (left == null) ? right : left;
	    } else {
		parent.right = (left == null) ? right : left;
	    }
	    return true;
	}
	value = (E) left.value;
	return left.remove();
    }

    //Abstract collection
    @Override
    public int size() {
	return size(this);
    }

    /**
     * Adds the value to the tree
     *
     * @param value
     * @return true if value is unique; false if it is already in the tree
     * (didnt get added)
     */
    @Override
    public boolean add(E value) {
	return add(left, value);
    }

    /**
     * Adds the value in the branch with the binary properties
     *
     * @param tree
     * @param value
     * @return
     */
    private boolean add(BinaryTree tree, E value) {
	switch (value.compareTo(this.value)) {
	    case 1:
		if (tree.right == null) {
		    tree.right = new BinaryTree(tree, value);
		    return true;
		}
		return add(tree.right, value);
	    case -1:
		if (tree.left == null) {
		    tree.left = new BinaryTree(tree, value);
		    return true;
		}
		return add(tree.left, value);
	    default:
		return false;
	}
    }

    public int size(BinaryTree tree) {
	if (tree == null) {
	    return 0;
	}
	return size(tree.left) + 1 + size(tree.right);
    }

    //Magic methods
    /**
     * Returns a string representation of the tree
     *
     * @return
     */
    @Override
    public String toString() {
	return "BinaryTree{" + '}';
    }

}
