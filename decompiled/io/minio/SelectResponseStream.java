/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import com.google.common.io.ByteStreams;
import io.minio.Xml;
import io.minio.errors.InternalException;
import io.minio.errors.MinioException;
import io.minio.messages.Progress;
import io.minio.messages.Stats;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

public class SelectResponseStream
extends InputStream {
    private InputStream inputStream;
    private CRC32 crcHasher;
    private byte[] prelude = new byte[8];
    private byte[] preludeCrc = new byte[4];
    private byte[] messageCrc = new byte[4];
    private byte[] headerValueLen = new byte[2];
    private Stats stats = null;
    private ByteArrayInputStream payloadStream = null;

    public SelectResponseStream(InputStream inputStream2) {
        this.inputStream = inputStream2;
        this.crcHasher = new CRC32();
    }

    private Map<String, String> decodeHeaderData(ByteArrayInputStream bais) throws IOException {
        int n;
        HashMap<String, String> headerMap = new HashMap<String, String>();
        while ((n = bais.read()) >= 0) {
            int len = n & 0xFF;
            byte[] b = new byte[len];
            if (bais.read(b, 0, len) < 0) {
                throw new IOException("insufficient data");
            }
            String name = new String(b, StandardCharsets.UTF_8);
            n = bais.read();
            if (n < 0) {
                throw new IOException("insufficient data");
            }
            if ((n & 0xFF) != 7) {
                throw new IOException("header value type is not 7");
            }
            if (bais.read(this.headerValueLen, 0, 2) < 0) {
                throw new IOException("insufficient data");
            }
            len = (0xFF & this.headerValueLen[0]) << 8 | 0xFF & this.headerValueLen[1];
            b = new byte[len];
            if (bais.read(b, 0, len) < 0) {
                throw new IOException("insufficient data");
            }
            String value = new String(b, StandardCharsets.UTF_8);
            headerMap.put(name, value);
        }
        return headerMap;
    }

    private boolean populate() throws EOFException, IOException, InternalException, MinioException {
        ByteStreams.readFully(this.inputStream, this.prelude);
        ByteStreams.readFully(this.inputStream, this.preludeCrc);
        this.crcHasher.reset();
        this.crcHasher.update(this.prelude);
        if ((int)this.crcHasher.getValue() != ByteBuffer.wrap(this.preludeCrc).getInt()) {
            throw new IOException("prelude CRC mismatch; expected: " + (int)this.crcHasher.getValue() + ", got: " + ByteBuffer.wrap(this.preludeCrc).getInt());
        }
        int totalLength = ByteBuffer.wrap(this.prelude, 0, 4).getInt();
        byte[] data = new byte[totalLength - 12 - 4];
        ByteStreams.readFully(this.inputStream, data);
        ByteStreams.readFully(this.inputStream, this.messageCrc);
        this.crcHasher.reset();
        this.crcHasher.update(this.prelude);
        this.crcHasher.update(this.preludeCrc);
        this.crcHasher.update(data);
        if ((int)this.crcHasher.getValue() != ByteBuffer.wrap(this.messageCrc).getInt()) {
            throw new IOException("message CRC mismatch; expected: " + (int)this.crcHasher.getValue() + ", got: " + ByteBuffer.wrap(this.messageCrc).getInt());
        }
        int headerLength = ByteBuffer.wrap(this.prelude, 4, 4).getInt();
        Map<String, String> headerMap = null;
        try {
            headerMap = this.decodeHeaderData(new ByteArrayInputStream(data, 0, headerLength));
        }
        catch (EOFException e) {
            throw new IOException("invalid header read");
        }
        if (headerMap.get(":message-type").equals("error")) {
            throw new MinioException(headerMap.get(":error-code") + ":" + headerMap.get(":error-message"));
        }
        if (headerMap.get(":event-type").equals("End")) {
            throw new EOFException();
        }
        int payloadLength = totalLength - headerLength - 16;
        if (headerMap.get(":event-type").equals("Cont") || payloadLength < 1) {
            return false;
        }
        ByteArrayInputStream payloadStream = new ByteArrayInputStream(data, headerLength, payloadLength);
        if (headerMap.get(":event-type").equals("Progress")) {
            Stats stats;
            this.stats = stats = (Stats)Xml.unmarshal(Progress.class, new InputStreamReader((InputStream)payloadStream, StandardCharsets.UTF_8));
            return false;
        }
        if (headerMap.get(":event-type").equals("Stats")) {
            Stats stats;
            this.stats = stats = Xml.unmarshal(Stats.class, new InputStreamReader((InputStream)payloadStream, StandardCharsets.UTF_8));
            return false;
        }
        if (headerMap.get(":event-type").equals("Records")) {
            this.payloadStream = payloadStream;
            return true;
        }
        throw new InternalException("unknown event-type '" + headerMap.get(":event-type") + "'", null);
    }

    @Override
    public int read() throws IOException {
        int n;
        if (this.payloadStream == null) {
            try {
                while (!this.populate()) {
                }
            }
            catch (EOFException e) {
                return -1;
            }
            catch (MinioException e) {
                throw new IOException(e);
            }
        }
        if ((n = this.payloadStream.read()) < 0) {
            this.payloadStream = null;
            return this.read();
        }
        return n;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }

    public Stats stats() {
        return this.stats;
    }
}

