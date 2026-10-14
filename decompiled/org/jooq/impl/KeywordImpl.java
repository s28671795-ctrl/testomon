/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Keyword;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.QOM;

final class KeywordImpl
extends AbstractQueryPart
implements Keyword,
QOM.UTransient {
    private final String asIs;
    private String lower;
    private String upper;
    private String pascal;

    KeywordImpl(String keyword) {
        this.asIs = keyword;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.separatorRequired()) {
            ctx.sql(' ');
        }
        ctx.sql(this.render(ctx), true);
    }

    private String render(Context<?> ctx) {
        RenderKeywordCase style = SettingsTools.getRenderKeywordCase(ctx.settings());
        switch (style) {
            case AS_IS: {
                return this.asIs;
            }
            case LOWER: {
                return this.lower == null ? (this.lower = this.asIs.toLowerCase()) : this.lower;
            }
            case UPPER: {
                return this.upper == null ? (this.upper = this.asIs.toUpperCase()) : this.upper;
            }
            case PASCAL: {
                return this.pascal == null ? (this.pascal = KeywordImpl.pascal(this.asIs)) : this.pascal;
            }
        }
        throw new UnsupportedOperationException("Unsupported style: " + String.valueOf((Object)style));
    }

    private static final String pascal(String keyword) {
        if (keyword.isEmpty()) {
            return keyword;
        }
        if (keyword.indexOf(32) >= 0) {
            StringBuilder sb = new StringBuilder();
            int prev = 0;
            int next = 0;
            do {
                next = keyword.indexOf(32, prev);
                if (prev > 0) {
                    sb.append(' ');
                }
                sb.append(Character.toUpperCase(keyword.charAt(prev)));
                sb.append(keyword.substring(prev + 1, next == -1 ? keyword.length() : next).toLowerCase());
                prev = next + 1;
            } while (next != -1);
            return sb.toString();
        }
        return Character.toUpperCase(keyword.charAt(0)) + keyword.substring(1).toLowerCase();
    }
}

