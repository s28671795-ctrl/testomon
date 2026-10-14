/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CosNaming;

import java.util.Hashtable;

public class NamingContextExtIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("to_name", "org.omg.CosNaming.Name(in:sn org.omg.CosNaming.NamingContextExtPackage.StringName)");
        irInfo.put("resolve_str", "(in:n org.omg.CosNaming.NamingContextExtPackage.StringName)");
        irInfo.put("to_url", "org.omg.CosNaming.NamingContextExtPackage.URLString(in:addr org.omg.CosNaming.NamingContextExtPackage.Address,in:sn org.omg.CosNaming.NamingContextExtPackage.StringName)");
        irInfo.put("to_string", "org.omg.CosNaming.NamingContextExtPackage.StringName(in:n org.omg.CosNaming.Name)");
    }
}

