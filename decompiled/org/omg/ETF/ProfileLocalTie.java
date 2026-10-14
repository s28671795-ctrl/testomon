/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Profile;
import org.omg.ETF.ProfileOperations;
import org.omg.ETF._ProfileLocalBase;
import org.omg.GIOP.Version;
import org.omg.IOP.TaggedComponentSeqHolder;
import org.omg.IOP.TaggedProfileHolder;

public class ProfileLocalTie
extends _ProfileLocalBase {
    private static final long serialVersionUID = 1L;
    private ProfileOperations _delegate;

    public ProfileLocalTie(ProfileOperations delegate) {
        this._delegate = delegate;
    }

    public ProfileOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ProfileOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public void marshal(TaggedProfileHolder tagged_profile, TaggedComponentSeqHolder components) {
        this._delegate.marshal(tagged_profile, components);
    }

    @Override
    public byte[] get_object_key() {
        return this._delegate.get_object_key();
    }

    @Override
    public void set_object_key(byte[] key) {
        this._delegate.set_object_key(key);
    }

    @Override
    public Profile copy() {
        return this._delegate.copy();
    }

    @Override
    public Version version() {
        return this._delegate.version();
    }

    @Override
    public boolean is_match(Profile prof) {
        return this._delegate.is_match(prof);
    }

    @Override
    public int tag() {
        return this._delegate.tag();
    }

    @Override
    public int hash() {
        return this._delegate.hash();
    }
}

