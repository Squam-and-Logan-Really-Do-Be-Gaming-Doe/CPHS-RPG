public class Sounds {
    public static String cSong;
    private static void loopMusic()
    {
        //System.out.println("whats crackin");
        //Game.loadingScreen();
        try{
            StdAudio.loop("Data/Music/" + cSong);
            //StdAudio.main(new String[]{""});
        }
        catch (Exception e) {e.printStackTrace();}
        //System.out.println("hello");
    }
    public static void sfx(String sound)
    {
        StdAudio.play("Data/SFX/" + sound);
    }
    public static void changeSong(String nSong)
    {
        if(cSong == null)
        {
            cSong = nSong;
            loopMusic();
            return;
        }
        if(!cSong.equals(nSong))
        {
            cSong = nSong;
            loopMusic();
            return;
        }

    }

}