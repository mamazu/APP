package app.exercise.testing;

import app.exercise.algebra.Rational;
import java.util.Stack;

public class RPN {

    private Stack<Rational> operatorStack;
    private Rational value;

    /**
     * Default empty constructor
     */
    public RPN() {
    }

    /**
     * Constructor that evaluates the sequence
     *
     * @param operators List of individual expressions
     */
    public RPN(String[] operators) {
	operatorStack = new Stack<Rational>();
	eval(operators);
	value = operatorStack.get(0);
    }

    /**
     * Evaluates the sequence and adds number to the stack or operation
     *
     * @param ops
     * @throws IllegalArgumentException
     */
    private void eval(String[] ops) throws IllegalArgumentException {
	for (String op : ops) {
	    try {
		operatorStack.push(new Rational(Integer.parseInt(op)));
		continue;
	    } catch (NumberFormatException e) {
		char operatorSign = op.charAt(0);
		switch (operatorSign) {
		    case '+':
			add();
			continue;
		    case '-':
			subtract();
			continue;
		    case '*':
			multiply();
			continue;
		    case '/':
			divide();
			continue;
		}
	    }
	    throw new IllegalArgumentException("Can't handle the argument: " + op);
	}
    }

    private Rational[] stackCheck() throws IllegalArgumentException {
	if (operatorStack.size() < 2) {
	    throw new IllegalArgumentException("Not enough operators on the stack to add");
	}
	Rational[] result = new Rational[2];
	result[1] = operatorStack.pop();
	result[0] = operatorStack.peek();
	return result;
    }

    /**
     * Adds the last two elements on the stack
     *
     * @throws IllegalArgumentException
     */
    private void add() throws IllegalArgumentException {
	Rational[] op = stackCheck();
	op[0].add(op[1]);
    }

    /**
     * Subtracts the last two elements on the stack
     *
     * @throws IllegalArgumentException
     */
    private void subtract() throws IllegalArgumentException {
	Rational[] op = stackCheck();
	op[0].sub(op[1]);
    }

    /**
     * Multiplies the last two elements on the stack
     *
     * @throws IllegalArgumentException
     */
    private void multiply() throws IllegalArgumentException {
	Rational[] op = stackCheck();
	op[0].mul(op[1]);
    }

    /**
     * Divides the last two elements on the stack
     *
     * @throws IllegalArgumentException
     */
    private void divide() throws IllegalArgumentException {
	Rational[] op = stackCheck();
	op[0].div(op[1]);
    }

    /**
     * Returns the value of the evaluated sequence
     *
     * @return Final value of the RPN sequence (null if not defined)
     */
    public Rational getValue() {
	return value;
    }

    /**
     * Main method
     *
     * @param args Elements of the sequence
     */
    public static void main(String[] args) {
	RPN rpnModel = new RPN(args);
	System.out.println(rpnModel.getValue());
    }
}
