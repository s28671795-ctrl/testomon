/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyvalidators;

import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.w3c.dom.Element;

public interface ClaimsPolicyValidator {
    public boolean validatePolicy(Element var1, SamlAssertionWrapper var2);

    public String getDialect();
}

