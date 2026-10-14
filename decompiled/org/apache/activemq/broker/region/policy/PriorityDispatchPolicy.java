/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.SimpleDispatchPolicy;
import org.apache.activemq.filter.MessageEvaluationContext;

public class PriorityDispatchPolicy
extends SimpleDispatchPolicy {
    private final Comparator<? super Subscription> orderedCompare = new Comparator<Subscription>(){

        @Override
        public int compare(Subscription o1, Subscription o2) {
            return o2.getConsumerInfo().getPriority() - o1.getConsumerInfo().getPriority();
        }
    };

    @Override
    public boolean dispatch(MessageReference node, MessageEvaluationContext msgContext, List<Subscription> consumers) throws Exception {
        ArrayList<Subscription> ordered = new ArrayList<Subscription>(consumers);
        Collections.sort(ordered, this.orderedCompare);
        StringBuffer stringBuffer = new StringBuffer();
        for (Subscription sub : ordered) {
            stringBuffer.append(sub.getConsumerInfo().getPriority());
            stringBuffer.append(',');
        }
        return super.dispatch(node, msgContext, ordered);
    }
}

