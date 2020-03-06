public class Thing extends Position {
    private String image;

    public Thing() {
        super(0,0,0);
        image = "";
    }

    public Thing(Position pos, String image) {
        super(pos);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
