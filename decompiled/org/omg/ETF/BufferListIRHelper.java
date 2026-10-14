/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ETF;

import java.util.Hashtable;

public class BufferListIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("add_buffer", "(in:size ,inout:buf org.omg.ETF.Buffer)");
        irInfo.put("release_buffers", "()");
        irInfo.put("get_buffer", "(in:index ,inout:buf org.omg.ETF.Buffer)");
        irInfo.put("num_buffers", "attribute;int");
    }
}

