/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.provider.BaseObligationHandler;
import org.opensaml.xacml.ctx.provider.ObligationProcessingContext;
import org.opensaml.xacml.ctx.provider.ObligationProcessingException;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;
import org.opensaml.xacml.policy.ObligationsType;

public class ObligationService {
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(true);
    private Set<BaseObligationHandler> obligationHandlers = new TreeSet<BaseObligationHandler>(new ObligationHandlerComparator());

    public Set<BaseObligationHandler> getObligationHandlers() {
        return Collections.unmodifiableSet(this.obligationHandlers);
    }

    public void addObligationhandler(BaseObligationHandler handler) {
        if (handler == null) {
            return;
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            this.obligationHandlers.add(handler);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void addObligationhandler(Collection<BaseObligationHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            return;
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            this.obligationHandlers.addAll(handlers);
        }
        finally {
            writeLock.unlock();
        }
    }

    public void removeObligationHandler(BaseObligationHandler handler) {
        if (handler == null) {
            return;
        }
        ReentrantReadWriteLock.WriteLock writeLock = this.rwLock.writeLock();
        writeLock.lock();
        try {
            this.obligationHandlers.remove(handler);
        }
        finally {
            writeLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void processObligations(ObligationProcessingContext context) throws ObligationProcessingException {
        ReentrantReadWriteLock.ReadLock readLock = this.rwLock.readLock();
        readLock.lock();
        try {
            Iterator<BaseObligationHandler> handlerItr = this.obligationHandlers.iterator();
            Map<String, ObligationType> effectiveObligations = this.preprocessObligations(context);
            while (handlerItr.hasNext()) {
                BaseObligationHandler handler = handlerItr.next();
                if (!effectiveObligations.containsKey(handler.getObligationId())) continue;
                handler.evaluateObligation(context, effectiveObligations.get(handler.getObligationId()));
            }
        }
        finally {
            readLock.unlock();
        }
    }

    protected Map<String, ObligationType> preprocessObligations(ObligationProcessingContext context) {
        HashMap<String, ObligationType> effectiveObligations = new HashMap<String, ObligationType>();
        ObligationsType obligations = context.getAuthorizationDecisionResult().getObligations();
        if (obligations == null || obligations.getObligations() == null) {
            return effectiveObligations;
        }
        EffectType activeEffect = context.getAuthorizationDecisionResult().getDecision().getDecision() == DecisionType.DECISION.Permit ? EffectType.Permit : EffectType.Deny;
        for (ObligationType obligation : obligations.getObligations()) {
            if (obligation == null || obligation.getFulfillOn() != activeEffect) continue;
            effectiveObligations.put(obligation.getObligationId(), obligation);
        }
        return effectiveObligations;
    }

    private class ObligationHandlerComparator
    implements Comparator<BaseObligationHandler> {
        private ObligationHandlerComparator() {
        }

        @Override
        public int compare(BaseObligationHandler o1, BaseObligationHandler o2) {
            if (o1.getHandlerPrecedence() == o2.getHandlerPrecedence()) {
                return o1.getObligationId().compareTo(o2.getObligationId());
            }
            if (o1.getHandlerPrecedence() < o2.getHandlerPrecedence()) {
                return -1;
            }
            return 1;
        }
    }
}

