/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import java.util.Hashtable;

public class POAIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("the_activator", "attribute-w;org.omg.PortableServer.AdapterActivator");
        irInfo.put("create_id_assignment_policy", "(in:value )");
        irInfo.put("create_lifespan_policy", "(in:value )");
        irInfo.put("reference_to_id", "org.omg.PortableServer.ObjectId(in:reference )");
        irInfo.put("create_thread_policy", "(in:value )");
        irInfo.put("create_request_processing_policy", "(in:value )");
        irInfo.put("the_parent", "attribute;org.omg.PortableServer.POA");
        irInfo.put("find_POA", "(in:adapter_name ,in:activate_it )");
        irInfo.put("activate_object", "org.omg.PortableServer.ObjectId(in:p_servant )");
        irInfo.put("create_reference", "(in:intf org.omg.CORBA.RepositoryId)");
        irInfo.put("get_servant_manager", "()");
        irInfo.put("get_servant", "()");
        irInfo.put("create_servant_retention_policy", "(in:value )");
        irInfo.put("activate_object_with_id", "(in:id org.omg.PortableServer.ObjectId,in:p_servant )");
        irInfo.put("id_to_servant", "(in:oid org.omg.PortableServer.ObjectId)");
        irInfo.put("create_implicit_activation_policy", "(in:value )");
        irInfo.put("create_id_uniqueness_policy", "(in:value )");
        irInfo.put("the_POAManagerFactory", "attribute;org.omg.PortableServer.POAManagerFactory");
        irInfo.put("id_to_reference", "(in:oid org.omg.PortableServer.ObjectId)");
        irInfo.put("servant_to_reference", "(in:p_servant )");
        irInfo.put("set_servant_manager", "(in:imgr )");
        irInfo.put("set_servant", "(in:p_servant )");
        irInfo.put("id", "attribute;org.omg.CORBA.OctetSeq");
        irInfo.put("the_children", "attribute;org.omg.PortableServer.POAList");
        irInfo.put("the_name", "attribute;java.lang.String");
        irInfo.put("the_POAManager", "attribute;org.omg.PortableServer.POAManager");
        irInfo.put("create_reference_with_id", "(in:oid org.omg.PortableServer.ObjectId,in:intf org.omg.CORBA.RepositoryId)");
        irInfo.put("deactivate_object", "(in:oid org.omg.PortableServer.ObjectId)");
        irInfo.put("reference_to_servant", "(in:reference )");
        irInfo.put("servant_to_id", "org.omg.PortableServer.ObjectId(in:p_servant )");
        irInfo.put("destroy", "(in:etherealize_objects ,in:wait_for_completion )");
        irInfo.put("create_POA", "(in:adapter_name ,in:a_POAManager ,in:policies org.omg.CORBA.PolicyList)");
    }
}

