/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.Security.DelegationMode;
import org.omg.Security.DelegationState;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.CredentialsOperations;

public interface ReceivedCredentialsOperations
extends CredentialsOperations {
    public Credentials accepting_credentials();

    public short association_options_used();

    public DelegationState delegation_state();

    public DelegationMode delegation_mode();
}

