/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class HandleIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("add_input", "(in:conn )");
        irInfo.put("closed_by_peer", "(in:conn )");
        irInfo.put("signal_data_available", "(in:conn )");
    }
}

