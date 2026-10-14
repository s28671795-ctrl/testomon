/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal.messages;

import java.io.IOException;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.messages.MessageInputStream;
import org.eclipse.jetty.websocket.core.internal.messages.MessageSink;

public class MessageReader
extends Reader
implements MessageSink {
    private static final int BUFFER_SIZE = 4096;
    private final ByteBuffer buffer;
    private final MessageInputStream stream;
    private final CharsetDecoder utf8Decoder = StandardCharsets.UTF_8.newDecoder().onUnmappableCharacter(CodingErrorAction.REPORT).onMalformedInput(CodingErrorAction.REPORT);

    public MessageReader() {
        this(4096);
    }

    public MessageReader(int bufferSize) {
        this.stream = new MessageInputStream();
        this.buffer = BufferUtil.allocate(bufferSize);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        CoderResult result;
        int read;
        CharBuffer charBuffer = CharBuffer.wrap(cbuf, off, len);
        boolean endOfInput = false;
        while ((read = this.stream.read(this.buffer)) != 0) {
            if (read >= 0) continue;
            endOfInput = true;
            break;
        }
        if ((result = this.utf8Decoder.decode(this.buffer, charBuffer, endOfInput)).isError()) {
            result.throwException();
        }
        if (endOfInput && charBuffer.position() == 0) {
            return -1;
        }
        return charBuffer.position();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    @Override
    public void fail(Throwable failure) {
        this.stream.fail(failure);
    }

    @Override
    public void accept(Frame frame, Callback callback) {
        this.stream.accept(frame, callback);
    }
}

