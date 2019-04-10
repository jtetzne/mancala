/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author jtetzner
 */
public class Mancala3 {
    public static Pit pits [];
    public static JButton buttons [];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mancala m = new mancala();
        buttons = m.returnButtonArr();
        m.setVisible(true);
        initPits(buttons);
        Player p1 = new Player(0,true);
        p1.goal =0;
        
        Player p2 =new Player(1,true);
        p2.goal =7;
        boolean flag = true;
       /* while(mancala.start() == false)
        {
            System.out.println("inloop");
        }*/
        
        while(!endState())
        {
            
            //player 1's turn
            while(flag ==true)
            {
                System.out.println("p1");
                flag =  move(p1);

            }
            flag = true;
            System.out.println("Switching Players");
            try {
            Thread.sleep(1000);
            } catch (InterruptedException e) {}
            mancala.clicked =0;
           
            while(flag ==true)
            {
               System.out.println("p2");
               flag =  move(p2);
               
            }
            flag = true;
            System.out.println("repeat");
            mancala.clicked =0;
            
        }
        
        
       
        
        
         

    }
    
    public static void initPits(JButton but[])
    {
      
        pits= new Pit[14];
        String num;
        for(int i =0; i<14;++i)
        {
            pits[i] = new Pit();
            pits[i].b = but[i];
            num = String.valueOf(pits[i].numStones);//(pits[i].numStones).toString();
            pits[i].b.setText(num);
        }
        
    
    }
    public static void updatePits()
    {
        String num;
        
        for(int i=0; i<14; ++i)
        {
            num = String.valueOf(pits[i].numStones);
            pits[i].b.setText(num);
        
        }
    
    }
    public static Pit[] sharePits()
    {
        return pits;
    }
    
    public static boolean endState()
    {
        int sum =0;
        //side 1 is 1-6
        //side 2 is 8-13
        for(int i =1; i< 7;++i)
        {
            sum += pits[i].numStones;
        }
        
        if(sum == 0){return true;}
        else{sum =0;}
        
        for(int i =8; i<14;++i)
        {
            sum += pits[i].numStones;
        }
        
      if(sum == 0){return true;}
        else{return false;}

    }
    
    
    public static void disableButtons(int player)
    {
        if(player ==0)
        {
            for(int i =1; i<7;++i)
            {
                pits[i].b.setEnabled(false);
                
            }
            for(int i =8; i<14;++i)
            {
                pits[i].b.setEnabled(true);
                
            }
        
        }
        
        if(player == 1)
        {
            for(int i =8; i<14;++i)
            {
                pits[i].b.setEnabled(false);
                
            }
            
            for(int i =1; i<7;++i)
            {
                pits[i].b.setEnabled(true);
                
            }
        }

        
        //goals
    
    }
    
    public static boolean move(Player player)
    {
        int i=0;
        boolean go = false;
        disableButtons(player.num);
        if(player.human == true)
        {
            while(i==0)
            {
                i=mancala.numClicked();
                try {
                Thread.sleep(500);
                } catch (InterruptedException e) {}
 
            }
           
        }
        
        go = anotherMove(player);
        if(go ==false)
        {
            System.out.println("Should switch");
        }
     
        return  go;
    }
    
    public static boolean anotherMove(Player player)
    {
        boolean flag=false;
        if(mancala.anotherMove == true)
        {
            pits[player.goal].numStones += mancala.numToGoal;
            mancala.updatePits();
            mancala.numToGoal =0;
            flag = true;
        }
        
        mancala.anotherMove = false;
        
        return flag;
    }
    
        
    
   

}

