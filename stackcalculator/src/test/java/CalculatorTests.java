
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.nsu.korolev.stackcalculator.CalculatorContext;
import ru.nsu.korolev.stackcalculator.StackCalculator;
import ru.nsu.korolev.stackcalculator.commands.CommandParser;
import ru.nsu.korolev.stackcalculator.commands.exceptions.CommandExecuteException;
import ru.nsu.korolev.stackcalculator.commands.exceptions.WrongArgsException;
import ru.nsu.korolev.stackcalculator.commands.implementation.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CalculatorTests {

    @Test
    public void fullFileInputTest() {
        String[] args = {"-i", "src/test/resources/test1.txt"};
        StackCalculator stackCalculator = new StackCalculator(args);
        stackCalculator.run();
    }

    @Test
    public void testSqrtNormal(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(4.0);
        when(contextMock.stackSize()).thenReturn(1);
        sqrt sqrt = new sqrt();
        sqrt.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(2.0, captor.getValue());
    }
    @Test
    public void testSqrtNegative(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(-4.0);
        when(contextMock.stackSize()).thenReturn(1);
        sqrt sqrt = new sqrt();
        assertThrows(CommandExecuteException.class, () -> sqrt.execute(contextMock));
    }
    @Test
    public void testSqrtEmpty(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.stackSize()).thenReturn(0);
        sqrt sqrt = new sqrt();
        assertThrows(WrongArgsException.class, () -> sqrt.execute(contextMock));
    }
    @Test
    public void testMult(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(4.0).thenReturn(6.0);
        when(contextMock.stackSize()).thenReturn(2);
        multiply m = new multiply();
        m.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(24.0, captor.getValue());
    }
    @Test
    public void testMultEmpty(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.stackSize()).thenReturn(1);
        multiply m = new multiply();
        assertThrows(WrongArgsException.class, () -> m.execute(contextMock));
    }
    @Test
    public void testAdd(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(4.0).thenReturn(6.0);
        when(contextMock.stackSize()).thenReturn(2);
        plus p = new plus();
        p.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(10.0, captor.getValue());
    }
    @Test
    public void testAddEmpty(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.stackSize()).thenReturn(1);
        plus p = new plus();
        assertThrows(WrongArgsException.class, () -> p.execute(contextMock));
    }
    @Test
    public void testSubtract(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(6.0).thenReturn(4.0);
        when(contextMock.stackSize()).thenReturn(2);
        subtract s = new subtract();
        s.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(2.0, captor.getValue());
    }
    @Test
    public void testSubtractEmpty(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.stackSize()).thenReturn(1);
        subtract s = new subtract();
        assertThrows(WrongArgsException.class, () -> s.execute(contextMock));
    }
    @Test
    public void testDivide(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(6.0).thenReturn(4.0);
        when(contextMock.stackSize()).thenReturn(2);
        divide d = new divide();
        d.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(1.5, captor.getValue());
    }
    @Test
    public void testDivideByZero(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.popValue()).thenReturn(6.0).thenReturn(0.0);
        when(contextMock.stackSize()).thenReturn(2);
        divide d = new divide();
        assertThrows(CommandExecuteException.class, () -> d.execute(contextMock));
    }
    @Test
    public void testDivideEmpty(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.stackSize()).thenReturn(1);
        divide d = new divide();
        assertThrows(WrongArgsException.class, () -> d.execute(contextMock));
    }
    @Test
    public void testPush(){
        CommandParser.Data data = new CommandParser.Data("push", new String[]{"a"}, "push a 10", 1);
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.getParameter(any(String.class))).thenReturn(10.0);
        push p = new push(data);
        p.execute(contextMock);
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);
        verify(contextMock).pushValue(captor.capture());
        assertEquals(10.0, captor.getValue());
    }
    @Test
    public void testPushNoArgs(){
        CommandParser.Data data = new CommandParser.Data("push", new String[]{}, "push a", 1);
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.getParameter(any(String.class))).thenReturn(10.0);
        assertThrows(WrongArgsException.class, () -> new push(data));
    }
    @Test
    public void testPop() {
       CalculatorContext contextMock = mock(CalculatorContext.class);
       when(contextMock.popValue()).thenReturn(6.0);
       pop p = new pop();
       p.execute(contextMock);
       verify(contextMock, times(1)).popValue();
    }
    @Test
    public void testDefine(){
        CalculatorContext contextMock = mock(CalculatorContext.class);
        CommandParser.Data data = new CommandParser.Data("define", new String[]{"a", "10"}, "define a 10", 1);
        when(contextMock.addParameter(any(String.class), any(Double.class))).thenReturn(null);
        define p = new define(data);
        p.execute(contextMock);
        verify(contextMock, times(1)).addParameter(any(String.class), any(Double.class));
    }
    @Test
    public void testPrint() {
        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.getOutputStream()).thenReturn(System.out);
        when(contextMock.isStackEmpty()).thenReturn(false);
        when(contextMock.peekValue()).thenReturn(10.0);
        print p = new print();
        p.execute(contextMock);
        verify(contextMock, times(1)).peekValue();
        verify(contextMock, times(1)).getOutputStream();
        verify(contextMock, times(1)).isStackEmpty();
    }
}
