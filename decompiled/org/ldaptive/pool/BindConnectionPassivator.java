/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import org.ldaptive.AnonymousBindRequest;
import org.ldaptive.BindRequest;
import org.ldaptive.BindResponse;
import org.ldaptive.Connection;
import org.ldaptive.pool.ConnectionPassivator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BindConnectionPassivator
implements ConnectionPassivator {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private BindRequest bindRequest;

    public BindConnectionPassivator() {
        this(new AnonymousBindRequest());
    }

    public BindConnectionPassivator(BindRequest br) {
        this.bindRequest = br;
    }

    public BindRequest getBindRequest() {
        return this.bindRequest;
    }

    public void setBindRequest(BindRequest br) {
        this.bindRequest = br;
    }

    @Override
    public Boolean apply(Connection conn) {
        if (conn != null) {
            try {
                BindResponse result = conn.operation(this.bindRequest).execute();
                return result.isSuccess();
            }
            catch (Exception e) {
                this.logger.debug("Passivation failed for bind request {}", (Object)this.bindRequest, (Object)e);
            }
        }
        return false;
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "bindRequest=" + this.bindRequest + "]";
    }
}

