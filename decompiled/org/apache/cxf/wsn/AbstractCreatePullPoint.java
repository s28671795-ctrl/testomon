/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.management.ObjectName;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.AbstractPullPoint;
import org.apache.cxf.wsn.CreatePullPointMBean;
import org.apache.cxf.wsn.EndpointMBean;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.apache.cxf.wsn.util.IdGenerator;
import org.oasis_open.docs.wsn.b_2.CreatePullPoint;
import org.oasis_open.docs.wsn.b_2.CreatePullPointResponse;
import org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;
import org.oasis_open.docs.wsn.bw_2.UnableToCreatePullPointFault;
import org.oasis_open.docs.wsn.bw_2.UnableToDestroyPullPointFault;

@WebService(endpointInterface="org.oasis_open.docs.wsn.bw_2.CreatePullPoint")
public abstract class AbstractCreatePullPoint
extends AbstractEndpoint
implements org.oasis_open.docs.wsn.bw_2.CreatePullPoint,
CreatePullPointMBean {
    private static final Logger LOGGER = LogUtils.getL7dLogger(AbstractCreatePullPoint.class);
    private IdGenerator idGenerator = new IdGenerator();
    private Map<String, AbstractPullPoint> pullPoints = new ConcurrentHashMap<String, AbstractPullPoint>();

    public AbstractCreatePullPoint(String name) {
        super(name);
    }

    @Override
    public ObjectName getMBeanName() {
        try {
            return new ObjectName("org.apache.cxf.service.wsn:type=CreatePullPoint");
        }
        catch (Exception e) {
            return null;
        }
    }

    public void init() throws Exception {
        this.register();
    }

    public void destroy() throws Exception {
        this.unregister();
    }

    @Override
    public List<String> getPullEndpoints() {
        return new ArrayList<String>(this.pullPoints.keySet());
    }

    @Override
    public EndpointMBean getPullEndpoint(String name) {
        return this.pullPoints.get(name);
    }

    @Override
    @WebMethod(operationName="CreatePullPoint")
    @WebResult(name="CreatePullPointResponse", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="CreatePullPointResponse")
    public CreatePullPointResponse createPullPoint(@WebParam(name="CreatePullPoint", targetNamespace="http://docs.oasis-open.org/wsn/b-2", partName="CreatePullPointRequest") CreatePullPoint createPullPointRequest) throws UnableToCreatePullPointFault {
        LOGGER.finest("CreatePullEndpoint");
        return this.handleCreatePullPoint(createPullPointRequest, null);
    }

    public CreatePullPointResponse handleCreatePullPoint(CreatePullPoint createPullPointRequest, EndpointManager manager) throws UnableToCreatePullPointFault {
        AbstractPullPoint pullPoint = null;
        boolean success = false;
        try {
            pullPoint = this.createPullPoint(this.createPullPointName(createPullPointRequest));
            pullPoint.setCreatePullPoint(this);
            this.pullPoints.put(pullPoint.getAddress(), pullPoint);
            pullPoint.create(createPullPointRequest);
            if (manager != null) {
                pullPoint.setManager(manager);
            }
            pullPoint.register();
            CreatePullPointResponse response = new CreatePullPointResponse();
            response.setPullPoint(pullPoint.getEpr());
            success = true;
            CreatePullPointResponse createPullPointResponse = response;
            return createPullPointResponse;
        }
        catch (EndpointRegistrationException e) {
            LOGGER.log(Level.WARNING, "Unable to register new endpoint", e);
            UnableToCreatePullPointFaultType fault = new UnableToCreatePullPointFaultType();
            throw new UnableToCreatePullPointFault("Unable to register new endpoint", fault, e);
        }
        finally {
            if (!success && pullPoint != null) {
                this.pullPoints.remove(pullPoint.getAddress());
                try {
                    pullPoint.destroy();
                }
                catch (UnableToDestroyPullPointFault e) {
                    LOGGER.log(Level.INFO, "Error destroying pullPoint", e);
                }
            }
        }
    }

    protected String createPullPointName(CreatePullPoint createPullPointRequest) {
        return this.idGenerator.generateSanitizedId();
    }

    public void destroyPullPoint(String address) throws UnableToDestroyPullPointFault {
        AbstractPullPoint pullPoint = this.pullPoints.remove(address);
        if (pullPoint != null) {
            pullPoint.destroy();
        }
    }

    protected abstract AbstractPullPoint createPullPoint(String var1);
}

