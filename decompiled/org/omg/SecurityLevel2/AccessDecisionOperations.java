/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Object;
import org.omg.SecurityLevel2.Credentials;

public interface AccessDecisionOperations {
    public boolean access_allowed(Credentials[] var1, Object var2, String var3, String var4);
}

