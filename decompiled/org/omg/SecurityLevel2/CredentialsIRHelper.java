/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class CredentialsIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_attributes", "org.omg.Security.AttributeList(in:attributes org.omg.Security.AttributeTypeList)");
        irInfo.put("copy", "()");
        irInfo.put("invocation_options_required", "attribute-w;org.omg.Security.AssociationOptions");
        irInfo.put("credentials_type", "attribute;org.omg.Security.InvocationCredentialsType");
        irInfo.put("authentication_state", "attribute;org.omg.Security.AuthenticationStatus");
        irInfo.put("invocation_options_supported", "attribute-w;org.omg.Security.AssociationOptions");
        irInfo.put("accepting_options_required", "attribute-w;org.omg.Security.AssociationOptions");
        irInfo.put("get_security_feature", "(in:direction ,in:feature )");
        irInfo.put("mechanism", "attribute;org.omg.Security.MechanismType");
        irInfo.put("set_privileges", "(in:force_commit ,in:requested_privileges org.omg.Security.AttributeList,out:actual_privileges org.omg.Security.AttributeList)");
        irInfo.put("refresh", "(in:refresh_data org.omg.Security.Opaque)");
        irInfo.put("is_valid", "(out:expiry_time org.omg.Security.UtcT)");
        irInfo.put("destroy", "()");
        irInfo.put("accepting_options_supported", "attribute-w;org.omg.Security.AssociationOptions");
    }
}

