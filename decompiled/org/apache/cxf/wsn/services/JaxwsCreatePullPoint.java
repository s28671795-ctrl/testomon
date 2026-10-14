/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import java.net.URI;
import javax.jms.ConnectionFactory;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.apache.cxf.wsn.AbstractPullPoint;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.jms.JmsCreatePullPoint;
import org.apache.cxf.wsn.services.JaxwsCreatePullPointMBean;
import org.apache.cxf.wsn.services.JaxwsEndpointManager;
import org.apache.cxf.wsn.services.JaxwsPullPoint;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.CreatePullPoint", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="CreatePullPointService", portName="CreatePullPointPort")
@BindingType(value="http://www.w3.org/2003/05/soap/bindings/HTTP/")
public class JaxwsCreatePullPoint
extends JmsCreatePullPoint
implements JaxwsCreatePullPointMBean {
    public JaxwsCreatePullPoint(String name) {
        this(name, null, null);
    }

    public JaxwsCreatePullPoint(String name, ConnectionFactory connectionFactory) {
        this(name, connectionFactory, null);
    }

    public JaxwsCreatePullPoint(String name, ConnectionFactory connectionFactory, EndpointManager epManager) {
        super(name, connectionFactory);
        this.manager = epManager == null ? new JaxwsEndpointManager() : epManager;
    }

    @Override
    protected AbstractPullPoint createPullPoint(String name) {
        JaxwsPullPoint pullPoint = new JaxwsPullPoint(name);
        pullPoint.setManager(this.getManager());
        pullPoint.setConnection(this.connection);
        pullPoint.setAddress(URI.create(this.getAddress()).resolve("pullpoints/" + name).toString());
        return pullPoint;
    }
}

