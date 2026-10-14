/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.auth;

import org.ldaptive.Connection;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.ConnectionFactoryManager;
import org.ldaptive.LdapException;
import org.ldaptive.LdapUtils;
import org.ldaptive.auth.AuthenticationCriteria;
import org.ldaptive.auth.AuthenticationHandler;
import org.ldaptive.auth.AuthenticationHandlerResponse;
import org.ldaptive.control.RequestControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAuthenticationHandler
implements AuthenticationHandler,
ConnectionFactoryManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private ConnectionFactory factory;
    private RequestControl[] authenticationControls;

    @Override
    public ConnectionFactory getConnectionFactory() {
        return this.factory;
    }

    @Override
    public void setConnectionFactory(ConnectionFactory cf) {
        this.factory = cf;
    }

    public RequestControl[] getAuthenticationControls() {
        return this.authenticationControls;
    }

    public void setAuthenticationControls(RequestControl ... cntrls) {
        this.authenticationControls = cntrls;
    }

    @Override
    public AuthenticationHandlerResponse authenticate(AuthenticationCriteria ac) throws LdapException {
        AuthenticationHandlerResponse response;
        this.logger.trace("authenticate criteria={}", (Object)ac);
        Connection conn = this.factory.getConnection();
        boolean closeConn = false;
        try {
            conn.open();
            response = this.authenticateInternal(conn, ac);
        }
        catch (Exception e) {
            closeConn = true;
            throw e;
        }
        finally {
            if (closeConn) {
                conn.close();
            }
        }
        this.logger.debug("Authenticate response={} for criteria={}", (Object)response, (Object)ac);
        return response;
    }

    protected abstract AuthenticationHandlerResponse authenticateInternal(Connection var1, AuthenticationCriteria var2) throws LdapException;

    protected RequestControl[] processRequestControls(AuthenticationCriteria criteria) {
        RequestControl[] ctls = criteria.getAuthenticationRequest().getControls() != null ? (this.getAuthenticationControls() != null ? LdapUtils.concatArrays(criteria.getAuthenticationRequest().getControls(), new RequestControl[][]{this.getAuthenticationControls()}) : criteria.getAuthenticationRequest().getControls()) : this.getAuthenticationControls();
        return ctls;
    }
}

