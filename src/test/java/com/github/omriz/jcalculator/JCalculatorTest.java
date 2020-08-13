package com.github.omriz.jcalculator;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public void testE2E() {
        String resourceName = "calculation.txt";
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(classLoader.getResource(resourceName).getFile()));
            String line = reader.readLine();
            while (line != null) {
                jCalculator.ProcessLine(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Character, Integer> expected = Map.of('i', 8, 'j',4,'k',9, 'f',1);
        assertTrue(expected.equals(jCalculator.getVariables()));
    }
}