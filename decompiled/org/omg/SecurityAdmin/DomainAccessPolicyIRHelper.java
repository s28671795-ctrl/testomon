/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import java.util.Hashtable;

public class DomainAccessPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("replace_rights", "(in:priv_attr ,in:del_state ,in:rights org.omg.Security.RightsList)");
        irInfo.put("get_all_rights", "org.omg.Security.RightsList(in:priv_attr ,in:del_state )");
        irInfo.put("get_rights", "org.omg.Security.RightsList(in:priv_attr ,in:del_state ,in:rights_family )");
        irInfo.put("revoke_rights", "(in:priv_attr ,in:del_state ,in:rights org.omg.Security.RightsList)");
        irInfo.put("grant_rights", "(in:priv_attr ,in:del_state ,in:rights org.omg.Security.RightsList)");
    }
}

