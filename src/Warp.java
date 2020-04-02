public class Warp extends Position{
    private int newX;
    private int newY;
    private String newRoom;

    public Warp(int xPos, int yPos, int zPos, int newX, int newY, String newRoom) {
        super(xPos, yPos, zPos);
        this.newX = newX;
        this.newY = newY;
        this.newRoom = newRoom;
    }

    public int getNewX() {
        return newX;
    }

    public void setNewX(int newX) {
        this.newX = newX;
    }

    public int getNewY() {
        return newY;
    }

    public void setNewY(int newY) {
        this.newY = newY;
    }

    public String getNewRoom() {
        return newRoom;
    }

    public void setNewRoom(String newRoom) {
        this.newRoom = newRoom;
    }
}
