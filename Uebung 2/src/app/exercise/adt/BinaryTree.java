package app.exercise.adt;

public class BinaryTree<E extends Comparable> {

    private BinaryTree<E> left;
    private BinaryTree<E> right;
    private E value;

    /**
     * Default constructor
     */
    public BinaryTree() {
    }

    /**
     * Constructor with the value of the root element
     *
     * @param root
     */
    public BinaryTree(E root) {
        value = root;
        left = null;
        right = null;
    }

    /**
     * Sets the value of the node
     *
     * @param value
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Sets the value of the left Branch
     *
     * @param left
     */
    public void setLeft(BinaryTree<E> left) {
        this.left = left;
    }

    /**
     * Sets the value of the right branch
     *
     * @param right
     */
    public void setRight(BinaryTree<E> right) {
        this.right = right;
    }

    public void insert(E element) throws RuntimeException{
        int comp = element.compareTo(value);
        if(comp == 0){
            throw new RuntimeException("Already stored the binary tree");
        }
        if(comp < 0){
            if(left == null)
                left = new BinaryTree<E>(element);
            else
                left.insert(element);
        }else{
            if(right == null)
                right = new BinaryTree<E>(element);
            else
                right.insert(element);
        }
    }

    //Magic methods
    @Override
    public String toString() {
        String s = "";
        if (left != null) {
            s = left.toString();
        }
        s += value;
        if (right != null) {
            s += right.toString();
        }
        return s;
    }
}
