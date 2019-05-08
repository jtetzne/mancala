/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dvoth
 */
public class GameCommunication {
    public static void updateGameBoard(Pit[] pits, Player currentPlayer, mancala m)
    {
        String fname = "gameboard.txt";
        File dataOut = new File(fname);
        try {
            FileWriter fw = new FileWriter(dataOut,false);
            fw.write(currentPlayer.num + "\n");
            for (Pit pit : pits) {
                fw.write(pit.numStones + "\n");
            }
            fw.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void initMoveFile() {
        String fname = "move.txt";
        File dataOut = new File(fname);
        try {
            FileWriter fw = new FileWriter(dataOut,false);
            fw.write(-1 + "\n");
            fw.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static int getAIMove()
    {
        Scanner lineScan;
        int move = -1;
        String fName = "move.txt";
        String line;
        
        try {
            lineScan = new Scanner(new File(fName));
            // Read through lines of file
            if (lineScan.hasNextLine()){
                line = lineScan.nextLine();
                // Make sure we didn't just get whitespace
                if (line.trim().length() > 0)
                    move = Integer.parseInt(line);
            } else {
                System.out.println("No AI move selected");
            }
            
            lineScan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
//        clearMoveFile();
        
        return move;
    }
    
    public static int firstLegalMove() {
        Scanner lineScan;
        String gameboardFileName = System.getProperty("user.dir") + "/gameboard.txt";
        int currentPlayer = -1;
        int move = -1;
        File file = new File(gameboardFileName);
        int pitNumber = -1;
        writeToConsole(file.getPath() + "\n");
        
        try {
            lineScan = new Scanner(file);
            // Read through lines of file
            if (lineScan.hasNextLine()){
               currentPlayer = Integer.parseInt(lineScan.nextLine());
               writeToConsole("FROM AI: Current Player: " + currentPlayer + "\n");
            }
            
            while (lineScan.hasNextLine()) {
                pitNumber++;
                int stonesInPit = Integer.parseInt(lineScan.nextLine());
                writeToConsole("Stones in pit " + pitNumber + ": " + stonesInPit + "\n");
                // Don't choose an empty pit
                if (stonesInPit == 0)
                {
                    writeToConsole("FROM AI: Pit " + pitNumber + " is an Empty Pit\n");
                    continue;
                }
                // Don't choose other player's pit
                else if(currentPlayer == 0 && (pitNumber>6 || pitNumber <1))
                {
                    writeToConsole("FROM AI: Pit " + pitNumber  + " is illegal for " + currentPlayer + " \n");
                    continue;
                }
                // Don't choose other player's pit
                else if(currentPlayer ==1 && (pitNumber>13 || pitNumber <8))
                {
                    writeToConsole("FROM AI: Pit " + pitNumber  + " is illegal for " + currentPlayer + " \n");
                    continue;
                }
                // Yay we found one that works!
                else
                {
                    writeToConsole("FROM AI: Pit " + pitNumber  + " is legal for " + currentPlayer + " \n");
                    move = pitNumber;
                    break;
                }
            }
            
            lineScan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        return move;
    }
    
    public static void clearMoveFile()
    {
        String fname = "move.txt";
        File dataOut = new File(fname);
        try {
            FileWriter fw = new FileWriter(dataOut,false);
            fw.write("");
            fw.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void clearConsoleFile()
    {
        String fname = "console.txt";
        File dataOut = new File(fname);
        try {
            FileWriter fw = new FileWriter(dataOut,false);
            fw.write("");
            fw.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public static void runAIFile(String filepath) {
        // Run a java app in a separate system process
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("java -jar " + filepath);
            // Then retreive the process output
            InputStream in = proc.getInputStream();
            
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = bin.readLine()) != null) {
              System.out.println(line);
            }
            
            InputStream err = proc.getErrorStream();
            
            
            BufferedReader berr = new BufferedReader(new InputStreamReader(err));
            while((line = berr.readLine()) != null) {
              System.out.println(line);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(GameCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readAIConsole(mancala m) {
        Scanner lineScan;
        String fName = "console.txt";
        String line = "";
        File file = new File(fName);
        
        try {
            lineScan = new Scanner(file);
            // Read through lines of file
            while (lineScan.hasNextLine()){
                line = lineScan.nextLine();
                m.addDebug(line + "\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void printGameBoard() {
        Scanner lineScan;
        String fName = "gameboard.txt";
        String line = "";
        File file = new File(fName);
        
        try {
            lineScan = new Scanner(file);
            // Read through lines of file
            while (lineScan.hasNextLine()){
                line = lineScan.nextLine();
                writeToConsole(line + ":l\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void writeToConsole(String message) {
        String consoleFileName = "console.txt";
        File dataOut = new File(consoleFileName);
        try {
            FileWriter fw = new FileWriter(dataOut,true);
            fw.write(message);
            fw.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
