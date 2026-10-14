/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletRequestWrapper
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http_jetty.continuations;

import java.lang.reflect.Method;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.ContinuationProviderFactory;
import org.apache.cxf.transport.http_jetty.continuations.JettyContinuationProvider;

public class JettyContinuationProviderFactory
implements ContinuationProviderFactory {
    final boolean disableJettyContinuations = Boolean.getBoolean("org.apache.cxf.transport.http_jetty.continuations.disable");

    @Override
    public ContinuationProvider createContinuationProvider(Message inMessage, HttpServletRequest req, HttpServletResponse resp) {
        if (!this.disableJettyContinuations) {
            HttpServletRequest r2 = req;
            while (r2 instanceof ServletRequestWrapper) {
                r2 = ((ServletRequestWrapper)r2).getRequest();
            }
            if (!r2.getClass().getName().contains("jetty")) {
                return null;
            }
            try {
                Method m4 = r2.getClass().getMethod("isAsyncSupported", new Class[0]);
                Object o = ReflectionUtil.setAccessible(m4).invoke((Object)r2, new Object[0]);
                if (((Boolean)o).booleanValue()) {
                    return new JettyContinuationProvider(req, resp, inMessage);
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public Message retrieveFromContinuation(HttpServletRequest req) {
        return (Message)req.getAttribute("cxf.continuation.message");
    }
}

