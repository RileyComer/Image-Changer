package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import displays.Mode3Display;

public class GameFrame {
	
	private String[] mode;
	private Window window;
	private int width;
	private int height;
	private int test;
	private Color input[][];
	private Color palette[];
	private Color edges[][];
	private double edgeTol;
	private double edgeTolSpeed;
	private int imageNum;
	private int maxP;
	
	public GameFrame(String[] mode, Window window) {
		this.mode=mode;
		this.window=window;
		width=1920;
		height=1080;
		test=0;
		edgeTol=0;
		edgeTolSpeed=0;
		maxP=255;
		imageNum=6;
		change();
		
		randomize();
	}
	
	public Color[][] getInput() {
		return input;
	}
	
	public Color[] getPalette() {
		return palette;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void randomize() {
		palette= new Color[256];
		palette[0]=new Color(0, 0, 0);
		double red=0.299;
		double green=0.587;
		double blue=0.114;
		
		for(int i=0; i<255; i++) {
			int r=0, g=0, b=0;
			double total=0;
			while(total<(i)) {
				int rand=(int) (Math.random()*(3));
				if(r==255 && g==255) {
					rand=2;
				}else if(r==255 && b==255) {
					rand=1;
				}else if(g==255 && b==255) {
					rand=0;
				}
				int amount=(int) (Math.random()*(i-total));
				amount++;
				
				if(rand==0 && r<255) {
					int a=(int) (amount/(red));
					if(amount==1) {
						r++;
						total+=(red);
					}else if(r+a>255) {
						a=255-r;
						r+=a;
						total+=a*(red);
					}else {
						r+=a;
						total+=a*(red);
					}
				}else if(rand==1 && g<255) {
					int a=(int) (amount/(green));
					if(amount==1) {
						g++;
						total+=(green);
					}else if(g+a>255) {
						a=255-g;
						g+=a;
						total+=a*(green);
					}else {
						g+=a;
						total+=a*(green);
					}
				}else if(rand==2 && b<255){
					int a=(int) (amount/(blue));
					if(amount==1) {
						b++;
						total+=(blue);
					}else if(b+a>255) {
						a=255-b;
						b+=a;
						total+=a*(blue);
					}else {
						b+=a;
						total+=a*(blue);
					}
				}
			}
			palette[i+1]=new Color(r, g, b);
		}
	}
	
	public void change() {
		test++;
		if(test==imageNum+1) {
			test=1;
			if(mode[0].equals("Mode4")) {
				window.setMode("Mode5");
			}else if(mode[0].equals("Mode5")) {
				window.setMode("Mode4");
			}
		}
		input=new Color[width][height];
		File file=new File("C:\\Users\\Riley\\Desktop\\changing\\test"+test+".png");
		if(file!=null) {
			BufferedImage img=null;
			Color c;
			try {
				img=ImageIO.read(file);
				for(int y=0; y<height; y++) {
					for(int x=0; x<width; x++) {
						c=new Color(img.getRGB(x, y));
						input[x][(height-1)-y]=c;
					}
				}
			}catch(IOException e){
			}
		}
		genEdges();
		if(edgeTolSpeed!=0) {
			int max=0;
			for(int x=0; x<width; x++) {
				for(int y=0; y<height; y++) {
					if(edges[x][y].getRed()>max) {
						max=edges[x][y].getRed();
					}
				}
			}
			edgeTol=max;
			maxP=max;
			if(edgeTolSpeed>0) {
				edgeTolSpeed=-edgeTolSpeed;
			}
		}
	}
	
	public void genEdges(){
		Color[][] out;
		out=new Color[width][height];
		
		double red=0.299;
		double green=0.587;
		double blue=0.114;
		
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				Color top=null, right=null, bot=null, left=null;
				if(y!=height-1) {
					int gray=(int)((red*input[x][y+1].getRed()+green*input[x][y+1].getGreen()+blue*input[x][y+1].getBlue()));
					top=new Color(gray, gray, gray);
				}
				if(x!=width-1) {
					int gray=(int)((red*input[x+1][y].getRed()+green*input[x+1][y].getGreen()+blue*input[x+1][y].getBlue()));
					right=new Color(gray, gray, gray);
				}
				if(y!=0) {
					int gray=(int)((red*input[x][y-1].getRed()+green*input[x][y-1].getGreen()+blue*input[x][y-1].getBlue()));
					bot=new Color(gray, gray, gray);
				}
				if(x!=0) {
					int gray=(int)((red*input[x-1][y].getRed()+green*input[x-1][y].getGreen()+blue*input[x-1][y].getBlue()));
					left=new Color(gray, gray, gray);
				}
				Color max, min;
				max=max(top, right, bot, left);
				min=min(top, right, bot, left);
				int maxNum=sub(input[x][y], max);
				int minNum=sub(input[x][y], min);
				if(maxNum<0) {
					maxNum=-maxNum;
				}
				if(minNum<0) {
					minNum=-minNum;
				}
				if(maxNum>minNum) {
					out[x][y]=new Color(maxNum/3, maxNum/3, maxNum/3);
				}else {
					out[x][y]=new Color(minNum/3, minNum/3, minNum/3);
				}
			}
		}
		edges=out;
	}
	
	public Color[][] getEdges(){
		return edges;
	}
	
	public Color max(Color top, Color right, Color bot, Color left) {
		Color out=top;
		if(top==null) {
			out=bot;
		}
		if(right!=null && out.getRed()+out.getGreen()+out.getBlue()<right.getRed()+right.getGreen()+right.getBlue()) {
			out=right;
		}
		if(bot!=null && out.getRed()+out.getGreen()+out.getBlue()<bot.getRed()+bot.getGreen()+bot.getBlue()) {
			out=bot;
		}
		if(left!=null && out.getRed()+out.getGreen()+out.getBlue()<left.getRed()+left.getGreen()+left.getBlue()) {
			out=left;
		}
		
		return out;
	}
	
	public Color min(Color top, Color right, Color bot, Color left) {
		Color out=top;
		if(top==null) {
			out=bot;
		}
		if(right!=null && out.getRed()+out.getGreen()+out.getBlue()>right.getRed()+right.getGreen()+right.getBlue()) {
			out=right;
		}
		if(bot!=null && out.getRed()+out.getGreen()+out.getBlue()>bot.getRed()+bot.getGreen()+bot.getBlue()) {
			out=bot;
		}
		if(left!=null && out.getRed()+out.getGreen()+out.getBlue()>left.getRed()+left.getGreen()+left.getBlue()) {
			out=left;
		}
		
		return out;
	}
	
	public int sub(Color prim, Color sec) {
		return prim.getRed()+prim.getGreen()+prim.getBlue()-sec.getRed()-sec.getGreen()-sec.getBlue();
	}

	public void update() {
		edgeTol+=edgeTolSpeed;
		if(edgeTol>=maxP && edgeTolSpeed>0) {
			change();
		}else if(edgeTol<=-10*(Math.abs(edgeTolSpeed)) && edgeTolSpeed<0) {
			edgeTolSpeed=-edgeTolSpeed;
		}
		
	}

	public int getEdgeTol() {
		return (int)edgeTol;
	}
	
	public int getMax() {
		return maxP;
	}
}
