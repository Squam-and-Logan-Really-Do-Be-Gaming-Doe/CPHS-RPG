import javax.xml.soap.Text;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public static int scale = 80;

    public static final int confirm = 88;
    public static final int cancel = 90;
    public static final int up = 38;
    public static final int down = 40;
    public static final int left = 37;
    public static final int right = 39;

    private Room cRoom;
    private int chapter;
    private String nameChapter;
    private String timeFrame;
    private PChar player;
    public Game()
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(768, 432);
        StdDraw.setXscale(0, 1280);
        StdDraw.setYscale(0, 720);


        titleScreen();
        roomHandler();
    }

    private void titleScreen()
    {
        StdDraw.setPenColor(Color.black);
        StdDraw.text(640,360, "CPHS-RPG");
        StdDraw.text(640, 300, "Press X");
        //StdDraw.filledSquare(0,0,250);
        StdDraw.show();

        while(true)
        {
            if (StdDraw.isKeyPressed(confirm)) break;

        }
        fileSelect();
    }

    private void fileSelect()
    {
        for (int i = 0; i < 1000; i+=10) {
            StdDraw.setPenColor(Color.black);
            if(i <= 920)StdDraw.text(640,360+i, "CPHS-RPG");
            if(i >= 355)
            for (int j = 0; j < 3; j++) {
                //1000-540 = 460 - 100 = 360
                if(-440+i-(j*200)+85 >= 0) {
                    StdDraw.setPenColor(Color.black);
                    StdDraw.filledRectangle(640, -440 + i - (j * 200), 210, 85);
                    StdDraw.setPenColor(Color.gray);
                    StdDraw.filledRectangle(640, -440 + i - (j * 200), 200, 75);
                }
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
        boolean yopoC = true;
        boolean yopoB = true;
        boolean yopoU = false;
        boolean yopoD = false;
        while ((!StdDraw.isKeyPressed(confirm) || yopoC) && (!StdDraw.isKeyPressed(cancel) || yopoC)) {

            if (StdDraw.isKeyPressed(38) && !yopoU) {
                selector--;
            }
            if (StdDraw.isKeyPressed(40) && !yopoD) {
                selector++;
            }

            if (selector > 2) selector = 0;
            if (selector < 0) selector = 2;

            yopoU = StdDraw.isKeyPressed(38);
            yopoD = StdDraw.isKeyPressed(40);
            yopoC = StdDraw.isKeyPressed(confirm);
            yopoB = StdDraw.isKeyPressed(cancel);
            drawFiles(selector, files);
            StdDraw.show();
            StdDraw.clear();
        }
        //System.out.println("break");
        if(files[selector].equals("New File"))
        {
            fileSetup(selector);
        }
        fileRead(selector);
        //scanRoom("test");
    }

    private void drawFiles(int selector, String[] info)
    {
        for (int j = 0; j < 3; j++) {
            //1000-540 = 460 - 100 = 360
            if (selector == j) StdDraw.setPenColor(Color.red);
            else StdDraw.setPenColor(Color.black);
            StdDraw.filledRectangle(640, 560 - (j * 200), 210, 85);
            StdDraw.setPenColor(Color.gray);
            StdDraw.filledRectangle(640, 560 - (j * 200), 200, 75);
            StdDraw.setPenColor(Color.black);
            StdDraw.text(640, 560 - (j * 200), info[j]);
        }
    }

    private void roomHandler()
    {
        StdDraw.clear();
        cRoom.drawRoom();
        StdDraw.show();
        int frame = 0;
        boolean yopoC = false;
        while(true)
        {
            boolean press = StdDraw.isKeyPressed(Game.confirm);
            if (frame == 30)
            {
                frame = 0;
                cRoom.animate();
                cRoom.drawRoom();
                player.animate();
            }
            if(player.moveIt(cRoom))
            {
                //System.out.println(player.printPos());
                cRoom.drawRoom();
            }
            if(press && !yopoC)
            {
                player.interact(cRoom);
                StdDraw.clear();
                cRoom.drawRoom();
            }
            int[] mins = cRoom.getMaxPos();
            player.draw(scale, (15.0-mins[0])/2, (9.0-mins[1])/2);
            yopoC = press;
            //TextHandler.drawFrame();
            goodSleep();
            StdDraw.show();
            frame ++;
        }
    }

    private void fileRead(int slot)
    {
        try {
            Scanner file = new Scanner(new File("Data/Saves/save" + slot + ".dat"));
            file.next();
            chapter = file.nextInt();
            file.nextLine();
            nameChapter = file.nextLine();
            timeFrame = file.nextLine();
            cRoom = scanRoom(file.nextLine());
            player = new PChar(0,1,"testy", "U");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void fileSetup(int slot)
    {
        try {
            File file = new File("Data/Saves/save" + slot + ".dat");

            //Create the file
            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }

            //Write Content
            FileWriter writer = new FileWriter(file);
            writer.write("Chapter 1");
            writer.write("\n");
            writer.write("This is Cedar Park");
            writer.write("\n");
            writer.write("Beginning");
            writer.write("\n");
            writer.write("test");
            writer.close();
        } catch (Exception e) { e.printStackTrace(); }


    }

    private Room scanRoom(String roomName)
    {
        loadingScreen();
        try {
            //fRoom means File-Room
            Scanner fRoom = new Scanner(new File("Data/Rooms/" + roomName + ".dat"));
            ArrayList<Tile> tiles = new ArrayList<>();
            Room newRoom;
            while(fRoom.hasNextLine())
            {
                String name = fRoom.next();
                if(name.equals("μ"))
                {
                    Sounds.changeSong(fRoom.nextLine().substring(1));
                    //fRoom.nextLine();
                    continue;
                }
                if(name.equals("α"))
                {
                    fRoom.next();
                    String tileName = fRoom.next();
                    int startX = fRoom.nextInt();
                    int startY = fRoom.nextInt();
                    int endX = fRoom.nextInt();
                    int endY = fRoom.nextInt();
                    for (int i = startX; i <= endX; i++) {
                        for (int j = startY; j <= endY; j++) {
                            tiles.add(new Tile(i,j,0, tileName));
                        }
                    }
                    continue;

                }
                if(name.equals("Δ")) break;
                int x = fRoom.nextInt();
                int y = fRoom.nextInt();
                tiles.add(new Tile(x,y,0, name));
            }
            newRoom = new Room(tiles);
            ArrayList<Character> chars = new ArrayList<>();
            fRoom.nextLine();
            while(fRoom.hasNextLine())
            {
                String name = fRoom.next();
                int x = fRoom.nextInt();
                int y = fRoom.nextInt();
                String direc = fRoom.next();
                //System.out.println(name + " " + x + " " + y + " " + direc);
                String maybeVoice = fRoom.next();
                if (!maybeVoice.equals("null")) {
                    String text = fRoom.nextLine().substring(1);
                    chars.add(new Character(x, y, name, direc, text, maybeVoice));
                }
                else
                {
                    chars.add(new Character(x, y, name, direc));
                }
            }
            newRoom.setNPCs(chars);
            fRoom.close();
            //newRoom.determineScroll();
            return newRoom;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Room();
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

    public static int getScale() {
        return scale;
    }

    public static void loadingScreen()
    {
        StdDraw.clear();
        StdDraw.text(640, 360, "NOW LOADING...");
        StdDraw.show();
    }

}
