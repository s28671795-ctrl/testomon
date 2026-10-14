/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class PrincipalAuthenticatorIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("authenticate", "(in:method org.omg.Security.AuthenticationMethod,in:mechanism org.omg.Security.MechanismType,in:security_name org.omg.Security.SecurityName,in:auth_data org.omg.Security.Opaque,in:privileges org.omg.Security.AttributeList,out:creds ,out:continuation_data org.omg.Security.Opaque,out:auth_specific_data org.omg.Security.Opaque)");
        irInfo.put("get_supported_authen_methods", "org.omg.Security.AuthenticationMethodList(in:mechanism org.omg.Security.MechanismType)");
        irInfo.put("continue_authentication", "(in:response_data org.omg.Security.Opaque,in:creds ,out:continuation_data org.omg.Security.Opaque,out:auth_specific_data org.omg.Security.Opaque)");
    }
}

