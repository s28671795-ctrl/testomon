/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.oa.poa;

import java.util.EmptyStackException;
import org.jboss.com.sun.corba.se.impl.logging.POASystemException;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAFactory;
import org.jboss.com.sun.corba.se.impl.oa.poa.POAImpl;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.InterfaceDefHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.portable.Delegate;

public class DelegateImpl
implements Delegate {
    private org.jboss.com.sun.corba.se.spi.orb.ORB orb;
    private POASystemException wrapper;
    private POAFactory factory;

    public DelegateImpl(org.jboss.com.sun.corba.se.spi.orb.ORB orb, POAFactory factory2) {
        this.orb = orb;
        this.wrapper = POASystemException.get(orb, "oa");
        this.factory = factory2;
    }

    @Override
    public ORB orb(Servant self) {
        return this.orb;
    }

    @Override
    public Object this_object(Servant self) {
        try {
            byte[] oid = this.orb.peekInvocationInfo().id();
            POA poa = (POA)((java.lang.Object)this.orb.peekInvocationInfo().oa());
            String repId = self._all_interfaces(poa, oid)[0];
            return poa.create_reference_with_id(oid, repId);
        }
        catch (EmptyStackException notInInvocationE) {
            POAImpl defaultPOA = null;
            try {
                defaultPOA = (POAImpl)self._default_POA();
            }
            catch (ClassCastException exception) {
                throw this.wrapper.defaultPoaNotPoaimpl(exception);
            }
            try {
                if (defaultPOA.getPolicies().isImplicitlyActivated() || defaultPOA.getPolicies().isUniqueIds() && defaultPOA.getPolicies().retainServants()) {
                    return defaultPOA.servant_to_reference(self);
                }
                throw this.wrapper.wrongPoliciesForThisObject();
            }
            catch (ServantNotActive e) {
                throw this.wrapper.thisObjectServantNotActive(e);
            }
            catch (WrongPolicy e) {
                throw this.wrapper.thisObjectWrongPolicy(e);
            }
        }
        catch (ClassCastException e) {
            throw this.wrapper.defaultPoaNotPoaimpl(e);
        }
        catch (WrongPolicy e) {
            throw this.wrapper.thisObjectWrongPolicy(e);
        }
    }

    @Override
    public POA poa(Servant self) {
        try {
            return (POA)((java.lang.Object)this.orb.peekInvocationInfo().oa());
        }
        catch (EmptyStackException exception) {
            POA returnValue = this.factory.lookupPOA(self);
            if (returnValue != null) {
                return returnValue;
            }
            throw this.wrapper.noContext(exception);
        }
    }

    @Override
    public byte[] object_id(Servant self) {
        try {
            return this.orb.peekInvocationInfo().id();
        }
        catch (EmptyStackException exception) {
            throw this.wrapper.noContext(exception);
        }
    }

    @Override
    public POA default_POA(Servant self) {
        return this.factory.getRootPOA();
    }

    @Override
    public boolean is_a(Servant self, String repId) {
        String[] repositoryIds = self._all_interfaces(this.poa(self), this.object_id(self));
        for (int i = 0; i < repositoryIds.length; ++i) {
            if (!repId.equals(repositoryIds[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean non_existent(Servant self) {
        try {
            byte[] oid = this.orb.peekInvocationInfo().id();
            return oid == null;
        }
        catch (EmptyStackException exception) {
            throw this.wrapper.noContext(exception);
        }
    }

    @Override
    public Object get_interface_def(Servant Self2) {
        throw this.wrapper.methodNotImplemented();
    }

    @Override
    public Object get_component(Servant self) {
        return null;
    }

    @Override
    public InterfaceDef get_interface(Servant self) {
        return InterfaceDefHelper.narrow(this.get_interface_def(self));
    }
}

