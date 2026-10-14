/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;
import org.omg.PortableInterceptor.ObjectReferenceTemplate;

public abstract class ObjectReferenceTemplateSeqHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, ObjectReferenceTemplate[] s2) {
        any.type(ObjectReferenceTemplateSeqHelper.type());
        ObjectReferenceTemplateSeqHelper.write(any.create_output_stream(), s2);
    }

    public static ObjectReferenceTemplate[] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return ObjectReferenceTemplateSeqHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ObjectReferenceTemplateSeqHelper> clazz = ObjectReferenceTemplateSeqHelper.class;
        synchronized (ObjectReferenceTemplateSeqHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(ObjectReferenceTemplateSeqHelper.id(), "ObjectReferenceTemplateSeq", ORB.init().create_sequence_tc(0, ORB.init().create_value_tc("IDL:omg.org/PortableInterceptor/ObjectReferenceTemplate:1.0", "ObjectReferenceTemplate", (short)2, null, null)));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableInterceptor/ObjectReferenceTemplateSeq:1.0";
    }

    public static ObjectReferenceTemplate[] read(org.omg.CORBA.portable.InputStream _in) {
        int _l_result0 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result0 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        ObjectReferenceTemplate[] _result = new ObjectReferenceTemplate[_l_result0];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = (ObjectReferenceTemplate)((InputStream)_in).read_value("IDL:omg.org/PortableInterceptor/ObjectReferenceTemplate:1.0");
        }
        return _result;
    }

    public static void write(org.omg.CORBA.portable.OutputStream _out, ObjectReferenceTemplate[] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            ((OutputStream)_out).write_value(_s[i]);
        }
    }
}

