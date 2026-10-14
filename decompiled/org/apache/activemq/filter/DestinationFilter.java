/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.io.IOException;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.CompositeDestinationFilter;
import org.apache.activemq.filter.DestinationPath;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.PrefixDestinationFilter;
import org.apache.activemq.filter.SimpleDestinationFilter;
import org.apache.activemq.filter.WildcardDestinationFilter;
import org.apache.activemq.util.JMSExceptionSupport;

public abstract class DestinationFilter
implements BooleanExpression {
    public static final String ANY_DESCENDENT = ">";
    public static final String ANY_CHILD = "*";

    @Override
    public Object evaluate(MessageEvaluationContext message) throws JMSException {
        return this.matches(message) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean matches(MessageEvaluationContext message) throws JMSException {
        try {
            if (message.isDropped()) {
                return false;
            }
            return this.matches(message.getMessage().getDestination());
        }
        catch (IOException e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    public abstract boolean matches(ActiveMQDestination var1);

    public static DestinationFilter parseFilter(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            return new CompositeDestinationFilter(destination);
        }
        String[] paths = DestinationPath.getDestinationPaths(destination);
        int idx = paths.length - 1;
        if (idx >= 0) {
            String lastPath = paths[idx];
            if (lastPath.equals(ANY_DESCENDENT)) {
                return new PrefixDestinationFilter(paths, destination.getDestinationType());
            }
            while (idx >= 0) {
                if (!(lastPath = paths[idx--]).equals(ANY_CHILD)) continue;
                return new WildcardDestinationFilter(paths, destination.getDestinationType());
            }
        }
        return new SimpleDestinationFilter(destination);
    }

    public abstract boolean isWildcard();
}

