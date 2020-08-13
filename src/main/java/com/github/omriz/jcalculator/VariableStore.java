package com.github.omriz.jcalculator;

import java.util.HashMap;
import java.util.Map;

public class VariableStore {
    private final Map<Character, Integer> variables = new HashMap<>();

    public Map<Character, Integer> getVariables() {
        return variables;
    }

    public void put(Character key, Integer value) {
        variables.put(key, value);
    }

    public String Eval(String line) {
        String processedLine = line;
        while (processedLine.contains("--") || processedLine.contains("++")) {
            int pIndex = processedLine.indexOf("++");
            if (pIndex != -1) {
                if (pIndex != 0 && Character.isLetter(processedLine.charAt(pIndex - 1))) {
                    Integer v = variables.get(processedLine.charAt(pIndex - 1));
                    variables.put(processedLine.charAt(pIndex - 1), v + 1);
                    processedLine = processedLine.substring(0, pIndex - 1) + v + processedLine.substring(pIndex + 2);
                }
                if (pIndex != processedLine.length() && Character.isLetter(processedLine.charAt(pIndex + 2))) {
                    Integer v = variables.get(processedLine.charAt(pIndex + 2)) + 1;
                    variables.put(processedLine.charAt(pIndex + 2), v);
                    processedLine = processedLine.substring(0, pIndex) + v + processedLine.substring(pIndex + 3);
                }
            }
            int nIndex = processedLine.indexOf("--");
            if (nIndex != -1) {
                if (nIndex != 0 && Character.isLetter(processedLine.charAt(nIndex - 1))) {
                    Integer v = variables.get(processedLine.charAt(nIndex - 1));
                    variables.put(processedLine.charAt(nIndex - 1), v - 1);
                    processedLine = processedLine.substring(0, nIndex - 1) + v + processedLine.substring(nIndex + 3);
                }
                if (nIndex != processedLine.length() && Character.isLetter(processedLine.charAt(nIndex + 2))) {
                    Integer v = variables.get(processedLine.charAt(nIndex + 2)) - 1;
                    variables.put(processedLine.charAt(nIndex + 2), v);
                    processedLine = processedLine.substring(0, nIndex) + v + processedLine.substring(nIndex + 4);
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
}
