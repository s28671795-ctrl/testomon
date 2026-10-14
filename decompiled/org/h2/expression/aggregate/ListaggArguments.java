/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.aggregate;

import org.h2.expression.Expression;

public final class ListaggArguments {
    private Expression separator;
    private boolean onOverflowTruncate;
    private Expression filter;
    private boolean withoutCount;

    public void setSeparator(Expression expression) {
        this.separator = expression;
    }

    public Expression getSeparator() {
        return this.separator;
    }

    public String getEffectiveSeparator() {
        if (this.separator != null) {
            String string = this.separator.getValue(null).getString();
            return string != null ? string : "";
        }
        return ",";
    }

    public void setOnOverflowTruncate(boolean bl) {
        this.onOverflowTruncate = bl;
    }

    public boolean getOnOverflowTruncate() {
        return this.onOverflowTruncate;
    }

    public void setFilter(Expression expression) {
        this.filter = expression;
    }

    public Expression getFilter() {
        return this.filter;
    }

    public String getEffectiveFilter() {
        if (this.filter != null) {
            String string = this.filter.getValue(null).getString();
            return string != null ? string : "";
        }
        return "...";
    }

    public void setWithoutCount(boolean bl) {
        this.withoutCount = bl;
    }

    public boolean isWithoutCount() {
        return this.withoutCount;
    }
}

