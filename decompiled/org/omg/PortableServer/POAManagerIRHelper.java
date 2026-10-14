/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class POAManagerIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_state", "()");
        irInfo.put("discard_requests", "(in:wait_for_completion )");
        irInfo.put("hold_requests", "(in:wait_for_completion )");
        irInfo.put("activate", "()");
        irInfo.put("get_id", "()");
        irInfo.put("deactivate", "(in:etherealize_objects ,in:wait_for_completion )");
    }
}

