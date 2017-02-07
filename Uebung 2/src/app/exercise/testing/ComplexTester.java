package app.exercise.testing;


import app.exercise.algebra.Complex;

public class ComplexTester {

    public static void main(String[] args) {
        //Creating the objects
        Complex c1 = new Complex();
        Complex c2 = Complex.I;
        Complex c3 = new Complex(3, 2);
        Complex c4 = new Complex(5, -4);
        //Checking Add
        c3.add(c4);
        System.out.println(Complex.add(c3, c4));
        //Cloning
        Complex c5;
        try {
            c5 = c1.clone();
        } catch (CloneNotSupportedException ex) {
            c5 = new Complex(c1);
        }
        //Hash code
        int c1H = c1.hashCode();
        int c5H = c5.hashCode();
        System.out.println(c1H == c5H);
        System.out.println(c4.hashCode());
        //Absolute values
        System.out.println(c4.abs());
        System.out.println(Complex.abs(c2));
        //Multiply
        System.out.println(Complex.multiply(c2, c4));
    }
}
