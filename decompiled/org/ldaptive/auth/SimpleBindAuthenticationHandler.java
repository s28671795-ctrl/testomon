/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import java.util.Arrays;
import org.ldaptive.BindResponse;
import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.LdapException;
import org.ldaptive.SimpleBindRequest;
import org.ldaptive.auth.AbstractAuthenticationHandler;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.auth.AuthenticationResultCode;

public class SimpleBindAuthenticationHandler
extends AbstractAuthenticationHandler
implements ConnectionFactoryManager {
    public SimpleBindAuthenticationHandler() {
    }

    public SimpleBindAuthenticationHandler(ConnectionFactory cf) {
        this.setConnectionFactory(cf);
    }

    @Override
    protected AuthenticationHandlerResponse authenticateInternal(Connection c, AuthenticationCriteria criteria) throws LdapException {
        SimpleBindRequest request = new SimpleBindRequest(criteria.getDn(), criteria.getCredential().getString());
        request.setControls(this.processRequestControls(criteria));
        BindResponse bindResult = c.operation(request).execute();
        return new AuthenticationHandlerResponse(bindResult, bindResult.isSuccess() ? AuthenticationResultCode.AUTHENTICATION_HANDLER_SUCCESS : AuthenticationResultCode.AUTHENTICATION_HANDLER_FAILURE, c);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "factory=" + this.getConnectionFactory() + ", " + "controls=" + Arrays.toString(this.getAuthenticationControls()) + "]";
    }
}

