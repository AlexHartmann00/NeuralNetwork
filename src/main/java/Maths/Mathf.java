package Maths;

import java.util.ArrayList;
import java.util.Collections;

public class Mathf {
    public static int clamp(int x, int low, int high){
        if(x < low) return low;
        if(x > high) return high;
        return x;
    }

    public static float clamp(float x, float low, float high){
        if(x < low) return low;
        if(x > high) return high;
        return x;
    }

    public static int[] randomIDs(int size){
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i = 0; i < size; i++){
            ids.add(i);
        }
        Collections.shuffle(ids);
        int[] ret = new int[size];
        for(int i = 0; i < size; i++){
            ret[i] = ids.get(i);
        }
        return ret;
    }

}
