/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsn.AbstractCreatePullPoint;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.oasis_open.docs.wsn.b_2.CreatePullPoint;
import org.oasis_open.docs.wsn.b_2.DestroyPullPoint;
import org.oasis_open.docs.wsn.b_2.DestroyPullPointResponse;
import org.oasis_open.docs.wsn.b_2.GetMessages;
import org.oasis_open.docs.wsn.b_2.GetMessagesResponse;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.oasis_open.docs.wsn.bw_2.PullPoint;
import org.oasis_open.docs.wsn.bw_2.UnableToCreatePullPointFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroyPullPointFault;
import org.oasis_open.docs.wsn.bw_2.UnableToGetMessagesFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.PullPoint")
public abstract class AbstractPullPoint
extends AbstractEndpoint
implements PullPoint,
NotificationConsumer {
    private static final Logger LOGGER = LogUtils.getL7dLogger(AbstractPullPoint.class);
    protected AbstractCreatePullPoint createPullPoint;

    public AbstractPullPoint(String name) {
        super(name);
    }

    @Override
    @WebMethod(operationName="Notify")
    @Oneway
    public void notify(@WebParam(name="Notify", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="Notify") Notify notify) {
        LOGGER.finest("Notify");
        for (NotificationMessageHolderType messageHolder : notify.getNotificationMessage()) {
            this.store(messageHolder);
        }
    }

    @Override
    @WebMethod(operationName="GetMessages")
    @WebResult(name="GetMessagesResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="GetMessagesResponse")
    public GetMessagesResponse getMessages(@WebParam(name="GetMessages", targetNamespace="http://docs.oasis-open.org/wsn/b-1", partName="GetMessagesRequest") GetMessages getMessagesRequest) throws ResourceUnknownFault, UnableToGetMessagesFault {
        LOGGER.finest("GetMessages");
        BigInteger max = getMessagesRequest.getMaximumNumber();
        List<NotificationMessageHolderType> messages = this.getMessages(max != null ? max.intValue() : 0);
        GetMessagesResponse response = new GetMessagesResponse();
        response.getNotificationMessage().addAll(messages);
        return response;
    }

    @Override
    @WebMethod(operationName="DestroyPullPoint")
    @WebResult(name="DestroyPullPointResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="DestroyPullPointResponse")
    public DestroyPullPointResponse destroyPullPoint(@WebParam(name="DestroyPullPoint", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="DestroyPullPointRequest") DestroyPullPoint destroyPullPointRequest) throws ResourceUnknownFault, UnableToDestroyPullPointFault {
        LOGGER.finest("Destroy");
        this.createPullPoint.destroyPullPoint(this.getAddress());
        return new DestroyPullPointResponse();
    }

    public void create(CreatePullPoint createPullPointRequest) throws UnableToCreatePullPointFault {
    }

    protected abstract void store(NotificationMessageHolderType var1);

    protected abstract List<NotificationMessageHolderType> getMessages(int var1) throws ResourceUnknownFault, UnableToGetMessagesFault;

    protected void destroy() throws UnableToDestroyPullPointFault {
        try {
            this.unregister();
        }
        catch (EndpointRegistrationException e) {
            UnableToDestroyPullPointFaultType fault = new UnableToDestroyPullPointFaultType();
            throw new UnableToDestroyPullPointFault("Error unregistering endpoint", fault, e);
        }
    }

    public AbstractCreatePullPoint getCreatePullPoint() {
        return this.createPullPoint;
    }

    public void setCreatePullPoint(AbstractCreatePullPoint createPullPoint) {
        this.createPullPoint = createPullPoint;
    }
}

