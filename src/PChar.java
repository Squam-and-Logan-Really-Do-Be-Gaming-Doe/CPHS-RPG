public class PChar extends Character {
    private boolean moving;
    private double movFactor;
    public PChar(int x, int y, String image, String direction) {
        super(x, y, image, direction); moving = false;
    }

    public boolean moveIt()
    {
        if(!moving)
        {
            /*
            private final int up = 38;
            private final int down = 40;
            private final int left = 37;
            private final int right = 39;
             */
            boolean up = StdDraw.isKeyPressed(38);
            boolean down = StdDraw.isKeyPressed(40);
            boolean left = StdDraw.isKeyPressed(37);
            boolean right = StdDraw.isKeyPressed(39);
            if(up) setDirection("U");
            else if(down) setDirection("D");
            else if(left) setDirection("L");
            else if(right) setDirection("R");
            if(!up && !down && !left && !right)   moving = false;
            else
            {
                moving = true;
            }
        }
        else
        {
            movFactor += .02;
            if(movFactor >= 1)
            {
                movFactor = 0;
                switch (getDirection()) {
                    case "U":
                        setyPos(getyPos()+1);
                        break;
                    case "D":
                        setyPos(getyPos()-1);
                        break;
                    case "L":
                        setxPos(getxPos()-1);
                        break;
                    case "R":
                        setxPos(getxPos()+1);
                        break;
                }
                moving = false;
            }
            //System.out.println(movFactor);
        }
        return moving;
    }

    @Override
    public void draw(int scale)
    {
        double modX = getxPos();
        double modY = getyPos();
        switch (getDirection()) {
            case "U":
                modY += movFactor;
                break;
            case "D":
                modY -= movFactor;
                break;
            case "L":
                modX -= movFactor;
                break;
            case "R":
                modX += movFactor;
                break;
        }
        StdDraw.picture(modX*scale+scale/2, modY*scale+scale/2, getFilePath() + getImage() + getFrame() + getDirection()+ getExtension() , scale, scale);
    }

}
