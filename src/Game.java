import java.awt.*;

public class Game {

    private final int confirm = 88;

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
        for (int i = 0; i < 1000; i+=5) {
            StdDraw.setPenColor(Color.black);
            StdDraw.text(640,360+i, "CPHS-RPG");
            StdDraw.setPenColor(Color.gray);
            for (int j = 0; j < 3; j++) {
                //1000-540 = 460 - 100 = 360
                StdDraw.filledRectangle(640,-540+i-(j*100), 200, 25);
            }
            StdDraw.show();
            StdDraw.clear();
            //StdDraw.text(640, 300=i, "Press X");
        }
    }


}
