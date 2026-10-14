/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTCORBA;

import java.util.Hashtable;

public class MutexIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("lock", "()");
        irInfo.put("try_lock", "(in:max_wait org.omg.TimeBase.TimeT)");
        irInfo.put("unlock", "()");
    }
}

