package com.github.omriz.jcalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class JCalculator {

    // This map holds the calculator created variables
    protected Map<Character, Integer> variables = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Hello from JCalcultor");
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
        return variables;
    }

    public String ProcessAdditionSubtraction(String line) {
        Character[] noOps = {'*', '/', '%'};
        String processedLine = "";
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (Arrays.asList(noOps).contains(it.current())) {
                // We do nothing at this point and reset the state.
                processedLine += leftSide + it.current();
                leftSide = "";
                it.next();
            } else {
                // So this is an actual operation now
                String rightSide = "";
                String operand = String.valueOf(it.current());
                it.next();
                while (it.current() != CharacterIterator.DONE && Character.isDigit(it.current())) {
                    rightSide += String.valueOf(it.current());
                    it.next();
                }
                if (operand.equals("+")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) + Integer.valueOf(rightSide));
                } else if (operand.equals("-")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) - Integer.valueOf(rightSide));
                } else {
                    throw new IllegalArgumentException("Unknown operator: \"" + operand + "\"");
                }
                leftSide = "";
            }
        }
        if (!leftSide.isEmpty()) {
            processedLine += leftSide;
        }
        return processedLine;
    }

    public String ProcessMultiplicationDivision(String line) {
        Character[] noOps = {'+', '-'};
        String processedLine = "";
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (Arrays.asList(noOps).contains(it.current())) {
                // We do nothing at this point and reset the state.
                processedLine += leftSide + it.current();
                leftSide = "";
                it.next();
            } else {
                // So this is an actual operation now
                String rightSide = "";
                String operand = String.valueOf(it.current());
                it.next();
                while (it.current() != CharacterIterator.DONE && Character.isDigit(it.current())) {
                    rightSide += String.valueOf(it.current());
                    it.next();
                }
                if (operand.equals("/")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) / Integer.valueOf(rightSide));
                } else if (operand.equals("*")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) * Integer.valueOf(rightSide));
                } else if (operand.equals("%")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) % Integer.valueOf(rightSide));
                } else {
                    throw new IllegalArgumentException("Unknown operator: \"" + operand + "\"");
                }
                leftSide = "";
            }
        }
        if (!leftSide.isEmpty()) {
            processedLine += leftSide;
        }
        return processedLine;
    }

    // This is the core function in the calculator which processes a given line
    public Integer ProcessLineValue(String line) {
        // Eval - this replaces all vars with their values and
        // increments/decrements in case of ++/--
        String evaluatedLine = Eval(line);

        // Process Parenthesis - each parenthesis will be calculated on it's own
        evaluatedLine = CalculateParenthesis(evaluatedLine);

        // Process Multiplications/Divisions
        evaluatedLine = ProcessMultiplicationDivision(evaluatedLine);

        // Process additions/subtractions
        String finalValue = ProcessAdditionSubtraction(evaluatedLine);

        // Return values
        return Integer.valueOf(finalValue);
    }

    public String CalculateParenthesis(String line) {
        // We'll go over the string while there's still paranthesis. We'll then calculate the values
        // from the inside to the outside.
        String processedLine = line;
        while (processedLine.contains("(")) {
            if (!processedLine.contains(")")) {
                throw new IllegalArgumentException("Unbalanced parentheses in: \"" + line + "\"");
            }
            int startIndex = 0;
            for (int i = 0; i <= processedLine.length(); i++) {
                if (processedLine.charAt(i) == '(') {
                    startIndex = i;
                } else if (processedLine.charAt(i) == ')') {
                    processedLine = processedLine.substring(0, startIndex) +
                            ProcessLineValue(processedLine.substring(startIndex + 1, i)) +
                            processedLine.substring((i + 1));
                    break;
                }
            }
        }
        if (processedLine.contains(")")) {
            throw new IllegalArgumentException("Unbalanced parentheses in: \"" + line + "\"");
        }
        return processedLine;
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
        // Simplifies processing
        String l = line.replaceAll(" ", "");
        String[] splitLine = l.split("=");
        if (splitLine.length != 2) {
            throw new IllegalArgumentException("Line \"" + line + "\" is invalid");
        }
        if (splitLine[0].length() != 1) {
            throw new IllegalArgumentException("\"" + splitLine[0] + "\" is invalid var name");
        }
        variables.put(splitLine[0].charAt(0), ProcessLineValue(splitLine[1]));
    }
}
