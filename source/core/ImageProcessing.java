package core;

import java.awt.image.BufferedImage;
import java.util.function.*;

public class ImageProcessing {

    public static final int ALL_EDGE = 2;
    public static final int LONG_EDGE = 0;
    public static final int BESIDE_EDGE = 1;
    public static final int ONLY_RED = 0;
    public static final int ONLY_GREEN = 1;
    public static final int ONLY_BLUE = 2;
    public static final int RED_GREEN = 3;
    public static final int RED_BLUE = 4;
    public static final int GREEN_BLUE = 5;
    public static final int ALL_COLOR = 6;
    public static final int MIX_GBR = 0;
    public static final int MIX_GRB = 1;
    public static final int MIX_BGR = 2;
    public static final int MIX_BRG = 3;
    public static final int MIX_RGB = 4;
    public static final int MIX_RBG = 5;
    public static final int FLIP_180 = 0;
    public static final int FLIP_MIRROR = 1;
    public static final int FLIP_180MIRROR = 2;
    public static final int FLIP_90LEFT = 3;
    public static final int FLIP_90RIGHT = 4;
    public static final int IMAGE_ADDITION = 0;
    public static final int IMAGE_SUBTRACTION = 1;
    public static final int THIN_WHITELINE = 0;
    public static final int THIN_BLACKLINE = 1;
    public static final int EXCOL_PURPLE = 0;
    public static final int EXCOL_PINK = 1;
    public static final int EXCOL_YELLOW = 2;

    public static void negp(Stdim imdate){
        BufferedImage im = imdate.clone().getImg();
        BufferedImage write = imdate.clone().getImg();
        for(int y = 0;y < im.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = 255 - Core.r(c);
                int g = 255 - Core.g(c);
                int b = 255 - Core.b(c);
                int rgb = Core.rgb(r,g,b);
                write.setRGB(x,y,rgb);
            }
        }
        imdate.setImg(write);
        Release(im,write);
    }

    public static Stdim blend(Stdim im1,Stdim im2,int imp1,int imp2){
        Stdim blend = new Stdim();
        BufferedImage blendb = new BufferedImage(im1.getImg().getWidth(),
                im1.getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0;y < im1.getHeight();y++){
            for(int x = 0;x < im1.getWidth();x++){
                int c1 = (im1.getImg()).getRGB(x,y);
                int c2 = (im2.getImg()).getRGB(x,y);
                int r = ((imp1*Core.r(c1)) + (imp2*Core.r(c2)))/(imp1 + imp2);
                int g = ((imp1*Core.g(c1)) + (imp2*Core.g(c2)))/(imp1 + imp2);
                int b = ((imp1*Core.b(c1)) + (imp2*Core.b(c2)))/(imp1 + imp2);
                int rgb = Core.rgb(r,g,b);
                blendb.setRGB(x,y,rgb);
            }
        }
        blend.setImg(blendb);
        Release(blendb);
        return blend;
    }

    public static void gray(Stdim img){
        BufferedImage grayb = new BufferedImage(img.getImg().getWidth(),
                img.getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = (img.getImg()).getRGB(x,y);
                int r = Core.r(c);
                int g = Core.g(c);
                int b = Core.b(c);
                int h = (r + g + b) / 3;
                int rgb = Core.rgb(h,h,h);
                grayb.setRGB(x,y,rgb);
            }
        }
        img.setImg(grayb);
        Release(grayb);
    }

    public static void twoway(Stdim img,int value,int select){
        BufferedImage tway;
        gray(img);
        tway = img.clone().getImg();
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = (img.getImg()).getRGB(x,y);
                int b = Core.b(c);
                if(b > 127 + value){
                    if(select == 0)
                        b = 255;
                    else if(select == 1)
                        b = 0;
                }else{
                    if(select == 0)
                        b = 0;
                    else if(select == 1)
                        b = 255;
                }
                int rgb = Core.rgb(b,b,b);
                tway.setRGB(x, y, rgb);
            }
        }
        img.setImg(tway);
        Release(tway);
    }

    public static void smooth(Stdim img){
        BufferedImage smoo = img.clone().getImg();
        int s[][] = new int[3][9];
        int rr,gg,bb;
        rr = gg = bb = 0;
        IntToDoubleFunction fx = (int a) -> {
            switch(a){
                case 0:
                    return -1;
                case 1:
                    return 0;
                case 2:
                    return 1;
                case 3:
                    return 1;
                case 4:
                    return 1;
                case 5:
                    return 0;
                case 6:
                    return -1;
                case 7:
                    return -1;
                case 8:
                    return 0;
                default:
                    return 0;
            }
        };
        IntToDoubleFunction fy = (int a) -> {
            switch(a){
                case 0:
                    return 1;
                case 1:
                    return 1;
                case 2:
                    return 1;
                case 3:
                    return 0;
                case 4:
                    return -1;
                case 5:
                    return -1;
                case 6:
                    return -1;
                case 7:
                    return 0;
                case 8:
                    return 0;
                default:
                    return 0;
            }

        };
        for(int y = 1;y < img.getHeight()-1;y++){
            for(int x = 1;x < img.getWidth()-1;x++){
                for(int i = 0;i < 9;i++){
                    int c = (img.getImg()).getRGB(x + (int)(fx.applyAsDouble(i)),y + (int)(fy.applyAsDouble(i)));
                    s[0][i] = Core.r(c);
                    s[1][i] = Core.g(c);
                    s[2][i] = Core.b(c);
                }
                rr = gg = bb = 0;
                for(int i = 0;i < 9;i++)
                    rr += s[0][i];

                for(int i = 0;i < 9;i++)
                    gg += s[1][i];

                for(int i = 0;i < 9;i++)
                    bb += s[2][i];

                rr /= 9;
                gg /= 9;
                bb /= 9;
                int rgb = Core.rgb(rr,gg,bb);
                smoo.setRGB(x,y,rgb);
            }
        }
        img.setImg(smoo);
        Release(smoo,s);
    }

    public static void sharping(Stdim img,int times){
        final double vl = 5.2;
        boolean sl = !(img.getType() == 1);
        BufferedImage sh = new BufferedImage(img.getImg().getWidth(),
                img.getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
        Stdim miwn = img.clone();
        final Stdim miw = miwn;
        Stdim im = new Stdim();
        im = img.clone();
        smooth(im);
        final Stdim mn = im.clone();
        IntBinaryOperator funr = (x,y) -> {
            int c1 = (miw.getImg()).getRGB(x,y);
            int c2 = (mn.getImg()).getRGB(x,y);
            return Core.r(c1) - Core.r(c2);
        };
        IntBinaryOperator fung = (x,y) -> {
            int c1 = (miw.getImg()).getRGB(x,y);
            int c2 = (mn.getImg()).getRGB(x,y);
            return Core.r(c1) - Core.g(c2);
        };
        IntBinaryOperator funb = (x,y) -> {
            int c1 = (miw.getImg()).getRGB(x,y);
            int c2 = (mn.getImg()).getRGB(x,y);
            return Core.b(c1) - Core.b(c2);
        };

        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int r,g,b;
                int c2 = (img.getImg()).getRGB(x,y);
                if(sl) {
                    if(funr.applyAsInt(x, y) < vl*times){
                        r = Core.r(c2);
                        r = (int)(r * 1.1);
                    }else {
                        r = (times * funr.applyAsInt(x, y)) + Core.r(c2);
                    }
                }else {
                    r = times * funr.applyAsInt(x, y) + Core.r(c2);
                }
                if(r < 0)
                    r = 0;
                if(r > 255)
                    r = 255;
                if(sl) {
                    if (funr.applyAsInt(x, y) < vl * times) {
                        g = Core.g(c2);
                        g = (int)(g * 1.1);
                    } else {
                        g = (times * funr.applyAsInt(x, y)) + Core.g(c2);
                    }
                }else {
                    g = times * fung.applyAsInt(x, y) + Core.g(c2);
                }
                if(g < 0)
                    g = 0;
                if(g > 255)
                    g = 255;
                if(sl) {
                    if (funr.applyAsInt(x, y) < vl * times) {
                        b = Core.b(c2);
                        b = (int)(b * 1.1);
                    } else {
                        b = (times * funr.applyAsInt(x, y)) + Core.b(c2);
                    }
                }else {
                    b = times * funb.applyAsInt(x, y) + Core.b(c2);
                }
                if(b < 0)
                    b = 0;
                if(b > 255)
                    b = 255;
                int rgb = Core.rgb(r,g,b);
                sh.setRGB(x,y,rgb);
            }
        }
        img.setImg(sh);
        Release(sh,miwn,im,mn);
    }

    public static void edge(Stdim img,int value,int select) {
        int ans;
        BufferedImage im = img.clone().getImg();
        if (select == 0) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                for (int x = 1; x < img.getWidth() - 1; x++) {
                    int cen = (img.getImg()).getRGB(x, y);
                    int c1 = (img.getImg()).getRGB(x + 1, y);
                    int c2 = (img.getImg()).getRGB(x - 1, y);
                    int ce = Core.r(cen);
                    int ri = Core.r(c1);
                    int le = Core.r(c2);
                    if (ce > ((ri + le) / 2) + value)  //5 is important
                        ans = 255;
                    else {
                        ans = 0;
                    }
                    int rgb = Core.rgb(ans, ans, ans);
                    im.setRGB(x, y, rgb);
                }
            }
            img.setImg(im);
            twoway(img,0,0);
        } else if (select == 1) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                for (int x = 1; x < img.getWidth() - 1; x++) {
                    int cen = (img.getImg()).getRGB(x, y);
                    int c1 = (img.getImg()).getRGB(x, y + 1);
                    int c2 = (img.getImg()).getRGB(x, y - 1);
                    int ce = Core.r(cen);
                    int ri = Core.r(c1);
                    int le = Core.r(c2);
                    if (ce > ((ri + le) / 2) + value)  //5 is important
                        ans = 255;
                    else {
                        ans = 0;
                    }
                    int rgb = Core.rgb(ans, ans, ans);
                    im.setRGB(x, y, rgb);
                }
            }
            img.setImg(im);
            twoway(img,0,0);
        } else if (select == 2) {
            Alledge(img, value);
        }
        Release(im);
    }

    public static void solari(Stdim img){
        gray(img);
        BufferedImage im = img.clone().getImg();
        ToIntFunction<Integer> func = (r) -> {
            int i = (int)r;
            return (int)((360 / 255) * i);
        };
        for(int y = 0;y < im.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = Core.r(c);
                r = (int)(127 * Math.sin(Math.toRadians(func.applyAsInt(r)))) + 128;
                int rgb = Core.rgb(r,r,r);
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void post(Stdim img){
        BufferedImage im = img.clone().getImg();
        ToIntFunction<Integer> func = (r) -> {
            if(r >= 0 && r < 64)
                return 0;
            else if(r >= 64 && r < 128)
                return 86;
            else if(r >= 128 && r < 192)
                return 172;
            else if(r >= 192 && r < 256)
                return 256;
            return 0;
        };
        for(int y = 0;y < im.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = func.applyAsInt(Core.r(c));
                int g = func.applyAsInt(Core.g(c));
                int b = func.applyAsInt(Core.b(c));
                int rgb = Core.rgb(r,g,b);
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void separ(int select,Stdim... args){
        IntConsumer funr = (num) -> {
            BufferedImage im = new BufferedImage(args[0].getImg().getWidth(),
                    args[0].getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
            for(int y = 0;y < args[0].getHeight();y++){
                for(int x = 0;x < args[0].getWidth();x++){
                    int c = args[0].getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int rgb = Core.rgb(r,0,0);
                    im.setRGB(x,y,rgb);
                }
            }
            args[num].setImg(im);
        };
        IntConsumer fung = (num) -> {
            BufferedImage im = new BufferedImage(args[0].getImg().getWidth(),
                    args[0].getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
            for(int y = 0;y < args[0].getHeight();y++){
                for(int x = 0;x < args[0].getWidth();x++){
                    int c = args[0].getImg().getRGB(x,y);
                    int g = Core.g(c);
                    int rgb = Core.rgb(0,g,0);
                    im.setRGB(x,y,rgb);
                }
            }
            args[num].setImg(im);
        };
        IntConsumer funb = (num) -> {
            BufferedImage im = new BufferedImage(args[0].getImg().getWidth(),
                    args[0].getImg().getHeight(), BufferedImage.TYPE_INT_RGB);
            for(int y = 0;y < args[0].getHeight();y++){
                for(int x = 0;x < args[0].getWidth();x++){
                    int c = args[0].getImg().getRGB(x,y);
                    int b = Core.b(c);
                    int rgb = Core.rgb(0,0,b);
                    im.setRGB(x,y,rgb);
                }
            }
            args[num].setImg(im);
        };
        switch(select){
            case 0:
                funr.accept(1);
                break;
            case 1:
                fung.accept(1);
                break;
            case 2:
                funb.accept(1);
                break;
            case 3:
                funr.accept(1);
                fung.accept(2);
                break;
            case 4:
                funr.accept(1);
                funb.accept(2);
                break;
            case 5:
                fung.accept(1);
                funb.accept(2);
                break;
            case 6:
                funr.accept(1);
                fung.accept(2);
                funb.accept(3);
                break;
            default:
                System.out.println("No Stdim objects");
        }
    }

    public static int tm = 0;
    public static int tl = 0;
    public static boolean nu = false;

    public static void line(Stdim img, Point st, Point ed, Color col,int r){
        int times = r;
        int nt;
        if(tm == 0){
            tl = r*2;
            if(times % 2 == 0)
                nu = true;
        }else{
            tl--;
            times = (r * 2) + 1;
            if(nu)
                times++;
            if((times -1)/2 - 1 % 2 == 0)
                nu = true;
        }

        BufferedImage im = img.clone().getImg();
        IntSupplier avr = ()-> {
            return ((int)(ed.getX() - st.getX()) / (ed.getY() - st.getY()));
        };
        IntBinaryOperator func = (num1,num2) -> {
            if(num1 < num2){
                return num1;
            }else{
                return num2;
            }
        };
        IntBinaryOperator funcl = (num1,num2) -> {
            if(num1 > num2){
                return num1;
            }else{
                return num2;
            }
        };
        ToIntFunction wh = (x) -> {
            if((int)x % 2 == 0){
                return 1;
            }else if((int)x % 2 == 1){
                return -1;
            }
            return 1;
        };
        IntBinaryOperator nw = (i,y) -> {
            if(i == 0){
                return y;
            }else{
                return y * 2 + 1;
            }
        };
        if(st.getX() == ed.getX() && st.getY() == ed.getY()){
            int rgb = Core.rgb(col.getR(), col.getG(), col.getB());
            im.setRGB(st.getX(),st.getY(),rgb);
            img.setImg(im);
        }else if(st.getX() == ed.getX()){
            for(int i = 0;i < funcl.applyAsInt(st.getY(), ed.getY()) - func.applyAsInt(st.getY(), ed.getY());i++){
                int rgb = Core.rgb(col.getR(), col.getG(), col.getB());
                im.setRGB(st.getX(),st.getY() + i,rgb);
            }
            img.setImg(im);
            r--;
            if(r > 0){
                line(img,Core.Point(st.getX() + (wh.applyAsInt(r) * times),
                        st.getY()),Core.Point(ed.getX() + (wh.applyAsInt(r) * times),
                        ed.getY()),col,r);
            }
        }else if(st.getY() == ed.getY()){
            for(int i = 0;i < funcl.applyAsInt(st.getX(), ed.getX()) - func.applyAsInt(st.getX(), ed.getX());i++){
                int rgb = Core.rgb(col.getR(), col.getG(), col.getB());
                im.setRGB(st.getX() + i,st.getY(),rgb);
            }
            img.setImg(im);
            r--;
            if(r > 0){
                line(img,Core.Point(st.getX(),st.getY() + (wh.applyAsInt(r) * times)),
                        Core.Point(ed.getX(),ed.getY() + (wh.applyAsInt(r) * times)),
                        col,r);
            }
        }else{
            for(int i = 0;i < funcl.applyAsInt(st.getX(), ed.getX()) - func.applyAsInt(st.getX(), ed.getX());i++){
                int rgb = Core.rgb(col.getR(), col.getG(), col.getB());
                im.setRGB(st.getX() + i,avr.getAsInt() * (st.getX() + i),rgb);
            }
            img.setImg(im);
            r--;
            tm++;
            if(r > 0){
                System.out.println(st.getY());
                line(img,
                        Core.Point(st.getX(),(st.getY() + ((wh.applyAsInt(r) * nw.applyAsInt(tm, r))))),
                        Core.Point(ed.getX(),(ed.getY() + ((wh.applyAsInt(r) * nw.applyAsInt(tm, r))))),
                        col,r);
            }
        }
        Release(im);
    }

    public static void bright(Stdim img,int pixel){
        BufferedImage im = img.clone().getImg();
        for(int y = 0;y < im.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = Core.r(c) + pixel;
                if(r > 255)
                    r = 255;
                if(r < 0)
                    r = 0;
                int g = Core.g(c) + pixel;
                if(g > 255)
                    g = 255;
                if(g < 0)
                    g = 0;
                int b = Core.b(c) + pixel;
                if(b > 255)
                    b = 255;
                if(b < 0)
                    b = 0;
                int rgb = Core.rgb(r,g,b);
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void mix(Stdim img,int select){
        BufferedImage im = img.clone().getImg();
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = Core.r(c);
                int g = Core.g(c);
                int b = Core.b(c);
                int rgb;
                switch(select){
                    case 0:
                        rgb = Core.rgb(g, b, r);
                        break;
                    case 1:
                        rgb = Core.rgb(g, r, b);
                        break;
                    case 2:
                        rgb = Core.rgb(b, g, r);
                        break;
                    case 3:
                        rgb = Core.rgb(b, r, g);
                        break;
                    case 4:
                        rgb = Core.rgb(r, g, b);
                        break;
                    case 5:
                        rgb = Core.rgb(r, b, g);
                        break;
                    default:
                        rgb = Core.rgb(0,0,0);
                }
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void flip(Stdim img,int select){
        BufferedImage im = new BufferedImage(img.getWidth(),
                img.getHeight(),BufferedImage.TYPE_INT_RGB);
        if(select == 0){
            for(int y = 0;y < im.getHeight();y++){
                for(int x = 0;x < im.getWidth();x++){
                    int c = img.getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int g = Core.g(c);
                    int b = Core.b(c);
                    int rgb = Core.rgb(r,g,b);
                    im.setRGB((-1*x) + im.getWidth() -1,(-1*y) + im.getHeight()-1,rgb);
                }
            }
        }
        else if(select == 1){
            for(int y = 0;y < im.getHeight();y++){
                for(int x = 0;x < im.getWidth();x++){
                    int c = img.getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int g = Core.g(c);
                    int b = Core.b(c);
                    int rgb = Core.rgb(r,g,b);
                    im.setRGB((-1*x) + im.getWidth() -1,y,rgb);
                }
            }
        }
        else if(select == 2){
            for(int y = 0;y < im.getHeight();y++){
                for(int x = 0;x < im.getWidth();x++){
                    int c = img.getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int g = Core.g(c);
                    int b = Core.b(c);
                    int rgb = Core.rgb(r,g,b);
                    im.setRGB(x,(-1*y) + im.getHeight()-1,rgb);
                }
            }
        }
        else if(select == 3){
            BufferedImage in = new BufferedImage( img.getHeight(),
                    img.getWidth(),BufferedImage.TYPE_INT_RGB);
            for(int y = 0;y < im.getHeight();y++){
                for(int x = 0;x < im.getWidth();x++){
                    int c = img.getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int g = Core.g(c);
                    int b = Core.b(c);
                    int rgb = Core.rgb(r,g,b);
                    in.setRGB(y,x,rgb);
                }
            }
            img.setImg(in);
            return;
        }
        else if(select == 4){
            BufferedImage in = new BufferedImage( img.getHeight(),
                    img.getWidth(),BufferedImage.TYPE_INT_RGB);
            for(int y = 1;y < im.getHeight();y++){
                for(int x = 0;x < im.getWidth();x++){
                    int c = img.getImg().getRGB(x,y);
                    int r = Core.r(c);
                    int g = Core.g(c);
                    int b = Core.b(c);
                    int rgb = Core.rgb(r,g,b);
                    in.setRGB((y*-1) + img.getHeight(),x,rgb);
                }
            }
            img.setImg(in);
            return;
        }
        img.setImg(im);
        Release(im);
    }

    public static float[] RGBavr(Stdim img){
        float[] avr = new float[3];
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = img.getImg().getRGB(x,y);
                avr[0] += (Core.r(c)) / (img.getHeight() * img.getWidth());
                avr[1] += (Core.g(c)) / (img.getHeight() * img.getWidth());
                avr[2] += (Core.b(c)) / (img.getHeight() * img.getWidth());
            }
        }
        return avr.clone();
    }

    public static void sum_twoims(Stdim img1,Stdim img2,Stdim dst,int select){
        BufferedImage tmp = new BufferedImage(img1.getWidth(),img1.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int r,g,b;
        r = g = b = 0;
        for(int y = 0;y < img1.getHeight();y++){
            for(int x = 0;x < img1.getWidth();x++){
                int c1 = img1.getImg().getRGB(x,y);
                int c2 = img2.getImg().getRGB(x,y);
                if(select == 0){
                    r = Core.r(c1) + Core.r(c2);
                    if(r > 255)
                        r = 255;
                    g = Core.g(c1) + Core.g(c2);
                    if(g > 255)
                        r = 255;
                    b = Core.b(c1) + Core.b(c2);
                    if(b > 255)
                        b = 255;
                }
                else if(select == 1){
                    r = Core.r(c1) - Core.r(c2);
                    if(r < 0)
                        r = 0;
                    g = Core.g(c1) - Core.g(c2);
                    if(g < 0)
                        r = 0;
                    b = Core.b(c1) - Core.b(c2);
                    if(b < 0)
                        b = 0;
                }
                int rgb = Core.rgb(r, g, b);
                tmp.setRGB(x,y,rgb);
                r = g = b = 0;
            }
        }
        dst.setImg(tmp);
        Release(tmp);
    }

    public static void cut(Stdim img, Size size)  {
        BufferedImage newimg = new BufferedImage(size.getWidth(),
                size.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int y = 0;y < size.getHeight();y++){
            for(int x = 0;x < size.getWidth();x++){
                int c = img.getImg().getRGB(x, y);
                newimg.setRGB(x, y, Core.rgb(Core.r(c), Core.g(c), Core.b(c)));
            }
        }
        img.setImg(newimg);
        Release(newimg);
    }

    public static void mask(Stdim img, Color st,Color ed){
        int r,g,b,rgb;
        BufferedImage newimg = img.clone().getImg();
        for(int y = 0;y < newimg.getHeight();y++){
            for(int x = 0;x < newimg.getWidth();x++){
                int c = newimg.getRGB(x,y);
                if((Core.r(c) - st.getR() > 0 && Core.r(c) - ed.getR() < 0) || (Core.r(c) - st.getR() < 0 && Core.r(c) - ed.getR() > 0)){
                    if((Core.g(c) - st.getR() > 0 && Core.g(c) - ed.getR() < 0) || (Core.g(c) - st.getR() < 0 && Core.g(c) - ed.getR() > 0)){
                        if((Core.g(c) - st.getR() > 0 && Core.g(c) - ed.getR() < 0) || (Core.g(c) - st.getR() < 0 && Core.g(c) - ed.getR() > 0)){
                            r = g = b = 0;
                        }else{
                            r = Core.r(c);
                            g = Core.g(c);
                            b = Core.b(c);
                        }
                    }
                    else{
                        r = Core.r(c);
                        g = Core.g(c);
                        b = Core.b(c);
                    }
                }else{
                    r = Core.r(c);
                    g = Core.g(c);
                    b = Core.b(c);
                }
                rgb = Core.rgb(r,g,b);
                newimg.setRGB(x,y,rgb);
                r = g = b = rgb = 0;
            }
        }
        img.setImg(newimg);
        Release(newimg);
    }

    private static void Alledge(Stdim img,int value){
        Stdim img2 = img.clone();
        edge(img,value,ImageProcessing.BESIDE_EDGE);
        BufferedImage newim = img.clone().getImg();
        edge(img2,value,ImageProcessing.LONG_EDGE);
        int cc;
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = newim.getRGB(x,y);
                int v = Core.r(c);
                if(v == 0){
                    cc = img2.getImg().getRGB(x,y);
                    int rgb = Core.rgb(cc,cc,cc);
                    newim.setRGB(x,y,rgb);
                }
            }
        }
        img.setImg(newim);
        Release(newim);
    }
    public static void outline(Stdim img,int value, Color col){
        BufferedImage newim = img.clone().getImg();
        edge(img,value,2);
        for(int y = 0;y < newim.getHeight();y++){
            for(int x = 0;x < newim.getWidth();x ++){
                int c = img.getImg().getRGB(x,y);
                int m = Core.r(c);
                if(m == 255){
                    int rgb = Core.rgb(col.getR(),col.getG(),col.getB());
                    newim.setRGB(x,y,rgb);
                }
            }
        }
        img.setImg(newim);
        Release(newim);
    }

    public static void rect(Stdim img, Point st, Point ed,Color col){
        BufferedImage newimg = img.clone().getImg();
        for(int y = st.getY();y < ed.getY();y++){
            for(int x = st.getX();x < ed.getX();x++){
                int c = newimg.getRGB(x,y);
                int r = Core.r(c) + col.getR();
                if(r > 255)
                    r = 255;
                else if(r < 0)
                    r = 0;
                int g = Core.g(c) + col.getG();
                if(g > 255)
                    g = 255;
                else if(g < 0)
                    g = 0;
                int b = Core.b(c) + col.getB();
                if(b > 255)
                    b = 255;
                else if(b < 0)
                    b = 0;
                int rgb = Core.rgb(r,g,b);
                newimg.setRGB(x,y,rgb);
            }
        }
        img.setImg(newimg);
        Release(newimg);
    }

    private static void Release(Object... args){
        for (Object obj : args) {
            obj = null;
        }
    }

    public static void hough(Stdim img, Color col){
        Matrix mat = new Matrix(180,ptgo(img.getWidth(), img.getHeight()),1,1);

        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                for(int rad = 0;rad < 180;rad++){
                    double p = x * Math.cos(Math.toRadians(rad)) + y * Math.sin(Math.toRadians(rad));
                    mat.getMat()[rad][Math.abs((int)p)][0] += 1;
                }
            }
        }
        for(int y = 0;y < mat.getMat()[0].length;y++){
            for(int x = 0;x < mat.getMat().length;x++){
                for(int rad = 0;rad < 180;rad++){
                    if(mat.getMat()[x][y][0] > 2000){
                        Houghfunction(img, x, y, col);
                    }
                }
            }
        }
        Release(mat);
    }

    private static int ptgo(int x, int y){
        return (int)Math.sqrt((x*x)+(y*y)) + 1;
    }

    private static void Houghfunction(Stdim img, int rad, int p, Color col){
        BufferedImage newimg = img.clone().getImg();
        for(int x = 0;x < newimg.getWidth();x++){
            int y = (int)((p - (x * Math.cos(Math.toRadians(rad)))) / Math.sin(Math.toRadians(rad)));
            int rgb = Core.rgb(col.getR(), col.getG(), col.getB());
            if(y >= 0) {
                if(y < img.getHeight()) {
                    newimg.setRGB(x, y, rgb);
                }
            }
        }
        img.setImg(newimg);
        Release(newimg);
    }

    public static void zero(Stdim img){
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int rgb = Core.rgb(0,0,0);
                img.getImg().setRGB(x, y, rgb);
            }
        }
    }

    public static void sepia(Stdim img){
        gray(img);
        BufferedImage im = img.clone().getImg();
        for(int y = 0;y < im.getHeight();y++){
            for(int x = 0;x < im.getWidth();x++){
                int c = im.getRGB(x,y);
                int r = (int)(((double)Core.r(c) / 255.0) * 240.0);
                int g = (int)(((double)Core.g(c) / 255.0) * 200.0);
                int b = (int)(((double)Core.b(c) / 255.0) * 145.0);
                int rgb = Core.rgb(r,g,b);
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void noise(Stdim img){
        final BufferedImage im = img.clone().getImg();
        IntBinaryOperator funcr = (x,y) ->{
            int val[] = new int[8];

            int c = im.getRGB(x-1,y+1);
            val[0] = Core.r(c);

            c = im.getRGB(x,y+1);
            val[1] = Core.r(c);

            c = im.getRGB(x+1,y+1);
            val[2] = Core.r(c);

            c = im.getRGB(x+1,y);
            val[3] = Core.r(c);

            c = im.getRGB(x+1,y-1);
            val[4] = Core.r(c);

            c = im.getRGB(x,y-1);
            val[5] = Core.r(c);

            c = im.getRGB(x-1,y-1);
            val[6] = Core.r(c);

            c = im.getRGB(x-1,y);
            val[7] = Core.r(c);

            Core.quickSort(val,0,7);
            return (val[3] + val[4]) / 2;
        };

        IntBinaryOperator funcg = (x,y) ->{
            int val[] = new int[8];

            int c = im.getRGB(x-1,y+1);
            val[0] = Core.g(c);

            c = im.getRGB(x,y+1);
            val[1] = Core.g(c);

            c = im.getRGB(x+1,y+1);
            val[2] = Core.g(c);

            c = im.getRGB(x+1,y);
            val[3] = Core.g(c);

            c = im.getRGB(x+1,y-1);
            val[4] = Core.g(c);

            c = im.getRGB(x,y-1);
            val[5] = Core.g(c);

            c = im.getRGB(x-1,y-1);
            val[6] = Core.g(c);

            c = im.getRGB(x-1,y);
            val[7] = Core.g(c);

            Core.quickSort(val,0,7);
            return (val[3] + val[4]) / 2;
        };

        IntBinaryOperator funcb = (x,y) ->{
            int val[] = new int[8];

            int c = im.getRGB(x-1,y+1);
            val[0] = Core.b(c);

            c = im.getRGB(x,y+1);
            val[1] = Core.b(c);

            c = im.getRGB(x+1,y+1);
            val[2] = Core.b(c);

            c = im.getRGB(x+1,y);
            val[3] = Core.b(c);

            c = im.getRGB(x+1,y-1);
            val[4] = Core.b(c);

            c = im.getRGB(x,y-1);
            val[5] = Core.b(c);

            c = im.getRGB(x-1,y-1);
            val[6] = Core.b(c);

            c = im.getRGB(x-1,y);
            val[7] = Core.b(c);

            Core.quickSort(val,0,7);
            return (val[3] + val[4]) / 2;
        };

        for(int y = 1;y < im.getHeight()-1;y++){
            for(int x = 1;x < im.getWidth()-1;x++){
                int r = funcr.applyAsInt(x, y);
                int g = funcg.applyAsInt(x, y);
                int b = funcb.applyAsInt(x, y);
                int rgb = Core.rgb(r, g, b);
                im.setRGB(x,y,rgb);
            }
        }
        img.setImg(im);
        Release(im);
    }

    public static void thin(Stdim img, int select){
        final int black = Core.rgb(0, 0, 0);
        int count = 0;
        twoway(img,0,0);
        int[][] mat = new int[img.getWidth()][img.getHeight()];
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = img.getImg().getRGB(x,y);
                mat[x][y] = Core.r(c);
            }
        }
        while(true) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                for (int x = 1; x < img.getWidth() - 1; x++) {
                    if (mat[x-1][y-1] == 255 && mat[x+1][y+1] == 0 && mat[x][y] == 255) {
                        mat[x][y] = 0;
                        img.getImg().setRGB(x,y,black);
                        count++;
                    }
                }
            }
            if(count == 0)
                break;
            count = 0;
        }
        if(select == 1)
            negp(img);
    }

    public static void psecol(Stdim img){
        gray(img);
        for(int y = 0;y < img.getHeight();y++){
            for(int x = 0;x < img.getWidth();x++){
                int c = img.getImg().getRGB(x,y);
                int r = Core.r(c);
                if(r >= 0 && r <=20)
                    img.getImg().setRGB(x,y,Core.rgb(150,150,255));
                else if(r >= 21 && r <= 40)
                    img.getImg().setRGB(x,y,Core.rgb(25,25,255));
                else if(r >= 41 && r <= 60)
                    img.getImg().setRGB(x,y,Core.rgb(140,255,140));
                else if(r >= 61 && r <= 80)
                    img.getImg().setRGB(x,y,Core.rgb(50,255,50));
                else if(r >= 81 && r <= 100)
                    img.getImg().setRGB(x,y,Core.rgb(255, 255, 0));
                else if(r >= 101 && r <= 120)
                    img.getImg().setRGB(x,y,Core.rgb(255, 200, 0));
                else if(r >= 121 && r <= 140)
                    img.getImg().setRGB(x,y,Core.rgb(255, 160, 0));
                else if(r >= 141 && r <= 160)
                    img.getImg().setRGB(x,y,Core.rgb(255, 120, 0));
                else if(r >= 161 && r <= 180)
                    img.getImg().setRGB(x,y,Core.rgb(255, 80, 0));
                else if(r >= 181 && r <= 200)
                    img.getImg().setRGB(x,y,Core.rgb(255, 40, 0));
                else if(r >= 201 && r <= 220)
                    img.getImg().setRGB(x,y,Core.rgb(255, 20, 0));
                else if(r >= 221 && r <= 240)
                    img.getImg().setRGB(x,y,Core.rgb(255, 0, 0));
                else
                    img.getImg().setRGB(x,y,Core.rgb(0,0,0));
            }
        }
    }

    static int thiner = -1;

    public static void circle(Stdim img, Color col, Point center, int r, int thin){
        if(thiner == -1) thiner = thin;
        for(int x = center.getX() - r;!(x >= center.getX() + r);x++){
            int qe[] = qe_curcle(x-center.getX(), r);
            if(!(qe[0] < 0 || qe[0] >= img.getHeight() || x + center.getX() < 0 ||
                    x + center.getX() >= img.getWidth())) {
                img.getImg().setRGB(x, qe[0] + center.getY(), Core.rgb(col.getR(), col.getG(), col.getB()));
                img.getImg().setRGB(x, qe[1] + center.getY(), Core.rgb(col.getR(), col.getG(), col.getB()));
            }
        }
        if(thiner != 0) {
            thiner--;
            circle(img, col, center, r - 1, 0);
        }else if(thiner == 0){
            thiner = -1;
            return;
        }
    }

    private static int[] qe_curcle(int x, int r){
        r *= r;
        int[] y = new int[2];
        int j =  r - x*x;
        y[0] = (int)Math.sqrt(j);
        y[1] = y[0] * -1;
        return y;
    }
}
