/*
 * Decompiled with CFR 0.152.
 */
package org.omg.IIOP;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.IIOP.ListenPoint;

public final class BiDirIIOPServiceContext
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public ListenPoint[] listen_points;

    public BiDirIIOPServiceContext() {
    }

    public BiDirIIOPServiceContext(ListenPoint[] listen_points) {
        this.listen_points = listen_points;
    }
}

