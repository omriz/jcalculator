package com.github.omriz.jcalculator;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VariableStoreTest {

    VariableStore variableStore;

    @Before
    public void setUp() throws Exception {
        variableStore = new VariableStore();
    }

    @Test
    public void testSimplePostIncrement() {
        variableStore.put('i', 1);
        String evaluatedLine = variableStore.Eval("i++");
        Map<Character, Integer> expected = Map.of('i', 2);
        assertTrue(expected.equals(variableStore.getVariables()));
        assertEquals("1", evaluatedLine);
    }

    @Test
    public void testSimplePreIncrement() {
        variableStore.put('i', 1);
        String evaluatedLine = variableStore.Eval("++i");
        Map<Character, Integer> expected = Map.of('i', 2);
        assertTrue(expected.equals(variableStore.getVariables()));
        assertEquals("2", evaluatedLine);
    }

    @Test
    public void testEval() {
        variableStore.put('i', 1);
        variableStore.put('j', 5);
        String evaluatedLine = variableStore.Eval("i+6-j");
        assertEquals("1+6-5", evaluatedLine);
    }


}