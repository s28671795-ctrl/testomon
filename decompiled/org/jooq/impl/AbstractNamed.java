/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.List;
import org.jooq.Comment;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.impl.AbstractName;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.CommentImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

abstract class AbstractNamed
extends AbstractQueryPart
implements Named {
    private final Name name;
    private final Comment comment;

    AbstractNamed(Name name, Comment comment) {
        this.name = name == null ? AbstractName.NO_NAME : name;
        this.comment = comment == null ? CommentImpl.NO_COMMENT : comment;
    }

    @Override
    public final String getName() {
        return StringUtils.defaultIfNull(this.getQualifiedName().last(), "");
    }

    @Override
    public Name getQualifiedName() {
        return this.name;
    }

    @Override
    public final Name getUnqualifiedName() {
        return this.name.unqualifiedName();
    }

    @Override
    public final String getComment() {
        return this.comment.getComment();
    }

    @Override
    public final Comment getCommentPart() {
        return this.comment;
    }

    @Override
    public int hashCode() {
        return this.getQualifiedName().hashCode();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (that instanceof AbstractNamed) {
            AbstractNamed n = (AbstractNamed)that;
            if (!this.getQualifiedName().equals(n.getQualifiedName())) {
                return false;
            }
        }
        return super.equals(that);
    }

    static final Name nameOrDefault(Named named) {
        return named == null ? AbstractName.NO_NAME : named.getUnqualifiedName();
    }

    static final Name qualify(Named qualifier, Name name) {
        return qualifier == null || name == null || name.empty() || name.qualified() ? name : qualifier.getQualifiedName().append(name);
    }

    static final <N extends Named> List<N> findAll(String name, Iterable<? extends N> in) {
        ArrayList<Named> result = new ArrayList<Named>();
        for (Named n : in) {
            if (!n.getName().equals(name)) continue;
            result.add(n);
        }
        return result;
    }

    static final <N extends Named> List<N> findAll(Name name, Iterable<? extends N> in) {
        ArrayList<Named> result = new ArrayList<Named>();
        for (Named n : in) {
            if (!n.getQualifiedName().equals(name) && !n.getUnqualifiedName().equals(name)) continue;
            result.add(n);
        }
        return result;
    }

    static final <N extends Named> N find(String name, Iterable<? extends N> in) {
        return (N)Tools.findAny(in, n -> n.getName().equals(name));
    }

    static final <N extends Named> N find(Name name, Iterable<? extends N> in) {
        Named unqualified = null;
        for (Named n : in) {
            if (n.getQualifiedName().equals(name)) {
                return (N)n;
            }
            if (unqualified != null || !n.getUnqualifiedName().equals(name.unqualifiedName())) continue;
            unqualified = n;
        }
        return (N)unqualified;
    }

    static final <N extends Named> N findIgnoreCase(String name, Iterable<? extends N> in) {
        return (N)Tools.findAny(in, n -> n.getName().equalsIgnoreCase(name));
    }

    static final <N extends Named> N findIgnoreCase(Name name, Iterable<? extends N> in) {
        Named unqualified = null;
        for (Named n : in) {
            if (n.getQualifiedName().equalsIgnoreCase(name)) {
                return (N)n;
            }
            if (unqualified != null || !n.getUnqualifiedName().equalsIgnoreCase(name.unqualifiedName())) continue;
            unqualified = n;
        }
        return (N)unqualified;
    }

    @Override
    public final Name $name() {
        return this.getQualifiedName();
    }
}

