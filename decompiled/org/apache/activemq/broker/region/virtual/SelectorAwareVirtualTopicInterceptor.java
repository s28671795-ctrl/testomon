/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.virtual;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.Topic;
import org.apache.activemq.broker.region.virtual.VirtualTopic;
import org.apache.activemq.broker.region.virtual.VirtualTopicInterceptor;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.BooleanExpression;
import org.apache.activemq.filter.MessageEvaluationContext;
import org.apache.activemq.filter.NonCachedMessageEvaluationContext;
import org.apache.activemq.plugin.SubQueueSelectorCacheBroker;
import org.apache.activemq.selector.SelectorParser;
import org.apache.activemq.util.LRUCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectorAwareVirtualTopicInterceptor
extends VirtualTopicInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(SelectorAwareVirtualTopicInterceptor.class);
    LRUCache<String, BooleanExpression> expressionCache = new LRUCache();
    private final SubQueueSelectorCacheBroker selectorCachePlugin;

    public SelectorAwareVirtualTopicInterceptor(Destination next, VirtualTopic virtualTopic) {
        super(next, virtualTopic);
        this.selectorCachePlugin = (SubQueueSelectorCacheBroker)((Topic)next).createConnectionContext().getBroker().getAdaptor(SubQueueSelectorCacheBroker.class);
    }

    @Override
    protected boolean shouldDispatch(Broker broker, Message message, Destination dest) throws IOException {
        if (super.shouldDispatch(broker, message, dest)) {
            boolean matches = false;
            NonCachedMessageEvaluationContext msgContext = new NonCachedMessageEvaluationContext();
            msgContext.setDestination(dest.getActiveMQDestination());
            ((MessageEvaluationContext)msgContext).setMessageReference(message);
            List<Subscription> subs = dest.getConsumers();
            for (Subscription sub : subs) {
                if (!sub.matches(message, msgContext)) continue;
                matches = true;
                break;
            }
            if (!matches) {
                matches = this.tryMatchingCachedSubs(broker, dest, msgContext);
            }
            return matches;
        }
        return false;
    }

    private boolean tryMatchingCachedSubs(Broker broker, Destination dest, MessageEvaluationContext msgContext) {
        Set<String> selectors;
        boolean matches = false;
        LOG.debug("No active consumer match found. Will try cache if configured...");
        if (this.selectorCachePlugin != null && (selectors = this.selectorCachePlugin.getSelector(dest.getActiveMQDestination().getQualifiedName())) != null) {
            for (String selector : selectors) {
                try {
                    BooleanExpression expression = this.getExpression(selector);
                    matches = expression.matches(msgContext);
                    if (!matches) continue;
                    return true;
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return matches;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private BooleanExpression getExpression(String selector) throws Exception {
        BooleanExpression result;
        LRUCache<String, BooleanExpression> lRUCache = this.expressionCache;
        synchronized (lRUCache) {
            result = (BooleanExpression)this.expressionCache.get(selector);
            if (result == null) {
                result = this.compileSelector(selector);
                this.expressionCache.put(selector, result);
            }
        }
        return result;
    }

    private BooleanExpression compileSelector(String selectorExpression) throws Exception {
        return SelectorParser.parse(selectorExpression);
    }
}

