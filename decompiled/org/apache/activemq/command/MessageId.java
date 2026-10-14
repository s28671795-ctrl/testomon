/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.ProducerInfo;

public class MessageId
implements DataStructure,
Comparable<MessageId> {
    public static final byte DATA_STRUCTURE_TYPE = 110;
    protected String textView;
    protected ProducerId producerId;
    protected long producerSequenceId;
    protected long brokerSequenceId;
    private transient String key;
    private transient int hashCode;
    private transient AtomicReference<Object> dataLocator = new AtomicReference();
    private transient Object entryLocator;
    private transient Object plistLocator;
    private transient Object futureOrSequenceLong;

    public MessageId() {
        this.producerId = new ProducerId();
    }

    public MessageId(ProducerInfo producerInfo, long producerSequenceId) {
        this.producerId = producerInfo.getProducerId();
        this.producerSequenceId = producerSequenceId;
    }

    public MessageId(String messageKey) {
        this.setValue(messageKey);
    }

    public MessageId(String producerId, long producerSequenceId) {
        this(new ProducerId(producerId), producerSequenceId);
    }

    public MessageId(ProducerId producerId, long producerSequenceId) {
        this.producerId = producerId;
        this.producerSequenceId = producerSequenceId;
    }

    public void setValue(String messageKey) {
        this.key = messageKey;
        int p = messageKey.lastIndexOf(":");
        if (p < 0) {
            throw new NumberFormatException();
        }
        this.producerSequenceId = Long.parseLong(messageKey.substring(p + 1));
        messageKey = messageKey.substring(0, p);
        this.producerId = new ProducerId(messageKey);
    }

    public void setTextView(String key) {
        this.textView = key;
    }

    public String getTextView() {
        return this.textView;
    }

    @Override
    public byte getDataStructureType() {
        return 110;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        MessageId id = (MessageId)o;
        return this.producerSequenceId == id.producerSequenceId && this.producerId.equals(id.producerId);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.producerId.hashCode() ^ (int)this.producerSequenceId;
        }
        return this.hashCode;
    }

    public String toProducerKey() {
        if (this.textView == null) {
            return this.toString();
        }
        return this.producerId.toString() + ":" + this.producerSequenceId;
    }

    public String toString() {
        if (this.key == null) {
            this.key = this.textView != null ? (this.textView.startsWith("ID:") ? this.textView : "ID:" + this.textView) : this.producerId.toString() + ":" + this.producerSequenceId;
        }
        return this.key;
    }

    public ProducerId getProducerId() {
        return this.producerId;
    }

    public void setProducerId(ProducerId producerId) {
        this.producerId = producerId;
    }

    public long getProducerSequenceId() {
        return this.producerSequenceId;
    }

    public void setProducerSequenceId(long producerSequenceId) {
        this.producerSequenceId = producerSequenceId;
    }

    public long getBrokerSequenceId() {
        return this.brokerSequenceId;
    }

    public void setBrokerSequenceId(long brokerSequenceId) {
        this.brokerSequenceId = brokerSequenceId;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    public MessageId copy() {
        MessageId copy = new MessageId(this.producerId, this.producerSequenceId);
        copy.key = this.key;
        copy.brokerSequenceId = this.brokerSequenceId;
        copy.dataLocator = this.dataLocator;
        copy.entryLocator = this.entryLocator;
        copy.futureOrSequenceLong = this.futureOrSequenceLong;
        copy.plistLocator = this.plistLocator;
        copy.textView = this.textView;
        return copy;
    }

    @Override
    public int compareTo(MessageId other) {
        int result = -1;
        if (other != null) {
            result = this.toString().compareTo(other.toString());
        }
        return result;
    }

    public Object getDataLocator() {
        return this.dataLocator.get();
    }

    public void setDataLocator(Object value) {
        this.dataLocator.set(value);
    }

    public Object getFutureOrSequenceLong() {
        return this.futureOrSequenceLong;
    }

    public void setFutureOrSequenceLong(Object futureOrSequenceLong) {
        this.futureOrSequenceLong = futureOrSequenceLong;
    }

    public Object getEntryLocator() {
        return this.entryLocator;
    }

    public void setEntryLocator(Object entryLocator) {
        this.entryLocator = entryLocator;
    }

    public Object getPlistLocator() {
        return this.plistLocator;
    }

    public void setPlistLocator(Object plistLocator) {
        this.plistLocator = plistLocator;
    }

    private Object readResolve() {
        this.dataLocator = new AtomicReference();
        return this;
    }
}

