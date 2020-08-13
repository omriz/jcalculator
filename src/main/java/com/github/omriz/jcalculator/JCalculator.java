package com.github.omriz.jcalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class JCalculator {

    // This map holds the calculator created variables
    VariableStore variableStore = new VariableStore();
    ArithmeticProcessor arithmeticProcessor = new ArithmeticProcessor();

    public static void main(String[] args) {
        System.out.println("Hello from JCalculator");
        JCalculator jCalculator = new JCalculator();
        if (args.length > 0) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(args[0]));
                String line = reader.readLine();
                while (line != null) {
                    jCalculator.ProcessLine(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Scanner input = new Scanner(System.in);
            while (input.hasNextLine()) {
                jCalculator.ProcessLine(input.nextLine());
            }
        }
        System.out.println(jCalculator.getVariables());
    }

    public Map<Character, Integer> getVariables() {
        return variableStore.getVariables();
    }

    // This is the core function in the calculator which processes a given line
    public Integer ProcessLineValue(String line) {
        // Eval - this replaces all vars with their values and
        // increments/decrements in case of ++/--
        String evaluatedLine = variableStore.Eval(line);

        // Return values
        return Integer.valueOf(arithmeticProcessor.CalculateString(evaluatedLine));
    }

    public void ProcessLine(String line) {
        // Simplifies processing
        String l = line.replaceAll(" ", "");
        if (l.isEmpty() || l.startsWith("//") || l.startsWith("#")) {
            return;
        }
        String[] splitLine = l.split("=");
        if (splitLine.length != 2) {
            throw new IllegalArgumentException("Line \"" + line + "\" is invalid");
        }
        if (splitLine[0].length() != 1) {
            throw new IllegalArgumentException("\"" + splitLine[0] + "\" is invalid var name");
        }
        variableStore.put(splitLine[0].charAt(0), ProcessLineValue(splitLine[1]));
    }
}
