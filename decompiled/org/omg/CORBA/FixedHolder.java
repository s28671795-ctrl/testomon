/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.math.BigDecimal;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public class FixedHolder
implements Streamable {
    public BigDecimal value;

    public FixedHolder() {
    }

    public FixedHolder(BigDecimal o) {
        this.value = o;
    }

    @Override
    public TypeCode _type() {
        int decimal;
        String s2 = this.value.toString();
        short digits = 0;
        short scale = 0;
        if (s2.startsWith("-")) {
            s2 = s2.substring(1);
        }
        if (!s2.startsWith("0.")) {
            for (int i = 0; i < s2.length() && s2.charAt(i) != '.'; ++i) {
                digits = (short)(digits + 1);
            }
        }
        if ((decimal = s2.indexOf(46)) != -1) {
            s2 = s2.substring(decimal + 1);
            for (int i = 0; i < s2.length(); ++i) {
                digits = (short)(digits + 1);
                scale = (short)(scale + 1);
            }
        }
        return ORB.init().create_fixed_tc(digits, scale);
    }

    @Override
    public void _read(InputStream in) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void _write(OutputStream out) {
        if (this.value == null) {
            throw new MARSHAL("value may not be null");
        }
        TypeCode typeCode = this._type();
        try {
            out.write_fixed(this.value, typeCode.fixed_digits(), typeCode.fixed_scale());
        }
        catch (BadKind e) {
            throw new RuntimeException("should never happen", e);
        }
    }
}

