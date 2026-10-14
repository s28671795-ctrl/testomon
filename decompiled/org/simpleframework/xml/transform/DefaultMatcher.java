/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.transform;

import org.simpleframework.xml.transform.ArrayMatcher;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.PackageMatcher;
import org.simpleframework.xml.transform.PrimitiveMatcher;
import org.simpleframework.xml.transform.Transform;

class DefaultMatcher
implements Matcher {
    private Matcher primitive = new PrimitiveMatcher();
    private Matcher matcher;
    private Matcher stock = new PackageMatcher();
    private Matcher array = new ArrayMatcher(this);

    public DefaultMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public Transform match(Class type) throws Exception {
        Transform value = this.matcher.match(type);
        if (value != null) {
            return value;
        }
        return this.matchType(type);
    }

    private Transform matchType(Class type) throws Exception {
        if (type.isArray()) {
            return this.array.match(type);
        }
        if (type.isPrimitive()) {
            return this.primitive.match(type);
        }
        return this.stock.match(type);
    }
}

