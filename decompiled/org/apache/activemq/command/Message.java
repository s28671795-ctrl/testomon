/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import java.beans.Transient;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.DeflaterOutputStream;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.BrokerId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.MarshallAware;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.util.MarshallingSupport;
import org.apache.activemq.wireformat.WireFormat;
import org.fusesource.hawtbuf.UTF8Buffer;

public abstract class Message
extends BaseCommand
implements MarshallAware,
MessageReference {
    public static final String ORIGINAL_EXPIRATION = "originalExpiration";
    public static final int DEFAULT_MINIMUM_MESSAGE_SIZE = 1024;
    protected MessageId messageId;
    protected ActiveMQDestination originalDestination;
    protected TransactionId originalTransactionId;
    protected ProducerId producerId;
    protected ActiveMQDestination destination;
    protected TransactionId transactionId;
    protected long expiration;
    protected long timestamp;
    protected long arrival;
    protected long brokerInTime;
    protected long brokerOutTime;
    protected String correlationId;
    protected ActiveMQDestination replyTo;
    protected boolean persistent;
    protected String type;
    protected byte priority;
    protected String groupID;
    protected int groupSequence;
    protected ConsumerId targetConsumerId;
    protected boolean compressed;
    protected String userID;
    protected ByteSequence content;
    protected ByteSequence marshalledProperties;
    protected DataStructure dataStructure;
    protected int redeliveryCounter;
    protected int size;
    protected Map<String, Object> properties;
    protected boolean readOnlyProperties;
    protected boolean readOnlyBody;
    protected transient boolean recievedByDFBridge;
    protected boolean droppable;
    protected boolean jmsXGroupFirstForConsumer;
    private transient short referenceCount;
    private transient ActiveMQConnection connection;
    transient MessageDestination regionDestination;
    transient MemoryUsage memoryUsage;
    transient AtomicBoolean processAsExpired = new AtomicBoolean(false);
    private BrokerId[] brokerPath;
    private BrokerId[] cluster;

    public abstract Message copy();

    public abstract void clearBody() throws JMSException;

    public abstract void storeContent();

    public abstract void storeContentAndClear();

    public void clearMarshalledState() throws JMSException {
        this.clearUnMarshalledState();
    }

    public void clearUnMarshalledState() throws JMSException {
        this.properties = null;
    }

    public boolean isMarshalled() {
        return this.isContentMarshalled() && this.isPropertiesMarshalled();
    }

    protected boolean isPropertiesMarshalled() {
        return this.marshalledProperties != null || this.properties == null;
    }

    protected boolean isContentMarshalled() {
        return this.content != null;
    }

    protected void copy(Message copy) {
        super.copy(copy);
        copy.producerId = this.producerId;
        copy.transactionId = this.transactionId;
        copy.destination = this.destination;
        copy.messageId = this.messageId != null ? this.messageId.copy() : null;
        copy.originalDestination = this.originalDestination;
        copy.originalTransactionId = this.originalTransactionId;
        copy.expiration = this.expiration;
        copy.timestamp = this.timestamp;
        copy.correlationId = this.correlationId;
        copy.replyTo = this.replyTo;
        copy.persistent = this.persistent;
        copy.redeliveryCounter = this.redeliveryCounter;
        copy.type = this.type;
        copy.priority = this.priority;
        copy.size = this.size;
        copy.groupID = this.groupID;
        copy.userID = this.userID;
        copy.groupSequence = this.groupSequence;
        if (this.properties != null) {
            copy.properties = new HashMap<String, Object>(this.properties);
            copy.properties.remove(ORIGINAL_EXPIRATION);
        } else {
            copy.properties = this.properties;
        }
        copy.content = this.copyByteSequence(this.content);
        copy.marshalledProperties = this.copyByteSequence(this.marshalledProperties);
        copy.dataStructure = this.dataStructure;
        copy.readOnlyProperties = this.readOnlyProperties;
        copy.readOnlyBody = this.readOnlyBody;
        copy.compressed = this.compressed;
        copy.recievedByDFBridge = this.recievedByDFBridge;
        copy.arrival = this.arrival;
        copy.connection = this.connection;
        copy.regionDestination = this.regionDestination;
        copy.brokerInTime = this.brokerInTime;
        copy.brokerOutTime = this.brokerOutTime;
        copy.memoryUsage = this.memoryUsage;
        copy.brokerPath = this.brokerPath;
        copy.jmsXGroupFirstForConsumer = this.jmsXGroupFirstForConsumer;
    }

    private ByteSequence copyByteSequence(ByteSequence content) {
        if (content != null) {
            return new ByteSequence(content.getData(), content.getOffset(), content.getLength());
        }
        return null;
    }

    public Object getProperty(String name) throws IOException {
        Object result;
        if (this.properties == null) {
            if (this.marshalledProperties == null) {
                return null;
            }
            this.properties = this.unmarsallProperties(this.marshalledProperties);
        }
        if ((result = this.properties.get(name)) instanceof UTF8Buffer) {
            result = result.toString();
        }
        return result;
    }

    public Map<String, Object> getProperties() throws IOException {
        if (this.properties == null) {
            if (this.marshalledProperties == null) {
                return Collections.EMPTY_MAP;
            }
            this.properties = this.unmarsallProperties(this.marshalledProperties);
        }
        return Collections.unmodifiableMap(this.properties);
    }

    public void clearProperties() {
        this.marshalledProperties = null;
        this.properties = null;
    }

    public void setProperty(String name, Object value) throws IOException {
        this.lazyCreateProperties();
        this.properties.put(name, value);
    }

    public void removeProperty(String name) throws IOException {
        this.lazyCreateProperties();
        this.properties.remove(name);
    }

    protected void lazyCreateProperties() throws IOException {
        if (this.properties == null) {
            if (this.marshalledProperties == null) {
                this.properties = new HashMap<String, Object>();
            } else {
                this.properties = this.unmarsallProperties(this.marshalledProperties);
                this.marshalledProperties = null;
            }
        } else {
            this.marshalledProperties = null;
        }
    }

    private Map<String, Object> unmarsallProperties(ByteSequence marshalledProperties) throws IOException {
        return MarshallingSupport.unmarshalPrimitiveMap(new DataInputStream(new ByteArrayInputStream(marshalledProperties)));
    }

    @Override
    public void beforeMarshall(WireFormat wireFormat) throws IOException {
        if (this.marshalledProperties == null && this.properties != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream os = new DataOutputStream(baos);
            MarshallingSupport.marshalPrimitiveMap(this.properties, os);
            os.close();
            this.marshalledProperties = baos.toByteSequence();
        }
    }

    @Override
    public void afterMarshall(WireFormat wireFormat) throws IOException {
    }

    @Override
    public void beforeUnmarshall(WireFormat wireFormat) throws IOException {
    }

    @Override
    public void afterUnmarshall(WireFormat wireFormat) throws IOException {
    }

    public ProducerId getProducerId() {
        return this.producerId;
    }

    public void setProducerId(ProducerId producerId) {
        this.producerId = producerId;
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public TransactionId getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isInTransaction() {
        return this.transactionId != null;
    }

    public ActiveMQDestination getOriginalDestination() {
        return this.originalDestination;
    }

    public void setOriginalDestination(ActiveMQDestination destination) {
        this.originalDestination = destination;
    }

    @Override
    public MessageId getMessageId() {
        return this.messageId;
    }

    public void setMessageId(MessageId messageId) {
        this.messageId = messageId;
    }

    public TransactionId getOriginalTransactionId() {
        return this.originalTransactionId;
    }

    public void setOriginalTransactionId(TransactionId transactionId) {
        this.originalTransactionId = transactionId;
    }

    @Override
    public String getGroupID() {
        return this.groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    @Override
    public int getGroupSequence() {
        return this.groupSequence;
    }

    public void setGroupSequence(int groupSequence) {
        this.groupSequence = groupSequence;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public boolean isPersistent() {
        return this.persistent;
    }

    public void setPersistent(boolean deliveryMode) {
        this.persistent = deliveryMode;
    }

    @Override
    public long getExpiration() {
        return this.expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public byte getPriority() {
        return this.priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority < 0 ? (byte)0 : (priority > 9 ? (byte)9 : priority);
    }

    public ActiveMQDestination getReplyTo() {
        return this.replyTo;
    }

    public void setReplyTo(ActiveMQDestination replyTo) {
        this.replyTo = replyTo;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ByteSequence getContent() {
        return this.content;
    }

    public void setContent(ByteSequence content) {
        this.content = content;
    }

    public ByteSequence getMarshalledProperties() {
        return this.marshalledProperties;
    }

    public void setMarshalledProperties(ByteSequence marshalledProperties) {
        this.marshalledProperties = marshalledProperties;
    }

    public DataStructure getDataStructure() {
        return this.dataStructure;
    }

    public void setDataStructure(DataStructure data) {
        this.dataStructure = data;
    }

    @Override
    public ConsumerId getTargetConsumerId() {
        return this.targetConsumerId;
    }

    public void setTargetConsumerId(ConsumerId targetConsumerId) {
        this.targetConsumerId = targetConsumerId;
    }

    @Override
    public boolean isExpired() {
        long expireTime = this.getExpiration();
        return expireTime > 0L && System.currentTimeMillis() > expireTime;
    }

    @Override
    public boolean isAdvisory() {
        return this.type != null && this.type.equals("Advisory");
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public boolean isRedelivered() {
        return this.redeliveryCounter > 0;
    }

    public void setRedelivered(boolean redelivered) {
        if (redelivered) {
            if (!this.isRedelivered()) {
                this.setRedeliveryCounter(1);
            }
        } else if (this.isRedelivered()) {
            this.setRedeliveryCounter(0);
        }
    }

    @Override
    public void incrementRedeliveryCounter() {
        ++this.redeliveryCounter;
    }

    @Override
    public int getRedeliveryCounter() {
        return this.redeliveryCounter;
    }

    public void setRedeliveryCounter(int deliveryCounter) {
        this.redeliveryCounter = deliveryCounter;
    }

    public BrokerId[] getBrokerPath() {
        return this.brokerPath;
    }

    public void setBrokerPath(BrokerId[] brokerPath) {
        this.brokerPath = brokerPath;
    }

    public boolean isReadOnlyProperties() {
        return this.readOnlyProperties;
    }

    public void setReadOnlyProperties(boolean readOnlyProperties) {
        this.readOnlyProperties = readOnlyProperties;
    }

    public boolean isReadOnlyBody() {
        return this.readOnlyBody;
    }

    public void setReadOnlyBody(boolean readOnlyBody) {
        this.readOnlyBody = readOnlyBody;
    }

    public ActiveMQConnection getConnection() {
        return this.connection;
    }

    public void setConnection(ActiveMQConnection connection) {
        this.connection = connection;
    }

    public long getArrival() {
        return this.arrival;
    }

    public void setArrival(long arrival) {
        this.arrival = arrival;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String jmsxUserID) {
        this.userID = jmsxUserID;
    }

    @Override
    public int getReferenceCount() {
        return this.referenceCount;
    }

    @Override
    public Message getMessageHardRef() {
        return this;
    }

    @Override
    public Message getMessage() {
        return this;
    }

    public void setRegionDestination(MessageDestination destination) {
        this.regionDestination = destination;
        if (this.memoryUsage == null) {
            this.memoryUsage = destination.getMemoryUsage();
        }
    }

    @Override
    @Transient
    public MessageDestination getRegionDestination() {
        return this.regionDestination;
    }

    public MemoryUsage getMemoryUsage() {
        return this.memoryUsage;
    }

    public void setMemoryUsage(MemoryUsage usage) {
        this.memoryUsage = usage;
    }

    @Override
    public boolean isMarshallAware() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int incrementReferenceCount() {
        int size;
        short rc;
        Message message = this;
        synchronized (message) {
            rc = this.referenceCount = (short)(this.referenceCount + 1);
            size = this.getSize();
        }
        if (rc == 1 && this.getMemoryUsage() != null) {
            this.getMemoryUsage().increaseUsage(size);
        }
        return rc;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int decrementReferenceCount() {
        int size;
        short rc;
        Message message = this;
        synchronized (message) {
            rc = this.referenceCount = (short)(this.referenceCount - 1);
            size = this.getSize();
        }
        if (rc == 0 && this.getMemoryUsage() != null) {
            this.getMemoryUsage().decreaseUsage(size);
        }
        return rc;
    }

    @Override
    public int getSize() {
        int minimumMessageSize = this.getMinimumMessageSize();
        if (this.size < minimumMessageSize || this.size == 0) {
            this.size = minimumMessageSize;
            if (this.marshalledProperties != null) {
                this.size += this.marshalledProperties.getLength();
            }
            if (this.content != null) {
                this.size += this.content.getLength();
            }
        }
        return this.size;
    }

    protected int getMinimumMessageSize() {
        int result = 1024;
        MessageDestination dest = this.regionDestination;
        if (dest != null) {
            result = dest.getMinimumMessageSize();
        }
        return result;
    }

    public boolean isRecievedByDFBridge() {
        return this.recievedByDFBridge;
    }

    public void setRecievedByDFBridge(boolean recievedByDFBridge) {
        this.recievedByDFBridge = recievedByDFBridge;
    }

    public void onMessageRolledBack() {
        this.incrementRedeliveryCounter();
    }

    public boolean isDroppable() {
        return this.droppable;
    }

    public void setDroppable(boolean droppable) {
        this.droppable = droppable;
    }

    public BrokerId[] getCluster() {
        return this.cluster;
    }

    public void setCluster(BrokerId[] cluster) {
        this.cluster = cluster;
    }

    @Override
    public boolean isMessage() {
        return true;
    }

    public long getBrokerInTime() {
        return this.brokerInTime;
    }

    public void setBrokerInTime(long brokerInTime) {
        this.brokerInTime = brokerInTime;
    }

    public long getBrokerOutTime() {
        return this.brokerOutTime;
    }

    public void setBrokerOutTime(long brokerOutTime) {
        this.brokerOutTime = brokerOutTime;
    }

    @Override
    public boolean isDropped() {
        return false;
    }

    public boolean isJMSXGroupFirstForConsumer() {
        return this.jmsXGroupFirstForConsumer;
    }

    public void setJMSXGroupFirstForConsumer(boolean val) {
        this.jmsXGroupFirstForConsumer = val;
    }

    public void compress() throws IOException {
        if (!this.isCompressed()) {
            this.storeContent();
            if (!this.isCompressed() && this.getContent() != null) {
                this.doCompress();
            }
        }
    }

    protected void doCompress() throws IOException {
        this.compressed = true;
        ByteSequence bytes = this.getContent();
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        DeflaterOutputStream os = new DeflaterOutputStream(bytesOut);
        ((OutputStream)os).write(bytes.data, bytes.offset, bytes.length);
        ((OutputStream)os).close();
        this.setContent(bytesOut.toByteSequence());
    }

    @Override
    public String toString() {
        return this.toString(null);
    }

    @Override
    public String toString(Map<String, Object> overrideFields) {
        try {
            this.getProperties();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return super.toString(overrideFields);
    }

    @Override
    public boolean canProcessAsExpired() {
        return this.processAsExpired.compareAndSet(false, true);
    }

    public static interface MessageDestination {
        public int getMinimumMessageSize();

        public MemoryUsage getMemoryUsage();
    }
}

