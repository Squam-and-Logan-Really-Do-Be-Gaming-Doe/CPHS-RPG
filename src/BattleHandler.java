import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleHandler {
    private final String sysVoice = "Data/Voice/medVoice.wav";

    private final int yourYPos = 300;
    private final int yourXPos = 400;

    private final int enemyYPos = 500;
    private final int enemyXPos = 880;

    private Pokemon[] friends;
    private Pokemon[] foes;
    private boolean[] whoWent;
    private int youInd = 0;
    private int foeInd = 0;
    private String foeName;
    private String foeClass;
    private String greeting;

    private Music music;
    private String oldSong;
    private long oldPos;

    public BattleHandler(String battle)
    {
        readBattle(battle);
        friends = Game.getPlayer().getPokemons();
        battleStart();
    }

    private void readBattle(String battle)
    {
        try {
            Scanner info = new Scanner(new File("Data/Battling/Battles/" + battle));
            foeClass = info.nextLine();
            foeName = info.nextLine();
            greeting = info.nextLine();
            int numpoke = Integer.parseInt(info.nextLine());
            foes = new Pokemon[numpoke];
            for (int i = 0; i < numpoke; i++) {
                String pokeName = info.nextLine();
                String stats = info.nextLine();
                int numMoves = Integer.parseInt(info.nextLine());
                Move[] moves = new Move[numMoves];
                for (int j = 0; j < numMoves; j++) {
                    String movName = info.nextLine();
                    moves[j] = new Move(movName);
                }
                foes[i] = new Pokemon(stats,pokeName, moves);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void battleStart()
    {
        music = Game.getMusic();
        oldSong = music.getSong();
        oldPos = music.getMusicTime();
        music.changeSong("paralyzer.wav");
        int rectX = 320;
        //int rectX = 427;
        //int rectX = 20;
        int rectY = 80;
        for (int x = 0; x <= Game.xSize; x+=rectX) {
            StdDraw.setPenColor((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
            for (int y = 0; y < Game.ySize; y+=rectY) {
                StdDraw.filledRectangle(x+rectX/2.0,y+rectY/2.0,rectX/2.0,rectY/2.0);
                StdDraw.show();
                Game.goodSleep();
            }
        }
        for (int x = 0; x < Game.xSize; x+=rectX) {
            StdDraw.setPenColor(Color.white);
            for (int y = 0; y < Game.ySize; y+=rectY) {
                StdDraw.filledRectangle(x+rectX/2.0,y+rectY/2.0,rectX/2.0,rectY/2.0);
                StdDraw.show();
                Game.goodSleep();
            }
        }
        int loopVal = 500;
        for (int i = 0; i < loopVal; i+=10) {
            StdDraw.clear();
            StdDraw.picture((yourXPos-loopVal)+i, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture((enemyXPos+loopVal)-i, enemyYPos, "Data/Battling/Trainers/" + foeClass + ".png");
            StdDraw.show();
            Game.goodSleep();
        }
        String foeTitle = foeClass + " " + foeName;
        TextHandler.textRead(foeTitle + " " + greeting, sysVoice);
        TextHandler.textRead(foeTitle + " sends out " + foes[foeInd].getNickName() + "!",sysVoice);
        for (int i = 0; i <= loopVal; i+=20) {
            StdDraw.clear();
            StdDraw.picture(yourXPos, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture(enemyXPos+i, enemyYPos, "Data/Battling/Trainers/" + foeClass + ".png");
            StdDraw.picture((enemyXPos+loopVal)-i, enemyYPos, "Data/Battling/Pokemon/" + foes[foeInd].getName() + "/front.png");
            StdDraw.show();
            Game.goodSleep();
        }
        Sounds.cry(foes[foeInd].getName());
        TextHandler.textRead("Go for it, " + friends[youInd].getNickName() + "!",sysVoice);
        for (int i = 0; i <= loopVal; i+=20) {
            StdDraw.clear();
            StdDraw.picture(yourXPos-i, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture((yourXPos-loopVal)+i, yourYPos,"Data/Battling/Pokemon/" + friends[youInd].getName() + "/back.png");
            StdDraw.picture(enemyXPos, enemyYPos, "Data/Battling/Pokemon/" + foes[foeInd].getName() + "/front.png");
            StdDraw.show();
            Game.goodSleep();
        }
        Sounds.cry(friends[youInd].getName());
        whoWent = new boolean[2];
        Game.goodSleep();
        Game.goodSleep();
        Game.goodSleep();
        //displayInfo();
        bothInfo();
        if(isYourTurn()) battleMenu();
        endBattle();
    }

    private void battleMenu()
    {
        TextHandler.textRead("What will you do? ", sysVoice);
        int xInd = 0;
        int yInd = 1;
        boolean[] yopos = new boolean[4];
        boolean yopoC = true;
        while(true)
        {
            if(StdDraw.isKeyPressed(Game.confirm) && !yopoC) break;
            TextHandler.drawFrame();
            StdDraw.text(400, 135, "FIGHT");
            StdDraw.text(400,65, "ITEMS");
            StdDraw.text(880, 135, "GANG");
            StdDraw.text(880, 65, "RUN");
            StdDraw.text(350+(xInd*480),65+(yInd*70),">");

            int[] nums = gridSelect(xInd,yInd,yopos[0],yopos[1],yopos[2],yopos[3]);
            xInd = nums[0];
            yInd = nums[1];

            yopos = yopoEr();
            yopoC = StdDraw.isKeyPressed(Game.confirm);

            StdDraw.show();
            Game.goodSleep();
        }
        Sounds.sfx("Selected.wav");
        if(xInd == 0 && yInd == 1)
        {
            moveMenu();
        }


    }

    private void moveMenu()
    {
        TextHandler.drawFrame();
        StdDraw.setPenColor(Color.black);
        Move[] moves = friends[youInd].getMoves();

        boolean[] yopos = new boolean[4];
        boolean yopoC = true;
        boolean yopoCancel = true;
        int xInd = 0;
        int yInd = 1;

        while(true) {
            TextHandler.drawFrame();
            for (int i = 0; i < moves.length; i++) {
                int xLoc = 400 + ((i % 2) * 440);
                int yLoc;
                if (i < 2) {
                    yLoc = 135;
                } else {
                    yLoc = 65;
                }
                StdDraw.text(xLoc, yLoc, moves[i].getName());
                StdDraw.text(xLoc, yLoc - 25, moves[i].getcUses() + "/" + moves[i].getMaxUses());
            }
            if(moves.length < 2) {
                xInd = 0;
                yInd = 0;
            }
            if(moves.length< 3 && yInd ==0)
            {
                yInd = 1;
            }
            if(moves.length<4 && xInd == 1 && yInd == 0)
            {
                xInd = 0;
            }

            StdDraw.text(350+(xInd*425),65+(yInd*70),">");
            int[] nums = gridSelect(xInd,yInd,yopos[0],yopos[1],yopos[2],yopos[3]);
            xInd = nums[0];
            yInd = nums[1];

            yopos = yopoEr();
            yopoC = StdDraw.isKeyPressed(Game.confirm);

            StdDraw.show();
            Game.goodSleep();
        }
    }

    private boolean[] yopoEr()
    {
        return new boolean[]{StdDraw.isKeyPressed(Game.up),StdDraw.isKeyPressed(Game.down),
                StdDraw.isKeyPressed(Game.left), StdDraw.isKeyPressed(Game.right)};
    }

    private int[] gridSelect(int xInd, int yInd, boolean yopoU, boolean yopoD, boolean yopoL, boolean yopoR)
    {
        boolean up = StdDraw.isKeyPressed(Game.up);
        boolean down = StdDraw.isKeyPressed(Game.down);
        boolean left = StdDraw.isKeyPressed(Game.left);
        boolean right = StdDraw.isKeyPressed(Game.right);

        if(up && !yopoU)
        {
            Sounds.sfx("Selector.wav");
            yInd++;
        }
        if(down && !yopoD)
        {
            Sounds.sfx("Selector.wav");
            yInd--;
        }
        if(right && !yopoR)
        {
            Sounds.sfx("Selector.wav");
            xInd++;
        }
        if(left && !yopoL)
        {
            Sounds.sfx("Selector.wav");
            xInd--;
        }

        if(yInd > 1) yInd = 0;
        if(yInd < 0 ) yInd = 1;
        if(xInd > 1) xInd = 0;
        if(xInd < 0) xInd = 1;

        return new int[]{xInd, yInd};
    }

    private void bothInfo()
    {
        displayInfo(friends[youInd], 0);
        displayInfo(foes[foeInd], 1);
    }

    private void displayInfo(Pokemon poke, int whomst)
    {
        int centerY;
        int centerX;
        int rectWid = 150;
        int rectHet = 100;
        if(whomst == 1) {
            centerY = 600;
            centerX = 175;
        }
        else
        {
            centerY = 305;
            centerX = 1100;
        }
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(centerX,centerY,rectWid,rectHet);
        StdDraw.setPenColor(Color.white);
        StdDraw.filledRectangle(centerX,centerY, rectWid*.90, rectHet*.80);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(centerX,centerY+(rectHet*.60), poke.getNickName());
        StdDraw.text(centerX,centerY+(rectHet*.35),"Level " + poke.getLevel());
        int foeHP = poke.getCHP();
        int foeMax = poke.getHP();
        StdDraw.text(centerX,centerY+(rectHet)*.10, "" + foeHP + "/" + foeMax);
        double perecntage = foeHP/(double)foeMax;
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(centerX, centerY-(rectHet*.50), rectWid*.90, rectHet*.16);
        StdDraw.setPenColor(Color.green);
        if(perecntage < .5) StdDraw.setPenColor(Color.yellow);
        if(perecntage < .25) StdDraw.setPenColor(Color.red);
        double invert = 1-perecntage;
        StdDraw.filledRectangle(centerX-(invert*centerX), centerY-(rectHet*.50), rectWid*.90, rectHet*.15);
    }


    private boolean isYourTurn()
    {
        if(whoWent[0] && !whoWent[1]) return false;
        if(!whoWent[0] && whoWent[1]) return true;
        //boolean[] speeds = mostSpeed();
        if(!whoWent[0] && !whoWent[1])
        {
            return mostSpeed()[0];
        }
        whoWent = new boolean[2];
        return isYourTurn();
    }

    private boolean[] mostSpeed()
    {
        int youSpeed = friends[youInd].getSpeed();
        int foeSpeed = foes[foeInd].getSpeed();
        if(youSpeed == foeSpeed) return new boolean[]{true, true};
        if(youSpeed > foeSpeed) return new boolean[]{true, false};
        return new boolean[]{false, true};
    }

    private void endBattle()
    {
        music.resumeOldSong(oldSong, oldPos);
    }

}
