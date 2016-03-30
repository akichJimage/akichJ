/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.awt.image.BufferedImage;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;

/**
 *
 * @author akichi
 */
public class Matrix {
    private int[][][] mat;
    private int width,height;
    private int channels;
    private int depth;
    public Matrix(int width,int height,int channels,int depth){
        mat = new int[width][height][channels];
        for(int i = 0;i < width;i++){
            for(int n = 0;n < height;n++){
                for(int m = 0;m < channels;m++){
                    mat[i][n][m] = 0;
                }
            }

        }
        this.channels = channels;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }
    
    public Matrix(Stdim img){
        mat = new int[img.getWidth()][img.getHeight()][img.getType()];
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = img.getImg().getRGB(x, y);
                mat[x][y][0] = Core.r(c);
                mat[x][y][1] = Core.g(c);
                mat[x][y][2] = Core.b(c);
            }
        }
    }

    public Matrix(BufferedImage img){
        mat = new int[img.getWidth()][img.getHeight()][3];
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = img.getRGB(x, y);
                mat[x][y][0] = Core.r(c);
                mat[x][y][1] = Core.g(c);
                mat[x][y][2] = Core.b(c);
                width = img.getWidth();
                height = img.getHeight();
            }
        }
    }
    
    public float[] MatAvg(){
        float[] mat_avg = new float[channels];
        for(int n = 0;n < channels;n++){
            mat_avg[n] = 0;
        }
        for(int y = 0;y < height;y++){
            for(int x = 0;x < width;x++){
                for(int c = 0;c < channels;c++){
                    mat_avg[c] += mat[x][y][c];
                }
            }
        }
        return mat_avg;
    }
    public static float[] quarterAvg(Matrix matim){
        float[] QAvg = new float[4];
        QAvg[0] = avg_quatry(matim,0);
        QAvg[1] = avg_quatry(matim,1);
        QAvg[2] = avg_quatry(matim,2);
        QAvg[3] = avg_quatry(matim,3);
        return QAvg;
    }
    private static float avg_quatry(Matrix mat,int num){
        float avg = 0;
        switch(num){
            case 0:
                for(int y = 0;y < mat.getHeight()/2;y++){
                    for(int x = 0;x < mat.getWidth()/2;x++){
                        for(int c = 0;c < mat.getChannels();c++){
                            avg += mat.mat[x][y][c];
                        }
                    }
                }
                avg /= (3*(mat.getHeight()/2)*(mat.getWidth()/2));
                return avg;
                
            case 1:
                for(int y = 0;y < mat.getHeight()/2;y++){
                    for(int x = mat.getWidth()/2;x < mat.getWidth();x++){
                        for(int c = 0;c < mat.getChannels();c++){
                            avg += mat.mat[x][y][c];
                        }
                    }
                }
                avg /= (3*(mat.getHeight()/2)*(mat.getWidth()/2));
                return avg;
            case 2:
                for(int y = mat.getHeight()/2;y < mat.getHeight();y++){
                    for(int x = mat.getWidth()/2;x < mat.getWidth();x++){
                        for(int c = 0;c < mat.getChannels();c++){
                            avg += mat.mat[x][y][c];
                        }
                    }
                }
                avg /= (3*(mat.getHeight()/2)*(mat.getWidth()/2));
                return avg;
            case 3:
                for(int y = mat.getHeight()/2;y < mat.getHeight();y++){
                    for(int x = 0;x < mat.getWidth()/2;x++){
                        for(int c = 0;c < mat.getChannels();c++){
                            avg += mat.mat[x][y][c];
                        }
                    }
                }
                avg /= (3*(mat.getHeight()/2)*(mat.getWidth()/2));
                return avg;
            default :
                return avg;
        }
    }
    
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getChannels(){
        return this.channels;
    }
    public int[][][] getMat(){return this.mat;}
    public void setMatValue(int x, int y, int ch, int value ){this.mat[x][y][ch] = value;}
}
