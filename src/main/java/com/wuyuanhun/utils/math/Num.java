package com.wuyuanhun.utils.math;

public class Num {
    public static int signOne(double x) {
        if(x == 0) return 0;
        return x > 0 ? 1 : -1;
    }
}
