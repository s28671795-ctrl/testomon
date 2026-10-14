/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.ior.iiop;

import org.jboss.com.sun.corba.se.spi.ior.TaggedComponentBase;
import org.omg.CORBA_2_3.portable.OutputStream;

public class JavaSerializationComponent
extends TaggedComponentBase {
    private byte version;
    private static JavaSerializationComponent singleton;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JavaSerializationComponent singleton() {
        if (singleton != null) return singleton;
        Class<JavaSerializationComponent> clazz = JavaSerializationComponent.class;
        synchronized (JavaSerializationComponent.class) {
            singleton = new JavaSerializationComponent(1);
            // ** MonitorExit[var0] (shouldn't be in output)
            return singleton;
        }
    }

    public JavaSerializationComponent(byte version) {
        this.version = version;
    }

    public byte javaSerializationVersion() {
        return this.version;
    }

    @Override
    public void writeContents(OutputStream os) {
        os.write_octet(this.version);
    }

    @Override
    public int getId() {
        return 1398099458;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof JavaSerializationComponent)) {
            return false;
        }
        JavaSerializationComponent other = (JavaSerializationComponent)obj;
        return this.version == other.version;
    }

    public int hashCode() {
        return this.version;
    }
}

