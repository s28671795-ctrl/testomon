/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.org.omg.CORBA.ValueDefPackage;

import org.jboss.com.sun.org.omg.CORBA.AttrDescriptionSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.AttributeDescriptionHelper;
import org.jboss.com.sun.org.omg.CORBA.IdentifierHelper;
import org.jboss.com.sun.org.omg.CORBA.InitializerHelper;
import org.jboss.com.sun.org.omg.CORBA.InitializerSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.OpDescriptionSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.OperationDescriptionHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdHelper;
import org.jboss.com.sun.org.omg.CORBA.RepositoryIdSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.jboss.com.sun.org.omg.CORBA.ValueMemberHelper;
import org.jboss.com.sun.org.omg.CORBA.ValueMemberSeqHelper;
import org.jboss.com.sun.org.omg.CORBA.VersionSpecHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public final class FullValueDescriptionHelper {
    private static String _id = "IDL:omg.org/CORBA/ValueDef/FullValueDescription:1.0";
    private static TypeCode __typeCode = null;
    private static boolean __active = false;

    public static void insert(Any a, FullValueDescription that) {
        OutputStream out = a.create_output_stream();
        a.type(FullValueDescriptionHelper.type());
        FullValueDescriptionHelper.write(out, that);
        a.read_value(out.create_input_stream(), FullValueDescriptionHelper.type());
    }

    public static FullValueDescription extract(Any a) {
        return FullValueDescriptionHelper.read(a.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static synchronized TypeCode type() {
        if (__typeCode != null) return __typeCode;
        Class<TypeCode> clazz = TypeCode.class;
        synchronized (TypeCode.class) {
            if (__typeCode != null) return __typeCode;
            if (__active) {
                // ** MonitorExit[var0] (shouldn't be in output)
                return ORB.init().create_recursive_tc(_id);
            }
            __active = true;
            StructMember[] _members0 = new StructMember[15];
            TypeCode _tcOf_members0 = null;
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(IdentifierHelper.id(), "Identifier", _tcOf_members0);
            _members0[0] = new StructMember("name", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[1] = new StructMember("id", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_boolean);
            _members0[2] = new StructMember("is_abstract", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_boolean);
            _members0[3] = new StructMember("is_custom", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[4] = new StructMember("defined_in", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(VersionSpecHelper.id(), "VersionSpec", _tcOf_members0);
            _members0[5] = new StructMember("version", _tcOf_members0, null);
            _tcOf_members0 = OperationDescriptionHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(OpDescriptionSeqHelper.id(), "OpDescriptionSeq", _tcOf_members0);
            _members0[6] = new StructMember("operations", _tcOf_members0, null);
            _tcOf_members0 = AttributeDescriptionHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(AttrDescriptionSeqHelper.id(), "AttrDescriptionSeq", _tcOf_members0);
            _members0[7] = new StructMember("attributes", _tcOf_members0, null);
            _tcOf_members0 = ValueMemberHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(ValueMemberSeqHelper.id(), "ValueMemberSeq", _tcOf_members0);
            _members0[8] = new StructMember("members", _tcOf_members0, null);
            _tcOf_members0 = InitializerHelper.type();
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(InitializerSeqHelper.id(), "InitializerSeq", _tcOf_members0);
            _members0[9] = new StructMember("initializers", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", _tcOf_members0);
            _members0[10] = new StructMember("supported_interfaces", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdSeqHelper.id(), "RepositoryIdSeq", _tcOf_members0);
            _members0[11] = new StructMember("abstract_base_values", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_boolean);
            _members0[12] = new StructMember("is_truncatable", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().create_string_tc(0);
            _tcOf_members0 = ORB.init().create_alias_tc(RepositoryIdHelper.id(), "RepositoryId", _tcOf_members0);
            _members0[13] = new StructMember("base_value", _tcOf_members0, null);
            _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_TypeCode);
            _members0[14] = new StructMember("type", _tcOf_members0, null);
            __typeCode = ORB.init().create_struct_tc(FullValueDescriptionHelper.id(), "FullValueDescription", _members0);
            __active = false;
            // ** MonitorExit[var0] (shouldn't be in output)
            return __typeCode;
        }
    }

    public static String id() {
        return _id;
    }

    public static FullValueDescription read(InputStream istream) {
        FullValueDescription value = new FullValueDescription();
        value.name = istream.read_string();
        value.id = istream.read_string();
        value.is_abstract = istream.read_boolean();
        value.is_custom = istream.read_boolean();
        value.defined_in = istream.read_string();
        value.version = istream.read_string();
        value.operations = OpDescriptionSeqHelper.read(istream);
        value.attributes = AttrDescriptionSeqHelper.read(istream);
        value.members = ValueMemberSeqHelper.read(istream);
        value.initializers = InitializerSeqHelper.read(istream);
        value.supported_interfaces = RepositoryIdSeqHelper.read(istream);
        value.abstract_base_values = RepositoryIdSeqHelper.read(istream);
        value.is_truncatable = istream.read_boolean();
        value.base_value = istream.read_string();
        value.type = istream.read_TypeCode();
        return value;
    }

    public static void write(OutputStream ostream, FullValueDescription value) {
        ostream.write_string(value.name);
        ostream.write_string(value.id);
        ostream.write_boolean(value.is_abstract);
        ostream.write_boolean(value.is_custom);
        ostream.write_string(value.defined_in);
        ostream.write_string(value.version);
        OpDescriptionSeqHelper.write(ostream, value.operations);
        AttrDescriptionSeqHelper.write(ostream, value.attributes);
        ValueMemberSeqHelper.write(ostream, value.members);
        InitializerSeqHelper.write(ostream, value.initializers);
        RepositoryIdSeqHelper.write(ostream, value.supported_interfaces);
        RepositoryIdSeqHelper.write(ostream, value.abstract_base_values);
        ostream.write_boolean(value.is_truncatable);
        ostream.write_string(value.base_value);
        ostream.write_TypeCode(value.type);
    }
}

