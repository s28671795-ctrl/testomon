/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Name;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractName;
import org.jooq.impl.Tools;
import org.jooq.impl.UnqualifiedName;
import org.jooq.tools.StringUtils;

final class QualifiedName
extends AbstractName {
    private final UnqualifiedName[] qualifiedName;
    private transient Integer hash;

    QualifiedName(String[] qualifiedName) {
        this(qualifiedName, Name.Quoted.DEFAULT);
    }

    QualifiedName(String[] qualifiedName, Name.Quoted quoted) {
        this(QualifiedName.names(qualifiedName, quoted));
    }

    QualifiedName(Name[] qualifiedName) {
        this(QualifiedName.last(QualifiedName.nonEmpty(qualifiedName)));
    }

    private QualifiedName(UnqualifiedName[] qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    private static final UnqualifiedName[] names(String[] qualifiedName, Name.Quoted quoted) {
        return Tools.map(QualifiedName.nonEmpty(qualifiedName), s2 -> new UnqualifiedName((String)s2, quoted), UnqualifiedName[]::new);
    }

    private static final UnqualifiedName[] last(Name[] qualifiedName) {
        if (qualifiedName instanceof UnqualifiedName[]) {
            UnqualifiedName[] u = (UnqualifiedName[])qualifiedName;
            return u;
        }
        UnqualifiedName[] result = new UnqualifiedName[qualifiedName.length];
        for (int i = 0; i < qualifiedName.length; ++i) {
            UnqualifiedName u;
            Name name = qualifiedName[i];
            if (name instanceof QualifiedName) {
                QualifiedName q = (QualifiedName)name;
                result[i] = q.qualifiedName[q.qualifiedName.length - 1];
                continue;
            }
            name = qualifiedName[i];
            result[i] = name instanceof UnqualifiedName ? (u = (UnqualifiedName)name) : new UnqualifiedName(qualifiedName[i].last());
        }
        return result;
    }

    private static final String[] nonEmpty(String[] qualifiedName) {
        String[] result;
        int nulls = 0;
        for (String name : qualifiedName) {
            if (!StringUtils.isEmpty(name)) continue;
            ++nulls;
        }
        if (nulls > 0) {
            result = new String[qualifiedName.length - nulls];
            for (int i = qualifiedName.length - 1; i >= 0; --i) {
                if (StringUtils.isEmpty(qualifiedName[i])) {
                    --nulls;
                    continue;
                }
                result[i - nulls] = qualifiedName[i];
            }
        } else {
            result = qualifiedName;
        }
        return result;
    }

    private static final Name[] nonEmpty(Name[] names) {
        Name[] result;
        int nulls = 0;
        for (Name name : names) {
            if (name != null && !name.equals(NO_NAME)) continue;
            ++nulls;
        }
        if (nulls > 0) {
            result = new Name[names.length - nulls];
            for (int i = names.length - 1; i >= 0; --i) {
                if (names[i] == null || names[i].equals(NO_NAME)) {
                    --nulls;
                    continue;
                }
                result[i - nulls] = names[i];
            }
        } else {
            result = names;
        }
        return result;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("name(");
            String separator = "";
            for (UnqualifiedName name : this.qualifiedName) {
                ctx.sql(separator).sql(Tools.stringLiteral(name.last()));
                separator = ", ";
            }
            ctx.sql(')');
            return;
        }
        if (ctx.qualify()) {
            String separator = "";
            for (UnqualifiedName name : this.qualifiedName) {
                ctx.sql(separator).visit(name);
                separator = ".";
            }
        } else {
            ctx.visit(this.qualifiedName[this.qualifiedName.length - 1]);
        }
    }

    @Override
    public final String first() {
        return this.qualifiedName.length > 0 ? this.qualifiedName[0].last() : null;
    }

    @Override
    public final String last() {
        return this.qualifiedName.length > 0 ? this.qualifiedName[this.qualifiedName.length - 1].last() : null;
    }

    @Override
    public final boolean empty() {
        return Tools.allMatch(this.qualifiedName, n -> n.empty());
    }

    @Override
    public final boolean qualified() {
        return this.qualifiedName.length > 1;
    }

    @Override
    public final boolean qualifierQualified() {
        return this.qualifiedName.length > 2;
    }

    @Override
    public final Name qualifier() {
        if (this.qualifiedName.length <= 1) {
            return null;
        }
        if (this.qualifiedName.length == 2) {
            return this.qualifiedName[0];
        }
        UnqualifiedName[] qualifier = new UnqualifiedName[this.qualifiedName.length - 1];
        System.arraycopy(this.qualifiedName, 0, qualifier, 0, qualifier.length);
        return new QualifiedName(qualifier);
    }

    @Override
    public final Name unqualifiedName() {
        if (this.qualifiedName.length == 0) {
            return this;
        }
        return this.qualifiedName[this.qualifiedName.length - 1];
    }

    @Override
    public final Name.Quoted quoted() {
        Name.Quoted result = null;
        for (UnqualifiedName name : this.qualifiedName) {
            if (result == null) {
                result = name.quoted();
                continue;
            }
            if (result == name.quoted()) continue;
            return Name.Quoted.MIXED;
        }
        return result == null ? Name.Quoted.DEFAULT : result;
    }

    @Override
    public final Name quotedName() {
        return new QualifiedName(Tools.map(this.qualifiedName, n -> n.quotedName(), Name[]::new));
    }

    @Override
    public final Name unquotedName() {
        return new QualifiedName(Tools.map(this.qualifiedName, n -> n.unquotedName(), Name[]::new));
    }

    @Override
    public final String[] getName() {
        return Tools.map(this.qualifiedName, n -> n.last(), String[]::new);
    }

    @Override
    public final Name[] parts() {
        return (Name[])this.qualifiedName.clone();
    }

    @Override
    public int hashCode() {
        if (this.hash == null) {
            int h2 = 1;
            for (int i = 0; i < this.qualifiedName.length; ++i) {
                UnqualifiedName n = this.qualifiedName[i];
                h2 = n.name == null ? 31 * h2 + 0 : 31 * h2 + n.name.hashCode();
            }
            this.hash = h2;
        }
        return this.hash;
    }
}

