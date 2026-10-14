/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import java.io.IOException;
import org.omg.CONV_FRAME.CodeSetComponent;
import org.omg.CONV_FRAME.CodeSetIdHelper;
import org.omg.CONV_FRAME.CodeSetIdSeqHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class CodeSetComponentHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CodeSetComponentHelper> clazz = CodeSetComponentHelper.class;
        synchronized (CodeSetComponentHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CodeSetComponentHelper.id(), "CodeSetComponent", new StructMember[]{new StructMember("native_code_set", ORB.init().create_alias_tc(CodeSetIdHelper.id(), "CodeSetId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("conversion_code_sets", ORB.init().create_alias_tc(CodeSetIdSeqHelper.id(), "CodeSetIdSeq", ORB.init().create_sequence_tc(0, CodeSetIdHelper.type())), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CodeSetComponent s2) {
        any.type(CodeSetComponentHelper.type());
        CodeSetComponentHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CodeSetComponent extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CodeSetComponent codeSetComponent = CodeSetComponentHelper.read(in);
            return codeSetComponent;
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
        return "IDL:omg.org/CONV_FRAME/CodeSetComponent:1.0";
    }

    public static CodeSetComponent read(InputStream in) {
        CodeSetComponent result = new CodeSetComponent();
        result.native_code_set = in.read_ulong();
        result.conversion_code_sets = CodeSetIdSeqHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, CodeSetComponent s2) {
        out.write_ulong(s2.native_code_set);
        CodeSetIdSeqHelper.write(out, s2.conversion_code_sets);
    }
}

