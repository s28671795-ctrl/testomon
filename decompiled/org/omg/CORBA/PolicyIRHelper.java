/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class PolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("destroy", "()");
        irInfo.put("policy_type", "attribute;org.omg.CORBA.PolicyType");
        irInfo.put("copy", "()");
    }
}

