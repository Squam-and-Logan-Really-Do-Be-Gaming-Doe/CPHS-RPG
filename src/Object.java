public class Object extends Position {
    private String image;

    public Object() {
        super(0,0,0);
        image = "";
    }

    public Object(Position pos, String image) {
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
