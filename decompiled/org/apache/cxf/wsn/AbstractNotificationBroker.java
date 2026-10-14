/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.management.ObjectName;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.AbstractPublisher;
import org.apache.cxf.wsn.AbstractSubscription;
import org.apache.cxf.wsn.EndpointMBean;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.apache.cxf.wsn.NotificationBrokerMBean;
import org.apache.cxf.wsn.util.IdGenerator;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse;
import org.oasis_open.docs.wsn.brw_2.NotificationBroker;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.brw_2.ResourceNotDestroyedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyResponse;
import org.oasis_open.docs.wsrf.rp_2.InvalidResourcePropertyQNameFaultType;
import org.oasis_open.docs.wsrf.rpw_2.GetResourceProperty;
import org.oasis_open.docs.wsrf.rpw_2.InvalidResourcePropertyQNameFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnavailableFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.brw_2.NotificationBroker")
public abstract class AbstractNotificationBroker
extends AbstractEndpoint
implements NotificationBroker,
NotificationBrokerMBean,
GetResourceProperty {
    public static final String NAMESPACE_URI = "http://docs.oasis-open.org/wsn/b-2";
    public static final String PREFIX = "wsnt";
    public static final QName TOPIC_EXPRESSION_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpression", "wsnt");
    public static final QName FIXED_TOPIC_SET_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "FixedTopicSet", "wsnt");
    public static final QName TOPIC_EXPRESSION_DIALECT_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpressionDialect", "wsnt");
    public static final QName TOPIC_SET_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicSet", "wsnt");
    private static final Logger LOGGER = LogUtils.getL7dLogger(AbstractNotificationBroker.class);
    private IdGenerator idGenerator = new IdGenerator();
    private AbstractPublisher anonymousPublisher;
    private Map<String, AbstractPublisher> publishers;
    private List<AbstractPublisher> nonContactPublishers;
    private Map<String, AbstractSubscription> subscriptions = new ConcurrentHashMap<String, AbstractSubscription>();

    public AbstractNotificationBroker(String name) {
        super(name);
        this.publishers = new ConcurrentHashMap<String, AbstractPublisher>();
        this.nonContactPublishers = new CopyOnWriteArrayList<AbstractPublisher>();
    }

    @Override
    public ObjectName getMBeanName() {
        try {
            return new ObjectName("org.apache.cxf.service.wsn:type=WSNotificationBroker");
        }
        catch (Exception e) {
            return null;
        }
    }

    public void init() throws Exception {
        this.register();
        this.anonymousPublisher = this.createPublisher("AnonymousPublisher");
        this.anonymousPublisher.setAddress(new URI(this.getAddress()).resolve(this.anonymousPublisher.getName()).toString());
        this.anonymousPublisher.register();
    }

    public void destroy() throws Exception {
        this.anonymousPublisher.destroy();
        this.unregister();
    }

    @Override
    public List<String> getPublisher() {
        return new ArrayList<String>(this.publishers.keySet());
    }

    @Override
    public List<String> getSubscriptions() {
        return new ArrayList<String>(this.subscriptions.keySet());
    }

    @Override
    public EndpointMBean getPublisher(String name) {
        return this.publishers.get(name);
    }

    @Override
    public EndpointMBean getSubscription(String name) {
        return this.subscriptions.get(name);
    }

    @Override
    public EndpointMBean getAnonymousPublisher() {
        return this.anonymousPublisher;
    }

    @Override
    @WebMethod(operationName="Notify")
    @Oneway
    public void notify(@WebParam(name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="Notify") Notify notify) {
        LOGGER.finest("Notify");
        this.handleNotify(notify);
    }

    protected void handleNotify(Notify notify) {
        for (NotificationMessageHolderType messageHolder : notify.getNotificationMessage()) {
            W3CEndpointReference producerReference = messageHolder.getProducerReference();
            AbstractPublisher publisher = this.getPublisher(producerReference);
            if (publisher == null) continue;
            publisher.notify(messageHolder);
        }
    }

    protected AbstractPublisher getPublisher(W3CEndpointReference producerReference) {
        AbstractPublisher publisher = null;
        if (producerReference != null) {
            String address = WSNHelper.getInstance().getWSAAddress(producerReference);
            publisher = this.publishers.get(address);
        }
        if (publisher == null) {
            publisher = this.anonymousPublisher;
        }
        return publisher;
    }

    @Override
    @WebMethod(operationName="Subscribe")
    @WebResult(name="SubscribeResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="SubscribeResponse")
    public SubscribeResponse subscribe(@WebParam(name="Subscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="SubscribeRequest") Subscribe subscribeRequest) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, ResourceUnknownFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault {
        LOGGER.finest("Subscribe");
        return this.handleSubscribe(subscribeRequest, null);
    }

    public SubscribeResponse handleSubscribe(Subscribe subscribeRequest, EndpointManager manager) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault {
        AbstractSubscription subscription = null;
        boolean success = false;
        try {
            subscription = this.createSubscription(this.idGenerator.generateSanitizedId());
            subscription.setBroker(this);
            this.subscriptions.put(subscription.getAddress(), subscription);
            subscription.create(subscribeRequest);
            if (manager != null) {
                subscription.setManager(manager);
            }
            subscription.register();
            SubscribeResponse response = new SubscribeResponse();
            response.setSubscriptionReference(subscription.getEpr());
            success = true;
            SubscribeResponse subscribeResponse = response;
            return subscribeResponse;
        }
        catch (EndpointRegistrationException e) {
            LOGGER.log(Level.WARNING, "Unable to register new endpoint", e);
            SubscribeCreationFailedFaultType fault = new SubscribeCreationFailedFaultType();
            throw new SubscribeCreationFailedFault("Unable to register new endpoint", fault, e);
        }
        finally {
            if (!success && subscription != null) {
                if (subscription.getAddress() != null) {
                    this.subscriptions.remove(subscription.getAddress());
                }
                try {
                    subscription.unsubscribe();
                }
                catch (UnableToDestroySubscriptionFault e) {
                    LOGGER.log(Level.INFO, "Error destroying subscription", e);
                }
            }
        }
    }

    public void unsubscribe(String address) throws UnableToDestroySubscriptionFault {
        AbstractSubscription subscription = this.subscriptions.remove(address);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    @WebMethod(operationName="GetCurrentMessage")
    @WebResult(name="GetCurrentMessageResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="GetCurrentMessageResponse")
    public GetCurrentMessageResponse getCurrentMessage(@WebParam(name="GetCurrentMessage", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="GetCurrentMessageRequest") GetCurrentMessage getCurrentMessageRequest) throws InvalidTopicExpressionFault, MultipleTopicsSpecifiedFault, NoCurrentMessageOnTopicFault, ResourceUnknownFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault {
        LOGGER.finest("GetCurrentMessage");
        NoCurrentMessageOnTopicFaultType fault = new NoCurrentMessageOnTopicFaultType();
        throw new NoCurrentMessageOnTopicFault("There is no current message on this topic.", fault);
    }

    @Override
    @WebMethod(operationName="RegisterPublisher")
    @WebResult(name="RegisterPublisherResponse", targetNamespace="http://docs.oasis-open.org/wsn/br-1", partName="RegisterPublisherResponse")
    public RegisterPublisherResponse registerPublisher(@WebParam(name="RegisterPublisher", targetNamespace="http://docs.oasis-open.org/wsn/br-1", partName="RegisterPublisherRequest") RegisterPublisher registerPublisherRequest) throws InvalidTopicExpressionFault, PublisherRegistrationFailedFault, PublisherRegistrationRejectedFault, ResourceUnknownFault, TopicNotSupportedFault {
        LOGGER.finest("RegisterPublisher");
        return this.handleRegisterPublisher(registerPublisherRequest);
    }

    public RegisterPublisherResponse handleRegisterPublisher(RegisterPublisher registerPublisherRequest) throws InvalidTopicExpressionFault, PublisherRegistrationFailedFault, PublisherRegistrationRejectedFault, ResourceUnknownFault, TopicNotSupportedFault {
        AbstractPublisher publisher = null;
        boolean success = false;
        try {
            publisher = this.createPublisher(this.idGenerator.generateSanitizedId());
            publisher.register();
            publisher.create(registerPublisherRequest);
            RegisterPublisherResponse response = new RegisterPublisherResponse();
            response.setPublisherRegistrationReference(publisher.getEpr());
            if (publisher.getPublisherReference() != null) {
                this.publishers.put(WSNHelper.getInstance().getWSAAddress(publisher.getPublisherReference()), publisher);
            } else {
                this.nonContactPublishers.add(publisher);
            }
            success = true;
            RegisterPublisherResponse registerPublisherResponse = response;
            return registerPublisherResponse;
        }
        catch (EndpointRegistrationException e) {
            LOGGER.log(Level.WARNING, "Unable to register new endpoint", e);
            PublisherRegistrationFailedFaultType fault = new PublisherRegistrationFailedFaultType();
            throw new PublisherRegistrationFailedFault("Unable to register new endpoint", fault, e);
        }
        finally {
            if (!success && publisher != null) {
                try {
                    publisher.destroy();
                }
                catch (ResourceNotDestroyedFault e) {
                    LOGGER.log(Level.INFO, "Error destroying publisher", e);
                }
            }
        }
    }

    protected abstract AbstractPublisher createPublisher(String var1);

    protected abstract AbstractSubscription createSubscription(String var1);

    @Override
    @WebResult(name="GetResourcePropertyResponse", targetNamespace="http://docs.oasis-open.org/wsrf/rp-2", partName="GetResourcePropertyResponse")
    @WebMethod(operationName="GetResourceProperty")
    public GetResourcePropertyResponse getResourceProperty(@WebParam(partName="GetResourcePropertyRequest", name="GetResourceProperty", targetNamespace="http://docs.oasis-open.org/wsrf/rp-2") QName getResourcePropertyRequest) throws ResourceUnavailableFault, ResourceUnknownFault, InvalidResourcePropertyQNameFault {
        LOGGER.finest("GetResourceProperty");
        return this.handleGetResourceProperty(getResourcePropertyRequest);
    }

    protected GetResourcePropertyResponse handleGetResourceProperty(QName property) throws ResourceUnavailableFault, ResourceUnknownFault, InvalidResourcePropertyQNameFault {
        InvalidResourcePropertyQNameFaultType fault = new InvalidResourcePropertyQNameFaultType();
        throw new InvalidResourcePropertyQNameFault("Invalid resource property QName: " + property, fault);
    }
}

