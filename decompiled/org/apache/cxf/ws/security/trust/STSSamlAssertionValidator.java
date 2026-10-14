/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.SamlAssertionValidator;

public class STSSamlAssertionValidator
extends SamlAssertionValidator {
    private static final Logger LOG = LogUtils.getL7dLogger(STSSamlAssertionValidator.class);
    private boolean trustVerificationSucceeded;

    @Override
    protected Credential verifySignedAssertion(SamlAssertionWrapper assertion, RequestData data) throws WSSecurityException {
        try {
            Credential credential = super.verifySignedAssertion(assertion, data);
            this.trustVerificationSucceeded = true;
            return credential;
        }
        catch (WSSecurityException ex) {
            LOG.log(Level.WARNING, "Local trust verification of SAML assertion failed: " + ex.getMessage(), ex);
            this.trustVerificationSucceeded = false;
            return null;
        }
    }

    public boolean isTrustVerificationSucceeded() {
        return this.trustVerificationSucceeded;
    }
}

