/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.util.List;
import org.apache.cxf.wsn.EndpointMBean;

public interface NotificationBrokerMBean
extends EndpointMBean {
    public List<String> getPublisher();

    public List<String> getSubscriptions();

    public EndpointMBean getPublisher(String var1);

    public EndpointMBean getSubscription(String var1);

    public EndpointMBean getAnonymousPublisher();
}

