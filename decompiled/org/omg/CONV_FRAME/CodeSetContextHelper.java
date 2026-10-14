/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import java.io.IOException;
import org.omg.CONV_FRAME.CodeSetContext;
import org.omg.CONV_FRAME.CodeSetIdHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class CodeSetContextHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CodeSetContextHelper> clazz = CodeSetContextHelper.class;
        synchronized (CodeSetContextHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CodeSetContextHelper.id(), "CodeSetContext", new StructMember[]{new StructMember("char_data", ORB.init().create_alias_tc(CodeSetIdHelper.id(), "CodeSetId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("wchar_data", ORB.init().create_alias_tc(CodeSetIdHelper.id(), "CodeSetId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CodeSetContext s2) {
        any.type(CodeSetContextHelper.type());
        CodeSetContextHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CodeSetContext extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CodeSetContext codeSetContext = CodeSetContextHelper.read(in);
            return codeSetContext;
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
        return "IDL:omg.org/CONV_FRAME/CodeSetContext:1.0";
    }

    public static CodeSetContext read(InputStream in) {
        CodeSetContext result = new CodeSetContext();
        result.char_data = in.read_ulong();
        result.wchar_data = in.read_ulong();
        return result;
    }

    public static void write(OutputStream out, CodeSetContext s2) {
        out.write_ulong(s2.char_data);
        out.write_ulong(s2.wchar_data);
    }
}

