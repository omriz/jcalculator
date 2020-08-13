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
        StringBuilder processedLine = new StringBuilder();
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (it.current() == '-' && leftSide.equals("")) {
                leftSide = "-";
                it.next();
            }else if (Arrays.asList(noOps).contains(it.current())) {
                // We do nothing at this point and reset the state.
                processedLine.append(leftSide).append(it.current());
                leftSide = "";
                it.next();
            } else {
                // So this is an actual operation now
                String operand = String.valueOf(it.current());
                // Get the first character of the number - it can also be a '-' sign for negatives
                StringBuilder rightSide = new StringBuilder(String.valueOf(it.next()));
                it.next();
                while (it.current() != CharacterIterator.DONE && Character.isDigit(it.current())) {
                    rightSide.append(it.current());
                    it.next();
                }
                switch (operand) {
                    case "/":
                        processedLine.append(Integer.parseInt(leftSide) / Integer.parseInt(rightSide.toString()));
                        break;
                    case "*":
                        processedLine.append(Integer.parseInt(leftSide) * Integer.parseInt(rightSide.toString()));
                        break;
                    case "%":
                        processedLine.append(Integer.parseInt(leftSide) % Integer.parseInt(rightSide.toString()));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: \"" + operand + "\"");
                }
                leftSide = "";
            }
        }
        if (!leftSide.isEmpty()) {
            processedLine.append(leftSide);
        }
        return processedLine.toString();
    }

    private String ProcessAdditionSubtraction(String line) {
        StringBuilder processedLine = new StringBuilder();
        String leftSide = "";
        CharacterIterator it = new StringCharacterIterator(line);
        while (it.current() != CharacterIterator.DONE) {
            if (Character.isDigit(it.current())) {
                // Calculating the number
                leftSide += String.valueOf(it.current());
                it.next();
            } else if (it.current() == '-' && leftSide.equals("")) {
                // We got a negative number
                leftSide = "-";
                it.next();
            } else {
                // So this is an actual operation now
                String operand = String.valueOf(it.current());
                // Get the first character of the number - it can also be a '-' sign for negatives
                StringBuilder rightSide = new StringBuilder(String.valueOf(it.next()));
                it.next();
                while (it.current() != CharacterIterator.DONE && Character.isDigit(it.current())) {
                    rightSide.append(it.current());
                    it.next();
                }
                if (operand.equals("+")) {
                    processedLine.append((Integer.parseInt(leftSide) + Integer.parseInt(rightSide.toString())));
                } else if (operand.equals("-")) {
                    if (leftSide.isEmpty()) {
                        processedLine.append(-1 * Integer.parseInt(rightSide.toString()));
                    } else {
                        processedLine.append((Integer.parseInt(leftSide) - Integer.parseInt(rightSide.toString())));
                    }
                } else {
                    throw new IllegalArgumentException("Unknown operator: \"" + operand + "\"");
                }
                leftSide = "";
            }
        }
        if (!leftSide.isEmpty()) {
            processedLine.append(leftSide);
        }
        return processedLine.toString();
    }
}
