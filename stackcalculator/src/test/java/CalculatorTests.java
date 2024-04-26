
import org.junit.jupiter.api.Test;
import ru.nsu.korolev.stackcalculator.StackCalculator;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorTests {

    @Test
    public void testSqrt() {
        String[] args = {"-i", "src/test/resources/test1.txt"};
        var Calculator = new StackCalculator(args);
        Calculator.run();
        assertEquals(2.0, Calculator.context.peekValue(), 0.0001);
    }

    @Test
    public void testMultPlus(){
        String[] args = {"-i", "src/test/resources/test2.txt"};
        var Calculator = new StackCalculator(args);
        Calculator.run();
        assertEquals(6.0, Calculator.context.peekValue(), 0.0001);
    }

    @Test
    public void testDivMinus(){
        String[] args = {"-i", "src/test/resources/test3.txt"};
        var Calculator = new StackCalculator(args);
        Calculator.run();
        assertEquals(1.0, Calculator.context.peekValue(), 0.0001);
    }

    @Test
    public void testPop(){
        String[] args = {"-i", "src/test/resources/test4.txt"};
        var Calculator = new StackCalculator(args);
        Calculator.run();
        assertTrue(Calculator.context.isStackEmpty());
    }
}
