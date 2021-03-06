public class Thing extends Position {
    private String image;
    private String filePath;
    private String extension;

    public Thing() {
        super(0,0,0);
        image = "";
    }

    public Thing(int x, int y, int z, String image)
    {
        super(x,y,z);
        this.image = image;
    }

    //<editor-fold desc="Gets and sets">
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
    //</editor-fold>

    public boolean willCollide(Thing t)
    {
        if(getxPos() == t.getxPos() && getyPos() == t.getyPos())
        {
            return getzPos() <= t.getzPos();
        }
        return false;
    }

    //<editor-fold desc="Drawing Methods">
    public void draw()
    {
        StdDraw.picture(getxPos(), getyPos(), getImage());
    }
    public void draw(int scale)
    {
        StdDraw.picture(getxPos()*scale+scale/2, getyPos()*scale+scale/2, getFilePath() + getImage() + getExtension(), scale, scale);
    }

    public void draw(int scale, double minX, double minY)
    {
        StdDraw.picture(getxPos()*scale+scale/2+minX*scale, getyPos()*scale+scale/2+minY*scale, getFilePath() + getImage() + getExtension(), scale, scale);
    }

    public void draw(double xPos, double yPos, double scale, String filepath)
    {
        StdDraw.picture(xPos, yPos, filepath, scale, scale);
    }
    //</editor-fold>


}
