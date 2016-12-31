
/**
 * Lab 19.1 Life
 * 
 * Emily Zhuang
 * Mr. Lantsberger
 * APCS:Period 4
 * 5 December 16
 */     

import apcslib.*;
import chn.util.*;

public class Life
{
    int[][] myDish;          //the current working petri dish
    int totalLiving;         //the total living bacteria at the end of each generation
    
    /**
     * Constructor for objects of class Life. This method instantiates
     * the totalLiving bacteria to zero, but it does not create an array
     * that is assigned to myDish. 
     */
    public Life()
    {
        myDish = null;
        totalLiving = 0;        //begins with zero living
    }
    
    /**
     * This method receives the number of generations the user wants to 
     * advance and also the file in which the information about the 
     * beginning state of the petri dish. 
     */
    public void life(int iterations, FileInput inFile)
    {
        readIn(inFile);         //transfers the information of the file to a 
                                //two-dimensional array
        for (int k = 1; k <= iterations; k++)       //calls checkAround the number of gen
        {
            checkAround();
        }
        printPetri();           //prints the final generation of the petri dish
    }
    
    /**
     * This method converts the information from the text file into a
     * two dimensional array with a gutter that is one unit on each
     * side. 
     */
    private void readIn(FileInput inFile)
    {
        int x, y;   //coordinates of the living bacteria from the file
        
        //allows for a gutter around the array
        int[][] dish = new int[22][22];
        
        //fills the array without the gutters with -1 (dead cells)
        for (int row = 1; row <= 20; row++)
        {
            for (int col = 1; col <= 20; col++)
            {
                dish[row][col] = -1;
            }
        }
        
        //reads in the given total living
        totalLiving = inFile.readInt();
        
        while (inFile.hasMoreTokens())
        {
            x = inFile.readInt();
            y = inFile.readInt();
            
            dish[x][y] = 1; //marks the cells that have living bacteria with 1
        }
        
        //sets dish as an instance variable under myDish
        myDish = dish;
    }
    
    /**
     * This method checks the 3 x 3 mini matrix around the working cell while
     * counting the living cells around it. After finishing checking all the 
     * cells, the method calls live which modifies the original array based on 
     * the information gained in this method. 
     */
    private void checkAround()
    {
        int[][] dishCopy = new int[22][22];
        //neighbors that are living
        int livingNeighbors;
        //whether the working cell is living or dead
        boolean liveWork = false;
        
        //makes the copy of the array
        for (int row = 0; row <= 21; row++)
        {
            for (int col = 0; col <= 21; col++)
            {
                dishCopy[row][col] = myDish[row][col];
            }
        }
        
        //traverses all cells except the gutter
        for (int row = 1; row <= 20; row++)
        {
            for (int col = 1; col <= 20; col++)
            {
                livingNeighbors = 0;    //no living neighbors
                liveWork = false;       //working cell is dead
                //traverses the mini 3x3 array that is currently being worked with
                for (int r = row - 1; r <= row + 1; r++)
                {
                    for (int c = col - 1; c <= col + 1; c++)
                    {
                        //if it is the center of the 3 x 3 matrix
                        if (r == row && c == col && dishCopy[r][c] == 1)
                            liveWork = true;    //if the working cell is alive
                        else 
                        {
                            if (dishCopy[r][c] == 1)    //if the surrounding cells are alive
                                livingNeighbors++;      //increment living neighbors
                        }
                    }
                }
                
                //calls live which modifies the original array
                live(livingNeighbors, liveWork, row, col);
            }
        }
    }
    
    /**
     * This method modifies the original array based on the information that 
     * is being received. The number of bacteria cells will change based on 
     * the generation. 
     */
    private void live(int livingNeighbors, boolean liveWork, int row, int col)
    {
        //if there are three living neighbors and the cell is dead
        if (livingNeighbors == 3 && !liveWork)
        {
            myDish[row][col] = 1;       //a new bacteria cell grows
        }
        else
        {
            //if the cell is alive and there are 0 or 1 living neighbors
            if (liveWork && (livingNeighbors == 0 || livingNeighbors == 1))
            {
                myDish[row][col] = -1;      //cell dies
            }
            else
            {
                //if the cell is alive and there are 4 or more living neighbors
                if (liveWork && livingNeighbors >= 4)
                {
                    myDish[row][col] = -1;  //cell dies
                }
            }
        }
    }
    
    /**
     * This method prints the petri dish using stars to represent the 
     * living bacteria cells. It also prints the total living cells 
     * as well as the living cells in row 10 and the living cells in 
     * column 10. 
     */
    private void printPetri()
    {
        int rowTen, colTen, checkedLiving;
        rowTen = 0;
        colTen = 0;
        checkedLiving = 0;
        
        //The top axis that numbers the columns
        System.out.println("  \t12345678901234567890");
        System.out.println();
        
        for (int row = 1; row <= 20; row++)
        {
            //prints the number of the rows and an indentation
            System.out.print(Format.right(row, 2) + "\t");
            
            for (int col = 0; col <= 20; col++)
            {
                //prints a blank space if it is dead
                if (myDish[row][col] == -1)
                    System.out.print(" ");
                //prints a star if cell is alive
                if (myDish[row][col] == 1)
                {
                    System.out.print("*");
                    
                    //keeps running total of all the alive cells
                    checkedLiving++;
                    
                    //keeps running total of the alive cells in row 10
                    if (row == 10)
                        rowTen++;
                    //keeps running total of the alive cells in column 10
                    if (col == 10)
                        colTen++;
                }    
            }
            System.out.println();
        }
        
        //updates the total living
        totalLiving = checkedLiving;
        
        //prints how many are living total, in row 10, and in column 10
        System.out.println("Number in Row 10 ---> " + rowTen + "\n");
        System.out.println("Number in Column 10 ---> " + colTen + "\n");
        System.out.println("Number of living organisms ---> " + totalLiving + "\n");
    }
}
