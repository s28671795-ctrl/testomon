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
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
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

@WebService(targetNamespace="http://docs.oasis-open.org/wsn/bw-2", name="NotificationProducer")
@XmlSeeAlso(value={ObjectFactory.class, org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class, org.oasis_open.docs.wsn.br_2.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public interface NotificationProducer {
    @WebMethod(operationName="GetCurrentMessage")
    @WebResult(name="GetCurrentMessageResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="GetCurrentMessageResponse")
    public GetCurrentMessageResponse getCurrentMessage(@WebParam(partName="GetCurrentMessageRequest", name="GetCurrentMessage", targetNamespace="http://docs.oasis-open.org/wsn/b-2") GetCurrentMessage var1) throws InvalidTopicExpressionFault, NoCurrentMessageOnTopicFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, MultipleTopicsSpecifiedFault, ResourceUnknownFault;

    @WebMethod(operationName="Subscribe")
    @WebResult(name="SubscribeResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="SubscribeResponse")
    public SubscribeResponse subscribe(@WebParam(partName="SubscribeRequest", name="Subscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Subscribe var1) throws InvalidTopicExpressionFault, InvalidMessageContentExpressionFault, TopicNotSupportedFault, TopicExpressionDialectUnknownFault, InvalidProducerPropertiesExpressionFault, NotifyMessageNotSupportedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidFilterFault, UnsupportedPolicyRequestFault, SubscribeCreationFailedFault, UnrecognizedPolicyRequestFault;
}

