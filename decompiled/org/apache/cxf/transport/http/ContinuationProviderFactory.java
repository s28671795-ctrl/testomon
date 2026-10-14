/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.continuations.ContinuationProvider;
import org.apache.cxf.message.Message;

public interface ContinuationProviderFactory {
    public ContinuationProvider createContinuationProvider(Message var1, HttpServletRequest var2, HttpServletResponse var3);

    public Message retrieveFromContinuation(HttpServletRequest var1);
}

