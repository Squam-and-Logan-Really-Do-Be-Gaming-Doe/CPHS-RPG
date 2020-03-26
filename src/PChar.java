public class PChar extends Character {
    private boolean moving;
    private double movFactor;
    public PChar(int x, int y, String image, String direction) {
        super(x, y, image, direction); moving = false;
    }

    public boolean moveIt(Room cRoom)
    {
        if(!moving)
        {
            /*
            private final int up = 38;
            private final int down = 40;
            private final int left = 37;
            private final int right = 39;
             */
            boolean up = StdDraw.isKeyPressed(Game.up);
            boolean down = StdDraw.isKeyPressed(Game.down);
            boolean left = StdDraw.isKeyPressed(Game.left);
            boolean right = StdDraw.isKeyPressed(Game.right);
            if(up) setDirection("U");
            else if(down) setDirection("D");
            else if(left) setDirection("L");
            else if(right) setDirection("R");
            moving = up || down || left || right;
        }
        else
        {
            if(StdDraw.isKeyPressed(Game.cancel))
            {
                movFactor += .04;
            }
            movFactor += .04;
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
        //System.out.println(moving);
        double[] smoth = mods();
        if(moving)
            for (int i = 0; i < cRoom.getNPCs().size(); i++) {
                if(willCollide(cRoom.getNPCs().get(i), smoth))
                {
                    moving = false;
                    movFactor = 0;
                    break;
                }
            }
        if(moving)
            for (int i = 0; i < cRoom.getTiles().length; i++) {
                if(willCollide(cRoom.getTiles()[i], smoth))
                {
                    moving = false;
                    movFactor = 0;
                    break;
                }
            }
        return moving;
    }

    private double[] mods()
    {
        double modX = 0;
        double modY = 0;
        //String path = getDirection();
        if(movFactor != 0)
            switch (getDirection()) {
                case "U":
                    modY += movFactor;
                    //path += "U";
                    break;
                case "D":
                    modY -= movFactor;
                    //path += "D";
                    break;
                case "L":
                    modX -= movFactor;
                    //path += "L";
                    break;
                case "R":
                    modX += movFactor;
                    //path += "R";
                    break;
            }
        //System.out.println(path);
        return new double[]{modX, modY};
    }

    public double[] smoother()
    {
        double modX = getxPos();
        double modY = getyPos();
        double[] mods = mods();
        modX += mods[0];
        modY += mods[1];
        return new double[]{modX, modY};
    }

    public boolean willCollide(Thing t, double[] smoth) {
        int checkX = (int)(getxPos()+Math.ceil(smoth[0]));
        int checkY = (int)(getyPos()+Math.ceil(smoth[1]));
        if(smoth[0]< 0)checkX = (int)(getxPos()+Math.floor(smoth[0]));
        if(smoth[1]< 0)checkY = (int)(getyPos()+Math.floor(smoth[1]));
        //System.out.println( checkX + "," + checkY + " vs. " + t.getxPos() + "," + t.getyPos());
        if(checkX == t.getxPos() && checkY == t.getyPos())
        {
            //System.out.println("ya");
            //System.out.println("yeet");
            return getzPos() <= t.getzPos();
        }
        return false;
    }

    public void interact(Room cRoom)
    {
        int checkX = getxPos();
        int checkY = getyPos();
        switch (getDirection())
        {
            case "U":
                checkY +=1;
                break;
            case "D":
                checkY -=1;
                break;
            case "L":
                checkX -=1;
                break;
            case "R":
                checkX +=1;
                break;
        }
        Character talkTo = null;
        for (Character dude :
                cRoom.getNPCs()) {
            if (dude.getxPos() == checkX && dude.getyPos() == getyPos())
            {
                talkTo = dude;
            }
        }
        if(talkTo != null)
        {

        }

    }

    @Override
    public void draw(int scale)
    {
        double[] mods = smoother();
        double modX = mods[0];
        double modY = mods[1];
        StdDraw.picture(modX*scale+scale/2, modY*scale+scale/2, getFilePath() + getImage() + getFrame() + getDirection()+ getExtension() , scale, scale);
    }

    public void draw(int scale, double xMin, double yMin)
    {
        double[] mods = smoother();
        double modX = mods[0];
        double modY = mods[1];
        //System.out.println(getExtension());
        super.draw(modX*scale+scale/2+xMin*scale,modY*scale+scale/2+yMin*scale,scale, getFilePath() + getImage() + getFrame() + getDirection()+ getExtension());
    }

}
