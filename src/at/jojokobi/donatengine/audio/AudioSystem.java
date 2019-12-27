package at.jojokobi.donatengine.audio;

import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioSystem {

//	private List<MediaPlayer> playing = new ArrayList<>();
	
	public void playSound (Media sound) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MediaPlayer player = new MediaPlayer(sound);
				player.play();
			}
		});
//		new Thread(new Runnable() {
//			@Override
//			public synchronized void run() {
//				playing.add(player);
//				player.setOnStopped(new Runnable() {
//					@Override
//					public synchronized void run() {
//						playing.remove(player);
//					}
//				});
//				player.setOnEndOfMedia(new Runnable() {
//					@Override
//					public synchronized void run() {
//						playing.remove(player);
//						System.out.println("End of sound");
//					}
//				});
//			}
//		}).start();
	}
	
	public void playSound (SoundEvent sound) {
		playSound(sound.chooseSound());
	}
	
	public void playSound (AudioClip sound) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				sound.play();
			}
		});
//		new Thread(new Runnable() {
//			@Override
//			public synchronized void run() {
//				playing.add(player);
//				player.setOnStopped(new Runnable() {
//					@Override
//					public synchronized void run() {
//						playing.remove(player);
//					}
//				});
//				player.setOnEndOfMedia(new Runnable() {
//					@Override
//					public synchronized void run() {
//						playing.remove(player);
//						System.out.println("End of sound");
//					}
//				});
//			}
//		}).start();
	}

}
