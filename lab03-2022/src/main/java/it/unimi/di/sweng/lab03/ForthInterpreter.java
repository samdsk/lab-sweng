package it.unimi.di.sweng.lab03;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForthInterpreter implements Interpreter{
    Stack<Integer> stack;
    static Pattern customOpPattern = Pattern.compile(":(.*);");
    static Pattern findNumbers = Pattern.compile("[0-9]");

    Map<String,String> customCommands;

    @Override
    public void input(String program){

        stack = new Stack<>();
        customCommands = new HashMap<>();

        if(program.length()<1) return;

        String filtered = program.replaceAll("[\\s]+"," ");

        execInput(collectCustomCommands(filtered));
    }

    private String collectCustomCommands(String str){
        Matcher matcher = customOpPattern.matcher(str);

        if(matcher.find()){
            String[] cmds = matcher.group(1).trim().split(" ",2);
            customCommands.put(cmds[0],cmds[1]);
            str = str.substring(0,matcher.start()) + str.substring(matcher.end());
        }

        return str.replaceAll("[\\s]+"," ");
    }

    private void execInput(String filtered) {
        String[] tokens = filtered.split(" ");

        for(String token : tokens) {
            processToken(token);
        }
    }

    private void processToken(String token) throws IllegalArgumentException{
        if(token.length()<1) return;

        switch (token) {
            case "+", "*", "-", "/", "swap" ->
                binaryOperation(token);

            case "dup","drop" ->
                unaryOperation(token);

            default -> {
                try {
                    if (customCommands.containsKey(token)) {
                        execInput(customCommands.get(token));
                    } else {
                        Matcher numberMatcher = findNumbers.matcher(token);
                        if (numberMatcher.find()) {
                            int number = Integer.parseInt(token);
                            stack.push(number);
                        } else {
                            throw new IllegalArgumentException("Undefined word '" + token + "'");
                        }
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Token error '" + token + "'");
                }

            }
        }
    }

    private void unaryOperation(String op){
        stackUnderflow(1);

        switch (op) {
            case "dup" -> {
                int top = stack.peek();
                stack.push(top);
            }
            case "drop" -> stack.pop();
        }
    }
    private void binaryOperation(String op){
        stackUnderflow(2);

        int x = stack.pop();
        int y = stack.pop();

        switch (op) {
            case "+" -> stack.push(x + y);
            case "-" -> stack.push(y - x);
            case "*" -> stack.push(x * y);
            case "/" -> stack.push(y / x);
            case "swap" -> {
                stack.push(x);
                stack.push(y);
            }
        }

    }

    private void stackUnderflow(int size) {
        if (stack.size() < size) throw new IllegalArgumentException("Stack Underflow");
    }


    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for(Integer token : stack){
            output.append(token);
            output.append(" ");
        }

        output.append("<- Top");

        return output.toString();
    }
}
