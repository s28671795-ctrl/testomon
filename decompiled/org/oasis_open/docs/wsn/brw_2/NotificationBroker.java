/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.brw_2;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rp_2.ObjectFactory;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/brw-2", name="NotificationBroker")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.oasis_open.docs.wsn.br_2.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface NotificationBroker {
    @WebMethod(operationName="Notify")
    @Oneway
    public void notify(@WebParam(partName="Notify", name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Notify var1);

    @WebMethod(operationName="GetCurrentMessage")
    @WebResult(name="GetCurrentMessageResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="GetCurrentMessageResponse")
    public GetCurrentMessageResponse getCurrentMessage(@WebParam(partName="GetCurrentMessageRequest", name="GetCurrentMessage", targetNamespace="http://docs.oasis-open.org/wsn/b-2") GetCurrentMessage var1) throws ResourceUnknownFault, InvalidTopicExpressionFault, NoCurrentMessageOnTopicFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, MultipleTopicsSpecifiedFault;

    @WebMethod(operationName="RegisterPublisher")
    @WebResult(name="RegisterPublisherResponse", targetNamespace="http://docs.oasis-open.org/wsn/br-2", partName="RegisterPublisherResponse")
    public RegisterPublisherResponse registerPublisher(@WebParam(partName="RegisterPublisherRequest", name="RegisterPublisher", targetNamespace="http://docs.oasis-open.org/wsn/br-2") RegisterPublisher var1) throws UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, PublisherRegistrationFailedFault, TopicNotSupportedFault, PublisherRegistrationRejectedFault;

    @WebMethod(operationName="Subscribe")
    @WebResult(name="SubscribeResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="SubscribeResponse")
    public SubscribeResponse subscribe(@WebParam(partName="SubscribeRequest", name="Subscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Subscribe var1) throws NotifyMessageNotSupportedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, InvalidMessageContentExpressionFault, TopicNotSupportedFault, TopicExpressionDialectUnknownFault, InvalidProducerPropertiesExpressionFault, SubscribeCreationFailedFault, UnrecognizedPolicyRequestFault, InvalidFilterFault, UnsupportedPolicyRequestFault;
}

