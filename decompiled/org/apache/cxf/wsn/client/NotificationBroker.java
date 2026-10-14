/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import java.util.Collections;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.client.Registration;
import org.apache.cxf.wsn.client.Subscription;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.QueryExpressionType;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.UseRaw;
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
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.w3c.dom.Document;

public class NotificationBroker
implements Referencable {
    public static final String WSN_URI = "http://docs.oasis-open.org/wsn/b-2";
    public static final String XPATH1_URI = "http://www.w3.org/TR/1999/REC-xpath-19991116";
    public static final QName QNAME_TOPIC_EXPRESSION = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpression");
    public static final QName QNAME_MESSAGE_CONTENT = new QName("http://docs.oasis-open.org/wsn/b-2", "MessageContent");
    public static final QName QNAME_INITIAL_TERMINATION_TIME = new QName("http://docs.oasis-open.org/wsn/b-2", "InitialTerminationTime");
    public static final QName QNAME_PULLPOINT_QUEUE_NAME = new QName("http://docs.oasis-open.org/wsn/b-2", "pullPointQueueName");
    private org.oasis_open.docs.wsn.brw_2.NotificationBroker broker;
    private final W3CEndpointReference epr;
    private Class<?>[] extraClasses;
    private JAXBContext context;

    public NotificationBroker(String address, Class<?> ... cls) {
        this(WSNHelper.getInstance().createWSA(address), cls);
    }

    public NotificationBroker(W3CEndpointReference epr, Class<?> ... cls) {
        this.epr = epr;
        this.extraClasses = cls;
    }

    public void setExtraClasses(Class<?> ... c) {
        this.extraClasses = c;
    }

    public synchronized org.oasis_open.docs.wsn.brw_2.NotificationBroker getBroker() {
        if (this.broker == null) {
            WSNHelper helper = WSNHelper.getInstance();
            if (helper.supportsExtraClasses()) {
                this.broker = WSNHelper.getInstance().getPort(this.epr, org.oasis_open.docs.wsn.brw_2.NotificationBroker.class, this.extraClasses);
            } else {
                this.broker = WSNHelper.getInstance().getPort(this.epr, org.oasis_open.docs.wsn.brw_2.NotificationBroker.class, new Class[0]);
                if (this.extraClasses != null && this.extraClasses.length > 0) {
                    try {
                        this.context = JAXBContext.newInstance(this.extraClasses);
                    }
                    catch (JAXBException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return this.broker;
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.epr;
    }

    public void notify(String topic, Object msg) {
        this.notify(null, topic, msg);
    }

    public void notify(Referencable publisher, String topic, Object msg) {
        this.getBroker();
        if (this.context != null) {
            try {
                DOMResult result = new DOMResult();
                this.context.createMarshaller().marshal(msg, result);
                msg = result.getNode();
                if (msg instanceof Document) {
                    msg = ((Document)msg).getDocumentElement();
                }
            }
            catch (JAXBException result) {
                // empty catch block
            }
        }
        Notify notify = new Notify();
        NotificationMessageHolderType holder = new NotificationMessageHolderType();
        if (publisher != null) {
            holder.setProducerReference(publisher.getEpr());
        }
        if (topic != null) {
            TopicExpressionType topicExp = new TopicExpressionType();
            topicExp.getContent().add(topic);
            holder.setTopic(topicExp);
        }
        holder.setMessage(new NotificationMessageHolderType.Message());
        holder.getMessage().setAny(msg);
        notify.getNotificationMessage().add(holder);
        this.getBroker().notify(notify);
    }

    public Subscription subscribe(Referencable consumer, String topic) throws TopicExpressionDialectUnknownFault, InvalidFilterFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, SubscribeCreationFailedFault, InvalidMessageContentExpressionFault, InvalidTopicExpressionFault, ResourceUnknownFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault, NotifyMessageNotSupportedFault, InvalidProducerPropertiesExpressionFault {
        return this.subscribe(consumer, topic, null, false, null);
    }

    public Subscription subscribe(Referencable consumer, String topic, String xpath) throws TopicExpressionDialectUnknownFault, InvalidFilterFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, SubscribeCreationFailedFault, InvalidMessageContentExpressionFault, InvalidTopicExpressionFault, ResourceUnknownFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault, NotifyMessageNotSupportedFault, InvalidProducerPropertiesExpressionFault {
        return this.subscribe(consumer, topic, xpath, false, null);
    }

    public Subscription subscribe(Referencable consumer, String topic, String xpath, boolean raw, String initialTerminationTime) throws TopicNotSupportedFault, InvalidFilterFault, TopicExpressionDialectUnknownFault, UnacceptableInitialTerminationTimeFault, SubscribeCreationFailedFault, InvalidMessageContentExpressionFault, InvalidTopicExpressionFault, UnrecognizedPolicyRequestFault, UnsupportedPolicyRequestFault, ResourceUnknownFault, NotifyMessageNotSupportedFault, InvalidProducerPropertiesExpressionFault {
        Subscribe subscribeRequest = new Subscribe();
        if (initialTerminationTime != null) {
            subscribeRequest.setInitialTerminationTime(new JAXBElement<String>(QNAME_INITIAL_TERMINATION_TIME, String.class, initialTerminationTime));
        }
        subscribeRequest.setConsumerReference(consumer.getEpr());
        subscribeRequest.setFilter(new FilterType());
        if (topic != null) {
            TopicExpressionType topicExp = new TopicExpressionType();
            topicExp.getContent().add(topic);
            subscribeRequest.getFilter().getAny().add(new JAXBElement<TopicExpressionType>(QNAME_TOPIC_EXPRESSION, TopicExpressionType.class, topicExp));
        }
        if (xpath != null) {
            QueryExpressionType xpathExp = new QueryExpressionType();
            xpathExp.setDialect(XPATH1_URI);
            xpathExp.getContent().add(xpath);
            subscribeRequest.getFilter().getAny().add(new JAXBElement<QueryExpressionType>(QNAME_MESSAGE_CONTENT, QueryExpressionType.class, xpathExp));
        }
        if (raw) {
            subscribeRequest.setSubscriptionPolicy(new Subscribe.SubscriptionPolicy());
            subscribeRequest.getSubscriptionPolicy().getAny().add(new UseRaw());
        }
        SubscribeResponse response = this.getBroker().subscribe(subscribeRequest);
        return new Subscription(response.getSubscriptionReference());
    }

    public List<Object> getCurrentMessage(String topic) throws TopicNotSupportedFault, TopicExpressionDialectUnknownFault, MultipleTopicsSpecifiedFault, InvalidTopicExpressionFault, ResourceUnknownFault, NoCurrentMessageOnTopicFault {
        GetCurrentMessage getCurrentMessageRequest = new GetCurrentMessage();
        if (topic != null) {
            TopicExpressionType topicExp = new TopicExpressionType();
            topicExp.getContent().add(topic);
            getCurrentMessageRequest.setTopic(topicExp);
        }
        GetCurrentMessageResponse response = this.getBroker().getCurrentMessage(getCurrentMessageRequest);
        return response.getAny();
    }

    public Registration registerPublisher(Referencable publisher, String topic) throws TopicNotSupportedFault, PublisherRegistrationFailedFault, UnacceptableInitialTerminationTimeFault, InvalidTopicExpressionFault, ResourceUnknownFault, PublisherRegistrationRejectedFault {
        return this.registerPublisher(publisher, topic, false);
    }

    public Registration registerPublisher(Referencable publisher, String topic, boolean demand) throws TopicNotSupportedFault, PublisherRegistrationFailedFault, UnacceptableInitialTerminationTimeFault, InvalidTopicExpressionFault, ResourceUnknownFault, PublisherRegistrationRejectedFault {
        return this.registerPublisher(publisher, Collections.singletonList(topic), demand);
    }

    public Registration registerPublisher(Referencable publisher, List<String> topics, boolean demand) throws TopicNotSupportedFault, PublisherRegistrationFailedFault, UnacceptableInitialTerminationTimeFault, InvalidTopicExpressionFault, ResourceUnknownFault, PublisherRegistrationRejectedFault {
        RegisterPublisher registerPublisherRequest = new RegisterPublisher();
        registerPublisherRequest.setPublisherReference(publisher.getEpr());
        if (topics != null) {
            for (String topic : topics) {
                TopicExpressionType topicExp = new TopicExpressionType();
                topicExp.getContent().add(topic);
                registerPublisherRequest.getTopic().add(topicExp);
            }
        }
        registerPublisherRequest.setDemand(demand);
        RegisterPublisherResponse response = this.getBroker().registerPublisher(registerPublisherRequest);
        return new Registration(response.getPublisherRegistrationReference());
    }
}

