/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.generator;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.generator.ValueGenerator;

public class StaticValueGenerator
implements ValueGenerator {
    private String value;

    public StaticValueGenerator() {
    }

    public StaticValueGenerator(String value) {
        this.value = value;
    }

    @Override
    public String generateValue(WebContext webContext, SessionStore sessionStore) {
        CommonHelper.assertNotNull("value", this.value);
        return this.value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

