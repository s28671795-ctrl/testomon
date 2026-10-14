/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import java.math.BigInteger;
import java.util.List;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.DestroyPullPoint;
import org.oasis_open.docs.wsn.b_2.GetMessages;
import org.oasis_open.docs.wsn.b_2.GetMessagesResponse;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroyPullPointFault;
import org.oasis_open.docs.wsn.bw_2.UnableToGetMessagesFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public class PullPoint
implements Referencable {
    private final org.oasis_open.docs.wsn.bw_2.PullPoint pullPoint;
    private final W3CEndpointReference epr;

    public PullPoint(String address) {
        this(WSNHelper.getInstance().createWSA(address));
    }

    public PullPoint(W3CEndpointReference epr) {
        this.pullPoint = WSNHelper.getInstance().getPort(epr, org.oasis_open.docs.wsn.bw_2.PullPoint.class, new Class[0]);
        this.epr = epr;
    }

    public org.oasis_open.docs.wsn.bw_2.PullPoint getPullPoint() {
        return this.pullPoint;
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.epr;
    }

    public List<NotificationMessageHolderType> getMessages(long max) throws UnableToGetMessagesFault, ResourceUnknownFault {
        GetMessages getMessages = new GetMessages();
        getMessages.setMaximumNumber(BigInteger.valueOf(max));
        GetMessagesResponse response = this.pullPoint.getMessages(getMessages);
        return response.getNotificationMessage();
    }

    public void destroy() throws UnableToDestroyPullPointFault, ResourceUnknownFault {
        this.pullPoint.destroyPullPoint(new DestroyPullPoint());
    }
}

