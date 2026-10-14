/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CSI.GSS_NT_ExportedNameHelper;
import org.omg.CSI.IdentityExtensionHelper;
import org.omg.CSI.IdentityToken;
import org.omg.CSI.IdentityTokenTypeHelper;
import org.omg.CSI.X501DistinguishedNameHelper;
import org.omg.CSI.X509CertificateChainHelper;

public abstract class IdentityTokenHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<IdentityTokenHelper> clazz = IdentityTokenHelper.class;
        synchronized (IdentityTokenHelper.class) {
            if (_type != null) return _type;
            UnionMember[] members = new UnionMember[6];
            Any label_any = ORB.init().create_any();
            label_any.insert_ulong(0);
            members[0] = new UnionMember("absent", label_any, ORB.init().get_primitive_tc(TCKind.from_int(8)), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(1);
            members[1] = new UnionMember("anonymous", label_any, ORB.init().get_primitive_tc(TCKind.from_int(8)), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(2);
            members[2] = new UnionMember("principal_name", label_any, ORB.init().create_alias_tc(GSS_NT_ExportedNameHelper.id(), "GSS_NT_ExportedName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(4);
            members[3] = new UnionMember("certificate_chain", label_any, ORB.init().create_alias_tc(X509CertificateChainHelper.id(), "X509CertificateChain", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(8);
            members[4] = new UnionMember("dn", label_any, ORB.init().create_alias_tc(X501DistinguishedNameHelper.id(), "X501DistinguishedName", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null);
            label_any = ORB.init().create_any();
            label_any.insert_octet((byte)0);
            members[5] = new UnionMember("id", label_any, ORB.init().create_alias_tc(IdentityExtensionHelper.id(), "IdentityExtension", ORB.init().create_sequence_tc(0, ORB.init().get_primitive_tc(TCKind.from_int(10)))), null);
            _type = ORB.init().create_union_tc(IdentityTokenHelper.id(), "IdentityToken", ORB.init().create_alias_tc(IdentityTokenTypeHelper.id(), "IdentityTokenType", ORB.init().get_primitive_tc(TCKind.from_int(5))), members);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, IdentityToken s2) {
        any.type(IdentityTokenHelper.type());
        IdentityTokenHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static IdentityToken extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            IdentityToken identityToken = IdentityTokenHelper.read(in);
            return identityToken;
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
        return "IDL:omg.org/CSI/IdentityToken:1.0";
    }

    public static IdentityToken read(InputStream in) {
        IdentityToken result = new IdentityToken();
        int disc = in.read_ulong();
        switch (disc) {
            case 0: {
                boolean _var = in.read_boolean();
                result.absent(_var);
                break;
            }
            case 1: {
                boolean _var = in.read_boolean();
                result.anonymous(_var);
                break;
            }
            case 2: {
                byte[] _var = GSS_NT_ExportedNameHelper.read(in);
                result.principal_name(_var);
                break;
            }
            case 4: {
                byte[] _var = X509CertificateChainHelper.read(in);
                result.certificate_chain(_var);
                break;
            }
            case 8: {
                byte[] _var = X501DistinguishedNameHelper.read(in);
                result.dn(_var);
                break;
            }
            default: {
                byte[] _var = IdentityExtensionHelper.read(in);
                result.id(_var);
            }
        }
        return result;
    }

    public static void write(OutputStream out, IdentityToken s2) {
        out.write_ulong(s2.discriminator());
        switch (s2.discriminator()) {
            case 0: {
                out.write_boolean(s2.absent());
                break;
            }
            case 1: {
                out.write_boolean(s2.anonymous());
                break;
            }
            case 2: {
                GSS_NT_ExportedNameHelper.write(out, s2.principal_name());
                break;
            }
            case 4: {
                X509CertificateChainHelper.write(out, s2.certificate_chain());
                break;
            }
            case 8: {
                X501DistinguishedNameHelper.write(out, s2.dn());
                break;
            }
            default: {
                IdentityExtensionHelper.write(out, s2.id());
            }
        }
    }
}

