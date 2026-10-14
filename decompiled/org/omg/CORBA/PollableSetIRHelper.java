/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class PollableSetIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("remove", "(in:potential )");
        irInfo.put("number_left", "()");
        irInfo.put("get_ready_pollable", "(in:timeout )");
        irInfo.put("create_dii_pollable", "()");
        irInfo.put("add_pollable", "(in:potential )");
    }
}

