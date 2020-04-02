public class Character extends Thing {
    private String direction;
    private int frame;
    private String textPath;
    private String voicePath;
    private Stats stats;
    public Character(int x, int y, String image, String direction) {
        super(x, y, 1, image);
        this.direction = direction;
        extensionAndPath();
    }
    public Character(int x, int y, String image, String direction, String text) {
        super(x, y, 1, image);
        this.direction = direction;
        textPath = "Data/Speech/" + text;
        extensionAndPath();
    }
    public Character(int x, int y, String image, String direction, String text, String voice) {
        super(x, y, 1, image);
        this.direction = direction;
        textPath = "Data/Speech/" + text;
        voicePath = "Data/Voice/" + voice + ".wav";
        extensionAndPath();
    }

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

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
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
        super.draw(getxPos()*scale+scale/2+xMin*scale,getyPos()*scale+scale/2+yMin*scale,scale, getFilePath() + getImage() + getExtension());
        extensionAndPath();
    }

    public String getVoicePath() {
        return voicePath;
    }
}
