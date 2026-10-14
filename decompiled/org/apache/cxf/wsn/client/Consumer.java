/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.client;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.client.Referencable;
import org.apache.cxf.wsn.util.WSNHelper;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.w3c.dom.Element;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.NotificationConsumer", targetNamespace="http://cxf.apache.org/wsn/jaxws", serviceName="NotificationConsumerService", portName="NotificationConsumerPort")
public class Consumer
implements NotificationConsumer,
Referencable {
    private final Callback callback;
    private final Endpoint endpoint;
    private final JAXBContext context;

    public Consumer(Callback callback, String address, Class<?> ... extraClasses) {
        this.callback = callback;
        WSNHelper helper = WSNHelper.getInstance();
        if (helper.supportsExtraClasses()) {
            this.endpoint = helper.publish(address, this, extraClasses);
            this.context = null;
        } else {
            this.endpoint = helper.publish(address, this, new Class[0]);
            if (extraClasses != null && extraClasses.length > 0) {
                try {
                    this.context = JAXBContext.newInstance(extraClasses);
                }
                catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
            } else {
                this.context = null;
            }
        }
    }

    public void stop() {
        this.endpoint.stop();
    }

    @Override
    public W3CEndpointReference getEpr() {
        return this.endpoint.getEndpointReference(W3CEndpointReference.class, new Element[0]);
    }

    @Override
    public void notify(@WebParam(partName="Notify", name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-2") Notify notify) {
        for (NotificationMessageHolderType message : notify.getNotificationMessage()) {
            Object o;
            if (this.context != null && (o = message.getMessage().getAny()) instanceof Element) {
                try {
                    o = this.context.createUnmarshaller().unmarshal((Element)o);
                    message.getMessage().setAny(o);
                }
                catch (JAXBException jAXBException) {
                    // empty catch block
                }
            }
            this.callback.notify(message);
        }
    }

    public static interface Callback {
        public void notify(NotificationMessageHolderType var1);
    }
}

