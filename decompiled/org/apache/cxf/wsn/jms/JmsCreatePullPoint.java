/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import org.apache.cxf.wsn.AbstractCreatePullPoint;
import org.apache.cxf.wsn.AbstractPullPoint;
import org.apache.cxf.wsn.client.NotificationBroker;
import org.apache.cxf.wsn.jms.JmsPullPoint;
import org.oasis_open.docs.wsn.b_2.CreatePullPoint;

public class JmsCreatePullPoint
extends AbstractCreatePullPoint {
    protected ConnectionFactory connectionFactory;
    protected Connection connection;

    public JmsCreatePullPoint(String name) {
        super(name);
    }

    public JmsCreatePullPoint(String name, ConnectionFactory connectionFactory) {
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
    protected String createPullPointName(CreatePullPoint createPullPointRequest) {
        String name = null;
        name = createPullPointRequest.getOtherAttributes().get(NotificationBroker.QNAME_PULLPOINT_QUEUE_NAME) != null ? createPullPointRequest.getOtherAttributes().get(NotificationBroker.QNAME_PULLPOINT_QUEUE_NAME) : super.createPullPointName(createPullPointRequest);
        name = name.replace("-", "");
        return name;
    }

    @Override
    protected AbstractPullPoint createPullPoint(String name) {
        JmsPullPoint pullPoint = new JmsPullPoint(name);
        pullPoint.setManager(this.getManager());
        pullPoint.setConnection(this.connection);
        return pullPoint;
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}

