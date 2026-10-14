/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class AccessDecisionIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("access_allowed", "(in:cred_list org.omg.SecurityLevel2.CredentialsList,in:target ,in:operation_name org.omg.CORBA.Identifier,in:target_interface_name org.omg.CORBA.Identifier)");
    }
}

