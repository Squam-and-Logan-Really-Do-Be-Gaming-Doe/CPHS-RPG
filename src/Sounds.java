public class Sounds {

    public static void sfx(String sound)
    {
        StdAudio.play("Data/SFX/" + sound);
    }

    public static void textBlip()
    {
        StdAudio.play("Data/Voice/deepVoice.wav");
    }
    public static void textBlip(String voice)
    {
        if(!voice.equals(""))
        {
            try {
                StdAudio.play(voice);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                textBlip();
            }
        }
    }

    public static void cry(String poke)
    {
        StdAudio.play("Data/Battling/Pokemon/" + poke + "/cry.wav");
    }

}
