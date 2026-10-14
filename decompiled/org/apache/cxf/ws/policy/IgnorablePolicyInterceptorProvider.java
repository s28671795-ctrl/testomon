/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;

public class IgnorablePolicyInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = 8411915832046532494L;
    private static final Logger LOG = LogUtils.getL7dLogger(IgnorablePolicyInterceptorProvider.class);
    private IgnorableAssertionsInterceptor interceptor = new IgnorableAssertionsInterceptor();

    public IgnorablePolicyInterceptorProvider(QName type) {
        this(Collections.singletonList(type));
    }

    public IgnorablePolicyInterceptorProvider(Collection<QName> at) {
        super(at);
        this.getInInterceptors().add(this.interceptor);
        this.getOutInterceptors().add(this.interceptor);
        this.getInFaultInterceptors().add(this.interceptor);
        this.getOutFaultInterceptors().add(this.interceptor);
    }

    private class IgnorableAssertionsInterceptor
    extends AbstractPhaseInterceptor<Message> {
        IgnorableAssertionsInterceptor() {
            super("post-logical");
        }

        @Override
        public void handleMessage(Message message) throws Fault {
            AssertionInfoMap aim = message.get(AssertionInfoMap.class);
            for (QName an : IgnorablePolicyInterceptorProvider.this.getAssertionTypes()) {
                Collection<AssertionInfo> ais = aim.getAssertionInfo(an);
                if (LOG.isLoggable(Level.INFO)) {
                    LOG.info("Asserting for " + an);
                }
                if (null == ais) continue;
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
            }
        }
    }
}

