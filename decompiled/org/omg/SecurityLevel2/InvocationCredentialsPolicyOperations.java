/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.PolicyOperations;
import org.omg.SecurityLevel2.Credentials;

public interface InvocationCredentialsPolicyOperations
extends PolicyOperations {
    public Credentials[] creds();
}

