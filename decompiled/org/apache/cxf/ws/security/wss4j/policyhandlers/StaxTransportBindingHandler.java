/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.wss4j.TokenStoreCallbackHandler;
import org.apache.cxf.ws.security.wss4j.policyhandlers.AbstractStaxBindingHandler;
import org.apache.neethi.Assertion;
import org.apache.wss4j.policy.SP11Constants;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.Header;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.TransportBinding;
import org.apache.wss4j.policy.model.TransportToken;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.policy.model.XPath;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.xml.security.stax.ext.OutboundSecurityContext;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;

public class StaxTransportBindingHandler
extends AbstractStaxBindingHandler {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxTransportBindingHandler.class);
    private TransportBinding tbinding;

    public StaxTransportBindingHandler(WSSSecurityProperties properties, SoapMessage msg, TransportBinding tbinding, OutboundSecurityContext outboundSecurityContext) {
        super(properties, msg, tbinding, outboundSecurityContext);
        this.tbinding = tbinding;
    }

    public void handleBinding() {
        AssertionInfoMap aim = this.getMessage().get(AssertionInfoMap.class);
        this.configureTimestamp(aim);
        if (this.isRequestor()) {
            if (this.tbinding != null) {
                TransportToken token;
                String symSignatureAlgorithm;
                this.assertPolicy(this.tbinding.getName());
                String asymSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.asymmetric.signature.algorithm");
                if (asymSignatureAlgorithm != null && this.tbinding.getAlgorithmSuite() != null) {
                    this.tbinding.getAlgorithmSuite().setAsymmetricSignature(asymSignatureAlgorithm);
                }
                if ((symSignatureAlgorithm = (String)this.getMessage().getContextualProperty("ws-security.symmetric.signature.algorithm")) != null && this.tbinding.getAlgorithmSuite() != null) {
                    this.tbinding.getAlgorithmSuite().setSymmetricSignature(symSignatureAlgorithm);
                }
                if ((token = this.tbinding.getTransportToken()).getToken() instanceof IssuedToken) {
                    SecurityToken secToken = this.getSecurityToken();
                    if (secToken == null) {
                        this.unassertPolicy((Assertion)token.getToken(), "No transport token id");
                        return;
                    }
                    this.addIssuedToken(token.getToken(), secToken, false, false);
                }
                this.assertToken(token.getToken());
                this.assertTokenWrapper(token);
            }
            try {
                this.handleNonEndorsingSupportingTokens(aim);
                this.handleEndorsingSupportingTokens(aim);
            }
            catch (Exception e) {
                LOG.log(Level.FINE, e.getMessage(), e);
                throw new Fault(e);
            }
        }
        try {
            this.handleNonEndorsingSupportingTokens(aim);
        }
        catch (Exception e) {
            LOG.log(Level.FINE, e.getMessage(), e);
            throw new Fault(e);
        }
        if (this.tbinding != null) {
            this.assertPolicy(this.tbinding.getName());
            if (this.tbinding.getTransportToken() != null) {
                this.assertTokenWrapper(this.tbinding.getTransportToken());
                this.assertToken(this.tbinding.getTransportToken().getToken());
                try {
                    this.handleEndorsingSupportingTokens(aim);
                }
                catch (Exception e) {
                    LOG.log(Level.FINE, e.getMessage(), e);
                    throw new Fault(e);
                }
            }
        }
        this.addSignatureConfirmation(null);
        this.configureLayout(aim);
        if (this.tbinding != null) {
            this.assertAlgorithmSuite(this.tbinding.getAlgorithmSuite());
            this.assertWSSProperties(this.tbinding.getName().getNamespaceURI());
            this.assertTrustProperties(this.tbinding.getName().getNamespaceURI());
        }
        this.assertPolicy(SP12Constants.SIGNED_PARTS);
        this.assertPolicy(SP11Constants.SIGNED_PARTS);
        this.assertPolicy(SP12Constants.ENCRYPTED_PARTS);
        this.assertPolicy(SP11Constants.ENCRYPTED_PARTS);
        this.putCustomTokenAfterSignature();
    }

    private void handleNonEndorsingSupportingTokens(AssertionInfoMap aim) throws Exception {
        SupportingTokens sgndSuppTokens;
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SignedSupportingTokens");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                if (sgndSuppTokens != null) {
                    this.addSignedSupportingTokens(sgndSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SignedEncryptedSupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                if (sgndSuppTokens != null) {
                    this.addSignedSupportingTokens(sgndSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "EncryptedSupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                SupportingTokens encrSuppTokens = (SupportingTokens)ai.getAssertion();
                if (encrSuppTokens != null) {
                    this.addSignedSupportingTokens(encrSuppTokens);
                }
                ai.setAsserted(true);
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SupportingTokens")).isEmpty()) {
            for (AssertionInfo ai : ais) {
                SupportingTokens suppTokens = (SupportingTokens)ai.getAssertion();
                if (suppTokens != null && suppTokens.getTokens() != null && suppTokens.getTokens().size() > 0) {
                    this.handleSupportingTokens(suppTokens, false, false);
                }
                ai.setAsserted(true);
            }
        }
    }

    private void addSignedSupportingTokens(SupportingTokens sgndSuppTokens) throws Exception {
        for (AbstractToken token : sgndSuppTokens.getTokens()) {
            this.assertToken(token);
            if (token != null && !this.isTokenRequired(token.getIncludeTokenType())) continue;
            if (token instanceof UsernameToken) {
                this.addUsernameToken((UsernameToken)token);
                continue;
            }
            if (token instanceof IssuedToken) {
                this.addIssuedToken(token, this.getSecurityToken(), false, false);
                continue;
            }
            if (token instanceof KerberosToken) {
                this.addKerberosToken((KerberosToken)token, false, false, false);
                continue;
            }
            if (token instanceof SamlToken) {
                this.addSamlToken((SamlToken)token, false, false);
                continue;
            }
            if (token != null) {
                throw new Exception(token.getName() + " is not supported in the streaming code");
            }
            throw new Exception("A null token was supplied to the streaming code");
        }
    }

    private void handleEndorsingSupportingTokens(AssertionInfoMap aim) throws Exception {
        SupportingTokens endSuppTokens;
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SignedEndorsingSupportingTokens");
        if (!ais.isEmpty()) {
            SupportingTokens sgndSuppTokens = null;
            for (AssertionInfo ai : ais) {
                sgndSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (sgndSuppTokens != null) {
                for (AbstractToken token : sgndSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, sgndSuppTokens);
                }
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "EndorsingSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "EndorsingEncryptedSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
        if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "SignedEndorsingEncryptedSupportingTokens")).isEmpty()) {
            endSuppTokens = null;
            for (AssertionInfo ai : ais) {
                endSuppTokens = (SupportingTokens)ai.getAssertion();
                ai.setAsserted(true);
            }
            if (endSuppTokens != null) {
                for (AbstractToken token : endSuppTokens.getTokens()) {
                    this.handleEndorsingToken(token, endSuppTokens);
                }
            }
        }
    }

    private void handleEndorsingToken(AbstractToken token, SupportingTokens wrapper) throws Exception {
        this.assertToken(token);
        if (token != null && !this.isTokenRequired(token.getIncludeTokenType())) {
            return;
        }
        if (token instanceof IssuedToken) {
            SecurityToken securityToken = this.getSecurityToken();
            this.addIssuedToken(token, securityToken, false, true);
            this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
            WSSSecurityProperties properties = this.getProperties();
            if (securityToken != null && securityToken.getSecret() != null) {
                properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getSymmetricSignature());
            } else {
                properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getAsymmetricSignature());
            }
            properties.setSignatureCanonicalizationAlgorithm(this.tbinding.getAlgorithmSuite().getC14n().getValue());
            AlgorithmSuite.AlgorithmSuiteType algType = this.tbinding.getAlgorithmSuite().getAlgorithmSuiteType();
            properties.setSignatureDigestAlgorithm(algType.getDigest());
        } else if (token instanceof SecureConversationToken || token instanceof SecurityContextToken || token instanceof SpnegoContextToken) {
            SecurityToken securityToken = this.getSecurityToken();
            this.addIssuedToken(token, securityToken, false, true);
            WSSSecurityProperties properties = this.getProperties();
            if (securityToken != null) {
                this.storeSecurityToken(token, securityToken);
                TokenStoreCallbackHandler callbackHandler = new TokenStoreCallbackHandler(properties.getCallbackHandler(), TokenStoreUtils.getTokenStore(this.message));
                properties.setCallbackHandler(callbackHandler);
            }
            this.doSignature(token, wrapper);
            properties.setIncludeSignatureToken(true);
            properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getSymmetricSignature());
            properties.setSignatureCanonicalizationAlgorithm(this.tbinding.getAlgorithmSuite().getC14n().getValue());
            AlgorithmSuite.AlgorithmSuiteType algType = this.tbinding.getAlgorithmSuite().getAlgorithmSuiteType();
            properties.setSignatureDigestAlgorithm(algType.getDigest());
        } else if (token instanceof X509Token || token instanceof KeyValueToken) {
            this.doSignature(token, wrapper);
        } else if (token instanceof SamlToken) {
            this.addSamlToken((SamlToken)token, false, true);
            this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
            WSSSecurityProperties properties = this.getProperties();
            properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getAsymmetricSignature());
            properties.setSignatureCanonicalizationAlgorithm(this.tbinding.getAlgorithmSuite().getC14n().getValue());
            AlgorithmSuite.AlgorithmSuiteType algType = this.tbinding.getAlgorithmSuite().getAlgorithmSuiteType();
            properties.setSignatureDigestAlgorithm(algType.getDigest());
        } else {
            if (token instanceof UsernameToken) {
                throw new Exception("Endorsing UsernameTokens are not supported in the streaming code");
            }
            if (token instanceof KerberosToken) {
                WSSSecurityProperties properties = this.getProperties();
                properties.addAction(XMLSecurityConstants.SIGNATURE);
                this.configureSignature(token, false);
                this.addKerberosToken((KerberosToken)token, false, true, false);
                this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
                properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getSymmetricSignature());
                properties.setSignatureCanonicalizationAlgorithm(this.tbinding.getAlgorithmSuite().getC14n().getValue());
                AlgorithmSuite.AlgorithmSuiteType algType = this.tbinding.getAlgorithmSuite().getAlgorithmSuiteType();
                properties.setSignatureDigestAlgorithm(algType.getDigest());
            }
        }
    }

    private void doSignature(AbstractToken token, SupportingTokens wrapper) throws Exception {
        this.signPartsAndElements(wrapper.getSignedParts(), wrapper.getSignedElements());
        WSSSecurityProperties properties = this.getProperties();
        XMLSecurityConstants.Action actionToPerform = XMLSecurityConstants.SIGNATURE;
        if (token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            actionToPerform = WSSConstants.SIGNATURE_WITH_DERIVED_KEY;
        }
        properties.addAction(actionToPerform);
        this.configureSignature(token, false);
        if (token.getDerivedKeys() == AbstractToken.DerivedKeys.RequireDerivedKeys) {
            properties.setSignatureAlgorithm(this.tbinding.getAlgorithmSuite().getSymmetricSignature());
        }
    }

    private void signPartsAndElements(SignedParts signedParts, SignedElements signedElements) throws SOAPException {
        SecurePart part;
        WSSSecurityProperties properties = this.getProperties();
        List<SecurePart> signatureParts = properties.getSignatureSecureParts();
        if (this.timestampAdded) {
            part = new SecurePart(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Timestamp"), SecurePart.Modifier.Element);
            signatureParts.add(part);
        }
        if (signedParts != null) {
            if (signedParts.isBody()) {
                part = new SecurePart(new QName("http://schemas.xmlsoap.org/soap/envelope/", "Body"), SecurePart.Modifier.Element);
                signatureParts.add(part);
            }
            for (Header head : signedParts.getHeaders()) {
                SecurePart part2 = new SecurePart(new QName(head.getNamespace(), head.getName()), SecurePart.Modifier.Element);
                part2.setRequired(false);
                signatureParts.add(part2);
            }
        }
        if (signedElements != null && signedElements.getXPaths() != null) {
            for (XPath xPath : signedElements.getXPaths()) {
                List<QName> qnames = org.apache.wss4j.policy.stax.PolicyUtils.getElementPath(xPath);
                if (qnames.isEmpty()) continue;
                SecurePart part3 = new SecurePart(qnames.get(qnames.size() - 1), SecurePart.Modifier.Element);
                signatureParts.add(part3);
            }
        }
    }
}

