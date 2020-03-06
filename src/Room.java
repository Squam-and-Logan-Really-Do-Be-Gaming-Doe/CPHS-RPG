import java.util.ArrayList;
import java.util.List;

public class Room {
    Tile[] tiles;

    public Room(Tile[] tiles) {
        this.tiles = tiles;
    }

    public Room(ArrayList<Tile> tiles)
    {
        this.tiles = (Tile[]) tiles.toArray();
    }

    public void drawRoom()
    {
        for (Tile temp : tiles) {
            Position pos = temp.getPos();
            StdDraw.picture(pos.getxPos(), pos.getyPos(), "Data/Rooms/" + temp.getImage(), 16, 16);
        }
    }



}
