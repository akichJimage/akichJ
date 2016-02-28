package core;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Stdim implements Cloneable{
	private BufferedImage img;
	private int ID;
	
	public Stdim clone(){
		Stdim result = new Stdim();
		result.setImg(deepCopy(this.img));
		return result;
	}
	public static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	Stdim(BufferedImage im){
		img = im;
	}
	public Stdim(){
		ID = Core.IDs;
		Core.IDs++;
	}
	public boolean equals(Stdim img){
		return this.ID == img.getID();
	}
	public void setImg(BufferedImage img){
		this.img = img;
	}
	public BufferedImage getImg(){
		return this.img;
	}
	public int getWidth(){
		return this.img.getWidth();
	}
	public int getHeight(){
		return this.img.getHeight();
	}
	public int getType(){
            return this.img.getType();
        }
	public Size getSize(){
		return Core.Size(getWidth(), getHeight());
	}
	public int[] getRGB(int x, int y){
		int[] rgb = new int[3];
		int c = img.getRGB(x,y);
		rgb[0] = Core.r(c);
		rgb[1] = Core.g(c);
		rgb[2] = Core.b(c);
		return rgb;
	}
	public int getID(){return ID;}
	public void setID(int id){ID = id;}
}
