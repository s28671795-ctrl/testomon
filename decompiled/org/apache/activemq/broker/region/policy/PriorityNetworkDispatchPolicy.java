/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.SimpleDispatchPolicy;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ConsumerInfo;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriorityNetworkDispatchPolicy
extends SimpleDispatchPolicy {
    private static final Logger LOG = LoggerFactory.getLogger(PriorityNetworkDispatchPolicy.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
        ArrayList<Subscription> duplicateFreeSubs = new ArrayList<Subscription>();
        List<Subscription> list = consumers;
        synchronized (list) {
            for (Subscription sub : consumers) {
                ConsumerInfo info = sub.getConsumerInfo();
                if (info.isNetworkSubscription()) {
                    boolean highestPrioritySub = true;
                    Iterator it = duplicateFreeSubs.iterator();
                    while (it.hasNext()) {
                        Subscription candidate = (Subscription)it.next();
                        if (!this.matches(candidate, info)) continue;
                        if (this.hasLowerPriority(candidate, info)) {
                            it.remove();
                            continue;
                        }
                        highestPrioritySub = false;
                        LOG.debug("ignoring lower priority: {} [{}, {}] in favour of: {} [{}, {}]", candidate, candidate.getConsumerInfo().getNetworkConsumerIds(), candidate.getConsumerInfo().getNetworkConsumerIds(), sub, sub.getConsumerInfo().getNetworkConsumerIds(), sub.getConsumerInfo().getNetworkConsumerIds());
                    }
                    if (!highestPrioritySub) continue;
                    duplicateFreeSubs.add(sub);
                    continue;
                }
                duplicateFreeSubs.add(sub);
            }
        }
        return super.dispatch(node, msgContext, duplicateFreeSubs);
    }

    private boolean hasLowerPriority(Subscription candidate, ConsumerInfo info) {
        return candidate.getConsumerInfo().getPriority() < info.getPriority();
    }

    private boolean matches(Subscription candidate, ConsumerInfo info) {
        boolean matched = false;
        block0: for (ConsumerId candidateId : candidate.getConsumerInfo().getNetworkConsumerIds()) {
            for (ConsumerId subId : info.getNetworkConsumerIds()) {
                if (!candidateId.equals(subId)) continue;
                matched = true;
                continue block0;
            }
        }
        return matched;
    }
}

