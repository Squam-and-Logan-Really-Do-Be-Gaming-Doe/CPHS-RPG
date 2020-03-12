import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    Tile[] tiles;
    ArrayList<Character> NPCs;

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
        int minX = -1;
        int minY = -1;
        int[] mins = new int[2];
        for (Tile temp :
                tiles) {
            if(temp.getxPos() > minX) minX = temp.getxPos();
            if(temp.getyPos() > minY) minY = temp.getyPos();
        }
        mins[0] = minX;
        mins[1] = minY;
        //System.out.println(Arrays.toString(mins));
        return mins;
    }


}
