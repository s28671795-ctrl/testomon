/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import java.util.Hashtable;

public class GenericFactoryIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("delete_object", "(in:factory_creation_id org.omg.PortableGroup.GenericFactoryPackage.FactoryCreationId)");
        irInfo.put("create_object", "(in:type_id org.omg.PortableGroup.TypeId,in:the_criteria org.omg.PortableGroup.Criteria,out:factory_creation_id org.omg.PortableGroup.GenericFactoryPackage.FactoryCreationId)");
    }
}

