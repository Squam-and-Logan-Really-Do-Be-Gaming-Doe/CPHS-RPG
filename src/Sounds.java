public class Sounds {

    /*
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

     */
    public static void sfx(String sound)
    {
        StdAudio.play("Data/SFX/" + sound);
    }

    /*public static void changeSong(String nSong)
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
            StdAudio.close();
            loopMusic();
            return;
        }

    }

     */

    public static void textBlip()
    {
        /*
        //double freq = 440.0;
        double freq = (Math.random()*200)+400;
        //44100
        for (int i = 0; i <= StdAudio.SAMPLE_RATE; i++) {
            StdAudio.play(0.5 * Math.sin(2*Math.PI * freq * i / StdAudio.SAMPLE_RATE));
        }
        StdAudio.close();
        System.out.println("nae");
         */
        StdAudio.play("Data/Voice/deepVoice.wav");
    }
    public static void textBlip(String voice)
    {
        StdAudio.play(voice);
    }

}
