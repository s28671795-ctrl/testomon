/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.selector.SelectorParser;

public class FilteredDestination {
    private ActiveMQDestination destination;
    private String selector;
    private BooleanExpression filter;

    public boolean matches(MessageEvaluationContext context) throws JMSException {
        BooleanExpression booleanExpression = this.getFilter();
        if (booleanExpression == null) {
            return false;
        }
        return booleanExpression.matches(context);
    }

    public ActiveMQDestination getDestination() {
        return this.destination;
    }

    public void setDestination(ActiveMQDestination destination) {
        this.destination = destination;
    }

    public String getSelector() {
        return this.selector;
    }

    public void setSelector(String selector) throws InvalidSelectorException {
        this.selector = selector;
        this.setFilter(SelectorParser.parse(selector));
    }

    public BooleanExpression getFilter() {
        return this.filter;
    }

    public void setFilter(BooleanExpression filter2) {
        this.filter = filter2;
    }

    public void setQueue(String queue) {
        this.setDestination(ActiveMQDestination.createDestination(queue, (byte)1));
    }

    public void setTopic(String topic) {
        this.setDestination(ActiveMQDestination.createDestination(topic, (byte)2));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.destination == null ? 0 : this.destination.hashCode());
        result = 31 * result + (this.selector == null ? 0 : this.selector.hashCode());
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
        FilteredDestination other = (FilteredDestination)obj;
        if (this.destination == null ? other.destination != null : !this.destination.equals(other.destination)) {
            return false;
        }
        return !(this.selector == null ? other.selector != null : !this.selector.equals(other.selector));
    }
}

