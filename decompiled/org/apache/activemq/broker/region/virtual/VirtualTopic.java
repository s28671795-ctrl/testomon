/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.virtual.MappedQueueFilter;
import org.apache.activemq.broker.region.virtual.SelectorAwareVirtualTopicInterceptor;
import org.apache.activemq.broker.region.virtual.VirtualDestination;
import org.apache.activemq.broker.region.virtual.VirtualTopicInterceptor;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.filter.DestinationFilter;

public class VirtualTopic
implements VirtualDestination {
    private String prefix = "Consumer.*.";
    private String postfix = "";
    private String name = ">";
    private boolean selectorAware = false;
    private boolean local = false;
    private boolean concurrentSend = false;
    private boolean transactedSend = false;

    @Override
    public ActiveMQDestination getVirtualDestination() {
        return new ActiveMQTopic(this.getName());
    }

    @Override
    public Destination intercept(Destination destination) {
        return this.selectorAware ? new SelectorAwareVirtualTopicInterceptor(destination, this) : new VirtualTopicInterceptor(destination, this);
    }

    @Override
    public ActiveMQDestination getMappedDestinations() {
        return new ActiveMQQueue(this.prefix + this.name + this.postfix);
    }

    @Override
    public Destination interceptMappedDestination(Destination destination) {
        String physicalName = destination.getActiveMQDestination().getPhysicalName();
        Pattern pattern = Pattern.compile(this.getRegex(this.prefix) + "(.*)" + this.getRegex(this.postfix));
        Matcher matcher = pattern.matcher(physicalName);
        if (matcher.matches()) {
            String virtualName = matcher.group(1);
            return new MappedQueueFilter(new ActiveMQTopic(virtualName), destination);
        }
        return destination;
    }

    private String getRegex(String part) {
        StringBuilder builder = new StringBuilder();
        block4: for (char c : part.toCharArray()) {
            switch (c) {
                case '.': {
                    builder.append("\\.");
                    continue block4;
                }
                case '*': {
                    builder.append("[^\\.]*");
                    continue block4;
                }
                default: {
                    builder.append(c);
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void create(Broker broker, ConnectionContext context, ActiveMQDestination destination) throws Exception {
        DestinationFilter filter2;
        if (destination.isQueue() && destination.isPattern() && (filter2 = DestinationFilter.parseFilter(new ActiveMQQueue(this.prefix + ">"))).matches(destination)) {
            broker.addDestination(context, destination, false);
        }
    }

    @Override
    public void remove(Destination destination) {
    }

    public String getPostfix() {
        return this.postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelectorAware(boolean selectorAware) {
        this.selectorAware = selectorAware;
    }

    public boolean isSelectorAware() {
        return this.selectorAware;
    }

    public boolean isLocal() {
        return this.local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String toString() {
        return "VirtualTopic:" + this.prefix + ',' + this.name + ',' + this.postfix + ',' + this.selectorAware + ',' + this.local;
    }

    public boolean isConcurrentSend() {
        return this.concurrentSend;
    }

    public void setConcurrentSend(boolean concurrentSend) {
        this.concurrentSend = concurrentSend;
    }

    public boolean isTransactedSend() {
        return this.transactedSend;
    }

    public void setTransactedSend(boolean transactedSend) {
        this.transactedSend = transactedSend;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.concurrentSend ? 1231 : 1237);
        result = 31 * result + (this.local ? 1231 : 1237);
        result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
        result = 31 * result + (this.postfix == null ? 0 : this.postfix.hashCode());
        result = 31 * result + (this.prefix == null ? 0 : this.prefix.hashCode());
        result = 31 * result + (this.selectorAware ? 1231 : 1237);
        result = 31 * result + (this.transactedSend ? 1231 : 1237);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        VirtualTopic other = (VirtualTopic)obj;
        if (this.concurrentSend != other.concurrentSend) {
            return false;
        }
        if (this.local != other.local) {
            return false;
        }
        if (this.name == null ? other.name != null : !this.name.equals(other.name)) {
            return false;
        }
        if (this.postfix == null ? other.postfix != null : !this.postfix.equals(other.postfix)) {
            return false;
        }
        if (this.prefix == null ? other.prefix != null : !this.prefix.equals(other.prefix)) {
            return false;
        }
        if (this.selectorAware != other.selectorAware) {
            return false;
        }
        return this.transactedSend == other.transactedSend;
    }
}

