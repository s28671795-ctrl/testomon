/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import java.net.URI;
import java.net.URISyntaxException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.apache.cxf.wsn.AbstractNotificationBroker;
import org.apache.cxf.wsn.AbstractPublisher;
import org.apache.cxf.wsn.AbstractSubscription;
import org.apache.cxf.wsn.jms.JmsPublisher;
import org.apache.cxf.wsn.jms.JmsSubscription;
import org.oasis_open.docs.wsrf.rp_2.GetResourcePropertyResponse;
import org.oasis_open.docs.wsrf.rpw_2.InvalidResourcePropertyQNameFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnavailableFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public abstract class JmsNotificationBroker
extends AbstractNotificationBroker {
    private ConnectionFactory connectionFactory;
    private Connection connection;

    public JmsNotificationBroker(String name) {
        super(name);
    }

    public JmsNotificationBroker(String name, ConnectionFactory connectionFactory) {
        super(name);
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void init() throws Exception {
        if (this.connection == null) {
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
        }
        super.init();
    }

    @Override
    public void destroy() throws Exception {
        if (this.connection != null) {
            this.connection.close();
        }
        super.destroy();
    }

    @Override
    protected AbstractPublisher createPublisher(String name) {
        JmsPublisher publisher = this.createJmsPublisher(name);
        publisher.setAddress(URI.create(this.getAddress()).resolve("registrations/" + name).toString());
        publisher.setManager(this.getManager());
        publisher.setConnection(this.connection);
        return publisher;
    }

    @Override
    protected AbstractSubscription createSubscription(String name) {
        JmsSubscription subscription = this.createJmsSubscription(name);
        subscription.setAddress(URI.create(this.getAddress()).resolve("subscriptions/" + name).toString());
        subscription.setManager(this.getManager());
        subscription.setConnection(this.connection);
        return subscription;
    }

    protected abstract JmsSubscription createJmsSubscription(String var1);

    protected abstract JmsPublisher createJmsPublisher(String var1);

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected GetResourcePropertyResponse handleGetResourceProperty(QName property) throws ResourceUnavailableFault, ResourceUnknownFault, InvalidResourcePropertyQNameFault {
        if (!TOPIC_EXPRESSION_QNAME.equals(property) && !FIXED_TOPIC_SET_QNAME.equals(property)) {
            if (TOPIC_EXPRESSION_DIALECT_QNAME.equals(property)) {
                GetResourcePropertyResponse r = new GetResourcePropertyResponse();
                try {
                    r.getAny().add(new JAXBElement<URI>(TOPIC_EXPRESSION_DIALECT_QNAME, URI.class, new URI("http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple")));
                }
                catch (URISyntaxException e) {
                    r.getAny().add(new JAXBElement<String>(TOPIC_EXPRESSION_DIALECT_QNAME, String.class, "http://docs.oasis-open.org/wsn/t-1/TopicExpression/Simple"));
                }
                return r;
            }
            if (TOPIC_SET_QNAME.equals(property)) {
                // empty if block
            }
        }
        return super.handleGetResourceProperty(property);
    }
}

