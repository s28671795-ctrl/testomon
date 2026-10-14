/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import java.util.HashMap;
import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.ldaptive.BindResponse;
import org.ldaptive.sasl.GssApiBindRequest;
import org.ldaptive.sasl.SaslClient;
import org.ldaptive.transport.TransportConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GssApiSaslClient
implements SaslClient<GssApiBindRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GssApiSaslClient.class);

    @Override
    public BindResponse bind(TransportConnection conn, GssApiBindRequest request) throws LoginException {
        Subject subject;
        if (request.getJaasName() != null) {
            if (request.getJaasRefreshConfig()) {
                try {
                    Configuration.getConfiguration().refresh();
                }
                catch (Exception e) {
                    LOGGER.warn("Could not refresh JAAS configuration", e);
                }
            }
            LOGGER.debug("Invoking JAAS configuration {} for request {}", (Object)request.getJaasName(), (Object)request);
            LoginContext context = new LoginContext(request.getJaasName(), request);
            context.login();
            subject = context.getSubject();
        } else {
            LoginModule loginModule;
            try {
                loginModule = (LoginModule)Class.forName(request.getJaasLoginModule()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                LOGGER.error("Error creating new instance of JAAS module for GSSAPI", e);
                throw new LoginException("Could not instantiate JAAS module '" + request.getJaasLoginModule() + "' for GSSAPI");
            }
            LOGGER.debug("Invoking module {} for request {}", (Object)loginModule, (Object)request);
            subject = new Subject();
            HashMap state = new HashMap();
            loginModule.initialize(subject, request, state, request.getJaasOptions());
            if (loginModule.login()) {
                loginModule.commit();
            } else {
                throw new LoginException("Login failed for " + request + " using " + loginModule);
            }
        }
        BindResponse result = Subject.doAs(subject, () -> {
            try {
                return conn.operation(request);
            }
            catch (Exception e) {
                LOGGER.warn("SASL GSSAPI operation failed for {}", (Object)this, (Object)e);
                return null;
            }
        });
        if (result == null) {
            throw new LoginException("SASL GSSAPI operation failed");
        }
        return result;
    }
}

