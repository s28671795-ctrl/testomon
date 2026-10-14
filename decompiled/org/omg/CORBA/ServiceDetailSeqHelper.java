/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.OctetSeqHelper;
import org.omg.CORBA.ServiceDetail;
import org.omg.CORBA.ServiceDetailDataHelper;
import org.omg.CORBA.ServiceDetailHelper;
import org.omg.CORBA.ServiceDetailTypeHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class ServiceDetailSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ServiceDetail[] s2) {
        any.type(ServiceDetailSeqHelper.type());
        ServiceDetailSeqHelper.write(any.create_output_stream(), s2);
    }

    public static ServiceDetail[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ServiceDetailSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ServiceDetailSeqHelper> clazz = ServiceDetailSeqHelper.class;
        synchronized (ServiceDetailSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ServiceDetailSeqHelper.id(), "ServiceDetailSeq", ORB.init().create_sequence_tc(0, ORB.init().create_struct_tc(ServiceDetailHelper.id(), "ServiceDetail", new StructMember[]{new StructMember("service_detail_type", ORB.init().create_alias_tc(ServiceDetailTypeHelper.id(), "ServiceDetailType", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("service_detail", ORB.init().create_alias_tc(ServiceDetailDataHelper.id(), "ServiceDetailData", ORB.init().create_alias_tc(OctetSeqHelper.id(), "OctetSeq", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10))))), null)})));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/CORBA/ServiceDetailSeq:1.0";
    }

    public static ServiceDetail[] read(InputStream _in) {
        int _l_result45 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result45 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result45);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ServiceDetail[] _result = new ServiceDetail[_l_result45];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = ServiceDetailHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, ServiceDetail[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ServiceDetailHelper.write(_out, _s[i]);
        }
    }
}

