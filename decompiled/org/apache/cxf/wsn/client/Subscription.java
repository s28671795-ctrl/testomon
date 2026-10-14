/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.PauseSubscription;
import org.oasis_open.docs.wsn.b_2.Renew;
import org.oasis_open.docs.wsn.b_2.ResumeSubscription;
import org.oasis_open.docs.wsn.b_2.Unsubscribe;
import org.oasis_open.docs.wsn.bw_2.PausableSubscriptionManager;
import org.oasis_open.docs.wsn.bw_2.PauseFailedFault;
import org.oasis_open.docs.wsn.bw_2.ResumeFailedFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroySubscriptionFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableTerminationTimeFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public class Subscription
implements Referencable {
    private final PausableSubscriptionManager subscription;
    private final W3CEndpointReference epr;

    public Subscription(String address) {
        this(WSNHelper.getInstance().createWSA(address));
    }

    public Subscription(W3CEndpointReference epr) {
        this.subscription = WSNHelper.getInstance().getPort(epr, PausableSubscriptionManager.class, new Class[0]);
        this.epr = epr;
    }

    public PausableSubscriptionManager getSubscription() {
        return this.subscription;
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.epr;
    }

    public void pause() throws PauseFailedFault, ResourceUnknownFault {
        this.subscription.pauseSubscription(new PauseSubscription());
    }

    public void resume() throws ResourceUnknownFault, ResumeFailedFault {
        this.subscription.resumeSubscription(new ResumeSubscription());
    }

    public void renew(String terminationTime) throws ResourceUnknownFault, UnacceptableTerminationTimeFault {
        Renew renew = new Renew();
        renew.setTerminationTime(terminationTime);
        this.subscription.renew(renew);
    }

    public void unsubscribe() throws UnableToDestroySubscriptionFault, ResourceUnknownFault {
        this.subscription.unsubscribe(new Unsubscribe());
    }
}

