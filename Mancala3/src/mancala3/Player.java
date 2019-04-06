package mancala3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jtetzner
 */
public class Player {
    public int num;
    public int score;
    public boolean human;

    
    
    Player(int num, boolean h )
    { 
        this.num = num;
        this.score =0;
        this.human = h;
    }
    
}
