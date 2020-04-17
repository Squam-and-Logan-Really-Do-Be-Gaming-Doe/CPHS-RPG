import java.util.Arrays;
import java.util.Scanner;

public class Stats {

    /*
    private int level; 0
    private int hp;    1
    private int sp;    2
    private int pow;   3
    private int def;   4
    private int speed; 5
    private int luck;  6
    //private int loveGems; not

     */
    /*
    private int level; 0
    private int hp;    1
    private int pow;   2
    private int def;   3
    private int cPow;  4
    private int cDef;  5
    private int speed; 6
     */


    private int[] stats;
    private int[] buffs;
    private int exp;
    private int cHP;
    //private int[] growths;

    /*public Stats(int[] stats, int[] growths) {
        this.stats = stats;
        //this.growths = growths;
    }

     */

    public Stats(String stat)
    {
        stats = new int[7];
        Scanner info = new Scanner(stat);
        for (int i = 0; i < stats.length; i++) {
            stats[i] = info.nextInt();
        }
        buffs = new int[7];
        Arrays.fill(buffs, 0);
        cHP = getHP();
    }
    public Stats() {
        this.stats = new int[7];
        //this.growths = new int[stats.length];
    }

    public void setcHP(int cHP1)
    {
        cHP = cHP1;
    }

    public void takeDamage(int damage)
    {
        cHP -= damage;
        if (cHP < 0) cHP = 0;
    }

    /*public boolean[] levelUP()
    {
        boolean[] up = new boolean[stats.length];
        for (int i = 0; i < stats.length; i++) {
            int num = (int)(Math.random()*100);
            if(num <= growths[i])
            {
                stats[i] +=1;
                up[i] = true;
            }
        }
        return up;
    }

     */

    /*
    public int getLevel()
    {
        return stats[0];
    }
    public int getHP()
    {
        return stats[1];
    }
    public int getSP()
    {
        return stats[2];
    }
    public int getPow()
    {
        return stats[3];
    }
    public int getDef()
    {
        return stats[4];
    }
    public int getSpeed(){
        return stats[5];
    }
    public int getLuck(){
        return stats[6];
    }
     */

    public int getLevel(){return stats[0];}
    public int getHP(){return stats[1];}
    public int getPow(){return stats[2];}
    public int getDef(){return stats[3];}
    public int getCoolPow(){return stats[4];}
    public int getCoolDef(){return stats[5];}
    public int getSpeed(){return stats[6];}

    public int getCHP() {
        return cHP;
    }
    public int getExp() {
        return exp;
    }

    public int getBuffedPow(){return buffs[2]+getPow();}
    public int getBuffedDef(){return buffs[3]+getDef();}
    public int getBuffedCoolPow(){return buffs[4]+getCoolPow();}
    public int getBuffedCoolDef(){return buffs[5]+getCoolDef();}
    public int getBuffedSpeed(){return buffs[6]+getSpeed();}

    public void resetBuffs()
    {
        Arrays.fill(buffs, 0);
    }

    public boolean buffPow(int buff)
    {
        //System.out.println(getPow());
        buffs[2] += (int)(Math.round(buff/4.5*getPow()));
        //System.out.println(buffs[2]);
        return threshHold(2);

    }
    public boolean buffStat(int buff, int stat)
    {
        buffs[stat] += (int)(Math.round(buff/4.5*stats[stat]));
        return threshHold(stat);
    }

    private boolean threshHold(int stat)
    {
        int threshHold = 1;
        if(buffs[stat] + stats[stat] <threshHold)
        {
            buffs[stat] = Math.negateExact(stats[stat])+threshHold;
            return false;
        }
        return true;
    }

}
