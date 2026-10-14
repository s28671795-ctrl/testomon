/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import javax.jws.WebService;
import org.apache.cxf.wsn.jms.JmsPullPoint;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.PullPoint", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="PullPointService", portName="PullPointPort")
public class JaxwsPullPoint
extends JmsPullPoint {
    public JaxwsPullPoint(String name) {
        super(name);
    }
}

