package com.turkdogan.socket.codec;

import com.turkdogan.Console;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class GZippedEncoder extends ProtocolEncoderAdapter {

    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput out) throws Exception {

        String message = (String) o;
        byte[] dataToSend = message.getBytes("UTF-8");

        //Gzip
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(dataToSend.length);
        GZIPOutputStream zipStream = new GZIPOutputStream(byteStream,dataToSend.length);
        zipStream.write(dataToSend);
        zipStream.close();
        byteStream.close();

        byte[] compressedData = byteStream.toByteArray();
        double percent = (double)(dataToSend.length - compressedData.length) / (double)dataToSend.length;

        Console.log("Compressed %" + Math.floor(percent * 100));

        IoBuffer buffer = IoBuffer.allocate(compressedData.length + 4); //the buffer to hold the output
        buffer.putInt(compressedData.length);
        buffer.put(compressedData);
        buffer.flip();
        out.write(buffer);

    }
}
