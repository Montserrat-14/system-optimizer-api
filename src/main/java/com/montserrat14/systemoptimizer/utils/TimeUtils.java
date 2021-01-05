package com.montserrat14.systemoptimizer.utils;

public class TimeUtils {

    private static long startTime;

    public static void timeStart(){
        startTime =  System.nanoTime();
    }

    public static double timeEnd(){
        return (double)(System.nanoTime() - startTime) / 1_000_000_000;
    }
}
