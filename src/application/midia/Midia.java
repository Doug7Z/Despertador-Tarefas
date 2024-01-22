package application.midia;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Midia {
	public static void midiaplayer() {
	    String url = Midia.class.getResource("/Sino.mp3").toExternalForm();
	    Media media = new Media(url);
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.play();
	}
}
