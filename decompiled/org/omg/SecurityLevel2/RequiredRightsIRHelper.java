/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class RequiredRightsIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_required_rights", "(in:obj ,in:operation_name org.omg.CORBA.Identifier,in:interface_name org.omg.CORBA.RepositoryId,out:rights org.omg.Security.RightsList,out:rights_combinator )");
        irInfo.put("set_required_rights", "(in:operation_name org.omg.CORBA.Identifier,in:interface_name org.omg.CORBA.RepositoryId,in:rights org.omg.Security.RightsList,in:rights_combinator )");
    }
}

