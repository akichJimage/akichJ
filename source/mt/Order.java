package mt;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by akichi on 16/03/11.
 */
public class Order {
    private short function_code;
    private Deque<Object> args = new ArrayDeque<>();

    public Order(short function_code){
        this.function_code = function_code;
    }

    public Order(short function_code, Object... objs){
        this.function_code = function_code;
        for (Object obj : objs) {
            args.add(obj);
        }
    }

    public short getFunction_code(){
        return this.function_code;
    }

    public Object getArgs(){
        return args.removeFirst();
    }

    public Object getArgs_keep(){return args.getFirst();}
}
