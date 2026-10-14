/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.applet.Applet;
import java.util.Properties;
import org.omg.CORBA.Any;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.Current;
import org.omg.CORBA.Environment;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.ORBSingleton;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.CORBA.Request;
import org.omg.CORBA.ServiceInformationHolder;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.WrongTransaction;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ValueFactory;

public abstract class ORB
extends ORBSingleton {
    public String id() {
        throw new NO_IMPLEMENT();
    }

    public abstract Object string_to_object(String var1);

    public abstract String object_to_string(Object var1);

    public abstract NVList create_list(int var1);

    public NVList create_operation_list(Object oper) {
        throw new NO_IMPLEMENT();
    }

    public abstract NamedValue create_named_value(String var1, Any var2, int var3);

    public abstract ExceptionList create_exception_list();

    public abstract ContextList create_context_list();

    public abstract Context get_default_context();

    public abstract Environment create_environment();

    public abstract void send_multiple_requests_oneway(Request[] var1);

    public abstract void send_multiple_requests_deferred(Request[] var1);

    public abstract boolean poll_next_response();

    public abstract Request get_next_response() throws WrongTransaction;

    public boolean get_service_information(short service_type, ServiceInformationHolder service_info) {
        throw new NO_IMPLEMENT();
    }

    public abstract String[] list_initial_services();

    public void register_initial_reference(String object_name, Object object) throws InvalidName {
        throw new NO_IMPLEMENT();
    }

    public abstract Object resolve_initial_references(String var1) throws InvalidName;

    public abstract TypeCode create_struct_tc(String var1, String var2, StructMember[] var3);

    public abstract TypeCode create_union_tc(String var1, String var2, TypeCode var3, UnionMember[] var4);

    public abstract TypeCode create_enum_tc(String var1, String var2, String[] var3);

    public abstract TypeCode create_alias_tc(String var1, String var2, TypeCode var3);

    public abstract TypeCode create_exception_tc(String var1, String var2, StructMember[] var3);

    public abstract TypeCode create_interface_tc(String var1, String var2);

    public abstract TypeCode create_string_tc(int var1);

    public abstract TypeCode create_wstring_tc(int var1);

    public TypeCode create_fixed_tc(short digits, short scale) {
        throw new NO_IMPLEMENT();
    }

    public abstract TypeCode create_sequence_tc(int var1, TypeCode var2);

    public abstract TypeCode create_recursive_sequence_tc(int var1, int var2);

    public abstract TypeCode create_array_tc(int var1, TypeCode var2);

    public TypeCode create_value_tc(String id, String name, short type_modifier, TypeCode concrete_base, ValueMember[] members) {
        throw new NO_IMPLEMENT();
    }

    public TypeCode create_value_box_tc(String id, String name, TypeCode boxed_type) {
        throw new NO_IMPLEMENT();
    }

    public TypeCode create_native_tc(String id, String name) {
        throw new NO_IMPLEMENT();
    }

    public TypeCode create_recursive_tc(String id) {
        throw new NO_IMPLEMENT();
    }

    public TypeCode create_abstract_interface_tc(String id, String name) {
        throw new NO_IMPLEMENT();
    }

    public TypeCode create_local_interface_tc(String id, String name) {
        throw new NO_IMPLEMENT();
    }

    public Current get_current() {
        throw new NO_IMPLEMENT();
    }

    public void connect(Object obj) {
        throw new NO_IMPLEMENT();
    }

    public void disconnect(Object obj) {
        throw new NO_IMPLEMENT();
    }

    public boolean work_pending() {
        throw new NO_IMPLEMENT();
    }

    public void perform_work() {
        throw new NO_IMPLEMENT();
    }

    public void run() {
        throw new NO_IMPLEMENT();
    }

    public void destroy() {
        throw new NO_IMPLEMENT();
    }

    public void shutdown(boolean wait_for_completion) {
        throw new NO_IMPLEMENT();
    }

    public Policy create_policy(int type, Any val) throws PolicyError {
        throw new NO_IMPLEMENT();
    }

    public abstract TypeCode get_primitive_tc(TCKind var1);

    public abstract Any create_any();

    public abstract OutputStream create_output_stream();

    public static ORB init(String[] args2, Properties props) {
        String className = null;
        if (props != null) {
            className = props.getProperty("org.omg.CORBA.ORBClass");
        }
        if (className == null) {
            className = System.getProperty("org.omg.CORBA.ORBClass", "org.jacorb.orb.ORB");
        }
        ORB orb = ORB.create(className);
        orb.set_parameters(args2, props);
        return orb;
    }

    public static ORB init(Applet applet, Properties props) {
        String className = applet.getParameter("org.omg.CORBA.ORBClass");
        if (className == null && props != null) {
            className = props.getProperty("org.omg.CORBA.ORBClass");
        }
        if (className == null) {
            className = "org.jacorb.orb.ORB";
        }
        ORB orb = ORB.create(className);
        orb.set_parameters(applet, props);
        return orb;
    }

    protected abstract void set_parameters(String[] var1, Properties var2);

    protected abstract void set_parameters(Applet var1, Properties var2);

    public Object get_value_def(String repid) {
        throw new NO_IMPLEMENT();
    }

    public ValueFactory register_value_factory(String id, ValueFactory factory2) {
        throw new NO_IMPLEMENT();
    }

    public void unregister_value_factory(String id) {
        throw new NO_IMPLEMENT();
    }

    public ValueFactory lookup_value_factory(String id) {
        throw new NO_IMPLEMENT();
    }

    public void set_delegate(java.lang.Object wrapper) {
        throw new NO_IMPLEMENT();
    }

    public String exception_to_string(SystemException exc) {
        throw new NO_IMPLEMENT();
    }
}

