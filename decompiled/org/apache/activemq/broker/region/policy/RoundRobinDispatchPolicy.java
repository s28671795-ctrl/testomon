/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.filter.MessageEvaluationContext;

public class RoundRobinDispatchPolicy
implements DispatchPolicy {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
        int count = 0;
        Subscription firstMatchingConsumer = null;
        List<Subscription> list = consumers;
        synchronized (list) {
            for (Subscription sub : consumers) {
                if (!sub.matches(node, msgContext)) {
                    sub.unmatched(node);
                    continue;
                }
                if (firstMatchingConsumer == null) {
                    firstMatchingConsumer = sub;
                }
                sub.add(node);
                ++count;
            }
            if (firstMatchingConsumer != null) {
                try {
                    consumers.remove(firstMatchingConsumer);
                    consumers.add(firstMatchingConsumer);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        return count > 0;
    }
}

