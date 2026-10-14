/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import javax.jms.ConnectionFactory;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.jms.JmsNotificationBroker;
import org.apache.cxf.wsn.jms.JmsPublisher;
import org.apache.cxf.wsn.jms.JmsSubscription;
import org.apache.cxf.wsn.services.JaxwsEndpointManager;
import org.apache.cxf.wsn.services.JaxwsNotificationBrokerMBean;
import org.apache.cxf.wsn.services.JaxwsPublisher;
import org.apache.cxf.wsn.services.JaxwsSubscription;

@WebService(endpointInterface="org.oasis_open.docs.wsn.brw_2.NotificationBroker", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="NotificationBrokerService", portName="NotificationBrokerPort")
@BindingType(value="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class JaxwsNotificationBroker
extends JmsNotificationBroker
implements JaxwsNotificationBrokerMBean {
    public JaxwsNotificationBroker(String name) {
        this(name, null);
    }

    public JaxwsNotificationBroker(String name, ConnectionFactory connectionFactory) {
        this(name, connectionFactory, null);
    }

    public JaxwsNotificationBroker(String name, ConnectionFactory connectionFactory, EndpointManager epManager) {
        super(name, connectionFactory);
        this.manager = epManager == null ? new JaxwsEndpointManager() : epManager;
    }

    @Override
    protected JmsSubscription createJmsSubscription(String name) {
        return new JaxwsSubscription(name);
    }

    @Override
    protected JmsPublisher createJmsPublisher(String name) {
        return new JaxwsPublisher(name, this);
    }
}

