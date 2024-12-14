package src;


import javax.sound.sampled.*;
import java.io.File;
    
public class BackgroundMusic extends Thread {
    private String fileName;

    public BackgroundMusic(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            File musicFile = new File("Assets/bgm/" + fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Gagal memutar musik: " + e.getMessage());
        }
    }
}


