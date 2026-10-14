/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.assertionStates;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.policy.AssertionState;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.EncryptionToken;
import org.apache.wss4j.policy.model.InitiatorEncryptionToken;
import org.apache.wss4j.policy.model.InitiatorSignatureToken;
import org.apache.wss4j.policy.model.InitiatorToken;
import org.apache.wss4j.policy.model.ProtectionToken;
import org.apache.wss4j.policy.model.RecipientEncryptionToken;
import org.apache.wss4j.policy.model.RecipientSignatureToken;
import org.apache.wss4j.policy.model.RecipientToken;
import org.apache.wss4j.policy.model.SignatureToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.TransportToken;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public abstract class TokenAssertionState
extends AssertionState
implements Assertable {
    private boolean initiator;
    private PolicyAsserter policyAsserter;

    public TokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, boolean initiator) {
        this(assertion, asserted, null, initiator);
    }

    public TokenAssertionState(AbstractSecurityAssertion assertion, boolean asserted, PolicyAsserter policyAsserter, boolean initiator) {
        super(assertion, asserted);
        this.initiator = initiator;
        this.policyAsserter = policyAsserter;
        if (this.policyAsserter == null) {
            this.policyAsserter = new DummyPolicyAsserter();
        }
        if (asserted) {
            AbstractToken token = (AbstractToken)this.getAssertion();
            this.getPolicyAsserter().assertPolicy(token);
            if (token.getDerivedKeys() != null) {
                AbstractToken.DerivedKeys derivedKeys = token.getDerivedKeys();
                String namespace = token.getName().getNamespaceURI();
                this.getPolicyAsserter().assertPolicy(new QName(namespace, derivedKeys.name()));
            }
        }
    }

    @Override
    public boolean assertEvent(SecurityEvent securityEvent) throws WSSPolicyException, XMLSecurityException {
        if (this.isAsserted()) {
            return true;
        }
        TokenSecurityEvent tokenSecurityEvent = (TokenSecurityEvent)securityEvent;
        AbstractToken abstractToken = (AbstractToken)this.getAssertion();
        AbstractSecurityAssertion parentAssertion = abstractToken.getParentAssertion();
        int ignoreToken = 0;
        List<SecurityTokenConstants.TokenUsage> tokenUsages = tokenSecurityEvent.getSecurityToken().getTokenUsages();
        for (SecurityTokenConstants.TokenUsage tokenUsage : tokenUsages) {
            String supportingTokensName;
            if (WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE.equals(tokenUsage)) {
                if (!(!this.initiator || parentAssertion instanceof RecipientToken || parentAssertion instanceof RecipientSignatureToken || parentAssertion instanceof SignatureToken || parentAssertion instanceof ProtectionToken || parentAssertion instanceof TransportToken)) {
                    ++ignoreToken;
                    continue;
                }
                if (this.initiator || parentAssertion instanceof InitiatorToken || parentAssertion instanceof InitiatorSignatureToken || parentAssertion instanceof SignatureToken || parentAssertion instanceof ProtectionToken || parentAssertion instanceof TransportToken) continue;
                ++ignoreToken;
                continue;
            }
            if (WSSecurityTokenConstants.TokenUsage_Signature.equals(tokenUsage)) {
                throw new WSSPolicyException("Illegal token usage!");
            }
            if (WSSecurityTokenConstants.TOKENUSAGE_MAIN_ENCRYPTION.equals(tokenUsage)) {
                if (!(!this.initiator || parentAssertion instanceof InitiatorToken || parentAssertion instanceof InitiatorEncryptionToken || parentAssertion instanceof EncryptionToken || parentAssertion instanceof ProtectionToken || parentAssertion instanceof TransportToken)) {
                    ++ignoreToken;
                    continue;
                }
                if (this.initiator || parentAssertion instanceof RecipientToken || parentAssertion instanceof RecipientEncryptionToken || parentAssertion instanceof EncryptionToken || parentAssertion instanceof ProtectionToken || parentAssertion instanceof TransportToken) continue;
                ++ignoreToken;
                continue;
            }
            if (WSSecurityTokenConstants.TokenUsage_Encryption.equals(tokenUsage)) {
                throw new WSSPolicyException("Illegal token usage!");
            }
            if (!WSSecurityTokenConstants.TOKENUSAGE_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_SIGNED_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENCRYPTED_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_ENCRYPTED_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS.equals(tokenUsage) && !WSSecurityTokenConstants.TOKENUSAGE_SIGNED_ENDORSING_ENCRYPTED_SUPPORTING_TOKENS.equals(tokenUsage) || parentAssertion instanceof TransportToken) continue;
            if (!(parentAssertion instanceof SupportingTokens)) {
                ++ignoreToken;
                continue;
            }
            SupportingTokens supportingTokens = (SupportingTokens)parentAssertion;
            String tokenUsageName = tokenUsage.getName();
            if (tokenUsageName.equals(supportingTokensName = supportingTokens.getName().getLocalPart())) continue;
            if (supportingTokensName.contains("Endorsing") && !tokenUsageName.contains("Endorsing")) {
                ++ignoreToken;
                continue;
            }
            if (supportingTokensName.startsWith("Signed") && !tokenUsageName.startsWith("Signed")) {
                ++ignoreToken;
                continue;
            }
            if (!supportingTokensName.contains("Encrypted") || tokenUsageName.contains("Encrypted")) continue;
            ++ignoreToken;
        }
        if (ignoreToken >= tokenUsages.size()) {
            return true;
        }
        boolean asserted = true;
        SPConstants.IncludeTokenType includeTokenType = abstractToken.getIncludeTokenType();
        boolean isIncludedInMessage = ((InboundSecurityToken)tokenSecurityEvent.getSecurityToken()).isIncludedInMessage();
        switch (includeTokenType) {
            case INCLUDE_TOKEN_NEVER: {
                if (!isIncludedInMessage) break;
                this.setErrorMessage("Token must not be included");
                asserted = false;
                break;
            }
            case INCLUDE_TOKEN_ONCE: {
                break;
            }
            case INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT: {
                if (this.initiator && isIncludedInMessage) {
                    this.setErrorMessage("Token must not be included");
                    asserted = false;
                    break;
                }
                if (this.initiator || isIncludedInMessage) break;
                this.setErrorMessage("Token must be included");
                asserted = false;
                break;
            }
            case INCLUDE_TOKEN_ALWAYS_TO_INITIATOR: {
                if (this.initiator && !isIncludedInMessage) {
                    this.setErrorMessage("Token must be included");
                    asserted = false;
                    break;
                }
                if (this.initiator || !isIncludedInMessage) break;
                this.setErrorMessage("Token must not be included");
                asserted = false;
                break;
            }
            case INCLUDE_TOKEN_ALWAYS: {
                if (isIncludedInMessage) break;
                this.setErrorMessage("Token must be included");
                asserted = false;
            }
        }
        boolean hasDerivedKeys = false;
        hasDerivedKeys = this.hasDerivedKeys((SecurityToken)tokenSecurityEvent.getSecurityToken());
        String namespace = this.getAssertion().getName().getNamespaceURI();
        if (abstractToken.getDerivedKeys() != null) {
            AbstractToken.DerivedKeys derivedKeys = abstractToken.getDerivedKeys();
            switch (derivedKeys) {
                case RequireDerivedKeys: 
                case RequireExplicitDerivedKeys: 
                case RequireImpliedDerivedKeys: {
                    if (!hasDerivedKeys) {
                        this.setErrorMessage("Derived key must be used");
                        this.getPolicyAsserter().unassertPolicy(new QName(namespace, derivedKeys.name()), "Derived key must be used");
                        asserted = false;
                        break;
                    }
                    this.getPolicyAsserter().assertPolicy(new QName(namespace, derivedKeys.name()));
                }
            }
        } else if (hasDerivedKeys) {
            this.setErrorMessage("Derived key must not be used");
            asserted = false;
        }
        if (asserted &= this.assertToken(tokenSecurityEvent, abstractToken)) {
            this.setAsserted(true);
        }
        return asserted || !tokenUsages.contains(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE) && !tokenUsages.contains(WSSecurityTokenConstants.TOKENUSAGE_MAIN_ENCRYPTION);
    }

    public abstract boolean assertToken(TokenSecurityEvent<? extends SecurityToken> var1, AbstractToken var2) throws WSSPolicyException, XMLSecurityException;

    protected boolean hasDerivedKeys(SecurityToken securityToken) throws XMLSecurityException {
        if (securityToken == null) {
            return false;
        }
        if (WSSecurityTokenConstants.DerivedKeyToken.equals(securityToken.getTokenType())) {
            return true;
        }
        if (securityToken.getWrappedTokens().isEmpty()) {
            return false;
        }
        boolean hasDerivedKeys = true;
        for (int i = 0; i < securityToken.getWrappedTokens().size(); ++i) {
            SecurityToken wrappedSecurityToken = securityToken.getWrappedTokens().get(i);
            hasDerivedKeys &= this.hasDerivedKeys(wrappedSecurityToken);
        }
        return hasDerivedKeys;
    }

    protected PolicyAsserter getPolicyAsserter() {
        return this.policyAsserter;
    }

    protected boolean isInitiator() {
        return this.initiator;
    }
}

