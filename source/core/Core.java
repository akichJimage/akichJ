package core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Core {
	public static final String AKICHIJ_VERSION = "akichJ Version 0.3.3e";
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
    //Stdimageオブジェクトの生成
	public static Stdim readim(String filename){
    	Stdim img = new Stdim();
    	File f = new File(filename);
    	BufferedImage im = null;
		try {
			im = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	img.setImg(im);
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
}
class Color{
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
	public void freemat(Matrix mat){mat = null;}
	public void copyim(Stdim... args){
		Stdim newim = args[0].clone();
		for (Stdim  img : args) {
			img = newim;
		}
	}
}

