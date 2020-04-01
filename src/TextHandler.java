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
    public static void textRead(String filePath)
    {
        try {
            Scanner reader = new Scanner(new File(filePath));
            drawFrame();
            reader.useDelimiter("");
            int index = 0;
            String[] texts = initializeTexts();
            while(reader.hasNext())
            {
                String chara = reader.next();
                if(chara.equals("\n")) index ++;
                else
                {
                    texts[index] += chara;
                }
                drawText(texts);
                Game.goodSleep();
                StdDraw.show();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void drawText(String[] texts)
    {
        for (int i = 0; i < texts.length; i++) {
            if(texts[i] != null) StdDraw.textLeft(250,160-(40*i),texts[i]);
        }
    }

}
