/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.cxf.wsn.AbstractCreatePullPoint;
import org.apache.cxf.wsn.AbstractNotificationBroker;
import org.apache.cxf.wsn.services.JaxwsCreatePullPoint;
import org.apache.cxf.wsn.services.JaxwsNotificationBroker;

public class Service {
    String rootURL = "http://0.0.0.0:9000/wsn";
    String activeMqUrl = "vm:(broker:(tcp://localhost:6000)?persistent=false)";
    String userName;
    String password;
    boolean jmxEnable = true;
    AbstractCreatePullPoint createPullPointServer;
    AbstractNotificationBroker notificationBrokerServer;

    public Service(String[] args2) {
        for (int x = 0; x < args2.length; ++x) {
            if ("-brokerUrl".equals(args2[x])) {
                this.activeMqUrl = args2[++x];
                continue;
            }
            if ("-userName".equals(args2[x])) {
                this.userName = args2[++x];
                continue;
            }
            if ("-password".equals(args2[x])) {
                this.password = args2[++x];
                continue;
            }
            if ("-rootUrl".equals(args2[x])) {
                this.rootURL = args2[++x];
                continue;
            }
            if (!"-jmxEnable".equals(args2[x])) continue;
            this.jmxEnable = Boolean.valueOf(args2[++x]);
        }
    }

    public static void main(String[] args2) throws Exception {
        new Service(args2).start();
    }

    public void start() throws Exception {
        ActiveMQConnectionFactory activemq = new ActiveMQConnectionFactory(this.userName, this.password, this.activeMqUrl);
        this.notificationBrokerServer = new JaxwsNotificationBroker("WSNotificationBroker", activemq);
        this.notificationBrokerServer.setAddress(this.rootURL + "/NotificationBroker");
        this.notificationBrokerServer.init();
        this.createPullPointServer = new JaxwsCreatePullPoint("CreatePullPoint", activemq);
        this.createPullPointServer.setAddress(this.rootURL + "/CreatePullPoint");
        this.createPullPointServer.init();
        if (this.jmxEnable) {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            mbs.registerMBean(this.notificationBrokerServer, this.notificationBrokerServer.getMBeanName());
            mbs.registerMBean(this.createPullPointServer, this.createPullPointServer.getMBeanName());
        }
    }
}

