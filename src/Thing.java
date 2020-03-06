public class Thing extends Position {
    private String image;
    private String filePath;
    private String extension;

    public Thing() {
        super(0,0,0);
        image = "";
    }

    public Thing(Position pos, String image) {
        super(pos);
        this.image = image;
    }

    public Thing(int x, int y, int z, String image)
    {
        super(x,y,z);
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void draw()
    {
        StdDraw.picture(getxPos(), getyPos(), getImage());
    }
    public void draw(int scale)
    {
        StdDraw.picture(getxPos()*scale+scale/2, getyPos()*scale+scale/2, getFilePath() + getImage() + getExtension(), scale, scale);
    }
}
