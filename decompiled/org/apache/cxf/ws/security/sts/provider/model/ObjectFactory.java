/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.apache.cxf.ws.addressing.EndpointReferenceType;
import org.apache.cxf.ws.security.sts.provider.model.AllowPostdatingType;
import org.apache.cxf.ws.security.sts.provider.model.AuthenticatorType;
import org.apache.cxf.ws.security.sts.provider.model.BinaryExchangeType;
import org.apache.cxf.ws.security.sts.provider.model.BinarySecretType;
import org.apache.cxf.ws.security.sts.provider.model.CancelTargetType;
import org.apache.cxf.ws.security.sts.provider.model.ClaimsType;
import org.apache.cxf.ws.security.sts.provider.model.DelegateToType;
import org.apache.cxf.ws.security.sts.provider.model.EncryptionType;
import org.apache.cxf.ws.security.sts.provider.model.EntropyType;
import org.apache.cxf.ws.security.sts.provider.model.KeyExchangeTokenType;
import org.apache.cxf.ws.security.sts.provider.model.LifetimeType;
import org.apache.cxf.ws.security.sts.provider.model.OnBehalfOfType;
import org.apache.cxf.ws.security.sts.provider.model.ParticipantType;
import org.apache.cxf.ws.security.sts.provider.model.ParticipantsType;
import org.apache.cxf.ws.security.sts.provider.model.ProofEncryptionType;
import org.apache.cxf.ws.security.sts.provider.model.RenewTargetType;
import org.apache.cxf.ws.security.sts.provider.model.RenewingType;
import org.apache.cxf.ws.security.sts.provider.model.RequestKETType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;
import org.apache.cxf.ws.security.sts.provider.model.RequestedProofTokenType;
import org.apache.cxf.ws.security.sts.provider.model.RequestedReferenceType;
import org.apache.cxf.ws.security.sts.provider.model.RequestedSecurityTokenType;
import org.apache.cxf.ws.security.sts.provider.model.RequestedTokenCancelledType;
import org.apache.cxf.ws.security.sts.provider.model.SignChallengeType;
import org.apache.cxf.ws.security.sts.provider.model.StatusType;
import org.apache.cxf.ws.security.sts.provider.model.UseKeyType;
import org.apache.cxf.ws.security.sts.provider.model.ValidateTargetType;

@XmlRegistry
public class ObjectFactory {
    private static final QName _RequestSecurityToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken");
    private static final QName _TokenType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "TokenType");
    private static final QName _RequestType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType");
    private static final QName _RequestSecurityTokenResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponse");
    private static final QName _RequestedSecurityToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedSecurityToken");
    private static final QName _BinarySecret_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret");
    private static final QName _Claims_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Claims");
    private static final QName _Entropy_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Entropy");
    private static final QName _Lifetime_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Lifetime");
    private static final QName _RequestSecurityTokenCollection_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollection");
    private static final QName _RequestSecurityTokenResponseCollection_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollection");
    private static final QName _ComputedKey_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKey");
    private static final QName _RequestedAttachedReference_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedAttachedReference");
    private static final QName _RequestedUnattachedReference_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedUnattachedReference");
    private static final QName _RequestedProofToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedProofToken");
    private static final QName _IssuedTokens_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "IssuedTokens");
    private static final QName _RenewTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewTarget");
    private static final QName _AllowPostdating_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdating");
    private static final QName _Renewing_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Renewing");
    private static final QName _CancelTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CancelTarget");
    private static final QName _RequestedTokenCancelled_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelled");
    private static final QName _ValidateTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ValidateTarget");
    private static final QName _Status_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Status");
    private static final QName _SignChallenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallenge");
    private static final QName _SignChallengeResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallengeResponse");
    private static final QName _Challenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Challenge");
    private static final QName _BinaryExchange_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchange");
    private static final QName _RequestKET_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestKET");
    private static final QName _KeyExchangeToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyExchangeToken");
    private static final QName _Authenticator_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Authenticator");
    private static final QName _CombinedHash_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CombinedHash");
    private static final QName _OnBehalfOf_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf");
    private static final QName _Issuer_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Issuer");
    private static final QName _AuthenticationType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationType");
    private static final QName _KeyType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType");
    private static final QName _KeySize_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize");
    private static final QName _SignatureAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignatureAlgorithm");
    private static final QName _EncryptionAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptionAlgorithm");
    private static final QName _CanonicalizationAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CanonicalizationAlgorithm");
    private static final QName _ComputedKeyAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyAlgorithm");
    private static final QName _Encryption_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Encryption");
    private static final QName _ProofEncryption_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption");
    private static final QName _UseKey_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey");
    private static final QName _KeyWrapAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyWrapAlgorithm");
    private static final QName _SignWith_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignWith");
    private static final QName _EncryptWith_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptWith");
    private static final QName _DelegateTo_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "DelegateTo");
    private static final QName _Forwardable_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Forwardable");
    private static final QName _Delegatable_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Delegatable");
    private static final QName _Participants_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Participants");

    public RequestSecurityTokenType createRequestSecurityTokenType() {
        return new RequestSecurityTokenType();
    }

    public RequestSecurityTokenResponseType createRequestSecurityTokenResponseType() {
        return new RequestSecurityTokenResponseType();
    }

    public RequestedSecurityTokenType createRequestedSecurityTokenType() {
        return new RequestedSecurityTokenType();
    }

    public BinarySecretType createBinarySecretType() {
        return new BinarySecretType();
    }

    public ClaimsType createClaimsType() {
        return new ClaimsType();
    }

    public EntropyType createEntropyType() {
        return new EntropyType();
    }

    public LifetimeType createLifetimeType() {
        return new LifetimeType();
    }

    public RequestSecurityTokenCollectionType createRequestSecurityTokenCollectionType() {
        return new RequestSecurityTokenCollectionType();
    }

    public RequestSecurityTokenResponseCollectionType createRequestSecurityTokenResponseCollectionType() {
        return new RequestSecurityTokenResponseCollectionType();
    }

    public RequestedReferenceType createRequestedReferenceType() {
        return new RequestedReferenceType();
    }

    public RequestedProofTokenType createRequestedProofTokenType() {
        return new RequestedProofTokenType();
    }

    public RenewTargetType createRenewTargetType() {
        return new RenewTargetType();
    }

    public AllowPostdatingType createAllowPostdatingType() {
        return new AllowPostdatingType();
    }

    public RenewingType createRenewingType() {
        return new RenewingType();
    }

    public CancelTargetType createCancelTargetType() {
        return new CancelTargetType();
    }

    public RequestedTokenCancelledType createRequestedTokenCancelledType() {
        return new RequestedTokenCancelledType();
    }

    public ValidateTargetType createValidateTargetType() {
        return new ValidateTargetType();
    }

    public StatusType createStatusType() {
        return new StatusType();
    }

    public SignChallengeType createSignChallengeType() {
        return new SignChallengeType();
    }

    public BinaryExchangeType createBinaryExchangeType() {
        return new BinaryExchangeType();
    }

    public RequestKETType createRequestKETType() {
        return new RequestKETType();
    }

    public KeyExchangeTokenType createKeyExchangeTokenType() {
        return new KeyExchangeTokenType();
    }

    public AuthenticatorType createAuthenticatorType() {
        return new AuthenticatorType();
    }

    public OnBehalfOfType createOnBehalfOfType() {
        return new OnBehalfOfType();
    }

    public EncryptionType createEncryptionType() {
        return new EncryptionType();
    }

    public ProofEncryptionType createProofEncryptionType() {
        return new ProofEncryptionType();
    }

    public UseKeyType createUseKeyType() {
        return new UseKeyType();
    }

    public DelegateToType createDelegateToType() {
        return new DelegateToType();
    }

    public ParticipantsType createParticipantsType() {
        return new ParticipantsType();
    }

    public ParticipantType createParticipantType() {
        return new ParticipantType();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestSecurityToken")
    public JAXBElement<RequestSecurityTokenType> createRequestSecurityToken(RequestSecurityTokenType value) {
        return new JAXBElement<RequestSecurityTokenType>(_RequestSecurityToken_QNAME, RequestSecurityTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="TokenType")
    public JAXBElement<String> createTokenType(String value) {
        return new JAXBElement<String>(_TokenType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestType")
    public JAXBElement<String> createRequestType(String value) {
        return new JAXBElement<String>(_RequestType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestSecurityTokenResponse")
    public JAXBElement<RequestSecurityTokenResponseType> createRequestSecurityTokenResponse(RequestSecurityTokenResponseType value) {
        return new JAXBElement<RequestSecurityTokenResponseType>(_RequestSecurityTokenResponse_QNAME, RequestSecurityTokenResponseType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestedSecurityToken")
    public JAXBElement<RequestedSecurityTokenType> createRequestedSecurityToken(RequestedSecurityTokenType value) {
        return new JAXBElement<RequestedSecurityTokenType>(_RequestedSecurityToken_QNAME, RequestedSecurityTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="BinarySecret")
    public JAXBElement<BinarySecretType> createBinarySecret(BinarySecretType value) {
        return new JAXBElement<BinarySecretType>(_BinarySecret_QNAME, BinarySecretType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Claims")
    public JAXBElement<ClaimsType> createClaims(ClaimsType value) {
        return new JAXBElement<ClaimsType>(_Claims_QNAME, ClaimsType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Entropy")
    public JAXBElement<EntropyType> createEntropy(EntropyType value) {
        return new JAXBElement<EntropyType>(_Entropy_QNAME, EntropyType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Lifetime")
    public JAXBElement<LifetimeType> createLifetime(LifetimeType value) {
        return new JAXBElement<LifetimeType>(_Lifetime_QNAME, LifetimeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestSecurityTokenCollection")
    public JAXBElement<RequestSecurityTokenCollectionType> createRequestSecurityTokenCollection(RequestSecurityTokenCollectionType value) {
        return new JAXBElement<RequestSecurityTokenCollectionType>(_RequestSecurityTokenCollection_QNAME, RequestSecurityTokenCollectionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestSecurityTokenResponseCollection")
    public JAXBElement<RequestSecurityTokenResponseCollectionType> createRequestSecurityTokenResponseCollection(RequestSecurityTokenResponseCollectionType value) {
        return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_RequestSecurityTokenResponseCollection_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="ComputedKey")
    public JAXBElement<String> createComputedKey(String value) {
        return new JAXBElement<String>(_ComputedKey_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestedAttachedReference")
    public JAXBElement<RequestedReferenceType> createRequestedAttachedReference(RequestedReferenceType value) {
        return new JAXBElement<RequestedReferenceType>(_RequestedAttachedReference_QNAME, RequestedReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestedUnattachedReference")
    public JAXBElement<RequestedReferenceType> createRequestedUnattachedReference(RequestedReferenceType value) {
        return new JAXBElement<RequestedReferenceType>(_RequestedUnattachedReference_QNAME, RequestedReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestedProofToken")
    public JAXBElement<RequestedProofTokenType> createRequestedProofToken(RequestedProofTokenType value) {
        return new JAXBElement<RequestedProofTokenType>(_RequestedProofToken_QNAME, RequestedProofTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="IssuedTokens")
    public JAXBElement<RequestSecurityTokenResponseCollectionType> createIssuedTokens(RequestSecurityTokenResponseCollectionType value) {
        return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_IssuedTokens_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RenewTarget")
    public JAXBElement<RenewTargetType> createRenewTarget(RenewTargetType value) {
        return new JAXBElement<RenewTargetType>(_RenewTarget_QNAME, RenewTargetType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="AllowPostdating")
    public JAXBElement<AllowPostdatingType> createAllowPostdating(AllowPostdatingType value) {
        return new JAXBElement<AllowPostdatingType>(_AllowPostdating_QNAME, AllowPostdatingType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Renewing")
    public JAXBElement<RenewingType> createRenewing(RenewingType value) {
        return new JAXBElement<RenewingType>(_Renewing_QNAME, RenewingType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="CancelTarget")
    public JAXBElement<CancelTargetType> createCancelTarget(CancelTargetType value) {
        return new JAXBElement<CancelTargetType>(_CancelTarget_QNAME, CancelTargetType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestedTokenCancelled")
    public JAXBElement<RequestedTokenCancelledType> createRequestedTokenCancelled(RequestedTokenCancelledType value) {
        return new JAXBElement<RequestedTokenCancelledType>(_RequestedTokenCancelled_QNAME, RequestedTokenCancelledType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="ValidateTarget")
    public JAXBElement<ValidateTargetType> createValidateTarget(ValidateTargetType value) {
        return new JAXBElement<ValidateTargetType>(_ValidateTarget_QNAME, ValidateTargetType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="SignChallenge")
    public JAXBElement<SignChallengeType> createSignChallenge(SignChallengeType value) {
        return new JAXBElement<SignChallengeType>(_SignChallenge_QNAME, SignChallengeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="SignChallengeResponse")
    public JAXBElement<SignChallengeType> createSignChallengeResponse(SignChallengeType value) {
        return new JAXBElement<SignChallengeType>(_SignChallengeResponse_QNAME, SignChallengeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Challenge")
    public JAXBElement<String> createChallenge(String value) {
        return new JAXBElement<String>(_Challenge_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="BinaryExchange")
    public JAXBElement<BinaryExchangeType> createBinaryExchange(BinaryExchangeType value) {
        return new JAXBElement<BinaryExchangeType>(_BinaryExchange_QNAME, BinaryExchangeType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="RequestKET")
    public JAXBElement<RequestKETType> createRequestKET(RequestKETType value) {
        return new JAXBElement<RequestKETType>(_RequestKET_QNAME, RequestKETType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="KeyExchangeToken")
    public JAXBElement<KeyExchangeTokenType> createKeyExchangeToken(KeyExchangeTokenType value) {
        return new JAXBElement<KeyExchangeTokenType>(_KeyExchangeToken_QNAME, KeyExchangeTokenType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Authenticator")
    public JAXBElement<AuthenticatorType> createAuthenticator(AuthenticatorType value) {
        return new JAXBElement<AuthenticatorType>(_Authenticator_QNAME, AuthenticatorType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="CombinedHash")
    public JAXBElement<byte[]> createCombinedHash(byte[] value) {
        return new JAXBElement<byte[]>(_CombinedHash_QNAME, byte[].class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="OnBehalfOf")
    public JAXBElement<OnBehalfOfType> createOnBehalfOf(OnBehalfOfType value) {
        return new JAXBElement<OnBehalfOfType>(_OnBehalfOf_QNAME, OnBehalfOfType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Issuer")
    public JAXBElement<EndpointReferenceType> createIssuer(EndpointReferenceType value) {
        return new JAXBElement<EndpointReferenceType>(_Issuer_QNAME, EndpointReferenceType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="AuthenticationType")
    public JAXBElement<String> createAuthenticationType(String value) {
        return new JAXBElement<String>(_AuthenticationType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="KeyType")
    public JAXBElement<String> createKeyType(String value) {
        return new JAXBElement<String>(_KeyType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="KeySize")
    public JAXBElement<Long> createKeySize(Long value) {
        return new JAXBElement<Long>(_KeySize_QNAME, Long.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="SignatureAlgorithm")
    public JAXBElement<String> createSignatureAlgorithm(String value) {
        return new JAXBElement<String>(_SignatureAlgorithm_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="EncryptionAlgorithm")
    public JAXBElement<String> createEncryptionAlgorithm(String value) {
        return new JAXBElement<String>(_EncryptionAlgorithm_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="CanonicalizationAlgorithm")
    public JAXBElement<String> createCanonicalizationAlgorithm(String value) {
        return new JAXBElement<String>(_CanonicalizationAlgorithm_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="ComputedKeyAlgorithm")
    public JAXBElement<String> createComputedKeyAlgorithm(String value) {
        return new JAXBElement<String>(_ComputedKeyAlgorithm_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Encryption")
    public JAXBElement<EncryptionType> createEncryption(EncryptionType value) {
        return new JAXBElement<EncryptionType>(_Encryption_QNAME, EncryptionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="ProofEncryption")
    public JAXBElement<ProofEncryptionType> createProofEncryption(ProofEncryptionType value) {
        return new JAXBElement<ProofEncryptionType>(_ProofEncryption_QNAME, ProofEncryptionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="UseKey")
    public JAXBElement<UseKeyType> createUseKey(UseKeyType value) {
        return new JAXBElement<UseKeyType>(_UseKey_QNAME, UseKeyType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="KeyWrapAlgorithm")
    public JAXBElement<String> createKeyWrapAlgorithm(String value) {
        return new JAXBElement<String>(_KeyWrapAlgorithm_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="SignWith")
    public JAXBElement<String> createSignWith(String value) {
        return new JAXBElement<String>(_SignWith_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="EncryptWith")
    public JAXBElement<String> createEncryptWith(String value) {
        return new JAXBElement<String>(_EncryptWith_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="DelegateTo")
    public JAXBElement<DelegateToType> createDelegateTo(DelegateToType value) {
        return new JAXBElement<DelegateToType>(_DelegateTo_QNAME, DelegateToType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Forwardable")
    public JAXBElement<Boolean> createForwardable(Boolean value) {
        return new JAXBElement<Boolean>(_Forwardable_QNAME, Boolean.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Delegatable")
    public JAXBElement<Boolean> createDelegatable(Boolean value) {
        return new JAXBElement<Boolean>(_Delegatable_QNAME, Boolean.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", name="Participants")
    public JAXBElement<ParticipantsType> createParticipants(ParticipantsType value) {
        return new JAXBElement<ParticipantsType>(_Participants_QNAME, ParticipantsType.class, null, value);
    }
}

