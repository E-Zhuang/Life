
/**
 * Lab 19.1 Life
 * 
 * Emily Zhuang
 * Mr. Lantsberger
 * APCS:Period 4
 * 5 December 16
 */     

import chn.util.*;

public class LifeTester
{
    /**
     * Here is the main() method. This method will simulate a petri dish
     * full of bacteria and will advance the given number of generations. 
     */
    public static void main(String[] args)
    {
        FileInput inFile = new FileInput("life100.txt");
        ConsoleIO keyboard = new ConsoleIO();
        Life petriDish = new Life ();
        int iterations;     //how many times the user wants to advance
        
        //prompts the user for the number of generations
        System.out.print("How many generations of life would you like to " +
                         "advance forward? ");
        iterations = keyboard.readInt();  
        System.out.println();
        //calls life() to advance and print the generations
        petriDish.life(iterations, inFile);
    }
}
