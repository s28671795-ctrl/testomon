/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.Security.CredentialType;
import org.omg.Security.MechandOptions;
import org.omg.Security.QOP;
import org.omg.Security.SecurityMechanismData;
import org.omg.SecurityLevel2.AccessDecision;
import org.omg.SecurityLevel2.AuditDecision;
import org.omg.SecurityLevel2.Credentials;
import org.omg.SecurityLevel2.DelegationMode;
import org.omg.SecurityLevel2.InvocationCredentialsPolicy;
import org.omg.SecurityLevel2.MechanismPolicy;
import org.omg.SecurityLevel2.PrincipalAuthenticator;
import org.omg.SecurityLevel2.QOPPolicy;
import org.omg.SecurityLevel2.ReceivedCredentials;
import org.omg.SecurityLevel2.RequiredRights;

public interface CurrentOperations
extends org.omg.SecurityLevel1.CurrentOperations {
    public ReceivedCredentials received_credentials();

    public void set_credentials(CredentialType var1, Credentials[] var2, DelegationMode var3);

    public Credentials[] get_credentials(CredentialType var1);

    public Policy get_policy(int var1);

    public void remove_own_credentials(Credentials var1);

    public MechandOptions[] supported_mechanisms();

    public Credentials[] own_credentials();

    public RequiredRights required_rights_object();

    public PrincipalAuthenticator principal_authenticator();

    public AccessDecision access_decision();

    public AuditDecision audit_decision();

    public SecurityMechanismData[] get_security_mechanisms(Object var1);

    public QOPPolicy create_qop_policy(QOP var1);

    public MechanismPolicy create_mechanism_policy(String[] var1);

    public InvocationCredentialsPolicy create_invoc_creds_policy(Credentials[] var1);
}

