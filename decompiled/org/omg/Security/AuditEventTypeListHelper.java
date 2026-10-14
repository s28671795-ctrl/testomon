/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AuditEventType;
import org.omg.Security.AuditEventTypeHelper;
import org.omg.Security.EventTypeHelper;
import org.omg.Security.ExtensibleFamilyHelper;

public abstract class AuditEventTypeListHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, AuditEventType[] s2) {
        any.type(AuditEventTypeListHelper.type());
        AuditEventTypeListHelper.write(any.create_output_stream(), s2);
    }

    public static AuditEventType[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return AuditEventTypeListHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditEventTypeListHelper> clazz = AuditEventTypeListHelper.class;
        synchronized (AuditEventTypeListHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(AuditEventTypeListHelper.id(), "AuditEventTypeList", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(AuditEventTypeHelper.id(), "AuditEventType", new StructMember[]{new StructMember("event_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("event_type", ORB.init().create_alias_tc(EventTypeHelper.id(), "EventType", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/Security/AuditEventTypeList:1.0";
    }

    public static AuditEventType[] read(InputStream _in) {
        int _l_result9 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result9 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result9);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        AuditEventType[] _result = new AuditEventType[_l_result9];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = AuditEventTypeHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, AuditEventType[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            AuditEventTypeHelper.write(_out, _s[i]);
        }
    }
}

