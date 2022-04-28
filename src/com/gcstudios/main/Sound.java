package com.gcstudios.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;
	
	public static final Sound shootEffect = new Sound("/shoot.wav");
	public static final Sound destroyEffect = new Sound("/destroy.wav");
	public static final Sound perdervidaEffect = new Sound("/menoslife.wav");
	
	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		}catch(Throwable e) {}
	}
	
	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		}catch(Throwable e) {}
	}
	
	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		}catch(Throwable e) {}
	}

	
}
