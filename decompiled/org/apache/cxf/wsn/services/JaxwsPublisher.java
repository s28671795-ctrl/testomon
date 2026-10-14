/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.ws.BindingType;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsn.AbstractSubscription;
import org.apache.cxf.wsn.jms.JmsPublisher;
import org.apache.cxf.wsn.services.JaxwsNotificationBroker;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.FilterType;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.bw_2.NotificationProducer;
import org.oasis_open.docs.wsn.bw_2.SubscriptionManager;

@WebService(endpointInterface="org.oasis_open.docs.wsn.brw_2.PublisherRegistrationManager", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="PublisherRegistrationManagerService", portName="PublisherRegistrationManagerPort")
@BindingType(value="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class JaxwsPublisher
extends JmsPublisher {
    private static final Logger LOGGER = LogUtils.getL7dLogger(JaxwsPublisher.class);
    protected JaxwsNotificationBroker notificationBroker;
    private NotificationProducer notificationProducer;

    public JaxwsPublisher(String name, JaxwsNotificationBroker notificationBroker) {
        super(name);
        this.notificationBroker = notificationBroker;
    }

    @Override
    protected void start() throws PublisherRegistrationFailedFault {
        super.start();
        if (this.demand) {
            this.notificationProducer = WSNHelper.getInstance().getPort(this.publisherReference, NotificationProducer.class, new Class[0]);
        }
    }

    @Override
    protected Object startSubscription(TopicExpressionType topic) {
        try {
            Subscribe subscribeRequest = new Subscribe();
            subscribeRequest.setConsumerReference(this.notificationBroker.getEpr());
            subscribeRequest.setFilter(new FilterType());
            subscribeRequest.getFilter().getAny().add(new JAXBElement<TopicExpressionType>(AbstractSubscription.QNAME_TOPIC_EXPRESSION, TopicExpressionType.class, topic));
            SubscribeResponse response = this.notificationProducer.subscribe(subscribeRequest);
            return WSNHelper.getInstance().getPort(response.getSubscriptionReference(), SubscriptionManager.class, new Class[0]);
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, "Error while subscribing on-demand publisher", e);
            return null;
        }
    }

    @Override
    protected void stopSubscription(Object sub) {
        try {
            ((SubscriptionManager)sub).unsubscribe(new Unsubscribe());
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, "Error while unsubscribing on-demand publisher", e);
        }
    }
}

