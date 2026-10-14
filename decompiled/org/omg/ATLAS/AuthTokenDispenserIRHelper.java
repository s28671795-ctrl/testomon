/*
 * Decompiled with CFR 0.152.
 */
package org.omg.ATLAS;

import java.util.Hashtable;

public class AuthTokenDispenserIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("get_my_authorization_token", "()");
        irInfo.put("translate_authorization_token", "(in:the_subject ,in:the_token org.omg.CSI.AuthorizationToken)");
    }
}

