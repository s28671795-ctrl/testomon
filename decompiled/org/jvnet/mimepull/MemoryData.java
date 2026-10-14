/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.mimepull;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jvnet.mimepull.Chunk;
import org.jvnet.mimepull.Data;
import org.jvnet.mimepull.DataFile;
import org.jvnet.mimepull.DataHead;
import org.jvnet.mimepull.FileData;
import org.jvnet.mimepull.MIMEConfig;
import org.jvnet.mimepull.MIMEParsingException;
import org.jvnet.mimepull.TempFiles;

final class MemoryData
implements Data {
    private static final Logger LOGGER = Logger.getLogger(MemoryData.class.getName());
    private final byte[] data;
    private final int len;
    private final MIMEConfig config;

    MemoryData(ByteBuffer buf, MIMEConfig config) {
        this.data = buf.array();
        this.len = buf.limit();
        this.config = config;
    }

    @Override
    public int size() {
        return this.len;
    }

    @Override
    public byte[] read() {
        return this.data;
    }

    @Override
    public long writeTo(DataFile file) {
        return file.writeTo(this.data, 0, this.len);
    }

    @Override
    public Data createNext(DataHead dataHead, ByteBuffer buf) {
        if (!this.config.isOnlyMemory() && dataHead.inMemory >= this.config.memoryThreshold) {
            try {
                String prefix = this.config.getTempFilePrefix();
                String suffix = this.config.getTempFileSuffix();
                File tempFile = TempFiles.createTempFile(prefix, suffix, this.config.getTempDir());
                tempFile.deleteOnExit();
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Created temp file = {0}", tempFile);
                }
                dataHead.dataFile = new DataFile(tempFile);
            }
            catch (IOException ioe) {
                throw new MIMEParsingException(ioe);
            }
            if (dataHead.head != null) {
                Chunk c = dataHead.head;
                while (c != null) {
                    long pointer = c.data.writeTo(dataHead.dataFile);
                    c.data = new FileData(dataHead.dataFile, pointer, this.len);
                    c = c.next;
                }
            }
            return new FileData(dataHead.dataFile, buf);
        }
        return new MemoryData(buf, this.config);
    }
}

