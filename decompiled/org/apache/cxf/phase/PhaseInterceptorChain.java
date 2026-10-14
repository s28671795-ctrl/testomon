/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.continuations.SuspendedInvocationException;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.InterceptorChain;
import org.apache.cxf.interceptor.ServiceInvokerInterceptor;
import org.apache.cxf.logging.FaultListener;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.FaultMode;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.transport.MessageObserver;

public class PhaseInterceptorChain
implements InterceptorChain {
    public static final String PREVIOUS_MESSAGE = PhaseInterceptorChain.class.getName() + ".PREVIOUS_MESSAGE";
    private static final Logger LOG = LogUtils.getL7dLogger(PhaseInterceptorChain.class);
    private static final ThreadLocal<Message> CURRENT_MESSAGE = new ThreadLocal();
    private final Map<String, Integer> nameMap;
    private final Phase[] phases;
    private InterceptorHolder[] heads;
    private InterceptorHolder[] tails;
    private boolean[] hasAfters;
    private InterceptorChain.State state;
    private Message pausedMessage;
    private MessageObserver faultObserver;
    private PhaseInterceptorIterator iterator;
    private final boolean isFineLogging;
    private boolean faultOccurred;
    private boolean chainReleased;

    private PhaseInterceptorChain(PhaseInterceptorChain src) {
        this.isFineLogging = LOG.isLoggable(Level.FINE);
        this.state = InterceptorChain.State.EXECUTING;
        this.nameMap = src.nameMap;
        this.phases = src.phases;
        int length = this.phases.length;
        this.hasAfters = new boolean[length];
        System.arraycopy(src.hasAfters, 0, this.hasAfters, 0, length);
        this.heads = new InterceptorHolder[length];
        this.tails = new InterceptorHolder[length];
        InterceptorHolder last = null;
        for (int x = 0; x < length; ++x) {
            InterceptorHolder ih = src.heads[x];
            while (ih != null && ih.phaseIdx == x) {
                InterceptorHolder ih2 = new InterceptorHolder(ih);
                ih2.prev = last;
                if (last != null) {
                    last.next = ih2;
                }
                if (this.heads[x] == null) {
                    this.heads[x] = ih2;
                }
                this.tails[x] = ih2;
                last = ih2;
                ih = ih.next;
            }
        }
    }

    public PhaseInterceptorChain(SortedSet<Phase> ps) {
        this.state = InterceptorChain.State.EXECUTING;
        this.isFineLogging = LOG.isLoggable(Level.FINE);
        int numPhases = ps.size();
        this.phases = new Phase[numPhases];
        this.nameMap = new HashMap<String, Integer>();
        this.heads = new InterceptorHolder[numPhases];
        this.tails = new InterceptorHolder[numPhases];
        this.hasAfters = new boolean[numPhases];
        int idx = 0;
        Iterator iterator2 = ps.iterator();
        while (iterator2.hasNext()) {
            Phase phase;
            this.phases[idx] = phase = (Phase)iterator2.next();
            this.nameMap.put(phase.getName(), idx);
            ++idx;
        }
    }

    public static Message getCurrentMessage() {
        return CURRENT_MESSAGE.get();
    }

    public static boolean setCurrentMessage(PhaseInterceptorChain chain, Message m4) {
        if (PhaseInterceptorChain.getCurrentMessage() == m4) {
            return false;
        }
        if (chain.iterator.hasPrevious()) {
            chain.iterator.previous();
            if (chain.iterator.next() instanceof ServiceInvokerInterceptor) {
                CURRENT_MESSAGE.set(m4);
                return true;
            }
            String error = "Only ServiceInvokerInterceptor can update the current chain message";
            LOG.warning(error);
            throw new IllegalStateException(error);
        }
        return false;
    }

    @Override
    public synchronized InterceptorChain.State getState() {
        return this.state;
    }

    public synchronized void releaseAndAcquireChain() {
        while (!this.chainReleased) {
            try {
                this.wait();
            }
            catch (InterruptedException interruptedException) {}
        }
        this.chainReleased = false;
    }

    public synchronized void releaseChain() {
        this.chainReleased = true;
        this.notifyAll();
    }

    public PhaseInterceptorChain cloneChain() {
        return new PhaseInterceptorChain(this);
    }

    private void updateIterator() {
        if (this.iterator == null) {
            this.iterator = new PhaseInterceptorIterator(this.heads);
            this.outputChainToLog(false);
        }
    }

    @Override
    public void add(Collection<Interceptor<? extends Message>> newhandlers) {
        this.add(newhandlers, false);
    }

    public void add(Collection<Interceptor<? extends Message>> newhandlers, boolean force) {
        if (newhandlers == null) {
            return;
        }
        for (Interceptor<? extends Message> handler : newhandlers) {
            this.add(handler, force);
        }
    }

    @Override
    public void add(Interceptor<? extends Message> i) {
        this.add(i, false);
    }

    public void add(Interceptor<? extends Message> i, boolean force) {
        PhaseInterceptor pi = (PhaseInterceptor)i;
        String phaseName = pi.getPhase();
        Integer phase = this.nameMap.get(phaseName);
        if (phase == null) {
            LOG.warning("Skipping interceptor " + i.getClass().getName() + (phaseName == null ? ": Phase declaration is missing." : ": Phase " + phaseName + " specified does not exist."));
        } else {
            if (this.isFineLogging) {
                LOG.fine("Adding interceptor " + i + " to phase " + phaseName);
            }
            this.insertInterceptor(phase, pi, force);
        }
        Collection<PhaseInterceptor<Message>> extras = pi.getAdditionalInterceptors();
        if (extras != null) {
            for (PhaseInterceptor<Message> p : extras) {
                this.add(p, force);
            }
        }
    }

    @Override
    public synchronized void pause() {
        this.state = InterceptorChain.State.PAUSED;
        this.pausedMessage = CURRENT_MESSAGE.get();
    }

    @Override
    public synchronized void unpause() {
        if (this.state == InterceptorChain.State.PAUSED || this.state == InterceptorChain.State.SUSPENDED) {
            this.state = InterceptorChain.State.EXECUTING;
            this.pausedMessage = null;
        }
    }

    @Override
    public synchronized void suspend() {
        this.state = InterceptorChain.State.SUSPENDED;
        this.pausedMessage = CURRENT_MESSAGE.get();
    }

    @Override
    public synchronized void resume() {
        if (this.state == InterceptorChain.State.PAUSED || this.state == InterceptorChain.State.SUSPENDED) {
            this.state = InterceptorChain.State.EXECUTING;
            Message m4 = this.pausedMessage;
            this.pausedMessage = null;
            this.doIntercept(m4);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized boolean doIntercept(Message message) {
        this.updateIterator();
        Message oldMessage = CURRENT_MESSAGE.get();
        try {
            CURRENT_MESSAGE.set(message);
            if (oldMessage != null && !message.containsKey(PREVIOUS_MESSAGE) && message != oldMessage && message.getExchange() != oldMessage.getExchange()) {
                message.put(PREVIOUS_MESSAGE, new WeakReference<Message>(oldMessage));
            }
            while (this.state == InterceptorChain.State.EXECUTING && this.iterator.hasNext()) {
                try {
                    Object currentInterceptor = this.iterator.next();
                    if (this.isFineLogging) {
                        LOG.fine("Invoking handleMessage on interceptor " + currentInterceptor);
                    }
                    currentInterceptor.handleMessage(message);
                    if (this.state != InterceptorChain.State.SUSPENDED) continue;
                    throw new SuspendedInvocationException();
                }
                catch (SuspendedInvocationException ex) {
                    Object suspendProp = message.remove("suspend.chain.on.current.interceptor");
                    if ((suspendProp == null || PropertyUtils.isFalse(suspendProp)) && this.iterator.hasPrevious()) {
                        this.iterator.previous();
                    }
                    this.pause();
                    throw ex;
                }
                catch (RuntimeException ex) {
                    if (!this.faultOccurred) {
                        this.faultOccurred = true;
                        this.wrapExceptionAsFault(message, ex);
                    }
                    this.state = InterceptorChain.State.ABORTED;
                }
            }
            if (this.state == InterceptorChain.State.EXECUTING) {
                this.state = InterceptorChain.State.COMPLETE;
            }
            boolean bl = this.state == InterceptorChain.State.COMPLETE;
            return bl;
        }
        finally {
            CURRENT_MESSAGE.set(oldMessage);
        }
    }

    private void wrapExceptionAsFault(Message message, RuntimeException ex) {
        String description = this.getServiceInfo(message);
        message.setContent(Exception.class, ex);
        this.unwind(message);
        Exception ex2 = message.getContent(Exception.class);
        if (ex2 == null) {
            ex2 = ex;
        }
        FaultListener flogger = (FaultListener)message.getContextualProperty(FaultListener.class.getName());
        boolean useDefaultLogging = true;
        if (flogger != null) {
            useDefaultLogging = flogger.faultOccurred(ex2, description, message);
        }
        if (useDefaultLogging) {
            this.doDefaultLogging(message, ex2, description);
        }
        if (message.getExchange() != null && message.getContent(Exception.class) != null) {
            message.getExchange().put(Exception.class, ex2);
        }
        if (this.faultObserver != null && !this.isOneWay(message)) {
            message.getExchange().setOneWay(false);
            this.faultObserver.onMessage(message);
        }
    }

    private String getServiceInfo(Message message) {
        Exchange exchange;
        Service service2;
        StringBuilder description = new StringBuilder();
        if (message.getExchange() != null && (service2 = (exchange = message.getExchange()).getService()) != null) {
            OperationInfo opInfo;
            description.append('\'');
            description.append(service2.getName());
            BindingOperationInfo boi = exchange.getBindingOperationInfo();
            OperationInfo operationInfo = opInfo = boi != null ? boi.getOperationInfo() : null;
            if (opInfo != null) {
                description.append('#').append(opInfo.getName());
            }
            description.append("' ");
        }
        return description.toString();
    }

    private void doDefaultLogging(Message message, Exception ex, String description) {
        FaultMode mode = message.get(FaultMode.class);
        if (mode == FaultMode.CHECKED_APPLICATION_FAULT) {
            if (this.isFineLogging) {
                LogUtils.log(LOG, Level.FINE, "Application " + description + "has thrown exception, unwinding now", ex);
            } else if (LOG.isLoggable(Level.INFO)) {
                Throwable t2 = ex;
                if (ex instanceof Fault && ex.getCause() != null) {
                    t2 = ex.getCause();
                }
                LogUtils.log(LOG, Level.INFO, "Application " + description + "has thrown exception, unwinding now: " + t2.getClass().getName() + ": " + ex.getMessage());
            }
        } else if (LOG.isLoggable(Level.WARNING)) {
            if (mode == FaultMode.UNCHECKED_APPLICATION_FAULT) {
                LogUtils.log(LOG, Level.WARNING, "Application " + description + "has thrown exception, unwinding now", ex);
            } else {
                LogUtils.log(LOG, Level.WARNING, "Interceptor for " + description + "has thrown exception, unwinding now", ex);
            }
        }
    }

    private boolean isOneWay(Message message) {
        return message.getExchange() != null && message.getExchange().isOneWay() && !this.isRobustOneWay(message);
    }

    private boolean isRobustOneWay(Message message) {
        return MessageUtils.getContextualBoolean(message, "org.apache.cxf.oneway.robust", false);
    }

    @Override
    public synchronized boolean doInterceptStartingAfter(Message message, String startingAfterInterceptorID) {
        PhaseInterceptor currentInterceptor;
        this.updateIterator();
        while (this.state == InterceptorChain.State.EXECUTING && this.iterator.hasNext() && !(currentInterceptor = (PhaseInterceptor)this.iterator.next()).getId().equals(startingAfterInterceptorID)) {
        }
        return this.doIntercept(message);
    }

    @Override
    public synchronized boolean doInterceptStartingAt(Message message, String startingAtInterceptorID) {
        this.updateIterator();
        while (this.state == InterceptorChain.State.EXECUTING && this.iterator.hasNext()) {
            PhaseInterceptor currentInterceptor = (PhaseInterceptor)this.iterator.next();
            if (!currentInterceptor.getId().equals(startingAtInterceptorID)) continue;
            this.iterator.previous();
            break;
        }
        return this.doIntercept(message);
    }

    @Override
    public synchronized void reset() {
        this.updateIterator();
        if (this.state == InterceptorChain.State.COMPLETE) {
            this.state = InterceptorChain.State.EXECUTING;
            this.iterator.reset();
        } else {
            this.iterator.reset();
        }
    }

    public void unwind(Message message) {
        while (this.iterator.hasPrevious()) {
            Object currentInterceptor = this.iterator.previous();
            if (this.isFineLogging) {
                LOG.fine("Invoking handleFault on interceptor " + currentInterceptor);
            }
            try {
                currentInterceptor.handleFault(message);
            }
            catch (RuntimeException e) {
                LOG.log(Level.WARNING, "Exception in handleFault on interceptor " + currentInterceptor, e);
                throw e;
            }
            catch (Exception e) {
                LOG.log(Level.WARNING, "Exception in handleFault on interceptor " + currentInterceptor, e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void remove(Interceptor<? extends Message> i) {
        PhaseInterceptorIterator it = new PhaseInterceptorIterator(this.heads);
        while (it.hasNext()) {
            InterceptorHolder holder = it.nextInterceptorHolder();
            if (holder.interceptor != i) continue;
            this.remove(holder);
            return;
        }
    }

    @Override
    public synchronized void abort() {
        this.state = InterceptorChain.State.ABORTED;
    }

    @Override
    public Iterator<Interceptor<? extends Message>> iterator() {
        return this.getIterator();
    }

    @Override
    public ListIterator<Interceptor<? extends Message>> getIterator() {
        return new PhaseInterceptorIterator(this.heads);
    }

    private void remove(InterceptorHolder i) {
        int ph;
        if (i.prev != null) {
            i.prev.next = i.next;
        }
        if (i.next != null) {
            i.next.prev = i.prev;
        }
        if (this.heads[ph = i.phaseIdx] == i) {
            if (i.next != null && i.next.phaseIdx == ph) {
                this.heads[ph] = i.next;
            } else {
                this.heads[ph] = null;
                this.tails[ph] = null;
            }
        }
        if (this.tails[ph] == i) {
            if (i.prev != null && i.prev.phaseIdx == ph) {
                this.tails[ph] = i.prev;
            } else {
                this.heads[ph] = null;
                this.tails[ph] = null;
            }
        }
    }

    private void insertInterceptor(int phase, PhaseInterceptor<? extends Message> interc, boolean force) {
        InterceptorHolder ih = new InterceptorHolder(interc, phase);
        if (this.heads[phase] == null) {
            int idx;
            this.heads[phase] = ih;
            this.tails[phase] = ih;
            this.hasAfters[phase] = !interc.getAfter().isEmpty();
            for (idx = phase - 1; idx >= 0 && this.tails[idx] == null; --idx) {
            }
            if (idx >= 0) {
                ih.prev = this.tails[idx];
                ih.next = this.tails[idx].next;
                if (ih.next != null) {
                    ih.next.prev = ih;
                }
                this.tails[idx].next = ih;
            } else {
                for (idx = phase + 1; idx < this.heads.length && this.heads[idx] == null; ++idx) {
                }
                if (idx != this.heads.length) {
                    ih.next = this.heads[idx];
                    this.heads[idx].prev = ih;
                }
            }
        } else {
            Set<String> beforeList = interc.getBefore();
            Set<String> afterList = interc.getAfter();
            InterceptorHolder firstBefore = null;
            InterceptorHolder lastAfter = null;
            String id = interc.getId();
            if (this.hasAfters[phase] || !beforeList.isEmpty()) {
                InterceptorHolder ih2 = this.heads[phase];
                while (ih2 != this.tails[phase].next) {
                    PhaseInterceptor<? extends Message> cmp = ih2.interceptor;
                    String cmpId = cmp.getId();
                    if (cmpId != null && firstBefore == null && (beforeList.contains(cmpId) || cmp.getAfter().contains(id))) {
                        firstBefore = ih2;
                    }
                    if (cmp.getBefore().contains(id) || cmpId != null && afterList.contains(cmpId)) {
                        lastAfter = ih2;
                    }
                    if (!force && cmpId.equals(id)) {
                        return;
                    }
                    ih2 = ih2.next;
                }
                if (lastAfter == null && beforeList.contains("*")) {
                    firstBefore = this.heads[phase];
                }
            } else if (!force) {
                InterceptorHolder ih2 = this.heads[phase];
                while (ih2 != this.tails[phase].next) {
                    if (ih2.interceptor.getId().equals(id)) {
                        return;
                    }
                    ih2 = ih2.next;
                }
            }
            int n = phase;
            this.hasAfters[n] = this.hasAfters[n] | !afterList.isEmpty();
            if (firstBefore == null && lastAfter == null && !beforeList.isEmpty() && afterList.isEmpty()) {
                firstBefore = this.heads[phase];
            }
            if (firstBefore == null) {
                ih.prev = this.tails[phase];
                ih.next = this.tails[phase].next;
                this.tails[phase].next = ih;
                if (ih.next != null) {
                    ih.next.prev = ih;
                }
                this.tails[phase] = ih;
            } else {
                ih.prev = firstBefore.prev;
                if (ih.prev != null) {
                    ih.prev.next = ih;
                }
                ih.next = firstBefore;
                firstBefore.prev = ih;
                if (this.heads[phase] == firstBefore) {
                    this.heads[phase] = ih;
                }
            }
        }
        if (this.iterator != null) {
            this.outputChainToLog(true);
        }
    }

    public String toString() {
        return this.toString("");
    }

    private String toString(String message) {
        StringBuilder chain = new StringBuilder(128);
        chain.append("Chain ").append(super.toString()).append(message).append(". Current flow:\n");
        for (int x = 0; x < this.phases.length; ++x) {
            if (this.heads[x] == null) continue;
            chain.append("  ");
            this.printPhase(x, chain);
        }
        return chain.toString();
    }

    private void printPhase(int ph, StringBuilder chain) {
        chain.append(this.phases[ph].getName()).append(" [");
        InterceptorHolder i = this.heads[ph];
        boolean first = true;
        while (i != this.tails[ph].next) {
            if (first) {
                first = false;
            } else {
                chain.append(", ");
            }
            String nm = i.interceptor.getClass().getSimpleName();
            if (StringUtils.isEmpty(nm)) {
                nm = i.interceptor.getId();
            }
            chain.append(nm);
            i = i.next;
        }
        chain.append("]\n");
    }

    private void outputChainToLog(boolean modified) {
        if (this.isFineLogging) {
            if (modified) {
                LOG.fine(this.toString(" was modified"));
            } else {
                LOG.fine(this.toString(" was created"));
            }
        }
    }

    @Override
    public MessageObserver getFaultObserver() {
        return this.faultObserver;
    }

    @Override
    public void setFaultObserver(MessageObserver faultObserver) {
        this.faultObserver = faultObserver;
    }

    static final class InterceptorHolder {
        PhaseInterceptor<? extends Message> interceptor;
        InterceptorHolder next;
        InterceptorHolder prev;
        int phaseIdx;

        InterceptorHolder(PhaseInterceptor<? extends Message> i, int p) {
            this.interceptor = i;
            this.phaseIdx = p;
        }

        InterceptorHolder(InterceptorHolder p) {
            this.interceptor = p.interceptor;
            this.phaseIdx = p.phaseIdx;
        }
    }

    static final class PhaseInterceptorIterator
    implements ListIterator<Interceptor<? extends Message>> {
        InterceptorHolder[] heads;
        InterceptorHolder prev;
        InterceptorHolder first;

        PhaseInterceptorIterator(InterceptorHolder[] h2) {
            this.heads = h2;
            this.first = this.findFirst();
        }

        public void reset() {
            this.prev = null;
            this.first = this.findFirst();
        }

        private InterceptorHolder findFirst() {
            for (int x = 0; x < this.heads.length; ++x) {
                if (this.heads[x] == null) continue;
                return this.heads[x];
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            if (this.prev == null) {
                return this.first != null;
            }
            return this.prev.next != null;
        }

        @Override
        public Interceptor<? extends Message> next() {
            if (this.prev == null) {
                if (this.first == null) {
                    throw new NoSuchElementException();
                }
                this.prev = this.first;
            } else {
                if (this.prev.next == null) {
                    throw new NoSuchElementException();
                }
                this.prev = this.prev.next;
            }
            return this.prev.interceptor;
        }

        public InterceptorHolder nextInterceptorHolder() {
            if (this.prev == null) {
                if (this.first == null) {
                    throw new NoSuchElementException();
                }
                this.prev = this.first;
            } else {
                if (this.prev.next == null) {
                    throw new NoSuchElementException();
                }
                this.prev = this.prev.next;
            }
            return this.prev;
        }

        @Override
        public boolean hasPrevious() {
            return this.prev != null;
        }

        @Override
        public Interceptor<? extends Message> previous() {
            if (this.prev == null) {
                throw new NoSuchElementException();
            }
            InterceptorHolder tmp = this.prev;
            this.prev = this.prev.prev;
            return tmp.interceptor;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Interceptor<? extends Message> o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Interceptor<? extends Message> o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

