/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import org.omg.ETF.Connection;
import org.omg.ETF.Listener;
import org.omg.ETF.Profile;
import org.omg.IOP.TaggedComponentSeqHolder;
import org.omg.IOP.TaggedProfileHolder;
import org.omg.RTCORBA.ProtocolProperties;

public interface FactoriesOperations {
    public Connection create_connection(ProtocolProperties var1);

    public Listener create_listener(ProtocolProperties var1, int var2, short var3);

    public Profile demarshal_profile(TaggedProfileHolder var1, TaggedComponentSeqHolder var2);

    public Profile decode_corbaloc(String var1);

    public int profile_tag();
}

