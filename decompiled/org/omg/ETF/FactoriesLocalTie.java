/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;
import org.omg.ETF.FactoriesOperations;
import org.omg.ETF.Listener;
import org.omg.ETF.Profile;
import org.omg.ETF._FactoriesLocalBase;
import org.omg.IOP.TaggedComponentSeqHolder;
import org.omg.IOP.TaggedProfileHolder;
import org.omg.RTCORBA.ProtocolProperties;

public class FactoriesLocalTie
extends _FactoriesLocalBase {
    private static final long serialVersionUID = 1L;
    private FactoriesOperations _delegate;

    public FactoriesLocalTie(FactoriesOperations delegate) {
        this._delegate = delegate;
    }

    public FactoriesOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(FactoriesOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public Profile demarshal_profile(TaggedProfileHolder tagged_profile, TaggedComponentSeqHolder components) {
        return this._delegate.demarshal_profile(tagged_profile, components);
    }

    @Override
    public int profile_tag() {
        return this._delegate.profile_tag();
    }

    @Override
    public Connection create_connection(ProtocolProperties props) {
        return this._delegate.create_connection(props);
    }

    @Override
    public Listener create_listener(ProtocolProperties props, int stacksize, short base_priority) {
        return this._delegate.create_listener(props, stacksize, base_priority);
    }

    @Override
    public Profile decode_corbaloc(String corbaloc) {
        return this._delegate.decode_corbaloc(corbaloc);
    }
}

