/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.filter.MessageEvaluationContext;

public interface DispatchPolicy {
    public boolean dispatch(MessageReference var1, MessageEvaluationContext var2, List<Subscription> var3) throws Exception;
}

