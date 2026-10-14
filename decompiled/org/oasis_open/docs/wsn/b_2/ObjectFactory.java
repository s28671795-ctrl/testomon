/*
 * Decompiled with CFR 0.152.
 */
package org.oasis_open.docs.wsn.b_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.oasis_open.docs.wsn.b_2.CreatePullPoint;
import org.oasis_open.docs.wsn.b_2.CreatePullPointResponse;
import org.oasis_open.docs.wsn.b_2.DestroyPullPoint;
import org.oasis_open.docs.wsn.b_2.DestroyPullPointResponse;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.GetMessages;
import org.oasis_open.docs.wsn.b_2.GetMessagesResponse;
import org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType;
import org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.NotificationProducerRP;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;
import org.oasis_open.docs.wsn.b_2.PauseSubscription;
import org.oasis_open.docs.wsn.b_2.PauseSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.QueryExpressionType;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.RenewResponse;
import org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;
import org.oasis_open.docs.wsn.b_2.ResumeSubscription;
import org.oasis_open.docs.wsn.b_2.ResumeSubscriptionResponse;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.SubscriptionManagerRP;
import org.oasis_open.docs.wsn.b_2.SubscriptionPolicyType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;
import org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.b_2.UnsubscribeResponse;
import org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType;
import org.oasis_open.docs.wsn.b_2.UseRaw;

@XmlRegistry
public class ObjectFactory {
    private static final QName _TopicExpression_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpression");
    private static final QName _FixedTopicSet_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "FixedTopicSet");
    private static final QName _TopicExpressionDialect_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpressionDialect");
    private static final QName _ConsumerReference_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "ConsumerReference");
    private static final QName _Filter_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "Filter");
    private static final QName _SubscriptionPolicy_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "SubscriptionPolicy");
    private static final QName _CreationTime_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "CreationTime");
    private static final QName _SubscriptionReference_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "SubscriptionReference");
    private static final QName _Topic_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "Topic");
    private static final QName _ProducerReference_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "ProducerReference");
    private static final QName _NotificationMessage_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "NotificationMessage");
    private static final QName _CurrentTime_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "CurrentTime");
    private static final QName _TerminationTime_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TerminationTime");
    private static final QName _ProducerProperties_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "ProducerProperties");
    private static final QName _MessageContent_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "MessageContent");
    private static final QName _SubscribeCreationFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "SubscribeCreationFailedFault");
    private static final QName _InvalidFilterFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "InvalidFilterFault");
    private static final QName _TopicExpressionDialectUnknownFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicExpressionDialectUnknownFault");
    private static final QName _InvalidTopicExpressionFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "InvalidTopicExpressionFault");
    private static final QName _TopicNotSupportedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "TopicNotSupportedFault");
    private static final QName _MultipleTopicsSpecifiedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "MultipleTopicsSpecifiedFault");
    private static final QName _InvalidProducerPropertiesExpressionFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "InvalidProducerPropertiesExpressionFault");
    private static final QName _InvalidMessageContentExpressionFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "InvalidMessageContentExpressionFault");
    private static final QName _UnrecognizedPolicyRequestFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnrecognizedPolicyRequestFault");
    private static final QName _UnsupportedPolicyRequestFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnsupportedPolicyRequestFault");
    private static final QName _NotifyMessageNotSupportedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "NotifyMessageNotSupportedFault");
    private static final QName _UnacceptableInitialTerminationTimeFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnacceptableInitialTerminationTimeFault");
    private static final QName _NoCurrentMessageOnTopicFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "NoCurrentMessageOnTopicFault");
    private static final QName _UnableToGetMessagesFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnableToGetMessagesFault");
    private static final QName _UnableToDestroyPullPointFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnableToDestroyPullPointFault");
    private static final QName _UnableToCreatePullPointFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnableToCreatePullPointFault");
    private static final QName _UnacceptableTerminationTimeFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnacceptableTerminationTimeFault");
    private static final QName _UnableToDestroySubscriptionFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "UnableToDestroySubscriptionFault");
    private static final QName _PauseFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "PauseFailedFault");
    private static final QName _ResumeFailedFault_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "ResumeFailedFault");
    private static final QName _SubscribeInitialTerminationTime_QNAME = new QName("http://docs.oasis-open.org/wsn/b-2", "InitialTerminationTime");

    public Subscribe createSubscribe() {
        return new Subscribe();
    }

    public NotificationMessageHolderType createNotificationMessageHolderType() {
        return new NotificationMessageHolderType();
    }

    public TopicExpressionType createTopicExpressionType() {
        return new TopicExpressionType();
    }

    public NotificationProducerRP createNotificationProducerRP() {
        return new NotificationProducerRP();
    }

    public FilterType createFilterType() {
        return new FilterType();
    }

    public SubscriptionPolicyType createSubscriptionPolicyType() {
        return new SubscriptionPolicyType();
    }

    public SubscriptionManagerRP createSubscriptionManagerRP() {
        return new SubscriptionManagerRP();
    }

    public Notify createNotify() {
        return new Notify();
    }

    public QueryExpressionType createQueryExpressionType() {
        return new QueryExpressionType();
    }

    public UseRaw createUseRaw() {
        return new UseRaw();
    }

    public Subscribe.SubscriptionPolicy createSubscribeSubscriptionPolicy() {
        return new Subscribe.SubscriptionPolicy();
    }

    public SubscribeResponse createSubscribeResponse() {
        return new SubscribeResponse();
    }

    public GetCurrentMessage createGetCurrentMessage() {
        return new GetCurrentMessage();
    }

    public GetCurrentMessageResponse createGetCurrentMessageResponse() {
        return new GetCurrentMessageResponse();
    }

    public SubscribeCreationFailedFaultType createSubscribeCreationFailedFaultType() {
        return new SubscribeCreationFailedFaultType();
    }

    public InvalidFilterFaultType createInvalidFilterFaultType() {
        return new InvalidFilterFaultType();
    }

    public TopicExpressionDialectUnknownFaultType createTopicExpressionDialectUnknownFaultType() {
        return new TopicExpressionDialectUnknownFaultType();
    }

    public InvalidTopicExpressionFaultType createInvalidTopicExpressionFaultType() {
        return new InvalidTopicExpressionFaultType();
    }

    public TopicNotSupportedFaultType createTopicNotSupportedFaultType() {
        return new TopicNotSupportedFaultType();
    }

    public MultipleTopicsSpecifiedFaultType createMultipleTopicsSpecifiedFaultType() {
        return new MultipleTopicsSpecifiedFaultType();
    }

    public InvalidProducerPropertiesExpressionFaultType createInvalidProducerPropertiesExpressionFaultType() {
        return new InvalidProducerPropertiesExpressionFaultType();
    }

    public InvalidMessageContentExpressionFaultType createInvalidMessageContentExpressionFaultType() {
        return new InvalidMessageContentExpressionFaultType();
    }

    public UnrecognizedPolicyRequestFaultType createUnrecognizedPolicyRequestFaultType() {
        return new UnrecognizedPolicyRequestFaultType();
    }

    public UnsupportedPolicyRequestFaultType createUnsupportedPolicyRequestFaultType() {
        return new UnsupportedPolicyRequestFaultType();
    }

    public NotifyMessageNotSupportedFaultType createNotifyMessageNotSupportedFaultType() {
        return new NotifyMessageNotSupportedFaultType();
    }

    public UnacceptableInitialTerminationTimeFaultType createUnacceptableInitialTerminationTimeFaultType() {
        return new UnacceptableInitialTerminationTimeFaultType();
    }

    public NoCurrentMessageOnTopicFaultType createNoCurrentMessageOnTopicFaultType() {
        return new NoCurrentMessageOnTopicFaultType();
    }

    public GetMessages createGetMessages() {
        return new GetMessages();
    }

    public GetMessagesResponse createGetMessagesResponse() {
        return new GetMessagesResponse();
    }

    public DestroyPullPoint createDestroyPullPoint() {
        return new DestroyPullPoint();
    }

    public DestroyPullPointResponse createDestroyPullPointResponse() {
        return new DestroyPullPointResponse();
    }

    public UnableToGetMessagesFaultType createUnableToGetMessagesFaultType() {
        return new UnableToGetMessagesFaultType();
    }

    public UnableToDestroyPullPointFaultType createUnableToDestroyPullPointFaultType() {
        return new UnableToDestroyPullPointFaultType();
    }

    public CreatePullPoint createCreatePullPoint() {
        return new CreatePullPoint();
    }

    public CreatePullPointResponse createCreatePullPointResponse() {
        return new CreatePullPointResponse();
    }

    public UnableToCreatePullPointFaultType createUnableToCreatePullPointFaultType() {
        return new UnableToCreatePullPointFaultType();
    }

    public Renew createRenew() {
        return new Renew();
    }

    public RenewResponse createRenewResponse() {
        return new RenewResponse();
    }

    public UnacceptableTerminationTimeFaultType createUnacceptableTerminationTimeFaultType() {
        return new UnacceptableTerminationTimeFaultType();
    }

    public Unsubscribe createUnsubscribe() {
        return new Unsubscribe();
    }

    public UnsubscribeResponse createUnsubscribeResponse() {
        return new UnsubscribeResponse();
    }

    public UnableToDestroySubscriptionFaultType createUnableToDestroySubscriptionFaultType() {
        return new UnableToDestroySubscriptionFaultType();
    }

    public PauseSubscription createPauseSubscription() {
        return new PauseSubscription();
    }

    public PauseSubscriptionResponse createPauseSubscriptionResponse() {
        return new PauseSubscriptionResponse();
    }

    public ResumeSubscription createResumeSubscription() {
        return new ResumeSubscription();
    }

    public ResumeSubscriptionResponse createResumeSubscriptionResponse() {
        return new ResumeSubscriptionResponse();
    }

    public PauseFailedFaultType createPauseFailedFaultType() {
        return new PauseFailedFaultType();
    }

    public ResumeFailedFaultType createResumeFailedFaultType() {
        return new ResumeFailedFaultType();
    }

    public NotificationMessageHolderType.Message createNotificationMessageHolderTypeMessage() {
        return new NotificationMessageHolderType.Message();
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="TopicExpression")
    public JAXBElement<TopicExpressionType> createTopicExpression(TopicExpressionType value) {
        return new JAXBElement<TopicExpressionType>(_TopicExpression_QNAME, TopicExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="FixedTopicSet", defaultValue="true")
    public JAXBElement<Boolean> createFixedTopicSet(Boolean value) {
        return new JAXBElement<Boolean>(_FixedTopicSet_QNAME, Boolean.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="TopicExpressionDialect")
    public JAXBElement<String> createTopicExpressionDialect(String value) {
        return new JAXBElement<String>(_TopicExpressionDialect_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="ConsumerReference")
    public JAXBElement<W3CEndpointReference> createConsumerReference(W3CEndpointReference value) {
        return new JAXBElement<W3CEndpointReference>(_ConsumerReference_QNAME, W3CEndpointReference.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="Filter")
    public JAXBElement<FilterType> createFilter(FilterType value) {
        return new JAXBElement<FilterType>(_Filter_QNAME, FilterType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="SubscriptionPolicy")
    public JAXBElement<SubscriptionPolicyType> createSubscriptionPolicy(SubscriptionPolicyType value) {
        return new JAXBElement<SubscriptionPolicyType>(_SubscriptionPolicy_QNAME, SubscriptionPolicyType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="CreationTime")
    public JAXBElement<XMLGregorianCalendar> createCreationTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CreationTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="SubscriptionReference")
    public JAXBElement<W3CEndpointReference> createSubscriptionReference(W3CEndpointReference value) {
        return new JAXBElement<W3CEndpointReference>(_SubscriptionReference_QNAME, W3CEndpointReference.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="Topic")
    public JAXBElement<TopicExpressionType> createTopic(TopicExpressionType value) {
        return new JAXBElement<TopicExpressionType>(_Topic_QNAME, TopicExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="ProducerReference")
    public JAXBElement<W3CEndpointReference> createProducerReference(W3CEndpointReference value) {
        return new JAXBElement<W3CEndpointReference>(_ProducerReference_QNAME, W3CEndpointReference.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="NotificationMessage")
    public JAXBElement<NotificationMessageHolderType> createNotificationMessage(NotificationMessageHolderType value) {
        return new JAXBElement<NotificationMessageHolderType>(_NotificationMessage_QNAME, NotificationMessageHolderType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="CurrentTime")
    public JAXBElement<XMLGregorianCalendar> createCurrentTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_CurrentTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="TerminationTime")
    public JAXBElement<XMLGregorianCalendar> createTerminationTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TerminationTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="ProducerProperties")
    public JAXBElement<QueryExpressionType> createProducerProperties(QueryExpressionType value) {
        return new JAXBElement<QueryExpressionType>(_ProducerProperties_QNAME, QueryExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="MessageContent")
    public JAXBElement<QueryExpressionType> createMessageContent(QueryExpressionType value) {
        return new JAXBElement<QueryExpressionType>(_MessageContent_QNAME, QueryExpressionType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="SubscribeCreationFailedFault")
    public JAXBElement<SubscribeCreationFailedFaultType> createSubscribeCreationFailedFault(SubscribeCreationFailedFaultType value) {
        return new JAXBElement<SubscribeCreationFailedFaultType>(_SubscribeCreationFailedFault_QNAME, SubscribeCreationFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="InvalidFilterFault")
    public JAXBElement<InvalidFilterFaultType> createInvalidFilterFault(InvalidFilterFaultType value) {
        return new JAXBElement<InvalidFilterFaultType>(_InvalidFilterFault_QNAME, InvalidFilterFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="TopicExpressionDialectUnknownFault")
    public JAXBElement<TopicExpressionDialectUnknownFaultType> createTopicExpressionDialectUnknownFault(TopicExpressionDialectUnknownFaultType value) {
        return new JAXBElement<TopicExpressionDialectUnknownFaultType>(_TopicExpressionDialectUnknownFault_QNAME, TopicExpressionDialectUnknownFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="InvalidTopicExpressionFault")
    public JAXBElement<InvalidTopicExpressionFaultType> createInvalidTopicExpressionFault(InvalidTopicExpressionFaultType value) {
        return new JAXBElement<InvalidTopicExpressionFaultType>(_InvalidTopicExpressionFault_QNAME, InvalidTopicExpressionFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="TopicNotSupportedFault")
    public JAXBElement<TopicNotSupportedFaultType> createTopicNotSupportedFault(TopicNotSupportedFaultType value) {
        return new JAXBElement<TopicNotSupportedFaultType>(_TopicNotSupportedFault_QNAME, TopicNotSupportedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="MultipleTopicsSpecifiedFault")
    public JAXBElement<MultipleTopicsSpecifiedFaultType> createMultipleTopicsSpecifiedFault(MultipleTopicsSpecifiedFaultType value) {
        return new JAXBElement<MultipleTopicsSpecifiedFaultType>(_MultipleTopicsSpecifiedFault_QNAME, MultipleTopicsSpecifiedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="InvalidProducerPropertiesExpressionFault")
    public JAXBElement<InvalidProducerPropertiesExpressionFaultType> createInvalidProducerPropertiesExpressionFault(InvalidProducerPropertiesExpressionFaultType value) {
        return new JAXBElement<InvalidProducerPropertiesExpressionFaultType>(_InvalidProducerPropertiesExpressionFault_QNAME, InvalidProducerPropertiesExpressionFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="InvalidMessageContentExpressionFault")
    public JAXBElement<InvalidMessageContentExpressionFaultType> createInvalidMessageContentExpressionFault(InvalidMessageContentExpressionFaultType value) {
        return new JAXBElement<InvalidMessageContentExpressionFaultType>(_InvalidMessageContentExpressionFault_QNAME, InvalidMessageContentExpressionFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnrecognizedPolicyRequestFault")
    public JAXBElement<UnrecognizedPolicyRequestFaultType> createUnrecognizedPolicyRequestFault(UnrecognizedPolicyRequestFaultType value) {
        return new JAXBElement<UnrecognizedPolicyRequestFaultType>(_UnrecognizedPolicyRequestFault_QNAME, UnrecognizedPolicyRequestFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnsupportedPolicyRequestFault")
    public JAXBElement<UnsupportedPolicyRequestFaultType> createUnsupportedPolicyRequestFault(UnsupportedPolicyRequestFaultType value) {
        return new JAXBElement<UnsupportedPolicyRequestFaultType>(_UnsupportedPolicyRequestFault_QNAME, UnsupportedPolicyRequestFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="NotifyMessageNotSupportedFault")
    public JAXBElement<NotifyMessageNotSupportedFaultType> createNotifyMessageNotSupportedFault(NotifyMessageNotSupportedFaultType value) {
        return new JAXBElement<NotifyMessageNotSupportedFaultType>(_NotifyMessageNotSupportedFault_QNAME, NotifyMessageNotSupportedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnacceptableInitialTerminationTimeFault")
    public JAXBElement<UnacceptableInitialTerminationTimeFaultType> createUnacceptableInitialTerminationTimeFault(UnacceptableInitialTerminationTimeFaultType value) {
        return new JAXBElement<UnacceptableInitialTerminationTimeFaultType>(_UnacceptableInitialTerminationTimeFault_QNAME, UnacceptableInitialTerminationTimeFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="NoCurrentMessageOnTopicFault")
    public JAXBElement<NoCurrentMessageOnTopicFaultType> createNoCurrentMessageOnTopicFault(NoCurrentMessageOnTopicFaultType value) {
        return new JAXBElement<NoCurrentMessageOnTopicFaultType>(_NoCurrentMessageOnTopicFault_QNAME, NoCurrentMessageOnTopicFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnableToGetMessagesFault")
    public JAXBElement<UnableToGetMessagesFaultType> createUnableToGetMessagesFault(UnableToGetMessagesFaultType value) {
        return new JAXBElement<UnableToGetMessagesFaultType>(_UnableToGetMessagesFault_QNAME, UnableToGetMessagesFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnableToDestroyPullPointFault")
    public JAXBElement<UnableToDestroyPullPointFaultType> createUnableToDestroyPullPointFault(UnableToDestroyPullPointFaultType value) {
        return new JAXBElement<UnableToDestroyPullPointFaultType>(_UnableToDestroyPullPointFault_QNAME, UnableToDestroyPullPointFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnableToCreatePullPointFault")
    public JAXBElement<UnableToCreatePullPointFaultType> createUnableToCreatePullPointFault(UnableToCreatePullPointFaultType value) {
        return new JAXBElement<UnableToCreatePullPointFaultType>(_UnableToCreatePullPointFault_QNAME, UnableToCreatePullPointFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnacceptableTerminationTimeFault")
    public JAXBElement<UnacceptableTerminationTimeFaultType> createUnacceptableTerminationTimeFault(UnacceptableTerminationTimeFaultType value) {
        return new JAXBElement<UnacceptableTerminationTimeFaultType>(_UnacceptableTerminationTimeFault_QNAME, UnacceptableTerminationTimeFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="UnableToDestroySubscriptionFault")
    public JAXBElement<UnableToDestroySubscriptionFaultType> createUnableToDestroySubscriptionFault(UnableToDestroySubscriptionFaultType value) {
        return new JAXBElement<UnableToDestroySubscriptionFaultType>(_UnableToDestroySubscriptionFault_QNAME, UnableToDestroySubscriptionFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="PauseFailedFault")
    public JAXBElement<PauseFailedFaultType> createPauseFailedFault(PauseFailedFaultType value) {
        return new JAXBElement<PauseFailedFaultType>(_PauseFailedFault_QNAME, PauseFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="ResumeFailedFault")
    public JAXBElement<ResumeFailedFaultType> createResumeFailedFault(ResumeFailedFaultType value) {
        return new JAXBElement<ResumeFailedFaultType>(_ResumeFailedFault_QNAME, ResumeFailedFaultType.class, null, value);
    }

    @XmlElementDecl(namespace="http://docs.oasis-open.org/wsn/b-2", name="InitialTerminationTime", scope=Subscribe.class)
    public JAXBElement<String> createSubscribeInitialTerminationTime(String value) {
        return new JAXBElement<String>(_SubscribeInitialTerminationTime_QNAME, String.class, Subscribe.class, value);
    }
}

