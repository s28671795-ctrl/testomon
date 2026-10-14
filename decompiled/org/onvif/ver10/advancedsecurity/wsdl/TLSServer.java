/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.advancedsecurity.wsdl;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.onvif.ver10.advancedsecurity.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", name="TLSServer")
@XmlSeeAlso(value={ObjectFactory.class})
public interface TLSServer {
    @WebMethod(operationName="RemoveServerCertificateAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/RemoveServerCertificateAssignment")
    @RequestWrapper(localName="RemoveServerCertificateAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignment")
    @ResponseWrapper(localName="RemoveServerCertificateAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.RemoveServerCertificateAssignmentResponse")
    public void removeServerCertificateAssignment(@WebParam(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetClientAuthenticationRequired", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetClientAuthenticationRequired")
    @RequestWrapper(localName="GetClientAuthenticationRequired", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequired")
    @ResponseWrapper(localName="GetClientAuthenticationRequiredResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetClientAuthenticationRequiredResponse")
    @WebResult(name="clientAuthenticationRequired", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public boolean getClientAuthenticationRequired();

    @WebMethod(operationName="ReplaceServerCertificateAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/ReplaceServerCertificateAssignment")
    @RequestWrapper(localName="ReplaceServerCertificateAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignment")
    @ResponseWrapper(localName="ReplaceServerCertificateAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.ReplaceServerCertificateAssignmentResponse")
    public void replaceServerCertificateAssignment(@WebParam(name="OldCertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1, @WebParam(name="NewCertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2);

    @WebMethod(operationName="AddCertPathValidationPolicyAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/AddCertPathValidationPolicyAssignment")
    @RequestWrapper(localName="AddCertPathValidationPolicyAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName="AddCertPathValidationPolicyAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.AddCertPathValidationPolicyAssignmentResponse")
    public void addCertPathValidationPolicyAssignment(@WebParam(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="RemoveCertPathValidationPolicyAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/RemoveCertPathValidationPolicyAssignment")
    @RequestWrapper(localName="RemoveCertPathValidationPolicyAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName="RemoveCertPathValidationPolicyAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.RemoveCertPathValidationPolicyAssignmentResponse")
    public void removeCertPathValidationPolicyAssignment(@WebParam(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="ReplaceCertPathValidationPolicyAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/ReplaceCertPathValidationPolicyAssignment")
    @RequestWrapper(localName="ReplaceCertPathValidationPolicyAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignment")
    @ResponseWrapper(localName="ReplaceCertPathValidationPolicyAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.ReplaceCertPathValidationPolicyAssignmentResponse")
    public void replaceCertPathValidationPolicyAssignment(@WebParam(name="OldCertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1, @WebParam(name="NewCertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var2);

    @WebMethod(operationName="AddServerCertificateAssignment", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/AddServerCertificateAssignment")
    @RequestWrapper(localName="AddServerCertificateAssignment", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignment")
    @ResponseWrapper(localName="AddServerCertificateAssignmentResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.AddServerCertificateAssignmentResponse")
    public void addServerCertificateAssignment(@WebParam(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") String var1);

    @WebMethod(operationName="GetAssignedServerCertificates", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAssignedServerCertificates")
    @RequestWrapper(localName="GetAssignedServerCertificates", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificates")
    @ResponseWrapper(localName="GetAssignedServerCertificatesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAssignedServerCertificatesResponse")
    @WebResult(name="CertificationPathID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<String> getAssignedServerCertificates();

    @WebMethod(operationName="GetAssignedCertPathValidationPolicies", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/GetAssignedCertPathValidationPolicies")
    @RequestWrapper(localName="GetAssignedCertPathValidationPolicies", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPolicies")
    @ResponseWrapper(localName="GetAssignedCertPathValidationPoliciesResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.GetAssignedCertPathValidationPoliciesResponse")
    @WebResult(name="CertPathValidationPolicyID", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl")
    public List<String> getAssignedCertPathValidationPolicies();

    @WebMethod(operationName="SetClientAuthenticationRequired", action="http://www.onvif.org/ver10/advancedsecurity/wsdl/SetClientAuthenticationRequired")
    @RequestWrapper(localName="SetClientAuthenticationRequired", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequired")
    @ResponseWrapper(localName="SetClientAuthenticationRequiredResponse", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl", className="org.onvif.ver10.advancedsecurity.wsdl.SetClientAuthenticationRequiredResponse")
    public void setClientAuthenticationRequired(@WebParam(name="clientAuthenticationRequired", targetNamespace="http://www.onvif.org/ver10/advancedsecurity/wsdl") boolean var1);
}

