/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.store;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.MessageStore;

public interface ReferenceStore
extends MessageStore {
    public boolean addMessageReference(ConnectionContext var1, MessageId var2, ReferenceData var3) throws IOException;

    public ReferenceData getMessageReference(MessageId var1) throws IOException;

    public boolean supportsExternalBatchControl();

    @Override
    public void setBatch(MessageId var1);

    public Lock getStoreLock();

    public static class ReferenceData {
        long expiration;
        int fileId;
        int offset;

        public long getExpiration() {
            return this.expiration;
        }

        public void setExpiration(long expiration) {
            this.expiration = expiration;
        }

        public int getFileId() {
            return this.fileId;
        }

        public void setFileId(int file) {
            this.fileId = file;
        }

        public int getOffset() {
            return this.offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public String toString() {
            return "ReferenceData fileId=" + this.fileId + ", offset=" + this.offset + ", expiration=" + this.expiration;
        }
    }
}

