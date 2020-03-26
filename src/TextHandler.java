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
    public static void textRead(String filePath)
    {
        try {
            Scanner reader = new Scanner(new File("Data/Text/" + filePath));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
