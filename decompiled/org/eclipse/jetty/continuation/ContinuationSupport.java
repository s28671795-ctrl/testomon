/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequest
 *  javax.servlet.ServletRequestWrapper
 *  javax.servlet.ServletResponse
 */
package org.eclipse.jetty.continuation;

import java.lang.reflect.Constructor;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.continuation.Continuation;

@Deprecated
public class ContinuationSupport {
    static final boolean __servlet3;
    static final Class<?> __waitingContinuation;
    static final Constructor<? extends Continuation> __newServlet3Continuation;

    public static Continuation getContinuation(ServletRequest request) {
        Continuation continuation = (Continuation)request.getAttribute("org.eclipse.jetty.continuation");
        if (continuation != null) {
            return continuation;
        }
        while (request instanceof ServletRequestWrapper) {
            request = ((ServletRequestWrapper)request).getRequest();
        }
        if (__servlet3) {
            try {
                continuation = __newServlet3Continuation.newInstance(request);
                request.setAttribute("org.eclipse.jetty.continuation", (Object)continuation);
                return continuation;
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("!(Jetty || Servlet 3.0 || ContinuationFilter)");
    }

    @Deprecated
    public static Continuation getContinuation(ServletRequest request, ServletResponse response) {
        return ContinuationSupport.getContinuation(request);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        boolean servlet3Support = false;
        Constructor<Continuation> s3cc = null;
        try {
            boolean servlet3;
            boolean bl = servlet3 = ServletRequest.class.getMethod("startAsync", new Class[0]) != null;
            if (servlet3) {
                Class<Continuation> s3c = ContinuationSupport.class.getClassLoader().loadClass("org.eclipse.jetty.continuation.Servlet3Continuation").asSubclass(Continuation.class);
                s3cc = s3c.getConstructor(ServletRequest.class);
                servlet3Support = true;
            }
        }
        catch (Exception servlet3) {
        }
        finally {
            __servlet3 = servlet3Support;
            __newServlet3Continuation = s3cc;
        }
        Class<?> waiting = null;
        try {
            waiting = ContinuationSupport.class.getClassLoader().loadClass("org.mortbay.util.ajax.WaitingContinuation");
        }
        catch (Exception exception) {
        }
        finally {
            __waitingContinuation = waiting;
        }
    }
}

