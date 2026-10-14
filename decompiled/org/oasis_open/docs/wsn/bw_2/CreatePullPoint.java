/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.oasis_open.docs.wsn.b_2.CreatePullPointResponse;
import org.oasis_open.docs.wsn.br_2.ObjectFactory;
import org.oasis_open.docs.wsn.bw_2.UnableToCreatePullPointFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/bw-2", name="CreatePullPoint")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface CreatePullPoint {
    @WebMethod(operationName="CreatePullPoint")
    @WebResult(name="CreatePullPointResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="CreatePullPointResponse")
    public CreatePullPointResponse createPullPoint(@WebParam(partName="CreatePullPointRequest", name="CreatePullPoint", targetNamespace="http://docs.oasis-open.org/wsn/b-2") org.oasis_open.docs.wsn.b_2.CreatePullPoint var1) throws UnableToCreatePullPointFault;
}

