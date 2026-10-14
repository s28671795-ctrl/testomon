/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.jooq.DSLContext;
import org.jooq.Meta;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.Source;
import org.jooq.Version;
import org.jooq.conf.InterpreterSearchSchema;
import org.jooq.exception.DataDefinitionException;
import org.jooq.impl.AbstractNode;
import org.jooq.impl.DSL;
import org.jooq.impl.Tools;

final class VersionImpl
extends AbstractNode<Version>
implements Version {
    private final DSLContext ctx;
    private final Meta meta;
    private final List<Parent> parents;

    private VersionImpl(DSLContext ctx, String id, Meta meta, List<Parent> parents) {
        super(id, null);
        this.ctx = ctx;
        this.meta = meta != null ? meta : VersionImpl.init(ctx);
        this.parents = parents;
    }

    private static final Meta init(DSLContext ctx) {
        Meta result = ctx.meta("");
        List<InterpreterSearchSchema> searchPath = ctx.settings().getInterpreterSearchPath();
        for (InterpreterSearchSchema schema : searchPath) {
            result = result.apply(DSL.createSchema(DSL.schema(DSL.name(schema.getCatalog(), schema.getSchema()))));
        }
        return result;
    }

    VersionImpl(DSLContext ctx, String id, Meta meta, Version parent, Queries queries) {
        this(ctx, id, meta, Arrays.asList(new Parent((VersionImpl)parent, queries)));
    }

    VersionImpl(DSLContext ctx, String id, Meta meta, Version[] parents) {
        this(ctx, id, meta, VersionImpl.wrap(parents));
    }

    private static List<Parent> wrap(Version[] parents) {
        return Tools.map(parents, p -> new Parent((VersionImpl)p, null));
    }

    @Override
    public final Meta meta() {
        return this.meta;
    }

    @Override
    public final List<Version> parents() {
        return new AbstractList<Version>(){

            @Override
            public Version get(int index) {
                return VersionImpl.this.parents.get((int)index).version;
            }

            @Override
            public int size() {
                return VersionImpl.this.parents.size();
            }
        };
    }

    @Override
    public final Version apply(String newId, Query ... migration2) {
        return this.apply(newId, this.ctx.queries(migration2));
    }

    @Override
    public final Version apply(String newId, Collection<? extends Query> migration2) {
        return this.apply(newId, this.ctx.queries(migration2));
    }

    @Override
    public final Version apply(String newId, String migration2) {
        return this.apply(newId, this.ctx.parser().parse(migration2));
    }

    @Override
    public final Version apply(String newId, Queries migration2) {
        return new VersionImpl(this.ctx, newId, this.meta().apply(migration2), this, migration2);
    }

    @Override
    public final Queries migrateTo(Version version) {
        if (this.equals(version)) {
            return this.ctx.queries(new Query[0]);
        }
        VersionImpl subgraph = ((VersionImpl)version).subgraphTo(this);
        if (subgraph == null) {
            if (Boolean.TRUE.equals(this.ctx.settings().isMigrationAllowsUndo())) {
                return this.meta().migrateTo(version.meta());
            }
            throw new DataDefinitionException("No forward path available between versions " + this.id() + " and " + version.id() + ". Use Settings.migrationAllowsUndo to enable this feature.");
        }
        return this.migrateTo(subgraph, this.ctx.queries(new Query[0]));
    }

    private final VersionImpl subgraphTo(VersionImpl ancestor) {
        ArrayList<Parent> list = null;
        for (Parent parent : this.parents) {
            if (parent.version.equals(ancestor)) {
                if (list == null) {
                    list = new ArrayList<Parent>();
                }
                list.add(new Parent(new VersionImpl(this.ctx, parent.version.id(), parent.version.meta, Collections.emptyList()), parent.queries));
                continue;
            }
            VersionImpl p = parent.version.subgraphTo(ancestor);
            if (p == null) continue;
            if (list == null) {
                list = new ArrayList();
            }
            list.add(new Parent(p, parent.queries));
        }
        return list == null ? null : new VersionImpl(this.ctx, this.id(), this.meta, list);
    }

    private final Queries migrateTo(VersionImpl target, Queries result) {
        if (!target.forceApply()) {
            return this.meta().migrateTo(target.meta());
        }
        for (Parent parent : target.parents) {
            result = this.migrateTo(parent.version, result);
            if (parent.queries != null) {
                result = result.concat(parent.queries);
                continue;
            }
            result = result.concat(parent.version.meta().migrateTo(target.meta()));
        }
        return result;
    }

    private final boolean forceApply() {
        return Tools.anyMatch(this.parents, p -> p.queries != null || p.version.forceApply());
    }

    @Override
    public final Version commit(String newId, String ... newMeta) {
        return this.commit(newId, this.ctx.meta(newMeta));
    }

    @Override
    public final Version commit(String newId, Source ... newMeta) {
        return this.commit(newId, this.ctx.meta(newMeta));
    }

    @Override
    public final Version commit(String newId, Meta newMeta) {
        return new VersionImpl(this.ctx, newId, newMeta, new Version[]{this});
    }

    @Override
    public final Version merge(String newId, Version with) {
        Meta m4 = this.commonAncestor(with).meta();
        return new VersionImpl(this.ctx, newId, m4.apply(m4.migrateTo(this.meta()).concat(m4.migrateTo(with.meta()))), new Version[]{this, with});
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + this.id().hashCode();
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        VersionImpl other = (VersionImpl)obj;
        return this.id().equals(other.id());
    }

    public String toString() {
        return "-- Version: " + this.id() + "\n" + String.valueOf(this.meta());
    }

    private record Parent(VersionImpl version, Queries queries) {
        @Override
        public String toString() {
            return this.version.toString();
        }
    }
}

