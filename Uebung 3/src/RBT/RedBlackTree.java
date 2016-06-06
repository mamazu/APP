package RBT;

import app.exercise.visualtree.DrawableTreeElement;

public class RedBlackTree<E extends Comparable> extends BinaryTree<E> implements DrawableTreeElement<E> {

    private RedBlackTree left, right;

    private boolean red = false;

    /**
     * Default constructor for the object
     *
     * @param parent of the node (null if its root)
     * @param value of the node
     */
    public RedBlackTree(RedBlackTree parent, E value) {
	super(parent, value);
	red = false;
    }

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public boolean isRed() {
	if (this == null) {
	    return false;
	}
	return red;
    }

    public void setRed(boolean red) {
	this.red = red;
    }

    public void setRed() {
	setRed(true);
    }

    public int getBlackDepth(RedBlackTree b) {
	if (b == null) {
	    return 1;
	}
	int depth = (!red) ? 1 : 0;
	return Math.max(getBlackDepth(this.left), getBlackDepth(this.right)) + depth;
    }
    //</editor-fold>

    /**
     * Adds an element to the tree
     *
     * @param value
     * @return Returns true if successfull; false otherwise
     * @throws RuntimeException Throws exception if element already in the tree
     */
    @Override
    public boolean add(E value) throws RuntimeException {
	if (!super.add(value)) {
	    return false;
	}
	RedBlackTree insertedNode = (RedBlackTree) find(value);
	if (insertedNode.right.isRed() && !insertedNode.left.isRed()) {
	    rotateLeft(insertedNode);
	}
	if (insertedNode.left.isRed() && !insertedNode.left.left.isRed()) {
	    rotateRight(insertedNode);
	}
	if (insertedNode.left.isRed() && !insertedNode.right.isRed()) {
	    reColor(insertedNode);
	}
	return true;
    }

    /**
     * Contains checks whether the value of the parameter is in the tree
     *
     * @param value
     * @return True if inside; false otherwise
     */
    public boolean contains(E value) {
	if (value == null) {
	    return true; // All leaves are null => null is in the tree
	}
	TreeItterator it = new TreeItterator(this);
	while (it.hasNext()) {
	    int isElement = it.next().compareTo(value);
	    if (isElement == 0) {
		return true;
	    }
	}
	return false;
    }

    /**
     * Rotates the tree given as parameter counter clockwise
     *
     * @param insertNode
     */
    private void rotateLeft(RedBlackTree insertNode) {
	RedBlackTree y = insertNode.left;
	insertNode.left = y.right;
	if (y.right != null) {
	    insertNode = y;
	} else if (insertNode == insertNode.parent.left) {
	    insertNode.parent.left = y;
	} else {
	    insertNode.parent.right = y;
	}
	insertNode.right = insertNode;
	insertNode.parent = y;
    }

    private void rotateRight(RedBlackTree insertNode) {
	RedBlackTree y = insertNode.right;
	insertNode.right = y.left;
	if (y.left != null) {
	    insertNode = y;
	} else if (insertNode == insertNode.parent.right) {
	    insertNode.parent.right = y;
	} else {
	    insertNode.parent.left = y;
	}
	insertNode.left = insertNode;
	insertNode.parent = y;

    }

    /**
     * Flips the color of the parent and its two nearest children
     *
     * @param insertedNode
     */
    private void reColor(RedBlackTree insertedNode) {
	insertedNode.setRed(!insertedNode.red);
	insertedNode.left.setRed(!insertedNode.left.red);
	insertedNode.right.setRed(!insertedNode.right.red);
    }

    /**
     * Returns the drawable left tree
     *
     * @return
     */
    public DrawableTreeElement<E> getLeft() {
	return this.left;
    }

    /**
     * Returns the drawable left tree
     *
     * @return
     */
    public DrawableTreeElement<E> getRight() {
	return this.right;
    }

}
