import java.util.ArrayList;

public class Room {
    private Tile[] tiles;
    private ArrayList<Character> NPCs;
    private Warp[] warps;
    private String bg;
    /*
    private boolean scrollX;
    private boolean scrollY;
    private double camXMod = 0;
    private double camYMod = 0;

     */

    public Room()
    {
        tiles = new Tile[0];
        NPCs = new ArrayList<>();
    }

    public Room(ArrayList<Tile> Atiles)
    {
        tiles = new Tile[Atiles.size()];
        for (int i = 0; i < Atiles.size(); i++) {
            tiles[i] = Atiles.get(i);
        }
    }

    //<editor-fold desc="Drawing Methods">
    public void drawRoom()
    {
        int scale = Game.getScale();
        int[] maxs = getMaxPos();
        //15 x - spaces in a room
        //9 y - spaces in a room
        //drawBG();
        for (Tile temp : tiles) {
            temp.draw(scale, centerX(maxs[0]), centerY(maxs[1]));
        }
        drawNPCs(scale, maxs);
    }
    private void drawNPCs(int scale, int[] maxs)
    {
        for (Character guy : NPCs) {
            //System.out.println(guy.getxPos() + ", " + guy.getyPos() + " vs " + ((15.0-mins[0])/2)*guy.getxPos());
            guy.draw(scale, centerX(maxs[0]), centerY(maxs[1]));
        }
    }
    public void drawBG()
    {
        if(bg != null)
        {
            StdDraw.picture((Game.xSize)/2.0,(Game.ySize)/2.0,"Data/BG/" + bg, Game.xSize, Game.ySize);
            //StdDraw.setPenColor(Color.black);
        }
    }

    public static double centerX(double maxX)
    {
        return ((Game.xSize/(double)Game.scale)-maxX-1)/2;
    }
    public static double centerY(double maxY)
    {
        return ((Game.ySize/(double)Game.scale)-maxY-1)/2;
    }

    public void animate()
    {
        for (Character cha : NPCs) {
            cha.animate();
        }
    }
    //</editor-fold>

    //<editor-fold desc="Gets and Sets">
    public ArrayList<Character> getNPCs() {
        return NPCs;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Warp[] getWarps() {
        return warps;
    }

    public void setWarps(ArrayList<Warp> warpArrayList)
    {
        warps = new Warp[warpArrayList.size()];
        for (int i = 0; i < warpArrayList.size(); i++) {
            warps[i] = warpArrayList.get(i);
        }
    }

    public void setBg(String bg1)
    {
        bg = bg1;
    }

    //<editor-fold desc="Max And Min">
    public int[] getMaxPos()
    {

        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int[] maxs = new int[2];
        for (Tile temp :
                tiles) {
            if(temp.getxPos() > maxX) maxX = temp.getxPos();
            if(temp.getyPos() > maxY) maxY = temp.getyPos();
        }
        maxs[0] = maxX;
        maxs[1] = maxY;
        //System.out.println(Arrays.toString(maxs));
        return maxs;
    }
    public int[] getMinPos()
    {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int[] mins = new int[2];
        for (Tile temp :
                tiles) {
            if(temp.getxPos() < minX) minX = temp.getxPos();
            if(temp.getyPos() < minY) minY = temp.getyPos();
        }
        mins[0] = minX;
        mins[1] = minY;
        //System.out.println(Arrays.toString(maxs));
        return mins;
    }

    public int[] maxAndMin()
    {
        int[] mins = getMinPos();
        int[] maxs = getMaxPos();
        int[] both = new int[4];

        both[0] = mins[0];
        both[1] = mins[1];
        both[2] = maxs[0];
        both[3] = maxs[1];

        return both;
    }
    //</editor-fold>

    /*
    public boolean isScrollX() {
        return scrollX;
    }

    public boolean isScrollY() {
        return scrollY;
    }

    public boolean isScroll()
    {
        return scrollX || scrollY;
    }

    public void determineScroll()
    {
        scrollX = false; scrollY = false;
        int[] both = maxAndMin();

        int min = both[0];
        int max = both[2];
        if(max-min>15) scrollX = true;

        min = both[1];
        max = both[3];

        if(max-min>9) scrollY = true;

    }

     */

    public void setNPCs(ArrayList<Character> NPCs) {
        this.NPCs = NPCs;
    }
    //</editor-fold>

    /*
    public void scrollModifier(PChar chara)
    {
        double[] smoth = chara.smoother();
        int[] both = maxAndMin();
        double charX = chara.getxPos()+ smoth[0];
        double charY = chara.getyPos()+ smoth[1];
        if(scrollX)
        {
            int minX = both[0];
            int maxX = both[2];
            int difference = maxX-minX;
            if(charX < difference/2.0)
            {

            }
        }
    }

     */
}
