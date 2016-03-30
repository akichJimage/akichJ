package kernel;

import mt.NImg;

import java.util.ArrayList;

/**
 * Created by akichi on 16/03/10.
 */
public class Schedule {
    //joinメソッドが動作を遅くしている原因の可能性あり
    //booleanのArrayListを作り出席制にして高速化
    public static short IDs = 0;
    public static void execute(NImg... NImgs){
        for (NImg img : NImgs) {
            img.ex.start();
        }
        for(NImg img : NImgs){
            try {
                img.ex.join();
            }catch (InterruptedException e){
                System.err.println(e);
            }
        }
    }

    public static short issueID(){
        return ++IDs;
    }

    public static short NonUpdate_issueID(){
        return IDs;
    }
}
