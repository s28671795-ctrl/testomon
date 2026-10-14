/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.share;

import com.hierynomus.mssmb2.SMB2Dialect;
import com.hierynomus.mssmb2.SMB2FileId;
import com.hierynomus.mssmb2.SMB2LockFlag;
import com.hierynomus.mssmb2.messages.submodule.SMB2LockElement;
import com.hierynomus.smbj.common.SmbPath;
import com.hierynomus.smbj.share.OperationBuckets;
import com.hierynomus.smbj.share.Share;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Open<S extends Share>
implements Closeable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected S share;
    protected SMB2FileId fileId;
    protected SmbPath name;
    private OperationBuckets operationBuckets = new OperationBuckets();

    Open(SMB2FileId fileId, SmbPath name, S share) {
        this.fileId = fileId;
        this.name = name;
        this.share = share;
    }

    public LockBuilder requestLock() {
        return new LockBuilder();
    }

    void lockRequest(List<SMB2LockElement> lockElements) {
        short sequenceNumber = 0;
        int sequenceIndex = 0;
        if (((Share)this.share).getDialect() != SMB2Dialect.SMB_2_0_2) {
            OperationBuckets.OperationBucket b = this.operationBuckets.takeFreeBucket();
            sequenceNumber = b.getSequenceNumber();
            sequenceIndex = b.getIndex();
        }
        ((Share)this.share).sendLockRequest(this.fileId, sequenceNumber, sequenceIndex, lockElements);
        if (((Share)this.share).getDialect() != SMB2Dialect.SMB_2_0_2) {
            this.operationBuckets.freeBucket(sequenceIndex);
        }
    }

    public SMB2FileId getFileId() {
        return this.fileId;
    }

    @Override
    public void close() {
        ((Share)this.share).closeFileId(this.fileId);
    }

    public void closeSilently() {
        try {
            this.close();
        }
        catch (Exception e) {
            this.logger.warn("{} close failed for {},{},{}", this.getClass().getSimpleName(), this.name, this.share, this.fileId, e);
        }
    }

    public final class LockBuilder {
        private List<SMB2LockElement> elements = new ArrayList<SMB2LockElement>();

        public LockBuilder exclusiveLock(long offset, long length) {
            return this.exclusiveLock(offset, length, false);
        }

        public LockBuilder exclusiveLock(long offset, long length, boolean failImmediately) {
            EnumSet<SMB2LockFlag> flags = EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_EXCLUSIVE_LOCK);
            if (failImmediately) {
                flags.add(SMB2LockFlag.SMB2_LOCKFLAG_FAIL_IMMEDIATELY);
            }
            return this.addElement(offset, length, flags);
        }

        public LockBuilder sharedLock(long offset, long length) {
            return this.sharedLock(offset, length, false);
        }

        public LockBuilder sharedLock(long offset, long length, boolean failImmediately) {
            EnumSet<SMB2LockFlag> flags = EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_SHARED_LOCK);
            if (failImmediately) {
                flags.add(SMB2LockFlag.SMB2_LOCKFLAG_FAIL_IMMEDIATELY);
            }
            return this.addElement(offset, length, flags);
        }

        public LockBuilder unlock(long offset, long length) {
            return this.addElement(offset, length, EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_UNLOCK));
        }

        private LockBuilder addElement(long offset, long length, Set<SMB2LockFlag> flags) {
            this.elements.add(new SMB2LockElement(offset, length, flags));
            return this;
        }

        public void send() {
            Open.this.lockRequest(this.elements);
        }
    }
}

