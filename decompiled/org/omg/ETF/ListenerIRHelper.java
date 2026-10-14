/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class ListenerIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("destroy", "()");
        irInfo.put("listen", "()");
        irInfo.put("accept", "()");
        irInfo.put("set_handle", "(in:up )");
        irInfo.put("completed_data", "(in:conn )");
        irInfo.put("endpoint", "attribute;org.omg.ETF.Profile");
    }
}

