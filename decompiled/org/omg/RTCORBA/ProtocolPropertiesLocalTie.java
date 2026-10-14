/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import org.omg.RTCORBA.ProtocolPropertiesOperations;
import org.omg.RTCORBA._ProtocolPropertiesLocalBase;

public class ProtocolPropertiesLocalTie
extends _ProtocolPropertiesLocalBase {
    private static final long serialVersionUID = 1L;
    private ProtocolPropertiesOperations _delegate;

    public ProtocolPropertiesLocalTie(ProtocolPropertiesOperations delegate) {
        this._delegate = delegate;
    }

    public ProtocolPropertiesOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ProtocolPropertiesOperations delegate) {
        this._delegate = delegate;
    }
}

