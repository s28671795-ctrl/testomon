/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.bw_2;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.oasis_open.docs.wsn.b_2.DestroyPullPoint;
import org.oasis_open.docs.wsn.b_2.DestroyPullPointResponse;
import org.oasis_open.docs.wsn.b_2.GetMessages;
import org.oasis_open.docs.wsn.b_2.GetMessagesResponse;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.br_2.ObjectFactory;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroyPullPointFault;
import org.oasis_open.docs.wsn.bw_2.UnableToGetMessagesFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/bw-2", name="PullPoint")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface PullPoint {
    @WebMethod(operationName="Notify")
    @Oneway
    public void notify(@WebParam(partName="Notify", name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Notify var1);

    @WebMethod(operationName="DestroyPullPoint")
    @WebResult(name="DestroyPullPointResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="DestroyPullPointResponse")
    public DestroyPullPointResponse destroyPullPoint(@WebParam(partName="DestroyPullPointRequest", name="DestroyPullPoint", targetNamespace="http://docs.oasis-open.org/wsn/b-2") DestroyPullPoint var1) throws UnableToDestroyPullPointFault, ResourceUnknownFault;

    @WebMethod(operationName="GetMessages")
    @WebResult(name="GetMessagesResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="GetMessagesResponse")
    public GetMessagesResponse getMessages(@WebParam(partName="GetMessagesRequest", name="GetMessages", targetNamespace="http://docs.oasis-open.org/wsn/b-2") GetMessages var1) throws UnableToGetMessagesFault, ResourceUnknownFault;
}

