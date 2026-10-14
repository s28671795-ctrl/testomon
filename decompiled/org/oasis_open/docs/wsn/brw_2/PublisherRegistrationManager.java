/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.brw_2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.oasis_open.docs.wsn.br_2.DestroyRegistration;
import org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse;
import org.oasis_open.docs.wsn.br_2.ObjectFactory;
import org.oasis_open.docs.wsn.brw_2.ResourceNotDestroyedFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/brw-2", name="PublisherRegistrationManager")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface PublisherRegistrationManager {
    @WebMethod(operationName="DestroyRegistration")
    @WebResult(name="DestroyRegistrationResponse", targetNamespace="http://docs.oasis-open.org/wsn/br-2", partName="DestroyRegistrationResponse")
    public DestroyRegistrationResponse destroyRegistration(@WebParam(partName="DestroyRegistrationRequest", name="DestroyRegistration", targetNamespace="http://docs.oasis-open.org/wsn/br-2") DestroyRegistration var1) throws ResourceNotDestroyedFault, ResourceUnknownFault;
}

