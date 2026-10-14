/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy.attachment;

import java.util.ResourceBundle;
import org.apache.cxf.Bus;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.ws.policy.PolicyBuilder;
import org.apache.cxf.ws.policy.PolicyEngine;
import org.apache.cxf.ws.policy.PolicyEngineImpl;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.policy.PolicyProvider;
import org.apache.cxf.ws.policy.attachment.reference.RemoteReferenceResolver;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyReference;
import org.apache.neethi.PolicyRegistry;

public abstract class AbstractPolicyProvider
implements PolicyProvider {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AbstractPolicyProvider.class);
    protected PolicyBuilder builder;
    protected PolicyRegistry registry;
    protected Bus bus;

    protected AbstractPolicyProvider() {
        this(null);
    }

    protected AbstractPolicyProvider(Bus b) {
        this.setBus(b);
    }

    public final void setBus(Bus b) {
        this.bus = b;
        if (null != this.bus) {
            this.setBuilder(this.bus.getExtension(PolicyBuilder.class));
            PolicyEngine pe = this.bus.getExtension(PolicyEngine.class);
            if (pe != null) {
                this.setRegistry(pe.getRegistry());
                ((PolicyEngineImpl)pe).addPolicyProvider(this);
            }
        }
    }

    public final void setBuilder(PolicyBuilder b) {
        this.builder = b;
    }

    public final void setRegistry(PolicyRegistry r) {
        this.registry = r;
    }

    protected Policy resolveExternal(PolicyReference ref, String baseURI) {
        Policy resolved = this.registry.lookup(ref.getURI());
        if (null != resolved) {
            return resolved;
        }
        RemoteReferenceResolver resolver2 = new RemoteReferenceResolver(baseURI, this.builder);
        return resolver2.resolveReference(ref.getURI());
    }

    protected boolean isExternal(PolicyReference ref) {
        return !ref.getURI().startsWith("#");
    }

    protected void checkResolved(PolicyReference ref, Policy p) {
        if (null == p) {
            throw new PolicyException(new Message("UNRESOLVED_POLICY_REFERENCE_EXC", BUNDLE, ref.getURI()));
        }
    }
}

