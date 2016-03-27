/*
 * Name: Kyomin Ku
 * UPI: kku031
 * ID: 5521999
 * 17.05.2014
 * The mathematical expression will be given in an infix form. 
   This program  print out its postfix expression and the calculated value.
 */

import java.io.*;
import java.util.*;

public class Calculator extends StackReferenceBased {
    
    public void execute(String argument) throws RuntimeException {
        try {
            String infix = argument.trim();
            
            System.out.println("A simple calculator by kku031:");
            System.out.println("Evaluating ...");
            
            boolean mismatch = bracketsCheck(infix);
            boolean divide0 = divisionBy0(infix);
            boolean invalid = invalidInputs(infix);
            boolean missingOperand = checkMissingOperand(infix);
            boolean missingOperator = checkMissingOperator(infix);
            
            if (!mismatch && !divide0 && !invalid && !missingOperand && !missingOperator) {
                String postfix = printPostfixForm(infix);
                printResult(postfix);
            }
            
        } catch (Exception e) {
            System.out.println("ERROR calculating the result.");
        }
    }
    
//////////////////////////         CHECKING SYNTAX ERRORS         ////////////////////////////
    
    private boolean bracketsCheck(String infix) throws StackException {
        StackReferenceBased s = new StackReferenceBased();
        boolean mismatch = false;
        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(') {
                s.push(infix.charAt(i));
            }
            if (infix.charAt(i) == ')') {
                if (!s.isEmpty()) {
                    s.pop();
                } else {
                    mismatch = true;
                    System.out.println("Syntax error: brackets mismatch -> too many closing brackets.");
                }
            }
        }
        if (!s.isEmpty()) {
            mismatch = true;
            System.out.println("Syntax error: brackets mismatch -> too many open brackets.");
        }
        return mismatch;
    }
    
    private boolean divisionBy0(String infix) throws StackException {
        boolean divide0 = false;
        for (int i = 0; i < infix.length() - 1; i++) {
            if (infix.charAt(i) == '/' && infix.charAt(i + 1) == '0') {
                divide0 = true;
            }
        }
        if (divide0) {
            System.out.println("Syntax error: division by 0 -> '/' followed by 0."); 
        }
        return divide0;
    }
    
    private boolean invalidInputs(String infix) throws StackException {
        boolean invalid = false;
        for (int i = 0; i < infix.length(); i++) {
            if (Character.isLetter(infix.charAt(i))) {
                invalid = true;
            }
        }
        if (invalid) {
            System.out.println("Syntax error: invalid inputs -> non-digit character found.");
        }
        return invalid;
    }
    
    private boolean checkMissingOperand(String infix) throws StackException {
        boolean missingOperand = false;
        for (int i = 0; i < infix.length() - 1; i++) {
            if ((infix.charAt(i)) == '(') {
                if (!Character.isDigit(infix.charAt(i + 1)) && (infix.charAt(i + 1)) != '(') {
                    missingOperand = true;
                }
            }
        }
        for (int i = 1; i < infix.length(); i++) {
            if ((infix.charAt(i)) == ')') {
                if (!Character.isDigit(infix.charAt(i - 1))) {
                    missingOperand = true;
                }
            }
        }
        for (int i = 0; i < infix.length() - 1; i++) {
            if (!Character.isDigit(infix.charAt(i)) && (!((infix.charAt(i)) == ')' || (infix.charAt(i)) == '('))) {
                if (!Character.isDigit(infix.charAt(i + 1)) && (!((infix.charAt(i + 1)) == ')' || (infix.charAt(i + 1)) == '('))) {
                    missingOperand = true;
                }
            }
        }
        if (missingOperand) {
            System.out.println("Syntax error: missing operand -> two consecutive operators or '(' followed by operator " +
								"or operator followed by ')'"); 
        }
        return missingOperand;
    }
    
    private boolean checkMissingOperator(String infix) throws StackException {
        boolean missingOperator = false;
        for (int i = 0; i < infix.length() - 1; i++) {
            if (Character.isDigit(infix.charAt(i))) {
                if (infix.charAt(i + 1) == '(') {
                    missingOperator = true;
                }
            }
            if (infix.charAt(i) == ')') {
                if (Character.isDigit(infix.charAt(i + 1)) || infix.charAt(i + 1) == '(') {
                    missingOperator = true;
                }
            }
        }
        if (missingOperator) {
            System.out.println("Syntax error: missing operator -> digit followed by '(' or ')' followed by digit " +
								"or ')' followed by '('");
        }
        return missingOperator;
    }
    
/////////////////////////////               BODY               ///////////////////////////////
    
    private String printPostfixForm(String infix) throws StackException {
        StackReferenceBased s = new StackReferenceBased();
        String postfix = "";
        
        for (int i = 0; i < infix.length(); i++) {
            if (Character.isDigit(infix.charAt(i))) {
                postfix += infix.charAt(i);
                if ((i == infix.length() - 1) || !Character.isDigit(infix.charAt(i + 1))) {
                    postfix += " ";
                }
            }
            if (!Character.isDigit(infix.charAt(i))) {
                if (infix.charAt(i) == '(') {
                    s.push(infix.charAt(i));
                } else if (s.isEmpty()) { 
                    s.push(infix.charAt(i)); 
                } else if (!s.isEmpty()) {
                    if ((infix.charAt(i) == '+') || (infix.charAt(i) == '-')) { 
                        while (!s.isEmpty()) {
                            if ((char)(s.peek()) == '(') {
                                break;
                            }
                            postfix += s.pop();
                            postfix += " ";
                        }
                        s.push(infix.charAt(i));
                    } else if ((infix.charAt(i) == '*') || (infix.charAt(i) == '/')) { 
                        while (!s.isEmpty()) {
                            if (((char)(s.peek()) == '(') || ((char)(s.peek()) == '+') || ((char)(s.peek()) == '-')) {
                                break;
                            }
                            postfix += s.pop();
                            postfix += " ";
                        }
                        s.push(infix.charAt(i));
                    } else if (infix.charAt(i) == ')') { 
                        while ((char)(s.peek()) != '(') {
                            postfix += s.pop();
                            postfix += " ";
                        }
                        s.pop();
                    }
                }
            }
        }
        
        if (!s.isEmpty()) {
            while (!s.isEmpty()) {
                postfix += s.pop();
                postfix += " ";
            }
        } 
        
        System.out.println("Postfix form: " + postfix.trim()); 
        return postfix.trim();
    }
    
    private void printResult(String postfix) throws StackException {
        StackReferenceBased s = new StackReferenceBased();
        outer: 
            for (int i = 0; i < postfix.length(); i++) {
            if (Character.isDigit(postfix.charAt(i))) {
                if (i < postfix.length() - 1) {
                    if (postfix.charAt(i + 1) == ' ') {
                        s.push(postfix.charAt(i));
                    } else {
                        String num = "" + postfix.charAt(i);
                        while (postfix.charAt(i + 1) != ' ') {
                            num += postfix.charAt(i + 1);
                            i++;
                        }
                        s.push(num);
                        continue outer;
                    }
                }
            }
            if (!Character.isDigit(postfix.charAt(i))) {
                if (postfix.charAt(i) == ' ') {
                    continue outer;
                }
                if (postfix.charAt(i) == '+') {
                    double args2 = Double.parseDouble(s.pop() + "");
                    double args1 = Double.parseDouble(s.pop() + "");
                    s.push(args1 + args2);
                } else if (postfix.charAt(i) == '-') {
                    double args2 = Double.parseDouble(s.pop() + "");
                    double args1 = Double.parseDouble(s.pop() + "");
                    s.push(args1 - args2);
                } else if (postfix.charAt(i) == '*') {
                    double args2 = Double.parseDouble(s.pop() + "");
                    double args1 = Double.parseDouble(s.pop() + "");
                    s.push(args1 * args2);
                } else if (postfix.charAt(i) == '/') {
                    double args2 = Double.parseDouble(s.pop() + "");
                    double args1 = Double.parseDouble(s.pop() + "");
                    s.push(args1 / args2);
                }
            }
        }
            
            double result = (Double) s.pop();
            System.out.println("Result: " + result);
    }
    
//////////////////////////         MAIN METHOD         ////////////////////////////    
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            throw new RuntimeException("Usage: java Calculator <args>");
        }
        new Calculator().execute(args[0]);
    }
}