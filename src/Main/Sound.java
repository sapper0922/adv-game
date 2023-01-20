package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];

    /**
     * 
     */
    public Sound() {

        soundURL[0] = getClass().getResource("/res/sound/BlueBoyAdventure.wav");
        soundURL[1] = getClass().getResource("res/sound/coin.wav");
        soundURL[2] = getClass().getResource("res/sound/powerup.wav");
        soundURL[3] = getClass().getResource("res/sound/unlock.wav");
        soundURL[4] = getClass().getResource("res/sound/fanfare.wav");
    }

    //basic Audio functions
    public void setFile(int i) {

        try {

            //This is a format to open an audio file in java
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
//            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public void play() {

        //Start the Audio files
        clip.start();

    }
    public void loop() {

        //Loops infinitely
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }
    public void stop() {

        //Stops playing Audio files
        clip.stop();

    }

}
