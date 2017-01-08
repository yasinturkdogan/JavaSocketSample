package com.turkdogan.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class BinaryEncoder extends TextLineEncoder {
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput out) throws Exception {

        String message = (String) o;

        byte[] dataToSend = message.getBytes("UTF-8");

        IoBuffer buffer = IoBuffer.allocate(dataToSend.length + 4); //the buffer to hold the output
        buffer.putInt(dataToSend.length);
        buffer.put(dataToSend);
        buffer.flip();
        out.write(buffer);

    }
}
