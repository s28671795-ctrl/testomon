/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class PolicyManagerIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_policy_overrides", "org.omg.CORBA.PolicyList(in:ts org.omg.CORBA.PolicyTypeSeq)");
        irInfo.put("set_policy_overrides", "(in:policies org.omg.CORBA.PolicyList,in:set_add )");
    }
}

