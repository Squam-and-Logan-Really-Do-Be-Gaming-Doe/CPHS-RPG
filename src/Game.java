import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Game {

    private final int confirm = 88;
    private final int up = 38;
    private final int down = 40;
    private final int left = 37;
    private final int right = 39;
    public Game()
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(768, 432);
        StdDraw.setXscale(0, 1280);
        StdDraw.setYscale(0, 720);


        titleScreen();
    }

    private void titleScreen()
    {
        StdDraw.setPenColor(Color.black);
        StdDraw.text(640,360, "CPHS-RPG");
        StdDraw.text(640, 300, "Press X");
        //StdDraw.filledSquare(0,0,250);
        StdDraw.show();

        while(!StdDraw.isKeyPressed(confirm))
        {

        }
        fileSelect();
    }

    private void fileSelect()
    {
        for (int i = 0; i < 1000; i+=10) {
            StdDraw.setPenColor(Color.black);
            if(i <= 920)StdDraw.text(640,360+i, "CPHS-RPG");
            for (int j = 0; j < 3; j++) {
                //1000-540 = 460 - 100 = 360
                StdDraw.setPenColor(Color.black);
                StdDraw.filledRectangle(640,-440+i-(j*200), 210, 85);
                StdDraw.setPenColor(Color.gray);
                StdDraw.filledRectangle(640,-440+i-(j*200), 200, 75);
            }
            StdDraw.show();
            StdDraw.clear();
            goodSleep();
            //StdDraw.text(640, 300=i, "Press X");
        }
        String[] files = new String[3];
        for (int i = 0; i < files.length; i++) {
            try {
                Scanner file = new Scanner(new File("Data/Saves/save" + i + ".dat"));
                files[i] = file.nextLine();
            } catch (Exception e) {
                files[i] = "New File";
            }
        }
        int selector = 0;
        int oldSelector = 1;
        boolean yopoC = true;
        boolean yopoU = false;
        boolean yopoD = false;
        while(true)
        {
            if (StdDraw.isKeyPressed(confirm) && !yopoC) break;
            if(oldSelector != selector){
            for (int j = 0; j < 3; j++) {
                //1000-540 = 460 - 100 = 360
                if(selector == j) StdDraw.setPenColor(Color.red);
                else StdDraw.setPenColor(Color.black);
                StdDraw.filledRectangle(640,560-(j*200), 210, 85);
                StdDraw.setPenColor(Color.gray);
                StdDraw.filledRectangle(640,560-(j*200), 200, 75);
                StdDraw.setPenColor(Color.black);
                StdDraw.text(640,560-(j*200), files[j]);
            }oldSelector = selector;}
            if(StdDraw.isKeyPressed(up) && !yopoU) selector --;
            if(StdDraw.isKeyPressed(down) && !yopoD) selector++;
            if(selector < 0) selector = files.length-1;
            if(selector > files.length-1) selector = 0;
            StdDraw.show();
            yopoC = StdDraw.isKeyPressed(confirm);
            yopoU = StdDraw.isKeyPressed(up);
            yopoD = StdDraw.isKeyPressed(down);
            goodSleep();
        }


    }


    public static void goodSleep()
    {
        try
        {
            Thread.sleep(16, 666667);
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
