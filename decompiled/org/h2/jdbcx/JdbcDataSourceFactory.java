/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbcx;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import org.h2.engine.SysProperties;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.message.Trace;
import org.h2.message.TraceSystem;

public final class JdbcDataSourceFactory
implements ObjectFactory {
    private static final TraceSystem traceSystem = new TraceSystem(SysProperties.CLIENT_TRACE_DIRECTORY + "h2datasource" + ".trace.db");
    private final Trace trace = traceSystem.getTrace(14);

    @Override
    public synchronized Object getObjectInstance(Object object, Name name, Context context, Hashtable<?, ?> hashtable) {
        Reference reference;
        if (this.trace.isDebugEnabled()) {
            this.trace.debug("getObjectInstance obj={0} name={1} nameCtx={2} environment={3}", object, name, context, hashtable);
        }
        if (object instanceof Reference && (reference = (Reference)object).getClassName().equals(JdbcDataSource.class.getName())) {
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL((String)reference.get("url").getContent());
            jdbcDataSource.setUser((String)reference.get("user").getContent());
            jdbcDataSource.setPassword((String)reference.get("password").getContent());
            jdbcDataSource.setDescription((String)reference.get("description").getContent());
            String string = (String)reference.get("loginTimeout").getContent();
            jdbcDataSource.setLoginTimeout(Integer.parseInt(string));
            return jdbcDataSource;
        }
        return null;
    }

    public static TraceSystem getTraceSystem() {
        return traceSystem;
    }

    Trace getTrace() {
        return this.trace;
    }

    static {
        traceSystem.setLevelFile(SysProperties.DATASOURCE_TRACE_LEVEL);
    }
}

