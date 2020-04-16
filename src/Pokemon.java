import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Pokemon extends Stats{
    private String name;
    private String nickName;
    private Move[] moves;

    public Pokemon(String stat, String name, Move[] moves) {
        super(stat);
        this.name = name;
        nickName = name;
        this.moves = moves;
    }
    public Pokemon(String stat, String name, Move[] moves, String nickName)
    {
        super(stat);
        this.name = name;
        this.nickName = nickName;
        this.moves = moves;
    }

    //<editor-fold desc="Gets">
    public String getNickName() {
        return nickName;
    }

    public String getName() {
        return name;
    }

    public Move[] getMoves() {
        return moves;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", moves=" + Arrays.toString(moves) +
                '}';
    }
    //</editor-fold>
}

class Move
{
    //<editor-fold desc="Variables">
    private final String name;
    private String type;
    private int cUses;
    private int maxUses;
    private int movType;
    private int power;
    private int accuracy;
    private boolean[] targets; //0 yourself 1 enemy
    //</editor-fold>
    /*
    Move Types:
    0. Attacking

    1. Change attack
    2. Change Defense
    3. Change Speed
    4. Change accuracy
     */
    public Move()
    {
        name = "DUMMY";
        type = "Normal";
        cUses = 0;
        maxUses = 0;
        movType = 0;
        power = 0;
        accuracy = 0;
        targets = new boolean[2];
    }

    public Move(String movName)
    {
        name = movName;
        readFromFile();
    }

    private void readFromFile()
    {
        try {
            Scanner info = new Scanner(new File("Data/Battling/Moves/" + name + ".dat"));
            type = info.nextLine();
            maxUses = Integer.parseInt(info.nextLine());
            cUses = maxUses;
            power = Integer.parseInt(info.nextLine());
            accuracy = Integer.parseInt(info.nextLine());
            movType = Integer.parseInt(info.nextLine());
            String STargets = info.nextLine();
            int targ1 = Integer.parseInt(STargets.substring(0,STargets.indexOf(" ")));
            int targ2 = Integer.parseInt(STargets.substring(STargets.indexOf(" ")+1));
            targets = new boolean[2];
            if(targ1 == 0) targets[0] = false;
            else if(targ1 == 1) targets[0] = true;
            if(targ2 == 0) targets[1] = false;
            else if(targ2 == 0) targets[1] = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //<editor-fold desc="gets">
    public String getName() {
        return name;
    }

    public int getcUses() {
        return cUses;
    }

    public int getMaxUses() {
        return maxUses;
    }
    //</editor-fold>

    public void use(Pokemon user, Pokemon victim)
    {
        if(cUses == 0)
        {
            struggle(user, victim);
            return;
        }
        TextHandler.textRead(user.getName() + " uses " + name, "Data/Voice/medVoice.wav");
        cUses--;
        int damage = 0;
        switch (movType)
        {
            //attacking
            case(0):
                damage = ((((2*user.getLevel())/5+2)*power*(user.getPow()/victim.getDef()))/50+2);
        }
        victim.takeDamage(damage);

    }
    public void struggle(Pokemon user, Pokemon victim)
    {

    }

    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", cUses=" + cUses +
                ", maxUses=" + maxUses +
                ", movType=" + movType +
                ", power=" + power +
                ", accuracy=" + accuracy +
                ", targets=" + Arrays.toString(targets) +
                '}';
    }
}
