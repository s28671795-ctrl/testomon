/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class ContainerIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("create_interface", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:base_interfaces org.omg.CORBA.InterfaceDefSeq)");
        irInfo.put("create_value_box", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:original_type_def )");
        irInfo.put("create_value", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:is_custom ,in:is_abstract ,in:base_value ,in:is_truncatable ,in:abstract_base_values org.omg.CORBA.ValueDefSeq,in:supported_interfaces org.omg.CORBA.InterfaceDefSeq,in:initializers org.omg.CORBA.InitializerSeq)");
        irInfo.put("create_enum", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:members org.omg.CORBA.EnumMemberSeq)");
        irInfo.put("create_local_interface", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:base_interfaces org.omg.CORBA.InterfaceDefSeq)");
        irInfo.put("lookup_name", "org.omg.CORBA.ContainedSeq(in:search_name org.omg.CORBA.Identifier,in:levels_to_search ,in:limit_type ,in:exclude_inherited )");
        irInfo.put("create_exception", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:members org.omg.CORBA.StructMemberSeq)");
        irInfo.put("create_union", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:discriminator_type ,in:members org.omg.CORBA.UnionMemberSeq)");
        irInfo.put("create_struct", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:members org.omg.CORBA.StructMemberSeq)");
        irInfo.put("create_native", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec)");
        irInfo.put("create_abstract_interface", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:base_interfaces org.omg.CORBA.AbstractInterfaceDefSeq)");
        irInfo.put("contents", "org.omg.CORBA.ContainedSeq(in:limit_type ,in:exclude_inherited )");
        irInfo.put("create_ext_value", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:is_custom ,in:is_abstract ,in:base_value ,in:is_truncatable ,in:abstract_base_values org.omg.CORBA.ValueDefSeq,in:supported_interfaces org.omg.CORBA.InterfaceDefSeq,in:initializers org.omg.CORBA.ExtInitializerSeq)");
        irInfo.put("describe_contents", "org.omg.CORBA.ContainerPackage.DescriptionSeq(in:limit_type ,in:exclude_inherited ,in:max_returned_objs )");
        irInfo.put("lookup", "(in:search_name org.omg.CORBA.ScopedName)");
        irInfo.put("create_module", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec)");
        irInfo.put("create_alias", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:original_type )");
        irInfo.put("create_constant", "(in:id org.omg.CORBA.RepositoryId,in:name org.omg.CORBA.Identifier,in:version org.omg.CORBA.VersionSpec,in:type ,in:value )");
    }
}

