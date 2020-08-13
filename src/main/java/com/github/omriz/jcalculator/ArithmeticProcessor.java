package com.github.omriz.jcalculator;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;

public class ArithmeticProcessor {
    public String CalculateString(String line) {

        // Process Parenthesis - each parenthesis will be calculated on it's own
        String evaluatedLine = CalculateParenthesis(line);

        // Process Multiplications/Divisions
        evaluatedLine = ProcessMultiplicationDivision(evaluatedLine);

        // Process additions/subtractions
        return ProcessAdditionSubtraction(evaluatedLine);
    }

    private String CalculateParenthesis(String line) {
        // We'll go over the string while there's still parenthesis. We'll then calculate the values
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
                            CalculateString(processedLine.substring(startIndex + 1, i)) +
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

    private String ProcessMultiplicationDivision(String line) {
        Character[] noOps = {'+', '-'};
        String processedLine = "";
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (it.current() == '-' && leftSide == "") {
                leftSide = "-";
                it.next();
            }else if (Arrays.asList(noOps).contains(it.current())) {
                // We do nothing at this point and reset the state.
                processedLine += leftSide + it.current();
                leftSide = "";
                it.next();
            } else {
                // So this is an actual operation now
                String operand = String.valueOf(it.current());
                // Get the first character of the number - it can also be a '-' sign for negatives
                String rightSide = String.valueOf(it.next());
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

    private String ProcessAdditionSubtraction(String line) {
        String processedLine = "";
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (it.current() == '-' && leftSide == "") {
                // We got a negative number
                leftSide = "-";
                it.next();
            } else {
                // So this is an actual operation now
                String operand = String.valueOf(it.current());
                // Get the first character of the number - it can also be a '-' sign for negatives
                String rightSide = String.valueOf(it.next());
                it.next();
                while (it.current() != CharacterIterator.DONE && Character.isDigit(it.current())) {
                    rightSide += String.valueOf(it.current());
                    it.next();
                }
                if (operand.equals("+")) {
                    processedLine += String.valueOf(Integer.valueOf(leftSide) + Integer.valueOf(rightSide));
                } else if (operand.equals("-")) {
                    if (leftSide.isEmpty()) {
                        processedLine += String.valueOf(-1 * Integer.valueOf(rightSide));
                    } else {
                        processedLine += String.valueOf(Integer.valueOf(leftSide) - Integer.valueOf(rightSide));
                    }
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
}
