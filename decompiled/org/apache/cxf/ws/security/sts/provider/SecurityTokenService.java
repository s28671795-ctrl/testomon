/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.sts.provider;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseCollectionType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenResponseType;
import org.apache.cxf.ws.security.sts.provider.model.RequestSecurityTokenType;
import org.apache.cxf.ws.security.sts.provider.model.utility.ObjectFactory;

@WebService(targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512/wsdl", name="SecurityTokenService")
@XmlSeeAlso(value={org.apache.cxf.ws.security.sts.provider.model.ObjectFactory.class, org.apache.cxf.ws.security.sts.provider.model.wstrust14.ObjectFactory.class, org.apache.cxf.ws.security.sts.provider.model.secext.ObjectFactory.class, ObjectFactory.class, org.apache.cxf.ws.security.sts.provider.model.xmldsig.ObjectFactory.class, org.apache.cxf.ws.addressing.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface SecurityTokenService {
    @WebResult(name="RequestSecurityTokenResponse", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="response")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/KET", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/KETFinal")
    @WebMethod(operationName="KeyExchangeToken")
    public RequestSecurityTokenResponseType keyExchangeToken(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);

    @WebResult(name="RequestSecurityTokenResponseCollection", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="responseCollection")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTRC/IssueFinal")
    @WebMethod(operationName="Issue")
    public RequestSecurityTokenResponseCollectionType issue(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);

    @WebResult(name="RequestSecurityTokenResponse", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="response")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Issue", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTRC/IssueFinal")
    @WebMethod(operationName="Issue")
    public RequestSecurityTokenResponseType issueSingle(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);

    @WebResult(name="RequestSecurityTokenResponse", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="response")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Cancel", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/CancelFinal")
    @WebMethod(operationName="Cancel")
    public RequestSecurityTokenResponseType cancel(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);

    @WebResult(name="RequestSecurityTokenResponse", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="response")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Validate", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/ValidateFinal")
    @WebMethod(operationName="Validate")
    public RequestSecurityTokenResponseType validate(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);

    @WebResult(name="RequestSecurityTokenResponseCollection", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="responseCollection")
    @WebMethod(operationName="RequestCollection")
    public RequestSecurityTokenResponseCollectionType requestCollection(@WebParam(partName="requestCollection", name="RequestSecurityTokenCollection", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenCollectionType var1);

    @WebResult(name="RequestSecurityTokenResponse", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512", partName="response")
    @Action(input="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RST/Renew", output="http://docs.oasis-open.org/ws-sx/ws-trust/200512/RSTR/RenewFinal")
    @WebMethod(operationName="Renew")
    public RequestSecurityTokenResponseType renew(@WebParam(partName="request", name="RequestSecurityToken", targetNamespace="http://docs.oasis-open.org/ws-sx/ws-trust/200512") RequestSecurityTokenType var1);
}

