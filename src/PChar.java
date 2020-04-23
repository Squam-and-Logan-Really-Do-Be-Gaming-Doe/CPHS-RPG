import java.util.Arrays;

public class PChar extends Character {
    private String name;
    private boolean moving;
    private double movFactor;
    private Pokemon[] pokemons;
    private String roomName;


    public PChar(int x, int y, String image, String direction) {
        super(x, y, image, direction); moving = false;
    }

    public Pokemon[] getPokemons() {
        return pokemons;
    }

    public void setPokemons(Pokemon[] pokemons) {
        this.pokemons = pokemons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    //<editor-fold desc="Movement">
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
            if(!canMove(cRoom))
            {
                moving = false;
                movFactor = 0;
            }
        }
        //System.out.println(moving);
        //if(moving)
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

    public boolean willCollide(Thing t) {
        double[] check = findCheck();

        double checkX = check[0];
        double checkY = check[1];
        //System.out.println( checkX + "," + checkY + " vs. " + t.getxPos() + "," + t.getyPos());
        if(checkX == t.getxPos() && checkY == t.getyPos())
        {
            //System.out.println("ya");
            //System.out.println("yeet");
            return getzPos() <= t.getzPos();
        }
        return false;
    }

    private boolean hasTile(Room cRoom, double x, double y)
    {
        for (int i = 0; i < cRoom.getTiles().length; i++) {
            Tile test = cRoom.getTiles()[i];
            if(test.getxPos() == x && test.getyPos() == y) return true;
        }

        return false;
    }

    public boolean canMove(Room cRoom)
    {
        for (int i = 0; i < cRoom.getNPCs().size(); i++) {
            if(willCollide(cRoom.getNPCs().get(i)))
            {
                return false;
            }
        }
        for (int i = 0; i < cRoom.getTiles().length; i++) {
            if (willCollide(cRoom.getTiles()[i])) {
                return false;
            }
        }

        double[] check = findCheck();

        double checkX = check[0];
        double checkY = check[1];

       return (hasTile(cRoom, checkX, checkY));

    }

    private double[] findCheck()
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

        return new double[]{checkX,checkY};


    }

    public void interact(Room cRoom)
    {
        double[] check = findCheck();
        double checkX = check[0];
        double checkY = check[1];
        Character talkTo = null;
        for (Character dude :
                cRoom.getNPCs()) {
            if (dude.getxPos() == checkX && dude.getyPos() == checkY)
            {
                talkTo = dude;
            }
        }
        if(talkTo != null) {
            switch (getDirection()) {
                case "U":
                    talkTo.setDirection("D");
                    break;
                case "D":
                    talkTo.setDirection("U");
                    break;
                case "L":
                    talkTo.setDirection("R");
                    break;
                case "R":
                    talkTo.setDirection("L");
                    break;
            }

            if (talkTo.getTextPath() != null) {
                cRoom.drawRoom();
                int[] maxs = cRoom.getMaxPos();
                draw(Game.getScale(), Room.centerX(maxs[0]), Room.centerY(maxs[1]));
                TextHandler.textRead(talkTo.getTextPath(),talkTo.getVoicePath());

            }
        }
    }
    //</editor-fold>



    @Override
    public void draw(int scale)
    {
        double[] mods = smoother();
        double modX = mods[0];
        double modY = mods[1];
        StdDraw.picture(modX*scale+scale/2.0, modY*scale+scale/2.0, getFilePath() + getImage() + getFrame() + getDirection()+ getExtension() , scale, scale);
    }

    public void draw(int scale, double xMin, double yMin)
    {
        double[] mods = smoother();
        double modX = mods[0];
        double modY = mods[1];
        //System.out.println(getExtension());
        super.draw(modX*scale+scale/2.0+xMin*scale,modY*scale+scale/2.0+yMin*scale,scale, getFilePath() + getImage() + getFrame() + getDirection()+ getExtension());
    }


    @Override
    public String toString() {
        return "PChar{" +
                "name='" + name + '\'' +
                ", moving=" + moving +
                ", movFactor=" + movFactor +
                ", pokemons=" + Arrays.toString(pokemons) +
                '}';
    }
}
