/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.util.Hashtable;

public class BindingIteratorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("next_one", "(out:b )");
        irInfo.put("destroy", "()");
        irInfo.put("next_n", "(in:how_many ,out:bl org.omg.CosNaming.BindingList)");
    }
}

