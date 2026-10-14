/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.InvalidPolicies;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class InvalidPoliciesHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<InvalidPoliciesHelper> clazz = InvalidPoliciesHelper.class;
        synchronized (InvalidPoliciesHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_exception_tc(InvalidPoliciesHelper.id(), "InvalidPolicies", new StructMember[]{new StructMember("indicies", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(4))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, InvalidPolicies s2) {
        any.type(InvalidPoliciesHelper.type());
        InvalidPoliciesHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InvalidPolicies extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            InvalidPolicies invalidPolicies = InvalidPoliciesHelper.read(in);
            return invalidPolicies;
        }
        finally {
            try {
                in.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Unexpected exception " + e.toString());
            }
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/InvalidPolicies:1.0";
    }

    public static InvalidPolicies read(InputStream in) {
        String id = in.read_string();
        if (!id.equals(InvalidPoliciesHelper.id())) {
            throw new MARSHAL("wrong id: " + id);
        }
        int _lx02 = in.read_long();
        try {
            int x = in.available();
            if (x > 0 && _lx02 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _lx02);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        short[] x0 = new short[_lx02];
        in.read_ushort_array(x0, 0, _lx02);
        InvalidPolicies result = new InvalidPolicies(id, x0);
        return result;
    }

    public static void write(OutputStream out, InvalidPolicies s2) {
        out.write_string(InvalidPoliciesHelper.id());
        out.write_long(s2.indicies.length);
        out.write_ushort_array(s2.indicies, 0, s2.indicies.length);
    }
}

