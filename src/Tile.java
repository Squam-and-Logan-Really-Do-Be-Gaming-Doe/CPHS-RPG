public class Tile extends Thing {

    public Tile(Position pos, String image) {
        super.setPos(pos);
        super.setImage(image);
    }

    public Tile(int xPos, int yPos, int zPos, String image) {
        super.setPos(new Position(xPos,yPos,zPos));
        super.setImage(image);
    }

}
