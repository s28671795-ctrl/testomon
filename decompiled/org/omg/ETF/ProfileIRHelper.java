/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class ProfileIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("tag", "attribute;int");
        irInfo.put("version", "attribute;org.omg.GIOP.Version");
        irInfo.put("marshal", "(inout:tagged_profile ,inout:components org.omg.IOP.TaggedComponentSeq)");
        irInfo.put("set_object_key", "(in:key org.omg.ETF.ObjectKey)");
        irInfo.put("hash", "()");
        irInfo.put("get_object_key", "org.omg.ETF.ObjectKey()");
        irInfo.put("is_match", "(in:prof )");
        irInfo.put("copy", "()");
    }
}

