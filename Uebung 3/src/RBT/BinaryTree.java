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

    public BinaryTree getLeftBranch() {
	return left;
    }

    public BinaryTree getRightBranch() {
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

    /**
     * Removes the current element form the tree
     *
     * @return True if successful, false otherwise
     */
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
    /**
     * Returns the size of the tree
     *
     * @return Size
     */
    @Override
    public int size() {
	return size(this);
    }

    /**
     * Returns wheather the tree is empty or not
     *
     * @return True if empty false otherwise
     */
    @Override
    public boolean isEmpty() {
	return size(this) == 0;
    }

    /**
     * Adds the value to the tree
     *
     * @param value
     * @return true if value is unique; false if it is already in the tree
     * (didnt get added)
     */
    @Override
    public boolean add(E value) throws RuntimeException {
	return add(this, value);
    }

    /**
     * Adds the value in the branch with the binary properties
     *
     * @param tree
     * @param value
     * @return
     */
    private boolean add(BinaryTree tree, E value) throws RuntimeException {
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
		throw new RuntimeException("Element already in the tree");
	}
    }

    public BinaryTree find(E value) {
	return find(this, value);
    }

    private BinaryTree find(BinaryTree tree, E value) {
	if(tree == null){
	    return null;
	}
	switch(this.value.compareTo(value)){
	    case 1:
		return find(tree.right, value);
	    case -1:
		return find(tree.left, value);
	    default:
		return tree;
	}
    }

    /**
     * Calculates the size of a subtree
     *
     * @param tree
     * @return Size of the subtree
     */
    public int size(BinaryTree tree) {
	if (tree == null) {
	    return 0;
	}
	return size(tree.left) + 1 + size(tree.right);
    }

}
