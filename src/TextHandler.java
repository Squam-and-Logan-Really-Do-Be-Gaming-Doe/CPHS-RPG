import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextHandler {
    private static void drawFrame()
    {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(640,100,640,100);
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(640,100,620,80);
    }
    private static String[] initializeTexts()
    {
        return new String[]{"","","",""};
    }
    public static void textRead(String filePath, String voice)
    {
        try {
            Scanner reader = new Scanner(new File(filePath));
            drawFrame();
            reader.useDelimiter("");
            int index = 0;
            int count = 0;
            String[] texts = initializeTexts();
            while(reader.hasNext())
            {
                String chara = reader.next();
                if(chara.equals("\n")) index ++;
                else
                {
                    texts[index] += chara;
                }
                if(index >= 4)
                {
                    textUp(texts);
                    index--;
                }
                drawText(texts);
                if(!StdDraw.isKeyPressed(Game.confirm)) {
                    Game.goodSleep();
                    Game.goodSleep();
                }
                if(!chara.equals("!") && !chara.equals(".") && !chara.equals(" ") && count%2==0) Sounds.textBlip(voice);
                count++;

                StdDraw.show();

            }
            triangleAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void triangleAndWait()
    {
        StdDraw.setPenColor(Color.yellow);
        StdDraw.filledPolygon(new double[]{900, 925,950}, new double[]{60, 30,60});
        StdDraw.show();
        boolean yopoS = true;
        while(true)
        {
            boolean press = StdDraw.isKeyPressed(Game.confirm);
            if(press && !yopoS) break;
            yopoS = press;
        }
        StdDraw.setPenColor(Color.black);
    }

    private static void drawText(String[] texts)
    {
        for (int i = 0; i < texts.length; i++) {
            if(texts[i] != null) StdDraw.textLeft(250,160-(40*i),texts[i]);
        }
    }


    private static String[] textUp(String[] texts)
    {
        drawFrame();
        drawText(texts);
        for (int i = 0; i < 50; i+=10) {
            for (int j = 1; j < texts.length; j++) {


                if (texts[j] != (null)) {
                    StdDraw.textLeft(250, 160 - (40 * j)+i, texts[j]);
                }

            }
            StdDraw.show();
            if(!StdDraw.isKeyPressed(Game.confirm)) {
                Game.goodSleep();
            }
            //StdDraw.clear();
            //drawBG(bg);
            //drawPortraits();
            drawFrame();
            //nameBox(name);
        }
        for (int i = 1; i < texts.length; i++) {
            texts[i-1] = texts[i];
        }
        texts[texts.length-1] = "";

        return texts;
    }

}
