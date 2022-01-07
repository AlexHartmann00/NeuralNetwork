package Maths;

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
}
