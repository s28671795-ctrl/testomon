/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.math.BigInteger;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.advancedsecurity.wsdl.AlgorithmIdentifier;
import org.onvif.ver10.advancedsecurity.wsdl.CRL;
import org.onvif.ver10.advancedsecurity.wsdl.CSRAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationParameters;
import org.onvif.ver10.advancedsecurity.wsdl.CertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.CertificateIDs;
import org.onvif.ver10.advancedsecurity.wsdl.CertificationPath;
import org.onvif.ver10.advancedsecurity.wsdl.CreateCertPathValidationPolicy;
import org.onvif.ver10.advancedsecurity.wsdl.DistinguishedName;
import org.onvif.ver10.advancedsecurity.wsdl.KeyAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.ObjectFactory;
import org.onvif.ver10.advancedsecurity.wsdl.PassphraseAttribute;
import org.onvif.ver10.advancedsecurity.wsdl.TrustAnchor;
import org.onvif.ver10.advancedsecurity.wsdl.UploadCRL;
import org.onvif.ver10.advancedsecurity.wsdl.X509Certificate;
import org.onvif.ver10.advancedsecurity.wsdl.X509V3Extension;

@WebService(targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", name="Keystore")
@XmlSeeAlso(value={ObjectFactory.class})
public interface Keystore {
    @WebMethod(operationName="GetAllKeys", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllKeys")
    @RequestWrapper(localName="GetAllKeys", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllKeys")
    @ResponseWrapper(localName="GetAllKeysResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllKeysResponse")
    @WebResult(name="KeyAttribute", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<KeyAttribute> getAllKeys();

    @WebMethod(operationName="GetAllPassphrases", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllPassphrases")
    @RequestWrapper(localName="GetAllPassphrases", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllPassphrases")
    @ResponseWrapper(localName="GetAllPassphrasesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllPassphrasesResponse")
    @WebResult(name="PassphraseAttribute", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<PassphraseAttribute> getAllPassphrases();

    @WebMethod(operationName="GetCRL", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetCRL")
    @RequestWrapper(localName="GetCRL", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCRL")
    @ResponseWrapper(localName="GetCRLResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCRLResponse")
    @WebResult(name="Crl", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public CRL getCRL(@WebParam(name="CrlID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="CreateCertificationPath", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/CreateCertificationPath")
    @RequestWrapper(localName="CreateCertificationPath", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateCertificationPath")
    @ResponseWrapper(localName="CreateCertificationPathResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateCertificationPathResponse")
    @WebResult(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String createCertificationPath(@WebParam(name="CertificateIDs", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") CertificateIDs var1, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2);

    @WebMethod(operationName="GetAllCertPathValidationPolicies", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllCertPathValidationPolicies")
    @RequestWrapper(localName="GetAllCertPathValidationPolicies", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertPathValidationPolicies")
    @ResponseWrapper(localName="GetAllCertPathValidationPoliciesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertPathValidationPoliciesResponse")
    @WebResult(name="CertPathValidationPolicy", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<CertPathValidationPolicy> getAllCertPathValidationPolicies();

    @WebMethod(operationName="CreatePKCS10CSR", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/CreatePKCS10CSR")
    @RequestWrapper(localName="CreatePKCS10CSR", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreatePKCS10CSR")
    @ResponseWrapper(localName="CreatePKCS10CSRResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreatePKCS10CSRResponse")
    @WebResult(name="PKCS10CSR", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public byte[] createPKCS10CSR(@WebParam(name="Subject", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") DistinguishedName var1, @WebParam(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(name="CSRAttribute", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") List<CSRAttribute> var3, @WebParam(name="SignatureAlgorithm", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") AlgorithmIdentifier var4);

    @WebMethod(operationName="UploadKeyPairInPKCS8", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/UploadKeyPairInPKCS8")
    @RequestWrapper(localName="UploadKeyPairInPKCS8", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadKeyPairInPKCS8")
    @ResponseWrapper(localName="UploadKeyPairInPKCS8Response", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadKeyPairInPKCS8Response")
    @WebResult(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String uploadKeyPairInPKCS8(@WebParam(name="KeyPair", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") byte[] var1, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(name="EncryptionPassphraseID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var3, @WebParam(name="EncryptionPassphrase", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var4);

    @WebMethod(operationName="UploadCertificate", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/UploadCertificate")
    @RequestWrapper(localName="UploadCertificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCertificate")
    @ResponseWrapper(localName="UploadCertificateResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateResponse")
    public void uploadCertificate(@WebParam(name="Certificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") byte[] var1, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(name="KeyAlias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var3, @WebParam(name="PrivateKeyRequired", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Boolean var4, @WebParam(mode=WebParam.Mode.OUT, name="CertificateID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<String> var5, @WebParam(mode=WebParam.Mode.OUT, name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<String> var6);

    @WebMethod(operationName="UploadCRL", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/UploadCRL")
    @RequestWrapper(localName="UploadCRL", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCRL")
    @ResponseWrapper(localName="UploadCRLResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCRLResponse")
    @WebResult(name="CrlID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String uploadCRL(@WebParam(name="Crl", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") byte[] var1, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(name="anyParameters", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") UploadCRL.AnyParameters var3);

    @WebMethod(operationName="DeleteCertificationPath", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeleteCertificationPath")
    @RequestWrapper(localName="DeleteCertificationPath", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificationPath")
    @ResponseWrapper(localName="DeleteCertificationPathResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificationPathResponse")
    public void deleteCertificationPath(@WebParam(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="DeleteCertificate", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeleteCertificate")
    @RequestWrapper(localName="DeleteCertificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificate")
    @ResponseWrapper(localName="DeleteCertificateResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertificateResponse")
    public void deleteCertificate(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetKeyStatus", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetKeyStatus")
    @RequestWrapper(localName="GetKeyStatus", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetKeyStatus")
    @ResponseWrapper(localName="GetKeyStatusResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetKeyStatusResponse")
    @WebResult(name="KeyStatus", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String getKeyStatus(@WebParam(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="CreateRSAKeyPair", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/CreateRSAKeyPair")
    @RequestWrapper(localName="CreateRSAKeyPair", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateRSAKeyPair")
    @ResponseWrapper(localName="CreateRSAKeyPairResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateRSAKeyPairResponse")
    public void createRSAKeyPair(@WebParam(name="KeyLength", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") BigInteger var1, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(mode=WebParam.Mode.OUT, name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<String> var3, @WebParam(mode=WebParam.Mode.OUT, name="EstimatedCreationTime", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<Duration> var4);

    @WebMethod(operationName="DeletePassphrase", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeletePassphrase")
    @RequestWrapper(localName="DeletePassphrase", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeletePassphrase")
    @ResponseWrapper(localName="DeletePassphraseResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeletePassphraseResponse")
    public void deletePassphrase(@WebParam(name="PassphraseID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetCertificate", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetCertificate")
    @RequestWrapper(localName="GetCertificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertificate")
    @ResponseWrapper(localName="GetCertificateResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertificateResponse")
    @WebResult(name="Certificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public X509Certificate getCertificate(@WebParam(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="UploadPassphrase", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/UploadPassphrase")
    @RequestWrapper(localName="UploadPassphrase", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadPassphrase")
    @ResponseWrapper(localName="UploadPassphraseResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadPassphraseResponse")
    @WebResult(name="PassphraseID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String uploadPassphrase(@WebParam(name="Passphrase", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1, @WebParam(name="PassphraseAlias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2);

    @WebMethod(operationName="DeleteCRL", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeleteCRL")
    @RequestWrapper(localName="DeleteCRL", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCRL")
    @ResponseWrapper(localName="DeleteCRLResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCRLResponse")
    public void deleteCRL(@WebParam(name="CrlID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetCertPathValidationPolicy", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetCertPathValidationPolicy")
    @RequestWrapper(localName="GetCertPathValidationPolicy", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertPathValidationPolicy")
    @ResponseWrapper(localName="GetCertPathValidationPolicyResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertPathValidationPolicyResponse")
    @WebResult(name="CertPathValidationPolicy", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public CertPathValidationPolicy getCertPathValidationPolicy(@WebParam(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="DeleteKey", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeleteKey")
    @RequestWrapper(localName="DeleteKey", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteKey")
    @ResponseWrapper(localName="DeleteKeyResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteKeyResponse")
    public void deleteKey(@WebParam(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetPrivateKeyStatus", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetPrivateKeyStatus")
    @RequestWrapper(localName="GetPrivateKeyStatus", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetPrivateKeyStatus")
    @ResponseWrapper(localName="GetPrivateKeyStatusResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetPrivateKeyStatusResponse")
    @WebResult(name="hasPrivateKey", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public boolean getPrivateKeyStatus(@WebParam(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetAllCertificationPaths", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllCertificationPaths")
    @RequestWrapper(localName="GetAllCertificationPaths", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificationPaths")
    @ResponseWrapper(localName="GetAllCertificationPathsResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificationPathsResponse")
    @WebResult(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<String> getAllCertificationPaths();

    @WebMethod(operationName="GetCertificationPath", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetCertificationPath")
    @RequestWrapper(localName="GetCertificationPath", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertificationPath")
    @ResponseWrapper(localName="GetCertificationPathResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetCertificationPathResponse")
    @WebResult(name="CertificationPath", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public CertificationPath getCertificationPath(@WebParam(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetAllCertificates", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllCertificates")
    @RequestWrapper(localName="GetAllCertificates", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificates")
    @ResponseWrapper(localName="GetAllCertificatesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCertificatesResponse")
    @WebResult(name="Certificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<X509Certificate> getAllCertificates();

    @WebMethod(operationName="CreateCertPathValidationPolicy", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/CreateCertPathValidationPolicy")
    @RequestWrapper(localName="CreateCertPathValidationPolicy", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateCertPathValidationPolicy")
    @ResponseWrapper(localName="CreateCertPathValidationPolicyResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateCertPathValidationPolicyResponse")
    @WebResult(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String createCertPathValidationPolicy(@WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1, @WebParam(name="Parameters", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") CertPathValidationParameters var2, @WebParam(name="TrustAnchor", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") List<TrustAnchor> var3, @WebParam(name="anyParameters", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") CreateCertPathValidationPolicy.AnyParameters var4);

    @WebMethod(operationName="DeleteCertPathValidationPolicy", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/DeleteCertPathValidationPolicy")
    @RequestWrapper(localName="DeleteCertPathValidationPolicy", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertPathValidationPolicy")
    @ResponseWrapper(localName="DeleteCertPathValidationPolicyResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.DeleteCertPathValidationPolicyResponse")
    public void deleteCertPathValidationPolicy(@WebParam(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="UploadCertificateWithPrivateKeyInPKCS12", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/UploadCertificateWithPrivateKeyInPKCS12")
    @RequestWrapper(localName="UploadCertificateWithPrivateKeyInPKCS12", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateWithPrivateKeyInPKCS12")
    @ResponseWrapper(localName="UploadCertificateWithPrivateKeyInPKCS12Response", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.UploadCertificateWithPrivateKeyInPKCS12Response")
    public void uploadCertificateWithPrivateKeyInPKCS12(@WebParam(name="CertWithPrivateKey", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") byte[] var1, @WebParam(name="CertificationPathAlias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2, @WebParam(name="KeyAlias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var3, @WebParam(name="IgnoreAdditionalCertificates", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Boolean var4, @WebParam(name="IntegrityPassphraseID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var5, @WebParam(name="EncryptionPassphraseID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var6, @WebParam(name="Passphrase", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var7, @WebParam(mode=WebParam.Mode.OUT, name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<String> var8, @WebParam(mode=WebParam.Mode.OUT, name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") Holder<String> var9);

    @WebMethod(operationName="GetAllCRLs", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAllCRLs")
    @RequestWrapper(localName="GetAllCRLs", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCRLs")
    @ResponseWrapper(localName="GetAllCRLsResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAllCRLsResponse")
    @WebResult(name="Crl", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<CRL> getAllCRLs();

    @WebMethod(operationName="CreateSelfSignedCertificate", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/CreateSelfSignedCertificate")
    @RequestWrapper(localName="CreateSelfSignedCertificate", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateSelfSignedCertificate")
    @ResponseWrapper(localName="CreateSelfSignedCertificateResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.CreateSelfSignedCertificateResponse")
    @WebResult(name="CertificateID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public String createSelfSignedCertificate(@WebParam(name="X509Version", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") BigInteger var1, @WebParam(name="Subject", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") DistinguishedName var2, @WebParam(name="KeyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var3, @WebParam(name="Alias", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var4, @WebParam(name="notValidBefore", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") XMLGregorianCalendar var5, @WebParam(name="notValidAfter", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") XMLGregorianCalendar var6, @WebParam(name="SignatureAlgorithm", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") AlgorithmIdentifier var7, @WebParam(name="Extension", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") List<X509V3Extension> var8);
}

