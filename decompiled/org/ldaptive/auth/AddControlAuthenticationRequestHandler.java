/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationRequestHandler;
import org.ldaptive.auth.User;
import org.ldaptive.control.RequestControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddControlAuthenticationRequestHandler
implements AuthenticationRequestHandler {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ControlFactory controlFactory;

    public AddControlAuthenticationRequestHandler(ControlFactory factory2) {
        this.controlFactory = factory2;
    }

    @Override
    public void handle(String dn, AuthenticationRequest request) throws LdapException {
        Object[] ctls = this.controlFactory.getControls(dn, request.getUser());
        this.logger.trace("{} produced controls {}", (Object)this.controlFactory, (Object)Arrays.toString(ctls));
        if (ctls != null && ctls.length > 0) {
            if (request.getControls() != null && request.getControls().length > 0) {
                request.setControls(LdapUtils.concatArrays(request.getControls(), new RequestControl[][]{ctls}));
            } else {
                request.setControls((RequestControl[])ctls);
            }
        }
    }

    public static interface ControlFactory {
        public RequestControl[] getControls(String var1, User var2);
    }
}

