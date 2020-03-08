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
        int scale = 160;
        for (Tile temp : tiles) {
            temp.draw(scale);
        }
        drawNPCs(scale);
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

    public void animate()
    {
        for (Character cha : NPCs) {
            cha.animate();
        }
    }

}
