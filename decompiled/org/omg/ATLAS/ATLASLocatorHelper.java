/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.io.IOException;
import org.omg.ATLAS.ATLASLocator;
import org.omg.ATLAS.ATLASLocatorTypeHelper;
import org.omg.ATLAS.AuthTokenDispenser;
import org.omg.ATLAS.AuthTokenDispenserHelper;
import org.omg.ATLAS.CosNamingLocator;
import org.omg.ATLAS.CosNamingLocatorHelper;
import org.omg.ATLAS.URLocatorHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;

public abstract class ATLASLocatorHelper {
    private static volatile TypeCode _type;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<ATLASLocatorHelper> clazz = ATLASLocatorHelper.class;
        synchronized (ATLASLocatorHelper.class) {
            if (_type != null) return _type;
            UnionMember[] members = new UnionMember[3];
            Any label_any = ORB.init().create_any();
            label_any.insert_ulong(1);
            members[0] = new UnionMember("naming_locator", label_any, CosNamingLocatorHelper.type(), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(2);
            members[1] = new UnionMember("the_url", label_any, ORB.init().create_alias_tc(URLocatorHelper.id(), "URLocator", ORB.init().create_alias_tc(URLStringHelper.id(), "URLString", ORB.init().create_string_tc(0))), null);
            label_any = ORB.init().create_any();
            label_any.insert_ulong(3);
            members[2] = new UnionMember("the_dispenser", label_any, ORB.init().create_interface_tc("IDL:omg.org/ATLAS/AuthTokenDispenser:1.0", "AuthTokenDispenser"), null);
            _type = ORB.init().create_union_tc(ATLASLocatorHelper.id(), "ATLASLocator", ORB.init().create_alias_tc(ATLASLocatorTypeHelper.id(), "ATLASLocatorType", ORB.init().get_primitive_tc(TCKind.from_int(5))), members);
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static void insert(Any any, ATLASLocator s2) {
        any.type(ATLASLocatorHelper.type());
        ATLASLocatorHelper.write(any.create_output_stream(), s2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ATLASLocator extract(Any any) {
        InputStream in = any.create_input_stream();
        try {
            ATLASLocator aTLASLocator = ATLASLocatorHelper.read(in);
            return aTLASLocator;
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
        return "IDL:omg.org/ATLAS/ATLASLocator:1.0";
    }

    public static ATLASLocator read(InputStream in) {
        ATLASLocator result = new ATLASLocator();
        int disc = in.read_ulong();
        switch (disc) {
            case 1: {
                CosNamingLocator _var = CosNamingLocatorHelper.read(in);
                result.naming_locator(_var);
                break;
            }
            case 2: {
                String _var = in.read_string();
                result.the_url(_var);
                break;
            }
            case 3: {
                AuthTokenDispenser _var = AuthTokenDispenserHelper.read(in);
                result.the_dispenser(_var);
                break;
            }
        }
        return result;
    }

    public static void write(OutputStream out, ATLASLocator s2) {
        out.write_ulong(s2.discriminator());
        switch (s2.discriminator()) {
            case 1: {
                CosNamingLocatorHelper.write(out, s2.naming_locator());
                break;
            }
            case 2: {
                String tmpResult2 = s2.the_url();
                out.write_string(tmpResult2);
                break;
            }
            case 3: {
                AuthTokenDispenserHelper.write(out, s2.the_dispenser());
            }
        }
    }
}

