/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth.ext;

import org.ldaptive.auth.AddControlAuthenticationRequestHandler;
import org.ldaptive.control.PasswordPolicyControl;
import org.ldaptive.control.RequestControl;

public class PasswordPolicyAuthenticationRequestHandler
extends AddControlAuthenticationRequestHandler {
    public PasswordPolicyAuthenticationRequestHandler() {
        super((dn, user) -> new RequestControl[]{new PasswordPolicyControl()});
    }
}

