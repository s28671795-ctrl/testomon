/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import javax.management.ObjectName;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import org.apache.activemq.broker.jmx.MBeanInfo;

public interface AbortSlowConsumerStrategyViewMBean {
    @MBeanInfo(value="returns the current max slow count, -1 disables")
    public long getMaxSlowCount();

    @MBeanInfo(value="sets the count after which a slow consumer will be aborted, -1 disables")
    public void setMaxSlowCount(long var1);

    @MBeanInfo(value="returns the current max slow (milliseconds) duration")
    public long getMaxSlowDuration();

    @MBeanInfo(value="sets the duration (milliseconds) after which a continually slow consumer will be aborted")
    public void setMaxSlowDuration(long var1);

    @MBeanInfo(value="returns the check period at which a sweep of consumers is done to determine continued slowness")
    public long getCheckPeriod();

    @MBeanInfo(value="returns the current list of slow consumers, Not HTML friendly")
    public TabularData getSlowConsumers() throws OpenDataException;

    @MBeanInfo(value="aborts the slow consumer gracefully by sending a shutdown control message to just that consumer")
    public void abortConsumer(ObjectName var1);

    @MBeanInfo(value="aborts the slow consumer forcefully by shutting down it's connection, note: all other users of the connection will be affected")
    public void abortConnection(ObjectName var1);

    @MBeanInfo(value="aborts the slow consumer gracefully by sending a shutdown control message to just that consumer")
    public void abortConsumer(String var1);

    @MBeanInfo(value="aborts the slow consumer forcefully by shutting down it's connection, note: all other users of the connection will be affected")
    public void abortConnection(String var1);
}

