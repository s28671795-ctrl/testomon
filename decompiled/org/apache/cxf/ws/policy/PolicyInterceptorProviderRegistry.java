/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.cxf.extension.Registry;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.PolicyInterceptorProvider;
import org.apache.neethi.Assertion;

public interface PolicyInterceptorProviderRegistry
extends Registry<QName, Set<PolicyInterceptorProvider>> {
    public void register(PolicyInterceptorProvider var1);

    public List<Interceptor<? extends Message>> getInterceptorsForAlternative(Collection<? extends Assertion> var1, boolean var2, boolean var3);

    public List<Interceptor<? extends Message>> getInInterceptorsForAssertion(QName var1);

    public List<Interceptor<? extends Message>> getInFaultInterceptorsForAssertion(QName var1);

    public List<Interceptor<? extends Message>> getOutInterceptorsForAssertion(QName var1);

    public List<Interceptor<? extends Message>> getOutFaultInterceptorsForAssertion(QName var1);
}

