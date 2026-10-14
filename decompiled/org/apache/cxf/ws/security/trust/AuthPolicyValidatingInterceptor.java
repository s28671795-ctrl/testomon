/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.rt.security.saml.interceptor.WSS4JBasicAuthValidator;

public class AuthPolicyValidatingInterceptor
extends WSS4JBasicAuthValidator
implements PhaseInterceptor<Message> {
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AuthPolicyValidatingInterceptor.class);
    private static final Logger LOG = LogUtils.getL7dLogger(AuthPolicyValidatingInterceptor.class);
    private String phase;

    public AuthPolicyValidatingInterceptor() {
        this("unmarshal");
    }

    public AuthPolicyValidatingInterceptor(String phase) {
        this.phase = phase;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);
        if (policy == null || policy.getUserName() == null || policy.getPassword() == null) {
            String name = null;
            if (policy != null) {
                name = policy.getUserName();
            }
            org.apache.cxf.common.i18n.Message errorMsg = new org.apache.cxf.common.i18n.Message("NO_USER_PASSWORD", BUNDLE, name);
            LOG.warning(errorMsg.toString());
            throw new SecurityException(errorMsg.toString());
        }
        try {
            super.validate(message);
        }
        catch (Exception ex) {
            throw new Fault(ex);
        }
    }

    @Override
    public void handleFault(Message arg0) {
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        return null;
    }

    @Override
    public Set<String> getAfter() {
        return Collections.emptySet();
    }

    @Override
    public Set<String> getBefore() {
        return Collections.emptySet();
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

    @Override
    public String getPhase() {
        return this.phase;
    }
}

