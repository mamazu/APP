package app.exercise.algebra;

public class Complex {

    //Static vars

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex I = new Complex(0, 1);

    //Properties
    private double real;
    private double img;

    //Default comstructor
    public Complex() {
        real = 0;
        img = 0;
    }

    public Complex(double real, double img) {
        this.real = real;
        this.img = img;
    }

    //Copy constructor
    public Complex(Complex c) {
        this.real = c.real;
        this.img = c.img;
    }

    //Getter and Setter
    public double getReal() {
        return real;
    }

    public double getImg() {
        return img;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public void setImg(double img) {
        this.img = img;
    }

    // Adding: with new Object
    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.real + c2.real, c1.img + c2.img);
    }

    // Adding inplace
    public void add(Complex c1) {
        this.real += c1.real;
        this.img += c1.img;
    }

    //Multiplying: new Object
    public static Complex multiply(Complex c1, Complex c2) {
        return new Complex(c1.real * c2.real - c1.img * c2.img, c1.img * c2.real + c1.real * c2.img);
    }

    //Multiply inplace
    public void multiply(Complex c1) {
        real = real * c1.real - img * c1.img;
        img = img * c1.real + real * c1.img;
    }

    // Abs: new Object
    public static double abs(Complex c1) {
        return Math.sqrt(Math.pow(c1.real, 2) + Math.pow(c1.img, 2));
    }

    //Abs: in place
    public double abs() {
        return Complex.abs(this);
    }

	//Magic methods
    //Equals
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Complex) {
            Complex c = (Complex) obj;
            return this.real == c.real && this.img == c.img;
        }
        return false;
    }

    //Clone
    @Override
    public Complex clone() throws CloneNotSupportedException {
        super.clone();
        return new Complex(this);
    }

    //Hash
    @Override
    public int hashCode() {
        int realHash = (int) real;
        int imgHash = (int) img;
        return realHash ^ imgHash;
    }

    //toString
    @Override
    public String toString() {
        if (img == 0) {
            return String.valueOf(real);
        }
        if (real == 0) {
            return String.valueOf(img) + "i";
        }
        char sign = (img < 0) ? '-' : '+';
        return real + String.valueOf(sign) + Math.abs(img) + "i";
    }

    //STATIC METHODS
    public boolean equals(Complex c1, Complex c2) {
        return c1.equals(c2);
    }

    public int hashCode(Complex c1) {
        return c1.hashCode();
    }

}
