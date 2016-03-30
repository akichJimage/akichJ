package kernel;

import mt.NImg;
import mt.Order;

/**
 * Created by akichi on 16/03/12.
 */
public class Execute extends Thread{
    NImg im;
    Boolean finished;
    public Execute(NImg img){
        im = img;
        finished = false;
    }

    private Order cvt(Object obj){
        return (Order)obj;
    }

    public void run(){
        while(true){
            if (!im.order.isEmpty()) {
                switch (cvt(im.order.getFirst()).getFunction_code()){
                    case NImgFuncionCode.NEGA:
                        im.NInegp();
                        im.order.removeFirst();
                        break;
                    case NImgFuncionCode.GRAY:
                        im.NIgray();
                        im.order.removeFirst();
                        break;
                    case NImgFuncionCode.TWOWAY:
                        im.NItwoway();
                        im.order.removeFirst();
                    default:
                        break;
                }
            }else break;
        }
    }
}
