package algebra;

public class Rational{

    private int numerator;
    private int denominator;

    /**
     * Default constructor (value 0)
     */
    public Rational() {
        numerator = 0;
        denominator = 1;
    }

    /**
     * Constructor with a specified value
     *
     * @param value
     */
    public Rational(int value) {
        numerator = value;
        denominator = 1;
    }

    /**
     * Creates a fraction with a certain numerator and dominator
     *
     * @param num The numerator of the faction
     * @param den The denominator of the faction (if the denominator is 0 the
     * fractions value will be 0)
     */
    public Rational(int num, int den) {
        if (den == 0) {
            numerator = 0;
            denominator = 1;
            System.err.println("Creating fractions with denominator 0");
            throw new ArithmeticException("Fraction with zero as a denominator");
        } else {
            numerator = num;
            denominator = den;
            reduce();
        }
    }

    /**
     * Reduced the fraction to the normalized form and operates in-place
     */
    private void reduce() {
        byte sign = (byte) (Math.signum(numerator) * Math.signum(denominator));
        int gcd = Rational.gcd(Math.abs(numerator), Math.abs(denominator));
        numerator = Math.abs(numerator) / gcd * sign;
        denominator = Math.abs(denominator) / gcd;
    }

    /**
     * Getter for numerator
     * @return numerator
     */
    public int getNumerator() {
        return numerator;
    }
    
    /**
     * Getter for denominator
     * @return denominator
     */
    public int getDenominator() {
        return denominator;
    }
    
    /**
     * Calculates the Greatest Common Divisor
     *
     * @param a First number
     * @param b Second number
     * @return The GCD and one of they don't share a common divisor
     */
    public static int gcd(int a, int b) {
        if (a == 0) {
            return b;
        }
        return gcd(b % a, a);
    }

    //Implementation of Arithmetic
    /**
     * Adds another rational number to the current one in-place
     *
     * @param operand
     */
    public void add(Rational operand) {
        numerator = numerator*operand.denominator + denominator * operand.numerator;
        denominator *= operand.denominator;
        reduce();
    }

    /**
     * Subtracts another rational number from the current one in-place
     *
     * @param operand
     */
    public void sub(Rational operand) {
        add(new Rational(-operand.numerator, operand.denominator));
    }

    /**
     * Multiplies another rational number to the current one in-place
     *
     * @param operand
     */
    public void mul(Rational operand) {
        numerator *= operand.numerator;
        denominator *= operand.denominator;
        reduce();
    }

    /**
     * Divides the current number in-place by another number (multiplies with reciprical)
     * @param operand
     */
    public void div(Rational operand) {
        mul(new Rational(operand.denominator, operand.numerator));
    }

    //Magic methods
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    @Override
    public Rational clone() throws CloneNotSupportedException {
        super.clone();
        return new Rational(numerator, denominator);
    }

    @Override
    public boolean equals(Object r) {
        if (r instanceof Rational) {
            return hashCode() == r.hashCode();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.numerator;
        hash = 59 * hash + this.denominator;
        return hash;
    }

}
