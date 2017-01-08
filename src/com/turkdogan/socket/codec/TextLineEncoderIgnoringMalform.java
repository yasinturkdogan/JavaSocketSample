package com.turkdogan.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

/**
 * Created by yasinturkdogan on 1/6/17.
 */
public class TextLineEncoderIgnoringMalform extends TextLineEncoder {
    private static final AttributeKey ENCODER = new AttributeKey(TextLineEncoder.class, "encoder");

    private Charset charset;
    private LineDelimiter delimiter;
    private int maxLineLength = Integer.MAX_VALUE;

    public TextLineEncoderIgnoringMalform(Charset charset, LineDelimiter delimiter) {
        super(charset, delimiter);
        this.charset = charset;
        this.delimiter = delimiter;

    }

    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        CharsetEncoder encoder = (CharsetEncoder) session.getAttribute(ENCODER);

        if (encoder == null) {
            encoder = charset.newEncoder();
            session.setAttribute(ENCODER, encoder);
        }
        encoder.onMalformedInput(CodingErrorAction.IGNORE);


        String value = (message == null ? "" : message.toString());
        IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
        buf.putString(value, encoder);

        if (buf.position() > maxLineLength) {
            throw new IllegalArgumentException("Line length: " + buf.position());
        }

        buf.putString(delimiter.getValue(), encoder);
        buf.flip();
        out.write(buf);
    }
}
