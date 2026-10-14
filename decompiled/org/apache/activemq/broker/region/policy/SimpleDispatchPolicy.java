/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.filter.MessageEvaluationContext;

public class SimpleDispatchPolicy
implements DispatchPolicy {
    @Override
    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
        int count = 0;
        for (Subscription sub : consumers) {
            if (sub.getConsumerInfo().isBrowser()) continue;
            if (!sub.matches(node, msgContext)) {
                sub.unmatched(node);
                continue;
            }
            sub.add(node);
            ++count;
        }
        return count > 0;
    }
}

