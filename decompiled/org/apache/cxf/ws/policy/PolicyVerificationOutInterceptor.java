/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptor;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.PolicyException;

public class PolicyVerificationOutInterceptor
extends AbstractPolicyInterceptor {
    public static final PolicyVerificationOutInterceptor INSTANCE = new PolicyVerificationOutInterceptor();
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyVerificationOutInterceptor.class);

    public PolicyVerificationOutInterceptor() {
        super("post-stream");
    }

    @Override
    protected void handle(Message message) {
        if (MessageUtils.isPartialResponse(message)) {
            LOG.fine("Not verifying policies on outbound partial response.");
            return;
        }
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        if (null == aim) {
            return;
        }
        this.getTransportAssertions(message);
        EffectivePolicy policy = message.get(EffectivePolicy.class);
        if (policy == null) {
            return;
        }
        try {
            aim.checkEffectivePolicy(policy.getPolicy());
        }
        catch (PolicyException e) {
            LOG.fine("An exception was thrown when verifying that the effective policy for this request was satisfied.  However, this exception will not result in a fault.  The exception raised is: " + e.toString());
            return;
        }
        LOG.fine("Verified policies for outbound message.");
    }
}

