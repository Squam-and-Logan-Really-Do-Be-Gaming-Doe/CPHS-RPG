import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextHandler {
    private static String[] initializeTexts()
    {
        return new String[]{"","","",""};
    }
    public static void textRead(String filePath, String voice)
    {
        try {
            //<editor-fold desc="Variable Setup">
            Scanner reader = new Scanner(new File(filePath));
            drawFrame();
            reader.useDelimiter("");
            int index = 0;
            int count = 0;
            int flip = 0;
            String face = "";
            String[] texts = initializeTexts();
            //</editor-fold>
            while(reader.hasNext())
            {
                String chara = reader.next();
                //<editor-fold desc="Checking Character">
                if(chara.equals("Δ"))
                {
                    String newFace = reader.nextLine().substring(1);
                    if(!face.equals("")) {
                        if (!face.equals(newFace)) {
                            face = newFace;
                            triangleAndWait();
                            texts = initializeTexts();
                            index = 0;
                            drawFrame();
                            drawFace(face, 0);
                            drawText(texts);
                        }
                    }
                    else
                    {
                        face = newFace;
                    }
                    continue;
                }
                if(chara.equals("φ"))
                {
                    voice = "Data/Voice/" + reader.nextLine().substring(1) + ".wav";
                    continue;
                }
                if(chara.equals("\n")) index ++;
                else
                {
                    texts[index] += chara;
                }
                //</editor-fold>
                if(index >= 4)
                {
                    if(face.equals(""))
                    textUp(texts);
                    else textUp(texts,face);
                    index--;
                }
                //<editor-fold desc="Flip And Face And Sounds">
                boolean punctuation = (chara.equals("!") || chara.equals(".") ||chara.equals(" ") || chara.equals("\n") || chara.equals(",") || chara.equals("?"));
                if(punctuation) flip = 0;
                drawFace(face, flip);
                if(flip == 0) flip =1;
                else flip = 0;
                drawText(texts);
                if(!StdDraw.isKeyPressed(Game.confirm)) {
                    Game.goodSleep();
                    Game.goodSleep();
                }
                if(!punctuation && count%2==0)
                {
                    Sounds.textBlip(voice);
                }
                count++;
                //</editor-fold>

                StdDraw.show();

            }
            drawFace(face, 0);
            triangleAndWait();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc="Drawing Methods">

    private static void drawFrame()
    {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(640,100,640,100);
        StdDraw.setPenColor(Color.black);
        StdDraw.rectangle(640,100,620,80);
    }

    private static void drawFace(String face, int open)
    {
        try{
            StdDraw.picture(120,100, "Data/Faces/" + face + open +  ".png", 150, 150);
        }
        catch (Exception ignored) {
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

    private static void textUp(String[] texts)
    {
        drawFrame();
        drawText(texts);
        for (int i = 0; i < 50; i+=10) {
            stupidLoop(texts, i);
            drawFrame();
        }
        if (texts.length - 1 >= 0) System.arraycopy(texts, 1, texts, 0, texts.length - 1);
        texts[texts.length-1] = "";

    }

    private static void textUp(String[] texts, String face)
    {
        drawFrame();
        drawFace(face, 0);
        drawText(texts);
        for (int i = 0; i < 50; i+=10) {
            stupidLoop(texts, i);
            drawFrame();
            drawFace(face, 0);
        }
        if (texts.length - 1 >= 0) System.arraycopy(texts, 1, texts, 0, texts.length - 1);
        texts[texts.length-1] = "";

    }

    private static void stupidLoop(String[] texts, int i)
    {
        for (int j = 1; j < texts.length; j++) {


            if (texts[j] != (null)) {
                StdDraw.textLeft(250, 160 - (40 * j)+i, texts[j]);
            }

        }
        StdDraw.show();
        if(!StdDraw.isKeyPressed(Game.confirm)) {
            Game.goodSleep();
        }
    }

    //</editor-fold>

}
