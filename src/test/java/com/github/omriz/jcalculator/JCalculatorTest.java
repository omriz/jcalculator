package com.github.omriz.jcalculator;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JCalculatorTest {
    JCalculator jCalculator;

    @Before
    public void setupJCalculator() {
        jCalculator = new JCalculator();
    }

    @Test
    public void testSimpleStatement() {
        assertEquals(Integer.valueOf(5), jCalculator.ProcessLineValue("5"));
    }


    @Test
    public void testCalculateParenthesis() {
        jCalculator.ProcessLine("i=(1)");
        jCalculator.ProcessLine("j=((++i))");
        Map<Character, Integer> expected = Map.of('i', 2, 'j', 2);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }

    @Test
    public void testSimpleDivision() {
        jCalculator.ProcessLine("i=8");
        jCalculator.ProcessLine("j=4");
        jCalculator.ProcessLine("k = i / j");
        Map<Character, Integer> expected = Map.of('i', 8, 'j', 4, 'k', 2);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }

    @Test
    public void testSimpleAddition() {
        jCalculator.ProcessLine("i=8");
        jCalculator.ProcessLine("j=4");
        jCalculator.ProcessLine("k = i + j");
        Map<Character, Integer> expected = Map.of('i', 8, 'j', 4, 'k', 12);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }
}