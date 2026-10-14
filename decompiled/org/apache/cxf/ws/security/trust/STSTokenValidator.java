/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreUtils;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSSamlAssertionValidator;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.cxf.ws.security.trust.delegation.DelegationCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.validate.Credential;
import org.apache.wss4j.dom.validate.Validator;
import org.w3c.dom.Element;

public class STSTokenValidator
implements Validator {
    private STSSamlAssertionValidator samlValidator = new STSSamlAssertionValidator();
    private boolean alwaysValidateToSts;
    private boolean useIssueBinding;
    private boolean useOnBehalfOf = true;
    private STSClient stsClient;
    private TokenStore tokenStore;
    private boolean disableCaching;

    public STSTokenValidator() {
    }

    public STSTokenValidator(boolean alwaysValidateToSts) {
        this.alwaysValidateToSts = alwaysValidateToSts;
    }

    @Override
    public Credential validate(Credential credential, RequestData data) throws WSSecurityException {
        if (this.isValidatedLocally(credential, data)) {
            return credential;
        }
        return this.validateWithSTS(credential, (Message)data.getMsgContext());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Credential validateWithSTS(Credential credential, Message message) throws WSSecurityException {
        try {
            SecurityToken token = new SecurityToken();
            Element tokenElement = null;
            int hash = 0;
            if (credential.getSamlAssertion() != null) {
                SamlAssertionWrapper assertion = credential.getSamlAssertion();
                byte[] signatureValue = assertion.getSignatureValue();
                if (signatureValue != null && signatureValue.length > 0) {
                    hash = Arrays.hashCode(signatureValue);
                }
                tokenElement = credential.getSamlAssertion().getElement();
            } else if (credential.getUsernametoken() != null) {
                tokenElement = credential.getUsernametoken().getElement();
                hash = credential.getUsernametoken().hashCode();
            } else if (credential.getBinarySecurityToken() != null) {
                tokenElement = credential.getBinarySecurityToken().getElement();
                hash = credential.getBinarySecurityToken().hashCode();
            } else if (credential.getSecurityContextToken() != null) {
                tokenElement = credential.getSecurityContextToken().getElement();
                hash = credential.getSecurityContextToken().hashCode();
            }
            token.setToken(tokenElement);
            TokenStore ts = null;
            if (!this.disableCaching) {
                SecurityToken transformedToken;
                ts = STSTokenValidator.getTokenStore(message);
                if (ts == null) {
                    ts = this.tokenStore;
                }
                if (ts != null && hash != 0 && (transformedToken = this.getTransformedToken(ts, hash)) != null && !transformedToken.isExpired()) {
                    SamlAssertionWrapper assertion = new SamlAssertionWrapper(transformedToken.getToken());
                    credential.setPrincipal(new SAMLTokenPrincipalImpl(assertion));
                    credential.setTransformedToken(assertion);
                    return credential;
                }
            }
            token.setTokenHash(hash);
            STSClient c = this.stsClient;
            if (c == null) {
                c = STSUtils.getClient(message, "sts");
            }
            STSClient sTSClient = c;
            synchronized (sTSClient) {
                System.setProperty("noprint", "true");
                SecurityToken returnedToken = null;
                if (this.useIssueBinding && this.useOnBehalfOf) {
                    ElementCallbackHandler callbackHandler = new ElementCallbackHandler(tokenElement);
                    c.setOnBehalfOf(callbackHandler);
                    returnedToken = c.requestSecurityToken();
                    c.setOnBehalfOf(null);
                } else if (this.useIssueBinding && !this.useOnBehalfOf && credential.getUsernametoken() != null) {
                    c.getProperties().put("security.username", credential.getUsernametoken().getName());
                    c.getProperties().put("security.password", credential.getUsernametoken().getPassword());
                    returnedToken = c.requestSecurityToken();
                    c.getProperties().remove("security.username");
                    c.getProperties().remove("security.password");
                } else {
                    List<SecurityToken> tokens = c.validateSecurityToken(token);
                    returnedToken = tokens.get(0);
                }
                if (returnedToken != token) {
                    SamlAssertionWrapper assertion = new SamlAssertionWrapper(returnedToken.getToken());
                    credential.setTransformedToken(assertion);
                    credential.setPrincipal(new SAMLTokenPrincipalImpl(assertion));
                    if (!this.disableCaching && hash != 0 && ts != null) {
                        ts.add(returnedToken);
                        token.setTransformedTokenIdentifier(returnedToken.getId());
                        ts.add(Integer.toString(hash), token);
                    }
                }
                return credential;
            }
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "invalidSAMLsecurity");
        }
    }

    static final TokenStore getTokenStore(Message message) {
        if (message == null) {
            return null;
        }
        return TokenStoreUtils.getTokenStore(message);
    }

    protected boolean isValidatedLocally(Credential credential, RequestData data) throws WSSecurityException {
        if (!this.alwaysValidateToSts && credential.getSamlAssertion() != null) {
            try {
                this.samlValidator.validate(credential, data);
                return this.samlValidator.isTrustVerificationSucceeded();
            }
            catch (RuntimeException e) {
                throw e;
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "invalidSAMLsecurity");
            }
        }
        return false;
    }

    private SecurityToken getTransformedToken(TokenStore ts, int hash) {
        String transformedTokenId;
        SecurityToken recoveredToken = ts.getToken(Integer.toString(hash));
        if (recoveredToken != null && recoveredToken.getTokenHash() == hash && (transformedTokenId = recoveredToken.getTransformedTokenIdentifier()) != null) {
            return ts.getToken(transformedTokenId);
        }
        return null;
    }

    public boolean isUseIssueBinding() {
        return this.useIssueBinding;
    }

    public void setUseIssueBinding(boolean useIssueBinding) {
        this.useIssueBinding = useIssueBinding;
    }

    public boolean isUseOnBehalfOf() {
        return this.useOnBehalfOf;
    }

    public void setUseOnBehalfOf(boolean useOnBehalfOf) {
        this.useOnBehalfOf = useOnBehalfOf;
    }

    public STSClient getStsClient() {
        return this.stsClient;
    }

    public void setStsClient(STSClient stsClient) {
        this.stsClient = stsClient;
    }

    public TokenStore getTokenStore() {
        return this.tokenStore;
    }

    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public boolean isDisableCaching() {
        return this.disableCaching;
    }

    public void setDisableCaching(boolean disableCaching) {
        this.disableCaching = disableCaching;
    }

    private static class ElementCallbackHandler
    implements CallbackHandler {
        private final Element tokenElement;

        ElementCallbackHandler(Element tokenElement) {
            this.tokenElement = tokenElement;
        }

        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (int i = 0; i < callbacks.length; ++i) {
                if (!(callbacks[i] instanceof DelegationCallback)) {
                    throw new UnsupportedCallbackException(callbacks[i], "Unrecognized Callback");
                }
                DelegationCallback callback = (DelegationCallback)callbacks[i];
                callback.setToken(this.tokenElement);
            }
        }
    }
}

