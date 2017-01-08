package com.turkdogan;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class Console {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void log(Throwable exception) {
        exception.printStackTrace();
    }
}
