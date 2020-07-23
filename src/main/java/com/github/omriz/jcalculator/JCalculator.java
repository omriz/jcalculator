package com.github.omriz.jcalculator;

import java.util.HashMap;
import java.util.Map;

public class JCalculator {

    // This map holds the calculator created variables
    protected Map<Character, Integer> variables = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Hello from JCalcultor");
    }

    public Map<Character, Integer> getVariables() {
        return variables;
    }

    // This is the core function in the calculator which processes a given line
    public Integer ProcessLineValue(String line) {
        // Eval - this replaces all vars with their values and
        // increments/decrements in case of ++/--
        String evaluatedLine = Eval(line);

        // Process Parenthesis - each parenthesis will be calculated on it's own
        evaluatedLine = CalculateParenthesis(evaluatedLine);

        // Process Multiplications/Divisions

        // Process additions/subtractions
        String finalValue = evaluatedLine;

        // Return values
        return Integer.valueOf(finalValue);
    }

    public String CalculateParenthesis(String line) {
        // We'll go over the string while there's still paranthesis. We'll then calculate the values
        // from the inside to the outside.
        String processedLine = line;
        while (processedLine.contains("(")) {
            int startIndex = 0;
            for (int i = 0; i <= processedLine.length(); i++) {
                if (processedLine.charAt(i) == '(') {
                    startIndex = i;
                } else if (processedLine.charAt(i) == ')') {
                    processedLine = processedLine.substring(0, startIndex) +
                            ProcessLineValue(processedLine.substring(startIndex, i)) +
                            processedLine.substring((i + 1));
                    break;
                }
            }
        }
        return processedLine
    }

    public String Eval(String line) {
        String processedLine = line;
        while (processedLine.contains("--") || processedLine.contains("++")) {
            int pIndex = processedLine.indexOf("++");
            if (pIndex != -1) {
                if (pIndex != 0 && Character.isLetter(processedLine.charAt(pIndex - 1))) {
                    Integer v = variables.get(processedLine.charAt(pIndex - 1)) + 1;
                    variables.put(processedLine.charAt(pIndex - 1), v);
                    processedLine = processedLine.substring(0, pIndex - 1) + String.valueOf(v) + processedLine.substring(pIndex + 2);
                }
                if (pIndex != processedLine.length() && Character.isLetter(processedLine.charAt(pIndex + 2))) {
                    Integer v = variables.get(processedLine.charAt(pIndex + 2));
                    variables.put(processedLine.charAt(pIndex + 2), v + 1);
                    processedLine = processedLine.substring(0, pIndex) + String.valueOf(v) + processedLine.substring(pIndex + 3);
                }
            }
            int nIndex = processedLine.indexOf("--");
            if (nIndex != -1) {
                if (nIndex != 0 && Character.isLetter(processedLine.charAt(nIndex - 1))) {
                    Integer v = variables.get(processedLine.charAt(nIndex - 1)) - 1;
                    variables.put(processedLine.charAt(nIndex - 1), v);
                    processedLine = processedLine.substring(0, nIndex - 1) + String.valueOf(v) + processedLine.substring(nIndex + 3);
                }
                if (nIndex != processedLine.length() && Character.isLetter(processedLine.charAt(nIndex + 2))) {
                    Integer v = variables.get(processedLine.charAt(nIndex + 2));
                    variables.put(processedLine.charAt(nIndex + 2), v - 1);
                    processedLine = processedLine.substring(0, nIndex) + String.valueOf(v) + processedLine.substring(nIndex + 4);
                }
            }
        }
        for (int i = 0; i < processedLine.length(); i++) {
            if (Character.isLetter(processedLine.charAt(i))) {
                processedLine = processedLine.substring(0, i) + variables.get(processedLine.charAt(i)) + processedLine.substring(i + 1);
            }
        }
        return processedLine;
    }

    public void ProcessLine(String line) {
        String[] splitLine = line.split("=");
        if (splitLine.length != 2) {
            throw new IllegalArgumentException("Line \"" + line + "\" is invalid");
        }
        if (splitLine[0].length() != 1) {
            throw new IllegalArgumentException("\"" + splitLine[0] + "\" is invalid var name");
        }
        variables.put(splitLine[0].charAt(0), ProcessLineValue(splitLine[1]));
    }
}
