/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
        initPits(buttons);
        Player p1 = new Player(0,true);
        Player p2 =new Player(1,true);
        boolean flag = true;
        
        while(!endState())
        {
            
            //player 1's turn
            while(flag ==true)
            {
               flag =  move(p1);
               break;
            }
            flag = true;
           
            while(flag ==true)
            {
               flag =  move(p2);
               break;
            }
            
            break;
        }
        
        
       
        
        
         m.setVisible(true);

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
        boolean go = false;
        disableButtons(player.num);
        if(player.human == true)
        {
             go = waitForMove();
            
        }
        
        
        return false;
    }
    
    public static boolean anotherMove()
    {
        return false;
    }
    
        public static boolean waitForMove()
        {
            int i =0;
            
            while(pits[i].clicked = false)
            {
                ++i;
                if(i==13)
                {
                    i=0;
                }
            }
            if(pits[i].clicked = true)
            {return true;}
            return false;
           
        }
    
   

}

