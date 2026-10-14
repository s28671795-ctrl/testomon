/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.Collections;
import javax.xml.namespace.QName;
import org.apache.cxf.interceptor.AbstractAttributedInterceptorProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.policy.PolicyInterceptorProvider;
import org.apache.neethi.Assertion;

public abstract class AbstractPolicyInterceptorProvider
extends AbstractAttributedInterceptorProvider
implements PolicyInterceptorProvider {
    private static final long serialVersionUID = 7076292509741199877L;
    private Collection<QName> assertionTypes;

    public AbstractPolicyInterceptorProvider(QName type) {
        this(Collections.singletonList(type));
    }

    public AbstractPolicyInterceptorProvider(Collection<QName> at) {
        this.assertionTypes = at;
    }

    @Override
    public Collection<QName> getAssertionTypes() {
        return this.assertionTypes;
    }

    @Override
    public boolean configurationPresent(Message msg, Assertion assertion) {
        return true;
    }
}

