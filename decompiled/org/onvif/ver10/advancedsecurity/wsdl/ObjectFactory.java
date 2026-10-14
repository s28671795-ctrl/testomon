/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.AlgorithmIdentifier;
import org.onvif.ver10.advancedsecurity.wsdl.BasicRequestAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.CRL;
import org.onvif.ver10.advancedsecurity.wsdl.CSRAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.Capabilities;
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationParameters;
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.CertificateIDs;
import org.onvif.ver10.advancedsecurity.wsdl.CertificationPath;
import org.onvif.ver10.advancedsecurity.wsdl.CreateCertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.CreateCertPathValidationPolicyResponse;
import org.onvif.ver10.advancedsecurity.wsdl.CreateCertificationPath;
import org.onvif.ver10.advancedsecurity.wsdl.CreateCertificationPathResponse;
import org.onvif.ver10.advancedsecurity.wsdl.CreatePKCS10CSR;
import org.onvif.ver10.advancedsecurity.wsdl.CreatePKCS10CSRResponse;
import org.onvif.ver10.advancedsecurity.wsdl.CreateRSAKeyPair;
import org.onvif.ver10.advancedsecurity.wsdl.CreateRSAKeyPairResponse;
import org.onvif.ver10.advancedsecurity.wsdl.CreateSelfSignedCertificate;
import org.onvif.ver10.advancedsecurity.wsdl.CreateSelfSignedCertificateResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DNAttributeTypeAndValue;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCRL;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCRLResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertPathValidationPolicyResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificate;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificateResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificationPath;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificationPathResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteKey;
import org.onvif.ver10.advancedsecurity.wsdl.DeleteKeyResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DeletePassphrase;
import org.onvif.ver10.advancedsecurity.wsdl.DeletePassphraseResponse;
import org.onvif.ver10.advancedsecurity.wsdl.DistinguishedName;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCRLs;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCRLsResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertPathValidationPolicies;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertPathValidationPoliciesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificates;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificatesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificationPaths;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificationPathsResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllKeys;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllKeysResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllPassphrases;
import org.onvif.ver10.advancedsecurity.wsdl.GetAllPassphrasesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPolicies;
import org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPoliciesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificates;
import org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificatesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetCRL;
import org.onvif.ver10.advancedsecurity.wsdl.GetCRLResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertPathValidationPolicyResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertificate;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertificateResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertificationPath;
import org.onvif.ver10.advancedsecurity.wsdl.GetCertificationPathResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequired;
import org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequiredResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetKeyStatus;
import org.onvif.ver10.advancedsecurity.wsdl.GetKeyStatusResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetPrivateKeyStatus;
import org.onvif.ver10.advancedsecurity.wsdl.GetPrivateKeyStatusResponse;
import org.onvif.ver10.advancedsecurity.wsdl.GetServiceCapabilities;
import org.onvif.ver10.advancedsecurity.wsdl.GetServiceCapabilitiesResponse;
import org.onvif.ver10.advancedsecurity.wsdl.KeyAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.KeystoreCapabilities;
import org.onvif.ver10.advancedsecurity.wsdl.MultiValuedRDN;
import org.onvif.ver10.advancedsecurity.wsdl.PassphraseAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignment;
import org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignmentResponse;
import org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequired;
import org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequiredResponse;
import org.onvif.ver10.advancedsecurity.wsdl.TLSServerCapabilities;
import org.onvif.ver10.advancedsecurity.wsdl.TrustAnchor;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCRL;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCRLResponse;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCertificate;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateResponse;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateWithPrivateKeyInPKCS12;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateWithPrivateKeyInPKCS12Response;
import org.onvif.ver10.advancedsecurity.wsdl.UploadKeyPairInPKCS8;
import org.onvif.ver10.advancedsecurity.wsdl.UploadKeyPairInPKCS8Response;
import org.onvif.ver10.advancedsecurity.wsdl.UploadPassphrase;
import org.onvif.ver10.advancedsecurity.wsdl.UploadPassphraseResponse;
import org.onvif.ver10.advancedsecurity.wsdl.X509Certificate;
import org.onvif.ver10.advancedsecurity.wsdl.X509V3Extension;

@XmlRegistry
public class ObjectFactory {
    private static final QName _Capabilities_QNAME = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "Capabilities");
    private static final QName _GetAssignedCertPathValidationPoliciesResponseCertPathValidationPolicyID_QNAME = new QName("http://www.onvif.org/ver10/advancedsecurity/wsdl", "CertPathValidationPolicyID");

    public UploadCRL createUploadCRL() {
        return new UploadCRL();
    }

    public CreateCertPathValidationPolicy createCreateCertPathValidationPolicy() {
        return new CreateCertPathValidationPolicy();
    }

    public KeystoreCapabilities createKeystoreCapabilities() {
        return new KeystoreCapabilities();
    }

    public CertPathValidationPolicy createCertPathValidationPolicy() {
        return new CertPathValidationPolicy();
    }

    public CertPathValidationParameters createCertPathValidationParameters() {
        return new CertPathValidationParameters();
    }

    public PassphraseAttribute createPassphraseAttribute() {
        return new PassphraseAttribute();
    }

    public CertificationPath createCertificationPath() {
        return new CertificationPath();
    }

    public AlgorithmIdentifier createAlgorithmIdentifier() {
        return new AlgorithmIdentifier();
    }

    public CSRAttribute createCSRAttribute() {
        return new CSRAttribute();
    }

    public DistinguishedName createDistinguishedName() {
        return new DistinguishedName();
    }

    public KeyAttribute createKeyAttribute() {
        return new KeyAttribute();
    }

    public Capabilities createCapabilities() {
        return new Capabilities();
    }

    public GetServiceCapabilities createGetServiceCapabilities() {
        return new GetServiceCapabilities();
    }

    public GetServiceCapabilitiesResponse createGetServiceCapabilitiesResponse() {
        return new GetServiceCapabilitiesResponse();
    }

    public CreateRSAKeyPair createCreateRSAKeyPair() {
        return new CreateRSAKeyPair();
    }

    public CreateRSAKeyPairResponse createCreateRSAKeyPairResponse() {
        return new CreateRSAKeyPairResponse();
    }

    public UploadKeyPairInPKCS8 createUploadKeyPairInPKCS8() {
        return new UploadKeyPairInPKCS8();
    }

    public UploadKeyPairInPKCS8Response createUploadKeyPairInPKCS8Response() {
        return new UploadKeyPairInPKCS8Response();
    }

    public UploadCertificateWithPrivateKeyInPKCS12 createUploadCertificateWithPrivateKeyInPKCS12() {
        return new UploadCertificateWithPrivateKeyInPKCS12();
    }

    public UploadCertificateWithPrivateKeyInPKCS12Response createUploadCertificateWithPrivateKeyInPKCS12Response() {
        return new UploadCertificateWithPrivateKeyInPKCS12Response();
    }

    public GetKeyStatus createGetKeyStatus() {
        return new GetKeyStatus();
    }

    public GetKeyStatusResponse createGetKeyStatusResponse() {
        return new GetKeyStatusResponse();
    }

    public GetPrivateKeyStatus createGetPrivateKeyStatus() {
        return new GetPrivateKeyStatus();
    }

    public GetPrivateKeyStatusResponse createGetPrivateKeyStatusResponse() {
        return new GetPrivateKeyStatusResponse();
    }

    public GetAllKeys createGetAllKeys() {
        return new GetAllKeys();
    }

    public GetAllKeysResponse createGetAllKeysResponse() {
        return new GetAllKeysResponse();
    }

    public DeleteKey createDeleteKey() {
        return new DeleteKey();
    }

    public DeleteKeyResponse createDeleteKeyResponse() {
        return new DeleteKeyResponse();
    }

    public CreatePKCS10CSR createCreatePKCS10CSR() {
        return new CreatePKCS10CSR();
    }

    public CreatePKCS10CSRResponse createCreatePKCS10CSRResponse() {
        return new CreatePKCS10CSRResponse();
    }

    public CreateSelfSignedCertificate createCreateSelfSignedCertificate() {
        return new CreateSelfSignedCertificate();
    }

    public X509V3Extension createX509V3Extension() {
        return new X509V3Extension();
    }

    public CreateSelfSignedCertificateResponse createCreateSelfSignedCertificateResponse() {
        return new CreateSelfSignedCertificateResponse();
    }

    public UploadCertificate createUploadCertificate() {
        return new UploadCertificate();
    }

    public UploadCertificateResponse createUploadCertificateResponse() {
        return new UploadCertificateResponse();
    }

    public GetCertificate createGetCertificate() {
        return new GetCertificate();
    }

    public GetCertificateResponse createGetCertificateResponse() {
        return new GetCertificateResponse();
    }

    public X509Certificate createX509Certificate() {
        return new X509Certificate();
    }

    public GetAllCertificates createGetAllCertificates() {
        return new GetAllCertificates();
    }

    public GetAllCertificatesResponse createGetAllCertificatesResponse() {
        return new GetAllCertificatesResponse();
    }

    public DeleteCertificate createDeleteCertificate() {
        return new DeleteCertificate();
    }

    public DeleteCertificateResponse createDeleteCertificateResponse() {
        return new DeleteCertificateResponse();
    }

    public CreateCertificationPath createCreateCertificationPath() {
        return new CreateCertificationPath();
    }

    public CertificateIDs createCertificateIDs() {
        return new CertificateIDs();
    }

    public CreateCertificationPathResponse createCreateCertificationPathResponse() {
        return new CreateCertificationPathResponse();
    }

    public GetCertificationPath createGetCertificationPath() {
        return new GetCertificationPath();
    }

    public GetCertificationPathResponse createGetCertificationPathResponse() {
        return new GetCertificationPathResponse();
    }

    public GetAllCertificationPaths createGetAllCertificationPaths() {
        return new GetAllCertificationPaths();
    }

    public GetAllCertificationPathsResponse createGetAllCertificationPathsResponse() {
        return new GetAllCertificationPathsResponse();
    }

    public DeleteCertificationPath createDeleteCertificationPath() {
        return new DeleteCertificationPath();
    }

    public DeleteCertificationPathResponse createDeleteCertificationPathResponse() {
        return new DeleteCertificationPathResponse();
    }

    public UploadPassphrase createUploadPassphrase() {
        return new UploadPassphrase();
    }

    public UploadPassphraseResponse createUploadPassphraseResponse() {
        return new UploadPassphraseResponse();
    }

    public GetAllPassphrases createGetAllPassphrases() {
        return new GetAllPassphrases();
    }

    public GetAllPassphrasesResponse createGetAllPassphrasesResponse() {
        return new GetAllPassphrasesResponse();
    }

    public DeletePassphrase createDeletePassphrase() {
        return new DeletePassphrase();
    }

    public DeletePassphraseResponse createDeletePassphraseResponse() {
        return new DeletePassphraseResponse();
    }

    public AddServerCertificateAssignment createAddServerCertificateAssignment() {
        return new AddServerCertificateAssignment();
    }

    public AddServerCertificateAssignmentResponse createAddServerCertificateAssignmentResponse() {
        return new AddServerCertificateAssignmentResponse();
    }

    public RemoveServerCertificateAssignment createRemoveServerCertificateAssignment() {
        return new RemoveServerCertificateAssignment();
    }

    public RemoveServerCertificateAssignmentResponse createRemoveServerCertificateAssignmentResponse() {
        return new RemoveServerCertificateAssignmentResponse();
    }

    public ReplaceServerCertificateAssignment createReplaceServerCertificateAssignment() {
        return new ReplaceServerCertificateAssignment();
    }

    public ReplaceServerCertificateAssignmentResponse createReplaceServerCertificateAssignmentResponse() {
        return new ReplaceServerCertificateAssignmentResponse();
    }

    public GetAssignedServerCertificates createGetAssignedServerCertificates() {
        return new GetAssignedServerCertificates();
    }

    public GetAssignedServerCertificatesResponse createGetAssignedServerCertificatesResponse() {
        return new GetAssignedServerCertificatesResponse();
    }

    public UploadCRL.AnyParameters createUploadCRLAnyParameters() {
        return new UploadCRL.AnyParameters();
    }

    public UploadCRLResponse createUploadCRLResponse() {
        return new UploadCRLResponse();
    }

    public GetCRL createGetCRL() {
        return new GetCRL();
    }

    public GetCRLResponse createGetCRLResponse() {
        return new GetCRLResponse();
    }

    public CRL createCRL() {
        return new CRL();
    }

    public GetAllCRLs createGetAllCRLs() {
        return new GetAllCRLs();
    }

    public GetAllCRLsResponse createGetAllCRLsResponse() {
        return new GetAllCRLsResponse();
    }

    public DeleteCRL createDeleteCRL() {
        return new DeleteCRL();
    }

    public DeleteCRLResponse createDeleteCRLResponse() {
        return new DeleteCRLResponse();
    }

    public TrustAnchor createTrustAnchor() {
        return new TrustAnchor();
    }

    public CreateCertPathValidationPolicy.AnyParameters createCreateCertPathValidationPolicyAnyParameters() {
        return new CreateCertPathValidationPolicy.AnyParameters();
    }

    public CreateCertPathValidationPolicyResponse createCreateCertPathValidationPolicyResponse() {
        return new CreateCertPathValidationPolicyResponse();
    }

    public GetCertPathValidationPolicy createGetCertPathValidationPolicy() {
        return new GetCertPathValidationPolicy();
    }

    public GetCertPathValidationPolicyResponse createGetCertPathValidationPolicyResponse() {
        return new GetCertPathValidationPolicyResponse();
    }

    public GetAllCertPathValidationPolicies createGetAllCertPathValidationPolicies() {
        return new GetAllCertPathValidationPolicies();
    }

    public GetAllCertPathValidationPoliciesResponse createGetAllCertPathValidationPoliciesResponse() {
        return new GetAllCertPathValidationPoliciesResponse();
    }

    public DeleteCertPathValidationPolicy createDeleteCertPathValidationPolicy() {
        return new DeleteCertPathValidationPolicy();
    }

    public DeleteCertPathValidationPolicyResponse createDeleteCertPathValidationPolicyResponse() {
        return new DeleteCertPathValidationPolicyResponse();
    }

    public SetClientAuthenticationRequired createSetClientAuthenticationRequired() {
        return new SetClientAuthenticationRequired();
    }

    public SetClientAuthenticationRequiredResponse createSetClientAuthenticationRequiredResponse() {
        return new SetClientAuthenticationRequiredResponse();
    }

    public GetClientAuthenticationRequired createGetClientAuthenticationRequired() {
        return new GetClientAuthenticationRequired();
    }

    public GetClientAuthenticationRequiredResponse createGetClientAuthenticationRequiredResponse() {
        return new GetClientAuthenticationRequiredResponse();
    }

    public AddCertPathValidationPolicyAssignment createAddCertPathValidationPolicyAssignment() {
        return new AddCertPathValidationPolicyAssignment();
    }

    public AddCertPathValidationPolicyAssignmentResponse createAddCertPathValidationPolicyAssignmentResponse() {
        return new AddCertPathValidationPolicyAssignmentResponse();
    }

    public RemoveCertPathValidationPolicyAssignment createRemoveCertPathValidationPolicyAssignment() {
        return new RemoveCertPathValidationPolicyAssignment();
    }

    public RemoveCertPathValidationPolicyAssignmentResponse createRemoveCertPathValidationPolicyAssignmentResponse() {
        return new RemoveCertPathValidationPolicyAssignmentResponse();
    }

    public ReplaceCertPathValidationPolicyAssignment createReplaceCertPathValidationPolicyAssignment() {
        return new ReplaceCertPathValidationPolicyAssignment();
    }

    public ReplaceCertPathValidationPolicyAssignmentResponse createReplaceCertPathValidationPolicyAssignmentResponse() {
        return new ReplaceCertPathValidationPolicyAssignmentResponse();
    }

    public GetAssignedCertPathValidationPolicies createGetAssignedCertPathValidationPolicies() {
        return new GetAssignedCertPathValidationPolicies();
    }

    public GetAssignedCertPathValidationPoliciesResponse createGetAssignedCertPathValidationPoliciesResponse() {
        return new GetAssignedCertPathValidationPoliciesResponse();
    }

    public DNAttributeTypeAndValue createDNAttributeTypeAndValue() {
        return new DNAttributeTypeAndValue();
    }

    public MultiValuedRDN createMultiValuedRDN() {
        return new MultiValuedRDN();
    }

    public BasicRequestAttribute createBasicRequestAttribute() {
        return new BasicRequestAttribute();
    }

    public TLSServerCapabilities createTLSServerCapabilities() {
        return new TLSServerCapabilities();
    }

    public KeystoreCapabilities.AnyElement createKeystoreCapabilitiesAnyElement() {
        return new KeystoreCapabilities.AnyElement();
    }

    public CertPathValidationPolicy.AnyParameters createCertPathValidationPolicyAnyParameters() {
        return new CertPathValidationPolicy.AnyParameters();
    }

    public CertPathValidationParameters.AnyParameters createCertPathValidationParametersAnyParameters() {
        return new CertPathValidationParameters.AnyParameters();
    }

    public PassphraseAttribute.Extension createPassphraseAttributeExtension() {
        return new PassphraseAttribute.Extension();
    }

    public CertificationPath.AnyElement createCertificationPathAnyElement() {
        return new CertificationPath.AnyElement();
    }

    public AlgorithmIdentifier.AnyParameters createAlgorithmIdentifierAnyParameters() {
        return new AlgorithmIdentifier.AnyParameters();
    }

    public CSRAttribute.AnyAttribute createCSRAttributeAnyAttribute() {
        return new CSRAttribute.AnyAttribute();
    }

    public DistinguishedName.AnyAttribute createDistinguishedNameAnyAttribute() {
        return new DistinguishedName.AnyAttribute();
    }

    public KeyAttribute.Extension createKeyAttributeExtension() {
        return new KeyAttribute.Extension();
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", name="Capabilities")
    public JAXBElement<Capabilities> createCapabilities(Capabilities value) {
        return new JAXBElement<Capabilities>(_Capabilities_QNAME, Capabilities.class, null, value);
    }

    @XmlElementDecl(namespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", name="CertPathValidationPolicyID", scope=GetAssignedCertPathValidationPoliciesResponse.class)
    @XmlJavaTypeAdapter(value=CollapsedStringAdapter.class)
    @XmlID
    public JAXBElement<String> createGetAssignedCertPathValidationPoliciesResponseCertPathValidationPolicyID(String value) {
        return new JAXBElement<String>(_GetAssignedCertPathValidationPoliciesResponseCertPathValidationPolicyID_QNAME, String.class, GetAssignedCertPathValidationPoliciesResponse.class, value);
    }
}

