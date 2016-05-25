package RBT;

public class RedBlackTree<E extends Comparable> extends BinaryTree<E> {

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

    @Override
    public boolean add(E value){
	//If node already in tree return false
	return super.add(value);
    }

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

}
