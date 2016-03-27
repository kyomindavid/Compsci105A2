/*
 * Name: Kyomin Ku
 * UPI: kku031
 * ID: 5521999
 * 15.05.2014
 * This program outputs the elimination sequence and calculates the safest position from the 
   given number of people and the number of steps taken before the next elimination.
 */

import java.io.*;
import java.util.*;

public class Josephus extends ListReferenceBased {
    
    public void execute(String argument1, String argument2) throws RuntimeException {
        try {
            int numberOfPeople = Integer.parseInt(argument1);
            int steps = Integer.parseInt(argument2);
            
            System.out.println("Josephus problem by kku031:");
            System.out.println("Total number of people: " + numberOfPeople + ",");
            System.out.println("Eliminate every " + steps + "th " + "person.");
            System.out.println("Solution...");
            
            ListReferenceBased list = new ListReferenceBased();
            for (int i = 0; i < numberOfPeople; i++) {
                list.add(i, i+1);
            }
            
            ListReferenceBased remaining = remove(list, numberOfPeople, steps);
            Object safePosition = remaining.get(0);
            
            System.out.println("The safest position in the circle is " + safePosition + "!");
            
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException!\nThe inputs are NOT numbers."); 
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ListIndexOutOfBoundsException!\nNot enough parameter.");
        } catch (Exception e) {
            System.out.println("ERROR calculating the safest position.");
        }
    }
    
    private ListReferenceBased remove(ListReferenceBased list, int numberOfPeople, int steps) throws ListIndexOutOfBoundsException {
        int index = 0;
        for (int i = 0; i < numberOfPeople - 1; i++) {
            index = (index + (steps - 1)) % list.size();
            System.out.println("Remove person at position " + list.get(index));
            list.remove(index);
        } 
        return list;   
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("Usage: java Josephus <numberOfPeople> <steps>");
        }
        new Josephus().execute(args[0], args[1]);
    }
}