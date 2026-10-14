/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.Arrays;
import org.apache.cxf.Bus;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxInInterceptor;
import org.apache.cxf.ws.security.wss4j.PolicyBasedWSS4JStaxOutInterceptor;
import org.apache.cxf.ws.security.wss4j.UsernameTokenInterceptor;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;

public class UsernameTokenInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -1507727324874727254L;

    public UsernameTokenInterceptorProvider() {
        this(new UsernameTokenInterceptor());
    }

    public UsernameTokenInterceptorProvider(Bus bus) {
        this((UsernameTokenInterceptor)bus.getProperty("org.apache.cxf.ws.security.usernametoken.interceptor"));
    }

    public UsernameTokenInterceptorProvider(UsernameTokenInterceptor inInterceptor) {
        super(Arrays.asList(SP12Constants.USERNAME_TOKEN, SP11Constants.USERNAME_TOKEN));
        this.getOutInterceptors().add(new UsernameTokenInterceptor());
        this.getInInterceptors().add(inInterceptor == null ? new UsernameTokenInterceptor() : inInterceptor);
        PolicyBasedWSS4JStaxOutInterceptor so = new PolicyBasedWSS4JStaxOutInterceptor();
        PolicyBasedWSS4JStaxInInterceptor si = new PolicyBasedWSS4JStaxInInterceptor();
        this.getOutInterceptors().add(so);
        this.getOutFaultInterceptors().add(so);
        this.getInInterceptors().add(si);
        this.getInFaultInterceptors().add(si);
    }
}

