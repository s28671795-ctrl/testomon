/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.impl.orbutil.ORBUtility;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.jboss.com.sun.corba.se.spi.ior.iiop.MaxStreamFormatVersionComponent;
import org.omg.CORBA_2_3.portable.OutputStream;

public class MaxStreamFormatVersionComponentImpl
extends TaggedComponentBase
implements MaxStreamFormatVersionComponent {
    private byte version;
    public static final MaxStreamFormatVersionComponentImpl singleton = new MaxStreamFormatVersionComponentImpl();

    public boolean equals(Object obj) {
        if (!(obj instanceof MaxStreamFormatVersionComponentImpl)) {
            return false;
        }
        MaxStreamFormatVersionComponentImpl other = (MaxStreamFormatVersionComponentImpl)obj;
        return this.version == other.version;
    }

    public int hashCode() {
        return this.version;
    }

    public String toString() {
        return "MaxStreamFormatVersionComponentImpl[version=" + this.version + "]";
    }

    public MaxStreamFormatVersionComponentImpl() {
        this.version = ORBUtility.getMaxStreamFormatVersion();
    }

    public MaxStreamFormatVersionComponentImpl(byte streamFormatVersion) {
        this.version = streamFormatVersion;
    }

    @Override
    public byte getMaxStreamFormatVersion() {
        return this.version;
    }

    @Override
    public void writeContents(OutputStream os) {
        os.write_octet(this.version);
    }

    @Override
    public int getId() {
        return 38;
    }
}

