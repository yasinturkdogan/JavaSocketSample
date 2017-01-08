package com.turkdogan;

import com.turkdogan.socket.SocketManager;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class Main {

    public static int listPort = 8888;


    public static void main(String[] args) {

        SocketManager socketManager = new SocketManager();
        socketManager.start();
    }
}
