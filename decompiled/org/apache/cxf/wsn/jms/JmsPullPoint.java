/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsn.AbstractPullPoint;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;
import org.oasis_open.docs.wsn.bw_2.UnableToGetMessagesFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public class JmsPullPoint
extends AbstractPullPoint {
    private static final Logger LOGGER = LogUtils.getL7dLogger(JmsPullPoint.class);
    private JAXBContext jaxbContext;
    private Connection connection;
    private Session producerSession;
    private Session consumerSession;
    private Queue queue;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public JmsPullPoint(String name) {
        super(name);
        try {
            this.jaxbContext = JAXBContext.newInstance(Notify.class);
        }
        catch (JAXBException e) {
            throw new RuntimeException("Could not create PullEndpoint", e);
        }
    }

    protected synchronized void initSession() throws JMSException {
        if (this.producerSession == null || this.consumerSession == null) {
            this.producerSession = this.connection.createSession(false, 1);
            this.consumerSession = this.connection.createSession(false, 1);
            this.queue = this.producerSession.createQueue(this.getName());
            this.producer = this.producerSession.createProducer(this.queue);
            this.consumer = this.consumerSession.createConsumer(this.queue);
        }
    }

    protected synchronized void closeSession() {
        if (this.producerSession != null) {
            try {
                this.producerSession.close();
            }
            catch (JMSException inner) {
                LOGGER.log(Level.FINE, "Error closing ProducerSession", inner);
            }
            finally {
                this.producerSession = null;
            }
        }
        if (this.consumerSession != null) {
            try {
                this.consumerSession.close();
            }
            catch (JMSException inner) {
                LOGGER.log(Level.FINE, "Error closing ConsumerSession", inner);
            }
            finally {
                this.consumerSession = null;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void store(NotificationMessageHolderType messageHolder) {
        try {
            this.initSession();
            Notify notify = new Notify();
            notify.getNotificationMessage().add(messageHolder);
            StringWriter writer = new StringWriter();
            this.jaxbContext.createMarshaller().marshal((Object)notify, writer);
            Session session = this.producerSession;
            synchronized (session) {
                TextMessage message = this.producerSession.createTextMessage(writer.toString());
                this.producer.send(message);
            }
        }
        catch (JMSException e) {
            LOGGER.log(Level.WARNING, "Error storing message", e);
            this.closeSession();
        }
        catch (JAXBException e) {
            LOGGER.log(Level.WARNING, "Error storing message", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected List<NotificationMessageHolderType> getMessages(int max) throws ResourceUnknownFault, UnableToGetMessagesFault {
        try {
            if (max == 0) {
                max = 256;
            }
            this.initSession();
            ArrayList<NotificationMessageHolderType> messages = new ArrayList<NotificationMessageHolderType>();
            for (int i = 0; i < max; ++i) {
                Message msg = null;
                Session session = this.consumerSession;
                synchronized (session) {
                    msg = this.consumer.receiveNoWait();
                }
                if (msg == null) break;
                TextMessage txtMsg = (TextMessage)msg;
                StringReader reader = new StringReader(txtMsg.getText());
                XMLStreamReader xreader = StaxUtils.createXMLStreamReader(reader);
                Notify notify = (Notify)this.jaxbContext.createUnmarshaller().unmarshal(xreader);
                try {
                    xreader.close();
                }
                catch (XMLStreamException xMLStreamException) {
                    // empty catch block
                }
                messages.addAll(notify.getNotificationMessage());
            }
            return messages;
        }
        catch (JMSException e) {
            LOGGER.log(Level.INFO, "Error retrieving messages", e);
            this.closeSession();
            UnableToGetMessagesFaultType fault = new UnableToGetMessagesFaultType();
            throw new UnableToGetMessagesFault("Unable to retrieve messages", fault, e);
        }
        catch (JAXBException e) {
            LOGGER.log(Level.INFO, "Error retrieving messages", e);
            UnableToGetMessagesFaultType fault = new UnableToGetMessagesFaultType();
            throw new UnableToGetMessagesFault("Unable to retrieve messages", fault, e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

