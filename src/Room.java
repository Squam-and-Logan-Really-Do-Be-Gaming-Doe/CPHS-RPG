import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {
    Tile[] tiles;

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
            Position pos = temp.getPos();
            StdDraw.picture(pos.getxPos()*scale+scale/2.0, pos.getyPos()*scale+scale/2.0, "Data/Tiles/" + temp.getImage() + ".png", scale, scale);
        }
    }


    @Override
    public String toString() {
        return "Room{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}
