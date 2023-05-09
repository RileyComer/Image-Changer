package displays;
import java.awt.Color;
import java.awt.Graphics;

import main.GameFrame;
import main.Gui;

public class Mode2Display{
	
	private Gui gui;
	private GameFrame gameframe;
	private int size,startX,startY,frameW,frameH;
	//game stuff
	
	public Mode2Display(Gui gui1, GameFrame gameframe){
		gui=gui1;
		this.gameframe=gameframe;
		frameW=gameframe.getWidth();
		frameH=gameframe.getHeight();
	}
	
	public void render(Graphics g) {
		if((gui.getWidth()/frameW)<gui.getHeight()/frameH) {
			size=gui.getWidth()/frameW;
		}else {
			size=gui.getHeight()/frameH;
		}
		if(size==0) {
			size=1;
		}
		
		startX=(int)((gui.getWidth()/2.0)-((size*frameW)/2.0));
		startY=(int)((gui.getHeight()/2.0)-((size*frameH)/2.0));
		
		Color[][] input=gameframe.getInput();
		
		int gray=0;
		
		double red=0.299;
		double green=0.587;
		double blue=0.114;
		
		for(int y=0; y< frameH; y++) {
			for(int x=0; x<frameW;x++) {
				gray=(int)((red*input[x][y].getRed()+green*input[x][y].getGreen()+blue*input[x][y].getBlue()));
				g.setColor(new Color(gray, gray, gray));
				g.fillRect(startX+size*x, startY+size*((frameH-1)-y), size, size);
			}
		}
	}
}