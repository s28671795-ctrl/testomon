/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Block;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Pro;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractContext;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.CreateProcedureImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRenderContext;
import org.jooq.impl.Keywords;
import org.jooq.impl.LoopImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.impl.WithImpl;

enum ScopeMarker {
    SCOPE_TOP_LEVEL_BLOCK(true, Tools.ExtendedDataKey.DATA_TOP_LEVEL_BLOCK, (ctx, beforeFirst, afterLast, object) -> {
        BlockImpl.TopLevelBlock block = (BlockImpl.TopLevelBlock)object;
        ((RenderContext)((RenderContext)ctx.visit(DSL.label(block.label).label(DSL.statement("")))).formatSeparator()).sql("");
    }),
    SCOPE_LOOP(false, Tools.ExtendedDataKey.DATA_SCOPE_LOOP, (ctx, beforeFirst, afterLast, object) -> {
        LoopImpl.ScopeLoop loop = (LoopImpl.ScopeLoop)object;
        ((RenderContext)((RenderContext)ctx.visit(DSL.label(loop.label).label(DSL.statement("")))).formatSeparator()).sql("");
    }),
    SCOPE_DECLARATIONS(false, Tools.ExtendedDataKey.DATA_SCOPE_DECLARATIONS, (ctx, beforeFirst, afterLast, object) -> {
        BlockImpl.ScopeDeclarations declarations = (BlockImpl.ScopeDeclarations)object;
        Block s2 = DSL.statements(declarations.variables.values());
        boolean declare = BlockImpl.SUPPORTS_DECLARE_BLOCK_KEYWORD.contains((Object)ctx.dialect());
        if (declare) {
            if (declarations.anonymousBlock || declarations.trigger && ctx.family() == SQLDialect.ORACLE || declarations.scopeLevel > 0 || CreateProcedureImpl.REQUIRE_BODY_AS_STRING.contains((Object)ctx.dialect())) {
                ctx.visit(Keywords.K_DECLARE);
            }
            ctx.formatIndentStart().formatSeparator();
        }
        Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> ctx.visit(s2));
        if (declare) {
            ((RenderContext)ctx.formatIndentEnd().formatSeparator()).sql("");
        }
    }),
    TOP_LEVEL_CTE(true, Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE, (ctx, beforeFirst, afterLast, object) -> {
        boolean noWith;
        TopLevelCte cte = (TopLevelCte)object;
        boolean single = cte.size() == 1;
        boolean bl = noWith = afterLast != null && beforeFirst.positions[0] == afterLast.positions[0];
        if (noWith) {
            WithImpl.acceptWithRecursive(ctx, cte.recursive);
            if (single) {
                ctx.formatIndentStart().formatSeparator();
            } else {
                ctx.sql(' ');
            }
        }
        ((RenderContext)ctx.scopeStart()).data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE, new TopLevelCte());
        ((RenderContext)((RenderContext)ctx.declareCTE(true)).visit(cte)).declareCTE(false);
        ctx.scopeEnd();
        if (noWith) {
            if (single) {
                ctx.formatIndentEnd();
            }
        } else if (!Tools.isRendersSeparator(cte)) {
            ctx.sql(',');
        }
        ctx.formatSeparator().sql("");
    });

    final ReplacementRenderer renderer;
    final boolean topLevelOnly;
    final Object key;
    final Marker beforeFirst;
    final Marker afterLast;

    private ScopeMarker(boolean topLevelOnly, Object key, ReplacementRenderer renderer) {
        this.renderer = renderer;
        this.topLevelOnly = topLevelOnly;
        this.key = key;
        this.beforeFirst = new Marker(this.name() + "_BEFORE");
        this.afterLast = new Marker(this.name() + "_AFTER");
    }

    @FunctionalInterface
    static interface ReplacementRenderer {
        public void render(DefaultRenderContext var1, AbstractContext.ScopeStackElement var2, AbstractContext.ScopeStackElement var3, ScopeContent var4);
    }

    static class Marker
    implements QueryPartInternal,
    QOM.UTransient {
        private final String marker;

        Marker(String marker) {
            this.marker = marker;
        }

        @Override
        public final boolean rendersContent(Context<?> ctx) {
            return false;
        }

        @Override
        public final void accept(Context<?> ctx) {
        }

        @Override
        public final Clause[] clauses(Context<?> ctx) {
            return null;
        }

        @Override
        public final boolean declaresFields() {
            return false;
        }

        @Override
        public final boolean declaresTables() {
            return false;
        }

        @Override
        public final boolean declaresWindows() {
            return false;
        }

        @Override
        public final boolean declaresCTE() {
            return false;
        }

        @Override
        @Pro
        public final boolean declaresParameters() {
            return false;
        }

        @Override
        public final boolean generatesCast() {
            return false;
        }

        @Override
        public String toString() {
            return this.marker;
        }
    }

    static interface ScopeContent {
        public boolean isEmpty();
    }
}

