/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import org.oasis_open.docs.wsn.br_2.DestroyRegistration;
import org.oasis_open.docs.wsn.br_2.DestroyRegistrationResponse;
import org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationManager;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.brw_2.ResourceNotDestroyedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.brw_2.PublisherRegistrationManager")
public abstract class AbstractPublisher
extends AbstractEndpoint
implements PublisherRegistrationManager {
    protected W3CEndpointReference publisherReference;
    protected boolean demand;
    protected List<TopicExpressionType> topic;

    public AbstractPublisher(String name) {
        super(name);
    }

    public W3CEndpointReference getPublisherReference() {
        return this.publisherReference;
    }

    @Override
    @WebMethod(operationName="DestroyRegistration")
    @WebResult(name="DestroyRegistrationResponse", targetNamespace="http://docs.oasis-open.org/wsn/br-2", partName="DestroyRegistrationResponse")
    public DestroyRegistrationResponse destroyRegistration(@WebParam(name="DestroyRegistration", targetNamespace="http://docs.oasis-open.org/wsn/br-2", partName="DestroyRegistrationRequest") DestroyRegistration destroyRegistrationRequest) throws ResourceNotDestroyedFault, ResourceUnknownFault {
        this.destroy();
        return new DestroyRegistrationResponse();
    }

    public abstract void notify(NotificationMessageHolderType var1);

    protected void destroy() throws ResourceNotDestroyedFault {
        try {
            this.unregister();
        }
        catch (EndpointRegistrationException e) {
            ResourceNotDestroyedFaultType fault = new ResourceNotDestroyedFaultType();
            throw new ResourceNotDestroyedFault("Error unregistering endpoint", fault, e);
        }
    }

    public void create(RegisterPublisher registerPublisherRequest) throws InvalidTopicExpressionFault, PublisherRegistrationFailedFault, PublisherRegistrationRejectedFault, ResourceUnknownFault, TopicNotSupportedFault {
        this.validatePublisher(registerPublisherRequest);
        this.start();
    }

    protected void validatePublisher(RegisterPublisher registerPublisherRequest) throws InvalidTopicExpressionFault, PublisherRegistrationFailedFault, PublisherRegistrationRejectedFault, ResourceUnknownFault, TopicNotSupportedFault {
        this.publisherReference = registerPublisherRequest.getPublisherReference();
        this.topic = registerPublisherRequest.getTopic();
        boolean bl = this.demand = registerPublisherRequest.isDemand() != null && registerPublisherRequest.isDemand() != false;
        if (this.publisherReference == null && this.demand) {
            PublisherRegistrationFailedFaultType fault = new PublisherRegistrationFailedFaultType();
            throw new PublisherRegistrationFailedFault("Invalid PublisherReference: null", fault);
        }
        if (this.demand && (this.topic == null || this.topic.isEmpty())) {
            InvalidTopicExpressionFaultType fault = new InvalidTopicExpressionFaultType();
            throw new InvalidTopicExpressionFault("Must specify at least one topic for demand-based publishing", fault);
        }
    }

    protected abstract void start() throws PublisherRegistrationFailedFault;
}

