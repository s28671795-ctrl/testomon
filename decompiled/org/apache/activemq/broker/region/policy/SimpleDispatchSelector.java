/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DispatchSelector;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;

public class SimpleDispatchSelector
implements DispatchSelector {
    private final ActiveMQDestination destination;

    public SimpleDispatchSelector(ActiveMQDestination destination) {
        this.destination = destination;
    }

    @Override
    public boolean canDispatch(Subscription subscription, MessageReference node) throws Exception {
        NonCachedMessageEvaluationContext msgContext = new NonCachedMessageEvaluationContext();
        msgContext.setDestination(this.destination);
        ((MessageEvaluationContext)msgContext).setMessageReference(node);
        return subscription.matches(node, msgContext);
    }
}

