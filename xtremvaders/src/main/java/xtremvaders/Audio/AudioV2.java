package xtremvaders.Audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioV2 extends Thread {

    private AudioInputStream audioInputStream;
    private SourceDataLine line;
    private String soundFile;
    private volatile float volume = 1.0f; // volume entre 0.0 (silence) et 1.0 (max)

    public AudioV2(String s) {
        this.soundFile = s;
    }

    /**
     * Change le volume (0.0 = silence, 1.0 = max).
     * Applique immédiatement si la ligne audio est ouverte.
     */
    public void setVolume(float volume) {
        if (volume < 0f) volume = 0f;
        if (volume > 1f) volume = 1f;
        this.volume = volume;

        if (line != null && line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float)(20.0 * Math.log10(volume <= 0.0001 ? 0.0001 : volume)); // éviter log(0)
            gainControl.setValue(dB);
        }
    }


     public void stopReimplemented() {
       this.setVolume(0);
    }


    @Override
    public void run() {
        try {
            // Récupérer le fichier ressource (ex : "son.wav")
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(soundFile + ".wav");
            if (url == null) {
                System.err.println("Fichier son introuvable : " + soundFile + ".wav");
                return;
            }
            File file = new File(url.getFile());

            // Charger le format audio
            audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();

            // Ouvrir la ligne audio
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            // Appliquer le volume initial
            setVolume(volume);

            // Buffer pour lecture
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            // Lecture et écriture dans la ligne audio
            while ((bytesRead = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.close();
            audioInputStream.close();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
