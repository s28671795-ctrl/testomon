/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.share;

import com.hierynomus.smbj.common.SMBRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class OperationBuckets {
    private List<OperationBucket> buckets = new ArrayList<OperationBucket>();
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    OperationBuckets() {
    }

    OperationBucket takeFreeBucket() {
        this.lock.writeLock().lock();
        try {
            for (OperationBucket bucket : this.buckets) {
                if (!bucket.free) continue;
                bucket.free = false;
                OperationBucket operationBucket = bucket;
                return operationBucket;
            }
            if (this.buckets.size() < 64) {
                OperationBucket bucket = new OperationBucket(this.buckets.size() + 1);
                this.buckets.add(bucket);
                OperationBucket operationBucket = bucket;
                return operationBucket;
            }
            throw new SMBRuntimeException("No OperationBucket found which is free");
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    void freeBucket(int index) {
        this.lock.writeLock().lock();
        try {
            OperationBucket bucket = this.buckets.get(index - 1);
            bucket.free = true;
            bucket.sequenceNumber = (short)(bucket.sequenceNumber + 1);
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    class OperationBucket {
        private boolean free = false;
        private final int index;
        private short sequenceNumber;

        OperationBucket(int index) {
            this.index = index;
            this.sequenceNumber = 0;
        }

        public int getIndex() {
            return this.index;
        }

        short getSequenceNumber() {
            return this.sequenceNumber;
        }
    }
}

