/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.events.wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.onvif.ver10.events.wsdl.Capabilities;
import org.onvif.ver10.events.wsdl.CreatePullPointSubscription;
import org.onvif.ver10.events.wsdl.CreatePullPointSubscriptionResponse;
import org.onvif.ver10.events.wsdl.GetEventProperties;
import org.onvif.ver10.events.wsdl.GetEventPropertiesResponse;
import org.onvif.ver10.events.wsdl.ObjectFactory;

@WebService(targetNamespace="http://www.onvif.org/ver10/events/wsdl", name="EventPortType")
@XmlSeeAlso(value={org.oasis_open.docs.wsrf.r_2.ObjectFactory.class, ObjectFactory.class, org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class, org.oasis_open.docs.wsn.b_2.ObjectFactory.class, org.oasis_open.docs.wsn.t_1.ObjectFactory.class})
public interface EventPortType {
    @WebMethod(operationName="GetEventProperties", action="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetEventPropertiesRequest")
    @Action(input="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetEventPropertiesRequest", output="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetEventPropertiesResponse")
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="GetEventPropertiesResponse", targetNamespace="http://www.onvif.org/ver10/events/wsdl", partName="parameters")
    public GetEventPropertiesResponse getEventProperties(@WebParam(partName="parameters", name="GetEventProperties", targetNamespace="http://www.onvif.org/ver10/events/wsdl") GetEventProperties var1);

    @WebMethod(operationName="CreatePullPointSubscription", action="http://www.onvif.org/ver10/events/wsdl/EventPortType/CreatePullPointSubscriptionRequest")
    @Action(input="http://www.onvif.org/ver10/events/wsdl/EventPortType/CreatePullPointSubscriptionRequest", output="http://www.onvif.org/ver10/events/wsdl/EventPortType/CreatePullPointSubscriptionResponse", fault={})
    @SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
    @WebResult(name="CreatePullPointSubscriptionResponse", targetNamespace="http://www.onvif.org/ver10/events/wsdl", partName="parameters")
    public CreatePullPointSubscriptionResponse createPullPointSubscription(@WebParam(partName="parameters", name="CreatePullPointSubscription", targetNamespace="http://www.onvif.org/ver10/events/wsdl") CreatePullPointSubscription var1) throws TopicNotSupportedFault, TopicExpressionDialectUnknownFault, InvalidTopicExpressionFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, UnacceptableInitialTerminationTimeFault, NotifyMessageNotSupportedFault, ResourceUnknownFault, UnsupportedPolicyRequestFault, InvalidFilterFault, SubscribeCreationFailedFault, UnrecognizedPolicyRequestFault;

    @WebMethod(operationName="GetServiceCapabilities", action="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetServiceCapabilitiesRequest")
    @Action(input="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetServiceCapabilitiesRequest", output="http://www.onvif.org/ver10/events/wsdl/EventPortType/GetServiceCapabilitiesResponse")
    @RequestWrapper(localName="GetServiceCapabilities", targetNamespace="http://www.onvif.org/ver10/events/wsdl", className="org.onvif.ver10.events.wsdl.GetServiceCapabilities")
    @ResponseWrapper(localName="GetServiceCapabilitiesResponse", targetNamespace="http://www.onvif.org/ver10/events/wsdl", className="org.onvif.ver10.events.wsdl.GetServiceCapabilitiesResponse")
    @WebResult(name="Capabilities", targetNamespace="http://www.onvif.org/ver10/events/wsdl")
    public Capabilities getServiceCapabilities();
}

