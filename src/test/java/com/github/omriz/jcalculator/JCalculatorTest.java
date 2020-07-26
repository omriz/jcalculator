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
    public void testSimpleAddition() {
        assertEquals(Integer.valueOf(2), jCalculator.ProcessLineValue("1+1"));
    }

    @Test
    public void testSimpleStatement() {
        assertEquals(Integer.valueOf(5), jCalculator.ProcessLineValue("5"));
    }

    @Test
    public void testSimplePostIncrement() {
        jCalculator.ProcessLine("i=1");
        jCalculator.ProcessLine("j=i++");
        Map<Character, Integer> expected = Map.of('i', 2, 'j', 2);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }

    @Test
    public void testSimplePreIncrement() {
        jCalculator.ProcessLine("i=1");
        jCalculator.ProcessLine("j=++i");
        Map<Character, Integer> expected = Map.of('i', 2, 'j', 1);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }

    @Test
    public void testCalculateParenthesis() {
        jCalculator.ProcessLine("i=(1)");
        jCalculator.ProcessLine("j=((++i))");
        Map<Character, Integer> expected = Map.of('i', 2, 'j', 1);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }
}