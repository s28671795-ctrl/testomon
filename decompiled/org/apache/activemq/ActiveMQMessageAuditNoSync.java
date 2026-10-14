/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.util.BitArrayBin;
import org.apache.activemq.util.IdGenerator;
import org.apache.activemq.util.LRUCache;

public class ActiveMQMessageAuditNoSync
implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int DEFAULT_WINDOW_SIZE = 2048;
    public static final int MAXIMUM_PRODUCER_COUNT = 64;
    private int auditDepth;
    private int maximumNumberOfProducersToTrack;
    private final LRUCache<String, BitArrayBin> map;
    private transient boolean modified = true;

    public ActiveMQMessageAuditNoSync() {
        this(2048, 64);
    }

    public ActiveMQMessageAuditNoSync(int auditDepth, int maximumNumberOfProducersToTrack) {
        this.auditDepth = auditDepth;
        this.maximumNumberOfProducersToTrack = maximumNumberOfProducersToTrack;
        this.map = new LRUCache(0, maximumNumberOfProducersToTrack, 0.75f, true);
    }

    public int getAuditDepth() {
        return this.auditDepth;
    }

    public void setAuditDepth(int auditDepth) {
        this.auditDepth = auditDepth;
        this.modified = true;
    }

    public int getMaximumNumberOfProducersToTrack() {
        return this.maximumNumberOfProducersToTrack;
    }

    public void setMaximumNumberOfProducersToTrack(int maximumNumberOfProducersToTrack) {
        if (maximumNumberOfProducersToTrack < this.maximumNumberOfProducersToTrack) {
            LRUCache<String, BitArrayBin> newMap = new LRUCache<String, BitArrayBin>(0, maximumNumberOfProducersToTrack, 0.75f, true);
            newMap.putAll(this.map);
            this.map.clear();
            this.map.putAll(newMap);
        }
        this.map.setMaxCacheSize(maximumNumberOfProducersToTrack);
        this.maximumNumberOfProducersToTrack = maximumNumberOfProducersToTrack;
        this.modified = true;
    }

    public boolean isDuplicate(Message message) throws JMSException {
        return this.isDuplicate(message.getJMSMessageID());
    }

    public boolean isDuplicate(String id) {
        boolean answer = false;
        String seed = IdGenerator.getSeedFromId(id);
        if (seed != null) {
            long index;
            BitArrayBin bab = (BitArrayBin)this.map.get(seed);
            if (bab == null) {
                bab = new BitArrayBin(this.auditDepth);
                this.map.put(seed, bab);
                this.modified = true;
            }
            if ((index = IdGenerator.getSequenceFromId(id)) >= 0L) {
                answer = bab.setBit(index, true);
                this.modified = true;
            }
        }
        return answer;
    }

    public boolean isDuplicate(MessageReference message) {
        MessageId id = message.getMessageId();
        return this.isDuplicate(id);
    }

    public boolean isDuplicate(MessageId id) {
        ProducerId pid;
        boolean answer = false;
        if (id != null && (pid = id.getProducerId()) != null) {
            BitArrayBin bab = (BitArrayBin)this.map.get(pid.toString());
            if (bab == null) {
                bab = new BitArrayBin(this.auditDepth);
                this.map.put(pid.toString(), bab);
                this.modified = true;
            }
            answer = bab.setBit(id.getProducerSequenceId(), true);
        }
        return answer;
    }

    public void rollback(MessageReference message) {
        MessageId id = message.getMessageId();
        this.rollback(id);
    }

    public void rollback(MessageId id) {
        BitArrayBin bab;
        ProducerId pid;
        if (id != null && (pid = id.getProducerId()) != null && (bab = (BitArrayBin)this.map.get(pid.toString())) != null) {
            bab.setBit(id.getProducerSequenceId(), false);
            this.modified = true;
        }
    }

    public void rollback(String id) {
        BitArrayBin bab;
        String seed = IdGenerator.getSeedFromId(id);
        if (seed != null && (bab = (BitArrayBin)this.map.get(seed)) != null) {
            long index = IdGenerator.getSequenceFromId(id);
            bab.setBit(index, false);
            this.modified = true;
        }
    }

    public boolean isInOrder(Message msg) throws JMSException {
        return this.isInOrder(msg.getJMSMessageID());
    }

    public boolean isInOrder(String id) {
        BitArrayBin bab;
        String seed;
        boolean answer = true;
        if (id != null && (seed = IdGenerator.getSeedFromId(id)) != null && (bab = (BitArrayBin)this.map.get(seed)) != null) {
            long index = IdGenerator.getSequenceFromId(id);
            answer = bab.isInOrder(index);
            this.modified = true;
        }
        return answer;
    }

    public boolean isInOrder(MessageReference message) {
        return this.isInOrder(message.getMessageId());
    }

    public boolean isInOrder(MessageId id) {
        ProducerId pid;
        boolean answer = false;
        if (id != null && (pid = id.getProducerId()) != null) {
            BitArrayBin bab = (BitArrayBin)this.map.get(pid.toString());
            if (bab == null) {
                bab = new BitArrayBin(this.auditDepth);
                this.map.put(pid.toString(), bab);
                this.modified = true;
            }
            answer = bab.isInOrder(id.getProducerSequenceId());
        }
        return answer;
    }

    public long getLastSeqId(ProducerId id) {
        long result = -1L;
        BitArrayBin bab = (BitArrayBin)this.map.get(id.toString());
        if (bab != null) {
            result = bab.getLastSetIndex();
        }
        return result;
    }

    public void clear() {
        this.map.clear();
    }

    public boolean isModified() {
        return this.modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean modified() {
        if (this.modified) {
            this.modified = false;
            return true;
        }
        return false;
    }
}

