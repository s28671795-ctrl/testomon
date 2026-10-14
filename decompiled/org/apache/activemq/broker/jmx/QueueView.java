/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Map;
import javax.jms.JMSException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.jmx.DestinationView;
import org.apache.activemq.broker.jmx.MBeanInfo;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.BrokerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueView
extends DestinationView
implements QueueViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(QueueView.class);

    public QueueView(ManagedRegionBroker broker, Queue destination) {
        super(broker, destination);
    }

    @Override
    public CompositeData getMessage(String messageId) throws OpenDataException {
        CompositeData result = null;
        QueueMessageReference ref = ((Queue)this.destination).getMessage(messageId);
        if (ref != null) {
            Message rc = ref.getMessage();
            if (rc == null) {
                return null;
            }
            result = OpenTypeSupport.convert(rc);
        }
        return result;
    }

    @Override
    public synchronized void purge() throws Exception {
        long originalMessageCount = this.destination.getDestinationStatistics().getMessages().getCount();
        ((Queue)this.destination).purge();
        LOG.info("{} purge of {} messages", (Object)this.destination.getActiveMQDestination().getQualifiedName(), (Object)originalMessageCount);
    }

    @Override
    public boolean removeMessage(String messageId) throws Exception {
        return ((Queue)this.destination).removeMessage(messageId);
    }

    @Override
    public synchronized int removeMatchingMessages(String selector) throws Exception {
        return ((Queue)this.destination).removeMatchingMessages(selector);
    }

    @Override
    public synchronized int removeMatchingMessages(String selector, int maximumMessages) throws Exception {
        return ((Queue)this.destination).removeMatchingMessages(selector, maximumMessages);
    }

    @Override
    public boolean copyMessageTo(String messageId, String destinationName) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).copyMessageTo(context, messageId, toDestination);
    }

    @Override
    public int copyMatchingMessagesTo(String selector, String destinationName) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).copyMatchingMessagesTo(context, selector, toDestination);
    }

    @Override
    public int copyMatchingMessagesTo(String selector, String destinationName, int maximumMessages) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).copyMatchingMessagesTo(context, selector, toDestination, maximumMessages);
    }

    @Override
    public boolean moveMessageTo(String messageId, String destinationName) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).moveMessageTo(context, messageId, toDestination);
    }

    @Override
    public synchronized int moveMatchingMessagesTo(String selector, String destinationName) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).moveMatchingMessagesTo(context, selector, toDestination);
    }

    @Override
    public synchronized int moveMatchingMessagesTo(String selector, String destinationName, int maximumMessages) throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        ActiveMQDestination toDestination = ActiveMQDestination.createDestination(destinationName, (byte)1);
        return ((Queue)this.destination).moveMatchingMessagesTo(context, selector, toDestination, maximumMessages);
    }

    @Override
    public synchronized int retryMessages() throws Exception {
        ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
        return ((Queue)this.destination).retryMessages(context, Integer.MAX_VALUE);
    }

    @Override
    public boolean retryMessage(String messageId) throws Exception {
        Queue queue = (Queue)this.destination;
        QueueMessageReference ref = queue.getMessage(messageId);
        Message rc = ref.getMessage();
        if (rc != null) {
            ActiveMQDestination originalDestination = rc.getOriginalDestination();
            if (originalDestination != null) {
                ConnectionContext context = BrokerSupport.getConnectionContext(this.broker.getContextBroker());
                return queue.moveMessageTo(context, ref, originalDestination);
            }
            throw new JMSException("No original destination for message: " + messageId);
        }
        throw new JMSException("Could not find message: " + messageId);
    }

    @Override
    public int cursorSize() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null) {
            return queue.getMessages().size();
        }
        return 0;
    }

    @Override
    public boolean doesCursorHaveMessagesBuffered() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null) {
            return queue.getMessages().hasMessagesBufferedToDeliver();
        }
        return false;
    }

    @Override
    public boolean doesCursorHaveSpace() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null) {
            return queue.getMessages().hasSpace();
        }
        return false;
    }

    @Override
    public long getCursorMemoryUsage() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null && queue.getMessages().getSystemUsage() != null) {
            return queue.getMessages().getSystemUsage().getMemoryUsage().getUsage();
        }
        return 0L;
    }

    @Override
    public int getCursorPercentUsage() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null && queue.getMessages().getSystemUsage() != null) {
            return queue.getMessages().getSystemUsage().getMemoryUsage().getPercentUsage();
        }
        return 0;
    }

    @Override
    public boolean isCursorFull() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null) {
            return queue.getMessages().isFull();
        }
        return false;
    }

    @Override
    public boolean isCacheEnabled() {
        Queue queue = (Queue)this.destination;
        if (queue.getMessages() != null) {
            return queue.getMessages().isCacheEnabled();
        }
        return false;
    }

    @Override
    public Map<String, String> getMessageGroups() {
        Queue queue = (Queue)this.destination;
        return queue.getMessageGroupOwners().getGroups();
    }

    @Override
    public String getMessageGroupType() {
        Queue queue = (Queue)this.destination;
        return queue.getMessageGroupOwners().getType();
    }

    @Override
    public void removeMessageGroup(@MBeanInfo(value="groupName") String groupName) {
        Queue queue = (Queue)this.destination;
        queue.getMessageGroupOwners().removeGroup(groupName);
    }

    @Override
    public void removeAllMessageGroups() {
        Queue queue = (Queue)this.destination;
        queue.getMessageGroupOwners().removeAll();
    }

    @Override
    public void pause() {
        Queue queue = (Queue)this.destination;
        queue.pauseDispatch();
    }

    @Override
    public void resume() {
        Queue queue = (Queue)this.destination;
        queue.resumeDispatch();
    }

    @Override
    public boolean isPaused() {
        Queue queue = (Queue)this.destination;
        return queue.isDispatchPaused();
    }
}

