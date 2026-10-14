/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.IdGenerator;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotificationProducer;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.SubscriptionManager;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.w3c.dom.Element;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.NotificationProducer", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="NotificationProducerService", portName="NotificationProducerPort")
public class Publisher
implements NotificationProducer,
Referencable {
    public static final String WSN_URI = "http://docs.oasis-open.org/wsn/b-2";
    public static final QName QNAME_TOPIC_EXPRESSION = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpression");
    private final Callback callback;
    private final String address;
    private final Endpoint endpoint;
    private final IdGenerator idGenerator = new IdGenerator();

    public Publisher(Callback callback, String address) {
        this.callback = callback;
        this.address = address;
        this.endpoint = callback == null || address == null ? null : WSNHelper.getInstance().publish(address, this, new Class[0]);
    }

    public void stop() {
        if (this.endpoint != null) {
            this.endpoint.stop();
        }
    }

    @Override
    public W3CEndpointReference getEpr() {
        if (this.endpoint == null) {
            return null;
        }
        return this.endpoint.getEndpointReference(W3CEndpointReference.class, new Element[0]);
    }

    @Override
    public SubscribeResponse subscribe(@WebParam(partName="SubscribeRequest", name="Subscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Subscribe subscribeRequest) throws InvalidTopicExpressionFault, ResourceUnknownFault, InvalidProducerPropertiesExpressionFault, UnrecognizedPolicyRequestFault, TopicExpressionDialectUnknownFault, NotifyMessageNotSupportedFault, InvalidFilterFault, UnsupportedPolicyRequestFault, InvalidMessageContentExpressionFault, SubscribeCreationFailedFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault {
        TopicExpressionType topic = null;
        if (subscribeRequest.getFilter() != null) {
            for (Object f : subscribeRequest.getFilter().getAny()) {
                JAXBElement e = null;
                if (f instanceof JAXBElement) {
                    e = (JAXBElement)f;
                    f = e.getValue();
                }
                if (!(f instanceof TopicExpressionType)) continue;
                if (!e.getName().equals(QNAME_TOPIC_EXPRESSION)) {
                    InvalidTopicExpressionFaultType fault = new InvalidTopicExpressionFaultType();
                    throw new InvalidTopicExpressionFault("Unrecognized TopicExpression: " + e, fault);
                }
                topic = (TopicExpressionType)f;
            }
        }
        if (topic == null) {
            InvalidFilterFaultType fault = new InvalidFilterFaultType();
            throw new InvalidFilterFault("Must specify a topic to subscribe on", fault);
        }
        PublisherSubscription pub = new PublisherSubscription(topic);
        SubscribeResponse response = new SubscribeResponse();
        response.setSubscriptionReference(pub.getEpr());
        this.callback.subscribe(topic);
        return response;
    }

    protected void unsubscribe(TopicExpressionType topic) {
        this.callback.unsubscribe(topic);
    }

    @Override
    public GetCurrentMessageResponse getCurrentMessage(@WebParam(partName="GetCurrentMessageRequest", name="GetCurrentMessage", targetNamespace="http://docs.oasis-open.org/wsn/b-2") GetCurrentMessage getCurrentMessageRequest) throws InvalidTopicExpressionFault, ResourceUnknownFault, TopicExpressionDialectUnknownFault, MultipleTopicsSpecifiedFault, NoCurrentMessageOnTopicFault, TopicNotSupportedFault {
        NoCurrentMessageOnTopicFaultType fault = new NoCurrentMessageOnTopicFaultType();
        throw new NoCurrentMessageOnTopicFault("There is no current message on this topic.", fault);
    }

    @WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.SubscriptionManager", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="SubscriptionManagerService", portName="SubscriptionManagerPort")
    protected class PublisherSubscription
    implements SubscriptionManager {
        private final String id;
        private final TopicExpressionType topic;
        private final Endpoint endpoint;

        public PublisherSubscription(TopicExpressionType topic) {
            this.topic = topic;
            this.id = Publisher.this.idGenerator.generateSanitizedId();
            this.endpoint = WSNHelper.getInstance().publish(Publisher.this.address + "/subscriptions/" + this.id, this, new Class[0]);
        }

        public W3CEndpointReference getEpr() {
            return this.endpoint.getEndpointReference(W3CEndpointReference.class, new Element[0]);
        }

        @Override
        public UnsubscribeResponse unsubscribe(@WebParam(partName="UnsubscribeRequest", name="Unsubscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Unsubscribe unsubscribeRequest) throws ResourceUnknownFault, UnableToDestroySubscriptionFault {
            Publisher.this.unsubscribe(this.topic);
            return new UnsubscribeResponse();
        }

        @Override
        public RenewResponse renew(@WebParam(partName="RenewRequest", name="Renew", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Renew renewRequest) throws ResourceUnknownFault, UnacceptableTerminationTimeFault {
            throw new UnsupportedOperationException();
        }
    }

    public static interface Callback {
        public void subscribe(TopicExpressionType var1);

        public void unsubscribe(TopicExpressionType var1);
    }
}

