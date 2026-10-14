/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j.policyhandlers;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.ws.policy.AssertionInfo;
import org.apache.cxf.ws.policy.AssertionInfoMap;
import org.apache.cxf.ws.policy.PolicyException;
import org.apache.cxf.ws.security.policy.PolicyUtils;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.neethi.Assertion;
import org.apache.wss4j.policy.SP13Constants;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AbstractTokenWrapper;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.HttpsToken;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.Trust10;
import org.apache.wss4j.policy.model.Trust13;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.Wss10;
import org.apache.wss4j.policy.model.Wss11;
import org.apache.wss4j.policy.model.X509Token;

public abstract class AbstractCommonBindingHandler {
    private static final Logger LOG = LogUtils.getL7dLogger(AbstractCommonBindingHandler.class);
    protected final SoapMessage message;

    public AbstractCommonBindingHandler(SoapMessage msg) {
        this.message = msg;
    }

    protected void unassertPolicy(Assertion assertion, String reason) {
        AssertionInfoMap aim;
        Collection ais;
        if (assertion == null) {
            return;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Not asserting " + assertion.getName() + ": " + reason);
        }
        if ((ais = (Collection)(aim = this.message.get(AssertionInfoMap.class)).get(assertion.getName())) != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setNotAsserted(reason);
            }
        }
        if (!assertion.isOptional()) {
            throw new PolicyException(new Message(reason, LOG, new Object[0]));
        }
    }

    protected void unassertPolicy(Assertion assertion, Exception reason) {
        AssertionInfoMap aim;
        Collection ais;
        if (assertion == null) {
            return;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Not asserting " + assertion.getName() + ": " + reason);
        }
        if ((ais = (Collection)(aim = this.message.get(AssertionInfoMap.class)).get(assertion.getName())) != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setNotAsserted(reason.getMessage());
            }
        }
        if (!assertion.isOptional()) {
            throw new PolicyException(new Message(reason.getMessage(), LOG, new Object[0]), (Throwable)reason);
        }
    }

    protected void assertTokenWrapper(AbstractTokenWrapper tokenWrapper) {
        if (tokenWrapper == null) {
            return;
        }
        this.assertPolicy(tokenWrapper.getName());
        this.assertToken(tokenWrapper.getToken());
    }

    protected void assertToken(AbstractToken token) {
        if (token == null) {
            return;
        }
        this.assertPolicy(token.getName());
        String namespace = token.getName().getNamespaceURI();
        if (token.getDerivedKeys() != null) {
            this.assertPolicy(new QName(namespace, token.getDerivedKeys().name()));
        }
        if (token instanceof X509Token) {
            X509Token x509Token = (X509Token)token;
            this.assertX509Token(x509Token);
        } else if (token instanceof HttpsToken) {
            HttpsToken httpsToken = (HttpsToken)token;
            if (httpsToken.getAuthenticationType() != null) {
                this.assertPolicy(new QName(namespace, httpsToken.getAuthenticationType().name()));
            }
        } else if (token instanceof KeyValueToken) {
            KeyValueToken keyValueToken = (KeyValueToken)token;
            if (keyValueToken.isRsaKeyValue()) {
                this.assertPolicy(new QName(namespace, "RsaKeyValue"));
            }
        } else if (token instanceof UsernameToken) {
            UsernameToken usernameToken = (UsernameToken)token;
            this.assertUsernameToken(usernameToken);
        } else if (token instanceof SecureConversationToken) {
            SecureConversationToken scToken = (SecureConversationToken)token;
            this.assertSecureConversationToken(scToken);
        } else if (token instanceof SecurityContextToken) {
            SecurityContextToken scToken = (SecurityContextToken)token;
            this.assertSecurityContextToken(scToken);
        } else if (token instanceof SpnegoContextToken) {
            SpnegoContextToken scToken = (SpnegoContextToken)token;
            this.assertSpnegoContextToken(scToken);
        } else if (token instanceof IssuedToken) {
            IssuedToken issuedToken = (IssuedToken)token;
            this.assertIssuedToken(issuedToken);
        } else if (token instanceof KerberosToken) {
            KerberosToken kerberosToken = (KerberosToken)token;
            this.assertKerberosToken(kerberosToken);
        } else if (token instanceof SamlToken) {
            SamlToken samlToken = (SamlToken)token;
            this.assertSamlToken(samlToken);
        }
    }

    private void assertX509Token(X509Token token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireEmbeddedTokenReference()) {
            this.assertPolicy(new QName(namespace, "RequireEmbeddedTokenReference"));
        }
        if (token.isRequireIssuerSerialReference()) {
            this.assertPolicy(new QName(namespace, "RequireIssuerSerialReference"));
        }
        if (token.isRequireKeyIdentifierReference()) {
            this.assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if (token.isRequireThumbprintReference()) {
            this.assertPolicy(new QName(namespace, "RequireThumbprintReference"));
        }
        if (token.getTokenType() != null) {
            this.assertPolicy(new QName(namespace, token.getTokenType().name()));
        }
    }

    private void assertUsernameToken(UsernameToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.getPasswordType() != null) {
            this.assertPolicy(new QName(namespace, token.getPasswordType().name()));
        }
        if (token.getUsernameTokenType() != null) {
            this.assertPolicy(new QName(namespace, token.getUsernameTokenType().name()));
        }
        if (token.isCreated()) {
            this.assertPolicy(SP13Constants.CREATED);
        }
        if (token.isNonce()) {
            this.assertPolicy(SP13Constants.NONCE);
        }
    }

    private void assertSecurityContextToken(SecurityContextToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireExternalUriReference()) {
            this.assertPolicy(new QName(namespace, "RequireExternalUriReference"));
        }
        if (token.isSc10SecurityContextToken()) {
            this.assertPolicy(new QName(namespace, "SC10SecurityContextToken"));
        }
        if (token.isSc13SecurityContextToken()) {
            this.assertPolicy(new QName(namespace, "SC13SecurityContextToken"));
        }
    }

    private void assertSecureConversationToken(SecureConversationToken token) {
        this.assertSecurityContextToken(token);
        String namespace = token.getName().getNamespaceURI();
        if (token.isMustNotSendAmend()) {
            this.assertPolicy(new QName(namespace, "MustNotSendAmend"));
        }
        if (token.isMustNotSendCancel()) {
            this.assertPolicy(new QName(namespace, "MustNotSendCancel"));
        }
        if (token.isMustNotSendRenew()) {
            this.assertPolicy(new QName(namespace, "MustNotSendRenew"));
        }
    }

    private void assertSpnegoContextToken(SpnegoContextToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isMustNotSendAmend()) {
            this.assertPolicy(new QName(namespace, "MustNotSendAmend"));
        }
        if (token.isMustNotSendCancel()) {
            this.assertPolicy(new QName(namespace, "MustNotSendCancel"));
        }
        if (token.isMustNotSendRenew()) {
            this.assertPolicy(new QName(namespace, "MustNotSendRenew"));
        }
    }

    private void assertIssuedToken(IssuedToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireExternalReference()) {
            this.assertPolicy(new QName(namespace, "RequireExternalReference"));
        }
        if (token.isRequireInternalReference()) {
            this.assertPolicy(new QName(namespace, "RequireInternalReference"));
        }
    }

    private void assertKerberosToken(KerberosToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireKeyIdentifierReference()) {
            this.assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if (token.getApReqTokenType() != null) {
            this.assertPolicy(new QName(namespace, token.getApReqTokenType().name()));
        }
    }

    private void assertSamlToken(SamlToken token) {
        String namespace = token.getName().getNamespaceURI();
        if (token.isRequireKeyIdentifierReference()) {
            this.assertPolicy(new QName(namespace, "RequireKeyIdentifierReference"));
        }
        if (token.getSamlTokenType() != null) {
            this.assertPolicy(new QName(namespace, token.getSamlTokenType().name()));
        }
    }

    protected void assertAlgorithmSuite(AlgorithmSuite algorithmSuite) {
        Collection algAis;
        if (algorithmSuite == null) {
            return;
        }
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        Collection algorithmSuiteAis = (Collection)aim.get(algorithmSuite.getName());
        for (AssertionInfo ai : algorithmSuiteAis) {
            ai.setAsserted(true);
        }
        AlgorithmSuite.AlgorithmSuiteType algorithmSuiteType = algorithmSuite.getAlgorithmSuiteType();
        String namespace = algorithmSuiteType.getNamespace();
        if (namespace != null && (algAis = (Collection)aim.get(new QName(namespace, algorithmSuiteType.getName()))) != null) {
            for (AssertionInfo algAi : algAis) {
                algAi.setAsserted(true);
            }
        }
    }

    protected void assertWSSProperties(String namespace) {
        Collection wss11Ais;
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        Collection wss10Ais = (Collection)aim.get(new QName(namespace, "Wss10"));
        if (wss10Ais != null) {
            for (AssertionInfo ai : wss10Ais) {
                ai.setAsserted(true);
                Wss10 wss10 = (Wss10)ai.getAssertion();
                this.assertWSS10Properties(wss10);
            }
        }
        if ((wss11Ais = (Collection)aim.get(new QName(namespace, "Wss11"))) != null) {
            for (AssertionInfo ai : wss11Ais) {
                ai.setAsserted(true);
                Wss11 wss11 = (Wss11)ai.getAssertion();
                this.assertWSS10Properties(wss11);
                if (wss11.isMustSupportRefThumbprint()) {
                    this.assertPolicy(new QName(namespace, "MustSupportRefThumbprint"));
                }
                if (wss11.isMustSupportRefEncryptedKey()) {
                    this.assertPolicy(new QName(namespace, "MustSupportRefEncryptedKey"));
                }
                if (!wss11.isRequireSignatureConfirmation()) continue;
                this.assertPolicy(new QName(namespace, "RequireSignatureConfirmation"));
            }
        }
    }

    private void assertWSS10Properties(Wss10 wss10) {
        String namespace = wss10.getName().getNamespaceURI();
        if (wss10.isMustSupportRefEmbeddedToken()) {
            this.assertPolicy(new QName(namespace, "MustSupportRefEmbeddedToken"));
        }
        if (wss10.isMustSupportRefKeyIdentifier()) {
            this.assertPolicy(new QName(namespace, "MustSupportRefKeyIdentifier"));
        }
        if (wss10.isMustSupportRefIssuerSerial()) {
            this.assertPolicy(new QName(namespace, "MustSupportRefIssuerSerial"));
        }
        if (wss10.isMustSupportRefExternalURI()) {
            this.assertPolicy(new QName(namespace, "MustSupportRefExternalURI"));
        }
    }

    protected void assertTrustProperties(String namespace) {
        Collection trust13Ais;
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        Collection trust10Ais = (Collection)aim.get(new QName(namespace, "Trust10"));
        if (trust10Ais != null) {
            for (AssertionInfo ai : trust10Ais) {
                ai.setAsserted(true);
                Trust10 trust10 = (Trust10)ai.getAssertion();
                this.assertTrust10Properties(trust10);
            }
        }
        if ((trust13Ais = (Collection)aim.get(new QName(namespace, "Trust13"))) != null) {
            for (AssertionInfo ai : trust13Ais) {
                ai.setAsserted(true);
                Trust13 trust13 = (Trust13)ai.getAssertion();
                this.assertTrust10Properties(trust13);
                if (trust13.isRequireRequestSecurityTokenCollection()) {
                    this.assertPolicy(new QName(namespace, "RequireRequestSecurityTokenCollection"));
                }
                if (trust13.isRequireAppliesTo()) {
                    this.assertPolicy(new QName(namespace, "RequireAppliesTo"));
                }
                if (trust13.isScopePolicy15()) {
                    this.assertPolicy(new QName(namespace, "ScopePolicy15"));
                }
                if (!trust13.isMustSupportInteractiveChallenge()) continue;
                this.assertPolicy(new QName(namespace, "MustSupportInteractiveChallenge"));
            }
        }
    }

    private void assertTrust10Properties(Trust10 trust10) {
        String namespace = trust10.getName().getNamespaceURI();
        if (trust10.isMustSupportClientChallenge()) {
            this.assertPolicy(new QName(namespace, "MustSupportClientChallenge"));
        }
        if (trust10.isMustSupportIssuedTokens()) {
            this.assertPolicy(new QName(namespace, "MustSupportIssuedTokens"));
        }
        if (trust10.isMustSupportServerChallenge()) {
            this.assertPolicy(new QName(namespace, "MustSupportServerChallenge"));
        }
        if (trust10.isRequireClientEntropy()) {
            this.assertPolicy(new QName(namespace, "RequireClientEntropy"));
        }
        if (trust10.isRequireServerEntropy()) {
            this.assertPolicy(new QName(namespace, "RequireServerEntropy"));
        }
    }

    protected Collection<AssertionInfo> getAllAssertionsByLocalname(String localname) {
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        return PolicyUtils.getAllAssertionsByLocalname(aim, localname);
    }

    protected SoapMessage getMessage() {
        return this.message;
    }

    protected boolean isRequestor() {
        return MessageUtils.isRequestor(this.message);
    }

    protected boolean isTokenRequired(SPConstants.IncludeTokenType includeToken) {
        if (includeToken == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
            return false;
        }
        if (includeToken == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS) {
            return true;
        }
        boolean initiator = MessageUtils.isRequestor(this.message);
        if (initiator && (includeToken == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT || includeToken == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ONCE)) {
            return true;
        }
        return !initiator && includeToken == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR;
    }

    protected Wss10 getWss10() {
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        AssertionInfo ai = PolicyUtils.getFirstAssertionByLocalname(aim, "Wss10");
        if (ai == null) {
            ai = PolicyUtils.getFirstAssertionByLocalname(aim, "Wss11");
        }
        if (ai != null) {
            return (Wss10)ai.getAssertion();
        }
        return null;
    }

    protected SecurityToken getSecurityToken() {
        String id;
        SecurityToken st = (SecurityToken)this.message.getContextualProperty("ws-security.token");
        if (st == null && (id = (String)this.message.getContextualProperty("ws-security.token.id")) != null) {
            st = TokenStoreUtils.getTokenStore(this.message).getToken(id);
        }
        return st;
    }

    protected void assertPolicy(QName name) {
        AssertionInfoMap aim = this.message.get(AssertionInfoMap.class);
        PolicyUtils.assertPolicy(aim, name);
    }

    protected void assertPolicy(Assertion assertion) {
        AssertionInfoMap aim;
        Collection ais;
        if (assertion == null) {
            return;
        }
        if (LOG.isLoggable(Level.FINE)) {
            LOG.log(Level.FINE, "Asserting " + assertion.getName());
        }
        if ((ais = (Collection)(aim = this.message.get(AssertionInfoMap.class)).get(assertion.getName())) != null) {
            for (AssertionInfo ai : ais) {
                if (ai.getAssertion() != assertion) continue;
                ai.setAsserted(true);
            }
        }
    }
}

