/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyListHolder;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ServantObject;

public abstract class ObjectImpl
implements Object {
    private transient Delegate __delegate;

    public Delegate _get_delegate() {
        if (this.__delegate == null) {
            throw new BAD_OPERATION();
        }
        return this.__delegate;
    }

    public void _set_delegate(Delegate delegate) {
        this.__delegate = delegate;
    }

    public abstract String[] _ids();

    @Override
    public InterfaceDef _get_interface() {
        return this._get_delegate().get_interface(this);
    }

    @Override
    public Object _get_interface_def() {
        return this._get_delegate().get_interface_def(this);
    }

    @Override
    public String _repository_id() {
        return this._get_delegate().repository_id(this);
    }

    @Override
    public Object _duplicate() {
        return this._get_delegate().duplicate(this);
    }

    @Override
    public void _release() {
        this._get_delegate().release(this);
    }

    @Override
    public boolean _is_a(String repository_id) {
        return this._get_delegate().is_a(this, repository_id);
    }

    @Override
    public boolean _is_equivalent(Object that) {
        return this._get_delegate().is_equivalent(this, that);
    }

    @Override
    public boolean _non_existent() {
        return this._get_delegate().non_existent(this);
    }

    @Override
    public int _hash(int maximum) {
        return this._get_delegate().hash(this, maximum);
    }

    @Override
    public Request _request(String operation) {
        return this._get_delegate().request(this, operation);
    }

    public OutputStream _request(String operation, boolean responseExpected) {
        return this._get_delegate().request(this, operation, responseExpected);
    }

    public InputStream _invoke(OutputStream output) throws ApplicationException, RemarshalException {
        return this._get_delegate().invoke(this, output);
    }

    public void _releaseReply(InputStream input) {
        this._get_delegate().releaseReply(this, input);
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
        return this._get_delegate().create_request(this, ctx, operation, arg_list, result);
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exceptions, ContextList contexts) {
        return this._get_delegate().create_request(this, ctx, operation, arg_list, result, exceptions, contexts);
    }

    @Override
    public Policy _get_policy(int policy_type) {
        return this._get_delegate().get_policy(this, policy_type);
    }

    @Override
    public DomainManager[] _get_domain_managers() {
        return this._get_delegate().get_domain_managers(this);
    }

    @Override
    public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
        return this._get_delegate().set_policy_overrides(this, policies, set_add);
    }

    @Override
    public Object _set_policy_overrides(Policy[] policies, SetOverrideType set_add) {
        return this._get_delegate().set_policy_overrides(this, policies, set_add);
    }

    public ORB _orb() {
        return this._get_delegate().orb(this);
    }

    public boolean _is_local() {
        return this._get_delegate().is_local(this);
    }

    public ServantObject _servant_preinvoke(String operation, Class expectedType) {
        return this._get_delegate().servant_preinvoke(this, operation, expectedType);
    }

    public void _servant_postinvoke(ServantObject servant) {
        this._get_delegate().servant_postinvoke(this, servant);
    }

    public String toString() {
        if (this.__delegate != null) {
            return this.__delegate.toString(this);
        }
        return this.getClass().getName() + ":no delegate set";
    }

    public int hashCode() {
        if (this.__delegate != null) {
            return this.__delegate.hashCode(this);
        }
        return System.identityHashCode(this);
    }

    public boolean equals(java.lang.Object obj) {
        if (this.__delegate != null) {
            return this.__delegate.equals(this, obj);
        }
        return this == obj;
    }

    @Override
    public Policy _get_client_policy(int type) {
        return this._get_delegate().get_client_policy(this, type);
    }

    @Override
    public Policy[] _get_policy_overrides(int[] types) {
        return this._get_delegate().get_policy_overrides(this, types);
    }

    @Override
    public boolean _validate_connection(PolicyListHolder inconsistent_policies) {
        return this._get_delegate().validate_connection(this, inconsistent_policies);
    }

    @Override
    public Object _get_component() {
        return this._get_delegate().get_component(this);
    }

    @Override
    public ORB _get_orb() {
        return this._orb();
    }
}

