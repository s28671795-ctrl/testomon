/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.util.GregorianCalendar;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.AbstractNotificationBroker;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.PauseSubscription;
import org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.QueryExpressionType;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.ResumeSubscription;
import org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;
import org.oasis_open.docs.wsn.b_2.UseRaw;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.PausableSubscriptionManager;
import org.oasis_open.docs.wsn.bw_2.PauseFailedFault;
import org.oasis_open.docs.wsn.bw_2.ResumeFailedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.bf_2.BaseFaultType;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.PausableSubscriptionManager")
public abstract class AbstractSubscription
extends AbstractEndpoint
implements PausableSubscriptionManager {
    public static final String WSN_URI = "http://docs.oasis-open.org/wsn/b-2";
    public static final String XPATH1_URI = "http://www.w3.org/TR/1999/REC-xpath-19991116";
    public static final QName QNAME_TOPIC_EXPRESSION = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpression");
    public static final QName QNAME_PRODUCER_PROPERTIES = new QName("http://docs.oasis-open.org/wsn/b-2", "ProducerProperties");
    public static final QName QNAME_MESSAGE_CONTENT = new QName("http://docs.oasis-open.org/wsn/b-2", "MessageContent");
    public static final QName QNAME_USE_RAW = new QName("http://docs.oasis-open.org/wsn/b-2", "UseRaw");
    protected DatatypeFactory datatypeFactory;
    protected XMLGregorianCalendar terminationTime;
    protected boolean useRaw;
    protected TopicExpressionType topic;
    protected QueryExpressionType contentFilter;
    protected W3CEndpointReference consumerReference;
    protected AbstractNotificationBroker broker;

    public AbstractSubscription(String name) {
        super(name);
        try {
            this.datatypeFactory = DatatypeFactory.newInstance();
        }
        catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Unable to initialize subscription", e);
        }
    }

    @Override
    @WebMethod(operationName="Renew")
    @WebResult(name="RenewResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="RenewResponse")
    public RenewResponse renew(@WebParam(name="Renew", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="RenewRequest") Renew renewRequest) throws ResourceUnknownFault, UnacceptableTerminationTimeFault {
        XMLGregorianCalendar time = this.validateTerminationTime(renewRequest.getTerminationTime());
        this.setTerminationTime(time);
        this.renew(time);
        RenewResponse response = new RenewResponse();
        response.setTerminationTime(time);
        response.setCurrentTime(this.getCurrentTime());
        return response;
    }

    @Override
    @WebMethod(operationName="Unsubscribe")
    @WebResult(name="UnsubscribeResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="UnsubscribeResponse")
    public UnsubscribeResponse unsubscribe(@WebParam(name="Unsubscribe", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="UnsubscribeRequest") Unsubscribe unsubscribeRequest) throws ResourceUnknownFault, UnableToDestroySubscriptionFault {
        this.broker.unsubscribe(this.getAddress());
        return new UnsubscribeResponse();
    }

    @Override
    @WebMethod(operationName="PauseSubscription")
    @WebResult(name="PauseSubscriptionResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="PauseSubscriptionResponse")
    public PauseSubscriptionResponse pauseSubscription(@WebParam(name="PauseSubscription", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="PauseSubscriptionRequest") PauseSubscription pauseSubscriptionRequest) throws PauseFailedFault, ResourceUnknownFault {
        this.pause();
        return new PauseSubscriptionResponse();
    }

    @Override
    @WebMethod(operationName="ResumeSubscription")
    @WebResult(name="ResumeSubscriptionResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="ResumeSubscriptionResponse")
    public ResumeSubscriptionResponse resumeSubscription(@WebParam(name="ResumeSubscription", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="ResumeSubscriptionRequest") ResumeSubscription resumeSubscriptionRequest) throws ResourceUnknownFault, ResumeFailedFault {
        this.resume();
        return new ResumeSubscriptionResponse();
    }

    protected XMLGregorianCalendar validateInitialTerminationTime(String value) throws UnacceptableInitialTerminationTimeFault {
        XMLGregorianCalendar tt = this.parseTerminationTime(value);
        if (tt == null) {
            UnacceptableInitialTerminationTimeFaultType fault = new UnacceptableInitialTerminationTimeFaultType();
            throw new UnacceptableInitialTerminationTimeFault("Unable to parse initial termination time: '" + value + "'", fault);
        }
        XMLGregorianCalendar ct = this.getCurrentTime();
        int c = tt.compare(ct);
        if (c == -1 || c == 0) {
            UnacceptableInitialTerminationTimeFaultType fault = new UnacceptableInitialTerminationTimeFaultType();
            fault.setMinimumTime(ct);
            throw new UnacceptableInitialTerminationTimeFault("Invalid initial termination time", fault);
        }
        return tt;
    }

    protected XMLGregorianCalendar validateTerminationTime(String value) throws UnacceptableTerminationTimeFault {
        XMLGregorianCalendar tt = this.parseTerminationTime(value);
        if (tt == null) {
            UnacceptableTerminationTimeFaultType fault = new UnacceptableTerminationTimeFaultType();
            throw new UnacceptableTerminationTimeFault("Unable to parse termination time: '" + value + "'", fault);
        }
        XMLGregorianCalendar ct = this.getCurrentTime();
        int c = tt.compare(ct);
        if (c == -1 || c == 0) {
            UnacceptableTerminationTimeFaultType fault = new UnacceptableTerminationTimeFaultType();
            fault.setMinimumTime(ct);
            throw new UnacceptableTerminationTimeFault("Invalid termination time", fault);
        }
        return tt;
    }

    protected XMLGregorianCalendar parseTerminationTime(String value) {
        try {
            Duration d = this.datatypeFactory.newDuration(value);
            XMLGregorianCalendar c = this.getCurrentTime();
            c.add(d);
            return c;
        }
        catch (Exception d) {
            try {
                Duration d2 = this.datatypeFactory.newDurationDayTime(value);
                XMLGregorianCalendar c = this.getCurrentTime();
                c.add(d2);
                return c;
            }
            catch (Exception d2) {
                try {
                    Duration d3 = this.datatypeFactory.newDurationYearMonth(value);
                    XMLGregorianCalendar c = this.getCurrentTime();
                    c.add(d3);
                    return c;
                }
                catch (Exception exception) {
                    try {
                        return this.datatypeFactory.newXMLGregorianCalendar(value);
                    }
                    catch (Exception exception2) {
                        return null;
                    }
                }
            }
        }
    }

    protected XMLGregorianCalendar getCurrentTime() {
        return this.datatypeFactory.newXMLGregorianCalendar(new GregorianCalendar());
    }

    public XMLGregorianCalendar getTerminationTime() {
        return this.terminationTime;
    }

    public void setTerminationTime(XMLGregorianCalendar terminationTime) {
        this.terminationTime = terminationTime;
    }

    public void create(Subscribe subscribeRequest) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnrecognizedPolicyRequestFault, UnsupportedPolicyRequestFault {
        this.validateSubscription(subscribeRequest);
        this.start();
    }

    protected abstract void start() throws SubscribeCreationFailedFault;

    protected abstract void pause() throws PauseFailedFault;

    protected abstract void resume() throws ResumeFailedFault;

    protected abstract void renew(XMLGregorianCalendar var1) throws UnacceptableTerminationTimeFault;

    protected void unsubscribe() throws UnableToDestroySubscriptionFault {
        try {
            this.unregister();
        }
        catch (EndpointRegistrationException e) {
            UnableToDestroySubscriptionFaultType fault = new UnableToDestroySubscriptionFaultType();
            throw new UnableToDestroySubscriptionFault("Error unregistering endpoint", fault, e);
        }
    }

    /*
     * WARNING - void declaration
     */
    protected void validateSubscription(Subscribe subscribeRequest) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnrecognizedPolicyRequestFault, UnsupportedPolicyRequestFault {
        BaseFaultType fault;
        JAXBElement e;
        this.consumerReference = subscribeRequest.getConsumerReference();
        if (subscribeRequest.getInitialTerminationTime() != null && !subscribeRequest.getInitialTerminationTime().isNil() && subscribeRequest.getInitialTerminationTime().getValue() != null) {
            String strTerminationTime = subscribeRequest.getInitialTerminationTime().getValue();
            this.terminationTime = this.validateInitialTerminationTime(strTerminationTime.trim());
        }
        if (subscribeRequest.getFilter() != null) {
            for (Object object : subscribeRequest.getFilter().getAny()) {
                void var3_3;
                e = null;
                if (object instanceof JAXBElement) {
                    e = (JAXBElement)object;
                    Object t2 = e.getValue();
                }
                if (var3_3 instanceof TopicExpressionType) {
                    if (!e.getName().equals(QNAME_TOPIC_EXPRESSION)) {
                        InvalidTopicExpressionFaultType fault2 = new InvalidTopicExpressionFaultType();
                        throw new InvalidTopicExpressionFault("Unrecognized TopicExpression: " + e, fault2);
                    }
                    this.topic = (TopicExpressionType)var3_3;
                    continue;
                }
                if (var3_3 instanceof QueryExpressionType) {
                    if (e != null && e.getName().equals(QNAME_PRODUCER_PROPERTIES)) {
                        InvalidProducerPropertiesExpressionFaultType fault3 = new InvalidProducerPropertiesExpressionFaultType();
                        throw new InvalidProducerPropertiesExpressionFault("ProducerProperties are not supported", fault3);
                    }
                    if (e != null && e.getName().equals(QNAME_MESSAGE_CONTENT)) {
                        if (this.contentFilter != null) {
                            InvalidMessageContentExpressionFaultType fault4 = new InvalidMessageContentExpressionFaultType();
                            throw new InvalidMessageContentExpressionFault("Only one MessageContent filter can be specified", fault4);
                        }
                        this.contentFilter = (QueryExpressionType)var3_3;
                        if (this.contentFilter.getDialect() != null) continue;
                        this.contentFilter.setDialect(XPATH1_URI);
                        continue;
                    }
                    InvalidFilterFaultType fault5 = new InvalidFilterFaultType();
                    throw new InvalidFilterFault("Unrecognized filter: " + (e != null ? e.getName() : var3_3), fault5);
                }
                InvalidFilterFaultType fault6 = new InvalidFilterFaultType();
                throw new InvalidFilterFault("Unrecognized filter: " + (e != null ? e.getName() : var3_3), fault6);
            }
        }
        if (subscribeRequest.getSubscriptionPolicy() != null) {
            for (Object e2 : subscribeRequest.getSubscriptionPolicy().getAny()) {
                void var3_9;
                e = null;
                if (e2 instanceof JAXBElement) {
                    e = (JAXBElement)e2;
                    Object t3 = e.getValue();
                }
                if (var3_9 instanceof UseRaw) {
                    this.useRaw = true;
                    continue;
                }
                UnrecognizedPolicyRequestFaultType fault7 = new UnrecognizedPolicyRequestFaultType();
                throw new UnrecognizedPolicyRequestFault("Unrecognized policy: " + var3_9, fault7);
            }
        }
        if (this.consumerReference == null) {
            fault = new SubscribeCreationFailedFaultType();
            throw new SubscribeCreationFailedFault("Invalid ConsumerReference: null", (SubscribeCreationFailedFaultType)fault);
        }
        if (this.topic == null) {
            fault = new InvalidFilterFaultType();
            throw new InvalidFilterFault("Must specify a topic to subscribe on", (InvalidFilterFaultType)fault);
        }
        if (this.contentFilter != null && !this.contentFilter.getDialect().equals(XPATH1_URI)) {
            fault = new InvalidMessageContentExpressionFaultType();
            throw new InvalidMessageContentExpressionFault("Unsupported MessageContent dialect: '" + this.contentFilter.getDialect() + "'", (InvalidMessageContentExpressionFaultType)fault);
        }
    }

    public AbstractNotificationBroker getBroker() {
        return this.broker;
    }

    public void setBroker(AbstractNotificationBroker broker) {
        this.broker = broker;
    }
}

