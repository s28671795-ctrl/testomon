/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.OBJECT_NOT_EXIST;
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

public abstract class LocalObject
implements Object {
    private transient ORB orb;

    public LocalObject() {
    }

    public LocalObject(ORB orb) {
        this.orb = orb;
    }

    @Override
    public boolean _is_equivalent(Object that) {
        return this.equals(that);
    }

    @Override
    public boolean _non_existent() {
        return false;
    }

    @Override
    public int _hash(int maximum) {
        return this.hashCode();
    }

    public String[] _ids() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public boolean _is_a(String repositoryId) {
        String[] ids = this._ids();
        for (int i = 0; i < ids.length; ++i) {
            if (!repositoryId.equals(ids[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    public Object _duplicate() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void _release() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Request _request(String operation) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Object _get_component() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Request _create_request(Context ctx, String operation, NVList arg_list, NamedValue result, ExceptionList exceptions, ContextList contexts) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public InterfaceDef _get_interface() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Object _get_interface_def() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public String _repository_id() {
        String[] ids = this._ids();
        return ids[0];
    }

    public ORB _orb() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Policy _get_policy(int policy_type) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public DomainManager[] _get_domain_managers() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Object _set_policy_override(Policy[] policies, SetOverrideType set_add) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Object _set_policy_overrides(Policy[] policies, SetOverrideType set_add) {
        throw new NO_IMPLEMENT();
    }

    public boolean _is_local() {
        throw new NO_IMPLEMENT();
    }

    public ServantObject _servant_preinvoke(String operation, Class expectedType) {
        throw new NO_IMPLEMENT();
    }

    public void _servant_postinvoke(ServantObject servant) {
        throw new NO_IMPLEMENT();
    }

    public OutputStream _request(String operation, boolean responseExpected) {
        throw new NO_IMPLEMENT();
    }

    public InputStream _invoke(OutputStream output) throws ApplicationException, RemarshalException {
        throw new NO_IMPLEMENT();
    }

    public void _releaseReply(InputStream input) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Policy _get_client_policy(int type) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public Policy[] _get_policy_overrides(int[] types) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public boolean _validate_connection(PolicyListHolder inconsistent_policies) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public ORB _get_orb() {
        if (this.orb == null) {
            throw new OBJECT_NOT_EXIST(0, CompletionStatus.COMPLETED_NO);
        }
        return this.orb;
    }
}

