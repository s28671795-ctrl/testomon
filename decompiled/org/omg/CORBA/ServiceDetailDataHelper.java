/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ServiceDetailDataHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, byte[] s2) {
        any.type(ServiceDetailDataHelper.type());
        ServiceDetailDataHelper.write(any.create_output_stream(), s2);
    }

    public static byte[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ServiceDetailDataHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceDetailDataHelper> clazz = ServiceDetailDataHelper.class;
        synchronized (ServiceDetailDataHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ServiceDetailDataHelper.id(), "ServiceDetailData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ServiceDetailData:1.0";
    }

    public static byte[] read(InputStream _in) {
        byte[] _result = OctetSeqHelper.read(_in);
        return _result;
    }

    public static void write(OutputStream _out, byte[] _s) {
        OctetSeqHelper.write(_out, _s);
    }
}

