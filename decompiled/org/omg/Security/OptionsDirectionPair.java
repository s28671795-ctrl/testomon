/*
 * Decompiled with CFR 0.152.
 */
package org.omg.Security;

import org.omg.CORBA.portable.IDLEntity;
import org.omg.Security.CommunicationDirection;

public final class OptionsDirectionPair
implements IDLEntity {
    private static final long serialVersionUID = 1L;
    public short options;
    public CommunicationDirection direction;

    public OptionsDirectionPair() {
    }

    public OptionsDirectionPair(short options, CommunicationDirection direction) {
        this.options = options;
        this.direction = direction;
    }
}

