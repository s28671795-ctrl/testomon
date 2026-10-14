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
import org.oasis_open.docs.wsn.b_2.PauseSubscription;
import org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.ResumeSubscription;
import org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;
import org.oasis_open.docs.wsn.bw_2.PauseFailedFault;
import org.oasis_open.docs.wsn.bw_2.ResumeFailedFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableTerminationTimeFault;
import org.oasis_open.docs.wsrf.rp_2.ObjectFactory;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/bw-2", name="PausableSubscriptionManager")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.oasis_open.docs.wsn.br_2.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface PausableSubscriptionManager {
    @WebMethod(operationName="Unsubscribe")
    @WebResult(name="UnsubscribeResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="UnsubscribeResponse")
    public UnsubscribeResponse unsubscribe(@WebParam(partName="UnsubscribeRequest", name="Unsubscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Unsubscribe var1) throws UnableToDestroySubscriptionFault, ResourceUnknownFault;

    @WebMethod(operationName="PauseSubscription")
    @WebResult(name="PauseSubscriptionResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="PauseSubscriptionResponse")
    public PauseSubscriptionResponse pauseSubscription(@WebParam(partName="PauseSubscriptionRequest", name="PauseSubscription", targetNamespace="http://docs.oasis-open.org/wsn/b-2") PauseSubscription var1) throws PauseFailedFault, ResourceUnknownFault;

    @WebMethod(operationName="ResumeSubscription")
    @WebResult(name="ResumeSubscriptionResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="ResumeSubscriptionResponse")
    public ResumeSubscriptionResponse resumeSubscription(@WebParam(partName="ResumeSubscriptionRequest", name="ResumeSubscription", targetNamespace="http://docs.oasis-open.org/wsn/b-2") ResumeSubscription var1) throws ResourceUnknownFault, ResumeFailedFault;

    @WebMethod(operationName="Renew")
    @WebResult(name="RenewResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="RenewResponse")
    public RenewResponse renew(@WebParam(partName="RenewRequest", name="Renew", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Renew var1) throws UnacceptableTerminationTimeFault, ResourceUnknownFault;
}

