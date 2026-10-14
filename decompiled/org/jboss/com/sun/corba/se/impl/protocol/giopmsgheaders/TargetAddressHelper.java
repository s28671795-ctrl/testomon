/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders;

import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.AddressingDispositionHelper;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.IORAddressingInfo;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.IORAddressingInfoHelper;
import org.jboss.com.sun.corba.se.impl.protocol.giopmsgheaders.TargetAddress;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.IOP.TaggedProfile;
import org.omg.IOP.TaggedProfileHelper;

public abstract class TargetAddressHelper {
    private static String _id = "IDL:messages/TargetAddress:1.0";
    private static TypeCode __typeCode = null;

    public static void insert(Any a, TargetAddress that) {
        OutputStream out = a.create_output_stream();
        a.type(TargetAddressHelper.type());
        TargetAddressHelper.write(out, that);
        a.read_value(out.create_input_stream(), TargetAddressHelper.type());
    }

    public static TargetAddress extract(Any a) {
        return TargetAddressHelper.read(a.create_input_stream());
    }

    public static synchronized TypeCode type() {
        if (__typeCode == null) {
            TypeCode _disTypeCode0 = ORB.init().get_primitive_tc(TCKind.tk_short);
            _disTypeCode0 = ORB.init().create_alias_tc(AddressingDispositionHelper.id(), "AddressingDisposition", _disTypeCode0);
            UnionMember[] _members0 = new UnionMember[3];
            Any _anyOf_members0 = ORB.init().create_any();
            _anyOf_members0.insert_short((short)0);
            TypeCode _tcOf_members0 = ORB.init().get_primitive_tc(TCKind.tk_octet);
            _tcOf_members0 = ORB.init().create_sequence_tc(0, _tcOf_members0);
            _members0[0] = new UnionMember("object_key", _anyOf_members0, _tcOf_members0, null);
            _anyOf_members0 = ORB.init().create_any();
            _anyOf_members0.insert_short((short)1);
            _tcOf_members0 = TaggedProfileHelper.type();
            _members0[1] = new UnionMember("profile", _anyOf_members0, _tcOf_members0, null);
            _anyOf_members0 = ORB.init().create_any();
            _anyOf_members0.insert_short((short)2);
            _tcOf_members0 = IORAddressingInfoHelper.type();
            _members0[2] = new UnionMember("ior", _anyOf_members0, _tcOf_members0, null);
            __typeCode = ORB.init().create_union_tc(TargetAddressHelper.id(), "TargetAddress", _disTypeCode0, _members0);
        }
        return __typeCode;
    }

    public static String id() {
        return _id;
    }

    public static TargetAddress read(InputStream istream) {
        TargetAddress value = new TargetAddress();
        short _dis0 = 0;
        _dis0 = istream.read_short();
        switch (_dis0) {
            case 0: {
                byte[] _object_key = null;
                int _len1 = istream.read_long();
                _object_key = new byte[_len1];
                istream.read_octet_array(_object_key, 0, _len1);
                value.object_key(_object_key);
                break;
            }
            case 1: {
                TaggedProfile _profile = null;
                _profile = TaggedProfileHelper.read(istream);
                value.profile(_profile);
                break;
            }
            case 2: {
                IORAddressingInfo _ior = null;
                _ior = IORAddressingInfoHelper.read(istream);
                value.ior(_ior);
                break;
            }
            default: {
                throw new BAD_OPERATION();
            }
        }
        return value;
    }

    public static void write(OutputStream ostream, TargetAddress value) {
        ostream.write_short(value.discriminator());
        switch (value.discriminator()) {
            case 0: {
                ostream.write_long(value.object_key().length);
                ostream.write_octet_array(value.object_key(), 0, value.object_key().length);
                break;
            }
            case 1: {
                TaggedProfileHelper.write(ostream, value.profile());
                break;
            }
            case 2: {
                IORAddressingInfoHelper.write(ostream, value.ior());
                break;
            }
            default: {
                throw new BAD_OPERATION();
            }
        }
    }
}

