/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.util.Hashtable;

public class NamingContextIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("bind_new_context", "(in:n org.omg.CosNaming.Name)");
        irInfo.put("new_context", "()");
        irInfo.put("list", "(in:how_many ,out:bl org.omg.CosNaming.BindingList,out:bi )");
        irInfo.put("bind", "(in:n org.omg.CosNaming.Name,in:obj )");
        irInfo.put("rebind_context", "(in:n org.omg.CosNaming.Name,in:nc )");
        irInfo.put("destroy", "()");
        irInfo.put("rebind", "(in:n org.omg.CosNaming.Name,in:obj )");
        irInfo.put("bind_context", "(in:n org.omg.CosNaming.Name,in:nc )");
        irInfo.put("resolve", "(in:n org.omg.CosNaming.Name)");
        irInfo.put("unbind", "(in:n org.omg.CosNaming.Name)");
    }
}

