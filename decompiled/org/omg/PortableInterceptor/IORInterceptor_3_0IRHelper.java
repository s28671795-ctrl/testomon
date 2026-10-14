/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class IORInterceptor_3_0IRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("adapter_state_changed", "(in:templates org.omg.PortableInterceptor.ObjectReferenceTemplateSeq,in:state org.omg.PortableInterceptor.AdapterState)");
        irInfo.put("components_established", "(in:info )");
        irInfo.put("adapter_manager_state_changed", "(in:id org.omg.PortableInterceptor.AdapterManagerId,in:state org.omg.PortableInterceptor.AdapterState)");
    }
}

