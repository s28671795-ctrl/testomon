/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.cxf.common.util.ModCountCopyOnWriteArrayList;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptorChain;

public final class PhaseChainCache {
    AtomicReference<ChainHolder> lastData = new AtomicReference();

    public PhaseInterceptorChain get(SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> p1) {
        return PhaseChainCache.getChain(this.lastData, phaseList, p1);
    }

    public PhaseInterceptorChain get(SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> p1, List<Interceptor<? extends Message>> p2) {
        return PhaseChainCache.getChain(this.lastData, phaseList, p1, p2);
    }

    public PhaseInterceptorChain get(SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> p1, List<Interceptor<? extends Message>> p2, List<Interceptor<? extends Message>> p3) {
        return PhaseChainCache.getChain(this.lastData, phaseList, p1, p2, p3);
    }

    public PhaseInterceptorChain get(SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> p1, List<Interceptor<? extends Message>> p2, List<Interceptor<? extends Message>> p3, List<Interceptor<? extends Message>> p4) {
        return PhaseChainCache.getChain(this.lastData, phaseList, p1, p2, p3, p4);
    }

    public PhaseInterceptorChain get(SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> p1, List<Interceptor<? extends Message>> p2, List<Interceptor<? extends Message>> p3, List<Interceptor<? extends Message>> p4, List<Interceptor<? extends Message>> p5) {
        return PhaseChainCache.getChain(this.lastData, phaseList, p1, p2, p3, p4, p5);
    }

    @SafeVarargs
    static PhaseInterceptorChain getChain(AtomicReference<ChainHolder> lastData, SortedSet<Phase> phaseList, List<Interceptor<? extends Message>> ... providers) {
        ChainHolder last = lastData.get();
        if (last == null || !last.matches(providers)) {
            PhaseInterceptorChain chain = new PhaseInterceptorChain(phaseList);
            ArrayList<ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>> copy = new ArrayList<ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>>(providers.length);
            for (List<Interceptor<? extends Message>> p : providers) {
                copy.add(new ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>((Collection<Interceptor<? extends Message>>)p));
                chain.add(p);
            }
            last = new ChainHolder(chain, copy);
            lastData.set(last);
        }
        return last.chain.cloneChain();
    }

    private static class ChainHolder {
        List<ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>> lists;
        PhaseInterceptorChain chain;

        ChainHolder(PhaseInterceptorChain c, List<ModCountCopyOnWriteArrayList<Interceptor<? extends Message>>> l) {
            this.lists = l;
            this.chain = c;
        }

        @SafeVarargs
        final boolean matches(List<Interceptor<? extends Message>> ... providers) {
            if (this.lists.size() == providers.length) {
                for (int x = 0; x < providers.length; ++x) {
                    if (this.lists.get(x).size() != providers[x].size()) {
                        return false;
                    }
                    if (providers[x].getClass() == ModCountCopyOnWriteArrayList.class) {
                        if (((ModCountCopyOnWriteArrayList)providers[x]).getModCount() == this.lists.get(x).getModCount()) continue;
                        return false;
                    }
                    ListIterator i1 = this.lists.get(x).listIterator();
                    ListIterator<Interceptor<? extends Message>> i2 = providers[x].listIterator();
                    while (i1.hasNext()) {
                        if (i1.next() == i2.next()) continue;
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}

