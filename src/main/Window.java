package main;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Window {
	
	private Gui gui;
	private GameFrame gameframe;
	private int frame;
	
	private String[] mode=new String[1];
	
	public Window() {
		// Creates Window
		mode[0]="Menu";
		frame=0;
		gameframe=new GameFrame(mode, this);
		gui= new Gui (this, mode, gameframe);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		gui.setUndecorated(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setVisible(true);
		
	}
	
	public void update() {
		if(!mode[0].equals("Menu")) {
			frame++;
			if(frame==60) {
				gameframe.update();
				frame=0;
			}
		}
		gui.redraw();
	}
	
	public void setMode(String mode) {
		this.mode[0]=mode;
	}
	
	private static void playSound(File Sound) {// just put name of file
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
		}catch(Exception e) {
			
		}
	}

}
