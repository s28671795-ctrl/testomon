/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import java.io.StringReader;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsn.AbstractSubscription;
import org.apache.cxf.wsn.jms.InvalidTopicException;
import org.apache.cxf.wsn.jms.JmsTopicExpressionConverter;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;
import org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
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
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

public abstract class JmsSubscription
extends AbstractSubscription
implements MessageListener {
    private static final Logger LOGGER = LogUtils.getL7dLogger(JmsSubscription.class);
    private Connection connection;
    private Session session;
    private JmsTopicExpressionConverter topicConverter = new JmsTopicExpressionConverter();
    private Topic jmsTopic;
    private JAXBContext jaxbContext;
    private boolean checkTermination = true;
    private boolean isSessionActive = true;
    private Thread terminationThread;

    public JmsSubscription(String name) {
        super(name);
        try {
            this.jaxbContext = JAXBContext.newInstance(Notify.class);
        }
        catch (JAXBException e) {
            throw new RuntimeException("Unable to create JAXB context", e);
        }
    }

    @Override
    protected void start() throws SubscribeCreationFailedFault {
        try {
            this.session = this.connection.createSession(false, 1);
            MessageConsumer consumer = this.session.createConsumer(this.jmsTopic);
            consumer.setMessageListener(this);
            this.checkTermination = true;
            this.isSessionActive = true;
            if (this.getTerminationTime() != null) {
                this.terminationThread = new TerminationThread();
                this.terminationThread.start();
            }
        }
        catch (JMSException e) {
            SubscribeCreationFailedFaultType fault = new SubscribeCreationFailedFaultType();
            throw new SubscribeCreationFailedFault("Error starting subscription", fault, e);
        }
    }

    @Override
    protected void validateSubscription(Subscribe subscribeRequest) throws InvalidFilterFault, InvalidMessageContentExpressionFault, InvalidProducerPropertiesExpressionFault, InvalidTopicExpressionFault, SubscribeCreationFailedFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault {
        super.validateSubscription(subscribeRequest);
        try {
            this.jmsTopic = this.topicConverter.toActiveMQTopic(this.topic);
        }
        catch (InvalidTopicException e) {
            InvalidTopicExpressionFaultType fault = new InvalidTopicExpressionFaultType();
            throw new InvalidTopicExpressionFault(e.getMessage(), fault);
        }
    }

    @Override
    protected void pause() throws PauseFailedFault {
        if (this.session == null) {
            PauseFailedFaultType fault = new PauseFailedFaultType();
            throw new PauseFailedFault("Subscription is already paused", fault);
        }
        try {
            this.session.close();
            this.isSessionActive = false;
        }
        catch (JMSException e) {
            PauseFailedFaultType fault = new PauseFailedFaultType();
            throw new PauseFailedFault("Error pausing subscription", fault, e);
        }
        finally {
            this.session = null;
        }
    }

    @Override
    protected void resume() throws ResumeFailedFault {
        if (this.session != null) {
            ResumeFailedFaultType fault = new ResumeFailedFaultType();
            throw new ResumeFailedFault("Subscription is already running", fault);
        }
        try {
            this.session = this.connection.createSession(false, 1);
            MessageConsumer consumer = this.session.createConsumer(this.jmsTopic);
            consumer.setMessageListener(this);
            this.isSessionActive = true;
        }
        catch (JMSException e) {
            ResumeFailedFaultType fault = new ResumeFailedFaultType();
            throw new ResumeFailedFault("Error resuming subscription", fault, e);
        }
    }

    @Override
    protected void renew(XMLGregorianCalendar terminationTime) throws UnacceptableTerminationTimeFault {
        try {
            this.resume();
            if (this.terminationThread == null) {
                this.terminationThread = new TerminationThread();
                this.terminationThread.start();
            }
        }
        catch (ResumeFailedFault e) {
            LOGGER.log(Level.WARNING, "renew failed", e);
        }
    }

    @Override
    protected void unsubscribe() throws UnableToDestroySubscriptionFault {
        super.unsubscribe();
        if (this.session != null) {
            try {
                this.session.close();
                this.checkTermination = false;
            }
            catch (JMSException e) {
                UnableToDestroySubscriptionFaultType fault = new UnableToDestroySubscriptionFaultType();
                throw new UnableToDestroySubscriptionFault("Unable to unsubscribe", fault, e);
            }
            finally {
                this.session = null;
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onMessage(Message jmsMessage) {
        try {
            TextMessage text = (TextMessage)jmsMessage;
            XMLStreamReader reader = StaxUtils.createXMLStreamReader(new StringReader(text.getText()));
            Notify notify = (Notify)this.jaxbContext.createUnmarshaller().unmarshal(reader);
            reader.close();
            Iterator<NotificationMessageHolderType> ith = notify.getNotificationMessage().iterator();
            while (ith.hasNext()) {
                NotificationMessageHolderType h2 = ith.next();
                Object content = h2.getMessage().getAny();
                if (!(content instanceof Element)) {
                    DocumentFragment doc = DOMUtils.getEmptyDocument().createDocumentFragment();
                    this.jaxbContext.createMarshaller().marshal(content, doc);
                    content = DOMUtils.getFirstElement(doc);
                }
                if (!this.doFilter((Element)content)) {
                    ith.remove();
                    continue;
                }
                h2.setTopic(this.topic);
                h2.setSubscriptionReference(this.getEpr());
            }
            if (!notify.getNotificationMessage().isEmpty()) {
                this.doNotify(notify);
            }
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error notifying consumer", e);
        }
    }

    protected boolean doFilter(Element content) {
        if (this.contentFilter != null) {
            if (!this.contentFilter.getDialect().equals("http://www.w3.org/TR/1999/REC-xpath-19991116")) {
                throw new IllegalStateException("Unsupported dialect: " + this.contentFilter.getDialect());
            }
            try {
                XPathFactory xpfactory = XPathFactory.newInstance();
                try {
                    xpfactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                XPath xpath = xpfactory.newXPath();
                XPathExpression exp = xpath.compile(this.contentFilter.getContent().get(0).toString());
                Boolean ret = (Boolean)exp.evaluate(content, XPathConstants.BOOLEAN);
                return ret;
            }
            catch (XPathExpressionException e) {
                LOGGER.log(Level.WARNING, "Could not filter notification", e);
                return false;
            }
        }
        return true;
    }

    protected abstract void doNotify(Notify var1);

    class TerminationThread
    extends Thread {
        TerminationThread() {
        }

        @Override
        public void run() {
            while (JmsSubscription.this.checkTermination) {
                XMLGregorianCalendar ct;
                int c;
                XMLGregorianCalendar tt = JmsSubscription.this.getTerminationTime();
                if (tt != null && JmsSubscription.this.isSessionActive && ((c = tt.compare(ct = JmsSubscription.this.getCurrentTime())) == -1 || c == 0)) {
                    LOGGER.log(Level.INFO, "Need Pause this subscribe");
                    try {
                        JmsSubscription.this.pause();
                    }
                    catch (PauseFailedFault e) {
                        LOGGER.log(Level.WARNING, "Pause failed", e);
                    }
                }
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException e) {
                    LOGGER.log(Level.WARNING, "TerminationThread sleep interrupted", e);
                }
            }
        }
    }
}

