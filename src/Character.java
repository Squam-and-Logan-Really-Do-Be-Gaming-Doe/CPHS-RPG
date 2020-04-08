public class Character extends Thing {
    private String direction;
    private int frame;
    private String textPath;
    private String voicePath;

    //<editor-fold desc="Constructors">
    public Character(int x, int y, String image, String direction) {
        super(x, y, 1, image);
        this.direction = direction;
        extensionAndPath();
    }

    public Character(int x, int y, String image, String direction, String text, String voice) {
        super(x, y, 1, image);
        this.direction = direction;
        textPath = "Data/Speech/" + text;
        voicePath = "Data/Voice/" + voice + ".wav";
        extensionAndPath();
    }
    //</editor-fold>

    //<editor-fold desc="Gets And Sets">
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getFrame() {
        return frame;
    }

    public String getTextPath() {
        return textPath;
    }

    public void setTextPath(String textPath) {
        this.textPath = textPath;
    }

    public String getVoicePath() {
        return voicePath;
    }
    //</editor-fold>

    private void extensionAndPath()
    {
        setExtension(".png");
        setFilePath("Data/Characters/");
    }

    public void animate()
    {
        if (frame == 0) frame = 1;
        else if(frame == 1) frame = 0;
    }

    //<editor-fold desc="Drawing Methods">
    @Override
    public void draw(int scale) {
        setExtension(getFrame() + getDirection() + getExtension());
        super.draw(scale);
        extensionAndPath();
    }

    public void draw(int scale, double xMin, double yMin)
    {
        setExtension(getFrame() + getDirection() + getExtension());
        //System.out.println(getExtension());
        super.draw(getxPos()*scale+scale/2.0+xMin*scale,getyPos()*scale+scale/2.0+yMin*scale,scale, getFilePath() + getImage() + getExtension());
        extensionAndPath();
    }
    //</editor-fold>
}
