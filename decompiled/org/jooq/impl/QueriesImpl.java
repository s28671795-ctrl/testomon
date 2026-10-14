/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jooq.Batch;
import org.jooq.Block;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.ResultQuery;
import org.jooq.Results;
import org.jooq.Traverser;
import org.jooq.impl.AbstractAttachableQueryPart;
import org.jooq.impl.DefaultParseContext;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ResultsImpl;
import org.jooq.impl.Tools;

final class QueriesImpl
extends AbstractAttachableQueryPart
implements Queries {
    private final QueryPartList<Query> queries;
    private static final Pattern P_IGNORE_FORMATTED = Pattern.compile("^(?sm:\\n? *(.*?) *\\n?)$");
    private static final Pattern P_IGNORE_UNFORMATTED = Pattern.compile("^(?sm: *(.*?) *)$");

    QueriesImpl(Configuration configuration, Collection<? extends Query> queries) {
        super(configuration);
        this.queries = new QueryPartList<Query>((Iterable<? extends Query>)queries);
    }

    @Override
    public final Queries concat(Queries other) {
        Query[] array = other.queries();
        ArrayList<Query> list = new ArrayList<Query>(this.queries.size() + array.length);
        list.addAll(this.queries);
        list.addAll(Arrays.asList(array));
        return new QueriesImpl(this.configuration(), list);
    }

    @Override
    public final Query[] queries() {
        return this.queries.toArray((E[])Tools.EMPTY_QUERY);
    }

    @Override
    public final Block block() {
        return this.configurationOrDefault().dsl().begin(this.queries);
    }

    @Override
    public final Batch batch() {
        return this.configurationOrDefault().dsl().batch(this.queries);
    }

    @Override
    public final Iterator<Query> iterator() {
        return this.queries.iterator();
    }

    @Override
    public final Stream<Query> stream() {
        return this.queryStream();
    }

    @Override
    public final Stream<Query> queryStream() {
        return this.queries.stream();
    }

    @Override
    public final Results fetchMany() {
        Configuration c = this.configurationOrThrow();
        ResultsImpl results = new ResultsImpl(c);
        DSLContext ctx = c.dsl();
        for (Query query : this) {
            if (query instanceof ResultQuery) {
                ResultQuery q = (ResultQuery)query;
                results.resultsOrRows.addAll(ctx.fetchMany(q).resultsOrRows());
                continue;
            }
            results.resultsOrRows.add(new ResultsImpl.ResultOrRowsImpl(ctx.execute(query)));
        }
        return results;
    }

    @Override
    public final int[] executeBatch() {
        return this.configurationOrThrow().dsl().batch(this).execute();
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean first = true;
        boolean separatorRequired = false;
        for (Query query : this) {
            boolean i = query instanceof DefaultParseContext.IgnoreQuery;
            if (first) {
                first = false;
            } else if (separatorRequired) {
                ctx.formatSeparator();
            }
            if (i) {
                query = ctx.format() ? new DefaultParseContext.IgnoreQuery(P_IGNORE_FORMATTED.matcher(((DefaultParseContext.IgnoreQuery)query).sql).replaceFirst("$1")) : new DefaultParseContext.IgnoreQuery(P_IGNORE_UNFORMATTED.matcher(((DefaultParseContext.IgnoreQuery)query).sql).replaceFirst("$1"));
            }
            ctx.visit(query);
            if (!i) {
                ctx.sql(';');
            }
            separatorRequired = !i || !((DefaultParseContext.IgnoreQuery)query).sql.endsWith("\n");
        }
    }

    @Override
    public final QOM.UnmodifiableList<? extends Query> $queries() {
        return QOM.unmodifiable(this.queries);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$queries());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.queries, q -> new QueriesImpl(this.configuration(), (Collection<? extends Query>)q), replacer);
    }

    @Override
    public int hashCode() {
        return this.queries.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QueriesImpl)) {
            return false;
        }
        return this.queries.equals(((QueriesImpl)obj).queries);
    }
}

