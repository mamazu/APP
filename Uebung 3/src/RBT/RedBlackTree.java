package RBT;

import java.awt.Color;
import java.util.*;

public class RedBlackTree<E extends Comparable> extends AbstractCollection<E> {
    //E has to be extending Comperable

    private int size = 0;
    private E value;
    protected RedBlackTree<E> left;
    protected RedBlackTree<E> right;
    private RedBlackTree<E> parent;
    private Color color = Color.RED;

    public RedBlackTree(RedBlackTree parent) {
	this.parent = parent;
	this.left = null;
	this.right = null;
	color();
    }

    public RedBlackTree(RedBlackTree parent, E value) {
	this(parent);
	this.value = value;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public E getValue() {
	return value;
    }

    public void setValue(E value) {
	this.value = value;
    }

    public void toggleColor() {
	if (color == Color.RED) {
	    color = Color.BLACK;
	}
	color = Color.RED;
    }

    public int getBlack(RedBlackTree b) {
	if (b == null) {
	    return 0;
	}
	int depth = (color == Color.BLACK) ? 1 : 0;
	return Math.max(getBlack(this.left), getBlack(this.right)) + depth;
    }
    //</editor-fold>

    private void rotateLeft(RedBlackTree x) {
	RedBlackTree y = x.left;
	x.left = y.right;
	if (y.right != null) {
	    x = y;
	} else if (x == x.parent.left) {
	    x.parent.left = y;
	} else {
	    x.parent.right = y;
	}
	x.right = x;
	x.parent = y;
    }

    /**
     * Sets the color for a node
     */
    private void color() {
	if (parent == null) {
	    color = Color.BLACK; // Root has to be black
	}
    }

    /**
     * Adds a node
     *
     * @param element
     * @return True if it has worked, false otherwise
     */
    @Override
    public boolean add(E element) {
	return add(this, element);
    }

    /**
     * Adds an element to the subtree (recursively)
     *
     * @param subtree Tree to insert
     * @param element Element that should be inserted
     * @return True on success and false on failure
     */
    private boolean add(RedBlackTree subtree, E element) {
	if (element.compareTo(subtree) < 0) {
	    if (subtree.left == null) {
		subtree.left = new RedBlackTree(subtree, element);
		size++;
		return true;
	    }
	    return add(subtree.left, element);
	} else {
	    if (subtree.right == null) {
		subtree.right = new RedBlackTree(subtree, element);
		size++;
		return true;
	    }
	    return add(subtree.right, element);
	}
    }

    private void fix() {

    }

    /**
     * Generates an iterator over all the elements in order
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
	Iterator<E> it;
	it = new Iterator() {
	    RedBlackTree subtree;
	    boolean parent = false;

	    /**
	     * Sets the start for the iterator
	     *
	     * @param node Redblacktree starting node
	     */
	    public void setStart(RedBlackTree node) {
		subtree = node;
		goLeft();
	    }

	    /**
	     * Returns if there is a next element in the tree
	     *
	     * @return
	     */
	    public boolean hasNext() {
		return subtree != null;
	    }

	    /**
	     * Returns the next Element in the
	     *
	     * @return
	     */
	    public Object next() {
		Object result = subtree.getValue();
		if (subtree.left == null) {
		    subtree = subtree.parent;
		    parent = true;
		}
		if (parent) {
		    if (subtree.right != null) {
			subtree = subtree.right;
			goLeft();
		    } else {
			subtree = subtree.parent;
		    }
		}
		return result;
	    }

	    /**
	     * Removes the current element from the tree
	     */
	    public void remove() {
		subtree.remove(subtree);
	    }

	    /**
	     * Sets the subtree property to the left most node in the subtree
	     */
	    private void goLeft() {
		while (subtree.left != null) {
		    subtree = subtree.left;
		}
	    }
	};
	return it;
    }

    /**
     * Generates an iterator over all the elements in reversed order
     *
     * @return
     */
    public Iterator<E> reversedIterator() {
	Iterator<E> it;
	it = new Iterator() {
	    RedBlackTree subtree;
	    boolean parent = false;

	    /**
	     * Sets the start for the iterator
	     *
	     * @param node Redblacktree starting node
	     */
	    public void setStart(RedBlackTree node) {
		subtree = node;
		goRight();
	    }

	    /**
	     * Returns if there is a next element in the tree
	     *
	     * @return
	     */
	    public boolean hasNext() {
		return subtree != null;
	    }

	    /**
	     * Returns the next Element in the
	     *
	     * @return
	     */
	    public Object next() {
		Object result = subtree.getValue();
		if (subtree.left == null) {
		    subtree = subtree.parent;
		    parent = true;
		}
		if (parent) {
		    if (subtree.left != null) {
			subtree = subtree.left;
			goRight();
		    } else {
			subtree = subtree.parent;
		    }
		}
		return result;
	    }

	    /**
	     * Removes the current element from the tree
	     */
	    public void remove() {
		subtree.remove(subtree);
	    }

	    /**
	     * Sets the subtree property to the left most node in the subtree
	     */
	    private void goRight() {
		while (subtree.right != null) {
		    subtree = subtree.right;
		}
	    }
	};
	return it;
    }

    /**
     * Returns the size of the tree
     *
     * @return
     */
    @Override
    public int size() {
	return size;
    }

    /**
     * Compares the node ot a different node
     *
     * @param o
     * @return -1 if smaller; 0 if equal and 1 if greater
     */
    public int compareTo(E o) {
	return o.compareTo(value);
    }

}
