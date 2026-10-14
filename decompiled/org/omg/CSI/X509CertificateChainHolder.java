/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CSI;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;
import org.omg.CSI.X509CertificateChainHelper;

public final class X509CertificateChainHolder
implements Streamable {
    public byte[] value;

    public X509CertificateChainHolder() {
    }

    public X509CertificateChainHolder(byte[] initial) {
        this.value = initial;
    }

    @Override
    public TypeCode _type() {
        return X509CertificateChainHelper.type();
    }

    @Override
    public void _read(InputStream in) {
        this.value = X509CertificateChainHelper.read(in);
    }

    @Override
    public void _write(OutputStream out) {
        X509CertificateChainHelper.write(out, this.value);
    }
}

