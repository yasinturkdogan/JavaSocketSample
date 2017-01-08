package com.turkdogan.socket;

import com.turkdogan.Console;
import com.turkdogan.Main;
import com.turkdogan.socket.codec.MessagingProtocolCodecFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.net.InetSocketAddress;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class SocketManager {

    private IoAcceptor acceptor;

    public SocketManager() {
        this.acceptor = createAcceptor();
    }


    private IoAcceptor createAcceptor() {
        int minaThreadCount = Runtime.getRuntime().availableProcessors();
        NioSocketAcceptor nioSocketAcceptor = new NioSocketAcceptor(minaThreadCount);
        nioSocketAcceptor.getSessionConfig().setTcpNoDelay(true);
        nioSocketAcceptor.getSessionConfig().setWriteTimeout(600);
        nioSocketAcceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 60);
        nioSocketAcceptor.setReuseAddress(true);
        return nioSocketAcceptor;
    }
    public void start() {

        IOHandler ioHandler = new IOHandler();

        acceptor.setHandler(ioHandler);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessagingProtocolCodecFactory()));

        try {
            InetSocketAddress socketAddress = new InetSocketAddress(Main.listPort);
            acceptor.bind(socketAddress);
        } catch (java.io.IOException e) {
            Console.log(e);
        }

        Console.log("Listening on port " + Main.listPort);
    }
}
