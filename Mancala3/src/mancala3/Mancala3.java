/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala3;
import java.awt.Color;
import javax.swing.JButton;
/**
 *
 * @author jtetzner
 */
public class Mancala3 {
    public static Pit pits [];
    public static JButton buttons [];
    public static boolean turn;
    public static Player p1;
    public static Player p2;
    public static boolean flag = true;
    public static int numAI =-1;
    public static int AImove=-1;

    public static mancala m;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         m = new mancala();
         initGame(m);
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
        enableAllButtons();
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
    
    public static void initGame(mancala m)
    {    
        buttons = m.returnButtonArr();
        m.setVisible(true);
        initPits(buttons);
        m.pits = sharePits();
        
        p1 = new Player(0,m.getHumanStatus(0));
        System.out.println("player one is "+p1.human);
        p1.goal =0;
        p2 =new Player(1,m.getHumanStatus(1));
             System.out.println("player two is "+p2.human);
        p2.goal =7;
        m.currentPlayer = p1;
        turn = false;
    }


    public static void disableButtons(boolean player)
    {
        if(!player)
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

        if(player)
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
    }
    
    public static void disableAllButtons()
    {
        for(int i =1; i<14;++i)
        {
            pits[i].b.setEnabled(false);
        }
    }
    
    public static void enableAllButtons()
    {
        for(int i =1; i<14;++i)
        {
            pits[i].b.setEnabled(true);
        }
    }

    public static void move(mancala m)
    {
        long start = 0;
        long end = 0;
        int playerNum = m.currentPlayer.getNum();
        int makingMove = 1;
        int limit = mancala.limit;
        
        if (m.currentPlayer.human == false && makingMove == 0 && !bothAI()) {
            move(m);
            makingMove = 1;
        }
        
        if(!endState() && ! m.resetGame)
        {
            GameCommunication.updateGameBoard(m.pits, m.currentPlayer, m);
            System.out.println("p" + (playerNum + 1));
            m.getPlayerNum(playerNum);
            if(m.currentPlayer.human == false)
            {
                String computerPath = "";
                
                if (playerNum == 0) {
                    computerPath = m.computer1FullPath;
                } else {
                    computerPath = m.computer2FullPath;
                }
                
                System.out.println("player is AI");
                start = System.currentTimeMillis();
                end = start + limit*1000;

                GameCommunication.runAIFile(computerPath);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}

                // Continuously check for a move from the AI for time limit
                while (System.currentTimeMillis() < end) {
                    numAI = GameCommunication.getAIMove();
                    if (numAI != -1 || System.currentTimeMillis() > end) {
                        break;
                    }
                }

                GameCommunication.readAIConsole(m);
                GameCommunication.clearConsoleFile();

                if(numAI == -1)
                {
                    m.addDebug("AI did not finish in " + limit + " seconds");
                }
                else if (!isValid(numAI,m.currentPlayer))
                {
                    m.addDebug("AI chose invalid move " + numAI);
                }
                else
                {
                    m.clicked = numAI;
                    buttons[numAI].setBackground(Color.RED);
                    m.moveStones(numAI);
                    
                }
            }
            else {
                System.out.println("p" + (playerNum + 1));
            }
        }
        numAI=-1;
        //rest AI num?
        m.addDebug("Switching Players");
        m.clicked =0;
        
        if (!anotherMove(m.currentPlayer)) {
            turn = !turn;
            disableButtons(turn);
            m.currentPlayer = turn ? p2 : p1;
            m.currentPlayer.setMoveMade(false);
            playerNum = m.currentPlayer.getNum();
            m.changePlayerText("Player " + (playerNum + 1) + "'s Turn");
            makingMove = 0;
        }
        
        if (m.currentPlayer.human == false && !bothAI()) {
            move(m);
            makingMove = 0;
        }
        
        if(endState() == true)
        {
            disableAllButtons();
            m.addDebug("End State True");
            m.finalScore(0);
            m.finalScore(1);
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
    
    public static boolean bothAI() {
        return !p1.human && !p2.human;
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
            if(num>13 || num <8)
            {return false;}
        }

        if (p.isMoveMade()) {
            return false;
        }

        return true;
    }
}

