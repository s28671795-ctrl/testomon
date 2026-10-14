/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class CurrentIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("audit_decision", "attribute;org.omg.SecurityLevel2.AuditDecision");
        irInfo.put("create_mechanism_policy", "(in:mechanisms org.omg.Security.MechanismTypeList)");
        irInfo.put("get_security_mechanisms", "org.omg.Security.SecurityMechanismDataList(in:obj_ref )");
        irInfo.put("get_credentials", "org.omg.SecurityLevel2.CredentialsList(in:cred_type )");
        irInfo.put("create_qop_policy", "(in:qop_ )");
        irInfo.put("get_policy", "(in:policy_type org.omg.CORBA.PolicyType)");
        irInfo.put("own_credentials", "attribute;org.omg.SecurityLevel2.CredentialsList");
        irInfo.put("set_credentials", "(in:cred_type ,in:creds org.omg.SecurityLevel2.CredentialsList,in:del )");
        irInfo.put("received_credentials", "attribute;org.omg.SecurityLevel2.ReceivedCredentials");
        irInfo.put("create_invoc_creds_policy", "(in:creds org.omg.SecurityLevel2.CredentialsList)");
        irInfo.put("supported_mechanisms", "attribute;org.omg.Security.MechandOptionsList");
        irInfo.put("required_rights_object", "attribute;org.omg.SecurityLevel2.RequiredRights");
        irInfo.put("principal_authenticator", "attribute;org.omg.SecurityLevel2.PrincipalAuthenticator");
        irInfo.put("access_decision", "attribute;org.omg.SecurityLevel2.AccessDecision");
        irInfo.put("remove_own_credentials", "(in:credentials_ )");
    }
}

