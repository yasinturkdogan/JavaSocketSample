package com.turkdogan.socket.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;

import java.nio.charset.Charset;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class MessagingProtocolCodecFactory implements ProtocolCodecFactory {
    private static final int MAX_INCOMING_MESSAGE_LENGTH = 2048;

    //You can set your own demiliter if you want. I prefer null char.
    private TextLineEncoderIgnoringMalform encoder = new TextLineEncoderIgnoringMalform(Charset.forName("UTF-8"), new LineDelimiter("\0"));
//    private BinaryEncoder encoder = new BinaryEncoder();
//    private GZippedEncoder encoder = new GZippedEncoder();

    private TextLineDecoder decoder = new TextLineDecoder(Charset.forName("UTF-8"), new LineDelimiter("\0"));

    public MessagingProtocolCodecFactory() {
        decoder.setMaxLineLength(MAX_INCOMING_MESSAGE_LENGTH);
    }

    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }
}
