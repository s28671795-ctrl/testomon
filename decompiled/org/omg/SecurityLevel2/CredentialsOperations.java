/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.Security.AttributeListHolder;
import org.omg.Security.AttributeType;
import org.omg.Security.AuthenticationStatus;
import org.omg.Security.CommunicationDirection;
import org.omg.Security.InvocationCredentialsType;
import org.omg.Security.SecAttribute;
import org.omg.Security.SecurityFeature;
import org.omg.SecurityLevel2.Credentials;
import org.omg.TimeBase.UtcTHolder;

public interface CredentialsOperations {
    public Credentials copy();

    public void destroy();

    public InvocationCredentialsType credentials_type();

    public AuthenticationStatus authentication_state();

    public String mechanism();

    public short accepting_options_supported();

    public void accepting_options_supported(short var1);

    public short accepting_options_required();

    public void accepting_options_required(short var1);

    public short invocation_options_supported();

    public void invocation_options_supported(short var1);

    public short invocation_options_required();

    public void invocation_options_required(short var1);

    public boolean get_security_feature(CommunicationDirection var1, SecurityFeature var2);

    public boolean set_privileges(boolean var1, SecAttribute[] var2, AttributeListHolder var3);

    public SecAttribute[] get_attributes(AttributeType[] var1);

    public boolean is_valid(UtcTHolder var1);

    public boolean refresh(byte[] var1);
}

