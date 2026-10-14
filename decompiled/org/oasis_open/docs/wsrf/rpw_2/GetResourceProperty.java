/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsrf.rpw_2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.namespace.QName;
import org.oasis_open.docs.wsn.br_2.ObjectFactory;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyResponse;
import org.oasis_open.docs.wsrf.rpw_2.InvalidResourcePropertyQNameFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnavailableFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsrf/rpw-2", name="GetResourceProperty")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface GetResourceProperty {
    @WebMethod(operationName="GetResourceProperty")
    @WebResult(name="GetResourcePropertyResponse", targetNamespace="http://docs.oasis-open.org/wsrf/rp-2", partName="GetResourcePropertyResponse")
    public GetResourcePropertyResponse getResourceProperty(@WebParam(partName="GetResourcePropertyRequest", name="GetResourceProperty", targetNamespace="http://docs.oasis-open.org/wsrf/rp-2") QName var1) throws ResourceUnavailableFault, InvalidResourcePropertyQNameFault, ResourceUnknownFault;
}

