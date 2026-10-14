/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.RenderContext;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DetachedException;
import org.jooq.impl.Cache;
import org.jooq.impl.CacheType;
import org.jooq.impl.DefaultBindContext;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.NoConnectionFactory;
import org.jooq.impl.ParsingStatement;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.DefaultConnection;

final class ParsingConnection
extends DefaultConnection {
    private static final JooqLogger log = JooqLogger.getLogger(ParsingConnection.class);
    final Configuration configuration;

    ParsingConnection(Configuration configuration) {
        super(configuration.connectionProvider().acquire());
        if (this.getDelegate() == null) {
            if (configuration.connectionFactory() instanceof NoConnectionFactory) {
                throw new DetachedException("ConnectionProvider did not provide a JDBC Connection");
            }
            throw new DetachedException("Attempt to use a ParsingConnection (JDBC) when only an R2BDC ConnectionFactory was configured. Using ParsingConnectionFactory instead.");
        }
        this.configuration = configuration;
    }

    static final DefaultRenderContext.Rendered translate(Configuration configuration, String sql, Param<?> ... bindValues) {
        log.debug((Object)"Translating from", sql);
        DefaultRenderContext.Rendered result = null;
        Supplier<CacheValue> miss = () -> {
            log.debug((Object)"Translation cache miss", sql);
            return new CacheValue(configuration, sql, bindValues);
        };
        Settings settings = configuration.settings();
        if (CacheType.CACHE_PARSING_CONNECTION.category.predicate.test(settings) && bindValues.length > 0) {
            switch (SettingsTools.getParamType(settings)) {
                case INLINED: 
                case NAMED_OR_INLINED: {
                    result = miss.get().rendered(bindValues);
                }
            }
        }
        if (result == null) {
            result = Cache.run(configuration, miss, CacheType.CACHE_PARSING_CONNECTION, () -> Cache.key(sql, Tools.map(ParsingConnection.nonNull(bindValues), f -> f.getDataType()))).rendered(bindValues);
        }
        log.debug((Object)"Translating to", result.sql);
        return result;
    }

    private static Param<?>[] nonNull(Param<?>[] bindValues) {
        for (int i = 0; i < bindValues.length; ++i) {
            if (bindValues[i] != null) continue;
            throw new DataAccessException("Bind value at position " + i + " not set");
        }
        return bindValues;
    }

    @Override
    public final Statement createStatement() throws SQLException {
        return new ParsingStatement(this, this.getDelegate().createStatement());
    }

    @Override
    public final Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ParsingStatement(this, this.getDelegate().createStatement(resultSetType, resultSetConcurrency));
    }

    @Override
    public final Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new ParsingStatement(this, this.getDelegate().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
    }

    private final ThrowingFunction<List<List<Param<?>>>, PreparedStatement, SQLException> prepareAndBind(String sql, ThrowingFunction<String, PreparedStatement, SQLException> prepare) {
        return p -> {
            int size = p.size();
            DefaultRenderContext.Rendered rendered = size == 0 ? ParsingConnection.translate(this.configuration, sql, new Param[0]) : ParsingConnection.translate(this.configuration, sql, ((List)p.get(0)).toArray(Tools.EMPTY_PARAM));
            PreparedStatement s2 = (PreparedStatement)prepare.apply(rendered.sql);
            for (int i = 0; i < size; ++i) {
                if (i > 0) {
                    rendered = ParsingConnection.translate(this.configuration, sql, ((List)p.get(i)).toArray(Tools.EMPTY_PARAM));
                }
                new DefaultBindContext(this.configuration, null, s2).visit(rendered.bindValues);
                if (size <= 1 && !(p instanceof ArrayList)) continue;
                s2.addBatch();
            }
            return s2;
        };
    }

    @Override
    public final PreparedStatement prepareStatement(String sql) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2)));
    }

    @Override
    public final PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2, resultSetType, resultSetConcurrency)));
    }

    @Override
    public final PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2, resultSetType, resultSetConcurrency, resultSetHoldability)));
    }

    @Override
    public final PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2, autoGeneratedKeys)));
    }

    @Override
    public final PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2, columnIndexes)));
    }

    @Override
    public final PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareStatement((String)s2, columnNames)));
    }

    @Override
    public final CallableStatement prepareCall(String sql) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareCall((String)s2)));
    }

    @Override
    public final CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareCall((String)s2, resultSetType, resultSetConcurrency)));
    }

    @Override
    public final CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return new ParsingStatement(this, this.prepareAndBind(sql, s2 -> this.getDelegate().prepareCall((String)s2, resultSetType, resultSetConcurrency, resultSetHoldability)));
    }

    @Override
    public final void close() throws SQLException {
        this.configuration.connectionProvider().release(this.getDelegate());
    }

    static final class CacheValue {
        final String output;
        final int bindSize;
        final Map<Integer, List<Integer>> bindMapping;

        CacheValue(Configuration configuration, String input, Param<?>[] bindValues) {
            DSLContext ctx = configuration.dsl();
            DefaultRenderContext render = (DefaultRenderContext)ctx.renderContext();
            ((RenderContext)render.paramType(configuration.settings().getParamType())).visit(ctx.parser().parseQuery(input, bindValues));
            this.output = render.render();
            this.bindSize = render.bindValues().size();
            this.bindMapping = new HashMap<Integer, List<Integer>>();
            for (int i = 0; i < bindValues.length; ++i) {
                for (int j = 0; j < render.bindValues().size(); ++j) {
                    if (bindValues[i] != render.bindValues().get(j)) continue;
                    this.bindMapping.computeIfAbsent(i, x -> new ArrayList()).add(j);
                }
            }
        }

        DefaultRenderContext.Rendered rendered(Param<?> ... bindValues) {
            QueryPart[] binds = new Param[this.bindSize];
            for (int i = 0; i < bindValues.length; ++i) {
                Iterator iterator2 = this.bindMapping.getOrDefault(i, Collections.emptyList()).iterator();
                while (iterator2.hasNext()) {
                    int mapped = (Integer)iterator2.next();
                    binds[mapped] = bindValues[i];
                }
            }
            return new DefaultRenderContext.Rendered(this.output, new QueryPartList(binds), 0);
        }

        public String toString() {
            return this.output;
        }
    }
}

