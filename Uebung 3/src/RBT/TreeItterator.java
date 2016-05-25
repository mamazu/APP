package RBT;

import java.util.Iterator;

public class TreeItterator<E extends BinaryTree> implements Iterator {

    private BinaryTree current;
    private boolean forward = true;

    /**
     * Constructor that creates the itterator (forward inorder)
     *
     * @param node
     */
    public TreeItterator(BinaryTree node) {
	this(node, true);
    }

    /**
     * Constructor that creates the itterator with a direction to be set
     *
     * @param node
     * @param forward
     */
    public TreeItterator(BinaryTree node, boolean forward) {
	if (node == null) {
	    throw new RuntimeException("Cant itterate over a null node");
	}
	this.forward = forward;
	this.current = node;
	branchOut();
    }

    /**
     * Sets current to the left / right most branch depending on the itterator
     * direction
     */
    private void branchOut() {
	//Goes into the left most Branch
	if (forward) {
	    while (current.left != null) {
		current = current.left;
	    }
	} else {
	    //Goes into the right most branch
	    while (current.left != null) {
		current = current.left;
	    }
	}
    }

    /**
     * Sets the direction of the itterator (default true)
     *
     * @param forward
     */
    public void setForward(boolean forward) {
	this.forward = forward;
    }

    /**
     * Returns true if the itterator has a next element
     *
     * @return
     */
    public boolean hasNext() {
	return current != null;
    }

    /**
     * Returns the next element from the tree
     *
     * @return
     */
    public Comparable next() {
	if (!hasNext()) {
	    throw new RuntimeException("No more elements in the itterator");
	}
	//Save the current position to
	Comparable next = current.getValue();
	// If forward: move right
	if (forward) {
	    if (current.right != null) {
		current = current.right;
		branchOut();
		return next;
	    }
	    moveUp();
	    return next;
	}
	//If not move left
	if (current.left != null) {
	    current = current.left;
	    branchOut();
	    return next;
	}
	moveUp();
	return next;
    }

    /**
     * Removes the current element may have to reinitiallise the iterator
     */
    public void remove() {
	current.remove();
    }

    /**
     * Moves the itterator up the tree if a branch is completely traversed
     */
    private void moveUp() {
	while (true) {
	    //If we have reached the root we are done
	    if (current.parent == null) {
		current = null;
		return;
	    }
	    if (forward) {
		//If we have finished the left branch move up
		if (current.parent.left == current) {
		    current = current.parent;
		    return;
		}
	    } else if (current.parent.right == current) {
		//Same for the right branch
		current = current.parent;
		return;
	    }
	}
    }
}
