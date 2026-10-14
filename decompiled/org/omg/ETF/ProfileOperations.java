/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Profile;
import org.omg.GIOP.Version;
import org.omg.IOP.TaggedComponentSeqHolder;
import org.omg.IOP.TaggedProfileHolder;

public interface ProfileOperations {
    public void marshal(TaggedProfileHolder var1, TaggedComponentSeqHolder var2);

    public int hash();

    public Profile copy();

    public boolean is_match(Profile var1);

    public Version version();

    public void set_object_key(byte[] var1);

    public byte[] get_object_key();

    public int tag();
}

