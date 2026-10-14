/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.message.Message;
import org.apache.neethi.Assertion;

public interface PolicyInterceptorProvider
extends InterceptorProvider {
    public Collection<QName> getAssertionTypes();

    public boolean configurationPresent(Message var1, Assertion var2);
}

