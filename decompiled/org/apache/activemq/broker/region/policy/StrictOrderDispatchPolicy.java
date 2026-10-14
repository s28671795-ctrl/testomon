/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.DispatchPolicy;
import org.apache.activemq.filter.MessageEvaluationContext;

public class StrictOrderDispatchPolicy
implements DispatchPolicy {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List consumers) throws Exception {
        List list = consumers;
        synchronized (list) {
            int count = 0;
            for (Subscription sub : consumers) {
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
}

