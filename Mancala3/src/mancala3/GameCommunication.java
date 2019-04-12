/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dvoth
 */
public class GameCommunication {
    public static void updateGameBoard(Pit[] pits)
    {
        String fname = "gameboard.txt";
        File dataOut = new File(fname);
        try {
            FileWriter fw = new FileWriter(dataOut,false);
            for (Pit pit : pits) {
                fw.write(pit.numStones + "\n");
            }
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
        
        try {
            lineScan = new Scanner(new File(fName));
            // Read through lines of file
            if (lineScan.hasNext()){
               move = lineScan.nextInt();
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
        clearMoveFile();
        
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
    
    public static void runAIFile(String filepath) {
        // Run a java app in a separate system process
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("java -jar " + filepath);
            // Then retreive the process output
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
        } catch (IOException ex) {
            Logger.getLogger(GameCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
