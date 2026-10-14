/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class FactoriesIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_listener", "(in:props ,in:stacksize ,in:base_priority org.omg.RTCORBA.Priority)");
        irInfo.put("profile_tag", "attribute;org.omg.IOP.ProfileId");
        irInfo.put("create_connection", "(in:props )");
        irInfo.put("decode_corbaloc", "(in:corbaloc )");
        irInfo.put("demarshal_profile", "(inout:tagged_profile ,out:components org.omg.IOP.TaggedComponentSeq)");
    }
}

