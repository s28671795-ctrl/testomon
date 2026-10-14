/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.wss4j.AlgorithmSuiteTranslater;
import org.apache.cxf.ws.security.wss4j.CryptoCoverageUtil;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JUtils;
import org.apache.cxf.ws.security.wss4j.policyvalidators.PolicyValidatorParameters;
import org.apache.cxf.ws.security.wss4j.policyvalidators.SecurityPolicyValidator;
import org.apache.cxf.ws.security.wss4j.policyvalidators.ValidatorUtils;
import org.apache.wss4j.common.crypto.Crypto;
import org.apache.wss4j.common.crypto.PasswordEncryptor;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.dom.message.token.Timestamp;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.wss4j.policy.SP12Constants;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.Wss11;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class PolicyBasedWSS4JInInterceptor
extends WSS4JInInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(PolicyBasedWSS4JInInterceptor.class);

    public PolicyBasedWSS4JInInterceptor() {
        super(true);
    }

    @Override
    public void handleMessage(SoapMessage msg) throws Fault {
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        boolean enableStax = MessageUtils.getContextualBoolean(msg, "ws-security.enable.streaming");
        if (!(aim == null || enableStax || msg.containsKey(SECURITY_PROCESSED) || this.isGET(msg) || msg.getExchange() == null)) {
            try {
                String actor = (String)this.getOption("actor");
                if (actor == null) {
                    actor = (String)msg.getContextualProperty("ws-security.actor");
                }
                if (!this.containsSecurityHeader(msg, actor, msg.getVersion().getVersion() != 1.1)) {
                    LOG.fine("The request contains no security header, so the SAAJInInterceptor is not applied");
                    this.computeAction(msg, new RequestData());
                    boolean utWithCallbacks = MessageUtils.getContextualBoolean(msg, "ws-security.validate.token", true);
                    this.doResults(msg, actor, null, null, new WSHandlerResult(actor, Collections.emptyList(), Collections.emptyMap()), utWithCallbacks);
                    msg.put(SECURITY_PROCESSED, (Object)Boolean.TRUE);
                    return;
                }
            }
            catch (WSSecurityException e) {
                throw WSS4JUtils.createSoapFault(msg, msg.getVersion(), e);
            }
            catch (XMLStreamException e) {
                throw new SoapFault(new Message("STAX_EX", LOG, new Object[0]), (Throwable)e, msg.getVersion().getSender());
            }
            catch (SOAPException e) {
                throw new SoapFault(new Message("SAAJ_EX", LOG, new Object[0]), (Throwable)e, msg.getVersion().getSender());
            }
            super.handleMessage(msg);
        }
    }

    private boolean containsSecurityHeader(SoapMessage message, String actor, boolean soap12) throws WSSecurityException {
        String actorLocal = "actor";
        String soapNamespace = "http://schemas.xmlsoap.org/soap/envelope/";
        if (soap12) {
            actorLocal = "role";
            soapNamespace = "http://www.w3.org/2003/05/soap-envelope";
        }
        for (Header h2 : message.getHeaders()) {
            Element elem;
            Attr attr;
            String hActor;
            QName n = h2.getName();
            if (!"Security".equals(n.getLocalPart()) || !n.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") && !n.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2002/04/secext") || !WSSecurityUtil.isActorEqual(actor, hActor = (attr = (elem = (Element)h2.getObject()).getAttributeNodeNS(soapNamespace, actorLocal)) != null ? attr.getValue() : null)) continue;
            return true;
        }
        return false;
    }

    private void handleWSS11(AssertionInfoMap aim, SoapMessage message) {
        if (this.isRequestor(message)) {
            message.put("enableSignatureConfirmation", (Object)"false");
            Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "Wss11");
            if (!ais.isEmpty()) {
                for (AssertionInfo ai : ais) {
                    Wss11 wss11 = (Wss11)ai.getAssertion();
                    if (!wss11.isRequireSignatureConfirmation()) continue;
                    message.put("enableSignatureConfirmation", (Object)"true");
                    break;
                }
            }
        }
    }

    private String addToAction(String action, String val, boolean pre) {
        if (action.contains(val)) {
            return action;
        }
        if (pre) {
            return val + " " + action;
        }
        return action + " " + val;
    }

    private String checkAsymmetricBinding(AssertionInfoMap aim, String action, SoapMessage message, RequestData data) throws WSSecurityException {
        String signCryptoRefId;
        Object e;
        AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(aim, "AsymmetricBinding");
        if (ai == null) {
            return action;
        }
        action = this.addToAction(action, "Signature", true);
        action = this.addToAction(action, "Encrypt", true);
        Object s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message);
        if (s2 == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, data);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, data);
        String string = signCryptoRefId = signCrypto != null ? "RefId-" + signCrypto.hashCode() : null;
        if (signCrypto != null) {
            message.put("decryptionPropRefId", (Object)signCryptoRefId);
            message.put(signCryptoRefId, (Object)signCrypto);
        }
        if (encrCrypto != null) {
            String encCryptoRefId = "RefId-" + encrCrypto.hashCode();
            message.put("signatureVerificationPropRefId", (Object)encCryptoRefId);
            message.put(encCryptoRefId, (Object)encrCrypto);
        } else if (signCrypto != null) {
            message.put("signatureVerificationPropRefId", (Object)signCryptoRefId);
            message.put(signCryptoRefId, (Object)signCrypto);
        }
        return action;
    }

    private String checkDefaultBinding(String action, SoapMessage message, RequestData data) throws WSSecurityException {
        String signCryptoRefId;
        Object e;
        action = this.addToAction(action, "Signature", true);
        action = this.addToAction(action, "Encrypt", true);
        Object s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message);
        if (s2 == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, data);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, data);
        String string = signCryptoRefId = signCrypto != null ? "RefId-" + signCrypto.hashCode() : null;
        if (signCrypto != null) {
            message.put("decryptionPropRefId", (Object)signCryptoRefId);
            message.put(signCryptoRefId, (Object)signCrypto);
        }
        if (encrCrypto != null) {
            String encCryptoRefId = "RefId-" + encrCrypto.hashCode();
            message.put("signatureVerificationPropRefId", (Object)encCryptoRefId);
            message.put(encCryptoRefId, (Object)encrCrypto);
        } else if (signCrypto != null) {
            message.put("signatureVerificationPropRefId", (Object)signCryptoRefId);
            message.put(signCryptoRefId, (Object)signCrypto);
        }
        return action;
    }

    @Override
    protected boolean isNonceCacheRequired(List<Integer> actions, SoapMessage msg) {
        AssertionInfo ai;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ai = PolicyUtils.getFirstAssertionByLocalname(aim, "UsernameToken")) != null;
    }

    @Override
    protected boolean isTimestampCacheRequired(List<Integer> actions, SoapMessage msg) {
        AssertionInfo ai;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ai = PolicyUtils.getFirstAssertionByLocalname(aim, "IncludeTimestamp")) != null;
    }

    @Override
    protected boolean isSamlCacheRequired(List<Integer> actions, SoapMessage msg) {
        AssertionInfo ai;
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        return aim != null && (ai = PolicyUtils.getFirstAssertionByLocalname(aim, "SamlToken")) != null;
    }

    private void checkUsernameToken(AssertionInfoMap aim, SoapMessage message) throws WSSecurityException {
        Collection<AssertionInfo> ais = PolicyUtils.getAllAssertionsByLocalname(aim, "UsernameToken");
        if (!ais.isEmpty()) {
            for (AssertionInfo ai : ais) {
                UsernameToken policy = (UsernameToken)ai.getAssertion();
                if (policy.getPasswordType() != UsernameToken.PasswordType.NoPassword) continue;
                message.put("allowUsernameTokenNoPassword", (Object)"true");
            }
        }
    }

    private String checkSymmetricBinding(AssertionInfoMap aim, String action, SoapMessage message, RequestData data) throws WSSecurityException {
        Object e;
        AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(aim, "SymmetricBinding");
        if (ai == null) {
            return action;
        }
        action = this.addToAction(action, "Signature", true);
        action = this.addToAction(action, "Encrypt", true);
        Object s2 = SecurityUtils.getSecurityPropertyValue("security.signature.crypto", message);
        if (s2 == null) {
            s2 = SecurityUtils.getSecurityPropertyValue("security.signature.properties", message);
        }
        if ((e = SecurityUtils.getSecurityPropertyValue("security.encryption.crypto", message)) == null) {
            e = SecurityUtils.getSecurityPropertyValue("security.encryption.properties", message);
        }
        Crypto encrCrypto = this.getEncryptionCrypto(e, message, data);
        Crypto signCrypto = null;
        signCrypto = e != null && e.equals(s2) ? encrCrypto : this.getSignatureCrypto(s2, message, data);
        if (this.isRequestor(message)) {
            String refId;
            Crypto crypto = encrCrypto;
            if (crypto == null) {
                crypto = signCrypto;
            }
            if (crypto != null) {
                refId = "RefId-" + crypto.hashCode();
                message.put("signatureVerificationPropRefId", (Object)refId);
                message.put(refId, (Object)crypto);
            }
            if ((crypto = signCrypto) == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                refId = "RefId-" + crypto.hashCode();
                message.put("decryptionPropRefId", (Object)refId);
                message.put(refId, (Object)crypto);
            }
        } else {
            String refId;
            Crypto crypto = signCrypto;
            if (crypto == null) {
                crypto = encrCrypto;
            }
            if (crypto != null) {
                refId = "RefId-" + crypto.hashCode();
                message.put("signatureVerificationPropRefId", (Object)refId);
                message.put(refId, (Object)crypto);
            }
            if ((crypto = encrCrypto) == null) {
                crypto = signCrypto;
            }
            if (crypto != null) {
                refId = "RefId-" + crypto.hashCode();
                message.put("decryptionPropRefId", (Object)refId);
                message.put(refId, (Object)crypto);
            }
        }
        return action;
    }

    private Crypto getEncryptionCrypto(Object e, SoapMessage message, RequestData requestData) throws WSSecurityException {
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(message, requestData);
        return WSS4JUtils.getEncryptionCrypto(e, message, passwordEncryptor);
    }

    private PasswordEncryptor getPasswordEncryptor(SoapMessage soapMessage, RequestData requestData) {
        PasswordEncryptor passwordEncryptor = (PasswordEncryptor)soapMessage.getContextualProperty("ws-security.password.encryptor.instance");
        if (passwordEncryptor != null) {
            return passwordEncryptor;
        }
        return super.getPasswordEncryptor(requestData);
    }

    private Crypto getSignatureCrypto(Object s2, SoapMessage message, RequestData requestData) throws WSSecurityException {
        PasswordEncryptor passwordEncryptor = this.getPasswordEncryptor(message, requestData);
        return WSS4JUtils.getSignatureCrypto(s2, message, passwordEncryptor);
    }

    @Override
    protected void setAlgorithmSuites(SoapMessage message, RequestData data) throws WSSecurityException {
        String symSignatureAlgorithm;
        AlgorithmSuiteTranslater translater = new AlgorithmSuiteTranslater();
        translater.translateAlgorithmSuites(message.get(AssertionInfoMap.class), data);
        boolean asymmAlgSet = false;
        String asymSignatureAlgorithm = (String)message.getContextualProperty("ws-security.asymmetric.signature.algorithm");
        if (asymSignatureAlgorithm != null && data.getAlgorithmSuite() != null) {
            data.getAlgorithmSuite().getSignatureMethods().clear();
            data.getAlgorithmSuite().getSignatureMethods().add(asymSignatureAlgorithm);
            asymmAlgSet = true;
        }
        if ((symSignatureAlgorithm = (String)message.getContextualProperty("ws-security.symmetric.signature.algorithm")) != null && data.getAlgorithmSuite() != null) {
            if (!asymmAlgSet) {
                data.getAlgorithmSuite().getSignatureMethods().clear();
            }
            data.getAlgorithmSuite().getSignatureMethods().add(symSignatureAlgorithm);
        }
    }

    @Override
    protected void computeAction(SoapMessage message, RequestData data) throws WSSecurityException {
        AssertionInfoMap aim;
        String action = this.getString("action", message);
        if (action == null) {
            action = "";
        }
        if ((aim = message.get(AssertionInfoMap.class)) != null) {
            Object algorithmSuites;
            this.handleWSS11(aim, message);
            action = this.checkAsymmetricBinding(aim, action, message, data);
            action = this.checkSymmetricBinding(aim, action, message, data);
            Collection<AssertionInfo> ais = (Collection<AssertionInfo>)aim.get(SP12Constants.TRANSPORT_BINDING);
            if ("".equals(action) || ais != null && !ais.isEmpty()) {
                action = this.checkDefaultBinding(action, message, data);
            }
            String asymSignatureAlgorithm = (String)message.getContextualProperty("ws-security.asymmetric.signature.algorithm");
            String symSignatureAlgorithm = (String)message.getContextualProperty("ws-security.symmetric.signature.algorithm");
            if (!(asymSignatureAlgorithm == null && symSignatureAlgorithm == null || (algorithmSuites = PolicyUtils.getAllAssertionsByLocalname(aim, "AlgorithmSuite")).isEmpty())) {
                Iterator<AssertionInfo> iterator2 = algorithmSuites.iterator();
                while (iterator2.hasNext()) {
                    AssertionInfo algorithmSuite = iterator2.next();
                    AlgorithmSuite algSuite = (AlgorithmSuite)algorithmSuite.getAssertion();
                    if (asymSignatureAlgorithm != null) {
                        algSuite.setAsymmetricSignature(asymSignatureAlgorithm);
                    }
                    if (symSignatureAlgorithm == null) continue;
                    algSuite.setSymmetricSignature(symSignatureAlgorithm);
                }
            }
            this.checkUsernameToken(aim, message);
            PolicyUtils.assertPolicy(aim, "KeyValueToken");
            PolicyUtils.assertPolicy(aim, "RsaKeyValue");
            ais = PolicyUtils.getAllAssertionsByLocalname(aim, "Wss10");
            if (!ais.isEmpty()) {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                PolicyUtils.assertPolicy(aim, "MustSupportRefKeyIdentifier");
                PolicyUtils.assertPolicy(aim, "MustSupportRefIssuerSerial");
                PolicyUtils.assertPolicy(aim, "MustSupportRefExternalURI");
                PolicyUtils.assertPolicy(aim, "MustSupportRefEmbeddedToken");
            }
            ais = PolicyUtils.getAllAssertionsByLocalname(aim, "Trust10");
            boolean trust10Asserted = false;
            if (!ais.isEmpty()) {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                PolicyUtils.assertPolicy(aim, "MustSupportClientChallenge");
                PolicyUtils.assertPolicy(aim, "MustSupportServerChallenge");
                PolicyUtils.assertPolicy(aim, "RequireClientEntropy");
                PolicyUtils.assertPolicy(aim, "RequireServerEntropy");
                PolicyUtils.assertPolicy(aim, "MustSupportIssuedTokens");
                trust10Asserted = true;
            }
            if (!(ais = PolicyUtils.getAllAssertionsByLocalname(aim, "Trust13")).isEmpty()) {
                for (AssertionInfo ai : ais) {
                    ai.setAsserted(true);
                }
                PolicyUtils.assertPolicy(aim, SP12Constants.REQUIRE_REQUEST_SECURITY_TOKEN_COLLECTION);
                PolicyUtils.assertPolicy(aim, SP12Constants.REQUIRE_APPLIES_TO);
                PolicyUtils.assertPolicy(aim, SP13Constants.SCOPE_POLICY_15);
                PolicyUtils.assertPolicy(aim, SP13Constants.MUST_SUPPORT_INTERACTIVE_CHALLENGE);
                if (!trust10Asserted) {
                    PolicyUtils.assertPolicy(aim, "MustSupportClientChallenge");
                    PolicyUtils.assertPolicy(aim, "MustSupportServerChallenge");
                    PolicyUtils.assertPolicy(aim, "RequireClientEntropy");
                    PolicyUtils.assertPolicy(aim, "RequireServerEntropy");
                    PolicyUtils.assertPolicy(aim, "MustSupportIssuedTokens");
                }
            }
            message.put("action", (Object)action.trim());
        }
    }

    @Override
    protected void doResults(SoapMessage msg, String actor, Element soapHeader, Element soapBody, WSHandlerResult results, boolean utWithCallbacks) throws SOAPException, XMLStreamException, WSSecurityException {
        ArrayList<WSSecurityEngineResult> signedResults = new ArrayList<WSSecurityEngineResult>();
        if (results.getActionResults().containsKey(2)) {
            signedResults.addAll((Collection)results.getActionResults().get(2));
        }
        if (results.getActionResults().containsKey(64)) {
            signedResults.addAll((Collection)results.getActionResults().get(64));
        }
        if (results.getActionResults().containsKey(16)) {
            signedResults.addAll((Collection)results.getActionResults().get(16));
        }
        HashSet<WSDataRef> signed = new HashSet<WSDataRef>();
        for (WSSecurityEngineResult result : signedResults) {
            List<WSDataRef> sl = CastUtils.cast((List)result.get("data-ref-uris"));
            if (sl == null) continue;
            for (WSDataRef r : sl) {
                signed.add(r);
            }
        }
        List<WSSecurityEngineResult> encryptResults = results.getActionResults().get(4);
        HashSet<WSDataRef> encrypted = new HashSet<WSDataRef>();
        if (encryptResults != null) {
            for (WSSecurityEngineResult result : encryptResults) {
                List<WSDataRef> sl = CastUtils.cast((List)result.get("data-ref-uris"));
                if (sl == null) continue;
                for (WSDataRef r : sl) {
                    encrypted.add(r);
                }
            }
        }
        CryptoCoverageUtil.reconcileEncryptedSignedRefs(signed, encrypted);
        PolicyValidatorParameters parameters = new PolicyValidatorParameters();
        AssertionInfoMap aim = msg.get(AssertionInfoMap.class);
        parameters.setAssertionInfoMap(aim);
        parameters.setMessage(msg);
        parameters.setSoapBody(soapBody);
        parameters.setSoapHeader(soapHeader);
        parameters.setResults(results);
        parameters.setSignedResults(signedResults);
        parameters.setEncryptedResults(encryptResults);
        parameters.setUtWithCallbacks(utWithCallbacks);
        parameters.setSigned(signed);
        parameters.setEncrypted(encrypted);
        ArrayList<WSSecurityEngineResult> utResults = new ArrayList<WSSecurityEngineResult>();
        if (results.getActionResults().containsKey(1)) {
            utResults.addAll((Collection)results.getActionResults().get(1));
        }
        if (results.getActionResults().containsKey(8192)) {
            utResults.addAll((Collection<WSSecurityEngineResult>)results.getActionResults().get(8192));
        }
        parameters.setUsernameTokenResults(utResults);
        ArrayList<WSSecurityEngineResult> samlResults = new ArrayList<WSSecurityEngineResult>();
        if (results.getActionResults().containsKey(16)) {
            samlResults.addAll((Collection)results.getActionResults().get(16));
        }
        if (results.getActionResults().containsKey(8)) {
            samlResults.addAll((Collection<WSSecurityEngineResult>)results.getActionResults().get(8));
        }
        parameters.setSamlResults(samlResults);
        HashMap tsResult = null;
        if (results.getActionResults().containsKey(32)) {
            tsResult = results.getActionResults().get(32).get(0);
        }
        Element timestamp = null;
        if (tsResult != null) {
            Timestamp ts = (Timestamp)tsResult.get("timestamp");
            timestamp = ts.getElement();
        }
        parameters.setTimestampElement(timestamp);
        Map<QName, SecurityPolicyValidator> validators = ValidatorUtils.getSecurityPolicyValidators(msg);
        for (Map.Entry entry : aim.entrySet()) {
            if (!validators.containsKey(entry.getKey())) continue;
            validators.get(entry.getKey()).validatePolicies(parameters, (Collection)entry.getValue());
        }
        super.doResults(msg, actor, soapHeader, soapBody, results, utWithCallbacks);
    }
}

