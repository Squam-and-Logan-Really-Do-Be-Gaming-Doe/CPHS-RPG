import javax.xml.stream.util.StreamReaderDelegate;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    //<editor-fold desc="Instance Variables">
    public static int scale = 80;

    public static final int confirm = 88;
    public static final int cancel = 90;
    public static final int start = 10;
    public static final int up = 38;
    public static final int down = 40;
    public static final int left = 37;
    public static final int right = 39;
    public static final int xSize = 1280;
    public static final int ySize = 720;


    private Room cRoom;
    private int chapter;
    private String nameChapter;
    private String timeFrame;
    private static PChar player;
    private static Music music;
    //</editor-fold>

    public Game()
    {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(768, 432);
        //Max X blocks: 16. Each Block is 80 Pixels
        StdDraw.setXscale(0, xSize);
        //Max Y Blocks: 9. Each Block is 80 Pixels
        StdDraw.setYscale(0, ySize);

        try {
            music = new Music();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        titleScreen();
        roomHandler();
    }

    //<editor-fold desc="UI Methods">
    private void titleScreen()
    {
        StdDraw.clear();
        music.changeSong("Snow-Halation.wav");
        StdDraw.setPenColor(Color.black);
        StdDraw.text(640,360, "CPHS-RPG");
        StdDraw.text(640, 300, "Press X");
        //StdDraw.filledSquare(0,0,250);
        StdDraw.show();

        while(true)
        {
            if (StdDraw.isKeyPressed(confirm)) break;

        }
        //fileSelect();
        Sounds.sfx("Selected.wav");
        selectMenu();
    }
    private void selectMenu()
    {
        StdDraw.clear();
        StdDraw.setPenColor(Color.black);
        boolean yopoU = false;
        boolean yopoD = false;
        boolean yopoC = true;
        boolean yopoB = true;
        int selector = 0;
        while(true)
        {
            StdDraw.text(640,360, "CPHS-RPG");
            StdDraw.text(640,300,"File Select");
            StdDraw.text(640,275, "Options");
            if(StdDraw.isKeyPressed(confirm) && !yopoC) break;
            if(StdDraw.isKeyPressed(cancel) && !yopoB) break;
            if(StdDraw.isKeyPressed(up) && !yopoU)
            {
                Sounds.sfx("Selector.wav");
                selector-=1;
            }
            else if(StdDraw.isKeyPressed(down) && !yopoD)
            {
                Sounds.sfx("Selector.wav");
                selector+=1;
            }
            if(selector<0) selector = 1;
            if(selector>1) selector = 0;
            StdDraw.text(500,300-(selector*25), ">");
            yopoC = StdDraw.isKeyPressed(confirm);
            yopoD = StdDraw.isKeyPressed(down);
            yopoU = StdDraw.isKeyPressed(up);
            yopoB = StdDraw.isKeyPressed(cancel);
            StdDraw.show();
            StdDraw.clear();
        }
        if(StdDraw.isKeyPressed(confirm)) {
            Sounds.sfx("Selected.wav");
            if (selector == 0) fileSelect();
            else if (selector == 1)
            {
               Menu.optionMenu();
                selectMenu();
            }
        }
        else
        {
            Sounds.sfx("Cancel.wav");
            titleScreen();
        }
    }
    public static void loadingScreen()
    {
        StdDraw.clear();
        StdDraw.text(640, 360, "NOW LOADING...");
        StdDraw.show();
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
    //</editor-fold>

    //<editor-fold desc="File Methods">
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
        while ((!StdDraw.isKeyPressed(confirm) || yopoC) && (!StdDraw.isKeyPressed(cancel) || yopoB)) {

            if (StdDraw.isKeyPressed(38) && !yopoU) {
                Sounds.sfx("Selector.wav");
                selector--;
            }
            if (StdDraw.isKeyPressed(40) && !yopoD) {
                Sounds.sfx("Selector.wav");
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
        if(StdDraw.isKeyPressed(cancel))
        {
            Sounds.sfx("Cancel.wav");
            titleScreen();
        }
        Sounds.sfx("Selected.wav");
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
            player = new PChar(file.nextInt(),file.nextInt(),"testy", "U");
            file.nextLine();
            player.setName(file.nextLine());
            int numPoke = file.nextInt();
            file.nextLine();
            Pokemon[] pokes = new Pokemon[numPoke];
            for (int i = 0; i < numPoke; i++) {
                String name = file.nextLine();
                String temp = file.nextLine();
                String stats;
                //System.out.println(name + " " + temp);
                if(temp.split(" ").length == 7)
                {
                    stats = temp;
                }
                else
                {
                    stats = file.nextLine();
                }
                int moveNum = Integer.parseInt(file.nextLine());
                Move[] moves = new Move[moveNum];
                for (int j = 0; j < moveNum; j++) {
                    moves[j] = new Move(file.nextLine());
                }
                if(temp.equals(name)) pokes[i] = new Pokemon(stats, name, moves);
                else {
                    pokes[i] = new Pokemon(stats, name, moves, temp);
                }
            }
            player.setPokemons(pokes);
            //System.out.println(player.toString());
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
    //</editor-fold>

    //<editor-fold desc="Room Methods">
    private void roomHandler()
    {
        StdDraw.clear();
        cRoom.drawBG();
        cRoom.drawRoom();
        StdDraw.show();
        int frame = 0;
        boolean yopoC = false;
        boolean yopoStart = false;
        while(true)
        {
            boolean press = StdDraw.isKeyPressed(Game.confirm);
            boolean start = StdDraw.isKeyPressed(Game.start);
            //System.out.println(start);
            if(start && !yopoStart)
            {
                Menu.startMenu();
                cRoom.drawBG();
                cRoom.drawRoom();

            }
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
                cRoom.drawBG();
                cRoom.drawRoom();
            }
            int[] maxs = cRoom.getMaxPos();
            player.draw(scale, Room.centerX(maxs[0]), Room.centerY(maxs[1]));
            yopoC = press;
            yopoStart = start;
            //TextHandler.drawFrame();
            if(cRoom.getWarps() != null)
            {
                warpHandler(player, cRoom);
            }
            //System.out.println(player.getxPos() + " " + player.getyPos());
            goodSleep();
            StdDraw.show();
            frame ++;
        }
    }

    //<editor-fold desc="Warp Methods">
    private void warpHandler(PChar player, Room cRoom)
    {
        //System.out.println(Arrays.toString(cRoom.getWarps()));
        for (Warp warp :
                cRoom.getWarps()) {
            if(willWarp(player, warp))
            {
                //System.out.println("i am a here");
                this.cRoom = scanRoom(warp.getNewRoom());
                StdDraw.clear();
                this.cRoom.drawBG();
                this.cRoom.drawRoom();
                player.setxPos(warp.getNewX());
                //System.out.println(player.getxPos());
                player.setyPos(warp.getNewY());
                //System.out.println(player.getyPos());
                StdDraw.show();
                break;
            }
            //System.out.println("ok");
        }
    }
    private boolean willWarp(PChar player, Warp warp)
    {
        return (player.getxPos() == warp.getxPos()) && (player.getyPos() == warp.getyPos());
    }
    //</editor-fold>

    private Room scanRoom(String roomName)
    {
        //loadingScreen();
        try {
            //fRoom means File-Room
            Scanner fRoom = new Scanner(new File("Data/Rooms/" + roomName + ".dat"));
            ArrayList<Tile> tiles = new ArrayList<>();
            Room newRoom;
            String bg = "";
            while(fRoom.hasNextLine())
            {
                String name = fRoom.next();
                if(name.equals("μ"))
                {
                    //System.out.println("music be longing");
                    music.changeSong(fRoom.nextLine().substring(1));
                    //System.out.println("for real doe");
                    //fRoom.nextLine();
                    continue;
                }
                if(name.equals("β"))
                {
                    bg = fRoom.nextLine().substring(1);
                    continue;
                }
                if(name.equals("α"))
                {
                    //System.out.println("nae nae");
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
            //System.out.println("tiles done");
            newRoom = new Room(tiles);
            if(!bg.equals(""))
            {
                newRoom.setBg(bg);
            }
            ArrayList<Character> chars = new ArrayList<>();
            fRoom.nextLine();
            while(fRoom.hasNextLine())
            {
                String name = fRoom.next();
                if(name.equals("Δ")) break;
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
            //System.out.println("characters done");
            newRoom.setNPCs(chars);
            if(fRoom.hasNext()) fRoom.nextLine();
            ArrayList<Warp> warpA = new ArrayList<>();
            //System.out.println("hello");
            while(fRoom.hasNext())
            {
                int oldX = fRoom.nextInt();
                int oldY = fRoom.nextInt();
                int z = 0;
                int newX = fRoom.nextInt();
                int newY = fRoom.nextInt();
                //System.out.println(oldX+ " " + oldY + " vs. " + newX + " " + newY);
                String warpRoom = fRoom.nextLine().substring(1);
                warpA.add(new Warp(oldX,oldY,z,newX,newY, warpRoom));
            }
            //System.out.println("warps done");
            //System.out.println("helo");
            if(!warpA.isEmpty()) newRoom.setWarps(warpA);
            fRoom.close();
            //newRoom.determineScroll();
            return newRoom;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Room();
        }
    }
    //</editor-fold>

    public static int getScale() {
        return scale;
    }

    public static PChar getPlayer() {
        return player;
    }

    public static Music getMusic() {
        return music;
    }

    public static int goodRandom(int max, int min)
    {
        int range = max-min+1;
        int rand = (int)(Math.random() * range) + min;
        return rand;
    }
}
