import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class Menu {
    private static final int xLoc = 1100;
    private static final int yLoc = 360;
    private static final int wid = 170;
    private static final int height = 300;
    //360 + 280 = 640 is the top
    //360 - 280 = 80  is the bottom
    //Space = 560
    //560/5 = 112
    //640 and 80??
    public static void startMenu()
    {
        Sounds.sfx("menuOpen.wav");
        int selector = 0;

        boolean yopoU = false;
        boolean yopoD = false;
        boolean yopoCan = true;

        while(true)
        {
            //System.out.println(StdDraw.mouseX() + " " + StdDraw.mouseY());
            if(StdDraw.isKeyPressed(Game.cancel) && !yopoCan) return;
            if(StdDraw.isKeyPressed(Game.confirm)) break;

            if(StdDraw.isKeyPressed(Game.up) && !yopoU)
            {
                selector -=1;
                Sounds.sfx("selector.wav");
            }
            if(StdDraw.isKeyPressed(Game.down) && !yopoD)
            {
                selector +=1;
                Sounds.sfx("selector.wav");
            }

            if(selector > 4) selector = 0;
            if(selector < 0) selector = 4;

            drawAll(selector);

            yopoU = StdDraw.isKeyPressed(Game.up);
            yopoD = StdDraw.isKeyPressed(Game.down);
            yopoCan = StdDraw.isKeyPressed(Game.cancel);
            StdDraw.show();
            Game.goodSleep();

        }
        Sounds.sfx("selected.wav");

        if(selector == 3)
        {
            save();
        }

        if(selector == 4)
        {
            optionMenu();
            //startMenu();
        }

    }

    private static void drawAll(int i)
    {
        drawSideBar();
        drawSelector(i);
        drawSelections();
    }

    private static void drawSideBar()
    {
        StdDraw.setPenColor(Color.black);
        StdDraw.filledRectangle(xLoc, yLoc, wid, height);

        StdDraw.setPenColor(Color.white);
        StdDraw.filledRectangle(xLoc,yLoc, wid-20, height-20);
        //StdDraw.show();
    }

    private static void drawSelector(int i)
    {
        StdDraw.setPenColor(Color.yellow);
        StdDraw.filledRectangle(xLoc,640-56-(i*112), wid-22, 54);
    }

    private static void drawSelections()
    {
        /*
        Selections:
        Gang
        Items
        Hoodlum-Dex
        Save
        Options
         */

        StdDraw.setPenColor(Color.black);

        String[] Selections = new String[]{"Gang","Items","Hoodlum-Log","Save","Options"};
        for (int i = 0; i < Selections.length; i++) {
            StdDraw.textLeft(xLoc-27,640-56-(i*112), Selections[i]);
            try
            {
                StdDraw.picture(xLoc-150+54, 640-56-(i*112), "Data/Icons/" + Selections[i] + ".png", 108, 108);
            }
            catch (Exception e)
            {
                //e.printStackTrace();
            }
        }

    }

    private static void save()
    {
        int choice = TextHandler.choice("Would you like to save your game?;YES;NO", "Data/Voice/medVoice.wav");
        if(choice == 0)
        {
            TextHandler.textRead("Now saving, please do not turn the power off bro", "Data/Voice/medVoice.wav");
            saveFileWrite();
        }
        else
        {
            TextHandler.textRead("Ok well fine then have it your way", "Data/Voice/medVoice.wav");
        }
    }

    private static void saveFileWrite()
    {
            try {
                File file = new File("Data/Saves/save" + Game.getSaveSlot() + ".dat");

                //Create the file
                if (file.createNewFile()) {
                    System.out.println("File is created!");
                } else {
                    System.out.println("File already exists.");
                }

                //Write Content
                PChar player = Game.getPlayer();
                FileWriter writer = new FileWriter(file);
                writer.write("Chapter " + Game.getChapter());
                writer.write("\n");
                writer.write(Game.getNameChapter());
                writer.write("\n");
                writer.write(Game.getTimeFrame());
                writer.write("\n");
                writer.write(player.getRoomName());
                writer.write("\n");
                writer.write(player.getxPos() + " " + player.getyPos());
                writer.write("\n");
                writer.write(player.getName());
                writer.write("\n");
                writer.write("" + player.getPokemons().length);
                //System.out.println("len is " + player.getPokemons().length);
                //writer.write(player.getPokemons().length);
                writer.write("\n");
                for (int i = 0; i < player.getPokemons().length; i++) {
                    Pokemon poke = player.getPokemons()[i];
                    writer.write(poke.getName());
                    writer.write("\n");
                    writer.write(poke.getNickName());
                    writer.write("\n");
                    writer.write(poke.asString());
                    writer.write("\n");
                    int moves = poke.getMoves().length;
                    writer.write("" + moves);
                    //System.out.println("moves are " + moves);
                    writer.write("\n");
                    for (int j = 0; j < moves; j++) {
                        Move mov = poke.getMoves()[j];
                        writer.write(mov.getName());
                        writer.write("\n");
                    }
                }
                writer.close();
            } catch (Exception e) {
                System.out.println("dang you really do be not working doe");
            }
    }

    //<editor-fold desc="Options">
    public static void optionMenu()
    {
        int index = 0;
        String[] ratios = aspectRatios();
        StdDraw.clear();
        boolean yopoL = false;
        boolean yopoR = false;
        boolean yopoS = true;
        boolean yopoC = true;

        while ((!StdDraw.isKeyPressed(Game.confirm) || yopoS) && (!StdDraw.isKeyPressed(Game.cancel) || yopoC)) {
            //System.out.println(index);
            String xRat = getRightRatio(ratios, index, 0);
            String yRat = getRightRatio(ratios, index, 1);
            StdDraw.text(640, 360, "Screen-Size:");
            StdDraw.textLeft(715, 360, xRat + " x " + yRat);
            if (StdDraw.isKeyPressed(Game.left) && !yopoL) {
                Sounds.sfx("Selector.wav");
                index--;
            }
            if (StdDraw.isKeyPressed(Game.right) && !yopoR) {
                Sounds.sfx("Selector.wav");
                index++;
            }
            if (index < 0) index = ratios.length - 1;
            if (index > ratios.length - 1) index = 0;

            yopoC = StdDraw.isKeyPressed(Game.cancel);
            yopoS = StdDraw.isKeyPressed(Game.confirm);
            yopoL = StdDraw.isKeyPressed(Game.left);
            yopoR = StdDraw.isKeyPressed(Game.right);

            Game.goodSleep();
            StdDraw.show();
            StdDraw.clear();
        }
        if(StdDraw.isKeyPressed(Game.cancel))
        {
            Sounds.sfx("Cancel.wav");
        }
        else if(StdDraw.isKeyPressed(Game.confirm))
        {
            Sounds.sfx("Selected.wav");
            int xRat = Integer.parseInt(getRightRatio(ratios,index,0));
            int yRat = Integer.parseInt(getRightRatio(ratios,index,1));
            StdDraw.setCanvasSize(xRat, yRat);
            //System.out.println(StdDraw.getFont().getSize());
            int oldX = Integer.parseInt(getRightRatio(ratios,0,0));
            double scale = xRat/(double)oldX;
            Font oldFont = StdDraw.getFont();
            int newSize = (int) Math.round(oldFont.getSize()*scale);
            StdDraw.setFont(new Font(oldFont.getName(),oldFont.getStyle(),newSize));
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0,Game.xSize);
            StdDraw.setYscale(0, Game.ySize);
            StdDraw.show();
        }

    }
    private static String getRightRatio(String[] ratios, int index, int xOrY)
    {
        if(xOrY == 0)
        {
            return (ratios[index].substring(0,ratios[index].indexOf("x")));
        }
        if(xOrY == 1)
        {
            return (ratios[index].substring(ratios[index].indexOf("x")+1));
        }
        return "7";
    }

    private static String[] aspectRatios()
    {
        return new String[]{"768x432","1024x576","1152x648","1280x720","1366x768",
                "1600x900","1920x1080","2560x1440","3840x2160"};
    }
    //</editor-fold>


}
