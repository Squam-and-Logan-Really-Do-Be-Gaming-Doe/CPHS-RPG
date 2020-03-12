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

    private int[] stats;
    private int[] growths;

    public Stats(int[] stats, int[] growths) {
        this.stats = stats;
        this.growths = growths;
    }

    public Stats() {
        this.stats = new int[7];
        this.growths = new int[stats.length];
    }

    public boolean[] levelUP()
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


}
