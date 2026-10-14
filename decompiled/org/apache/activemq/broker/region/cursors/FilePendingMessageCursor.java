/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.IndirectMessageReference;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PrioritizedPendingList;
import org.apache.activemq.command.Message;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.store.PList;
import org.apache.activemq.store.PListEntry;
import org.apache.activemq.store.PListStore;
import org.apache.activemq.usage.SystemUsage;
import org.apache.activemq.usage.Usage;
import org.apache.activemq.usage.UsageListener;
import org.apache.activemq.util.ByteSequence;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePendingMessageCursor
extends AbstractPendingMessageCursor
implements UsageListener {
    static final Logger LOG = LoggerFactory.getLogger(FilePendingMessageCursor.class);
    private static final AtomicLong NAME_COUNT = new AtomicLong();
    protected Broker broker;
    private final PListStore store;
    private final String name;
    private PendingList memoryList;
    private PList diskList;
    private Iterator<MessageReference> iter;
    private Destination regionDestination;
    private boolean iterating;
    private boolean flushRequired;
    private final AtomicBoolean started = new AtomicBoolean();
    private final WireFormat wireFormat = new OpenWireFormat();

    public FilePendingMessageCursor(Broker broker, String name, boolean prioritizedMessages) {
        super(prioritizedMessages);
        this.memoryList = this.prioritizedMessages ? new PrioritizedPendingList() : new OrderedPendingList();
        this.broker = broker;
        this.store = broker.getTempDataStore();
        this.name = NAME_COUNT.incrementAndGet() + "_" + name;
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            if (this.broker != null) {
                this.wireFormat.setVersion(this.broker.getBrokerService().getStoreOpenWireVersion());
            }
            super.start();
            if (this.systemUsage != null) {
                this.systemUsage.getMemoryUsage().addUsageListener(this);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.started.compareAndSet(true, false)) {
            super.stop();
            if (this.systemUsage != null) {
                this.systemUsage.getMemoryUsage().removeUsageListener(this);
            }
        }
    }

    @Override
    public synchronized boolean isEmpty() {
        if (this.memoryList.isEmpty() && this.isDiskListEmpty()) {
            return true;
        }
        Iterator<MessageReference> iterator2 = this.memoryList.iterator();
        while (iterator2.hasNext()) {
            MessageReference node = iterator2.next();
            if (node == QueueMessageReference.NULL_MESSAGE) continue;
            if (!node.isDropped()) {
                return false;
            }
            iterator2.remove();
        }
        return this.isDiskListEmpty();
    }

    @Override
    public synchronized void reset() {
        this.iterating = true;
        this.last = null;
        this.iter = this.isDiskListEmpty() ? this.memoryList.iterator() : new DiskIterator();
    }

    @Override
    public synchronized void release() {
        this.iterating = false;
        if (this.iter instanceof DiskIterator) {
            ((DiskIterator)this.iter).release();
        }
        if (this.flushRequired) {
            this.flushRequired = false;
            if (!this.hasSpace()) {
                this.flushToDisk();
            }
        }
        this.iter = null;
    }

    @Override
    public synchronized void destroy() throws Exception {
        this.stop();
        for (MessageReference node : this.memoryList) {
            node.decrementReferenceCount();
        }
        this.memoryList.clear();
        this.destroyDiskList();
    }

    private void destroyDiskList() throws Exception {
        if (this.diskList != null) {
            this.store.removePList(this.name);
            this.diskList = null;
        }
    }

    @Override
    public synchronized LinkedList<MessageReference> pageInList(int maxItems) {
        int count;
        LinkedList<MessageReference> result = new LinkedList<MessageReference>();
        DiskIterator i = this.memoryList.iterator();
        for (count = 0; i.hasNext() && count < maxItems; ++count) {
            MessageReference ref = i.next();
            ref.incrementReferenceCount();
            result.add(ref);
        }
        if (count < maxItems && !this.isDiskListEmpty()) {
            i = new DiskIterator();
            while (i.hasNext() && count < maxItems) {
                Message message = (Message)i.next();
                message.setRegionDestination(this.regionDestination);
                message.setMemoryUsage(this.getSystemUsage().getMemoryUsage());
                message.incrementReferenceCount();
                result.add(message);
                ++count;
            }
        }
        return result;
    }

    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long maxWaitTime) throws Exception {
        if (!node.isExpired()) {
            try {
                this.regionDestination = (Destination)node.getMessage().getRegionDestination();
                if (this.isDiskListEmpty() && (this.hasSpace() || this.store == null)) {
                    this.memoryList.addMessageLast(node);
                    node.incrementReferenceCount();
                    this.setCacheEnabled(true);
                    return true;
                }
                if (!this.hasSpace() && this.isDiskListEmpty()) {
                    this.expireOldMessages();
                    if (this.hasSpace()) {
                        this.memoryList.addMessageLast(node);
                        node.incrementReferenceCount();
                        return true;
                    }
                    this.flushToDisk();
                }
                if (this.systemUsage.getTempUsage().waitForSpace(maxWaitTime)) {
                    ByteSequence bs = this.getByteSequence(node.getMessage());
                    this.getDiskList().addLast(node.getMessageId().toString(), bs);
                    return true;
                }
                return false;
            }
            catch (Exception e) {
                LOG.error("Caught an Exception adding a message: {} first to FilePendingMessageCursor ", (Object)node, (Object)e);
                throw new RuntimeException(e);
            }
        }
        this.discardExpiredMessage(node);
        return true;
    }

    @Override
    public synchronized void addMessageFirst(MessageReference node) {
        if (!node.isExpired()) {
            try {
                this.regionDestination = (Destination)node.getMessage().getRegionDestination();
                if (this.isDiskListEmpty() && this.hasSpace()) {
                    this.memoryList.addMessageFirst(node);
                    node.incrementReferenceCount();
                    this.setCacheEnabled(true);
                    return;
                }
                if (!this.hasSpace() && this.isDiskListEmpty()) {
                    this.expireOldMessages();
                    if (this.hasSpace()) {
                        this.memoryList.addMessageFirst(node);
                        node.incrementReferenceCount();
                        return;
                    }
                    this.flushToDisk();
                }
                this.systemUsage.getTempUsage().waitForSpace();
                node.decrementReferenceCount();
                ByteSequence bs = this.getByteSequence(node.getMessage());
                Object locator = this.getDiskList().addFirst(node.getMessageId().toString(), bs);
                node.getMessageId().setPlistLocator(locator);
            }
            catch (Exception e) {
                LOG.error("Caught an Exception adding a message: {} first to FilePendingMessageCursor ", (Object)node, (Object)e);
                throw new RuntimeException(e);
            }
        } else {
            this.discardExpiredMessage(node);
        }
    }

    @Override
    public synchronized boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public synchronized MessageReference next() {
        MessageReference reference;
        this.last = reference = this.iter.next();
        if (!this.isDiskListEmpty()) {
            reference.getMessage().setRegionDestination(this.regionDestination);
            reference.getMessage().setMemoryUsage(this.getSystemUsage().getMemoryUsage());
        }
        reference.incrementReferenceCount();
        return reference;
    }

    @Override
    public synchronized void remove() {
        this.iter.remove();
        if (this.last != null) {
            this.last.decrementReferenceCount();
        }
    }

    @Override
    public synchronized void remove(MessageReference node) {
        if (this.memoryList.remove(node) != null) {
            node.decrementReferenceCount();
        }
        if (!this.isDiskListEmpty()) {
            try {
                this.getDiskList().remove(node.getMessageId().getPlistLocator());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public synchronized int size() {
        return this.memoryList.size() + (this.isDiskListEmpty() ? 0 : (int)this.getDiskList().size());
    }

    @Override
    public synchronized long messageSize() {
        return this.memoryList.messageSize() + (this.isDiskListEmpty() ? 0L : this.getDiskList().messageSize());
    }

    @Override
    public synchronized void clear() {
        this.memoryList.clear();
        if (!this.isDiskListEmpty()) {
            try {
                this.getDiskList().destroy();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.last = null;
    }

    @Override
    public synchronized boolean isFull() {
        return super.isFull() || !this.isDiskListEmpty() && this.systemUsage != null && this.systemUsage.getTempUsage().isFull();
    }

    @Override
    public boolean hasMessagesBufferedToDeliver() {
        return !this.isEmpty();
    }

    @Override
    public void setSystemUsage(SystemUsage usageManager) {
        super.setSystemUsage(usageManager);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onUsageChanged(Usage usage, int oldPercentUsage, int newPercentUsage) {
        if (newPercentUsage >= this.getMemoryUsageHighWaterMark()) {
            List<MessageReference> expiredMessages = null;
            FilePendingMessageCursor filePendingMessageCursor = this;
            synchronized (filePendingMessageCursor) {
                if (!this.flushRequired && this.size() != 0) {
                    this.flushRequired = true;
                    if (!this.iterating) {
                        expiredMessages = this.expireOldMessages();
                        if (!this.hasSpace()) {
                            this.flushToDisk();
                            this.flushRequired = false;
                        }
                    }
                }
            }
            if (expiredMessages != null) {
                for (MessageReference node : expiredMessages) {
                    this.discardExpiredMessage(node);
                }
            }
        }
    }

    @Override
    public boolean isTransient() {
        return true;
    }

    private synchronized List<MessageReference> expireOldMessages() {
        ArrayList<MessageReference> expired = new ArrayList<MessageReference>();
        if (!this.memoryList.isEmpty()) {
            Iterator<MessageReference> iterator2 = this.memoryList.iterator();
            while (iterator2.hasNext()) {
                MessageReference node = iterator2.next();
                if (!node.isExpired()) continue;
                node.decrementReferenceCount();
                expired.add(node);
                iterator2.remove();
            }
        }
        return expired;
    }

    protected synchronized void flushToDisk() {
        if (!this.memoryList.isEmpty() && this.store != null) {
            long start2 = 0L;
            if (LOG.isTraceEnabled()) {
                start2 = System.currentTimeMillis();
                LOG.trace("{}, flushToDisk() mem list size: {} {}", this.name, this.memoryList.size(), this.systemUsage != null ? this.systemUsage.getMemoryUsage() : "");
            }
            for (MessageReference node : this.memoryList) {
                node.decrementReferenceCount();
                try {
                    ByteSequence bs = this.getByteSequence(node.getMessage());
                    this.getDiskList().addLast(node.getMessageId().toString(), bs);
                }
                catch (IOException e) {
                    LOG.error("Failed to write to disk list", e);
                    throw new RuntimeException(e);
                }
            }
            this.memoryList.clear();
            this.setCacheEnabled(false);
            LOG.trace("{}, flushToDisk() done - {} ms {}", this.name, System.currentTimeMillis() - start2, this.systemUsage != null ? this.systemUsage.getMemoryUsage() : "");
        }
    }

    protected boolean isDiskListEmpty() {
        return this.diskList == null || this.diskList.isEmpty();
    }

    public PList getDiskList() {
        if (this.diskList == null) {
            try {
                this.diskList = this.store.getPList(this.name);
            }
            catch (Exception e) {
                LOG.error("Caught an IO Exception getting the DiskList {}", (Object)this.name, (Object)e);
                throw new RuntimeException(e);
            }
        }
        return this.diskList;
    }

    private void discardExpiredMessage(MessageReference reference) {
        LOG.debug("Discarding expired message {}", (Object)reference);
        if (reference.isExpired() && this.broker.isExpired(reference)) {
            ConnectionContext context = new ConnectionContext();
            context.setBroker(this.broker);
            ((Destination)reference.getRegionDestination()).messageExpired(context, null, new IndirectMessageReference(reference.getMessage()));
        }
    }

    protected ByteSequence getByteSequence(Message message) throws IOException {
        ByteSequence packet = this.wireFormat.marshal(message);
        return new ByteSequence(packet.data, packet.offset, packet.length);
    }

    protected Message getMessage(ByteSequence bs) throws IOException {
        ByteSequence packet = new ByteSequence(bs.getData(), bs.getOffset(), bs.getLength());
        return (Message)this.wireFormat.unmarshal(packet);
    }

    final class DiskIterator
    implements Iterator<MessageReference> {
        private final PList.PListIterator iterator;

        DiskIterator() {
            try {
                this.iterator = FilePendingMessageCursor.this.getDiskList().iterator();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public MessageReference next() {
            try {
                PListEntry entry = (PListEntry)this.iterator.next();
                Message message = FilePendingMessageCursor.this.getMessage(entry.getByteSequence());
                message.getMessageId().setPlistLocator(entry.getLocator());
                return message;
            }
            catch (IOException e) {
                LOG.error("I/O error", e);
                throw new RuntimeException(e);
            }
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }

        public void release() {
            this.iterator.release();
        }
    }
}

