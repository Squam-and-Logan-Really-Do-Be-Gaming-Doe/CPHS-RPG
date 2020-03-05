import java.awt.*;

public class Game {

    private final int confirm = 88;

    public Game()
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1280);
        StdDraw.setYscale(0, 720);
        StdDraw.setCanvasSize(768, 432);

        titleScreen();
    }

    private void titleScreen()
    {
        StdDraw.setPenColor(Color.black);
        StdDraw.text(640,360, "CPHS-RPG");
        StdDraw.text(640, 300, "Press X");
        StdDraw.show();

        while(!StdDraw.isKeyPressed(confirm))
        {

        }
        fileSelect();
    }

    private void fileSelect()
    {
        for (int i = 0; i < 500; i++) {
            StdDraw.text(640,360-i, "CPHS-RPG");
            StdDraw.show();
            StdDraw.clear();
            //StdDraw.text(640, 300=i, "Press X");
        }
    }


}
