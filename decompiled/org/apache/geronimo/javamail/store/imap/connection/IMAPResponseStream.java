/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.store.imap.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.store.imap.connection.IMAPACLResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPCapabilityResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPContinuationResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFetchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPListResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPListRightsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPMyRightsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPNamespaceResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPOkResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPPermanentFlagsResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPQuotaResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPQuotaRootResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseBuffer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPResponseTokenizer;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSearchResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPServerStatusResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPSizeResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPStatusResponse;
import org.apache.geronimo.javamail.store.imap.connection.IMAPTaggedResponse;

public class IMAPResponseStream {
    protected final int BUFFER_SIZE = 1024;
    protected InputStream in;
    IMAPResponseBuffer out;
    protected byte[] buffer = new byte[1024];
    int position;
    int length;

    public IMAPResponseStream(InputStream in) {
        this.in = in;
        this.out = new IMAPResponseBuffer();
    }

    public int read() throws IOException {
        if (!this.fillBufferIfNeeded()) {
            return -1;
        }
        return this.buffer[this.position++];
    }

    protected boolean fillBufferIfNeeded() throws IOException {
        if (this.position >= this.length) {
            int readLength = 0;
            while (readLength == 0) {
                readLength = this.in.read(this.buffer, 0, this.buffer.length);
            }
            if (readLength == -1) {
                return false;
            }
            this.position = 0;
            this.length = readLength;
        }
        return true;
    }

    public IMAPResponse readResponse() throws MessagingException {
        this.out.reset();
        byte[] data = this.readData();
        IMAPResponseTokenizer tokenizer = new IMAPResponseTokenizer(data);
        IMAPResponseTokenizer.Token token = tokenizer.next();
        int type = token.getType();
        if (type == 43) {
            return new IMAPContinuationResponse(data);
        }
        if (type == 42) {
            token = tokenizer.next();
            if (token.isType(-4)) {
                int size = token.getInteger();
                token = tokenizer.next();
                String keyword = token.getValue();
                if (keyword.equals("FETCH")) {
                    return new IMAPFetchResponse(size, data, tokenizer);
                }
                return new IMAPSizeResponse(keyword, size, data);
            }
            if (token.getType() != -1) {
                try {
                    throw new MessagingException("Unknown server response: " + new String(data, "ISO8859-1"));
                }
                catch (UnsupportedEncodingException e) {
                    throw new MessagingException("Unknown server response: " + new String(data));
                }
            }
            String keyword = token.getValue();
            if (keyword.equals("OK")) {
                return this.parseUntaggedOkResponse(data, tokenizer);
            }
            if (keyword.equals("PREAUTH")) {
                return new IMAPServerStatusResponse("PREAUTH", tokenizer.getRemainder(), data);
            }
            if (keyword.equals("BYE")) {
                return new IMAPServerStatusResponse("BYE", tokenizer.getRemainder(), data);
            }
            if (keyword.equals("BAD")) {
                return new IMAPServerStatusResponse("BAD", tokenizer.getRemainder(), data);
            }
            if (keyword.equals("NO")) {
                return new IMAPServerStatusResponse("NO", tokenizer.getRemainder(), data);
            }
            if (keyword.equals("CAPABILITY")) {
                return new IMAPCapabilityResponse(tokenizer, data);
            }
            if (keyword.equals("LIST")) {
                return new IMAPListResponse("LIST", data, tokenizer);
            }
            if (keyword.equals("FLAGS")) {
                return new IMAPFlagsResponse(data, tokenizer);
            }
            if (keyword.equals("LSUB")) {
                return new IMAPListResponse("LSUB", data, tokenizer);
            }
            if (keyword.equals("STATUS")) {
                return new IMAPStatusResponse(data, tokenizer);
            }
            if (keyword.equals("SEARCH")) {
                return new IMAPSearchResponse(data, tokenizer);
            }
            if (keyword.equals("ACL")) {
                return new IMAPACLResponse(data, tokenizer);
            }
            if (keyword.equals("LISTRIGHTS")) {
                return new IMAPListRightsResponse(data, tokenizer);
            }
            if (keyword.equals("MYRIGHTS")) {
                return new IMAPMyRightsResponse(data, tokenizer);
            }
            if (keyword.equals("QUOTAROOT")) {
                return new IMAPQuotaRootResponse(data, tokenizer);
            }
            if (keyword.equals("QUOTA")) {
                return new IMAPQuotaResponse(data, tokenizer);
            }
            if (keyword.equals("NAMESPACE")) {
                return new IMAPNamespaceResponse(data, tokenizer);
            }
        } else if (type == -1) {
            String tag = token.getValue();
            token = tokenizer.next();
            String status = token.getValue();
            return new IMAPTaggedResponse(tag, status, tokenizer.getRemainder(), data);
        }
        try {
            throw new MessagingException("Unknown server response: " + new String(data, "ISO8859-1"));
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Unknown server response: " + new String(data));
        }
    }

    private IMAPResponse parseUntaggedOkResponse(byte[] data, IMAPResponseTokenizer tokenizer) throws MessagingException {
        IMAPResponseTokenizer.Token token = tokenizer.peek();
        if (token.getType() != 91) {
            return new IMAPOkResponse("OK", null, tokenizer.getRemainder(), data);
        }
        tokenizer.next();
        token = tokenizer.next();
        String keyword = token.getValue();
        if (keyword.equals("PERMANENTFLAGS")) {
            return new IMAPPermanentFlagsResponse(data, tokenizer);
        }
        ArrayList<IMAPResponseTokenizer.Token> arguments = new ArrayList<IMAPResponseTokenizer.Token>();
        token = tokenizer.next();
        while (token.getType() != 93) {
            arguments.add(token);
            token = tokenizer.next();
        }
        return new IMAPOkResponse(keyword, arguments, tokenizer.getRemainder(), data);
    }

    public byte[] readData() throws MessagingException {
        this.out.reset();
        this.readBuffer();
        return this.out.toByteArray();
    }

    public void readBuffer() throws MessagingException {
        while (true) {
            int next;
            int ch;
            if ((ch = this.nextByte()) == 13 && (next = this.nextByte()) == 10) {
                this.checkLiteral();
                return;
            }
            this.out.write(ch);
        }
    }

    public void checkLiteral() throws MessagingException {
        try {
            int length = this.out.getLiteralLength();
            if (length == -1) {
                return;
            }
            this.out.write(13);
            this.out.write(10);
            if (length > 0) {
                byte[] bytes = new byte[length];
                int offset = 0;
                while (length > 0) {
                    int read = -1;
                    try {
                        read = this.in.read(bytes, offset, length);
                    }
                    catch (IOException e) {
                        throw new MessagingException("Unexpected read error on server connection", e);
                    }
                    if (read == -1) {
                        throw new MessagingException("Unexpected end of stream");
                    }
                    length -= read;
                    offset += read;
                }
                this.out.write(bytes);
            }
            this.readBuffer();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected int nextByte() throws MessagingException {
        try {
            int next = this.in.read();
            if (next == -1) {
                throw new MessagingException("Read error on IMAP server connection");
            }
            return next;
        }
        catch (IOException e) {
            throw new MessagingException("Unexpected error on server stream", e);
        }
    }
}

