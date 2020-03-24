import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    private Tile[] tiles;
    private ArrayList<Character> NPCs;
    private boolean scrollX;
    private boolean scrollY;
    private int camXMod = 0;
    private int camYMod = 0;

    public Room()
    {
        tiles = new Tile[0];
        NPCs = new ArrayList<>();
    }

    public Room(Tile[] tiles) {
        this.tiles = tiles;
    }

    public Room(ArrayList<Tile> Atiles)
    {
        tiles = new Tile[Atiles.size()];
        for (int i = 0; i < Atiles.size(); i++) {
            tiles[i] = Atiles.get(i);
        }
    }

    public void drawRoom()
    {
        int scale = Game.getScale();
        int[] mins = getStartPos();
        //15 x - spaces in a room
        //9 y - spaces in a room
        for (Tile temp : tiles) {
            temp.draw(scale, (15.0-mins[0])/2, (9.0-mins[1])/2);
        }
        drawNPCs(scale, mins);
    }

    public ArrayList<Character> getNPCs() {
        return NPCs;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setNPCs(ArrayList<Character> NPCs) {
        this.NPCs = NPCs;
    }

    private void drawNPCs(int scale)
    {
        for (Character guy : NPCs) {
            guy.draw(scale);
        }
    }
    private void drawNPCs(int scale, int[] mins)
    {
        for (Character guy : NPCs) {
            //System.out.println(guy.getxPos() + ", " + guy.getyPos() + " vs " + ((15.0-mins[0])/2)*guy.getxPos());
            guy.draw(scale, (15.0-mins[0])/2, (9.0-mins[1])/2);
        }
    }

    public void animate()
    {
        for (Character cha : NPCs) {
            cha.animate();
        }
    }

    public int[] getStartPos()
    {

        int maxX = -1;
        int maxY = -1;
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


}
