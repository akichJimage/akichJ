package mt;

import core.Core;
import kernel.Execute;
import kernel.NImgFuncionCode;
import kernel.Schedule;

import static core.Core.SUCCESS;
import static core.Core.a;

import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by akichi on 16/02/20.
 */

public class NImg {

    private int[][][] image;
    private int height;
    private int width;
    private boolean wait = false;
    private short wait_ID = 0;
    public Deque<Order> order = new ArrayDeque<>();
    public Execute ex = new Execute(this);


    public NImg(BufferedImage img){
        height = img.getHeight();
        width = img.getWidth();
        image = new int[width][height][3];

        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                int c = img.getRGB(x, y);
                image[x][y][0] = Core.r(c);
                image[x][y][1] = Core.g(c);
                image[x][y][2] = Core.b(c);
            }
        }
    }

    public NImg(int width, int height, int depth){
        this.width = width;
        this.height = height;
        image = new int[width][height][depth];
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int[][][] getImageArray(){return image;}

    public void execute(){
        ex.start();
    }

    public boolean isWait(){
        return wait;
    }

    public void waitOrder(short issued_ID, boolean order){
        wait_ID = issued_ID;
        wait = order;
    }

    public short getWait_ID(){
        return wait_ID;
    }

    public void setWait_ID(short issued_ID){
        wait_ID = issued_ID;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////BEGIN DEFINE NEGA

    public void SInega(){
        order.add(new Order(NImgFuncionCode.NEGA));
    }

    public void NInegp(){
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                image[x][y][0] = 255 - image[x][y][0];
                image[x][y][1] = 255 - image[x][y][1];
                image[x][y][2] = 255 - image[x][y][2];
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////END DEFINE NEGA

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////BEGIN DEFINE BLEND

    public void SIblend(NImg blendim, int p1, int p2){
        this.waitOrder(Schedule.issueID(), true);
        order.add(new Order(NImgFuncionCode.BLEND, blendim, p1, p2));
        blendim.order.add(new Order(NImgFuncionCode.WAIT, Schedule.NonUpdate_issueID(), this));
    }

    public void NIblend(NImg blendim, int p1, int p2){
        int p = p1 + p2;
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                image[x][y][0] = (p2*blendim.getImageArray()[x][y][0] + p1*image[x][y][0])/p;
                image[x][y][1] = (p2*blendim.getImageArray()[x][y][1] + p1*image[x][y][1])/p;
                image[x][y][2] = (p2*blendim.getImageArray()[x][y][2] + p1*image[x][y][2])/p;
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////END DEFINE BLEND

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////BEGIN DEFINE GRAY
    public void SIgray(){
        order.add(new Order(NImgFuncionCode.GRAY));
    }

    public void NIgray(){
        int c;
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                c = (image[x][y][0] + image[x][y][1] + image[x][y][2])/3;
                image[x][y][0] = c;
                image[x][y][1] = c;
                image[x][y][2] = c;
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////END DEFINE GRAY

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////BEGIN DEFINE TWOWAY

    public void SItwoway(){
        order.add(new Order(NImgFuncionCode.TWOWAY));
    }

    public void NItwoway(){
        NIgray();
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                if(image[x][y][0] > 128){
                    image[x][y][0] = 255;
                    image[x][y][1] = 255;
                    image[x][y][2] = 255;
                }else{
                    image[x][y][0] = 0;
                    image[x][y][1] = 0;
                    image[x][y][2] = 0;
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////BEGIN DEFINE TWOWAY
    public void link(){

    }
}

