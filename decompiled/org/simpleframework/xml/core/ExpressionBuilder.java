/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import org.simpleframework.xml.core.ClassType;
import org.simpleframework.xml.core.Detail;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.PathParser;
import org.simpleframework.xml.core.Support;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.util.Cache;
import org.simpleframework.xml.util.LimitedCache;

class ExpressionBuilder {
    private final Cache<Expression> cache = new LimitedCache<Expression>();
    private final Format format;
    private final Class type;

    public ExpressionBuilder(Detail detail, Support support) {
        this.format = support.getFormat();
        this.type = detail.getType();
    }

    public Expression build(String path) throws Exception {
        Expression expression = this.cache.fetch(path);
        if (expression == null) {
            return this.create(path);
        }
        return expression;
    }

    private Expression create(String path) throws Exception {
        ClassType detail = new ClassType(this.type);
        PathParser expression = new PathParser(path, detail, this.format);
        if (this.cache != null) {
            this.cache.cache(path, expression);
        }
        return expression;
    }
}

