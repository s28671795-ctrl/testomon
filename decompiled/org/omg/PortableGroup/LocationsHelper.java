/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.io.IOException;
import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NameHelper;
import org.omg.PortableGroup.LocationHelper;

public abstract class LocationsHelper {
    private static volatile TypeCode _type;

    public static void insert(Any any, NameComponent[][] s2) {
        any.type(LocationsHelper.type());
        LocationsHelper.write(any.create_output_stream(), s2);
    }

    public static NameComponent[][] extract(Any any) {
        if (any.type().kind() == TCKind.tk_null) {
            throw new BAD_OPERATION("Can't extract from Any with null type.");
        }
        return LocationsHelper.read(any.create_input_stream());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static TypeCode type() {
        if (_type != null) return _type;
        Class<LocationsHelper> clazz = LocationsHelper.class;
        synchronized (LocationsHelper.class) {
            if (_type != null) return _type;
            _type = ORB.init().create_alias_tc(LocationsHelper.id(), "Locations", ORB.init().create_sequence_tc(0, LocationHelper.type()));
            // ** MonitorExit[var0] (shouldn't be in output)
            return _type;
        }
    }

    public static String id() {
        return "IDL:omg.org/PortableGroup/Locations:1.0";
    }

    public static NameComponent[][] read(InputStream _in) {
        int _l_result2 = _in.read_long();
        try {
            int x = _in.available();
            if (x > 0 && _l_result2 > x) {
                throw new MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result2);
            }
        }
        catch (IOException e) {
            // empty catch block
        }
        NameComponent[][] _result = new NameComponent[_l_result2][];
        for (int i = 0; i < _result.length; ++i) {
            _result[i] = NameHelper.read(_in);
        }
        return _result;
    }

    public static void write(OutputStream _out, NameComponent[][] _s) {
        _out.write_long(_s.length);
        for (int i = 0; i < _s.length; ++i) {
            NameHelper.write(_out, _s[i]);
        }
    }
}

