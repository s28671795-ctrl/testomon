/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import io.minio.ByteBufferStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.SequenceInputStream;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nonnull;
import okio.Okio;
import okio.Source;

class PartSource {
    private int partNumber;
    private long size;
    private String md5Hash;
    private String sha256Hash;
    private RandomAccessFile file;
    private long position;
    private ByteBufferStream[] buffers;

    private PartSource(int partNumber, long size, String md5Hash, String sha256Hash) {
        this.partNumber = partNumber;
        this.size = size;
        this.md5Hash = md5Hash;
        this.sha256Hash = sha256Hash;
    }

    public PartSource(int partNumber, @Nonnull RandomAccessFile file, long size, String md5Hash, String sha256Hash) throws IOException {
        this(partNumber, size, md5Hash, sha256Hash);
        this.file = Objects.requireNonNull(file, "file must not be null");
        this.position = this.file.getFilePointer();
    }

    public PartSource(int partNumber, @Nonnull ByteBufferStream[] buffers, long size, String md5Hash, String sha256Hash) {
        this(partNumber, size, md5Hash, sha256Hash);
        this.buffers = Objects.requireNonNull(buffers, "buffers must not be null");
    }

    public int partNumber() {
        return this.partNumber;
    }

    public long size() {
        return this.size;
    }

    public String md5Hash() {
        return this.md5Hash;
    }

    public String sha256Hash() {
        return this.sha256Hash;
    }

    public Source source() throws IOException {
        if (this.file != null) {
            this.file.seek(this.position);
            return Okio.source(Channels.newInputStream(this.file.getChannel()));
        }
        InputStream stream = this.buffers[0].inputStream();
        if (this.buffers.length == 1) {
            return Okio.source(stream);
        }
        ArrayList<InputStream> streams = new ArrayList<InputStream>();
        streams.add(stream);
        for (int i = 1; i < this.buffers.length && this.buffers[i].size() != 0; ++i) {
            streams.add(this.buffers[i].inputStream());
        }
        if (streams.size() == 1) {
            return Okio.source(stream);
        }
        return Okio.source(new SequenceInputStream(Collections.enumeration(streams)));
    }
}

