package com.github.omriz.jcalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticProcessorTest {

    ArithmeticProcessor arithmeticProcessor = new ArithmeticProcessor();

    @Test
    public void testAddition() {
        assertEquals("7",arithmeticProcessor.CalculateString("5+2"));
    }
    @Test
    public void testSubtraction() {
        assertEquals("3",arithmeticProcessor.CalculateString("5-2"));
    }
    @Test
    public void testMultiplication() {
        assertEquals("10",arithmeticProcessor.CalculateString("5*2"));
    }
    @Test
    public void testDivision() {
        assertEquals("3",arithmeticProcessor.CalculateString("6/2"));
    }
    @Test
    public void testNegatives() {
        assertEquals("-3",arithmeticProcessor.CalculateString("-6/2"));
        assertEquals("-3",arithmeticProcessor.CalculateString("6/-2"));
        assertEquals("4",arithmeticProcessor.CalculateString("6+-2"));
        assertEquals("-4",arithmeticProcessor.CalculateString("-6+2"));
        assertEquals("-8",arithmeticProcessor.CalculateString("-6-2"));
    }
}