/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.stax.enforcer;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyContainingAssertion;
import org.apache.neethi.PolicyOperator;
import org.apache.neethi.builders.PrimitiveAssertion;
import org.apache.wss4j.common.WSSPolicyException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AbstractBinding;
import org.apache.wss4j.policy.model.AbstractSecurityAssertion;
import org.apache.wss4j.policy.model.AbstractSymmetricAsymmetricBinding;
import org.apache.wss4j.policy.model.AbstractToken;
import org.apache.wss4j.policy.model.AlgorithmSuite;
import org.apache.wss4j.policy.model.ContentEncryptedElements;
import org.apache.wss4j.policy.model.EncryptedElements;
import org.apache.wss4j.policy.model.EncryptedParts;
import org.apache.wss4j.policy.model.HttpsToken;
import org.apache.wss4j.policy.model.IssuedToken;
import org.apache.wss4j.policy.model.KerberosToken;
import org.apache.wss4j.policy.model.KeyValueToken;
import org.apache.wss4j.policy.model.Layout;
import org.apache.wss4j.policy.model.RelToken;
import org.apache.wss4j.policy.model.RequiredElements;
import org.apache.wss4j.policy.model.RequiredParts;
import org.apache.wss4j.policy.model.SamlToken;
import org.apache.wss4j.policy.model.SecureConversationToken;
import org.apache.wss4j.policy.model.SecurityContextToken;
import org.apache.wss4j.policy.model.SignedElements;
import org.apache.wss4j.policy.model.SignedParts;
import org.apache.wss4j.policy.model.SpnegoContextToken;
import org.apache.wss4j.policy.model.SupportingTokens;
import org.apache.wss4j.policy.model.Trust10;
import org.apache.wss4j.policy.model.Trust13;
import org.apache.wss4j.policy.model.UsernameToken;
import org.apache.wss4j.policy.model.Wss10;
import org.apache.wss4j.policy.model.Wss11;
import org.apache.wss4j.policy.model.X509Token;
import org.apache.wss4j.policy.stax.Assertable;
import org.apache.wss4j.policy.stax.DummyPolicyAsserter;
import org.apache.wss4j.policy.stax.OperationPolicy;
import org.apache.wss4j.policy.stax.PolicyAsserter;
import org.apache.wss4j.policy.stax.PolicyViolationException;
import org.apache.wss4j.policy.stax.assertionStates.AlgorithmSuiteAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.ContentEncryptedElementsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.EncryptedElementsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.EncryptedPartsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.HttpsTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.IncludeTimeStampAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.IssuedTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.KerberosTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.KeyValueTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.OnlySignEntireHeadersAndBodyAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.ProtectionOrderAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.RelTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.RequiredElementsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.RequiredPartsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SamlTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SecureConversationTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SecurityContextTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SignatureConfirmationAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SignatureProtectionAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SignedElementsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SignedPartsAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.SpnegoContextTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.TokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.TokenProtectionAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.UsernameTokenAssertionState;
import org.apache.wss4j.policy.stax.assertionStates.X509TokenAssertionState;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.securityEvent.NoSecuritySecurityEvent;
import org.apache.wss4j.stax.securityEvent.OperationSecurityEvent;
import org.apache.wss4j.stax.securityEvent.WSSecurityEventConstants;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyEnforcer
implements SecurityEventListener {
    private static final transient Logger LOG = LoggerFactory.getLogger(PolicyEnforcer.class);
    private static final QName SOAP11_FAULT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
    private static final QName SOAP12_FAULT = new QName("http://www.w3.org/2003/05/soap-envelope", "Fault");
    private final List<OperationPolicy> operationPolicies;
    private OperationPolicy effectivePolicy;
    private final List<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMap;
    private final List<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> failedAssertionStateMap;
    private final Deque<SecurityEvent> securityEventQueue = new LinkedList<SecurityEvent>();
    private boolean operationSecurityEventOccured = false;
    private boolean initiator;
    private String actorOrRole;
    private int attachmentCount;
    private boolean noSecurityHeader;
    private boolean faultOccurred;
    private final PolicyAsserter policyAsserter;

    public PolicyEnforcer(List<OperationPolicy> operationPolicies, String soapAction, boolean initiator, String actorOrRole, int attachmentCount) throws WSSPolicyException {
        this(operationPolicies, soapAction, initiator, actorOrRole, attachmentCount, null);
    }

    public PolicyEnforcer(List<OperationPolicy> operationPolicies, String soapAction, boolean initiator, String actorOrRole, int attachmentCount, PolicyAsserter policyAsserter) throws WSSPolicyException {
        this.operationPolicies = operationPolicies;
        this.initiator = initiator;
        this.actorOrRole = actorOrRole;
        this.attachmentCount = attachmentCount;
        this.assertionStateMap = new LinkedList<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>>();
        this.failedAssertionStateMap = new LinkedList<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>>();
        this.policyAsserter = policyAsserter == null ? new DummyPolicyAsserter() : policyAsserter;
        if (soapAction != null && !soapAction.isEmpty()) {
            this.effectivePolicy = this.findPolicyBySOAPAction(operationPolicies, soapAction);
            if (this.effectivePolicy != null) {
                this.buildAssertionStateMap(this.effectivePolicy.getPolicy(), this.assertionStateMap);
            }
        }
    }

    private OperationPolicy findPolicyBySOAPAction(List<OperationPolicy> operationPolicies, String soapAction) {
        for (OperationPolicy operationPolicy : operationPolicies) {
            if (!soapAction.equals(operationPolicy.getOperationAction())) continue;
            return operationPolicy;
        }
        return null;
    }

    private OperationPolicy findPolicyBySOAPOperationName(List<OperationPolicy> operationPolicies, QName soapOperationName) {
        Iterator<OperationPolicy> operationPolicyIterator = operationPolicies.iterator();
        OperationPolicy noNamespaceOperation = null;
        while (operationPolicyIterator.hasNext()) {
            OperationPolicy operationPolicy = operationPolicyIterator.next();
            if (operationPolicy.getOperationName() == null) continue;
            if (soapOperationName.equals(operationPolicy.getOperationName())) {
                return operationPolicy;
            }
            if (!"".equals(operationPolicy.getOperationName().getNamespaceURI()) || !soapOperationName.getLocalPart().equals(operationPolicy.getOperationName().getLocalPart())) continue;
            noNamespaceOperation = operationPolicy;
        }
        return noNamespaceOperation;
    }

    private void buildAssertionStateMap(PolicyComponent policyComponent, List<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMap) throws WSSPolicyException {
        if (policyComponent instanceof PolicyOperator) {
            PolicyOperator policyOperator = (PolicyOperator)policyComponent;
            List<PolicyComponent> policyComponents = policyOperator.getPolicyComponents();
            int alternative = 0;
            for (PolicyComponent curPolicyComponent : policyComponents) {
                if (policyOperator instanceof ExactlyOne) {
                    assertionStateMap.add(new HashMap());
                    this.buildAssertionStateMap(curPolicyComponent, assertionStateMap, alternative++);
                    continue;
                }
                this.buildAssertionStateMap(curPolicyComponent, assertionStateMap);
            }
        } else {
            throw new WSSPolicyException("Invalid PolicyComponent: " + policyComponent + " " + policyComponent.getType());
        }
    }

    private void buildAssertionStateMap(PolicyComponent policyComponent, List<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMap, int alternative) throws WSSPolicyException {
        if (policyComponent instanceof PolicyOperator) {
            PolicyOperator policyOperator = (PolicyOperator)policyComponent;
            List<PolicyComponent> policyComponents = policyOperator.getPolicyComponents();
            for (PolicyComponent curPolicyComponent : policyComponents) {
                this.buildAssertionStateMap(curPolicyComponent, assertionStateMap, alternative);
            }
        } else if (policyComponent instanceof AbstractSecurityAssertion) {
            AbstractSecurityAssertion abstractSecurityAssertion = (AbstractSecurityAssertion)policyComponent;
            List<Assertable> assertablesList = this.getAssertableForAssertion(abstractSecurityAssertion);
            for (Assertable assertable : assertablesList) {
                Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map = assertionStateMap.get(alternative);
                SecurityEventConstants.Event[] securityEventType = assertable.getSecurityEventType();
                for (int j = 0; j < securityEventType.length; ++j) {
                    SecurityEventConstants.Event event = securityEventType[j];
                    Map<Assertion, List<Assertable>> assertables = map.get(event);
                    if (assertables == null) {
                        assertables = new HashMap<Assertion, List<Assertable>>();
                        map.put(event, assertables);
                    }
                    this.addAssertionState(assertables, abstractSecurityAssertion, assertable);
                }
            }
            if (abstractSecurityAssertion instanceof PolicyContainingAssertion) {
                this.buildAssertionStateMap(((PolicyContainingAssertion)((Object)abstractSecurityAssertion)).getPolicy(), assertionStateMap, alternative);
            }
        } else if (!(policyComponent instanceof PrimitiveAssertion)) {
            throw new WSSPolicyException("Unsupported PolicyComponent: " + policyComponent + " type: " + policyComponent.getType());
        }
    }

    private void addAssertionState(Map<Assertion, List<Assertable>> assertables, Assertion keyAssertion, Assertable assertable) {
        List<Assertable> assertableList = assertables.get(keyAssertion);
        if (assertableList == null) {
            assertableList = new LinkedList<Assertable>();
            assertables.put(keyAssertion, assertableList);
        }
        assertableList.add(assertable);
    }

    private boolean isTokenRequired(AbstractToken token) {
        SPConstants.IncludeTokenType includeTokenType = token.getIncludeTokenType();
        if (includeTokenType == SPConstants.IncludeTokenType.INCLUDE_TOKEN_NEVER) {
            return false;
        }
        if (this.initiator && includeTokenType == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_RECIPIENT) {
            return false;
        }
        if (this.initiator && includeTokenType == SPConstants.IncludeTokenType.INCLUDE_TOKEN_ONCE) {
            return false;
        }
        return this.initiator || includeTokenType != SPConstants.IncludeTokenType.INCLUDE_TOKEN_ALWAYS_TO_INITIATOR;
    }

    protected List<Assertable> getAssertableForAssertion(AbstractSecurityAssertion abstractSecurityAssertion) throws WSSPolicyException {
        LinkedList<Assertable> assertableList = new LinkedList<Assertable>();
        boolean tokenRequired = true;
        if (abstractSecurityAssertion instanceof AbstractToken) {
            tokenRequired = this.isTokenRequired((AbstractToken)abstractSecurityAssertion);
        }
        if (abstractSecurityAssertion instanceof ContentEncryptedElements) {
            assertableList.add(new ContentEncryptedElementsAssertionState(abstractSecurityAssertion, this.policyAsserter, true));
        } else if (abstractSecurityAssertion instanceof EncryptedParts) {
            assertableList.add(new EncryptedPartsAssertionState(abstractSecurityAssertion, this.policyAsserter, true, this.attachmentCount));
        } else if (abstractSecurityAssertion instanceof EncryptedElements) {
            assertableList.add(new EncryptedElementsAssertionState(abstractSecurityAssertion, this.policyAsserter, true));
        } else if (abstractSecurityAssertion instanceof SignedParts) {
            assertableList.add(new SignedPartsAssertionState(abstractSecurityAssertion, this.policyAsserter, true, this.attachmentCount));
        } else if (abstractSecurityAssertion instanceof SignedElements) {
            assertableList.add(new SignedElementsAssertionState(abstractSecurityAssertion, this.policyAsserter, true));
        } else if (abstractSecurityAssertion instanceof RequiredElements) {
            assertableList.add(new RequiredElementsAssertionState(abstractSecurityAssertion, this.policyAsserter, false));
        } else if (abstractSecurityAssertion instanceof RequiredParts) {
            assertableList.add(new RequiredPartsAssertionState(abstractSecurityAssertion, this.policyAsserter, false));
        } else if (abstractSecurityAssertion instanceof UsernameToken) {
            assertableList.add(new UsernameTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof IssuedToken) {
            assertableList.add(new IssuedTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof X509Token) {
            assertableList.add(new X509TokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof KerberosToken) {
            assertableList.add(new KerberosTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof SpnegoContextToken) {
            assertableList.add(new SpnegoContextTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof SecureConversationToken) {
            assertableList.add(new SecureConversationTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof SecurityContextToken) {
            assertableList.add(new SecurityContextTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof SamlToken) {
            assertableList.add(new SamlTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof RelToken) {
            assertableList.add(new RelTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof HttpsToken) {
            assertableList.add(new HttpsTokenAssertionState(abstractSecurityAssertion, !tokenRequired || this.initiator, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof KeyValueToken) {
            assertableList.add(new KeyValueTokenAssertionState(abstractSecurityAssertion, !tokenRequired, this.policyAsserter, this.initiator));
        } else if (abstractSecurityAssertion instanceof AlgorithmSuite) {
            assertableList.add(new AlgorithmSuiteAssertionState(abstractSecurityAssertion, this.policyAsserter, true));
        } else if (abstractSecurityAssertion instanceof Layout) {
            String namespace = abstractSecurityAssertion.getName().getNamespaceURI();
            this.policyAsserter.assertPolicy(new QName(namespace, "Lax"));
            this.policyAsserter.assertPolicy(new QName(namespace, "LaxTsFirst"));
            this.policyAsserter.assertPolicy(new QName(namespace, "LaxTsLast"));
            this.policyAsserter.assertPolicy(new QName(namespace, "Strict"));
            this.policyAsserter.assertPolicy(abstractSecurityAssertion);
        } else if (abstractSecurityAssertion instanceof AbstractBinding) {
            this.policyAsserter.assertPolicy(abstractSecurityAssertion);
            AbstractBinding abstractBinding = (AbstractBinding)abstractSecurityAssertion;
            if (abstractBinding instanceof AbstractSymmetricAsymmetricBinding) {
                AbstractSymmetricAsymmetricBinding abstractSymmetricAsymmetricBinding = (AbstractSymmetricAsymmetricBinding)abstractSecurityAssertion;
                assertableList.add(new ProtectionOrderAssertionState(abstractSymmetricAsymmetricBinding, this.policyAsserter, true));
                assertableList.add(new SignatureProtectionAssertionState(abstractSymmetricAsymmetricBinding, this.policyAsserter, true));
                if (abstractSymmetricAsymmetricBinding.isOnlySignEntireHeadersAndBody()) {
                    assertableList.add(new OnlySignEntireHeadersAndBodyAssertionState(abstractSecurityAssertion, this.policyAsserter, true, this.actorOrRole));
                }
                assertableList.add(new TokenProtectionAssertionState(abstractSecurityAssertion, this.policyAsserter, true));
            }
            assertableList.add(new IncludeTimeStampAssertionState(abstractBinding, this.policyAsserter, true));
            if (abstractBinding.isIncludeTimestamp()) {
                LinkedList<QName> timestampElementPath = new LinkedList<QName>();
                timestampElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
                timestampElementPath.add(WSSConstants.TAG_WSU_TIMESTAMP);
                RequiredElementsAssertionState requiredElementsAssertionState = new RequiredElementsAssertionState(abstractBinding, this.policyAsserter, false);
                requiredElementsAssertionState.addElement(timestampElementPath);
                assertableList.add(requiredElementsAssertionState);
                SignedElementsAssertionState signedElementsAssertionState = new SignedElementsAssertionState(abstractSecurityAssertion, this.policyAsserter, true);
                signedElementsAssertionState.addElement(timestampElementPath);
                assertableList.add(signedElementsAssertionState);
            }
        } else if (abstractSecurityAssertion instanceof Wss10) {
            Wss10 wss10 = (Wss10)abstractSecurityAssertion;
            String namespace = wss10.getName().getNamespaceURI();
            this.policyAsserter.assertPolicy(abstractSecurityAssertion);
            if (wss10.isMustSupportRefEmbeddedToken()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefEmbeddedToken"));
            }
            if (wss10.isMustSupportRefExternalURI()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefExternalURI"));
            }
            if (wss10.isMustSupportRefIssuerSerial()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefIssuerSerial"));
            }
            if (wss10.isMustSupportRefKeyIdentifier()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefKeyIdentifier"));
            }
            if (abstractSecurityAssertion instanceof Wss11) {
                Wss11 wss11 = (Wss11)abstractSecurityAssertion;
                if (wss11.isMustSupportRefEncryptedKey()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefEncryptedKey"));
                }
                if (wss11.isMustSupportRefThumbprint()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportRefThumbprint"));
                }
                if (wss11.isRequireSignatureConfirmation()) {
                    assertableList.add(new SignatureConfirmationAssertionState(wss11, this.policyAsserter, true));
                    if (this.initiator) {
                        LinkedList<QName> signatureConfirmationElementPath = new LinkedList<QName>();
                        signatureConfirmationElementPath.addAll(WSSConstants.WSSE_SECURITY_HEADER_PATH);
                        signatureConfirmationElementPath.add(WSSConstants.TAG_WSSE11_SIG_CONF);
                        RequiredElementsAssertionState requiredElementsAssertionState = new RequiredElementsAssertionState(wss11, this.policyAsserter, false);
                        requiredElementsAssertionState.addElement(signatureConfirmationElementPath);
                        assertableList.add(requiredElementsAssertionState);
                        SignedElementsAssertionState signedElementsAssertionState = new SignedElementsAssertionState(wss11, this.policyAsserter, true);
                        signedElementsAssertionState.addElement(signatureConfirmationElementPath);
                        assertableList.add(signedElementsAssertionState);
                    }
                }
            }
        } else if (abstractSecurityAssertion instanceof Trust10) {
            Trust10 trust10 = (Trust10)abstractSecurityAssertion;
            String namespace = trust10.getName().getNamespaceURI();
            this.policyAsserter.assertPolicy(abstractSecurityAssertion);
            if (trust10.isMustSupportClientChallenge()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportClientChallenge"));
            }
            if (trust10.isMustSupportIssuedTokens()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportIssuedTokens"));
            }
            if (trust10.isMustSupportServerChallenge()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportServerChallenge"));
            }
            if (trust10.isRequireClientEntropy()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "RequireClientEntropy"));
            }
            if (trust10.isRequireServerEntropy()) {
                this.policyAsserter.assertPolicy(new QName(namespace, "RequireServerEntropy"));
            }
            if (trust10 instanceof Trust13) {
                Trust13 trust13 = (Trust13)trust10;
                if (trust13.isMustSupportInteractiveChallenge()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "MustSupportInteractiveChallenge"));
                }
                if (trust13.isRequireAppliesTo()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "RequireAppliesTo"));
                }
                if (trust13.isRequireRequestSecurityTokenCollection()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "RequireRequestSecurityTokenCollection"));
                }
                if (trust13.isScopePolicy15()) {
                    this.policyAsserter.assertPolicy(new QName(namespace, "ScopePolicy15"));
                }
            }
        } else {
            this.policyAsserter.assertPolicy(abstractSecurityAssertion);
        }
        return assertableList;
    }

    private void verifyPolicy(SecurityEvent securityEvent) throws WSSPolicyException, XMLSecurityException {
        if (!this.failedAssertionStateMap.isEmpty()) {
            block0: for (Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map : this.failedAssertionStateMap) {
                Map<Assertion, List<Assertable>> assertionListMap = map.get(securityEvent.getSecurityEventType());
                if (assertionListMap == null || assertionListMap.isEmpty()) continue;
                for (Map.Entry<Assertion, List<Assertable>> assertionStateEntry : assertionListMap.entrySet()) {
                    List<Assertable> assertionStates = assertionStateEntry.getValue();
                    for (Assertable assertable : assertionStates) {
                        boolean asserted = assertable.assertEvent(securityEvent);
                        if (asserted) continue;
                        continue block0;
                    }
                }
            }
        }
        String assertionMessage = null;
        Iterator<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMapIterator = this.assertionStateMap.iterator();
        block3: while (assertionStateMapIterator.hasNext()) {
            Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map = assertionStateMapIterator.next();
            Map<Assertion, List<Assertable>> assertionListMap = map.get(securityEvent.getSecurityEventType());
            if (assertionListMap == null || assertionListMap.isEmpty()) continue;
            for (Map.Entry<Assertion, List<Assertable>> assertionStateEntry : assertionListMap.entrySet()) {
                List<Assertable> assertionStates = assertionStateEntry.getValue();
                for (Assertable assertable : assertionStates) {
                    boolean asserted = assertable.assertEvent(securityEvent);
                    if (asserted) continue;
                    assertionMessage = assertable.getErrorMessage();
                    this.failedAssertionStateMap.add(map);
                    assertionStateMapIterator.remove();
                    continue block3;
                }
            }
        }
        if (!(!this.assertionStateMap.isEmpty() || this.faultOccurred && this.noSecurityHeader && this.initiator)) {
            this.logFailedAssertions();
            throw new PolicyViolationException(assertionMessage);
        }
    }

    private void verifyPolicy() throws WSSPolicyException {
        String assertionMessage = null;
        Iterator<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMapIterator = this.assertionStateMap.iterator();
        block0: while (assertionStateMapIterator.hasNext()) {
            Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map = assertionStateMapIterator.next();
            for (Map.Entry<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> mapEntry : map.entrySet()) {
                for (Map.Entry<Assertion, List<Assertable>> assertionListEntry : mapEntry.getValue().entrySet()) {
                    List<Assertable> assertableList = assertionListEntry.getValue();
                    for (Assertable assertable : assertableList) {
                        if (assertable.isAsserted()) continue;
                        assertionMessage = assertable.getErrorMessage();
                        this.failedAssertionStateMap.add(map);
                        assertionStateMapIterator.remove();
                        continue block0;
                    }
                }
            }
        }
        if (!(!this.assertionStateMap.isEmpty() || this.faultOccurred && this.noSecurityHeader && this.initiator)) {
            this.logFailedAssertions();
            throw new WSSPolicyException(assertionMessage);
        }
    }

    private void verifyPolicyAfterOperationSecurityEvent() throws WSSPolicyException {
        String assertionMessage = null;
        Iterator<Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> assertionStateMapIterator = this.assertionStateMap.iterator();
        block0: while (assertionStateMapIterator.hasNext()) {
            Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map = assertionStateMapIterator.next();
            for (Map.Entry<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> mapEntry : map.entrySet()) {
                for (Map.Entry<Assertion, List<Assertable>> assertionListEntry : mapEntry.getValue().entrySet()) {
                    List<Assertable> assertableList = assertionListEntry.getValue();
                    for (Assertable assertable : assertableList) {
                        boolean doAssert = false;
                        if (assertable instanceof TokenAssertionState) {
                            TokenAssertionState tokenAssertionState = (TokenAssertionState)assertable;
                            AbstractToken abstractToken = (AbstractToken)tokenAssertionState.getAssertion();
                            AbstractSecurityAssertion assertion = abstractToken.getParentAssertion();
                            if (assertion instanceof SupportingTokens || assertable instanceof HttpsTokenAssertionState || assertable instanceof RelTokenAssertionState || assertable instanceof SecurityContextTokenAssertionState || assertable instanceof SpnegoContextTokenAssertionState || assertable instanceof UsernameTokenAssertionState) {
                                doAssert = true;
                            }
                        } else if (assertable instanceof TokenProtectionAssertionState || assertable instanceof SignatureConfirmationAssertionState || assertable instanceof IncludeTimeStampAssertionState || assertable instanceof RequiredPartsAssertionState || assertable instanceof SignatureProtectionAssertionState) {
                            doAssert = true;
                        }
                        if (!doAssert && !assertable.isHardFailure() || assertable.isAsserted()) continue;
                        assertionMessage = assertable.getErrorMessage();
                        this.failedAssertionStateMap.add(map);
                        assertionStateMapIterator.remove();
                        continue block0;
                    }
                }
            }
        }
        if (!(!this.assertionStateMap.isEmpty() || this.faultOccurred && this.noSecurityHeader && this.initiator)) {
            this.logFailedAssertions();
            throw new WSSPolicyException(assertionMessage);
        }
    }

    private void logFailedAssertions() {
        if (this.failedAssertionStateMap.isEmpty()) {
            return;
        }
        for (Map<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> map : this.failedAssertionStateMap) {
            Set<Map.Entry<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>>> entrySet = map.entrySet();
            for (Map.Entry<SecurityEventConstants.Event, Map<Assertion, List<Assertable>>> eventCollectionEntry : entrySet) {
                Map<Assertion, List<Assertable>> assertionListMap = eventCollectionEntry.getValue();
                for (Map.Entry<Assertion, List<Assertable>> entry : assertionListMap.entrySet()) {
                    List<Assertable> assertionStates = entry.getValue();
                    for (Assertable assertable : assertionStates) {
                        if (assertable.isAsserted() || assertable.isLogged()) continue;
                        LOG.error(entry.getKey().getName() + " not satisfied: " + assertable.getErrorMessage());
                        assertable.setLogged(true);
                    }
                }
            }
        }
    }

    @Override
    public synchronized void registerSecurityEvent(SecurityEvent securityEvent) throws WSSecurityException {
        if (!this.noSecurityHeader && securityEvent instanceof NoSecuritySecurityEvent) {
            this.noSecurityHeader = true;
        }
        if (this.operationSecurityEventOccured) {
            try {
                this.verifyPolicy(securityEvent);
            }
            catch (WSSPolicyException | XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
            }
        }
        if (WSSecurityEventConstants.OPERATION.equals(securityEvent.getSecurityEventType())) {
            this.operationSecurityEventOccured = true;
            OperationSecurityEvent operationSecurityEvent = (OperationSecurityEvent)securityEvent;
            if (!this.faultOccurred && (SOAP11_FAULT.equals(operationSecurityEvent.getOperation()) || SOAP12_FAULT.equals(operationSecurityEvent.getOperation()))) {
                this.faultOccurred = true;
            }
            if (this.effectivePolicy == null) {
                this.effectivePolicy = this.findPolicyBySOAPOperationName(this.operationPolicies, operationSecurityEvent.getOperation());
                if (this.effectivePolicy == null) {
                    this.effectivePolicy = new OperationPolicy(new QName(null, "NoPolicyFoundForOperation"));
                    this.effectivePolicy.setPolicy(new Policy());
                }
                try {
                    this.buildAssertionStateMap(this.effectivePolicy.getPolicy(), this.assertionStateMap);
                }
                catch (WSSPolicyException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
                }
            }
            try {
                Iterator<SecurityEvent> securityEventIterator = this.securityEventQueue.descendingIterator();
                while (securityEventIterator.hasNext()) {
                    SecurityEvent prevSecurityEvent = securityEventIterator.next();
                    this.verifyPolicy(prevSecurityEvent);
                }
                this.verifyPolicy(securityEvent);
                this.verifyPolicyAfterOperationSecurityEvent();
            }
            catch (WSSPolicyException | XMLSecurityException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
            }
            this.securityEventQueue.clear();
            return;
        }
        this.securityEventQueue.push(securityEvent);
    }

    public void doFinal() throws WSSPolicyException {
        this.verifyPolicy();
    }
}

