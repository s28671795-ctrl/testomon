/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletResponse
 */
package org.eclipse.jetty.continuation;

import javax.servlet.ServletResponse;
import org.eclipse.jetty.continuation.ContinuationListener;
import org.eclipse.jetty.continuation.ContinuationThrowable;

@Deprecated
public interface Continuation {
    public static final String ATTRIBUTE = "org.eclipse.jetty.continuation";

    public void setTimeout(long var1);

    public void suspend();

    public void suspend(ServletResponse var1);

    public void resume();

    public void complete();

    public boolean isSuspended();

    public boolean isResumed();

    public boolean isExpired();

    public boolean isInitial();

    public boolean isResponseWrapped();

    public ServletResponse getServletResponse();

    public void addContinuationListener(ContinuationListener var1);

    public void setAttribute(String var1, Object var2);

    public Object getAttribute(String var1);

    public void removeAttribute(String var1);

    public void undispatch() throws ContinuationThrowable;
}

