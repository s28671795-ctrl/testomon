/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index;

import com.google.common.base.MoreObjects;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;

public class SimpleStringMetadataIndexKey
implements MetadataIndexKey {
    private String value;

    public SimpleStringMetadataIndexKey(@Nonnull String newValue) {
        this.value = Constraint.isNotNull(StringSupport.trimOrNull(newValue), "String index value was null");
    }

    @Nonnull
    public String getValue() {
        return this.value;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.value).toString();
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleStringMetadataIndexKey) {
            return this.value.equals(((SimpleStringMetadataIndexKey)obj).value);
        }
        return false;
    }
}

