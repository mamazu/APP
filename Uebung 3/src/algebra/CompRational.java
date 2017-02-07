package algebra;

public class CompRational<T> extends Rational implements Comparable<T> {

    public CompRational() {}

    /**
     * Constructor with a specified value
     *
     * @param value
     */
    public CompRational(int value) {
        super(value);
    }

    /**
     * Creates a fraction with a certain numerator and dominator
     *
     * @param num The numerator of the faction
     * @param den The denominator of the faction (if the denominator is 0 the
     * fractions value will be 0)
     */
    public CompRational(int num, int den) {
        super(num, den);
    }

    /**
     * Compares the current Rational and the following rational
     * @param comparer
     * @return
     *      1 if other is bigger
     *      0 if they are equal
     *      -1 if the other is smaller
     */
    public int compareTo(Rational comparer) {
        Rational copy;
        try {
            copy = this.clone();
        } catch (CloneNotSupportedException ex) {
            copy = new Rational(0);
        }
        copy.sub(comparer);
        int num = getNumerator();
        if (num < 0) {
            return 1;
        }else if(num > 0){
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) {
        CompRational comp = new CompRational(2, 1);
    }

    public int compareTo(T t) {
        if(t instanceof Rational){
            return compareTo((Rational) t);
        }
        throw new ClassFormatError("Cant compare multiple classes to a Rational");
    }

    public double valueOf() {
	return (double) getNumerator() / getDenominator();
    }

}
