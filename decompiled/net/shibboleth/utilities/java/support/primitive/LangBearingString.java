/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.primitive;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class LangBearingString
extends Pair<String, String> {
    public LangBearingString(@Nullable String value) {
        super(value, null);
    }

    public LangBearingString(@Nullable String value, @Nullable @NotEmpty String lang) {
        super(value, StringSupport.trimOrNull(lang));
    }

    @Nullable
    public String getValue() {
        return (String)this.getFirst();
    }

    @Nullable
    @NotEmpty
    public String getLang() {
        return (String)this.getSecond();
    }

    @Override
    public void setSecond(@Nullable String newSecond) {
        super.setSecond(StringSupport.trimOrNull(newSecond));
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

