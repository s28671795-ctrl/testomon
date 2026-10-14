/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class ReceivedCredentialsIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("delegation_state", "attribute;org.omg.Security.DelegationState");
        irInfo.put("accepting_credentials", "attribute;org.omg.SecurityLevel2.Credentials");
        irInfo.put("association_options_used", "attribute;org.omg.Security.AssociationOptions");
        irInfo.put("delegation_mode", "attribute;org.omg.Security.DelegationMode");
    }
}

