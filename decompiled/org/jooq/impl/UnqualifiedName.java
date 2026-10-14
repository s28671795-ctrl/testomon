/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractName;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class UnqualifiedName
extends AbstractName {
    final String name;
    final Name.Quoted quoted;

    UnqualifiedName(String name) {
        this(name, Name.Quoted.DEFAULT);
    }

    UnqualifiedName(String name, Name.Quoted quoted) {
        this.name = name;
        this.quoted = quoted;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            if (this.quoted == Name.Quoted.UNQUOTED) {
                ctx.sql("unquotedName(");
            } else if (this.quoted == Name.Quoted.QUOTED) {
                ctx.sql("quotedName(");
            } else if (this.quoted == Name.Quoted.SYSTEM) {
                ctx.sql("systemName(");
            } else {
                ctx.sql("name(");
            }
            ctx.sql(Tools.stringLiteral(this.name)).sql(")");
            return;
        }
        RenderQuotedNames q = SettingsTools.getRenderQuotedNames(ctx.settings());
        boolean previous = ctx.quote();
        boolean current = this.quoted != Name.Quoted.SYSTEM && (q == RenderQuotedNames.ALWAYS || q == RenderQuotedNames.EXPLICIT_DEFAULT_QUOTED && (this.quoted == Name.Quoted.DEFAULT || this.quoted == Name.Quoted.QUOTED) || q == RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED && this.quoted == Name.Quoted.QUOTED);
        ctx.quote(current);
        ctx.literal(this.name);
        ctx.quote(previous);
    }

    @Override
    public final String first() {
        return this.name;
    }

    @Override
    public final String last() {
        return this.name;
    }

    @Override
    public final boolean empty() {
        return StringUtils.isEmpty(this.name);
    }

    @Override
    public final boolean qualified() {
        return false;
    }

    @Override
    public final boolean qualifierQualified() {
        return false;
    }

    @Override
    public final Name qualifier() {
        return null;
    }

    @Override
    public final Name unqualifiedName() {
        return this;
    }

    @Override
    public final Name.Quoted quoted() {
        return this.quoted;
    }

    @Override
    public final Name quotedName() {
        return new UnqualifiedName(this.name, Name.Quoted.QUOTED);
    }

    @Override
    public final Name unquotedName() {
        return new UnqualifiedName(this.name, Name.Quoted.UNQUOTED);
    }

    @Override
    public final String[] getName() {
        return new String[]{this.name};
    }

    @Override
    public final Name[] parts() {
        return new Name[]{this};
    }

    @Override
    public int hashCode() {
        if (this.name == null) {
            return 0;
        }
        return 31 + this.name.hashCode();
    }
}

