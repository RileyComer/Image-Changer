package displays;
import java.awt.Color;
import java.awt.Graphics;

import main.GameFrame;
import main.Gui;

public class Mode5Display{
	
	private Gui gui;
	private GameFrame gameframe;
	private int size,startX,startY,frameW,frameH;
	//game stuff
	
	public Mode5Display(Gui gui1, GameFrame gameframe){
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
		double rd=1;
		double gd=1;
		double bd=1;
				
		double red=0.299;
		double green=0.587;
		double blue=0.114;
		
		double mult=255.0/gameframe.getMax();
		
		for(int y=0; y< frameH; y++) {
			for(int x=0; x<frameW;x++) {
				rd=(y*1.0)/frameH;
				bd=(x*1.0)/frameW;
				gd=1-(rd*red+bd*blue);
				if(gameframe.getEdges()[x][y].getRed()>gameframe.getEdgeTol()) {
					g.setColor(new Color((int) ((gameframe.getEdges()[x][y].getRed()*mult)*rd), (int) ((gameframe.getEdges()[x][y].getGreen()*mult)*gd), (int) ((gameframe.getEdges()[x][y].getBlue()*mult)*bd)));
				}else {
					g.setColor(new Color(0, 0, 0));
				}
				g.fillRect(startX+size*x, startY+size*((frameH-1)-y), size, size);
			}
		}
	}
}