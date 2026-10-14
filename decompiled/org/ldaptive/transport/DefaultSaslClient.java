/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.transport;

import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import org.ldaptive.BindResponse;
import org.ldaptive.ResultCode;
import org.ldaptive.sasl.DefaultSaslClientRequest;
import org.ldaptive.sasl.Mechanism;
import org.ldaptive.sasl.QualityOfProtection;
import org.ldaptive.transport.TransportConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSaslClient
implements org.ldaptive.sasl.SaslClient<DefaultSaslClientRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSaslClient.class);
    private SaslClient client;

    public SaslClient getClient() {
        return this.client;
    }

    @Override
    public BindResponse bind(TransportConnection conn, DefaultSaslClientRequest request) throws SaslException {
        String serverName = conn.getLdapURL().getHostname();
        try {
            this.client = Sasl.createSaslClient(new String[]{request.getMechanism().mechanism()}, request.getAuthorizationID(), "ldap", serverName, request.getSaslProperties(), request);
            byte[] bytes = this.client.hasInitialResponse() ? this.client.evaluateChallenge(new byte[0]) : null;
            BindResponse response = conn.operation(request.createBindRequest(bytes)).execute();
            if (ResultCode.SASL_BIND_IN_PROGRESS != response.getResultCode()) {
                return response;
            }
            while (!(this.client.isComplete() || ResultCode.SASL_BIND_IN_PROGRESS != response.getResultCode() && ResultCode.SUCCESS != response.getResultCode())) {
                bytes = this.client.evaluateChallenge(response.getServerSaslCreds());
                if (ResultCode.SUCCESS == response.getResultCode()) {
                    if (bytes == null) break;
                    throw new SaslException("SASL client error: received response after completion");
                }
                response = conn.operation(request.createBindRequest(bytes)).execute();
            }
            return response;
        }
        catch (Throwable e) {
            this.dispose();
            if (e instanceof SaslException) {
                throw (SaslException)e;
            }
            throw new SaslException("SASL bind failed for " + request, e);
        }
    }

    public Mechanism getMechanism() {
        return Mechanism.valueOf(this.client.getMechanismName());
    }

    public QualityOfProtection getQualityOfProtection() {
        if (!this.client.isComplete()) {
            return null;
        }
        return QualityOfProtection.fromString((String)this.client.getNegotiatedProperty("javax.security.sasl.qop"));
    }

    public void dispose() {
        if (this.client != null) {
            try {
                this.client.dispose();
            }
            catch (SaslException e) {
                LOGGER.warn("Error disposing of SASL client", e);
            }
            finally {
                this.client = null;
            }
        }
    }
}

