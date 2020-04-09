import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BattleHandler {
    private final String sysVoice = "Data/Voice/medVoice.wav";

    private int yourYPos = 300;
    private int yourXPos = 400;

    private int enemyYPos = 500;
    private int enemyXPos = 880;

    private Pokemon[] friends;
    private Pokemon[] foes;
    private int youInd = 0;
    private int foeInd = 0;
    private String foeName;
    private String foeClass;
    private String greeting;

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
        int rectX = 320;
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
        for (int i = 0; i < 500; i+=10) {
            StdDraw.clear();
            StdDraw.picture(-100+i, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture(1380-i, enemyYPos, "Data/Battling/Trainers/" + foeClass + ".png");
            StdDraw.show();
            Game.goodSleep();
        }
        String foeTitle = foeClass + " " + foeName;
        TextHandler.textRead(foeTitle + " " + greeting, sysVoice);
        TextHandler.textRead(foeTitle + " sends out " + foes[foeInd].getNickName() + "!",sysVoice);
        for (int i = 0; i < 500; i+=20) {
            StdDraw.clear();
            StdDraw.picture(yourXPos, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture(enemyXPos+i, enemyYPos, "Data/Battling/Trainers/" + foeClass + ".png");
            StdDraw.picture(1380-i, enemyYPos, "Data/Battling/Pokemon/" + foes[foeInd].getName() + "/front.png");
            StdDraw.show();
            Game.goodSleep();
        }
        TextHandler.textRead("Go for it, " + friends[youInd].getNickName() + "!",sysVoice);
        for (int i = 0; i < 500; i+=20) {
            StdDraw.clear();
            StdDraw.picture(yourXPos-i, yourYPos,"Data/Battling/Trainers/You.png");
            StdDraw.picture(-100+i, yourYPos,"Data/Battling/Pokemon/" + friends[youInd].getName() + "/back.png");
            StdDraw.picture(enemyXPos, enemyYPos, "Data/Battling/Pokemon/" + foes[foeInd].getName() + "/front.png");
            StdDraw.show();
            Game.goodSleep();
        }
    }

}
