/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.NotificationBroker;
import org.apache.cxf.wsn.client.PullPoint;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.CreatePullPointResponse;
import org.oasis_open.docs.wsn.bw_2.UnableToCreatePullPointFault;

public class CreatePullPoint
implements Referencable {
    private final org.oasis_open.docs.wsn.bw_2.CreatePullPoint createPullPoint;
    private final W3CEndpointReference epr;

    public CreatePullPoint(String address) {
        this(WSNHelper.getInstance().createWSA(address));
    }

    public CreatePullPoint(W3CEndpointReference epr) {
        this.createPullPoint = WSNHelper.getInstance().getPort(epr, org.oasis_open.docs.wsn.bw_2.CreatePullPoint.class, new Class[0]);
        this.epr = epr;
    }

    public org.oasis_open.docs.wsn.bw_2.CreatePullPoint getCreatePullPoint() {
        return this.createPullPoint;
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.epr;
    }

    public PullPoint create() throws UnableToCreatePullPointFault {
        org.oasis_open.docs.wsn.b_2.CreatePullPoint request = new org.oasis_open.docs.wsn.b_2.CreatePullPoint();
        CreatePullPointResponse response = this.createPullPoint.createPullPoint(request);
        return new PullPoint(response.getPullPoint());
    }

    public PullPoint create(String queueName) throws UnableToCreatePullPointFault {
        org.oasis_open.docs.wsn.b_2.CreatePullPoint request = new org.oasis_open.docs.wsn.b_2.CreatePullPoint();
        request.getOtherAttributes().put(NotificationBroker.QNAME_PULLPOINT_QUEUE_NAME, queueName);
        CreatePullPointResponse response = this.createPullPoint.createPullPoint(request);
        return new PullPoint(response.getPullPoint());
    }
}

