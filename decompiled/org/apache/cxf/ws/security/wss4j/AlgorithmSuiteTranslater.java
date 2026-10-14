/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SupportingTokens;

public final class AlgorithmSuiteTranslater {
    public void translateAlgorithmSuites(AssertionInfoMap aim, RequestData data) throws WSSecurityException {
        Collection<AssertionInfo> ais;
        if (aim == null) {
            return;
        }
        List<AlgorithmSuite> algorithmSuites = this.getAlgorithmSuites(this.getBindings(aim));
        if (!algorithmSuites.isEmpty()) {
            org.apache.wss4j.common.crypto.AlgorithmSuite algorithmSuite = this.translateAlgorithmSuites(algorithmSuites);
            data.setAlgorithmSuite(algorithmSuite);
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SamlToken")).isEmpty()) {
            ArrayList<AlgorithmSuite> samlAlgorithmSuites = new ArrayList<AlgorithmSuite>();
            for (AssertionInfo ai : ais) {
                SamlToken samlToken = (SamlToken)ai.getAssertion();
                AbstractSecurityAssertion parentAssertion = samlToken.getParentAssertion();
                if (!(parentAssertion instanceof SupportingTokens) || ((SupportingTokens)parentAssertion).getAlgorithmSuite() == null) continue;
                samlAlgorithmSuites.add(((SupportingTokens)parentAssertion).getAlgorithmSuite());
            }
            if (!samlAlgorithmSuites.isEmpty()) {
                data.setSamlAlgorithmSuite(this.translateAlgorithmSuites(samlAlgorithmSuites));
            }
        }
    }

    private org.apache.wss4j.common.crypto.AlgorithmSuite translateAlgorithmSuites(List<AlgorithmSuite> algorithmSuites) {
        org.apache.wss4j.common.crypto.AlgorithmSuite algorithmSuite = null;
        for (AlgorithmSuite cxfAlgorithmSuite : algorithmSuites) {
            AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType;
            if (cxfAlgorithmSuite == null) continue;
            if (algorithmSuite == null) {
                algorithmSuite = new org.apache.wss4j.common.crypto.AlgorithmSuite();
            }
            if ((algorithmSuiteType = cxfAlgorithmSuite.getAlgorithmSuiteType()) != null) {
                if (algorithmSuite.getMaximumAsymmetricKeyLength() < algorithmSuiteType.getMaximumAsymmetricKeyLength()) {
                    algorithmSuite.setMaximumAsymmetricKeyLength(algorithmSuiteType.getMaximumAsymmetricKeyLength());
                }
                if (algorithmSuite.getMinimumAsymmetricKeyLength() > algorithmSuiteType.getMinimumAsymmetricKeyLength()) {
                    algorithmSuite.setMinimumAsymmetricKeyLength(algorithmSuiteType.getMinimumAsymmetricKeyLength());
                }
                if (algorithmSuite.getMaximumSymmetricKeyLength() < algorithmSuiteType.getMaximumSymmetricKeyLength()) {
                    algorithmSuite.setMaximumSymmetricKeyLength(algorithmSuiteType.getMaximumSymmetricKeyLength());
                }
                if (algorithmSuite.getMinimumSymmetricKeyLength() > algorithmSuiteType.getMinimumSymmetricKeyLength()) {
                    algorithmSuite.setMinimumSymmetricKeyLength(algorithmSuiteType.getMinimumSymmetricKeyLength());
                }
                algorithmSuite.addEncryptionMethod(algorithmSuiteType.getEncryption());
                algorithmSuite.addKeyWrapAlgorithm(algorithmSuiteType.getSymmetricKeyWrap());
                algorithmSuite.addKeyWrapAlgorithm(algorithmSuiteType.getAsymmetricKeyWrap());
                algorithmSuite.addDigestAlgorithm(algorithmSuiteType.getDigest());
            }
            algorithmSuite.addSignatureMethod(cxfAlgorithmSuite.getAsymmetricSignature());
            algorithmSuite.addSignatureMethod(cxfAlgorithmSuite.getSymmetricSignature());
            algorithmSuite.addC14nAlgorithm(cxfAlgorithmSuite.getC14n().getValue());
            algorithmSuite.addTransformAlgorithm(cxfAlgorithmSuite.getC14n().getValue());
            algorithmSuite.addTransformAlgorithm("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform");
            algorithmSuite.addTransformAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
            algorithmSuite.addTransformAlgorithm("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
            algorithmSuite.addTransformAlgorithm("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform");
            algorithmSuite.addTransformAlgorithm("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform");
            algorithmSuite.addDerivedKeyAlgorithm("http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1");
            algorithmSuite.addDerivedKeyAlgorithm("http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1");
        }
        return algorithmSuite;
    }

    private List<AbstractBinding> getBindings(AssertionInfoMap aim) {
        ArrayList<AbstractBinding> bindings = new ArrayList<AbstractBinding>();
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "TransportBinding");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                bindings.add((AbstractBinding)ai.getAssertion());
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "AsymmetricBinding")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                bindings.add((AbstractBinding)ai.getAssertion());
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SymmetricBinding")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                bindings.add((AbstractBinding)ai.getAssertion());
            }
        }
        return bindings;
    }

    private List<AlgorithmSuite> getAlgorithmSuites(List<AbstractBinding> bindings) {
        ArrayList<AlgorithmSuite> algorithmSuites = new ArrayList<AlgorithmSuite>();
        for (AbstractBinding binding : bindings) {
            if (binding.getAlgorithmSuite() == null) continue;
            algorithmSuites.add(binding.getAlgorithmSuite());
        }
        return algorithmSuites;
    }
}

