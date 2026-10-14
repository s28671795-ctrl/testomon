/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;

public class AlgorithmSuiteAssertionState
extends AssertionState
implements Assertable {
    private PolicyAsserter policyAsserter;

    public AlgorithmSuiteAssertionState(AbstractSecurityAssertion assertion, PolicyAsserter policyAsserter, boolean asserted) {
        super(assertion, asserted);
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            AlgorithmSuite algorithmSuite = (AlgorithmSuite)this.getAssertion();
            policyAsserter.assertPolicy(this.getAssertion());
            String namespace = algorithmSuite.getAlgorithmSuiteType().getNamespace();
            String name = algorithmSuite.getAlgorithmSuiteType().getName();
            policyAsserter.assertPolicy(new QName(namespace, name));
        }
    }

    @Override
    public SecurityEventConstants.Event[] getSecurityEventType() {
        return new SecurityEventConstants.Event[]{SecurityEventConstants.AlgorithmSuite};
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException {
        AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = (AlgorithmSuiteSecurityEvent)securityEvent;
        AlgorithmSuite algorithmSuite = (AlgorithmSuite)this.getAssertion();
        XMLSecurityConstants.AlgorithmUsage keyUsage = algorithmSuiteSecurityEvent.getAlgorithmUsage();
        int keyLength = algorithmSuiteSecurityEvent.getKeyLength();
        String algorithmURI = algorithmSuiteSecurityEvent.getAlgorithmURI();
        if (WSSConstants.Sym_Sig.equals(keyUsage)) {
            if (algorithmSuite.getSymmetricSignature() != null && !algorithmSuite.getSymmetricSignature().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Symmetric signature algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
            if (algorithmSuite.getAlgorithmSuiteType() != null) {
                if (!(algorithmSuiteSecurityEvent.isDerivedKey() || algorithmSuite.getAlgorithmSuiteType().getMinimumSymmetricKeyLength() <= keyLength && algorithmSuite.getAlgorithmSuiteType().getMaximumSymmetricKeyLength() >= keyLength)) {
                    this.setAsserted(false);
                    this.setErrorMessage("Symmetric signature algorithm key length " + keyLength + " does not meet policy");
                    this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
                } else if (algorithmSuiteSecurityEvent.isDerivedKey() && algorithmSuite.getAlgorithmSuiteType().getSignatureDerivedKeyLength() != keyLength) {
                    this.setAsserted(false);
                    this.setErrorMessage("Symmetric signature algorithm derived key length " + keyLength + " does not meet policy");
                    this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
                }
            }
        } else if (WSSConstants.Asym_Sig.equals(keyUsage)) {
            if (algorithmSuite.getAsymmetricSignature() != null && !algorithmSuite.getAsymmetricSignature().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Asymmetric algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
            if (algorithmSuite.getAlgorithmSuiteType() != null && (algorithmSuite.getAlgorithmSuiteType().getMinimumAsymmetricKeyLength() > keyLength || algorithmSuite.getAlgorithmSuiteType().getMaximumAsymmetricKeyLength() < keyLength)) {
                this.setAsserted(false);
                this.setErrorMessage("Asymmetric signature algorithm key length " + keyLength + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.SigDig.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getDigest().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Digest algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.Enc.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getEncryption().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Encryption algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
            if (algorithmSuite.getAlgorithmSuiteType() != null) {
                if (!(algorithmSuiteSecurityEvent.isDerivedKey() || algorithmSuite.getAlgorithmSuiteType().getMinimumSymmetricKeyLength() <= keyLength && algorithmSuite.getAlgorithmSuiteType().getMaximumSymmetricKeyLength() >= keyLength)) {
                    this.setAsserted(false);
                    this.setErrorMessage("Symmetric encryption algorithm key length " + keyLength + " does not meet policy");
                    this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
                } else if (algorithmSuiteSecurityEvent.isDerivedKey() && algorithmSuite.getAlgorithmSuiteType().getEncryptionDerivedKeyLength() != keyLength) {
                    this.setAsserted(false);
                    this.setErrorMessage("Symmetric encryption algorithm derived key length " + keyLength + " does not meet policy");
                    this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
                }
            }
        } else if (WSSConstants.Sym_Key_Wrap.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getSymmetricKeyWrap().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Symmetric key wrap algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
            if (algorithmSuite.getAlgorithmSuiteType() != null && (algorithmSuite.getAlgorithmSuiteType().getMinimumSymmetricKeyLength() > keyLength || algorithmSuite.getAlgorithmSuiteType().getMaximumSymmetricKeyLength() < keyLength)) {
                this.setAsserted(false);
                this.setErrorMessage("Symmetric key wrap algorithm key length " + keyLength + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.Asym_Key_Wrap.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getAsymmetricKeyWrap().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Asymmetric key wrap algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
            if (algorithmSuite.getAlgorithmSuiteType() != null && (algorithmSuite.getAlgorithmSuiteType().getMinimumAsymmetricKeyLength() > keyLength || algorithmSuite.getAlgorithmSuiteType().getMaximumAsymmetricKeyLength() < keyLength)) {
                this.setAsserted(false);
                this.setErrorMessage("Asymmetric key wrap algorithm key length " + keyLength + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.COMP_KEY.equals(keyUsage)) {
            if (algorithmSuite.getComputedKey() != null && !algorithmSuite.getComputedKey().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Computed key algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.ENC_KD.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getEncryptionKeyDerivation().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Encryption key derivation algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.SIG_KD.equals(keyUsage)) {
            if (algorithmSuite.getAlgorithmSuiteType() != null && !algorithmSuite.getAlgorithmSuiteType().getSignatureKeyDerivation().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Signature key derivation algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.SigC14n.equals(keyUsage)) {
            if (algorithmSuite.getC14n() != null && !algorithmSuite.getC14n().getValue().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("C14N algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.SigTransform.equals(keyUsage)) {
            if (!(algorithmSuite.getC14n() == null || algorithmSuite.getC14n().getValue().equals(algorithmURI) || "http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithmURI) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithmURI) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(algorithmURI) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(algorithmURI))) {
                this.setAsserted(false);
                this.setErrorMessage("Transform C14N algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.SOAP_NORM.equals(keyUsage)) {
            if (algorithmSuite.getSoapNormType() != null && !algorithmSuite.getSoapNormType().getValue().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("Soap normalization algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.STR_TRANS.equals(keyUsage)) {
            if (algorithmSuite.getStrType() != null && !algorithmSuite.getStrType().getValue().equals(algorithmURI)) {
                this.setAsserted(false);
                this.setErrorMessage("STR transformation algorithm " + algorithmURI + " does not meet policy");
                this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
            }
        } else if (WSSConstants.XPATH.equals(keyUsage) && algorithmSuite.getXPathType() != null && !algorithmSuite.getXPathType().getValue().equals(algorithmURI)) {
            this.setAsserted(false);
            this.setErrorMessage("XPATH algorithm " + algorithmURI + " does not meet policy");
            this.policyAsserter.unassertPolicy(this.getAssertion(), this.getErrorMessage());
        }
        if (this.isAsserted()) {
            this.policyAsserter.assertPolicy(this.getAssertion());
            String namespace = algorithmSuite.getAlgorithmSuiteType().getNamespace();
            String name = algorithmSuite.getAlgorithmSuiteType().getName();
            this.policyAsserter.assertPolicy(new QName(namespace, name));
            if (algorithmSuite.getC14n() != null) {
                this.policyAsserter.assertPolicy(new QName(namespace, algorithmSuite.getC14n().name()));
            }
        }
        return this.isAsserted();
    }
}

