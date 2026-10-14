/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.codec.StringDigester;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class MetadataQueryProtocolSHA1Transformer
implements Function<String, String> {
    private StringDigester digester;

    public MetadataQueryProtocolSHA1Transformer() {
        try {
            this.digester = new StringDigester("SHA-1", StringDigester.OutputFormat.HEX_LOWER);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            // empty catch block
        }
    }

    @Override
    @Nullable
    public String apply(@Nullable String input) {
        String entityID = Constraint.isNotNull(StringSupport.trimOrNull(input), "Entity ID was null or empty");
        String digested = this.digester.apply(entityID);
        if (digested == null) {
            return null;
        }
        return "{sha1}" + digested;
    }
}

