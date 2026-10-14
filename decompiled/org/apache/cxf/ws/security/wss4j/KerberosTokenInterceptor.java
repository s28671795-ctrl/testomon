/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.BinarySecurityTokenInterceptor;
import org.apache.wss4j.policy.model.AbstractToken;

public class KerberosTokenInterceptor
extends BinarySecurityTokenInterceptor {
    @Override
    protected AbstractToken assertTokens(SoapMessage message) {
        AssertionInfoMap aim = message.get(AssertionInfoMap.class);
        PolicyUtils.assertPolicy(aim, "WssKerberosV5ApReqToken11");
        PolicyUtils.assertPolicy(aim, "WssGssKerberosV5ApReqToken11");
        return this.assertTokens(message, "KerberosToken", false);
    }
}

