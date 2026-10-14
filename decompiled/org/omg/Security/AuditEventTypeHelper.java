/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.Security.AuditEventType;
import org.omg.Security.EventTypeHelper;
import org.omg.Security.ExtensibleFamilyHelper;

public abstract class AuditEventTypeHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<AuditEventTypeHelper> clazz = AuditEventTypeHelper.class;
        synchronized (AuditEventTypeHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(AuditEventTypeHelper.id(), "AuditEventType", new StructMember[]{new StructMember("event_family", ORB.init().create_struct_tc(ExtensibleFamilyHelper.id(), "ExtensibleFamily", new StructMember[]{new StructMember("family_definer", ORB.init().get_primitive_tc(TCKind.from_int(4)), null), new StructMember("family", ORB.init().get_primitive_tc(TCKind.from_int(4)), null)}), null), new StructMember("event_type", ORB.init().create_alias_tc(EventTypeHelper.id(), "EventType", ORB.init().get_primitive_tc(TCKind.from_int(4))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, AuditEventType s2) {
        any.type(AuditEventTypeHelper.type());
        AuditEventTypeHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static AuditEventType extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            AuditEventType auditEventType = AuditEventTypeHelper.read(in);
            return auditEventType;
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
        return "IDL:omg.org/Security/AuditEventType:1.0";
    }

    public static AuditEventType read(InputStream in) {
        AuditEventType result = new AuditEventType();
        result.event_family = ExtensibleFamilyHelper.read(in);
        result.event_type = in.read_ushort();
        return result;
    }

    public static void write(OutputStream out, AuditEventType s2) {
        ExtensibleFamilyHelper.write(out, s2.event_family);
        out.write_ushort(s2.event_type);
    }
}

