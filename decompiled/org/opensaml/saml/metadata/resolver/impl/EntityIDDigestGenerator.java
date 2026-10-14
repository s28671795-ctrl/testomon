/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.codec.StringDigester;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.core.criterion.EntityIdCriterion;

public class EntityIDDigestGenerator
implements Function<CriteriaSet, String> {
    @Nonnull
    private StringDigester digester;
    @Nullable
    private String prefix;
    @Nullable
    private String suffix;
    @Nullable
    private String separator;

    public EntityIDDigestGenerator() {
        this(null, null, null, null);
    }

    public EntityIDDigestGenerator(@Nullable StringDigester valueDigester, @Nullable String keyPrefix, @Nullable String keySuffix, @Nullable String valueSeparator) {
        this.prefix = StringSupport.trimOrNull(keyPrefix);
        this.suffix = StringSupport.trimOrNull(keySuffix);
        this.separator = StringSupport.trimOrNull(valueSeparator);
        this.digester = valueDigester;
        if (this.digester == null) {
            try {
                this.digester = new StringDigester("SHA-1", StringDigester.OutputFormat.HEX_LOWER);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                // empty catch block
            }
        }
    }

    @Override
    public String apply(CriteriaSet input) {
        if (input == null) {
            return null;
        }
        EntityIdCriterion entityIDCrit = input.get(EntityIdCriterion.class);
        if (entityIDCrit == null) {
            return null;
        }
        String entityID = StringSupport.trimOrNull(entityIDCrit.getEntityId());
        if (entityID == null) {
            return null;
        }
        String digested = this.digester.apply(entityID);
        if (this.prefix == null && this.suffix == null) {
            return digested;
        }
        StringBuffer buffer = new StringBuffer();
        if (this.prefix != null) {
            buffer.append(this.prefix);
            if (this.separator != null) {
                buffer.append(this.separator);
            }
        }
        buffer.append(digested);
        if (this.suffix != null) {
            if (this.separator != null) {
                buffer.append(this.separator);
            }
            buffer.append(this.suffix);
        }
        return buffer.toString();
    }
}

