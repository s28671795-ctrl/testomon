/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;

public final class OpaqueBuffer
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public byte[] buffer;
    public int startpos;
    public int endpos;

    public OpaqueBuffer() {
    }

    public OpaqueBuffer(byte[] buffer, int startpos, int endpos) {
        this.buffer = buffer;
        this.startpos = startpos;
        this.endpos = endpos;
    }
}

