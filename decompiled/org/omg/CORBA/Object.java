/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.DomainManager;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.InterfaceDef;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyListHolder;
import org.omg.CORBA.Request;
import org.omg.CORBA.SetOverrideType;

public interface Object {
    public boolean _is_a(String var1);

    public boolean _is_equivalent(Object var1);

    public boolean _non_existent();

    public int _hash(int var1);

    public Object _duplicate();

    public void _release();

    public InterfaceDef _get_interface();

    public Object _get_interface_def();

    public String _repository_id();

    public Request _request(String var1);

    public Request _create_request(Context var1, String var2, NVList var3, NamedValue var4);

    public Request _create_request(Context var1, String var2, NVList var3, NamedValue var4, ExceptionList var5, ContextList var6);

    public Policy _get_policy(int var1);

    public DomainManager[] _get_domain_managers();

    public Object _set_policy_override(Policy[] var1, SetOverrideType var2);

    public Object _set_policy_overrides(Policy[] var1, SetOverrideType var2);

    public Policy _get_client_policy(int var1);

    public Policy[] _get_policy_overrides(int[] var1);

    public boolean _validate_connection(PolicyListHolder var1);

    public Object _get_component();

    public ORB _get_orb();
}

