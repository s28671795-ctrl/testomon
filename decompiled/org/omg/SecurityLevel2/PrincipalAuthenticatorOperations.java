/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.Security.AuthenticationStatus;
import org.omg.Security.OpaqueHolder;
import org.omg.Security.SecAttribute;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.CredentialsHolder;

public interface PrincipalAuthenticatorOperations {
    public int[] get_supported_authen_methods(String var1);

    public AuthenticationStatus authenticate(int var1, String var2, String var3, byte[] var4, SecAttribute[] var5, CredentialsHolder var6, OpaqueHolder var7, OpaqueHolder var8);

    public AuthenticationStatus continue_authentication(byte[] var1, Credentials var2, OpaqueHolder var3, OpaqueHolder var4);
}

