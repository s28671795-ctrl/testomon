/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.logic;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

@ThreadSafe
public class TrimOrNullStringFunction
implements Function<String, String> {
    public static final TrimOrNullStringFunction INSTANCE = new TrimOrNullStringFunction();

    @Override
    @Nullable
    @NotEmpty
    public String apply(@Nullable String input) {
        return StringSupport.trimOrNull(input);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return obj instanceof TrimOrNullStringFunction;
    }

    public int hashCode() {
        return 31;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).toString();
    }
}

