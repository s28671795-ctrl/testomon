/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.generator;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.generator.ValueGenerator;

public class RandomValueGenerator
implements ValueGenerator {
    private int size;

    public RandomValueGenerator() {
        this.setSize(10);
    }

    public RandomValueGenerator(int size) {
        this.setSize(size);
    }

    @Override
    public String generateValue(WebContext webContext, SessionStore sessionStore) {
        return CommonHelper.randomString(this.size);
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        CommonHelper.assertTrue(size > 0, "size must be greater than 0");
        this.size = size;
    }
}

