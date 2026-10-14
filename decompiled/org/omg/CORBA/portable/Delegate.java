/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyListHolder;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;

public abstract class Delegate {
    public abstract InterfaceDef get_interface(Object var1);

    public abstract Object get_interface_def(Object var1);

    public abstract String repository_id(Object var1);

    public abstract Object duplicate(Object var1);

    public abstract void release(Object var1);

    public abstract boolean is_a(Object var1, String var2);

    public abstract boolean non_existent(Object var1);

    public abstract boolean is_equivalent(Object var1, Object var2);

    public abstract int hash(Object var1, int var2);

    public abstract Request create_request(Object var1, Context var2, String var3, NVList var4, NamedValue var5);

    public abstract Request create_request(Object var1, Context var2, String var3, NVList var4, NamedValue var5, ExceptionList var6, ContextList var7);

    public abstract Request request(Object var1, String var2);

    public OutputStream request(Object self, String operation, boolean responseExpected) {
        throw new NO_IMPLEMENT();
    }

    public InputStream invoke(Object self, OutputStream os) throws ApplicationException, RemarshalException {
        throw new NO_IMPLEMENT();
    }

    public void releaseReply(Object self, InputStream is) {
        throw new NO_IMPLEMENT();
    }

    public Policy get_policy(Object self, int policy_type) {
        throw new NO_IMPLEMENT();
    }

    public DomainManager[] get_domain_managers(Object self) {
        throw new NO_IMPLEMENT();
    }

    public Object set_policy_override(Object self, Policy[] policies, SetOverrideType set_add) {
        throw new NO_IMPLEMENT();
    }

    public Object set_policy_overrides(Object self, Policy[] policies, SetOverrideType set_add) {
        throw new NO_IMPLEMENT();
    }

    public Policy get_client_policy(Object self, int type) {
        throw new NO_IMPLEMENT();
    }

    public Policy[] get_policy_overrides(Object self, int[] types) {
        throw new NO_IMPLEMENT();
    }

    public boolean validate_connection(Object self, PolicyListHolder inconsistent_policies) {
        throw new NO_IMPLEMENT();
    }

    public Object get_component(Object self) {
        throw new NO_IMPLEMENT();
    }

    public ORB orb(Object self) {
        throw new NO_IMPLEMENT();
    }

    public boolean is_local(Object self) {
        return false;
    }

    public ServantObject servant_preinvoke(Object self, String operation, Class expectedType) {
        return null;
    }

    public void servant_postinvoke(Object self, ServantObject servant) {
    }

    public String toString(Object self) {
        return self.getClass().getName() + ":" + this.toString();
    }

    public int hashCode(Object self) {
        return System.identityHashCode(self);
    }

    public boolean equals(Object self, java.lang.Object obj) {
        return self == obj;
    }
}

