public class Tile {
    private Position pos;
    private String image;

    public Tile(Position pos, String image) {
        this.pos = pos;
        this.image = image;
    }

    public Tile(int xPos, int yPos, int zPos, String image) {
        pos = new Position(xPos,yPos,zPos);
        this.image = image;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
