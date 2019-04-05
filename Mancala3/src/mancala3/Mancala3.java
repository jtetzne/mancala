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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mancala m = new mancala();
        JButton arr[];
        arr = m.returnButtonArr();
        String num;
        
        pits = new Pit[14];
        
        for(int i =0; i<14;++i)
        {
            pits[i] = new Pit();
            pits[i].b = arr[i];
            num = String.valueOf(pits[i].numStones);//(pits[i].numStones).toString();
            pits[i].b.setText(num);
        }
        
        
        
         m.setVisible(true);

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
    
   

}
