/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import org.apache.activemq.broker.jmx.DestinationViewMBean;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface QueueViewMBean
extends DestinationViewMBean {
    @MBeanInfo(value="View a message from the destination by JMS message ID.")
    public CompositeData getMessage(@MBeanInfo(value="messageId") String var1) throws OpenDataException;

    @MBeanInfo(value="Remove a message from the destination by JMS message ID.  If the message has been dispatched, it cannot be deleted and false is returned.")
    public boolean removeMessage(@MBeanInfo(value="messageId") String var1) throws Exception;

    @MBeanInfo(value="Removes messages from the destination based on an SQL-92 selection on the message headers or XPATH on the body.")
    public int removeMatchingMessages(@MBeanInfo(value="selector") String var1) throws Exception;

    @MBeanInfo(value="Removes up to a specified number of messages from the destination based on an SQL-92 selection on the message headers or XPATH on the body.")
    public int removeMatchingMessages(@MBeanInfo(value="selector") String var1, @MBeanInfo(value="maximumMessages") int var2) throws Exception;

    @MBeanInfo(value="Removes all of the messages in the queue.")
    public void purge() throws Exception;

    @MBeanInfo(value="Copies a message with the given JMS message ID into the specified destination.")
    public boolean copyMessageTo(@MBeanInfo(value="messageId") String var1, @MBeanInfo(value="destinationName") String var2) throws Exception;

    @MBeanInfo(value="Copies messages based on an SQL-92 selecton on the message headers or XPATH on the body into the specified destination.")
    public int copyMatchingMessagesTo(@MBeanInfo(value="selector") String var1, @MBeanInfo(value="destinationName") String var2) throws Exception;

    @MBeanInfo(value="Copies up to a specified number of messages based on an SQL-92 selecton on the message headers or XPATH on the body into the specified destination.")
    public int copyMatchingMessagesTo(@MBeanInfo(value="selector") String var1, @MBeanInfo(value="destinationName") String var2, @MBeanInfo(value="maximumMessages") int var3) throws Exception;

    @MBeanInfo(value="Moves a message with the given JMS message ID into the specified destination.")
    public boolean moveMessageTo(@MBeanInfo(value="messageId") String var1, @MBeanInfo(value="destinationName") String var2) throws Exception;

    @MBeanInfo(value="Moves a message with the given JMS message back to its original destination")
    public boolean retryMessage(@MBeanInfo(value="messageId") String var1) throws Exception;

    @MBeanInfo(value="Moves messages based on an SQL-92 selecton on the message headers or XPATH on the body into the specified destination.")
    public int moveMatchingMessagesTo(@MBeanInfo(value="selector") String var1, @MBeanInfo(value="destinationName") String var2) throws Exception;

    @MBeanInfo(value="Moves up to a specified number of messages based on an SQL-92 selecton on the message headers or XPATH on the body into the specified destination.")
    public int moveMatchingMessagesTo(@MBeanInfo(value="selector") String var1, @MBeanInfo(value="destinationName") String var2, @MBeanInfo(value="maximumMessages") int var3) throws Exception;

    @MBeanInfo(value="Retries messages sent to the DLQ")
    public int retryMessages() throws Exception;

    @MBeanInfo(value="Message cursor has memory space available")
    public boolean doesCursorHaveSpace();

    @MBeanInfo(value="Message cusor has reached its memory limit for paged in messages")
    public boolean isCursorFull();

    @MBeanInfo(value="Message cursor has buffered messages to deliver")
    public boolean doesCursorHaveMessagesBuffered();

    @MBeanInfo(value="Message cursor memory usage, in bytes.")
    public long getCursorMemoryUsage();

    @MBeanInfo(value="Percentage of memory limit used")
    public int getCursorPercentUsage();

    @MBeanInfo(value="Number of messages available to be paged in by the cursor.")
    public int cursorSize();

    @MBeanInfo(value="Caching is enabled")
    public boolean isCacheEnabled();

    @MBeanInfo(value="Map of groupNames and ConsumerIds")
    public Map<String, String> getMessageGroups();

    @MBeanInfo(value="group  implementation (simple,bucket,cached)")
    public String getMessageGroupType();

    @MBeanInfo(value="remove a message group by its groupName")
    public void removeMessageGroup(@MBeanInfo(value="groupName") String var1);

    @MBeanInfo(value="emove all the message groups - will rebalance all message groups across consumers")
    public void removeAllMessageGroups();

    @MBeanInfo(value="pause dispatch to consumers")
    public void pause();

    @MBeanInfo(value="resume dispatch to consumers if paused")
    public void resume();

    @MBeanInfo(value="Dispatch to consumers is paused")
    public boolean isPaused();
}

