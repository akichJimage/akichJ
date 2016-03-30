package core;

import mt.NImg;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Core{
	public static final String AKICHIJ_VERSION = "akichJ version 0.4.0";
	public static final boolean SUCCESS = true;
	public static final boolean FAILED = false;

	public static int IDs = 0;
	public static Point Point(int x,int y){
		Point P = new Point(x,y);
		return P;
	}
	public static Color Color(int r,int g,int b){
		Color C = new Color(r,g,b);
		return C;
	}
        public static Size Size(int width,int height){
            Size S = new Size(width,height);
            return S;
        }
	//下記6関数はピクセル値取得の関数
    public static int a(int c){
        return c>>>24;
    }
    public static int r(int c){
        return c>>16&0xff;
    }
    public static int g(int c){
        return c>>8&0xff;
    }
    public static int b(int c){
        return c&0xff;
    }
    public static int rgb(int r,int g,int b){
        return 0xff000000 | r <<16 | g <<8 | b;
    }
    public static int argb(int a,int r,int g,int b){
        return a<<24 | r <<16 | g <<8 | b;
    }


	public static Stdim readStdim(String filename){
    	File f = new File(filename);
    	Stdim img = null;
		try {
			img = new Stdim(ImageIO.read(f));
		} catch (IOException e) {
			System.out.println("Not Found " + filename);
		}
    	return img;
    }


	public static NImg readNImg(String filename){
		File f = new File(filename);
		NImg img = null;
		try {
			img = new NImg(ImageIO.read(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

    public static void saveim(Stdim img,String filename,String format){
    	File out = new File(filename);
		try {
			ImageIO.write(img.getImg(), format, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	public static void quickSort(int[] arr, int left, int right){
		if (left <= right) {
			int p = arr[(left+right) / 2];
			int l = left;
			int r = right;

			while(l <= r) {
				while(arr[l] < p){ l++; }
				while(arr[r] > p){ r--; }

				if (l <= r) {
					int tmp = arr[l];
					arr[l] = arr[r];
					arr[r] = tmp;
					l++;
					r--;
				}
			}

			quickSort(arr, left, r);
			quickSort(arr, l, right);
		}
	}
	public static BufferedImage cvtStdim2BI(Stdim img){
		return img.getImg();
	}

	public static BufferedImage cvtNImg2BI(NImg img){
		BufferedImage im = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int y = 0;y < img.getHeight();y++){
			for(int x = 0;x < img.getWidth();x++){
				im.setRGB(x, y, Core.rgb(img.getImageArray()[x][y][0], img.getImageArray()[x][y][1], img.getImageArray()[x][y][2]));
			}
		}
		return im;
	}

	public static Stdim getCleannessImage(int width, int height){
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = img.createGraphics();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0,0,img.getHeight(),img.getHeight());
		g2D.fill(rect);
		g2D.setPaintMode();
		Stdim stdim = new Stdim(img);
		return stdim;
	}

	public static Stdim getWhiteStdim(int width, int height){
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Stdim img = new Stdim(bufferedImage);
		for(int y = 0;y < img.getHeight();y++){
			for(int x = 0;x < img.getWidth();x++){
				int rgb = Core.rgb(255,255,255);
				img.getImg().setRGB(x, y, rgb);
			}
		}
		return img;
	}
}

class Point{
	int x;
	int y;
	public Point(int x,int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
}

class Color {
	private int red;
	private int green;
	private int blue;
	public Color(int red,int green,int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public int getR(){
		return this.red;
	}
	public int getG(){
		return this.green;
	}
	public int getB(){
		return this.blue;
	}
}

class Size{
    int height;
    int width;
    public Size(int width,int height){
        this.height = height;
        this.width = width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getWidth(){
        return this.width;
    }
	public void freeim(Stdim img){img =null;}
	public void copyim(Stdim... args){
		Stdim newim = args[0].clone();
		for (Stdim  img : args) {
			img = newim;
		}
	}
}


