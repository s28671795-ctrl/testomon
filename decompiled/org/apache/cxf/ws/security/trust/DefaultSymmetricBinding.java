/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.EncryptionToken;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.SignatureToken;
import org.apache.wss4j.policy.model.SymmetricBinding;

public class DefaultSymmetricBinding
extends SymmetricBinding {
    public DefaultSymmetricBinding(SPConstants.SPVersion version, Policy nestedPolicy) {
        super(version, nestedPolicy);
    }

    @Override
    public void setEncryptionToken(EncryptionToken encryptionToken) {
        super.setEncryptionToken(encryptionToken);
    }

    @Override
    public void setSignatureToken(SignatureToken signatureToken) {
        super.setSignatureToken(signatureToken);
    }

    @Override
    public void setProtectionToken(ProtectionToken protectionToken) {
        super.setProtectionToken(protectionToken);
    }

    @Override
    public void setOnlySignEntireHeadersAndBody(boolean onlySignEntireHeadersAndBody) {
        super.setOnlySignEntireHeadersAndBody(onlySignEntireHeadersAndBody);
    }

    @Override
    public void setProtectTokens(boolean protectTokens) {
        super.setProtectTokens(protectTokens);
    }

    @Override
    public void setIncludeTimestamp(boolean includeTimestamp) {
        super.setIncludeTimestamp(includeTimestamp);
    }

    @Override
    public void setAlgorithmSuite(AlgorithmSuite algorithmSuite) {
        super.setAlgorithmSuite(algorithmSuite);
    }
}

