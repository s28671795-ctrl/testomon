/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Arrays;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxOutInterceptor;
import org.apache.cxf.ws.security.wss4j.SamlTokenInterceptor;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;

public class SamlTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -2270910622513357794L;

    public SamlTokenInterceptorProvider() {
        super(Arrays.asList(SP12Constants.SAML_TOKEN, SP11Constants.SAML_TOKEN));
        this.getOutInterceptors().add(new SamlTokenInterceptor());
        this.getInInterceptors().add(new SamlTokenInterceptor());
        PolicyBasedWSS4JStaxOutInterceptor so = new PolicyBasedWSS4JStaxOutInterceptor();
        PolicyBasedWSS4JStaxInInterceptor si = new PolicyBasedWSS4JStaxInInterceptor();
        this.getOutInterceptors().add(so);
        this.getOutFaultInterceptors().add(so);
        this.getInInterceptors().add(si);
        this.getInFaultInterceptors().add(si);
    }
}

