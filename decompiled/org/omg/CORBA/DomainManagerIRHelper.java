/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.util.Hashtable;

public class DomainManagerIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_domain_policy", "(in:policy_type org.omg.CORBA.PolicyType)");
    }
}

