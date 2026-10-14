/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.activemq.advisory.ConsumerEvent;
import org.apache.activemq.advisory.ConsumerEventSource;
import org.apache.activemq.advisory.ConsumerListener;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsn.AbstractPublisher;
import org.apache.cxf.wsn.jms.InvalidTopicException;
import org.apache.cxf.wsn.jms.JmsTopicExpressionConverter;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.brw_2.ResourceNotDestroyedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public abstract class JmsPublisher
extends AbstractPublisher
implements ConsumerListener {
    private static final Logger LOGGER = LogUtils.getL7dLogger(JmsPublisher.class);
    private Connection connection;
    private JmsTopicExpressionConverter topicConverter = new JmsTopicExpressionConverter();
    private JAXBContext jaxbContext;
    private List<ConsumerEventSource> advisories;
    private Map<Destination, Object> producers;

    public JmsPublisher(String name) {
        super(name);
        try {
            this.jaxbContext = JAXBContext.newInstance(Notify.class);
        }
        catch (JAXBException e) {
            throw new RuntimeException("Unable to create JAXB context", e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void notify(NotificationMessageHolderType messageHolder) {
        Session session = null;
        try {
            ActiveMQTopic topic = this.topicConverter.toActiveMQTopic(messageHolder.getTopic());
            session = this.connection.createSession(false, 1);
            MessageProducer producer = session.createProducer(topic);
            Notify notify = new Notify();
            notify.getNotificationMessage().add(messageHolder);
            StringWriter writer = new StringWriter();
            this.jaxbContext.createMarshaller().marshal((Object)notify, writer);
            TextMessage message = session.createTextMessage(writer.toString());
            producer.send(message);
        }
        catch (JMSException | JAXBException | InvalidTopicException e) {
            LOGGER.log(Level.WARNING, "Error dispatching message", e);
        }
        finally {
            if (session != null) {
                try {
                    session.close();
                }
                catch (JMSException e) {
                    LOGGER.log(Level.FINE, "Error closing session", e);
                }
            }
        }
    }

    @Override
    protected void validatePublisher(RegisterPublisher registerPublisherRequest) throws InvalidTopicExpressionFault, PublisherRegistrationFailedFault, PublisherRegistrationRejectedFault, ResourceUnknownFault, TopicNotSupportedFault {
        super.validatePublisher(registerPublisherRequest);
        try {
            this.topicConverter.toActiveMQTopic(this.topic);
        }
        catch (InvalidTopicException e) {
            InvalidTopicExpressionFaultType fault = new InvalidTopicExpressionFaultType();
            throw new InvalidTopicExpressionFault(e.getMessage(), fault);
        }
    }

    @Override
    protected void start() throws PublisherRegistrationFailedFault {
        if (this.demand) {
            try {
                this.producers = new HashMap<Destination, Object>();
                this.advisories = new ArrayList<ConsumerEventSource>();
                for (TopicExpressionType topic : this.topic) {
                    ConsumerEventSource advisory = new ConsumerEventSource(this.connection, this.topicConverter.toActiveMQTopic(topic));
                    advisory.setConsumerListener(this);
                    advisory.start();
                    this.advisories.add(advisory);
                }
            }
            catch (Exception e) {
                PublisherRegistrationFailedFaultType fault = new PublisherRegistrationFailedFaultType();
                throw new PublisherRegistrationFailedFault("Error starting demand-based publisher", fault, e);
            }
        }
    }

    @Override
    protected void destroy() throws ResourceNotDestroyedFault {
        try {
            if (this.advisories != null) {
                for (ConsumerEventSource advisory : this.advisories) {
                    advisory.stop();
                }
            }
        }
        catch (Exception e) {
            ResourceNotDestroyedFaultType fault = new ResourceNotDestroyedFaultType();
            throw new ResourceNotDestroyedFault("Error destroying publisher", fault, e);
        }
        finally {
            super.destroy();
        }
    }

    @Override
    public synchronized void onConsumerEvent(ConsumerEvent event) {
        Object producer = this.producers.get(event.getDestination());
        if (event.getConsumerCount() > 0) {
            if (producer == null) {
                producer = this.startSubscription(this.topicConverter.toTopicExpression((Topic)event.getDestination()));
                this.producers.put(event.getDestination(), producer);
            }
        } else if (producer != null) {
            Object sub = this.producers.remove(event.getDestination());
            this.stopSubscription(sub);
        }
    }

    protected abstract Object startSubscription(TopicExpressionType var1);

    protected abstract void stopSubscription(Object var1);
}

