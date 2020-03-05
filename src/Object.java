public class Object {
    private Position pos;
    private String image;

    public Object() {
        pos = new Position();
        image = "";
    }

    public Object(Position pos, String image) {
        this.pos = pos;
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
