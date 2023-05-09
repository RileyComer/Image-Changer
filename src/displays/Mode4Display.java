package displays;
import java.awt.Color;
import java.awt.Graphics;

import main.GameFrame;
import main.Gui;

public class Mode4Display{
	
	private Gui gui;
	private GameFrame gameframe;
	private int size,startX,startY,frameW,frameH;
	//game stuff
	
	public Mode4Display(Gui gui1, GameFrame gameframe){
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
		
		Color canvas=new Color(221, 199, 165);
		double rd=canvas.getRed()/255.0;
		double gd=canvas.getGreen()/255.0;
		double bd=canvas.getBlue()/255.0;
		
		double mult=255.0/gameframe.getMax();
		
		for(int y=0; y< frameH; y++) {
			for(int x=0; x<frameW;x++) {
				g.setColor(canvas);
				g.fillRect(startX+size*x, startY+size*((frameH-1)-y), size, size);
				
				if(gameframe.getEdges()[x][y].getRed()>gameframe.getEdgeTol()) {
					g.setColor(new Color((int) ((255-(int)(gameframe.getEdges()[x][y].getRed()*mult))*rd), (int) ((255-(int)(gameframe.getEdges()[x][y].getGreen()*mult))*gd), (int) ((255-(int)(gameframe.getEdges()[x][y].getBlue()*mult))*bd)));
				}
				g.fillRect(startX+size*x, startY+size*((frameH-1)-y), size, size);
			}
		}
	}
}