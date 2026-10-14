/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.Security.SecurityFeature;
import org.omg.Security.SecurityFeatureHelper;

public final class SecurityFeatureHolder
implements Streamable {
    public SecurityFeature value;

    public SecurityFeatureHolder() {
    }

    public SecurityFeatureHolder(SecurityFeature initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return SecurityFeatureHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = SecurityFeatureHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        SecurityFeatureHelper.write(out, this.value);
    }
}

