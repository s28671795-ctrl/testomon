/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.br_2.DestroyRegistration;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationManager;
import org.oasis_open.docs.wsn.brw_2.ResourceNotDestroyedFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

public class Registration
implements Referencable {
    private final PublisherRegistrationManager registration;
    private final W3CEndpointReference epr;

    public Registration(String address) {
        this(WSNHelper.getInstance().createWSA(address));
    }

    public Registration(W3CEndpointReference epr) {
        this.registration = WSNHelper.getInstance().getPort(epr, PublisherRegistrationManager.class, new Class[0]);
        this.epr = epr;
    }

    public PublisherRegistrationManager getRegistration() {
        return this.registration;
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.epr;
    }

    public void destroy() throws ResourceUnknownFault, ResourceNotDestroyedFault {
        this.registration.destroyRegistration(new DestroyRegistration());
    }
}

