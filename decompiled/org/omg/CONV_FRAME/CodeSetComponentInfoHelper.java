/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CONV_FRAME;

import java.io.IOException;
import org.omg.CONV_FRAME.CodeSetComponentHelper;
import org.omg.CONV_FRAME.CodeSetComponentInfo;
import org.omg.CONV_FRAME.CodeSetIdHelper;
import org.omg.CONV_FRAME.CodeSetIdSeqHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class CodeSetComponentInfoHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<CodeSetComponentInfoHelper> clazz = CodeSetComponentInfoHelper.class;
        synchronized (CodeSetComponentInfoHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_struct_tc(CodeSetComponentInfoHelper.id(), "CodeSetComponentInfo", new StructMember[]{new StructMember("ForCharData", ORB.init().create_struct_tc(CodeSetComponentHelper.id(), "CodeSetComponent", new StructMember[]{new StructMember("native_code_set", ORB.init().create_alias_tc(CodeSetIdHelper.id(), "CodeSetId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("conversion_code_sets", ORB.init().create_alias_tc(CodeSetIdSeqHelper.id(), "CodeSetIdSeq", ORB.init().create_sequence_tc(0, CodeSetIdHelper.type())), null)}), null), new StructMember("ForWcharData", ORB.init().create_struct_tc(CodeSetComponentHelper.id(), "CodeSetComponent", new StructMember[]{new StructMember("native_code_set", ORB.init().create_alias_tc(CodeSetIdHelper.id(), "CodeSetId", ORB.init().get_primitive_tc(TCKind.from_int(5))), null), new StructMember("conversion_code_sets", ORB.init().create_alias_tc(CodeSetIdSeqHelper.id(), "CodeSetIdSeq", ORB.init().create_sequence_tc(0, CodeSetIdHelper.type())), null)}), null)});
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, CodeSetComponentInfo s2) {
        any.type(CodeSetComponentInfoHelper.type());
        CodeSetComponentInfoHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CodeSetComponentInfo extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            CodeSetComponentInfo codeSetComponentInfo = CodeSetComponentInfoHelper.read(in);
            return codeSetComponentInfo;
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
        return "IDL:omg.org/CONV_FRAME/CodeSetComponentInfo:1.0";
    }

    public static CodeSetComponentInfo read(InputStream in) {
        CodeSetComponentInfo result = new CodeSetComponentInfo();
        result.ForCharData = CodeSetComponentHelper.read(in);
        result.ForWcharData = CodeSetComponentHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, CodeSetComponentInfo s2) {
        CodeSetComponentHelper.write(out, s2.ForCharData);
        CodeSetComponentHelper.write(out, s2.ForWcharData);
    }
}

