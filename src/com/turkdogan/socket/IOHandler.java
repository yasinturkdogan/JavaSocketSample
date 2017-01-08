package com.turkdogan.socket;

import com.turkdogan.Console;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class IOHandler extends IoHandlerAdapter {


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        Console.log("Session created");
        session.write("Welcome");
    }


    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Console.log("Session closed");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Console.log("Exception caught");
        Console.log(cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Console.log("Message received : " + message);
        session.write("This is server saying : " + message);

    }
}
