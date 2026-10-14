/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.oa;

import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.POASystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.Policies;
import org.jboss.com.sun.corba.se.spi.copyobject.ObjectCopierFactory;
import org.jboss.com.sun.corba.se.spi.ior.IORFactories;
import org.jboss.com.sun.corba.se.spi.ior.IORTemplate;
import org.jboss.com.sun.corba.se.spi.ior.ObjectAdapterId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.oa.OADestroyed;
import org.jboss.com.sun.corba.se.spi.oa.OAInvocationInfo;
import org.jboss.com.sun.corba.se.spi.oa.ObjectAdapter;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.protocol.PIHandler;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

public abstract class ObjectAdapterBase
extends LocalObject
implements ObjectAdapter {
    private ORB orb;
    private final POASystemException _iorWrapper;
    private final POASystemException _invocationWrapper;
    private final POASystemException _lifecycleWrapper;
    private final OMGSystemException _omgInvocationWrapper;
    private final OMGSystemException _omgLifecycleWrapper;
    private IORTemplate iortemp;
    private byte[] adapterId;
    private ObjectReferenceTemplate adapterTemplate;
    private ObjectReferenceFactory currentFactory;

    public ObjectAdapterBase(ORB orb) {
        this.orb = orb;
        this._iorWrapper = POASystemException.get(orb, "oa.ior");
        this._lifecycleWrapper = POASystemException.get(orb, "oa.lifecycle");
        this._omgLifecycleWrapper = OMGSystemException.get(orb, "oa.lifecycle");
        this._invocationWrapper = POASystemException.get(orb, "oa.invocation");
        this._omgInvocationWrapper = OMGSystemException.get(orb, "oa.invocation");
    }

    public final POASystemException iorWrapper() {
        return this._iorWrapper;
    }

    public final POASystemException lifecycleWrapper() {
        return this._lifecycleWrapper;
    }

    public final OMGSystemException omgLifecycleWrapper() {
        return this._omgLifecycleWrapper;
    }

    public final POASystemException invocationWrapper() {
        return this._invocationWrapper;
    }

    public final OMGSystemException omgInvocationWrapper() {
        return this._omgInvocationWrapper;
    }

    public final void initializeTemplate(ObjectKeyTemplate oktemp, boolean notifyORB, Policies policies, String codebase, String objectAdapterManagerId, ObjectAdapterId objectAdapterId) {
        PIHandler pih;
        this.adapterId = oktemp.getAdapterId();
        this.iortemp = IORFactories.makeIORTemplate(oktemp);
        this.orb.getCorbaTransportManager().addToIORTemplate(this.iortemp, policies, codebase, objectAdapterManagerId, objectAdapterId);
        this.adapterTemplate = IORFactories.makeObjectReferenceTemplate(this.orb, this.iortemp);
        this.currentFactory = this.adapterTemplate;
        if (notifyORB && (pih = this.orb.getPIHandler()) != null) {
            pih.objectAdapterCreated(this);
        }
        this.iortemp.makeImmutable();
    }

    public final Object makeObject(String repId, byte[] oid) {
        return this.currentFactory.make_object(repId, oid);
    }

    public final byte[] getAdapterId() {
        return this.adapterId;
    }

    @Override
    public final ORB getORB() {
        return this.orb;
    }

    @Override
    public abstract Policy getEffectivePolicy(int var1);

    @Override
    public final IORTemplate getIORTemplate() {
        return this.iortemp;
    }

    @Override
    public abstract int getManagerId();

    @Override
    public abstract short getState();

    @Override
    public final ObjectReferenceTemplate getAdapterTemplate() {
        return this.adapterTemplate;
    }

    @Override
    public final ObjectReferenceFactory getCurrentFactory() {
        return this.currentFactory;
    }

    @Override
    public final void setCurrentFactory(ObjectReferenceFactory factory2) {
        this.currentFactory = factory2;
    }

    @Override
    public abstract Object getLocalServant(byte[] var1);

    @Override
    public abstract void getInvocationServant(OAInvocationInfo var1);

    @Override
    public abstract void returnServant();

    @Override
    public abstract void enter() throws OADestroyed;

    @Override
    public abstract void exit();

    protected abstract ObjectCopierFactory getObjectCopierFactory();

    @Override
    public OAInvocationInfo makeInvocationInfo(byte[] objectId) {
        OAInvocationInfo info = new OAInvocationInfo(this, objectId);
        info.setCopierFactory(this.getObjectCopierFactory());
        return info;
    }

    @Override
    public abstract String[] getInterfaces(java.lang.Object var1, byte[] var2);
}

