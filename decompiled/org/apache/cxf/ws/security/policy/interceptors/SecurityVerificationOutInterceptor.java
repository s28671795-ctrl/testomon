/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.interceptors;

import java.util.logging.Logger;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.wss4j.policy.SP12Constants;

public class SecurityVerificationOutInterceptor
extends AbstractPhaseInterceptor<SoapMessage> {
    public static final SecurityVerificationOutInterceptor INSTANCE = new SecurityVerificationOutInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(SecurityVerificationOutInterceptor.class);

    public SecurityVerificationOutInterceptor() {
        super("pre-logical");
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        AssertionInfo assertion;
        AssertionInfoMap aim;
        if (MessageUtils.isRequestor(message) && (aim = message.get(AssertionInfoMap.class)) != null && PolicyUtils.getSecurityBinding(aim) == null && (assertion = this.getSecuredPart(aim)) != null) {
            String error = String.format("%s assertion cannot be fulfilled without binding. At least one binding assertion (%s, %s, %s) must be specified in policy.", assertion.getAssertion().getName(), SP12Constants.TRANSPORT_BINDING.getLocalPart(), SP12Constants.ASYMMETRIC_BINDING.getLocalPart(), SP12Constants.SYMMETRIC_BINDING.getLocalPart());
            assertion.setNotAsserted(error);
            LOG.severe(error);
            throw new PolicyException(assertion);
        }
    }

    private AssertionInfo getSecuredPart(AssertionInfoMap aim) {
        AssertionInfo assertion = PolicyUtils.getFirstAssertionByLocalname(aim, "SignedParts");
        if (assertion != null) {
            return assertion;
        }
        assertion = PolicyUtils.getFirstAssertionByLocalname(aim, "SignedElements");
        if (assertion != null) {
            return assertion;
        }
        assertion = PolicyUtils.getFirstAssertionByLocalname(aim, "EncryptedParts");
        if (assertion != null) {
            return assertion;
        }
        assertion = PolicyUtils.getFirstAssertionByLocalname(aim, "EncryptedElements");
        if (assertion != null) {
            return assertion;
        }
        assertion = PolicyUtils.getFirstAssertionByLocalname(aim, "ContentEncryptedElements");
        if (assertion != null) {
            return assertion;
        }
        return null;
    }
}

