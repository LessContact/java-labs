package ru.nsu.korolev.stackcalculator;

import org.apache.logging.log4j.LogManager;

import java.io.FileWriter;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorContext {
    private final Map<String, Double> parameters;
    private final Stack<Double> stack;
    private final PrintStream outputStream;
    private FileWriter OutputFileWriter;
    private static final String realRegex = "[0-9]+[.]?[0-9]*";

    public CalculatorContext() {
        this.stack = new Stack<>();
        this.parameters = new HashMap<>();
        this.outputStream = System.out;
    }

    public double pushValue(double val) {
        return stack.push(val);
    }

    public double popValue()  {
        if(stack.empty()){
            LogManager.getLogger(CalculatorContext.class).warn("Stack is empty");
            throw new RuntimeException("Stack was empty when asked to pop");
        }
        return stack.pop();
    }

    public int stackSize() {
        return stack.size();
    }

    public boolean isStackEmpty() {
        return stack.isEmpty();
    }

    public double peekValue() {
        if(stack.empty()){
            LogManager.getLogger(CalculatorContext.class).error("Stack is empty");
            throw new RuntimeException("Stack was empty when asked to peek");
        }
        return stack.peek();
    }

    public PrintStream getOutputStream() {
        return outputStream;
    }

    public Double getParameter(String name) throws RuntimeException {
        Double value = parameters.get(name);
        if (value == null) {
            throw new RuntimeException(String.format("%s in an unknown operation", name));
        }

        return value;
    }

    public Double addParameter(String name, Double value) {
//        if(name.matches(realRegex)){
//            LogManager.getLogger(CalculatorContext.class).error("Variable name can not be a number, that doesn't make sense, man");
//            throw new RuntimeException("Variable was attempted to make a name with number");
//        }
        if(parameters.containsKey(name)){
            LogManager.getLogger(CalculatorContext.class).warn("Variable {} was overwritten with {}", name, value);
        }
        return parameters.put(name, value);
    }
}
