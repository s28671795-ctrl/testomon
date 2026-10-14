/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.util.Collection;
import java.util.ListIterator;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.MessageObserver;

public interface InterceptorChain
extends Iterable<Interceptor<? extends Message>> {
    public static final String STARTING_AFTER_INTERCEPTOR_ID = "starting_after_interceptor_id";
    public static final String STARTING_AT_INTERCEPTOR_ID = "starting_at_interceptor_id";

    public void add(Interceptor<? extends Message> var1);

    public void add(Collection<Interceptor<? extends Message>> var1);

    public void remove(Interceptor<? extends Message> var1);

    public boolean doIntercept(Message var1);

    public boolean doInterceptStartingAfter(Message var1, String var2);

    public boolean doInterceptStartingAt(Message var1, String var2);

    public void pause();

    public void suspend();

    public void resume();

    public void unpause();

    public void reset();

    public State getState();

    public ListIterator<Interceptor<? extends Message>> getIterator();

    public MessageObserver getFaultObserver();

    public void setFaultObserver(MessageObserver var1);

    public void abort();

    public static enum State {
        PAUSED,
        SUSPENDED,
        EXECUTING,
        COMPLETE,
        ABORTED;

    }
}

