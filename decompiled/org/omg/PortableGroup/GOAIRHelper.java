/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.util.Hashtable;

public class GOAIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("reference_to_ids", "org.omg.PortableGroup.IDs(in:the_ref )");
        irInfo.put("disassociate_reference_with_id", "(in:ref ,in:oid org.omg.PortableServer.ObjectId)");
        irInfo.put("associate_reference_with_id", "(in:ref ,in:oid org.omg.PortableServer.ObjectId)");
        irInfo.put("create_id_for_reference", "org.omg.PortableServer.ObjectId(in:the_ref )");
    }
}

