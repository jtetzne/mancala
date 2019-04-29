/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;
import javax.swing.JButton;
/**
 *
 * @author jtetzner
 */
public class Mancala3 {
    public static Pit pits [];
    public static JButton buttons [];
    public boolean turn;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        mancala m = new mancala();
        buttons = m.returnButtonArr();
        m.setVisible(true);
        initPits(buttons);
        
         while(m.gameCanStart() == false)
        {
            System.out.println("Waiting");
        }
        Player p1 = new Player(0,mancala.getHumanStatus(0));
        p1.goal =0;
        Player p2 =new Player(1,mancala.getHumanStatus(1));
        p2.goal =7;
        boolean flag = true;
        long start = 0;
        long end = 0;
        int limit = mancala.limit;
        int numAI =-1;
        int AImove=-1;
        
        // spin wait for game to start
        while (!m.gameStarted()) {
            System.out.println("wait 2");
        }
        
        while(!endState())
        {
            
            //player 1's turn
            m.changePlayerText("Player 1's Turn");
            m.setCurrentPlayer(p1);
            while(flag ==true)
            {
                GameCommunication.updateGameBoard(pits, p1);
                if(endState() ==true)
                {break;}
                mancala.getPlayerNum(0);
                if(p1.human == false)
                {
                    start = System.currentTimeMillis();
                    end = start + limit*1000; 
                    
                    GameCommunication.runAIFile(m.computer1FullPath);
                    
                    // Continuously check for a move from the AI for time limit
                    while (System.currentTimeMillis() < end) {
                        numAI = GameCommunication.getAIMove();
                        if (numAI != -1 || System.currentTimeMillis() > end) {
                            break;
                        }
                    }
                    
                    if(numAI!= -1)
                    {
                        m.addDebug("AI did not finish in " + limit + " seconds");
                        flag = false;
                    }
                    else if (!isValid(numAI,p1))
                    {
                        m.addDebug("AI chose invalid move");
                        flag = false;
                    }
                    else
                    {   
                        mancala.clicked = numAI;
                        m.moveStones(numAI);   
                        flag =  move(p1);                 
                        break;
                    }            
                }
                else {
                    System.out.println("p1");
                    flag =  move(p1);
                }
            }
            numAI=-1;
            flag = true;
            //rest AI num?
            m.addDebug("Switching Players");
            try {
            Thread.sleep(200);
            } catch (InterruptedException e) {}
            mancala.clicked =0;
           
            m.changePlayerText("Player 2's Turn");
            m.setCurrentPlayer(p2);
            while(flag ==true)
            {
                GameCommunication.updateGameBoard(pits, p2);
                if(endState() ==true)
                {break;}
                mancala.getPlayerNum(1);
                if(p2.human == false)
                {
                    start = System.currentTimeMillis();
                    end = start + limit*1000; 
                    
                    m.addDebug("Running AI file \n");
                    GameCommunication.runAIFile(m.computer2FullPath);
                    
                    // Continuously check for a move from the AI for time limit
                    while (System.currentTimeMillis() < end) {
                        numAI = GameCommunication.getAIMove();
                        if (numAI != -1 || System.currentTimeMillis() > end) {
                            break;
                        }
                    }
                    
                    if(numAI == -1)
                    {
                        m.addDebug("AI did not finish in " + limit + " seconds");
                        flag = false;
                    }
                    else if (!isValid(numAI,p2))
                    {
                        m.addDebug("AI chose invalid move: " + numAI);
                        flag = false;
                    }
                    else
                    {   
                        mancala.clicked = numAI;
                        m.moveStones(numAI);   
                        flag =  move(p2);  
                    }     
                } else {
                    System.out.println("p2");
                    flag =  move(p2);
                }
            }
            numAI=-1;
            flag = true;
            System.out.println("repeat");
            mancala.clicked =0;
            
        }
        if(endState() ==true)
        {
            
            m.addDebug("End State True");
            mancala.finalScore(0);
            mancala.finalScore(1);
            int p1S = Integer.parseInt(buttons[p1.goal].getText());
            int p2S = Integer.parseInt(buttons[p2.goal].getText());
            System.out.println(p1S);
            System.out.println(p2S);
             
            if(p1S >p2S)
            {
                m.changePlayerText("Player 1 Won!");
            }
            else if(p2S>p1S)
            {
                m.changePlayerText("Player 2 Won!");
            } else if(p2S == p1S)
            {
                m.changePlayerText("Draw");
            }
           
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
    /*public static void updatePits()
    {
        String num;
        
        for(int i=0; i<14; ++i)
        {
            num = String.valueOf(pits[i].numStones);
            pits[i].b.setText(num);
        }
    
    }*/
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
                pits[i].b.setEnabled(true);
                
            }
            for(int i =8; i<14;++i)
            {
                pits[i].b.setEnabled(false);
                
            }
        
        }
        
        if(player == 1)
        {
            for(int i =8; i<14;++i)
            {
                pits[i].b.setEnabled(true);
                
            }
            
            for(int i =1; i<7;++i)
            {
                pits[i].b.setEnabled(false);
                
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
                Thread.sleep(300);
                } catch (InterruptedException e) {}
 
            }
           
        }
        
        go = anotherMove(player);

     
        return  go;
    }
    
    public static boolean anotherMove(Player player)
    {
        boolean flag=false;
        if(mancala.anotherMove == true)
        {
            mancala.clicked =0;
            mancala.numToGoal =0;
            player.setMoveMade(false);
            flag = true;
        }
        
        mancala.anotherMove = false;
        
        return flag;
    }
    
    public static boolean isValid(int num, Player p)
    {
        if(num<0 || num>13 || pits[num].numStones == 0)
        {return false;}
        if(p.num ==0)
        {
            if(num>6 || num <1)
            {return false;}
        }
        
        else if(p.num ==1)
        {
            if(num>12 || num <8)
            {return false;}
        }
        
        if (p.isMoveMade()) {
            return false;
        }
        
        return true;
    }
}

